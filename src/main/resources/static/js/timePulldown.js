// 学習時間のプルダウンメニューを生成する関数
function createDropdown() {
    const wrapper = document.createElement("div");
    wrapper.className = "dropdown-wrapper";

    const select = document.createElement("select");
    select.className = "time-dropdown";

    for (let i = 1; i <= 180; i++) {
        const option = document.createElement("option");
        option.value = i;
        option.textContent = `${i}`;
        select.appendChild(option);
    }

    const arrowContainer = document.createElement("div");
    arrowContainer.className = "dropdown-arrow";

    const arrowUp = document.createElement("div");
    arrowUp.className = "arrow-up";

    const arrowDown = document.createElement("div");
    arrowDown.className = "arrow-down";

    arrowContainer.appendChild(arrowUp);
    arrowContainer.appendChild(arrowDown);

    wrapper.appendChild(select);
    wrapper.appendChild(arrowContainer);

    return wrapper;
}

document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll(".middle-box").forEach(middleBox => {
        const timeItem = middleBox.children[1];
        timeItem.textContent = "";
        timeItem.appendChild(createDropdown());
    });
});