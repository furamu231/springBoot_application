document.addEventListener("DOMContentLoaded", () => {
    const ctx = document.getElementById('myChart');
    const userIdElement = document.querySelector("#user-id");

    // ユーザーIDの取得
    const userId = userIdElement?.value || null;
    if (!userId) {
        console.error("ユーザーIDが取得できませんでした");
        return;
    }

    // API からデータ取得
    fetch(`/api/skills/totalLearningTime?userId=${userId}`)
        .then(response => response.json())
        .then(data => {
            const labels = ["2024-09", "2024-10", "2024-11"];
            const backendData = [];
            const frontendData = [];
            const infraData = [];

            labels.forEach(month => {
                const backend = data.find(item => item.category === "Backend" && item.month === month);
                backendData.push(backend ? backend.total_time : 0);

                const frontend = data.find(item => item.category === "Frontend" && item.month === month);
                frontendData.push(frontend ? frontend.total_time : 0);

                const infra = data.find(item => item.category === "Infra" && item.month === month);
                infraData.push(infra ? infra.total_time : 0);
            });

            // チャート描画
            new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [
                        {
                            label: 'Backend',
                            data: backendData,
                            backgroundColor: 'rgba(255, 99, 132, 0.5)',
                            borderColor: 'rgba(255, 99, 132, 1)',
                            borderWidth: 1
                        },
                        {
                            label: 'Frontend',
                            data: frontendData,
                            backgroundColor: 'rgba(255, 159, 64, 0.5)',
                            borderColor: 'rgba(255, 159, 64, 1)',
                            borderWidth: 1
                        },
                        {
                            label: 'Infra',
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
                            max: 100,
                            ticks: {
                                stepSize: 10
                            },
                        }
                    },
                    plugins: {
                        legend: {
                            display: true,
                            position: 'top'
                        }
                    },
                    barPercentage: 0.8,
                    categoryPercentage: 0.7
                }
            });
        })
        .catch(error => {
            
        });
});