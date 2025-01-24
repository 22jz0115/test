<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="assets/css/an/style.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="assets/js/home/script.js"></script>
    <title>ライフハック変更</title>
    <link rel="shortcut icon" href="assets/img/icon-192x192.png" type="image/png">
    <link rel="manifest" href="manifest.json">
    <script type="text/javascript" src="assets/js/nav/script.js"></script>
   
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
        <h1>ライフハック変更</h1>
        <div class="openbtn1"><span></span><span></span><span></span></div>
	    <nav id="g-nav">
	      <ul>
	      	<li> <a class="homebutton" href=Home>ホーム</a></li>
	      	<li> <a class="todaybutton" href=Task>今日のタスク</a></li>
	        <li> <a class="link-button" href="Category">タスク履歴</a></li>
	        <li> <a class="link-button" href="PresetList">プリセット</a></li>
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
    
    <form id="lifeChangeForm" action="LifeChange" method="POST" style="margin: 0;" enctype="multipart/form-data">
	   <div class="background-1">
	       <input type="hidden" name="lifeId" value="${life.id}">
	       
	       <div class="class1">
	           <label for="title">タイトル</label>
	           <input type="text" id="title" name="title" required minlength="1" maxlength="50" size="30" value="${life.title}">
	       </div>
	
	       <div class="atcontent">
	           <label for="comment">内容</label>
	           <textarea name="comment" id="comment" rows="13" cols="40" required>${life.content}</textarea>
	       </div>
	
	       <c:if test="${not empty life.img}">
	           <!-- 現在の写真プレビューと削除オプション -->
	           <div id="currentPhoto">
	               <img id="previewImage" src="${life.img}" alt="現在の画像" style="width: 150px;">
	               <button type="button" id="deleteImageButton">写真を削除</button>
	           </div>
	           <!-- 画像がある場合、新しい写真選択を無効化 -->
	           <input type="file" id="file" name="file" accept="image/*" multiple disabled>
	           <p>写真を削除すると新しい写真を選択できます。</p>
	       </c:if>
	       
	       <c:if test="${empty life.img}">
	           <!-- 画像がない場合、新しい写真を選択可能 -->
	           <label for="file">新しい写真を選択:</label>
	           <input type="file" id="file2" name="file" accept="image/*" multiple onchange="previewFiles(this);">
	       </c:if>
	       
   	       <div id="preview"></div>
	       <!-- 画像削除用の隠しフィールド -->
	        <input type="hidden" name="deleteImage" id="deleteImage" value="false">
	        
	    </div>
	    
	    <div class="button-container">
	        <button type="submit" class="submit-button">変更を保存</button>
	    </div>
	</form>

    
    <form action="LifeHackHistory" method="POST">
        <div class="button-container">
            <input type="hidden" name="lifeId" value="${life.id}">
            <button type="submit" class="submit-button">削除</button>
        </div>
    </form>

     <script type="text/javascript" src="assets/js/lifeChange/script.js"></script>
</body>
</html>
