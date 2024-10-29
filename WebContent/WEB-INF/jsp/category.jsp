<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
 <link rel="stylesheet" href="assets/css/an/style.css">
    <title>カテゴリー画面</title>
    <style>
        .content {
            display: none; /* 初期状態で全て非表示 */
        }
        .active {
            display: block; /* アクティブなコンテンツのみ表示 */
        }
    </style>
</head>
<body>
    <header>
        <a href="Home" class="back1"><img src="assets/img/戻るボタン.png" alt="戻るボタン"></a>
        <h1>カテゴリー</h1>
    </header>
    
    <div class="box-1">
        <label for="switch" class="switch_label-4">  
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
        <div id="indoorContent" class="content active">
            <a href="../タスク履歴/index.html"><p>料理</p></a> 
            <p>掃除</p>
        </div>
        <div id="outdoorContent" class="content">
            <p>屋外でのアクティビティ</p>
            <p>ピクニック</p>
        </div>
    </div>
    
    <script>
        const switchInput = document.getElementById('switch');
        const indoorContent = document.getElementById('indoorContent');
        const outdoorContent = document.getElementById('outdoorContent');

        switchInput.addEventListener('change', function() {
            if (this.checked) {
                // 屋外が選択された場合
                indoorContent.classList.remove('active'); // 屋内を非表示
                outdoorContent.classList.add('active'); // 屋外を表示
            } else {
                // 屋内が選択された場合
                outdoorContent.classList.remove('active'); // 屋外を非表示
                indoorContent.classList.add('active'); // 屋内を表示
            }
        });
    </script>
    
    
    <div class="delete">
        <form action="<%= request.getContextPath() %>/deleteCategoryServlet" method="post"> <!-- JSPサーブレットへの削除処理のURLを指定 -->
            <button type="submit" class="delete-button">
                <img src="assets/img/mingcute_delete-2-line.png" alt="削除アイコン"><br>
                削除
            </button>
        </form>
    </div>
</body>
</html>
