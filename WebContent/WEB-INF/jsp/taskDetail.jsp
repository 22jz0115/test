<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>タスク詳細画面</title>
</head>
<body>
 <header>
        <a href="#"><img class="imgVector" src="../img/Vector.png" alt=""></a>
        <h1>タスク詳細</h1>
    </header>

    <div class="display">
        <form action="" method="post">

            <!-- 日付表示 -->
            <div class="form-group">
                <label for="dateDisplay">日付</label>
                <div id="dateDisplay"></div>
            </div>

            <!-- 時間入力 -->
            <div class="form-group">
                <label for="appt-time">時間</label>
                <input id="appt-time" type="time" name="appt-time" value="13:30" />
            </div>

            <div class="form-group">
                <label for="taskName" class="taskname">カテゴリー名:</label>
            </div>

    
            <!-- タスク名の入力 -->
            <div class="form-group">
                <label for="taskName" class="taskname">タスク名:</label>
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

</body>
</html>