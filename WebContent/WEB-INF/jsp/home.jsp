<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="assets/css/style.css">
    <title>Weather Information</title>
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
        <a href="#"><img class="imgVector" src="assets/js/home/script.js" alt=""></a>
        <h1>ホーム</h1>
    </header>
    <div class="calendar-wrap">
        <table class="calendar"><caption></caption></table>
    </div>

    <h2 class="notice">お知らせ</h2>
    <div class="noticeMsg">
        <p>
            <%= request.getAttribute("weatherDescription") != null ? request.getAttribute("weatherDescription") : "天気情報が取得できませんでした。" %>
        </p>
    </div>

    <div class="flex-other">
        <div class="columntaskbt">
            <div class="shorttaskmaneger">
                 <ul>
       				 <c:forEach var="task" items="${taskList}">
                                <li>${task.formattedTime} - ${task.taskName}</li>
                     </c:forEach>
    			</ul>
            </div>
            <div class="buttons">
                <a class="starbutton" href="Collection"><img src="assets/img/unnamed.png" alt=""></a>
                <a class="bellbutton" href="Setting"><img src="assets/img/kkrn_icon_oshirase_4.png" alt=""></a>
                <a class="ratebutton" href="Result"><img src="assets/img/22462108.png" alt=""></a>
            </div>
        </div>
        <div class="other-button">
            <a class="link-button" href="Category"><p>カテゴリー</p></a>
            <a class="link-button" href="PresetList"><p>プリセット</p></a>
            <a class="link-button" href="LifeHack"><p>ライフハック</p></a>
            <a class="todaybutton" href=Task><p>今日のタスク</p></a>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="assets/js/home/script.js"></script>
</body>
</html>
