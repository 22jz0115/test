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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script type="text/javascript" src="jquery-3.7.1.min.js"></script>
<script type="text/javascript" src="assets/js/タスク入力画面/taskadd.js"></script>

    <header>
        <a href="Home" class="back1"><img src="assets/img/戻るボタン.png"></a><h1>タスク入力</h1>
    </header>

    <div class="display">
        <form action="TaskInput" method="post">

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

            <!-- 屋内/屋外の切り替えスイッチ -->
            <div class="radiobutton">
                <label class="switch_label9">
                    <span>屋外</span>
                    <div class="switch">
                        <input type="checkbox" id="switch" />
                        <div class="circle"></div>
                        <div class="base"></div>
                    </div>
                    <span class="title">屋内</span>
                </label>
            </div>

            <!-- カテゴリー選択ラジオボタン -->
            <div class="form-group radio-group">
                <label><input type="radio" name="categoryType" class="radio" value="existing"> 既存カテゴリー</label>
                <label><input type="radio" name="categoryType" class="radio" value="new"> 新規カテゴリー</label>
            </div>

            <!-- 既存カテゴリーのプルダウン -->
            <div id="existingCategoryDiv" class="form-group">
                <label for="existingCategory">既存カテゴリーを選択:</label>
                <select id="existingCategory" name="existingCategory">
                    <option value="category1">料理</option>
                    <option value="category2">掃除</option>
                </select>
            </div>

            <!-- 新規カテゴリーの入力 -->
            <div id="newCategoryDiv" class="form-group" style="display:none;">
                <label for="newCategory">新規カテゴリー名:</label>
                <input type="text" id="newCategory" name="newCategory" minlength="4" maxlength="8" />
            </div>

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