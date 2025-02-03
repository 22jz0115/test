<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="assets/css/an/style.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="assets/js/nav/script.js"></script>
    <title>ライフハック追加画面</title>
    <link rel="shortcut icon" href="assets/img/icon-192x192.png" type="image/png">
    <link rel="manifest" href="manifest.json">
    <script>
	    if ('serviceWorker' in navigator) {
	        window.addEventListener('load', () => {
	            navigator.serviceWorker.register('/test/service-worker.js').then((registration) => {
	                console.log('Service Worker registered with scope:', registration.scope);
	            }).catch((error) => {
	                console.error('Service Worker registration failed:', error);
	            });
	        });
	    }
	</script>
</head>
<body>
    <header>
        <a href="LifeHack" class="back1"><img src="assets/img/戻るボタン.png" alt="戻るボタン"></a>
        <h1>ライフハック追加</h1>
        <div class="openbtn1"><span></span><span></span><span></span></div>
	    <nav id="g-nav">
	      <ul>
	      	<li> <a class="homebutton" href=Home>ホーム</a></li>
	      	<li> <a class="todaybutton" href=Task>今日のタスク</a></li>
	        <li> <a class="link-button" href="Category">タスク履歴</a></li>
	        <li> <a class="link-button" href="PresetList">プリセット履歴</a></li>
	        <li><a class="link-button" href="LifeHack">ライフハック</a></li>
	        <li> <a class="link-button" href="Result">統計</a></li>
	        <li><a class="link-button" href="Collection">コレクション</a></li>
	        <li> <a class="link-button" href="Setting">設定</a></li>        
	        <c:if test="${not empty loginUser }">
	       		<li><a class="rogout" href="Logout">ログアウト</a></li>
	       	</c:if> 
	      </ul>
	    </nav>
    </header>

    <div class="background-1">
        <form action="LifeHack" method="post" enctype="multipart/form-data">
            <div class="class1">
                <label for="name">タイトル</label>
                <input type="text" id="name" name="name" required minlength="1" maxlength="50" size="30">
            </div>
            
           

            <!-- 写真を挿入する -->
            <div class="atcontent">
                <div class="name">
                    <label for="comment">内容</label>
                </div>
                <textarea name="comment" id="comment" rows="13" cols="40" placeholder="内容を入力してください" required></textarea>
                
                <label for="example">写真を選択:</label>
                <input type="file" name="file" accept="image/*" multiple onchange="previewFiles(this);">
            </div>

            <div id="preview"></div>

            <div class="input">
                <button type="submit" class="submit-button">投稿する</button>
            </div>
        </form>
    </div>

    <script>
        // 画像プレビュー用のJavaScript
        function previewFiles(input) {
            const preview = document.getElementById('preview');
            preview.innerHTML = ''; // 既存のプレビューをクリア	

            const files = input.files;
            for (let i = 0; i < files.length; i++) {
                const file = files[i];
                const reader = new FileReader();
                
                reader.onload = function(e) {
                    const img = document.createElement('img');
                    img.src = e.target.result;
                    img.style.width = '100px'; // プレビュー画像のサイズを調整
                    img.style.margin = '5px';
                    preview.appendChild(img);
                };
                
                reader.readAsDataURL(file);
            }
        }
    </script>
</body>
</html>
