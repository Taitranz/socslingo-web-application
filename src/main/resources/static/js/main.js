function toggleActiveState(el) {
    const container = el.parentElement;
    let button;
    // If the clicked element is btn__middle, use the btn__top element for styling
    if (el.classList.contains("btn__middle")) {
        button = container.querySelector(".btn__top");
    } else {
        button = el;
    }

    container.classList.toggle("active");

    button.classList.remove("border-changed");
    const btnBottom = container.querySelector(".btn__bottom--select-pairs");
    const btnMiddle = container.querySelector(".btn__middle");

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
