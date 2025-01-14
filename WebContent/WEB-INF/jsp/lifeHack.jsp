<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="assets/css/style.css">
    <title>ライフハック</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="assets/js/nav/script.js"></script>
	<script type="text/javascript" src="assets/js/lifeHack/script.js"></script>
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
    <div class="pagetop">Top</div>

    <header>
        <a href="Home"><img class="imgVector" src="assets/img/Vector.png" alt="Home"></a>
        <h1>ライフハック</h1>
        <div class="openbtn1"><span></span><span></span><span></span></div>
	    <nav id="g-nav">
	      <ul>
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

    <!-- 検索フォーム -->
    <form action="LifeHack" method="get" class="search-form-3">
        <label>
            <input type="text" name="keyword"  placeholder="キーワードを入力">
        </label>
        <button type="submit" aria-label="検索"></button>
    </form>

 <!-- ライフハック追加リンク -->
    <div class="bts">
        <a href="AddedLife"><p>ライフハック追加</p></a>
        <a href="LifeHackHistory"><p>投稿履歴</p></a> 
    </div>
    
    
	<div>
		<!-- ライフハックリストを表示 -->
	    <c:forEach var="life" items="${lifeList}">
	        <div class="lifehacks">
	            <div>
	                <h2>${life.account.name}さん</h2> <!-- ここを変更 -->
	                <h3>${life.title}</h3>
	                <p class="ligeptag">${life.content}</p>
	                <img src="${life.img}" alt="画像">
	            </div>
	        </div>    	
	    </c:forEach>
	</div>
    

   

    <script>
        // ページトップボタンのスムーズスクロールと表示切り替え
        const pagetopBtn = document.querySelector(".pagetop");

        // ページトップへ移動
        pagetopBtn.addEventListener("click", scrollToTop);
        function scrollToTop() {
            window.scroll({ top: 0, behavior: "smooth" });
        }

        // スクロール時のイベント
        window.addEventListener("scroll", handleScroll);
        function handleScroll() {
            if (window.pageYOffset > 100) {
                pagetopBtn.style.opacity = "1"; // ボタンを表示
            } else {
                pagetopBtn.style.opacity = "0"; // ボタンを非表示
            }
        }
    </script>

</body>
</html>
