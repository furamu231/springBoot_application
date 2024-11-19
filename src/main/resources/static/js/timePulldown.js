// 
document.addEventListener("DOMContentLoaded", () => {
    // 増減ボタンと入力欄のイベントリスナーを追加
    document.querySelectorAll(".dropdown-wrapper").forEach(wrapper => {
        const timeInput = wrapper.querySelector(".time-dropdown");

        // 初期値の取得とパース
        let value = parseInt(timeInput.value, 10);

        // 増減ボタンのクリックイベント
        wrapper.addEventListener("click", (event) => {
            const action = event.target.getAttribute("data-action");
            if (action === "increment") {
                value++;
            } else if (action === "decrement") {
                value = Math.max(0, value - 1); // 0以下にはならない
            }

            // 更新された値を表示
            timeInput.value = value;
        });

        // 手動入力の変更イベント
        timeInput.addEventListener("change", () => {
            const newValue = parseInt(timeInput.value, 10);

            // 数値が無効な場合は元の値に戻す
            if (isNaN(newValue) || newValue < 0) {
                timeInput.value = value;
            } else {
                value = newValue; // 有効な値の場合に更新
            }
        });
    });
});