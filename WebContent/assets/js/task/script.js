$(function () {
    // 初期化: JSPから受け取った `selectedDate`
    generateWeekCalendar(selectedDate);

    // 前週・次週ボタンのイベントハンドラー
    $("#prev-month").click(function () {
        changeWeek(-1); // 前週へ
    });

    $("#next-month").click(function () {
        changeWeek(1); // 次週へ
    });
});

// 週カレンダー生成
function generateWeekCalendar(selectedDate) {
    const startOfWeek = getStartOfWeek(selectedDate);
    const endOfWeek = new Date(startOfWeek);
    endOfWeek.setDate(startOfWeek.getDate() + 6); // 週の終了日

    let displayYear = startOfWeek.getFullYear();
    let displayMonth = startOfWeek.getMonth();

    // 月をまたぐ場合、次月の第1週と表示
    if (startOfWeek.getMonth() !== endOfWeek.getMonth() && selectedDate.getMonth() === endOfWeek.getMonth()) {
        displayYear = endOfWeek.getFullYear();
        displayMonth = endOfWeek.getMonth();
    }

    // 週の範囲を表示（例： 2024年 12月 1日～7日）
    const weekRangeLabel = `${startOfWeek.getMonth() + 1}月 ${startOfWeek.getDate()}日～${endOfWeek.getDate()}日`;
    $("#yearMonth").text(weekRangeLabel); // 週の範囲を更新

    const head = `<tr class="week">
        <th class="sun">日</th><th>月</th><th>火</th>
        <th>水</th><th>木</th><th>金</th><th class="sat">土</th>
    </tr>`;
    $("table").empty().prepend(head);

    // 1週間分の日付を生成
    let line = $('<tr>');
    for (let i = 0; i < 7; i++) {
        let currentDay = new Date(startOfWeek);
        currentDay.setDate(startOfWeek.getDate() + i);

        let dayOfWeek = currentDay.getDay();
        let currentDate = currentDay.getDate();

        let tdClass = '';
        if (
            currentDay.getFullYear() === selectedDate.getFullYear() &&
            currentDay.getMonth() === selectedDate.getMonth() &&
            currentDate === selectedDate.getDate()
        ) {
            tdClass = 'today'; // 今日のタスク
        } else if (dayOfWeek === 0) {
            tdClass = 'sunday'; // 日曜日
        } else if (dayOfWeek === 6) {
            tdClass = 'saturday'; // 土曜日
        }

        line.append($('<td>', {
            class: tdClass,
            text: currentDate,
            click: (function (currentYear, currentMonth, day) {
                return function () {
                    sendDate(new Date(currentYear, currentMonth, day));
                    generateWeekCalendar(new Date(currentYear, currentMonth, day)); // 更新
                };
            })(currentDay.getFullYear(), currentDay.getMonth(), currentDate)
        }));
    }
    $("table").append(line);
}

// 日付を送信する
function sendDate(date) {
    const formattedDate = `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`;
    console.log("送信する日付: " + formattedDate);

    const urlParams = new URLSearchParams();
    urlParams.append('date', formattedDate);
    window.location.href = 'Task?' + urlParams.toString();
}

// 週の移動
function changeWeek(weekDiff) {
    selectedDate.setDate(selectedDate.getDate() + weekDiff * 7);
    generateWeekCalendar(selectedDate);
}

// 選択した日付が属する週の初日（日曜日）を取得
function getStartOfWeek(date) {
    const dayOfWeek = date.getDay();
    const diff = date.getDate() - dayOfWeek; // 現在の日付から日曜日の日付を求める
    const startOfWeek = new Date(date);
    startOfWeek.setDate(diff);
    return startOfWeek;
}


// 日付を送信する
function sendDate(date) {
    const formattedDate = `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, '0')}-${String(date.getDate()).padStart(2, '0')}`;
    console.log("送信する日付: " + formattedDate);

    const urlParams = new URLSearchParams();
    urlParams.append('date', formattedDate);
    window.location.href = 'Task?' + urlParams.toString();
}

// 週の移動
function changeWeek(weekDiff) {
    selectedDate.setDate(selectedDate.getDate() + weekDiff * 7);
    generateWeekCalendar(selectedDate);
}

// 選択した日付が属する週の初日（日曜日）を取得
function getStartOfWeek(date) {
    const dayOfWeek = date.getDay();
    const diff = date.getDate() - dayOfWeek; // 現在の日付から日曜日の日付を求める
    const startOfWeek = new Date(date);
    startOfWeek.setDate(diff);
    return startOfWeek;
}





   function updateCheckedCount() {
    const totalElements = document.querySelectorAll('.task').length;
    const checkedCount = document.querySelectorAll('.check:checked').length;
    
    console.log('totalElements =' + totalElements);

    let taskRate = totalElements > 0 ? Math.round((checkedCount / totalElements) * 100) : 0;

    document.getElementById('countDisplay').textContent = taskRate + "％達成";
    document.querySelector('.progress-bar').style.width = taskRate + '%';
}


    // チェックボックスにイベントリスナーを追加
    document.querySelectorAll('.check').forEach(checkbox => {
        checkbox.addEventListener('change', updateCheckedCount);
    });
    
    document.querySelectorAll('.check').forEach(checkbox => {
    checkbox.addEventListener('change', function () {
        updateCheckedCount();
        const taskId = this.closest('.task').getAttribute('data-task-id'); // タスクIDを取得
        const isChecked = this.checked ? 1 : 0; // チェック状態を取得 (1: 完了, 0: 未完了)
        // サーバーへ更新リクエストを送信
        fetch('Task', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: `taskId=${taskId}&check=${isChecked}`
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('サーバーエラーが発生しました');
            }
            return response.text();
        })
        .then(result => {
            console.log('サーバー応答: ', result);
        })
        .catch(error => {
            console.error('エラー: ', error);
        });
    });
});

    updateCheckedCount(); // 初期状態の進捗を表示

