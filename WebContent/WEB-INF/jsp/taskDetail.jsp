<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="assets/js/taskDetail/script.js"></script>
    
    <header>
        <a href="Task?date=${task.formattedDate}">
            <img class="imgVector" src="assets/img/戻るボタン.png" alt="">
        </a>
        <h1>タスク詳細</h1>
    </header>

    <form action="TaskDetail" method="post">
        
        <!-- 日付表示 -->
        <div class="form-group">
            <label for="dateInput">日付:</label>
            <input type="date" id="dateInput" name="dateInput" value="${task.formattedDate}">
        </div>

        <!-- 時間入力 -->
        <div class="form-group">
            <label for="appt-time-${task.id}">時間:</label>
            <input id="appt-time-${task.id}" type="time" name="apptTime" value="${task.formattedTime}"  />
        </div>

        <!-- 屋内・屋外スイッチ -->
        <div class="radiobutton">
            <label class="switch_label9">
                <span id="outdoorText">屋内</span>
                <div class="switch">
                    <input type="checkbox" class="check" name="switch" id="checkTask" data-check-task="${task.outin}">
                    <div class="circle"></div>
                    <div class="base"></div>
                </div>
                <span id="indoorText" class="title">屋外</span>
            </label>
        </div>

        <!-- カテゴリ選択 -->
        <div class="form-group">
            <p class="taskname">カテゴリ名</p>
            <select id="categorySelect" name="categorySelect" required>
                <c:forEach var="category" items="${categoryList}">
                    <option value="${category.id}" <c:if test="${category.id == task.categoryId}">selected</c:if>>
                        ${category.categoryName}
                    </option>
                </c:forEach>
            </select>
        </div>
        

        <!-- タスク名表示 -->
        <li class="task" data-task-id="${task.id}">
            <div class="form-group">
                <p class="taskname">タスク名</p>
                <input type="text" id="taskName" name="taskName" value="${task.taskName}" required minlength="1" maxlength="8" />
            </div>
        </li>

        <!-- メモの表示 -->
        <div class="form-group">
            <label for="story" class="memo-label">メモ</label>
            <textarea id="story" name="story" rows="5" cols="50">${task.memo}</textarea>
        </div>

     

        <!-- 変更保存ボタン -->
        <input type="hidden" name="taskId" value="${task.id}">
        <input type="hidden" name="action" value="edit">
        <button type="submit" class="button">変更を保存</button>
    </form>
    
       <!-- 削除ボタン -->
        <form action="Delete" method="post">
            <input type="hidden" name="taskId" value="${task.id}">
            <input type="hidden" name="selectedDate" value="${selectedDate}">
            <input type="hidden" name="action" value="delete">
            <button type="submit" class="button">削除</button>
        </form>
</body>
</html>
