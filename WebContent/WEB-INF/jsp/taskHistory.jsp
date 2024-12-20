<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="assets/css/style.css">
    <title>タスク履歴</title>
    <link rel="shortcut icon" href="assets/img/icon-192x192.png" type="image/png">
    <link rel="manifest" href="manifest.json">
</head>
<body>
    <header>
        <a href="Category?from=TaskHistory"><img src="assets/img/Vector.png" alt=""></a>
        <h1>タスク履歴</h1>
    </header>
     
    <h2 class="taskcategory">${categoryName.categoryName}</h2>
    
     <form action="TaskHistory" method="POST">
	    <input type="hidden" name="categoryId" value="${categoryName.id}"> <!-- hiddenでカテゴリIDを送信 -->
	    <select id="categorySelect" name="searchHistory" onchange="this.form.submit()">            
	        <option value="dateAsc" <c:if test="${searchHistory == 'dateAsc'}">selected</c:if>>昇順</option>
	        <option value="dateDesc" <c:if test="${searchHistory == 'dateDesc'}">selected</c:if>>降順</option>
	        <option value="completed" <c:if test="${searchHistory == 'completed'}">selected</c:if>>完了</option>
	        <option value="incomplete" <c:if test="${searchHistory == 'incomplete'}">selected</c:if>>未完了</option>
	    </select>
	</form>
	
    <div>
        <c:forEach var="entry" items="${groupedTasks}">
            <h3 class="taskdate">${entry.key}</h3> <!-- 日付を表示 -->
            <ul class="taskhistory">
                <c:forEach var="task" items="${entry.value}">
                    <li class="history">
                        <a href="TaskDetail?taskId=${task.id}&from=TaskHistory&categoryId=${task.categoryId}" class="task-item">
                            <p class="taskname">${task.taskName}</p>
                            <p class="task-status">
                                <c:choose>
                                    <c:when test="${task.check == 1}">
                                        完了 <!-- 完了日付 -->
                                    </c:when>
                                    <c:otherwise>
                                        未完了 <!-- 未完了の場合 -->
                                    </c:otherwise>
                                </c:choose>
                            </p>
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </c:forEach>
    </div>
</body>
</html>

