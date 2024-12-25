<%@ page contentType="text/html; charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
 <link rel="stylesheet" href="assets/css/an/style.css">
    <title>ご褒美画面</title>
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

<body class="body-1">

    <header>
        <a href="Home"><img src="assets/img/戻るボタン.png" alt="戻るボタン"></a>
        <h1>Collection</h1>
        <div class="openbtn1"><span></span><span></span><span></span></div>
	    <nav id="g-nav">
	      <ul>
	      <li> <a class="todaybutton" href=Task>今日のタスク</a></li>
	        <li> <a class="link-button" href="Category">カテゴリー</a></li>
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

    <p class="years">現在の年は <span id="year"><%= new java.util.Date().getYear() + 1900 %></span>年です。</p>

    <div class="grid-container">
        <c:choose>
            <c:when test="${empty collection}">
                <!-- ${collection} が空の場合、1から12までの月を表示 -->
                <c:forEach var="month" begin="1" end="12" step="1">
                    <div class="dotted-circle">
                        <p>${month}月</p>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <!-- ${collection} がある場合は、各月のコレクション画像を表示 -->
                <c:forEach var="month" begin="1" end="12" step="1">
                    <c:forEach var="collect" items="${collection}">
                        <c:choose>
                            <c:when test="${collect.id == month}">
                                <img src="${collect.img}" alt="金メダル" class="round-image">
                            </c:when>
                            <c:otherwise>
                                <div class="dotted-circle">
                                    <p>${month}月</p>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>