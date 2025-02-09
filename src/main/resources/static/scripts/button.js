document.addEventListener("DOMContentLoaded", function () {
    // List of button IDs
    const buttons = ["hiragana-button", "katakana-button", "kanji-button", "personal-button"];

    buttons.forEach((buttonId) => {
        const button = document.getElementById(buttonId);
        button.addEventListener("click", function (event) {
            event.preventDefault();

            // Reset all buttons and update their container's border color
            buttons.forEach((id) => {
                const btn = document.getElementById(id);
                btn.classList.remove("active");
                const container = btn.parentElement;
                container.classList.remove("border-blue-176");
                container.classList.add("border-grey-229");
            });


            // Mark the clicked button active and change its container's border color
            button.classList.add("active");
            const container = button.parentElement;
            container.classList.remove("border-grey-229");
            container.classList.add("border-blue-176");
        });
    });
});
