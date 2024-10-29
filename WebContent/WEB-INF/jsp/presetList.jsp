<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <link rel="stylesheet" href="assets/css/an/style.css">
    <title>プリセット一覧画面</title>
    <link rel="shortcut icon" href="assets/img/icon-192x192.png" type="image/png">
    <link rel="manifest" href="manifest.json">
</head>
<body>
    <header>
        <a href="Home" class="back1"><img src="assets/img/戻るボタン.png" alt="戻るボタン"></a>
        <h1>プリセット一覧画面</h1>
    </header>

    <div class="box-1">
        <label for="switch" class="switch_label">  
            <p>屋外</p>
            <div class="switch">
                <input type="checkbox" id="switch" />
                <div class="circle"></div>
                <div class="base"></div>
            </div>
            <span class="title">屋内</span>
        </label>
    </div>
    
    <div class="categories">
        <p>月曜日</p>
        <p>火曜日</p>
    </div>

    <div class="delete">
        <form action="<%= request.getContextPath() %>/deletePresetServlet" method="post"> <!-- JSPサーブレットへの削除処理のURLを指定 -->
            <button type="submit" class="delete-button">
                <img src="assets/img/mingcute_delete-2-line.png" alt="削除アイコン"><br>
                削除
            </button>
        </form>
    </div>
</body>
</html>