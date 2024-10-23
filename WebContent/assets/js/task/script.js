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
    if (selectedDate) {
        const dateParts = selectedDate.split("-");
        const year = parseInt(dateParts[0]);
        const month = parseInt(dateParts[1]) - 1; // JavaScriptの月は0始まり
        const day = parseInt(dateParts[2]);

        displayCalendar(year, month, day);
    }

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

    updateCheckedCount(); // 初期状態の進捗を表示
});
