// document.addEventListener("DOMContentLoaded", () => {
//     const monthDropdown = document.querySelector("#month-dropdown");

//     if (!monthDropdown) {
//         console.error("month-dropdown 要素が見つかりません。");
//         return;
//     }

//     const currentDate = new Date();
//     const months = [];

//     // 現在の月から過去2か月分を追加
//     for (let i = 0; i < 3; i++) {
//         const date = new Date(currentDate.getFullYear(), currentDate.getMonth() - i, 1);
//         const year = date.getFullYear();
//         const month = date.getMonth() + 1; // JavaScript の月は 0 インデックスなので +1

//         // 月のみの表示 ("11月", "10月", "9月")
//         const monthText = `${month}月`;
//         const monthValue = `${year}-${String(month).padStart(2, "0")}`;

//         // オプション要素を作成してプルダウンに追加
//         const option = document.createElement("option");
//         option.value = monthValue;
//         option.textContent = monthText;
//         monthDropdown.appendChild(option);
//     }

//     // 初期選択は当月に設定
//     monthDropdown.value = `${currentDate.getFullYear()}-${String(currentDate.getMonth() + 1).padStart(2, "0")}`;

//      // 月が選択されたときの処理
//      monthDropdown.addEventListener("change", () => {
//         const selectedMonth = monthDropdown.value;
//         console.log(`選択された月: ${selectedMonth}`); // デバッグ用

//         // 正しい URL にリダイレクト
//         window.location.href = `/users/editSkill/${userId}?month=${selectedMonth}`;
//     });
// });

document.addEventListener("DOMContentLoaded", () => {
    const monthDropdown = document.querySelector("#month-dropdown");
    const userIdElement = document.querySelector("#user-id");

    // userId の取得
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

    // URL パラメータから 'month' を取得
    const urlParams = new URLSearchParams(window.location.search);
    const selectedMonth = urlParams.get("month") || `${currentDate.getFullYear()}-${String(currentDate.getMonth() + 1).padStart(2, "0")}`;

    // 現在の月から過去2か月分をプルダウンに追加
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

    // URL パラメータの 'month' を初期選択に設定
    monthDropdown.value = selectedMonth;

    // 月が選択されたときの処理
    monthDropdown.addEventListener("change", () => {
        const newSelectedMonth = monthDropdown.value;
        console.log(`選択された月: ${newSelectedMonth}`);

        // URL にリダイレクト
        window.location.href = `/users/editSkill/${userId}?month=${newSelectedMonth}`;
    });
});