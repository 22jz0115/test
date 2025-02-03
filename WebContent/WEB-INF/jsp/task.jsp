<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="assets/css/style.css">
	<title>タスク</title>
    <link rel="shortcut icon" href="assets/img/icon-192x192.png" type="image/png">
    <link rel="manifest" href="manifest.json">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="assets/js/nav/script.js"></script>
   
    
    <!-- サーバーから送られた日付をJavaScriptに渡す -->
	<script>
	// サーバーから送られた日付を取得
	    const selectedDateString = '<%= request.getAttribute("selectedDate") %>';
	    const selectedDate = selectedDateString ? new Date(selectedDateString) : new Date();
    console.log("サーブレットからの日付: " + selectedDate.toISOString());
	</script>
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
       <a href="Home"><img class="imgVector" src="assets/img/Vector.png" alt=""></a>
       <h1>タスク</h1>
       <div class="openbtn1"><span></span><span></span><span></span></div>
    <nav id="g-nav">
      <ul>
      	<li> <a class="homebutton" href=Home>ホーム</a></li>
      	<li> <a class="todaybutton" href=Task>今日のタスク</a></li>
        <li> <a class="link-button" href="Category">タスク履歴</a></li>
        <li> <a class="link-button" href="PresetList">プリセット履歴</a></li>
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
 	 <div id="month-buttons">
         <button id="prev-month"><img alt="前月" src="assets/img/left_01.png"></button>
         <h2 id="yearMonth"></h2>
         <button id="next-month"><img alt="次月" src="assets/img/right_01.png"></button>
     </div>
     <table></table>
            
    <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>

        <!--
        //-->
    </script>
    
    <div class="progress-container">
        <div class="progress-bar" id="countDisplay"></div> <!-- ここに進捗率を表示 -->
    </div>
    
    <div class="tasks">
        <ul>
	        <c:forEach var="task" items="${taskList}">
	            <li class="task" data-task-id="${task.id}">
                    <div class="taskNameAndTime">
                        <div class="timersolid">
                            <p class="tasktime">${task.formattedTime}</p>
                            <span class="span"></span>
                        </div>
                		<a href="TaskDetail?taskId=${task.id}">
                        	<p class="taskname">${task.taskName}</p>
                		</a>
                    </div>
	                <div class="buttons">
	                    <label class="toggle-button-4">
	                        <input type="checkbox" class="check" 
	                            <c:if test="${task.check == 1}">checked</c:if>>
	                    </label>
	                </div>
	            </li>
	        </c:forEach>
	    </ul>
    </div>

    <div class="bts">
       <a  href="TaskInput?date=${selectedDate}"><p>タスク追加</p></a>
       <a  href="Preset?date=${selectedDate}"><p>プリセット追加</p></a> 
    </div>
    

    <script type="text/javascript" src="assets/js/task/script.js"></script>
</body>
</html>