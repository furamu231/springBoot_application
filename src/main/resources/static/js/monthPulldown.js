document.addEventListener("DOMContentLoaded", () => {
    const pulldownContainer = document.querySelector(".pulldown-container");
    const pulldownMenu = document.querySelector(".pulldown-menu");
    const subContainer = document.querySelector(".sub-container");

    // メニューの表示・非表示を切り替え
    pulldownContainer.addEventListener("click", (event) => {
        event.stopPropagation();
        const isVisible = pulldownMenu.style.display === "block";
        pulldownMenu.style.display = isVisible ? "none" : "block";

        // プルダウンメニューが表示されている場合は .sub-container のクリックを無効化
        if (pulldownMenu.style.display === "block") {
            subContainer.classList.add("disabled");
        } else {
            subContainer.classList.remove("disabled");
        }
    });

    // メニューアイテムがクリックされたときの処理
    document.querySelectorAll(".menu-item").forEach((item) => {
        item.addEventListener("click", (event) => {
            event.stopPropagation();
            const selectedText = event.target.textContent;
            pulldownContainer.querySelector(".text-box").textContent = selectedText;
            pulldownMenu.style.display = "none";
            subContainer.classList.remove("disabled");
        });
    });

    // プルダウンメニュー以外をクリックしたときに閉じる
    document.addEventListener("click", () => {
        pulldownMenu.style.display = "none";
        subContainer.classList.remove("disabled");
    });
});