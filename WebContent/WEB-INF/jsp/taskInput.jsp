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

            <div class="form-group">
			    <label for="dateOption">日付:</label>
			    <select id="dateOption" name="dateOption" onchange="toggleDateInput()" required>
			        <option value="specified">指定</option>
			        <option value="currentMonth">今月</option>
			    </select>
			</div>
			
			<!-- 日付を指定する入力欄 -->
			<div id="dateInputs" style="display: block;">
			    <label for="dateInput1">開始日</label>
			    <input type="date" id="dateInput1" name="dateInput1" value="${selectedDate}" required>
			    
			    <label for="dateInput2">終了日</label>
			    <input type="date" id="dateInput2" name="dateInput2">
			</div>
			
			<script>
		    // プルダウン選択に応じて日付入力を切り替える
		    function toggleDateInput() {
		        const dateOption = document.getElementById("dateOption").value;
		        const dateInputs = document.getElementById("dateInputs");
		        const dateInput1 = document.getElementById("dateInput1");
		
		        if (dateOption === "currentMonth") {
		            // 今月が選択された場合、日付入力を非表示に
		            dateInputs.style.display = "none";
		            // 開始日のrequired属性を削除
		            dateInput1.removeAttribute("required");
		        } else {
		            // 指定が選択された場合、日付入力を表示
		            dateInputs.style.display = "block";
		            // 開始日のrequired属性を追加
		            dateInput1.setAttribute("required", "required");
		        }
		    }
		
		    // サーバーから渡された日付 (例: "2024-11-15") をJavaScriptに渡す
		    const selectedDate = '<%= request.getAttribute("selectedDate") %>';
		    // 初期状態で、選択された日付を表示
		    document.getElementById("dateInput1").value = selectedDate;
		</script>



           
            
            <!-- 繰り返し設定 -->
			<div class="form-group">
		    <label for="repeatSelect">繰り返し:</label>
		    <select id="repeatSelect" name="repeatSelect" onchange="toggleRepeatOptions()" required>
		        <option value="daily" selected>毎日</option>
		        <option value="weekly">毎週</option>
		    </select>
		</div>
			
			<!-- 毎週の選択肢 -->
			<div id="weeklyOptions" class="form-group" style="display: none;">
			    <label for="daySelect">曜日を選択:</label>
			    <select id="daySelect" name="daySelect">
			        <option value="monday">月曜日</option>
			        <option value="tuesday">火曜日</option>
			        <option value="wednesday">水曜日</option>
			        <option value="thursday">木曜日</option>
			        <option value="friday">金曜日</option>
			    </select>
			</div>
			
			<script>
			    // プルダウン選択に応じて繰り返し設定を切り替える
			    function toggleRepeatOptions() {
			        const repeatSelect = document.getElementById("repeatSelect").value;
			        const weeklyOptions = document.getElementById("weeklyOptions");
			
			        if (repeatSelect === "weekly") {
			            // 毎週が選択された場合、曜日選択のプルダウンを表示
			            weeklyOptions.style.display = "block";
			        } else {
			            // 毎日が選択された場合、曜日選択のプルダウンを非表示
			            weeklyOptions.style.display = "none";
			        }
			    }
			
			    // 初期状態のチェックを行う
			    window.onload = function() {
			        toggleRepeatOptions(); // ページ読み込み時に状態を確認
			    };
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

           <button 
			    id="touroku" 
			    type="submit" 
			    class="button3" 
			    onclick="preventMultipleSubmit(this);">
			    追加
			</button>

        </form>
    </div>
</body>

</html>
