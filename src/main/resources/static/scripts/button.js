document.addEventListener("DOMContentLoaded", function () {
    const hiraganaButton = document.getElementById("hiragana-button");
    // Toggle the 'active' class on button click
    hiraganaButton.addEventListener("click", function (event) {
        event.preventDefault();
        hiraganaButton.classList.toggle("active");
    });
});
