<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="assets/css/an/style.css">
    <title>設定画面</title>
    <link rel="shortcut icon" href="assets/img/icon-192x192.png" type="image/png">
    <link rel="manifest" href="manifest.json">
   	<script>
	 	// サービスワーカー登録とPush通知の購読
	   	function subscribeUserToPush() {
	   	    if ('serviceWorker' in navigator && 'PushManager' in window) {
	   	        // サービスワーカーを登録
	   	        navigator.serviceWorker.register('/test/service-worker.js')
	   	            .then(function (registration) {
	   	                console.log('サービスワーカーが登録されました:', registration);
	   	                // Push通知の購読関数を呼び出し
	   	                subscribeToPushNotifications(registration);
	   	            })
	   	            .catch(function (error) {
	   	                console.error('サービスワーカーの登録に失敗しました:', error);
	   	            });
	   	    } else {
	   	        console.warn('このブラウザはサービスワーカーまたはPush通知をサポートしていません');
	   	    }
	   	}
	
	   	// Push通知の購読
	   	function subscribeToPushNotifications(registration) {
	   	    registration.pushManager.subscribe({
	   	        userVisibleOnly: true, // 通知がユーザーに表示されることを保証
	   	        applicationServerKey: urlBase64ToUint8Array('BDkzLFhl0ZDx2ho-Snk_ZIITgZjBfXGunbNgycezdritqHIuESNIJkjmG_-LFM5ikVVlRZBmWk1m9uJzRRkZ19Y')
	   	    })
	   	    .then(function (subscription) {
	   	        console.log('Push通知の購読に成功しました:', subscription);
	   	  		// サーバーに登録情報を送信
	            fetch('/test/PushServlet', {
	                method: 'POST',
	                headers: { 'Content-Type': 'application/json' },
	                body: JSON.stringify(subscription)
	            });
	   	    })
	   	    .catch(function (error) {
	   	        console.error('Push通知の購読に失敗しました:', error);
	   	    });
	   	}
	
	   	// VAPID公開鍵をUint8Arrayに変換する関数
	   	function urlBase64ToUint8Array(base64String) {
	   	    const padding = '='.repeat((4 - base64String.length % 4) % 4);
	   	    const base64 = (base64String + padding).replace(/-/g, '+').replace(/_/g, '/');
	   	    const rawData = window.atob(base64);
	   	    const outputArray = new Uint8Array(rawData.length);
	   	    for (let i = 0; i < rawData.length; ++i) {
	   	        outputArray[i] = rawData.charCodeAt(i);
	   	    }
	   	    return outputArray;
	   	}
	</script>
</head>
<body>

   <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="assets/js/setting/setting.js"></script>
    <header id="header">
        <a href="Home" class="back1"><img src="assets/img/戻るボタン.png" alt="戻る"></a>
        <h1>設定</h1>
    </header>
    <div class="switch">   
        <div class="notification">
            <img src="assets/img/通知アイコン.png" alt="通知アイコン">
            <p>通知</p>
            <label for="switch1" class="switch_label">  
                <p>OFF</p>
                <div class="switch">
                    <input type="checkbox" id="switch1" onclick="subscribeUserToPush()"/>
                    <div class="circle"></div>
                    <div class="base"></div>
                </div>
                <span class="title">ON</span>
            </label>
        </div>
   </div>
    <p class="setting">背景設定</p>
<!-- 背景色変更のためのフォーム -->
<div class="selection">
    <input id="bgcolor" type="color" />
    <button id="bgColorBtn" type="button" class="button2">決定</button>
</div>

<!-- 通知メッセージの要素 -->
<div id="notification" class="notification-message" style="display: none;">
    変更できました
</div>

<p class="setting">文字色変更</p>
<!-- 文字色変更のためのフォーム -->
<div class="selection">
    <input id="textcolor" type="color" />
    <button id="textColorBtn" type="button" class="button2">決定</button>
</div>
    <p class="setting">ヘッダーの色変更</p>
    <!-- ヘッダー背景色変更のためのフォーム -->
    <div class="selection">
        <input id="headercolor" type="color" />
        <button id="headerColorBtn" type="button" class="button2">決定</button>
    </div>
    <p class="setting">ログアウト</p>
    <div class="button-1">
        <form action="Login" method="post">
            <button type="submit" class="logout-button">ログアウト</button>
        </form>
    </div>


</body>
</html>