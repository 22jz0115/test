<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ja">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="assets/css/an/style.css">
    <title>ログインページ</title>
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
    <div class="rogin-form">
        <form action="Home" method="post"> <!-- サーブレットにPOSTリクエストを送る -->
            <div class="background">
                <h1 class="rogin1">ログイン</h1>
            </div>
            <h2>sign in to your account</h2>
            
            
            
            <div class="ground-1">
                <div class="form-1">
                    <div class="login">
				  <p class="form-item">
				  <img src="assets/img/mail.png" alt="mail" class="icon">
				  <input type="text" class="loginemail"id="loginemail" placeholder="メールアドレス" name="email" required>
				  <div class="text_underline"></div>
				</p>
				<br>
				<p class="form-item">
				  <img src="assets/img/パスワード.png" alt="パスワード" class="icon">
				  <input type="password" id="loginpassword" class="loginpass" placeholder="パスワード" name="password" minlength="4" maxlength="20" required>
				  <div class="text_underline"></div>
				</p>
				<br>
				</div>
				
				   <!-- エラーメッセージを表示 -->
            <c:if test="${not empty errorMessage}">
                <p class="error-message">${errorMessage}</p>
            </c:if>
				
                <div class="migi0">
                    <button 
				        id="touroku" 
				        type="submit" 
				        class="button00" 
				        >
				        Login
				    </button>
                    <p class="sinki">
                        <a href="CreateUser" class="sinki"><span id="sinki">新規登録</span></a>
                    </p>
                </div>
            </div>
        </form>
    </div>
</body>

</html>