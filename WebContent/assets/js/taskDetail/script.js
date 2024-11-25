
function dateDisplay() {
	
	
	
	// チェック状態を task.checkTask に基づいて設定
    const checkTaskInput = document.getElementById('checkTask'); // IDがcheckTaskに変更された
    if (checkTaskInput) {
        const checkTaskValue = checkTaskInput.dataset.checkTask; // data-check-task 属性から値を取得
        console.log(checkTaskValue);

        // 値が '1'（屋外）の場合チェックを有効化、それ以外（'0' 屋内）はチェックを無効化
        checkTaskInput.checked = checkTaskValue === '1';
        
        
      
    }
    
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
        const radios = document.querySelectorAll('input[name="categoryType"]');
            const existingCategoryDiv = document.getElementById('existingCategoryDiv');
            const newCategoryDiv = document.getElementById('newCategoryDiv');

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
    
    
}

// ページが読み込まれたときにdateDisplay関数を実行
window.onload = dateDisplay;
