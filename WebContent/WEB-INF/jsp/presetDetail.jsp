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
	    <a href="PresetList"><img src="assets/img/Vector.png" alt=""></a>
	    <h1>プリセット詳細</h1>
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