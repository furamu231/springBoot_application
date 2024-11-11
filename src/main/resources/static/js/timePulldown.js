// 学習時間のプルダウンメニューを生成する関数
function createDropdown() {
    const select = document.createElement("select");
    select.className = "time-dropdown";

    for (let i = 1; i <= 180; i++) {
        const option = document.createElement("option");
        option.value = i;
        option.textContent = `${i}`;
        select.appendChild(option);
    }

    return select;
}

document.addEventListener("DOMContentLoaded", () => {
    document.querySelectorAll(".middle-box").forEach(middleBox => {
        const timeItem = middleBox.children[1]; 
        timeItem.textContent = ""; 
        timeItem.appendChild(createDropdown()); 
    });
});