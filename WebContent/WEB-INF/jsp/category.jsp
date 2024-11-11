<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="assets/css/an/style.css">
    <title>カテゴリー画面</title>

    <link rel="shortcut icon" href="assets/img/icon-192x192.png" type="image/png">
    <link rel="manifest" href="manifest.json">

</head>
<body>
   <header>
        <a href="Home" class="back1"><img src="assets/img/戻るボタン.png" alt="戻るボタン"></a>
        <h1>カテゴリー</h1>
    </header>


    <div class="categories">
        <div id="indoorContent" class="content active">
        	<c:forEach var="category" items="${categoryList}">
            	<p id="task-1" class="deletable">${category.categoryName}</p> 
            </c:forEach>
        </div>       
    </div>
    
 
</body>
</html>
