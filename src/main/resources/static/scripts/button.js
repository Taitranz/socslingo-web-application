document.addEventListener("DOMContentLoaded", function () {
    // Get all buttons
    const buttons = ["hiragana-button", "katakana-button", "kanji-button", "personal-button"];

    buttons.forEach((buttonId) => {
        const button = document.getElementById(buttonId);
        button.addEventListener("click", function (event) {
            event.preventDefault();

            // Remove active class from all buttons and containers
            buttons.forEach((id) => {
                const btn = document.getElementById(id);
                btn.classList.remove("active");
                btn.parentElement.classList.remove("active");
            });

            // Add active class to clicked button and its container
            button.classList.add("active");
            button.parentElement.classList.add("active");
        });
    });
});
