<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ユーザ新規登録 | J'z VoD</title>
    <link rel="shortcut icon" href="icon.png" type="image/png">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="manifest" href="manifest.json">
</head>
<body id="body-createUser">
    <main class="login-main">
        <c:if test="${not empty msg}">
            <%-- メッセージが存在するときのみ表示 --%>
            <p>${msg}</p>
        </c:if>
        <section>
            <h1>ユーザ登録</h1>
            <form action="CreateUser" method="POST" class="login-form">
                <dl>
                	<div class="form-item">
                        <dt><label for="login-name">名前</label></dt>
                        <dd><input type="text" name="name" id="login-name" required></dd>
                    </div>
                    <div class="form-item">
                        <dt><label for="login-email">メールアドレス</label></dt>
                        <dd><input type="email" name="email" id="login-email" required></dd>
                    </div>
                    <div class="form-item">
                        <dt><label for="login-pass">パスワード</label></dt>
                        <dd><input type="password" name="pass" id="login-pass" required></dd>
                    </div>
                    <div class="form-item">
                        <dt><label for="login-location">住所</label></dt>
                        <dd><input type="text" name="location" id="login-location" required></dd>
                    </div>
                </dl>
                <button class="btn">新規登録</button>
            </form>
        </section>
    </main>
</body>
</html>