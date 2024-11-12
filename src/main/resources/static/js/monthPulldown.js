const pulldownContainer = document.querySelector('.pulldown-container');
const pulldownMenu = document.querySelector('.pulldown-menu');

// プルダウンメニューの表示・非表示を切り替える
pulldownContainer.addEventListener('click', () => {
    pulldownMenu.style.display = pulldownMenu.style.display === 'none' || pulldownMenu.style.display === '' ? 'block' : 'none';
});

// メニューの項目をクリックしたときに選択したテキストを表示
pulldownMenu.addEventListener('click', (event) => {
    if (event.target.tagName === 'LI') {
        const selectedText = event.target.textContent;
        document.querySelector('.text-box').textContent = selectedText;
        pulldownMenu.style.display = 'none';
    }
});