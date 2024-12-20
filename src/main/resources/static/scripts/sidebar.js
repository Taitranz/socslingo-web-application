
document.addEventListener('DOMContentLoaded', function () {
    const sidebarButtons = document.querySelectorAll('.sidebar-button');

    sidebarButtons.forEach(sidebarButton => {
        const anchor = sidebarButton.querySelector('a[data-toggle="button"]');
        const content = sidebarButton.querySelector('.content');

        anchor.addEventListener('click', function (event) {
            event.preventDefault();
            document.querySelectorAll('.sidebar-button .content').forEach(c => c.classList.remove('active'));
            content.classList.add('active');
        });
    });
});
