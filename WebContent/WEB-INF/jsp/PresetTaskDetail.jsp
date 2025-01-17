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
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="assets/js/nav/script.js"></script>
    <title>プリセットタスク詳細画面</title>
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
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="assets/js/taskDetail/script.js"></script>
    
    <header>
        <a href="PresetDetail?presetId=${presetTask.presetId}">
            <img class="imgVector" src="assets/img/戻るボタン.png" alt="">
        </a>
        <h1>プリセットタスク詳細</h1>
        <div class="openbtn1"><span></span><span></span><span></span></div>
	    <nav id="g-nav">
	      <ul>
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

    <form action="PresetTaskDetail" method="post">
	<input type="hidden" id="presetId" name="presetId" value="${presetTask.presetId}" />
        <!-- 時間入力 -->
        <div class="form-group">
            <label for="appt-time-${presetTask.id}">時間:</label>
            <input id="appt-time-${presetTask.id}" type="time" name="apptTime" value="${presetTask.taskTime}"  />
        </div>

        <!-- 屋内・屋外スイッチ -->
        <div class="radiobutton">
            <label class="switch_label9">
                <span id="outdoorText">屋内</span>
                <div class="switch">
                    <input type="checkbox" class="check" name="switch" id="checkTask" data-check-task="${presetTask.outin}">
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
                    <option value="${category.id}" <c:if test="${category.id == presetTask.categoryId}">selected</c:if>>
                        ${category.categoryName}
                    </option>
                </c:forEach>
            </select>
        </div>
        

        <!-- タスク名表示 -->
        <li class="task" data-task-id="${presetTask.id}">
            <div class="form-group">
                <p class="taskname">タスク名</p>
                <input type="text" id="taskName" name="taskName" value="${presetTask.name}" required minlength="1" maxlength="8" />
            </div>
        </li>

        <!-- メモの表示 -->
        <div class="form-group">
            <label for="story" class="memo-label">メモ</label>
            <textarea id="story" name="story" rows="5" cols="50">${presetTask.memo}</textarea>
        </div>

     
		<div class="button-container">
	        <!-- 変更保存ボタン -->
	        <input type="hidden" name="presetTaskId" value="${presetTask.id}">
	        <input type="hidden" name="action" value="edit">
	        <button type="submit" class="button">変更を保存</button>
        </div>
    </form>
    
    <div class="button-container">
       <!-- 削除ボタン -->
        <form action="PresetTaskDelete" method="post">
            <input type="hidden" name="taskId" value="${presetTask.id}">
            <input type="hidden" name="presetId" value="${presetTask.presetId}">
            <input type="hidden" name="action" value="delete">
            <button type="submit" class="button">削除</button>
        </form>
        </div>
</body>
</html>
