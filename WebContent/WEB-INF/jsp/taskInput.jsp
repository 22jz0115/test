<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="assets/css/an/style.css">
    <title>タスク入力画面</title>
</head>
<body>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="assets/js/taskup/script.js"></script>
    <header>
        <a href="../ホーム画面/index.jsp" class="back1"><img src="assets/img/戻るボタン.png"></a>
        <h1>タスク入力</h1>
    </header>

    <div class="display">
        <form action="<%= request.getContextPath() %>/taskAddServlet" method="post">
            <!-- 日付表示 -->
            <div class="form-group">
                <label for="dateDisplay">日付</label>
                <div id="dateDisplay"><%= new java.util.Date() %></div>
            </div>

            <!-- 時間入力 -->
            <div class="form-group">
                <label for="appt-time">時間</label>
                <input id="appt-time" type="time" name="appt-time" value="13:30" />
            </div>

          <div class="radiobutton">
    <label class="switch_label9">
        <span id="outdoorText">屋内</span>
        <div class="switch">
            <input type="checkbox" id="switch" onclick="toggleCategory()" />
            <div class="circle"></div>
            <div class="base"></div>
        </div>
        <span id="indoorText" class="title">屋外</span>
    </label>
</div>


<!-- カテゴリーのプルダウン -->
<div id="categoryDiv" class="form-group">
    <label for="categorySelect">カテゴリーを選択:</label>
    <select id="categorySelect" name="category">
        <!-- カテゴリのオプションがJavaScriptで動的に設定されます -->
    </select>
</div>

<script>
    // 屋外と屋内のカテゴリの選択肢
    const outdoorCategories = [
        { value: 'gardening', text: 'ガーデニング' },
        { value: 'exercise', text: '運動' }
    ];

    const indoorCategories = [
        { value: 'cooking', text: '料理' },
        { value: 'cleaning', text: '掃除' }
    ];

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

    // 初期状態で屋内のカテゴリを表示
    window.onload = function() {
        populateCategories(indoorCategories);
    };
</script>

            <!-- タスク名の入力 -->
            <div class="form-group">
                <label for="taskName" class="taskname">タスク名:</label>
                <input type="text" id="taskName" name="taskName" required minlength="4" maxlength="8" />
            </div>
            
            

            <!-- メモの入力 -->
            <div class="form-group">
                <label for="story" class="memo-label">メモ</label>
                <textarea id="story" name="story" rows="5" cols="50">メモ</textarea>
            </div>

            <!-- 追加ボタン -->
            <div class="migi">
                <button id="touroku" type="submit" class="button3">追加</button>
            </div>
        </form>

        <!-- カテゴリー選択に応じた表示切り替えのスクリプト -->
        <script>
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
        </script>
    </div>
</body>
</html>