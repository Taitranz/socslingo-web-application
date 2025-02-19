document.addEventListener("DOMContentLoaded", function () {
    const toggleButtons = document.querySelectorAll(".btn-toggle--blue");

    // Set the default active button ("following-button")
    const defaultButton = document.getElementById("following-button");
    if (defaultButton) {
        defaultButton.classList.add("active");
        const container = defaultButton.parentElement;
        container.classList.remove("border-grey-229");
        container.classList.add("border-blue-176");
    }

    toggleButtons.forEach((button) => {
        button.addEventListener("click", function (event) {
            event.preventDefault();

            // Reset all toggle buttons on this page
            toggleButtons.forEach((btn) => {
                btn.classList.remove("active");
                const container = btn.parentElement;
                container.classList.remove("border-blue-176");
                container.classList.add("border-grey-229");
            });

            // Activate the clicked button
            button.classList.add("active");
            const container = button.parentElement;
            container.classList.remove("border-grey-229");
            container.classList.add("border-blue-176");
        });
    });
});
