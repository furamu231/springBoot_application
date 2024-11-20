$(document).ready(function () {
    const userId = $("#userId").val(); 
    if (userId) {
        const userPortfolioLink = `/users/success/${userId}`;
        $("#userPortfolioLink").attr("href", userPortfolioLink); 
    }
});