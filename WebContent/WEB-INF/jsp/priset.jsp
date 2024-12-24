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
	</header>

    

    <form action="Preset" method="post" class="presetsForm">
    	<div class="setName">
            <h2>プリセット名</h2>
            <select id="presetName" name="preset_name" required>
            	<option  hidden selected="selected" value="">プリセットを選択してください</option>
            	<c:forEach var="preset" items="${presetList}">
			            <option id="preset" value="${preset.id}">${preset.presetName}</option> 
			    </c:forEach>
            </select>
        </div>
        
        <div class="form-group">
			    <label for="dateInput1">日付</label>
			    <input type="date" id="dateInput1" name="dateInput1" value="${selectedDate}" required>
			</div>
			
			<p>～</p>
			
			<div class="form-group">
			    <label for="dateInput2">日付</label>
			    <input type="date" id="dateInput2" name="dateInput2" value="${selectedDate}" required>
			</div>

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
