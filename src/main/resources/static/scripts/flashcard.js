(() => {
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
        const walker = document.createTreeWalker(el, NodeFilter.SHOW_TEXT, null);
        let node,
            currentOffset = 0;
        const range = document.createRange();
        const sel = window.getSelection();

        while ((node = walker.nextNode())) {
            const nodeLen = node.textContent.length;
            if (currentOffset + nodeLen >= offset) {
                range.setStart(node, offset - currentOffset);
                range.collapse(true);
                sel.removeAllRanges();
                sel.addRange(range);
                return;
            }
            currentOffset += nodeLen;
        }

        if (el.lastChild) {
            range.selectNodeContents(el);
            range.collapse(false);
            sel.removeAllRanges();
            sel.addRange(range);
        }
    };

    const createSpannedHTML = (text) =>
        Array.from(text)
            .map((char) => {
                const isSpace = char === " " || char === "\u00A0";
                const isPunctuation = /[.,\/#!$%\^&\*;:{}=\-_`~()'"?？！，、。「」『』（）]/.test(char);
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
    };

    const onFocus = ({ currentTarget: flashEl }) => {
        if (!flashEl.classList.contains("placeholder-cleared")) {
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

    const onInput = ({ currentTarget: flashEl }) => {
        const text = flashEl.textContent;

        if (!text) {
            updateHiddenInput(flashEl, "");
            return;
        }
        const caretPos = getCaretOffset(flashEl);
        flashEl.classList.add("placeholder-cleared");

        flashEl.innerHTML = createSpannedHTML(text);
        updateHiddenInput(flashEl, text);
        setCaretOffset(flashEl, caretPos);
    };

    document.addEventListener("DOMContentLoaded", () => {
        document.querySelectorAll(".flashcard-input").forEach((flashEl) => {
            flashEl.hiddenInput = document.getElementById(flashEl.dataset.hiddenInput);
            if (!flashEl.textContent.trim()) {
                resetPlaceholder(flashEl);
            }
            flashEl.addEventListener("focus", onFocus);
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
})();
