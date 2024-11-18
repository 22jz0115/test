$(function() {
    // displayCalendar関数を使ってカレンダーを表示する
    function displayCalendar(year, month, day) {
        const head = '<tr><th class="sun">日</th><th>月</th><th>火</th>' +
                     '<th>水</th><th>木</th><th>金</th><th class="sat">土</th></tr>';
        $("table").prepend(head);
        $("caption").text(year + "年 " + (month + 1) + "月");

        let selectedDate = new Date(year, month, day);
        let dayOfWeek = selectedDate.getDay();
        let sunday = new Date(selectedDate);
        sunday.setDate(selectedDate.getDate() - dayOfWeek);

        let line = $('<tr>');

        for (let i = 0; i <= 6; i++) {
            let currentDay = new Date(sunday);
            currentDay.setDate(sunday.getDate() + i);
            let yobi = currentDay.getDay();
            let dayNumber = currentDay.getDate();
            let tdClass = (currentDay.getFullYear() === year && currentDay.getMonth() === month && dayNumber === day) ? 'toda' : 
                          (yobi === 0) ? 'sun' : (yobi === 6) ? 'sat' : '';

            line.append($('<td>', { text: dayNumber, class: tdClass }));
            if (yobi == 6) {
                $("table").append(line);
                line = $('<tr>');
            }
        }
        $("table").append(line);
    }

    // selectedDate (サーバーから送られた値) を使ってカレンダーを表示
    // selectedDateがない場合、今日の日付データを格納して表示
    let year, month, day;
    if (selectedDate && selectedDate !== "null") {
        const dateParts = selectedDate.split("-");
        year = parseInt(dateParts[0]);
        month = parseInt(dateParts[1]) - 1; // JavaScriptの月は0始まり
        day = parseInt(dateParts[2]);
    } else {
        const today = new Date();
        year = today.getFullYear();
        month = today.getMonth();
        day = today.getDate();
       ;
        
      console.log(selectedDate)
    }

    displayCalendar(year, month, day);

    // チェックボックスの進捗を更新する関数
    function updateCheckedCount() {
        const totalElements = document.querySelectorAll('.task').length;
        const checkedCount = document.querySelectorAll('.check:checked').length;
        let taskRate = Math.round((checkedCount / totalElements) * 100);
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
});



