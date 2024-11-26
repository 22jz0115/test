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
</head>
<body>
	<header>
	    <a href="PresetList"><img src="assets/img/Vector.png" alt=""></a>
	    <h1>プリセット詳細</h1>
	</header>
	 
	<h2 class="taskcategory">${preset.presetName}</h2>
	<input type="hidden" id="presetId" name="presetId" value="${presetId}" />
	
	<div class="tasks">
        <ul>
	        <c:forEach var="presetTask" items="${presetTaskList}">
	            <li class="task">
                    <div>
                        <div class="timersolid">
                            <p class="tasktime">${presetTask.taskTime}</p>
                            <span></span>
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