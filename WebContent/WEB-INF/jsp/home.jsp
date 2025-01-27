<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="assets/css/style.css">
    <title>ホーム</title>
    <link rel="shortcut icon" href="assets/img/icon-192x192.png" type="image/png">
    <link rel="manifest" href="manifest.json">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
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

	    const Location = '${Location}';
	</script>
	<script>
        const taskDates = ${taskDatesJson};
    </script>
	
</head>
<body>
    <header>
        <h1>ホーム</h1>
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
    
    
    <div class="calendar-wrap">
        <div id="month-buttons">
            <button id="prev-month"><img alt="前月" src="assets/img/left_01.png"></button>
            <h2 id="yearMonth"></h2>
            <button id="next-month"><img alt="次月" src="assets/img/right_01.png"></button>
        </div>

        
       	<table class="calendar">   	
        </table>
    
    </div>

    <h2 class="notice">お知らせ</h2>
    <div class="noticeMsg">
        <p id="weather"></p>
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
        </div>
    </div>
    
    <div class="bts">
       <a  href="TaskInput"><p>タスク追加</p></a>
       <a  href="Preset"><p>プリセット追加</p></a> 
    </div>
    <script type="text/javascript" src="assets/js/home/script.js"></script>
    <script src="assets/js/geolocation/geocode.js"></script>
    
</body>
</html>
