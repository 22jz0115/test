$(function () {

    // サーバーから渡されたデータを使用して円グラフを更新
    const pieChartElement1 = document.getElementById('pieChart');
    pieChartElement1.style.backgroundImage = `
        radial-gradient(#fff 55%, transparent 55%), 
        conic-gradient(#2589d0 ${percentageFromDatabase1}%, #f2f2f2 ${percentageFromDatabase1}% 100%)
    `;
    pieChartElement1.textContent = `${percentageFromDatabase1}%`;

    const pieChartElement2 = document.getElementById('pieChart2');
    pieChartElement2.style.backgroundImage = `
        radial-gradient(#fff 55%, transparent 55%), 
        conic-gradient(#2589d0 ${percentageFromDatabase2}%, #f2f2f2 ${percentageFromDatabase2}% 100%)
    `;
    pieChartElement2.textContent = `${percentageFromDatabase2}%`;

    const pieChartElement3 = document.getElementById('pieChart3');
    pieChartElement3.style.backgroundImage = `
        radial-gradient(#fff 55%, transparent 55%), 
        conic-gradient(#2589d0 ${percentageFromDatabase3}%, #f2f2f2 ${percentageFromDatabase3}% 100%)
    `;
    pieChartElement3.textContent = `${percentageFromDatabase3}%`;
});
