<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
       <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
       <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
 <header>
        <a href="Task?date=${task.formattedDate}"><img class="imgVector" src="assets/img/戻るボタン.png" alt=""></a>
        <h1>タスク詳細</h1>
    </header>
 

     <form action="TaskDetail" method="post">
   
  

    <!-- 日付表示 -->
 
       <div class="form-group">
            <label for="dateInput">日付:</label>
            <input type="date" id="dateInput" name="dateInput" value="${task.formattedDate}" readonly>
        </div>     
        
    <!-- 時間入力 -->
    <div class="form-group">
        <label for="appt-time-${task.id}">時間:</label>
        <input id="appt-time-${task.id}" type="time" name="apptTime" value="${task.formattedTime}" readonly />
    </div>

    <!-- カテゴリ表示 -->
       <div class="form-group">
                   <p class="taskname">カテゴリ名</p>
                <input type="text" id="categotyname" name="categotyname" value="${categorys.categoryName}" required minlength="1" maxlength="8" />
            </div>
    
    
    <!-- タスク名表示 -->
   
        <li class="task" data-task-id="${task.id}">
       
              
                
                     <!-- タスク名の入力 -->
            <div class="form-group">
                   <p class="taskname">タスク名</p>
                <input type="text" id="taskName" name="taskName" value="${task.taskName}" required minlength="1" maxlength="8" />
            </div>
                
     
            <div class="buttons">
                <label class="toggle-button-4">
                    <input type="checkbox" class="check" 
                        <c:if test="${task.check == 1}">checked</c:if>>
                </label>
            </div>
        </li>


    <!-- メモの表示 -->
    <div class="form-group">
        <label for="story" class="memo-label">メモ</label>
        <textarea id="story" name="story" rows="5" cols="50" readonly>${task.memo}</textarea>
    </div>
    
    </form>
    
    <!-- 削除ボタン -->
    <form action="Delete" method="post">
        <input type="hidden" name="taskId" value="${task.id}">
        <input type="hidden" name="selectedDate" value="${selectedDate}">
        <input type="hidden" name="action" value="delete">
        <button type="submit" class="button">削除</button>
    </form>

    <!-- 変更保存ボタン -->
    <form action="TaskDetail" method="post">
        <input type="hidden" name="taskId" value="${task.id}">
        <input type="hidden" name="action" value="edit">
        <button type="submit" class="button">変更を保存</button>
    </form>

</body>
</html>