<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
       <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 <meta name="viewport" content="width=device-width, initial-scale=1.0">
 <link rel="stylesheet" href="assets/css/an/style.css">
 <link rel="shortcut icon" href="assets/img/icon-192x192.png" type="image/png">
    <link rel="manifest" href="manifest.json">
    
<title>タスク詳細画面</title>
</head>
<body>
 <header>
        <a href="Task"><img class="imgVector" src="assets/img/戻るボタン.png" alt=""></a>
        <h1>タスク詳細</h1>
    </header>
 

    <form action="TaskInput" method="post">
   
    <!-- 日付表示 -->
    <div class="form-group">
        <label for="dateInput">日付</label>
        <input type="date" id="dateInput" name="dateInput" value="${dateInput}" readonly>
    </div>

     <!-- 時間入力 -->
            <div class="form-group">
                <label for="appt-time-${task.id}">時間</label>
                <input id="appt-time-${task.id}" type="time" name="apptTime" value="${task.formattedTime}" readonly />
            </div>


<ul>
    <c:forEach var="category" items="${categoryList}">
        <li>
            <a href="yourServletPath?categoryId=${category.id}">
                ${category.categoryName}
            </a>
        </li>
    </c:forEach>
</ul>
    <!-- タスク名表示 -->
    <c:forEach var="task" items="${taskList}">
            <li class="task" data-task-id="${task.id}">
                <a href="TaskDetail?taskId=${task.id}">
                    <div>
                        <div class="timersolid">
                           
                            <span></span>
                        </div>
                        <p class="taskname">タスク名：${task.taskName}</p>
                    </div>
                </a>
                <div class="buttons">
                    <label class="toggle-button-4">
                        <input type="checkbox" class="check" 
                            <c:if test="${task.check == 1}">checked</c:if>>
                    </label>
                </div>
            </li>
        </c:forEach>

    <!-- メモの表示 -->
    <div class="form-group">
        <label for="story" class="memo-label">メモ</label>
        <textarea id="story" name="story" rows="5" cols="50" readonly>${story}</textarea>
    </div>
    
    <!-- 削除ボタン -->
<form action="TaskDetail" method="post">
    <input type="hidden" name="taskId" value="${task.id}">
    <input type="hidden" name="action" value="delete">
    <button type="submit" class="button">削除</button>
</form>

<form action="TaskDetail" method="post">
    <input type="hidden" name="taskId" value="${task.id}">
    <input type="hidden" name="action" value="edit">
    <button type="submit" class="button">変更を保存</button>
</form>
</form>
</body>
</html>