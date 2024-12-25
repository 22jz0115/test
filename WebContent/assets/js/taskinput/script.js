function dateDisplay() {
    // 今日の日付データを変数に格納
    var today = new Date();

    // 年・月・日・曜日を取得
    var year = today.getFullYear();
    var month = today.getMonth() + 1; // 月は0から始まるため +1
    var day = today.getDate();
    var week = today.getDay(); // 0-6の値

    // 曜日を日本語で表現
    var week_ja = ["日", "月", "火", "水", "木", "金", "土"];

    // 年・月・日・曜日を表示する文字列を作成
    var dateString = year + "/" + month + "/" + day + " (" + week_ja[week] + "曜日)";
    document.getElementById("dateDisplay").innerText = dateString;

    const radios = document.querySelectorAll('input[name="categoryType"]');
    const existingCategoryDiv = document.getElementById('existingCategoryDiv');
    const newCategoryDiv = document.getElementById('newCategoryDiv');

    // ラジオボタンの変更イベントにリスナーを追加
    radios.forEach(radio => {
        radio.addEventListener('change', () => {
            if (radio.value === 'existing') {
                existingCategoryDiv.style.display = 'block';
                newCategoryDiv.style.display = 'none';
            } else {
                existingCategoryDiv.style.display = 'none';
                newCategoryDiv.style.display = 'block';
            }
        });
    });

    function populateCategories(categories) {
        const categorySelect = document.getElementById('categorySelect');
        categorySelect.innerHTML = ''; // 既存のオプションをクリア

        categories.forEach(category => {
            const option = document.createElement('option');
            option.value = category.value;
            option.textContent = category.text;
            categorySelect.appendChild(option);
        });
    }

    function toggleCategory() {
        const isOutdoor = document.getElementById('switch').checked;
        populateCategories(isOutdoor ? outdoorCategories : indoorCategories);
    }

    // 初期状態の設定
    populateCategories(indoorCategories);
    document.getElementById('switch').addEventListener('change', toggleCategory);

    // --- 新しい変更をここに追加 ---
    const form = document.querySelector("form");
    form.addEventListener("submit", function (e) {
        const submitButton = document.getElementById("touroku");
        if (submitButton.disabled) {
            e.preventDefault(); // 二重送信を防止
        } else {
            submitButton.disabled = true; // ボタンを無効化
            submitButton.innerText = "処理中..."; // フィードバックを表示
        }
    });
    // --- 追加ここまで ---
}

// ページが読み込まれたときにdateDisplay関数を実行
window.onload = dateDisplay;
