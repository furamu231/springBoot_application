document.addEventListener("DOMContentLoaded", function () {
    const middleBoxItems = document.querySelectorAll(".middle-box-item");
    
    middleBoxItems.forEach(item => {
        const maxChars = 35; 
        const text = item.textContent.trim(); 
        if (text.length > maxChars) {
            item.textContent = text.slice(0, maxChars) + "…"; 
        }
    });
});

// 文字数カウント

