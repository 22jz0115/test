$(function() {
    // カレンダーの生成
    const head = '<tr class="week"><th class="sun">日</th><th>月</th>' +
        '<th>火</th><th>水</th><th>木</th><th>金</th><th class="sat">土</th></tr>';
    $("table").prepend(head);

    let d = new Date(); // 現在の年月を基準にする
    let today = new Date();  // 今日の日付を取得

    // 月の初日に設定
    d.setDate(1);
    let currentMonth = d.getMonth(); // カレンダーを表示する月
    $("caption").prepend(d.getFullYear() + "年", d.getMonth() + 1 + "月"); // 年月の表示

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

        // クリックイベントで現在の月と日を明示的に指定
        line.append($('<td>', {
            class: tdClass,
            text: day,
            click: (function(day) {
                return function() {
                    sendDate(new Date(d.getFullYear(), d.getMonth(), day)); // クリックされた日付を送信
                };
            })(day) // 即時関数で現在の日を保持
        }));

        if (yobi == 6) {  // 土曜日で行を終了
            $("table").append(line);
            line = $('<tr>');
        }

        day++;
        d.setDate(day);  // 日付を次の日に設定

    } while (d.getMonth() === currentMonth);

    // 月の残りの日付を埋める
    if (yobi < 6) { // 最後の週の残りを埋める
        for (let y = yobi + 1; y <= 6; y++) {
            line.append($('<td>', { html: '' }));
        }
        $("table").append(line);
    }
});

// 日付を送信する関数
function sendDate(date) {
    // 日付を"YYYY-MM-DD"形式にフォーマット
    const formattedDate = date.getFullYear() + '-' + 
                          String(date.getMonth()).padStart(2, '0') + '-' + 
                          String(date.getDate()).padStart(2, '0');
    
    // URLに日付を追加
    const urlParams = new URLSearchParams();
    urlParams.append('date', formattedDate);
    window.location.href = 'Task?' + urlParams.toString();
}
