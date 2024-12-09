<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="assets/css/style.css">
    <title>Document</title>
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
</header>

<!-- 検索フォーム -->
<form action="LifeHack" method="get" class="search-form-3">
    <label>
        <input type="text" name="keyword"  placeholder="キーワードを入力">
    </label>
    <button type="submit" aria-label="検索"></button>
</form>

<!-- ライフハック追加リンク -->
<p class="lifehackAdd"><a href="AddedLife">ライフハック追加</a></p>

<!-- ライフハックリストを表示 -->
<c:forEach var="life" items="${lifeList}">
    <div class="lifehacks">
        <div>
            <h2>${life.account.name}さん</h2> <!-- ここを変更 -->
            <h3>${life.title}</h3>
            <p>${life.content}</p>
            <img src="${life.img}" alt="画像">
        </div>
    </div>    	
</c:forEach>

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