<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="assets/css/style.css">
    <title>Document</title>
    <link rel="shortcut icon" href="assets/img/icon-192x192.png" type="image/png">
    <link rel="manifest" href="manifest.json">
</head>
<body>
    <header>
        <a href="Home"><img class="imgVector" src="assets/img/Vector.png" alt=""></a>
        <h1>ライフハック</h1>
    </header>
    <form action="#" class="search-form-3">
        <label>
            <input type="text" placeholder="キーワードを入力">
        </label>
        <button type="submit" aria-label="検索"></button>
    </form>
    
    <p  class="lifehackAdd"><a href="AddedLife">ライフハック追加</a></p>
    
    <c:forEach var="life" items="${lifeList}">
          <div class="lifehacks">
        <div>
            <h2>${life.accountId}さん</h2>
            <h3>${life.title}</h3>
            <p>
               ${life.content}
            </p>
            <img src="${life.img}" alt="">
        </div>
    </div>    	
    
    </c:forEach>

  
</body>
</html>