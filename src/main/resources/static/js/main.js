function toggleActiveState(el) {
    // Find the container whether clicked on container or any child element
    const container = el.classList.contains("btn-toggle--select-pairs") ? el : el.closest(".btn-toggle--select-pairs");
    if (!container) return;

    const button = container.querySelector(".btn__top--select-pairs");
    container.classList.toggle("active");

    button.classList.remove("border-changed");
    const btnBottom = container.querySelector(".btn__bottom--select-pairs");
    const btnMiddle = container.querySelector(".btn__middle--select-pairs");

    if (btnBottom) {
        btnBottom.classList.remove("border-changed");
    }
    if (btnMiddle) {
        btnMiddle.classList.remove("border-changed");
    }

    if (container.classList.contains("active")) {
        const transitionDuration = 125;
        setTimeout(() => {
            button.classList.add("border-changed");
            if (btnBottom) {
                btnBottom.classList.add("border-changed");
            }
            if (btnMiddle) {
                btnMiddle.classList.add("border-changed");
            }
        }, transitionDuration);
    }
}
