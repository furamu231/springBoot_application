$(".save-button").on("click", function () {
    const learningDataId = $(this).data("id");
    const newLearningTime = $(this).closest(".middle-box-item").prev().find(".time-dropdown").val();

    console.log("learningDataId:", learningDataId);
    console.log("newLearningTime:", newLearningTime);

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
            const message = `学習時間が${newLearningTime}分に更新されました！`;
            $("#modalMessage").text(message);
            $("#successModal").show();
        },
        error: function (xhr) {
            const errorMessage = xhr.responseText || "更新処理中にエラーが発生しました。";
            showError(errorMessage);
        }
    });

    $("#closeModal").on("click", function () {
        $("#successModal").hide(); // モーダルを非表示にする
        location.reload(); // ページをリロードして変更を反映
    });
});