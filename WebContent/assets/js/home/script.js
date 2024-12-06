$(function() {
    let currentMonth;
    let currentYear;

    const ham = document.querySelector('.openbtn1');
    const nav = document.querySelector('#g-nav');

    ham.addEventListener('click', function(){
        ham.classList.toggle('active');
        nav.classList.toggle('panelactive');
    });

    nav.addEventListener('click', function(){
        ham.classList.remove('active');
        nav.classList.remove('panelactive');
    });

    // 初期表示
    let today = new Date();
    currentMonth = today.getMonth();
    currentYear = today.getFullYear();

    // 月の初日に設定
    generateCalendar(currentYear, currentMonth);

    // 前月ボタン
    $('#prev-month').click(function() {
        currentMonth--;
        if (currentMonth < 0) {
            currentMonth = 11;
            currentYear--;
        }
        generateCalendar(currentYear, currentMonth);
    });

    // 次月ボタン
    $('#next-month').click(function() {
        currentMonth++;
        if (currentMonth > 11) {
            currentMonth = 0;
            currentYear++;
        }
        generateCalendar(currentYear, currentMonth);
    });
});

// カレンダーを生成
// カレンダーを生成
function generateCalendar(year, month) {
    const head = '<tr class="week"><th class="sun">日</th><th>月</th>' +
        '<th>火</th><th>水</th><th>木</th><th>金</th><th class="sat">土</th></tr>';
    $("table").empty().prepend(head);

    // 年月の表示を修正
    const monthLabel = year + "年 " + (month + 1) + "月";
    console.log("表示される年月: " + monthLabel);

    // caption の内容を更新
    $("#yearMonth").text(monthLabel); // 月の表示を更新

    let d = new Date(year, month, 1); // 指定月の初日を設定
    let today = new Date();  // 今日の日付を取得

    let yobi1 = d.getDay();  // 月初めの日の曜日
    let line = $('<tr>');

    // 1日が始まるまでの空白を埋める
    for (let x = 0; x < yobi1; x++) {
        line.append($('<td>', { html: '' }));
    }

    let day = 1; // 日付カウント
    let yobi;

    // 日付のセルを作成
    do {
        yobi = d.getDay();
        let tdClass = (today.getFullYear() === d.getFullYear() && today.getMonth() === d.getMonth() && today.getDate() === day) ? 'today' : '';

        // 土曜日と日曜日の背景色を設定
        if (yobi === 0) {
            tdClass += ' sunday'; // 日曜日
        } else if (yobi === 6) {
            tdClass += ' saturday'; // 土曜日
        }

        // クリックイベントで現在の月と日を明示的に指定
        line.append($('<td>', {
            class: tdClass,
            text: day,
            click: (function(year, month, day) {
                return function() {
                    sendDate(new Date(year, month, day)); // 正しい年月日を指定
                };
            })(d.getFullYear(), d.getMonth(), day) // 即時関数で現在の年月日を保持
        }));

        if (yobi == 6) {  // 土曜日で行を終了
            $("table").append(line);
            line = $('<tr>');
        }

        day++;
        d.setDate(day);  // 日付を次の日に設定

    } while (d.getMonth() === month);

    // 月の残りの日付を埋める
    if (yobi < 6) { // 最後の週の残りを埋める
        for (let y = yobi + 1; y <= 6; y++) {
            line.append($('<td>', { html: '' }));
        }
        $("table").append(line);
    }
}



// 日付を送信
function sendDate(date) {
    // 日付を"YYYY-MM-DD"形式にフォーマット
    const formattedDate = date.getFullYear() + '-' + 
                          String(date.getMonth() + 1).padStart(2, '0') + '-' + 
                          String(date.getDate()).padStart(2, '0');
    console.log("HomeJavaScriptの日付: " + formattedDate);

    // URLに日付を追加
    const urlParams = new URLSearchParams();
    urlParams.append('date', formattedDate);
    window.location.href = 'Task?' + urlParams.toString();
}
