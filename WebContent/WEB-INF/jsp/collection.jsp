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
</head>

<body class="body-1">

    <header>
        <a href="Home"><img src="assets/img/戻るボタン.png" alt="戻るボタン"></a><h1>Collection</h1>
    </header>

    <p class="years">現在の年は <span id="year"><%= new java.util.Date().getYear() + 1900 %></span>年です。</p>

    <div class="grid-container">
    	 <c:forEach begin="1" end="12" step="1" var="month">
    	 	<c:forEach var="collect" items="${collection }">
	    	 	 <c:choose>
				    <c:when test="${collect.id == month }">
				    	<img src="${collect.img }" alt="金メダル" class="round-image">
				    </c:when>
				    <c:otherwise>
				    	<div class="dotted-circle"><p>${month }月</p></div>
				    </c:otherwise>
				</c:choose>   
    	 	</c:forEach>
	    	         
        </c:forEach>
        
    </div>
</body>
</html>