

document.addEventListener('DOMContentLoaded', function () {
    const buttons = document.querySelectorAll('[data-toggle="button"]');
    buttons.forEach(button => {
        button.addEventListener('click', function (event) {
            event.preventDefault();
            buttons.forEach(btn => btn.classList.remove('active'));
            this.classList.add('active');
        });
    });
});