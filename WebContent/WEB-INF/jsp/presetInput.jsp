<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
  	<link rel="stylesheet" href="assets/css/an/style.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <script type="text/javascript" src="assets/js/preset/preset.js"></script>
    <title>プリセット入力画面</title>
    <link rel="shortcut icon" href="assets/img/icon-192x192.png" type="image/png">
    <link rel="manifest" href="manifest.json">
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/ui/1.13.2/jquery-ui.min.js"></script>

</head>
<body>
    <header>
        <a href="Preset" class="back1"><img src="assets/img/戻るボタン.png"></a><h1>プリセット入力</h1>
    </header>

    <div class="display">

        <form action="PresetInput" method="post">

			<div class="setName">
                <label for="preset_name">プリセット名</label>
                <input type="text" name="preset_name" value="" data-options='${presetList}' class="free_dropdown" />
            </div>
			
            <!-- 時間入力 -->
            <div class="form-group">
                <label for="appt-time">時間</label>
                <input id="appt-time" type="time" name="appt-time" value="13:30" />
            </div>

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
			    <select id="categorySelect" name="categorySelect">
			        <!-- カテゴリのオプションがJavaScriptで動的に設定されます -->
			        <c:forEach var="category" items="${categoryList}">
			            <option id="task-1" class="deletable" value="${category.id}">${category.categoryName}</option> 
			        </c:forEach>
			    
			    </select>
			</div>
		
		    <!-- タスク名の入力 -->
		    <div class="form-group">
		        <label for="taskName" class="taskname">タスク名:</label>
		        <input type="text" id="taskName" name="taskName" required minlength="1" maxlength="8" />
		    </div>
		    
		    <!-- メモの入力 -->
		    <div class="form-group">
		        <label for="story" class="memo-label">メモ</label>
		        <textarea id="story" name="story" rows="5" cols="50">メモ</textarea>
		    </div>
		
		    <button id="touroku" type="submit" class="button3">追加</button>
		</form>
    </div>
</body>  

</html>