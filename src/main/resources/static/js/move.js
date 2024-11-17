$(document).ready(function () {
    // クラスセレクタで複数のボタンに対応
    $(".add-skill-button").on("click", function (event) {
        event.preventDefault(); // デフォルトのリンク動作を無効化

        // クリックされたボタンからデータ属性を取得
        const category = $(this).attr("data-category");
        const userId = $(this).attr("data-id");

        // デバッグ用に値を表示
        console.log(`Category: ${category}, UserId: ${userId}`);

        // 月のプルダウンの値を取得
        const registeredMonth = $("#month-dropdown").val();
        if (!registeredMonth) {
            alert("登録月が選択されていません。");
            return;
        }

        // 遷移先の URL を組み立てる
        const redirectUrl = `/users/addSkill/${category}/${userId}?month=${registeredMonth}`;

        // デバッグ用に URL を表示
        console.log(`Redirect URL: ${redirectUrl}`);

        // ページ遷移
        window.location.href = redirectUrl;
    });
});