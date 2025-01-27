<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="assets/css/style.css">
    <title>プリセット詳細</title>
    <link rel="shortcut icon" href="assets/img/icon-192x192.png" type="image/png">
    <link rel="manifest" href="manifest.json">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
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

	    document.addEventListener("DOMContentLoaded", function() {
            const deleteForm = document.querySelector('form[action="PresetDetail"]');
            deleteForm.addEventListener('submit', function(event) {
                event.preventDefault(); // フォーム送信を一時停止

                if (confirm("本当に削除しますか？")) {
                    this.submit(); // OKならフォーム送信
                }
            });
        });
	</script>
</head>

<body>
	<header>
	    <a href="PresetList"><img src="assets/img/Vector.png" alt=""></a>
	    <h1>プリセット詳細</h1>
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
	 
	<div id="presetDelete">
		<h2 class="presetCategory">${preset.presetName}</h2>
		<form action="PresetDetail" method="post" id="presetDeleteForm">
			<input type="hidden" id="presetId" name="presetId" value="${presetId}" />
			<button type="submit" id="presetDeleteButton">プリセット削除</button>
		</form>
	</div> 
	
	<div class="tasks">
        <ul>
	        <c:forEach var="presetTask" items="${presetTaskList}">
	            <li class="task">
                    <div>
                        <div class="timersolid">
                            <p class="tasktime">${presetTask.taskTime}</p>
                            <span class="prisetSpan"></span>
                        </div>
                        <a href="PresetTaskDetail?presetTaskId=${presetTask.id}">
                        	<p class="taskname">${presetTask.name}</p>
                        </a>
                    </div>
	            </li>
	        </c:forEach>
	    </ul>
    </div>
</body>
</html>