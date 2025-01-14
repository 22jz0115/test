<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="assets/css/style.css">
    <title>統計</title>
    <link rel="shortcut icon" href="assets/img/icon-192x192.png" type="image/png">
    <link rel="manifest" href="manifest.json">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="assets/js/nav/script.js"></script>
    <script>
        // サーブレットから渡されたデータをJavaScriptに埋め込む
        const percentageFromDatabase1 = ${percentageFromDatabase1};
        const percentageFromDatabase2 = ${percentageFromDatabase2};
        const percentageFromDatabase3 = ${percentageFromDatabase3};
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
        <a href="Home"><img src="assets/img/Vector.png" alt=""></a>
        <h1>達成率</h1>
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

    

    <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <script type="text/javascript" src="assets/js/result/script.js"></script>

    <div>

      
        
       
        <div class="month-navigation">
		    <!-- 月の変更ボタン -->
		    <form action="Result" method="get">
		        <input type="hidden" name="month" value="${month - 1}" />
		        <button type="submit"><img alt="前月" src="assets/img/left_01.png"></button>
		    </form>
		    <h2 class="toMounthrate">${month }月の達成率</h2>
		    <form action="Result" method="get">
		        <input type="hidden" name="month" value="${month + 1}" />
		        <button type="submit"><img alt="次月" src="assets/img/right_01.png"></button>
		    </form>
		</div>

    

      

        <div class="pie-chart-2" id="pieChart"></div>
    </div>
    <div class="cycle-flex">
        <div>
            <p>屋外タスク</p>
            <div class="pie-chart-2" id="pieChart2"></div>
        </div>
        
        <div>
            <p>屋内タスク</p>
            <div class="pie-chart-2" id="pieChart3"></div>
        </div>
    </div>
</body>
</html>
