<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="assets/css/an/style.css">
    <title>設定画面</title>
    <style>
        body {
            background-color: <%= request.getParameter("bgcolor") != null ? request.getParameter("bgcolor") : "#ffffff" %>;
            color: <%= request.getParameter("textcolor") != null ? request.getParameter("textcolor") : "#000000" %>;
        }
    </style>
</head>
<body>
    <header>
        <a href="../ホーム画面/index.html" class="back1"><img src="assets/img/戻るボタン.png" alt="戻る"></a>
        <h1>設定</h1>
    </header>

    <div class="switch">   
        <div class="notification">
            <img src="assets/img/通知アイコン.png" alt="通知アイコン">
            <p>通知</p>
            <label for="switch1" class="switch_label">  
                <p>OFF</p>
                <div class="switch">
                    <input type="checkbox" id="switch1" />
                    <div class="circle"></div>
                    <div class="base"></div>
                </div>
                <span class="title">ON</span>
            </label>
        </div>

        <div class="bluetooth">
            <img src="assets/img/bluethooth.png" alt="bluetoothアイコン">
            <p>Bluetooth</p>
            <label for="switch2" class="switch_label">  
                <p>OFF</p>
                <div class="switch">
                    <input type="checkbox" id="switch2" />
                    <div class="circle"></div>
                    <div class="base"></div>
                </div>
                <span class="title">ON</span>
            </label>
        </div>
    </div>
    <p class="setting">背景設定</p>
<!-- 背景色変更のためのフォーム -->
<div class="selection">
    <input id="bgcolor" type="color" />
    <button id="bgColorBtn" type="button" class="button2">決定</button>
</div>

<p class="setting">文字色変更</p>
<!-- 文字色変更のためのフォーム -->
<div class="selection">
    <input id="textcolor" type="color" />
    <button id="textColorBtn" type="button" class="button2">決定</button>
</div>

    
    
    <script>
    // 背景色を変更する関数
    document.getElementById('bgColorBtn').addEventListener('click', function() {
        var bgColor = document.getElementById('bgcolor').value;
        document.body.style.backgroundColor = bgColor; // 背景色を変更
    });

    // 文字色を変更する関数
    document.getElementById('textColorBtn').addEventListener('click', function() {
        var textColor = document.getElementById('textcolor').value;
        document.body.style.color = textColor; // 文字色を変更
    });
</script>

    <p class="setting">ログアウト</p>
    <div class="button-1">
        <form action="../ログイン画面/index.html" method="post">
            <button type="submit" class="logout-button">ログアウト</button>
        </form>
    </div>

</body>
</html>