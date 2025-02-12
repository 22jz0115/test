<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="assets/css/style.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <script type="text/javascript" src="assets/js/preset/preset.js"></script>
    <title>プリセット</title>
    <link rel="shortcut icon" href="assets/img/icon-192x192.png" type="image/png">
    <link rel="manifest" href="manifest.json">
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/ui/1.13.2/jquery-ui.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="assets/js/nav/script.js"></script>
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
        <h1>プリセット</h1>
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

    <form action="Preset" method="post" class="presetsForm">
        <div class="setName">
            <h2>プリセット名</h2>
            <select id="presetName" name="preset_name" required>
                <option hidden selected="selected" value="">プリセットを選択してください</option>
                <c:forEach var="preset" items="${presetList}">
                    <option id="preset" value="${preset.id}">${preset.presetName}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="dateOption">日付:</label>
            <select id="dateOption" name="dateOption" onchange="toggleDateInput()" required>
            	<option value="todayonly">一日</option>
                <option value="specified">指定</option>
                <option value="currentMonth">今月</option>
            </select>
        </div>

        <!-- 日付を指定する入力欄 -->
        <div id="dateInputGroup">
			    <div id="dateInput1Container">
			        <label for="dateInput1">開始日</label>
			        <input type="date" id="dateInput1" name="dateInput1" value="${selectedDate}" required>
			    </div>
			    <div id="dateInput2Container" style="display:none;">
			        <label id="labelDateInput2" for="dateInput2">終了日</label>
			        <input type="date" id="dateInput2" name="dateInput2">
			    </div>
			</div>

	        <!-- 繰り返し設定 -->
	        <div id="repeatafterme" class="form-group" style="display:none;">
	            <label for="repeatSelect">繰り返し:</label>
	            <select id="repeatSelect" name="repeatSelect" onchange="toggleRepeatOptions()">
	                <option value="daily" selected>毎日</option>
	                <option value="weekly">毎週</option>
	            </select>
	        </div>
			
			<script>
			    // プルダウン選択に応じて日付入力を切り替える
			    function toggleDateInput() {
			        const dateOption = document.getElementById("dateOption").value;
			        const dateInput1Container = document.getElementById("dateInput1Container");
			        const dateInput2Container = document.getElementById("dateInput2Container");
			        const dateInput1 = document.getElementById("dateInput1");
			        const dateInput2 = document.getElementById("dateInput2");
			        const repeatafterme = document.getElementById("repeatafterme");
			        const repeatSelect = document.getElementById("repeatSelect").value;
	                const weeklyOptions = document.getElementById("weeklyOptions");
			
			        if (dateOption === "currentMonth") {
			            // 今月が選択された場合、日付入力を非表示に
			            dateInput1Container.style.display = "none";
			            dateInput2Container.style.display = "none";
			            repeatafterme.style.display = "block";
			            
			            // required属性を削除
			            dateInput1.removeAttribute("required");
			            dateInput2.removeAttribute("required");
			            repeatafterme.setAttribute("required", "required");
			        } else if(dateOption === "specified") {
			            // 指定が選択された場合、日付入力を表示
			            dateInput1Container.style.display = "block";
			            dateInput2Container.style.display = "block";
			            repeatafterme.style.display = "block";
			            // required属性を追加
			            dateInput1.setAttribute("required", "required");
			            dateInput2.setAttribute("required", "required");
			            repeatafterme.setAttribute("required", "required");
			        } else {
			        	dateInput1Container.style.display = "block";
			        	dateInput2Container.style.display = "none";
			        	repeatafterme.style.display = "none";
			        	 weeklyOptions.style.display = "none";

			        	dateInput1.setAttribute("required", "required");
			        	dateInput2.removeAttribute("required");
			        	repeatafterme.removeAttribute("required");

			
						document.getElementById("repeatSelect").options[0].selected = true;
			
			             toggleRepeatOptions(); // UIを更新
				    }
    			}

		
			    // サーバーから渡された日付 (例: "2024-11-15") をJavaScriptに渡す
			    const selectedDate = '<%= request.getAttribute("selectedDate") %>';
			    // 初期状態で、選択された日付を表示
			    document.getElementById("dateInput1").value = selectedDate;
			</script>
        
	        <!-- 毎週の選択肢 -->
	        <div id="weeklyOptions" class="form-group" style="display: none;">
	            <label for="daySelect">曜日を選択:</label>
	            <select id="daySelect" name="daySelect">
	                <option value="monday">月曜日</option>
	                <option value="tuesday">火曜日</option>
	                <option value="wednesday">水曜日</option>
	                <option value="thursday">木曜日</option>
	                <option value="friday">金曜日</option>
	                <option value="saturday">土曜日</option>
			        <option value="sunday">日曜日</option>
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

	        <!-- タスクリスト表示 -->
	        <div class="presets">
	            <ul id="taskList">
	                <!-- タスクリストはJavaScriptで動的に表示されます -->
	            </ul>
	        </div>
	
	        <div class="presetActions">
	            <input type="submit" value="プリセット追加">
	            <a href="PresetInput" id="presetButton">
	                <button id="presetTaskAdd" type="button">
	                    <img alt="追加ボタン" src="assets/img/pulsButton.png">
	                </button>
	            </a>
	        </div>
   		 </form>
</body>
</html>
