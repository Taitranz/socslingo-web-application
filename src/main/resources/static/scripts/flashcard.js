(() => {
    // Polyfill: if caretPositionFromPoint is missing but caretRangeFromPoint exists, define caretPositionFromPoint.
    if (!document.caretPositionFromPoint && document.caretRangeFromPoint) {
        document.caretPositionFromPoint = function (x, y) {
            const range = document.caretRangeFromPoint(x, y);
            return { offsetNode: range.startContainer, offset: range.startOffset };
        };
    }

    // Cache the punctuation regex to avoid re-compiling it on every iteration
    const punctuationRegex = /[.,\/#!$%\^&\*;:{}=\-_`~()'"?？！，、。「」『』（）]/;

    const getCaretOffset = (el) => {
        const sel = window.getSelection();
        if (!sel || !sel.rangeCount) return 0;

        const range = sel.getRangeAt(0);
        const preRange = range.cloneRange();
        preRange.selectNodeContents(el);
        preRange.setEnd(range.endContainer, range.endOffset);
        return preRange.toString().length;
    };

    const setCaretOffset = (el, offset) => {
        // Handle invalid inputs
        if (!el || typeof offset !== "number" || offset < 0) return;

        const walker = document.createTreeWalker(el, NodeFilter.SHOW_TEXT, null);
        let node,
            currentOffset = 0;
        const range = document.createRange();
        const sel = window.getSelection();

        try {
            while ((node = walker.nextNode())) {
                const nodeLen = node.textContent.length;
                if (currentOffset + nodeLen >= offset) {
                    const nodeOffset = Math.min(offset - currentOffset, nodeLen);
                    range.setStart(node, nodeOffset);
                    range.collapse(true);
                    sel.removeAllRanges();
                    sel.addRange(range);
                    return;
                }
                currentOffset += nodeLen;
            }

            // If we couldn't find the exact position, place caret at the end
            if (el.lastChild) {
                range.selectNodeContents(el.lastChild);
                range.collapse(false);
                sel.removeAllRanges();
                sel.addRange(range);
            }
        } catch (error) {
            console.error("Error setting caret position:", error);
        }
    };

    const createSpannedHTML = (text) =>
        Array.from(text)
            .map((char) => {
                const isSpace = char === " " || char === "\u00A0";
                const isPunctuation = punctuationRegex.test(char);
                return `<span class="flashcard-char${isSpace ? " space" : ""}${isPunctuation ? " punctuation" : ""}">${isSpace ? "&nbsp;" : char}</span>`;
            })
            .join("");

    const updateHiddenInput = (flashEl, text) => {
        if (flashEl.hiddenInput) {
            flashEl.hiddenInput.value = text.replace(/\u00A0/g, " ");
        }
    };

    const resetPlaceholder = (flashEl) => {
        const placeholderText = flashEl.dataset.placeholder || "";
        flashEl.innerHTML = createSpannedHTML(placeholderText);
        flashEl.classList.remove("placeholder-cleared");
        updateHiddenInput(flashEl, "");
        // Reset our cached value
        flashEl._lastSanitizedText = "";
    };

    const onFocus = ({ currentTarget: flashEl }) => {
        // Only set caret at end if not being clicked.
        if (!flashEl._mouseIsDown) {
            setCaretOffset(flashEl, flashEl.textContent.length);
        }
    };

    /**
     * Try to get a caret range near the given (x, y) within a small tolerance.
     * Returns a valid range if a non-whitespace character is found, otherwise null.
     */
    function getAdjustedRange(x, y) {
        const tolerance = 3; // pixels
        for (let dx = -tolerance; dx <= tolerance; dx++) {
            let testX = x + dx;
            let range = null;
            const pos = document.caretPositionFromPoint(testX, y);
            if (pos) {
                range = document.createRange();
                range.setStart(pos.offsetNode, pos.offset);
                range.collapse(true);
            }
            if (
                range &&
                range.startContainer.nodeType === Node.TEXT_NODE &&
                range.startContainer.textContent.charAt(Math.min(range.startOffset, range.startContainer.textContent.length - 1)).trim() !== ""
            ) {
                return range;
            }
        }
        return null;
    }

    // Updated onClick: attempts to set the caret at the clicked position,
    // unless the click is on whitespace – in which case it defaults to the end.
    const onClick = (event) => {
        const flashEl = event.currentTarget;
        const x = event.clientX;
        const y = event.clientY;
        let range = getAdjustedRange(x, y);

        if (range && flashEl.contains(range.startContainer)) {
            const sel = window.getSelection();
            sel.removeAllRanges();
            sel.addRange(range);
        } else {
            // Fallback: place the caret at the end.
            setCaretOffset(flashEl, flashEl.textContent.length);
        }
    };

    const onKeyDown = ({ currentTarget: flashEl, key }) => {
        if (!flashEl.classList.contains("placeholder-cleared") && key.length === 1) {
            flashEl.innerHTML = "";
            flashEl.classList.add("placeholder-cleared");
        }
    };

    const onBlur = ({ currentTarget: flashEl }) => {
        if (!flashEl.textContent.trim()) {
            resetPlaceholder(flashEl);
        }
    };

    const onInput = ({ currentTarget: flashEl, inputType }) => {
        if (flashEl._inputTimeout) {
            clearTimeout(flashEl._inputTimeout);
        }

        // Debounce input with a delay (50ms can be increased if needed)
        flashEl._inputTimeout = setTimeout(() => {
            const text = flashEl.textContent;
            if (!text) {
                updateHiddenInput(flashEl, "");
                if (inputType === "deleteContentBackward" || inputType === "deleteContentForward") {
                    resetPlaceholder(flashEl);
                }
                return;
            }

            // Capture the caret offset before sanitizing
            const caretPos = getCaretOffset(flashEl);
            flashEl.classList.add("placeholder-cleared");

            // Sanitize input text (removes zero-width spaces and newlines)
            const sanitizedText = text.replace(/\u200B/g, "").replace(/\r?\n|\r/g, "");

            // Skip update if the sanitized text hasn't changed
            if (flashEl._lastSanitizedText === sanitizedText) return;
            flashEl._lastSanitizedText = sanitizedText;

            // Update the displayed HTML with the dotted-line spans
            const spannedHTML = createSpannedHTML(sanitizedText);
            flashEl.innerHTML = spannedHTML;
            updateHiddenInput(flashEl, sanitizedText);

            // Restore the caret offset (ensure it is within the new text length)
            setCaretOffset(flashEl, Math.min(caretPos, sanitizedText.length));
        }, 50);
    };

    document.addEventListener("DOMContentLoaded", () => {
        document.querySelectorAll(".flashcard-input").forEach((flashEl) => {
            flashEl.addEventListener("mousedown", () => {
                flashEl._mouseIsDown = true;
            });
            flashEl.addEventListener("mouseup", () => {
                flashEl._mouseIsDown = false;
            });

            // Link the hidden input based on the data attribute
            flashEl.hiddenInput = document.getElementById(flashEl.dataset.hiddenInput);
            if (!flashEl.textContent.trim()) {
                resetPlaceholder(flashEl);
            }
            flashEl.addEventListener("focus", onFocus);
            flashEl.addEventListener("click", onClick);
            flashEl.addEventListener("keydown", onKeyDown);
            flashEl.addEventListener("blur", onBlur);
            flashEl.addEventListener("input", onInput);
        });

        document.querySelectorAll(".flashcard-wrapper").forEach((wrapper) => {
            wrapper.addEventListener("click", (e) => {
                if (e.target.closest(".flashcard-input")) return;

                const rect = wrapper.getBoundingClientRect();
                const clickY = e.clientY - rect.top;
                const halfHeight = rect.height / 2;

                const flashcards = wrapper.querySelectorAll(".flashcard-input");
                if (flashcards.length > 1) {
                    if (clickY < halfHeight) {
                        flashcards[0].focus();
                    } else {
                        flashcards[1].focus();
                    }
                } else if (flashcards.length === 1) {
                    flashcards[0].focus();
                }
            });
        });
    });

    // Expose an initializer for dynamically added flashcard inputs.
    // This function sets up the placeholder (with dotted lines) and event listeners.
    window.initializeFlashcardInput = function (flashEl) {
        flashEl.hiddenInput = document.getElementById(flashEl.dataset.hiddenInput);
        if (!flashEl.textContent.trim()) {
            resetPlaceholder(flashEl);
        }
        flashEl.addEventListener("focus", onFocus);
        flashEl.addEventListener("click", onClick);
        flashEl.addEventListener("keydown", onKeyDown);
        flashEl.addEventListener("blur", onBlur);
        flashEl.addEventListener("input", onInput);
    };
})();
