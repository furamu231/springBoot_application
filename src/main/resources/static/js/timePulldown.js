
document.addEventListener("DOMContentLoaded", () => {
    
    document.querySelectorAll(".dropdown-wrapper").forEach(wrapper => {
        const timeInput = wrapper.querySelector(".time-dropdown");

       
        let value = parseInt(timeInput.value, 10);

        wrapper.addEventListener("click", (event) => {
            const action = event.target.getAttribute("data-action");
            if (action === "increment") {
                value++;
            } else if (action === "decrement") {
                value = Math.max(0, value - 1); 
            }

            timeInput.value = value;
        });

        timeInput.addEventListener("change", () => {
            const newValue = parseInt(timeInput.value, 10);

            if (isNaN(newValue) || newValue < 0) {
                timeInput.value = value;
            } else {
                value = newValue; 
            }
        });
    });
});