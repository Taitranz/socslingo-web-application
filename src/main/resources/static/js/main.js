function toggleActiveState(el) {
    // Find the container whether clicked on container or any child element
    const container = el.classList.contains("select-pairs-toggle") ? el : el.closest(".select-pairs-toggle");
    if (!container) return;

    const button = container.querySelector(".select-pairs__top");
    container.classList.toggle("active");

    button.classList.remove("border-changed");
    const btnBottom = container.querySelector(".select-pairs__bottom");
    const btnMiddle = container.querySelector(".select-pairs__middle");

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
