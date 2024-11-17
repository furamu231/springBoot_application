document.addEventListener("DOMContentLoaded", () => {
    const monthDropdown = document.querySelector("#month-dropdown");
    const userIdElement = document.querySelector("#user-id");

    const userId = userIdElement ? userIdElement.value : null;

    if (!userId) {
        console.error("userId が取得できませんでした。HTML に <input type='hidden' id='user-id'> が正しく設定されているか確認してください。");
        return;
    }

    if (!monthDropdown) {
        console.error("month-dropdown 要素が見つかりません。");
        return;
    }

    const currentDate = new Date();

    const urlParams = new URLSearchParams(window.location.search);
    const selectedMonth = urlParams.get("month") || `${currentDate.getFullYear()}-${String(currentDate.getMonth() + 1).padStart(2, "0")}`;

    for (let i = 0; i < 3; i++) {
        const date = new Date(currentDate.getFullYear(), currentDate.getMonth() - i, 1);
        const year = date.getFullYear();
        const month = date.getMonth() + 1;

        const monthText = `${month}月`;
        const monthValue = `${year}-${String(month).padStart(2, "0")}`;

        const option = document.createElement("option");
        option.value = monthValue;
        option.textContent = monthText;
        monthDropdown.appendChild(option);
    }

    monthDropdown.value = selectedMonth;

    monthDropdown.addEventListener("change", () => {
        const newSelectedMonth = monthDropdown.value;
        console.log(`選択された月: ${newSelectedMonth}`);

        window.location.href = `/users/editSkill/${userId}?month=${newSelectedMonth}`;
    });
    
});