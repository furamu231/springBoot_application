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

    const categoryMap = {
        "Backend": "バックエンド",
        "Frontend": "フロントエンド",
        "Infra": "インフラ"
    };

    if (categoryMap[categoryName]) {
        categoryName = categoryMap[categoryName];
        $("#categoryName").val(categoryName); 
    }

    $("#registerButton").on("click", function () {
        const learningDataName = $("#learningDataName").val();
        const learningTime = $("#learningTime").val();
        const errors = [];

        if (!learningDataName) {
            errors.push("項目名は必ず入力してください");
        } else if (learningDataName.length > 50) {
            errors.push("項目名は50文字以内で入力してください");
        }

        if (!learningTime) {
            errors.push("学習時間は必ず入力してください");
        } else if (isNaN(learningTime) || Number(learningTime) <= 0) {
            errors.push("学習時間は1以上の数字で入力してください");
        }

        if (!registeredMonth) {
            errors.push("登録月が選択されていません");
        }

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

        if (errors.length > 0) {
            showErrors(errors);
            return;
        }

        const formData = {
            userId: userId,
            categoryId: categoryId,
            learningDataName: learningDataName,
            learningTime: learningTime,
            registeredMonth: registeredMonth
        };

        console.log("送信するデータ:", formData);

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