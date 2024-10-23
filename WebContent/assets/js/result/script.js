$(function(){

    let d = new Date();
    let year = d.getFullYear();
    let month = d.getMonth();
    const cmp = document.getElementById('cmpmonth')
    cmp.prepend(year + "年 " + (month + 1) + "月");


     
 // 例として、データベースから取得した％データをJavaScriptでシミュレート

    const percentageFromDatabase = 70;  // 75%のデータを例として使用

    // 動的に円グラフのスタイルを変更
    const pieChartElement = document.getElementById('pieChart');
    pieChartElement.style.backgroundImage = `
        radial-gradient(#fff 55%, transparent 55%), 
        conic-gradient(#2589d0 ${percentageFromDatabase}%, #f2f2f2 ${percentageFromDatabase}% 100%)
    `;
   
    // グラフ内のテキストも更新
    pieChartElement.textContent = `${percentageFromDatabase}%`;

    const percentageFromDatabase2 = 70;  // 75%のデータを例として使用

    // 動的に円グラフのスタイルを変更
    const pieChartElemen2t = document.getElementById('pieChart2');
    pieChartElemen2t.style.backgroundImage = `
        radial-gradient(#fff 55%, transparent 55%), 
        conic-gradient(#2589d0 ${percentageFromDatabase2}%, #f2f2f2 ${percentageFromDatabase2}% 100%)
    `;
   
    // グラフ内のテキストも更新
    pieChartElemen2t.textContent = `${percentageFromDatabase2}%`;

    const percentageFromDatabase3 = 40;  // 75%のデータを例として使用

    // 動的に円グラフのスタイルを変更
    const pieChartElemen3t = document.getElementById('pieChart3');
    pieChartElemen3t.style.backgroundImage = `
        radial-gradient(#fff 55%, transparent 55%), 
        conic-gradient(#2589d0 ${percentageFromDatabase3}%, #f2f2f2 ${percentageFromDatabase3}% 100%)
    `;
   
    // グラフ内のテキストも更新
    pieChartElemen3t.textContent = `${percentageFromDatabase3}%`;
});


