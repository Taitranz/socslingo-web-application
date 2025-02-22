// Sidebar.js

document.addEventListener("DOMContentLoaded", function () {
    const sidebarButtons = document.querySelectorAll("a[data-toggle='button']");
    // Get the current pathname (e.g. "/home")
    const currentPath = window.location.pathname;

    sidebarButtons.forEach((button) => {
        // If the button has an href, check if its pathname matches the current page
        const href = button.getAttribute("href");
        if (href) {
            const linkPath = new URL(button.href).pathname;
            if (linkPath === currentPath) {
                button.classList.add("active");
            }
        }

        // Attach the click event to update active state on click.
        button.addEventListener("click", function () {
            // Remove 'active' from all buttons.
            sidebarButtons.forEach((btn) => btn.classList.remove("active"));
            // Mark the clicked button as active.
            this.classList.add("active");



            
            // Navigation will occur naturally since we're not calling event.preventDefault()
        });
    });
});
