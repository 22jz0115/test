<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="assets/css/an/style.css">
    <title>タスク入力</title>
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
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="assets/js/taskinput/script.js"></script>

    <header>
	    <c:choose>
	
	        <c:when test="${not empty selectedDate}">
	            <a href="Task?date=${selectedDate}" class="back1">
	                <img src="assets/img/戻るボタン.png" alt="戻る">
	            </a>
	        </c:when>
	
	   
	        <c:otherwise>
	            <a href="Home" class="back1">
	                <img src="assets/img/戻るボタン.png" alt="戻る">
	            </a>
	        </c:otherwise>
	    </c:choose>
	    <h1>タスク入力</h1>
	</header>


    <div class="display">
        <form action="TaskInput" method="post">

            <!-- 日付選択 -->
			<div class="form-group">
			    <label for="dateInput1">日付</label>
			    <input type="date" id="dateInput1" name="dateInput1" value="${selectedDate}" required>
			</div>
			
			<p>～</p>
			
			<div class="form-group">
			    <label for="dateInput2">日付</label>
			    <input type="date" id="dateInput2" name="dateInput2" value="${selectedDate}" required>
			</div>

            <script>
                // サーバーから渡された日付 (例: "2024-11-15") をJavaScriptに渡す
                const selectedDate = '<%= request.getAttribute("selectedDate") %>';
            </script>

            <!-- 時間入力 -->
            <div class="form-group">
                <label for="appt-time">時間</label>
                <input id="appt-time" type="time" name="appt-time" value="13:30" />
            </div>

            <!-- ラジオボタン -->
            <div class="radiobutton">
                <label class="switch_label9">
                    <span id="outdoorText">屋内</span>
                    <div class="switch">
                        <input type="checkbox" name="switch" id="switch" onclick="toggleCategory()" />
                        <div class="circle"></div>
                        <div class="base"></div>
                    </div>
                    <span id="indoorText" class="title">屋外</span>
                </label>
            </div>

            <!-- カテゴリーのプルダウン -->
		<div id="categoryDiv" class="form-group">
		    <label for="categorySelect">カテゴリーを選択:</label>
		    <select id="categorySelect" name="categorySelect" required>
		        <c:forEach var="category" items="${categoryList}">
		            <option id="task-1" class="deletable" value="${category.id}">${category.categoryName}</option>
		        </c:forEach>
		    </select>
		</div>

            <!-- タスク名の入力 -->
            <div class="form-group">
                <label for="taskName" class="taskname">タスク名:</label>
                <input type="text" id="taskName" name="taskName" required minlength="1" maxlength="15" required/>
            </div>

            <!-- メモの入力 -->
            <div class="form-group">
                <label for="story" class="memo-label">メモ</label>
                <textarea id="story" name="story" rows="5" cols="50">メモ</textarea>
            </div>

            <button id="touroku" type="submit" class="button3" onclick="this.disabled = true; this.form.submit();">追加</button>
        </form>
    </div>
</body>

</html>
