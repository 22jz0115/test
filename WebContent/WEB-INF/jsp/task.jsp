<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="assets/css/style.css">
	<title>Insert title here</title>
    <link rel="shortcut icon" href="assets/img/icon-192x192.png" type="image/png">
    <link rel="manifest" href="manifest.json">
	<script>
        // リセットボタンがクリックされたときにその親のliを削除する関数
        function removeTask(button) {
            const taskItem = button.closest('li'); // ボタンの親li要素を取得
            if (taskItem) {
                taskItem.remove(); // li要素をDOMから削除
            }
        }
    </script>
</head>
<body>
	    <header>
        <a href="Home"><img class="imgVector" src="assets/img/Vector.png" alt=""></a>
        <h1>タスク</h1>
    </header>
    
        <table><caption></caption></table>
            
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
               	<li class="task">
                <div>
                    <div class="timersolid">
                        <p class="tasktime">${task.formattedTime}</p>
                        <span></span>
                    </div>
                    <p class="taskname">
                        ${task.taskName }
                    </p>
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
    <h2>メモ</h2>
    <div class="area">
        <textarea name="" id="" cols="50" rows="10"></textarea>
    </div>

    <div class="bts">
       <a  href="TaskInput"><p>タスク追加</p></a>
       <a  href="Preset"><p>プリセット追加</p></a> 
    </div>
    
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="assets/js/task/script.js"></script>
    
    <!-- サーバーから送られた日付をJavaScriptに渡す -->
    <script>
        // サーバーから渡された日付 (例: "2024-11-15") をJavaScriptに渡す
        const selectedDate = '<%= request.getAttribute("selectedDate") %>';
    </script>
</body>
</html>