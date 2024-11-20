$(document).ready(function () {
    $(".add-skill-button").on("click", function (event) {
        event.preventDefault(); 


        const category = $(this).attr("data-category");
        const userId = $(this).attr("data-id");

        console.log(`Category: ${category}, UserId: ${userId}`);

        const registeredMonth = $("#month-dropdown").val();
        if (!registeredMonth) {
            alert("登録月が選択されていません。");
            return;
        }

        const redirectUrl = `/users/addSkill/${category}/${userId}?month=${registeredMonth}`;

        console.log(`Redirect URL: ${redirectUrl}`);

        window.location.href = redirectUrl;
    });
});