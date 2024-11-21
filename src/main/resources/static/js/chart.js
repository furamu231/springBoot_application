document.addEventListener("DOMContentLoaded", () => {
    const ctx = document.getElementById('myChart');
    const userIdElement = document.querySelector("#user-id");

    const userId = userIdElement?.value || null;
    if (!userId) {
        console.error("ユーザーIDが取得できませんでした");
        return;
    }

    const categoryMap = {
        "Backend": "バックエンド",
        "Frontend": "フロントエンド",
        "Infra": "インフラ"
    };

    const monthMap = {
        "先々月": "2024-09",
        "先月": "2024-10",
        "今月": "2024-11"
    };

    const labels = Object.keys(monthMap); 

    fetch(`/api/skills/totalLearningTime?userId=${userId}`)
        .then(response => response.json())
        .then(data => {
            const backendData = [];
            const frontendData = [];
            const infraData = [];

            labels.forEach(label => {
                const month = monthMap[label]; 

                const backend = data.find(item => item.category === "Backend" && item.month === month);
                backendData.push(backend ? backend.total_time : 0);

                const frontend = data.find(item => item.category === "Frontend" && item.month === month);
                frontendData.push(frontend ? frontend.total_time : 0);

                const infra = data.find(item => item.category === "Infra" && item.month === month);
                infraData.push(infra ? infra.total_time : 0);
            });

            const maxDataValue = Math.max(
                ...backendData,
                ...frontendData,
                ...infraData
            );

            const calculateNiceMax = (value) => {
                const defaultMax = 100; 
                const finalValue = Math.max(value, defaultMax); 
                const magnitude = Math.pow(10, Math.floor(Math.log10(finalValue)));
                const normalizedValue = finalValue / magnitude;

                if (normalizedValue <= 1) return 1 * magnitude;
                if (normalizedValue <= 2) return 2 * magnitude;
                if (normalizedValue <= 5) return 5 * magnitude;
                return 10 * magnitude;
            };

            const niceMax = calculateNiceMax(maxDataValue);

            new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: labels, 
                    datasets: [
                        {
                            label: categoryMap["Backend"], 
                            data: backendData,
                            backgroundColor: 'rgba(255, 99, 132, 0.5)',
                            borderColor: 'rgba(255, 99, 132, 1)',
                            borderWidth: 1
                        },
                        {
                            label: categoryMap["Frontend"], 
                            data: frontendData,
                            backgroundColor: 'rgba(255, 159, 64, 0.5)',
                            borderColor: 'rgba(255, 159, 64, 1)',
                            borderWidth: 1
                        },
                        {
                            label: categoryMap["Infra"], 
                            data: infraData,
                            backgroundColor: 'rgba(255, 206, 86, 0.5)',
                            borderColor: 'rgba(255, 206, 86, 1)',
                            borderWidth: 1
                        }
                    ]
                },
                options: {
                    responsive: true,
                    scales: {
                        y: {
                            beginAtZero: true,
                            max: niceMax,
                            ticks: {
                                stepSize: niceMax / 10 
                            }
                        }
                    },
                    plugins: {
                        title: {
                            display: true, 
                            text: 'Chart.js Bar Chart', 
                            font: {
                                size: 14 
                            },
                            padding: {
                                bottom: 10 
                            }
                        },
                        legend: {
                            display: true,
                            position: 'top'
                        },
                    },
                    barPercentage: 0.8,
                    categoryPercentage: 0.7
                }
            });
        })
        .catch(error => {
            console.error("データの取得に失敗しました:", error);
        });
});