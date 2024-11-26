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
</head>
<body>
	<header>
	    <a href="Category"><img src="assets/img/Vector.png" alt=""></a>
	    <h1>タスク履歴</h1>
	</header>
	 
	<h2 class="taskcategory">${categoryName.categoryName}</h2>
	<div>
		<ul class="taskhistory">
		  <c:forEach var="task" items="${taskList}">
		    <li class="history">
		      ${task.taskName}
		    </li>
		  </c:forEach>
		  
		</ul>
	</div>
</body>
</html>
