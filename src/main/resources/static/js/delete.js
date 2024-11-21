$(document).ready(function () {
    $("body").on("click", ".delete-button", function () {
        const learningDataId = $(this).data("id");
        const learningDataName = $(this).closest(".middle-box").find(".middle-box-item").first().text().trim();

        console.log("削除対象のID:", learningDataId);
        console.log("削除対象の項目名:", learningDataName);

        if (!learningDataId) {
            alert("削除対象のID取得失敗");
            return;
        }

        $.ajax({
            type: "DELETE",
            url: `/api/skills/delete/${learningDataId}`,
            success: function () {
                const message = `${learningDataName}を削除しました！`;
                $("#modalMessage").text(message);
                $("#successModal").show();
            },
            error: function (xhr) {
                const errorMessage = xhr.responseText || "削除処理中にエラー";
                alert(errorMessage);
            }
        });
    });

    $("#closeModalButton").on("click", function () {
        $("#successModal").hide(); 
        location.reload(); 
    });
});