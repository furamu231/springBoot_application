// $(document).ready(function () {
//     // 「削除」ボタンのクリックイベント（デリゲートを使用）
//     $("body").on("click", ".delete-button", function () {
//         const learningDataId = $(this).data("id");

//         console.log("削除対象のID:", learningDataId);

//         if (!learningDataId) {
//             alert("削除対象のIDが取得できませんでした。");
//             return;
//         }

//         // DELETE リクエストを送信
//         $.ajax({
//             type: "DELETE",
//             url: `/api/skills/delete/${learningDataId}`,
//             success: function () {
//                 // 削除成功時のメッセージ表示
//                 const message = `「${learningDataName}」を削除しました！`;
//                 $("#modalMessage").text(message);
//                 $("#successModal").show();
//             },
//             error: function (xhr) {
//                 const errorMessage = xhr.responseText || "削除処理中にエラーが発生しました。";
//                 alert(errorMessage);
//             }
//         });
//     });

//     // 「閉じる」ボタンのクリックイベント
//     $("#closeModalButton").on("click", function () {
//         $("#successModal").hide(); // モーダルを非表示にする
//         location.reload(); // ページをリロードして変更を反映
//     });
// });

$(document).ready(function () {
    // 「削除」ボタンのクリックイベント（デリゲートを使用）
    $("body").on("click", ".delete-button", function () {
        const learningDataId = $(this).data("id");
        const learningDataName = $(this).closest(".middle-box").find(".middle-box-item").first().text().trim();

        console.log("削除対象のID:", learningDataId);
        console.log("削除対象の項目名:", learningDataName);

        if (!learningDataId) {
            alert("削除対象のIDが取得できませんでした。");
            return;
        }

        // DELETE リクエストを送信
        $.ajax({
            type: "DELETE",
            url: `/api/skills/delete/${learningDataId}`,
            success: function () {
                // 削除成功時のメッセージ表示
                const message = `${learningDataName}を削除しました！`;
                $("#modalMessage").text(message);
                $("#successModal").show();
            },
            error: function (xhr) {
                const errorMessage = xhr.responseText || "削除処理中にエラーが発生しました。";
                alert(errorMessage);
            }
        });
    });

    // 「閉じる」ボタンのクリックイベント
    $("#closeModalButton").on("click", function () {
        $("#successModal").hide(); 
        location.reload(); 
    });
});