// function createDropdown() {
//     const wrapper = document.createElement("div");
//     wrapper.className = "dropdown-wrapper";

//     const select = document.createElement("select");
//     select.className = "time-dropdown";

//     for (let i = 1; i <= 180; i++) {
//         const option = document.createElement("option");
//         option.value = i;
//         option.textContent = `${i}`;
//         select.appendChild(option);
//     }

//     const arrowContainer = document.createElement("div");
//     arrowContainer.className = "dropdown-arrow";

//     const arrowUp = document.createElement("div");
//     arrowUp.className = "arrow-up";

//     const arrowDown = document.createElement("div");
//     arrowDown.className = "arrow-down";

//     arrowContainer.appendChild(arrowUp);
//     arrowContainer.appendChild(arrowDown);

//     wrapper.appendChild(select);
//     wrapper.appendChild(arrowContainer);

//     return wrapper;
// }

// document.addEventListener("DOMContentLoaded", () => {
//     document.querySelectorAll(".middle-box").forEach(middleBox => {
//         const timeItem = middleBox.children[1];
//         timeItem.textContent = "";
//         timeItem.appendChild(createDropdown());
//     });
// });

document.addEventListener("DOMContentLoaded", () => {
    const maxOptions = 6666; 
    const optionsPerBatch = 100; 

    document.querySelectorAll(".dropdown-wrapper").forEach(wrapper => {
        const selectElement = wrapper.querySelector(".time-dropdown");
        const selectedValue = wrapper.getAttribute("data-selected"); 
        let isOptionsLoaded = false;

        const createInitialOptions = () => {
            for (let i = 1; i <= optionsPerBatch; i++) {
                const option = document.createElement("option");
                option.value = i;
                option.textContent = i;

                if (i === parseInt(selectedValue, 10)) {
                    option.selected = true;
                }

                selectElement.appendChild(option);
            }
        };

        const loadAdditionalOptions = () => {
            if (isOptionsLoaded) return; 
            isOptionsLoaded = true;

            for (let i = optionsPerBatch + 1; i <= maxOptions; i++) {
                const option = document.createElement("option");
                option.value = i;
                option.textContent = i;
                selectElement.appendChild(option);
            }
        };

        createInitialOptions();

        selectElement.addEventListener("focus", loadAdditionalOptions);
    });
});