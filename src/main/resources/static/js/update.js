// $(document).ready(function () {
//     // 「学習時間を保存する」ボタンのクリックイベント
//     $(".save-button").on("click", function () {
//         const learningDataId = $(this).data("id");
//         const newLearningTime = $(this).closest(".middle-box-item").prev().find(".time-dropdown").val();

//         console.log("learningDataId:", learningDataId);
//         console.log("newLearningTime:", newLearningTime);

//         // バリデーションチェック
//         if (!newLearningTime || isNaN(newLearningTime) || Number(newLearningTime) < 1) {
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
//                 learningTime: Number(newLearningTime)
//             }),
//             success: function () {
//                 const message = `学習時間が${newLearningTime}分に更新されました！`;
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
//         location.reload(); 
//     });
// });

$(document).ready(function () {
    /**
     * エラーメッセージ表示関数
     */
    function showError(message) {
        const $errorMessageContainer = $("#errorMessageContainer");
        $errorMessageContainer.text(message).css("visibility", "visible");
    }

    // 「学習時間を保存する」ボタンのクリックイベント
    $(".save-button").on("click", function () {
        const learningDataId = $(this).data("id");
        const newLearningTime = $(this).closest(".middle-box-item").prev().find(".time-dropdown").val();
        const learningDataName = $(this).closest(".middle-box").find(".middle-box-item").first().text().trim();

        console.log("learningDataId:", learningDataId);
        console.log("newLearningTime:", newLearningTime);
        console.log("learningDataName:", learningDataName);

        // バリデーションチェック
        if (!newLearningTime || isNaN(newLearningTime) || Number(newLearningTime) < 1) {
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
                learningTime: Number(newLearningTime)
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
        location.reload();
    });
});