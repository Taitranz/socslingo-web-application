document.addEventListener("DOMContentLoaded", function () {
    // Get the flashcards container and the number-of-cards input element
    var flashcardsContainer = document.getElementById("flashcards-container");
    var numCardsInput = document.getElementById("numCards");

    /**
     * Creates and returns a flashcard element for a given index.
     * @param {number} index - The index (card number) for the flashcard.
     * @returns {HTMLElement} - The flashcard element.
     */
    function createFlashcard(index) {
        // Create main card container
        var cardContainer = document.createElement("div");
        cardContainer.classList.add("container", "flex-col");

        // Create card heading
        var heading = document.createElement("h2");
        heading.classList.add("text-grey-75");
        heading.textContent = "Card " + index;
        cardContainer.appendChild(heading);

        // Create wrapper for flashcard inputs
        var flashcardWrapper = document.createElement("div");
        flashcardWrapper.classList.add("container", "flex", "flex-col", "w-min-160", "h-96", "p-16", "border-2", "border-grey-224", "radius-16", "w-fit-content");


        // Create front flashcard input (contenteditable div)
        var frontDiv = document.createElement("div");
        frontDiv.contentEditable = "true";
        frontDiv.classList.add("border-none", "bg-transparent", "outline-none", "w-fit-content", "pb-4", "fs-22", "flashcard-input");
        frontDiv.setAttribute("data-placeholder", "Enter front text");
        frontDiv.setAttribute("data-hidden-input", "front-" + index);
        flashcardWrapper.appendChild(frontDiv);


        // Create back flashcard input (contenteditable div)
        var backDiv = document.createElement("div");
        backDiv.contentEditable = "true";
        backDiv.classList.add("border-none", "bg-transparent", "outline-none", "w-fit-content", "fs-22", "flashcard-input");
        backDiv.setAttribute("data-placeholder", "Enter back text");
        backDiv.setAttribute("data-hidden-input", "back-" + index);
        flashcardWrapper.appendChild(backDiv);


        // Create hidden input for front side of the flashcard
        var frontInput = document.createElement("input");
        frontInput.type = "hidden";
        frontInput.id = "front-" + index;
        frontInput.name = "front[]";
        frontInput.value = "";
        flashcardWrapper.appendChild(frontInput);

        // Create hidden input for back side of the flashcard
        var backInput = document.createElement("input");
        backInput.type = "hidden";
        backInput.id = "back-" + index;
        backInput.name = "back[]";
        backInput.value = "";
        flashcardWrapper.appendChild(backInput);

        // IMPORTANT: Initialize the dynamic flashcard inputs so they get the dotted line placeholder
        if (window.initializeFlashcardInput) {
            window.initializeFlashcardInput(frontDiv);
            window.initializeFlashcardInput(backDiv);
        }

        // Append the flashcard wrapper to the card container
        cardContainer.appendChild(flashcardWrapper);

        return cardContainer;
    }

    /**
     * Updates the flashcards container by generating the required number of flashcard elements.
     * @param {number} numCards - The number of flashcards to display.
     */
    function updateFlashcards(numCards) {
        // Clear the container
        flashcardsContainer.innerHTML = "";

        // Create and append flashcard elements
        for (var i = 1; i <= numCards; i++) {
            var flashcard = createFlashcard(i);
            flashcardsContainer.appendChild(flashcard);
        }
    }

    // Listen for changes on the number input and update the flashcards accordingly.
    if (numCardsInput) {
        numCardsInput.addEventListener("input", function () {
            var numCards = parseInt(this.value, 10);
            if (isNaN(numCards) || numCards < 1) {
                numCards = 1;
            } else if (numCards > 30) {
                numCards = 30;
            }
            updateFlashcards(numCards);
        });

        // Initialize the flashcards
        updateFlashcards(parseInt(numCardsInput.value, 10));
    }
});

document.addEventListener("DOMContentLoaded", function () {
    const numCardsInput = document.getElementById("numCards");
    if (!numCardsInput) return;

    // Create a hidden mirror element if it doesn't exist
    let mirror = document.getElementById("numCardsMirror");
    if (!mirror) {
        mirror = document.createElement("span");
        mirror.id = "numCardsMirror";
        mirror.style.cssText = "position: absolute; visibility: hidden; white-space: pre;";
        document.body.appendChild(mirror);
    }

    function updateInputWidth() {
        mirror.textContent = numCardsInput.value;
        // Adding a few extra pixels for padding/spinner size if needed
        numCardsInput.style.width = mirror.getBoundingClientRect().width + 10 + "px";
    }

    numCardsInput.addEventListener("input", updateInputWidth);
    updateInputWidth();
});
