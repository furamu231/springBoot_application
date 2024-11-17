$(document).ready(function () {
    // 「削除」ボタンのクリックイベント（デリゲートを使用）
    $("body").on("click", ".delete-button", function () {
        const learningDataId = $(this).data("id");

        console.log("削除対象のID:", learningDataId);

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
                $("#modalMessage").text("学習データが削除されました！");
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
        $("#successModal").hide(); // モーダルを非表示にする
        location.reload(); // ページをリロードして変更を反映
    });
});