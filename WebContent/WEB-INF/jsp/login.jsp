<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ビデオ作品リスト</title>
    <link rel="stylesheet" href="assets/css/style.css">
</head>

<body>

  <header>
  	<c:choose>
	  	<c:when test="${empty loginUser}">
		  	<div class="btn-auth">
		      <a href="Login" class="btn">ログイン</a>
		    </div>
	  	</c:when>
	  	<c:otherwise>
	  		<p>ログイン中 : ${loginUser.email}さん</p>
	  		<div class="btn-auth">
		      <a href="Logout" class="btn out">ログアウト</a>
		    </div>
	  	</c:otherwise>
  	</c:choose>
  </header>
  
  <ul>
    <c:forEach var="accounts" items="${list}">
      <li class="list-item">
      	<div class="items">
      	  <p class="item-ttl">${accounts.name}</p>
      	</div>
        
        <div>
          <p>${accounts.email}</p>
        </div>
        
        <div>
          <p>${accounts.location}</p>
        </div>
      </li>     
    </c:forEach>
  </ul>

</body>
</html>