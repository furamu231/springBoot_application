// $(document).ready(function () {
   
//     function showError(message) {
//         const $errorMessageContainer = $("#errorMessageContainer");
//         $errorMessageContainer.text(message).css("visibility", "visible");
//     }

//     // 「学習時間を保存する」ボタンのクリックイベント
//     $(".save-button").on("click", function () {
//         const learningDataId = $(this).data("id");
//         const newLearningTime = $(this).closest(".middle-box-item").prev().find(".time-dropdown").text().trim();
//         const learningDataName = $(this).closest(".middle-box").find(".middle-box-item").first().text().trim();

//         console.log("learningDataId:", learningDataId);
//         console.log("newLearningTime:", newLearningTime);
//         console.log("learningDataName:", learningDataName);

//         // バリデーションチェック
//         if (!newLearningTime || isNaN(newLearningTime) || parseInt(newLearningTime, 10) < 1) {
//             showError("学習時間は1以上の数字で選択してください。");
//             return;
//         }

//         // 更新リクエストの送信
//         $.ajax({
//             type: "PUT",
//             url: "/api/skills/update",
//             contentType: "application/json",
//             data: JSON.stringify({
//                 id: learningDataId,
//                 learningTime: parseInt(newLearningTime, 10)
//             }),
//             success: function () {
//                 const message = `${learningDataName}の学習時間を${newLearningTime}分に更新しました！`;
//                 $("#modalMessage").text(message);
//                 $("#successModal").show();
//             },
//             error: function (xhr) {
//                 const errorMessage = xhr.responseText || "更新処理中にエラーが発生しました。";
//                 showError(errorMessage);
//             }
//         });
//     });

//     // モーダルの閉じるボタンのクリックイベント
//     $("#closeModalButton").on("click", function () {
//         $("#successModal").hide();
//         location.reload(); // 必要に応じてコメントアウト
//     });
// });

$(document).ready(function () {
    function showError(message) {
        const $errorMessageContainer = $("#errorMessageContainer");
        $errorMessageContainer.text(message).css("visibility", "visible");
    }

    // 「学習時間を保存する」ボタンのクリックイベント
    $(".save-button").on("click", function () {
        const learningDataId = $(this).data("id");
        // 修正箇所: text() -> val()
        const newLearningTime = $(this).closest(".middle-box-item").prev().find(".time-dropdown").val().trim();
        const learningDataName = $(this).closest(".middle-box").find(".middle-box-item").first().text().trim();

        console.log("learningDataId:", learningDataId);
        console.log("newLearningTime:", newLearningTime);
        console.log("learningDataName:", learningDataName);

        // バリデーションチェック
        if (!newLearningTime || isNaN(newLearningTime) || parseInt(newLearningTime, 10) < 1) {
            showError("学習時間は1以上の数字で選択してください。");
            return;
        }

        // 更新リクエストの送信
        $.ajax({
            type: "PUT",
            url: "/api/skills/update",
            contentType: "application/json",
            data: JSON.stringify({
                id: learningDataId,
                learningTime: parseInt(newLearningTime, 10)
            }),
            success: function () {
                const message = `${learningDataName}の学習時間を${newLearningTime}分に更新しました！`;
                $("#modalMessage").text(message);
                $("#successModal").show();
            },
            error: function (xhr) {
                const errorMessage = xhr.responseText || "更新処理中にエラーが発生しました。";
                showError(errorMessage);
            }
        });
    });

    // モーダルの閉じるボタンのクリックイベント
    $("#closeModalButton").on("click", function () {
        $("#successModal").hide();
        location.reload(); // 必要に応じてコメントアウト
    });
});