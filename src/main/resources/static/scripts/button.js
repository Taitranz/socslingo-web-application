document.addEventListener("DOMContentLoaded", function () {
    const toggleButtons = document.querySelectorAll(".btn-toggle--blue");
    const activityButtons = document.querySelectorAll(".btn-toggle--activity .btn__top");

    // Set the default active button ("following-button")
    const defaultButton = document.getElementById("following-button");
    if (defaultButton) {
        defaultButton.classList.add("active");
        const container = defaultButton.parentElement;
        container.classList.remove("border-grey-229");
        container.classList.add("border-blue-176");
    }

    // Updated toggle for blue buttons to allow untoggling
    toggleButtons.forEach((button) => {
        button.addEventListener("click", function (event) {
            event.preventDefault();
            const container = button.parentElement;
            if (button.classList.contains("active")) {
                // If already active, untoggle it
                button.classList.remove("active");
                container.classList.remove("border-blue-176");
                container.classList.add("border-grey-229");
            } else {
                // Reset all toggle buttons
                toggleButtons.forEach((btn) => {
                    btn.classList.remove("active");
                    const cont = btn.parentElement;
                    cont.classList.remove("border-blue-176");
                    cont.classList.add("border-grey-229");
                });
                // Activate the clicked button
                button.classList.add("active");
                container.classList.remove("border-grey-229");
                container.classList.add("border-blue-176");
            }
        });
    });

    // Updated toggle for activity buttons to allow untoggling
    activityButtons.forEach((button) => {
        button.addEventListener("click", function () {
            const container = this.closest(".btn-toggle--activity");
            if (container.classList.contains("active")) {
                container.classList.remove("active");
            } else {
                const allToggles = document.querySelectorAll(".btn-toggle--activity");
                allToggles.forEach((toggle) => toggle.classList.remove("active"));
                container.classList.add("active");
            }
        });
    });
});

// Updated function toggleActiveState for activity buttons to allow untoggling
function toggleActiveState(el) {
    const container = el.closest(".btn-toggle--activity");
    if (container.classList.contains("active")) {
        container.classList.remove("active");
    } else {
        const allToggles = document.querySelectorAll(".btn-toggle--activity");
        allToggles.forEach((toggle) => toggle.classList.remove("active"));
        container.classList.add("active");
    }
}

