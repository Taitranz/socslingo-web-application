function toggleActiveState(button) {
    const container = button.parentElement;
    container.classList.toggle("active");

    button.classList.remove("border-changed");
    const btnBottom = container.querySelector(".btn__bottom--select-pairs");
    if (btnBottom) {
        btnBottom.classList.remove("border-changed");
    }

    if (container.classList.contains("active")) {
        const transitionDuration = 120;
        setTimeout(() => {
            button.classList.add("border-changed");
            if (btnBottom) {
                btnBottom.classList.add("border-changed");
            }
        }, transitionDuration);
    }
}
