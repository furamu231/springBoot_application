// document.addEventListener("DOMContentLoaded", () => {
//     const maxOptions = 6666; // 最大オプション数
//     const batchSize = 50;   // 一度に生成するオプションの数

//     document.querySelectorAll(".dropdown-wrapper").forEach(wrapper => {
//         const selectElement = wrapper.querySelector(".time-dropdown");
//         const selectedValue = parseInt(wrapper.getAttribute("data-selected"), 10);

//         // 初期設定: 初期値を中心にオプションを生成
//         loadOptions(selectElement, Math.max(1, selectedValue - batchSize / 2), Math.min(maxOptions, selectedValue + batchSize / 2), selectedValue);

//         // 仮想スクロールのイベント処理
//         selectElement.addEventListener("scroll", () => {
//             const scrollPosition = selectElement.scrollTop / (selectElement.scrollHeight / maxOptions);
//             const firstOptionValue = parseInt(selectElement.firstChild.value, 10);
//             const lastOptionValue = parseInt(selectElement.lastChild.value, 10);

//             // 必要に応じて上方向または下方向のオプションを生成
//             if (scrollPosition < firstOptionValue && firstOptionValue > 1) {
//                 loadOptions(selectElement, Math.max(1, firstOptionValue - batchSize), firstOptionValue - 1, selectedValue, true);
//                 removeExtraOptions(selectElement, false); // 下方向の不要なオプションを削除
//             }
//             if (scrollPosition > lastOptionValue && lastOptionValue < maxOptions) {
//                 loadOptions(selectElement, lastOptionValue + 1, Math.min(maxOptions, lastOptionValue + batchSize), selectedValue, false);
//                 removeExtraOptions(selectElement, true); // 上方向の不要なオプションを削除
//             }
//         });

//         // 初期値を中心にスクロール
//         scrollToInitialValue(selectElement, selectedValue);
//     });

//     // オプションの生成と削除
//     function loadOptions(selectElement, start, end, selectedValue, prepend = false) {
//         for (let i = start; i <= end; i++) {
//             if (!Array.from(selectElement.options).some(option => parseInt(option.value, 10) === i)) {
//                 const option = document.createElement("option");
//                 option.value = i;
//                 option.textContent = i;
//                 if (i === selectedValue) option.selected = true;
//                 if (prepend) {
//                     selectElement.insertBefore(option, selectElement.firstChild);
//                 } else {
//                     selectElement.appendChild(option);
//                 }
//             }
//         }
//     }

//     function removeExtraOptions(selectElement, removeTop) {
//         const options = Array.from(selectElement.options);
//         while (options.length > batchSize * 2) {
//             if (removeTop && options[0]) {
//                 selectElement.removeChild(options.shift());
//             } else if (!removeTop && options[options.length - 1]) {
//                 selectElement.removeChild(options.pop());
//             }
//         }
//     }

//     function scrollToInitialValue(selectElement, selectedValue) {
//         const options = Array.from(selectElement.options);
//         const selectedOptionIndex = options.findIndex(option => parseInt(option.value, 10) === selectedValue);
//         if (selectedOptionIndex !== -1) {
//             const optionHeight = selectElement.scrollHeight / options.length;
//             selectElement.scrollTop = selectedOptionIndex * optionHeight - selectElement.clientHeight / 2;
//         }
//     }
// });

document.addEventListener("DOMContentLoaded", () => {
    // プルダウンを動的に生成
    document.querySelectorAll(".dropdown-wrapper").forEach(wrapper => {
        const selectElement = wrapper.querySelector(".time-dropdown");
        const selectedValue = wrapper.getAttribute("data-selected"); // 初期値を取得
        const maxOptions = 6666; // 最大オプション数

        // プルダウンのオプションを生成
        for (let i = 1; i <= maxOptions; i++) {
            const option = document.createElement("option");
            option.value = i;
            option.textContent = i;

            // 初期値を選択状態に設定
            if (i === parseInt(selectedValue, 10)) {
                option.selected = true;
            }

            selectElement.appendChild(option);
        }
    });
});