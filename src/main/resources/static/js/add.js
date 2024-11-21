// $(document).ready(function () {
//     function showError(message) {
//         const $errorMessageContainer = $("#errorMessageContainer");
//         $errorMessageContainer.text(message).css("visibility", "visible");
//     }

//     const urlParams = new URLSearchParams(window.location.search);
//     const selectedMonth = urlParams.get("month");
//     const registeredMonth = selectedMonth ? `${selectedMonth}-01` : null;

//     const userId = $("#userId").val();
//     let categoryName = $("#categoryName").val();
//     let categoryId;

//     // 英語のカテゴリ名を日本語に変換するマッピング
//     const categoryMap = {
//         "Backend": "バックエンド",
//         "Frontend": "フロントエンド",
//         "Infra": "インフラ"
//     };

//     // カテゴリ名が英語の場合、日本語に変換
//     if (categoryMap[categoryName]) {
//         categoryName = categoryMap[categoryName];
//         $("#categoryName").val(categoryName); // hidden input の値も更新
//     }

//     $("#registerButton").on("click", function () {
//         const learningDataName = $("#learningDataName").val();
//         const learningTime = $("#learningTime").val();

//         if (!learningDataName) {
//             showError("項目名は必ず入力してください。");
//             return;
//         }

//         if (learningDataName.length > 50) {
//             showError("項目名は50文字以内で入力してください。");
//             return;
//         }

//         if (!learningTime) {
//             showError("学習時間は必ず入力してください。");
//             return;
//         }

//         // `0` が入力された場合もチェック
//         if (isNaN(learningTime) || Number(learningTime) <= 0) {
//             showError("学習時間は1以上の数字で入力してください。");
//             return;
//         }

//         if (!registeredMonth) {
//             showError("登録月が選択されていません。");
//             return;
//         }

//         // カテゴリIDの決定
//         switch (categoryName) {
//             case "バックエンド":
//                 categoryId = 1;
//                 break;
//             case "フロントエンド":
//                 categoryId = 2;
//                 break;
//             case "インフラ":
//                 categoryId = 3;
//                 break;
//             default:
//                 showError("無効なカテゴリが選択されました。");
//                 return;
//         }

//         // フォームデータの作成
//         const formData = {
//             userId: userId,
//             categoryId: categoryId,
//             learningDataName: learningDataName,
//             learningTime: learningTime,
//             registeredMonth: registeredMonth
//         };

//         console.log("送信するデータ:", formData);

//         // API リクエスト
//         $.ajax({
//             type: "POST",
//             url: "/api/skills/add",
//             contentType: "application/json",
//             data: JSON.stringify(formData),
//             success: function (response) {
//                 const message = `${categoryName} に ${learningDataName} を ${learningTime} 分で追加しました！`;
//                 $("#modalMessage").text(message);
//                 $("#successModal").show();
//             },
//             error: function (xhr) {
//                 if (xhr.status === 409) {
//                     showError(`「${learningDataName}」は既に登録されています。`);
//                 } else {
//                     const errorMessage = xhr.responseText || "登録処理中にエラーが発生しました。";
//                     showError(errorMessage);
//                 }
//             }
//         });
//     });

//     $("#closeModal").on("click", function () {
//         window.location.href = `/users/editSkill/${userId}?month=${selectedMonth}`;
//     });
// });

$(document).ready(function () {
    
    function showErrors(messages) {
        const $errorMessageContainer = $("#errorMessageContainer");
        const messageList = messages.map(msg => `<li>${msg}</li>`).join("");
        $errorMessageContainer.html(`<ul>${messageList}</ul>`).css("visibility", "visible");
    }

    const urlParams = new URLSearchParams(window.location.search);
    const selectedMonth = urlParams.get("month");
    const registeredMonth = selectedMonth ? `${selectedMonth}-01` : null;

    const userId = $("#userId").val();
    let categoryName = $("#categoryName").val();
    let categoryId;

    // 英語のカテゴリ名を日本語に変換するマッピング
    const categoryMap = {
        "Backend": "バックエンド",
        "Frontend": "フロントエンド",
        "Infra": "インフラ"
    };

    // カテゴリ名が英語の場合、日本語に変換
    if (categoryMap[categoryName]) {
        categoryName = categoryMap[categoryName];
        $("#categoryName").val(categoryName); 
    }

    $("#registerButton").on("click", function () {
        const learningDataName = $("#learningDataName").val();
        const learningTime = $("#learningTime").val();
        const errors = [];

        // 項目名のバリデーション
        if (!learningDataName) {
            errors.push("項目名は必ず入力してください");
        } else if (learningDataName.length > 50) {
            errors.push("項目名は50文字以内で入力してください");
        }

        // 学習時間のバリデーション
        if (!learningTime) {
            errors.push("学習時間は必ず入力してください");
        } else if (isNaN(learningTime) || Number(learningTime) <= 0) {
            errors.push("学習時間は1以上の数字で入力してください");
        }

        // デバック用
        if (!registeredMonth) {
            errors.push("登録月が選択されていません");
        }

        // デバック用
        switch (categoryName) {
            case "バックエンド":
                categoryId = 1;
                break;
            case "フロントエンド":
                categoryId = 2;
                break;
            case "インフラ":
                categoryId = 3;
                break;
            default:
                errors.push("無効なカテゴリが選択されました。");
        }

        // エラーがある場合は表示して処理を終了
        if (errors.length > 0) {
            showErrors(errors);
            return;
        }

        // フォームデータの作成
        const formData = {
            userId: userId,
            categoryId: categoryId,
            learningDataName: learningDataName,
            learningTime: learningTime,
            registeredMonth: registeredMonth
        };

        console.log("送信するデータ:", formData);

        // API リクエスト
        $.ajax({
            type: "POST",
            url: "/api/skills/add",
            contentType: "application/json",
            data: JSON.stringify(formData),
            success: function (response) {
                const message = `${categoryName} に ${learningDataName} を ${learningTime} 分で追加しました！`;
                $("#modalMessage").text(message);
                $("#successModal").show();
            },
            error: function (xhr) {
                if (xhr.status === 409) {
                    showErrors([`「${learningDataName}」は既に登録されています。`]);
                } else {
                    const errorMessage = xhr.responseText || "登録処理中にエラーが発生しました。";
                    showErrors([errorMessage]);
                }
            }
        });
    });

    $("#closeModal").on("click", function () {
        window.location.href = `/users/editSkill/${userId}?month=${selectedMonth}`;
    });
});