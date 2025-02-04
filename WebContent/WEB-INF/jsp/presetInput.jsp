<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/ui/1.13.2/jquery-ui.min.js"></script>
  	<link rel="stylesheet" href="assets/css/an/style.css">
    <script type="text/javascript" src="assets/js/preset/preset.js"></script>
    <script type="text/javascript" src="assets/js/nav/script.js"></script>
    <title>プリセット入力画面</title>
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
    <header>
        <a href="Preset" class="back1"><img src="assets/img/戻るボタン.png"></a><h1>プリセット入力</h1>
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

    <div class="display">

        <form action="PresetInput" method="post">

			<div class="setName">
                <label class="PrisetNameLabel" for="preset_name">プリセット名</label>
                <input type="text" name="presetTask_name" value="" data-options='${presetList}' class="free_dropdown" required/>
            </div>
			
            <!-- 時間入力 -->
            <div class="form-group">
                <label for="appt-time">時間</label>
                <input id="appt-time" type="time" name="appt-time" value="13:30" required/>
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
			    <label for="categorySelect">カテゴリーを選択</label>
			    <select id="categorySelect" name="categorySelect">
			        <!-- カテゴリのオプションがJavaScriptで動的に設定されます -->
			        <c:forEach var="category" items="${categoryList}">
			            <option id="task-1" class="deletable" value="${category.id}">${category.categoryName}</option> 
			        </c:forEach>
			    
			    </select>
			</div>
		
		    <!-- タスク名の入力 -->
		    <div class="form-group">
		        <label for="taskName" class="taskname">タスク名</label>
		        <input type="text" id="taskName" name="taskName" required minlength="1" maxlength="8" required/>
		    </div>
		    
		    <!-- メモの入力 -->
		    <div class="form-group">
		        <label for="story" class="memo-label">メモ</label>
		        <textarea id="story" name="story" rows="5" cols="50" required>メモ</textarea>
		    </div>
		
		    <button id="touroku" type="submit" class="button3" onclick="this.disabled = true; this.form.submit();">追加</button>
		</form>
    </div>
</body>  

</html>