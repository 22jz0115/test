<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="assets/css/style.css">
    <title>投稿履歴</title>
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
    <h1>投稿履歴</h1>
</header>

<!-- ライフハック追加リンク -->
<p class="lifehackAdd"><a href="AddedLife">ライフハック追加</a></p>

<!-- ライフハックリストを表示 -->
<c:forEach var="life" items="${lifeList}">
    <div class="lifehacks">
        <div>
            <div id="lifeHisha">
                <h2>${life.account.name}さん</h2> <!-- ここを変更 -->
                <!-- フォームで隠しIDを送信 -->
                <form action="LifeChange" method="Get" style="margin: 0;">
                    <input type="hidden" name="lifeId" value="${life.id}">
                    <button type="submit" class="lifelink" style="background: none; border: none; padding: 0;">
                        <img id="hensyuuImg" alt="編集" src="assets/img/hensyuu.png">
                    </button>
                </form>
            </div>
            <h3>${life.title}</h3>
            <p style="white-space: pre-wrap;">${life.content}</p> <!-- 改行を反映 -->
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
