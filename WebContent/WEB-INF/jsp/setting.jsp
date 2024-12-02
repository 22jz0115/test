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
	    if ('serviceWorker' in navigator) {
	        window.addEventListener('load', () => {
	            navigator.serviceWorker.register('/test/service-worker.js').then((registration) => {
	                console.log('Service Worker registered with scope:', registration.scope);
	                subscribeToPushNotifications(registration);
	            }).catch((error) => {
	                console.error('Service Worker registration failed:', error);
	            });
	        });
	    }else {
	        console.warn('サービスワーカーまたはPushManagerがサポートされていません');
	    }

	    // VAPID公開鍵（事前にサーバー側で生成して埋め込む）
        const vapidPublicKey = 'MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlhI3eNMWKAmqQDM7tGOHjthyRD1qtGDaQyndE_45WIWKhsjoU_uaIJLptetAwSaT8APe277ZK7dAA3psGjRkb76_tcg1bTR-H3_2wTN1uGHuQNaXagjMGafC12wAayhdFkQ4HP6TsBFgycGrscvi1udZ3AC78Ot3NW3nOba1P9cdKfXyGkXEJLfp5CRy9QdBOqELLdv-McxLQFX4K_ic4MaJGVPu9xu1zkDhK4pkXlwrxYS9DHuWAB20Jf72LAQ06JqIR0Bi0WMQuYaIfaSGL-4tSJzIBorKM6buodhX4kjFB_hYNGrgiEyCziz_ZgGZPgBTbkxsWIuW1RUsMUAEwQIDAQAB';

        // Push Subscriptionの登録
        async function subscribeUserToPush() {
            const subscription = await registration.pushManager.subscribe({
                userVisibleOnly: true,
                applicationServerKey: urlBase64ToUint8Array(vapidPublicKey)
            });

            console.log('Push Subscription:', subscription);

            // サーバーに登録情報を送信
            await fetch('/PushServlet', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(subscription)
            });
        }

        // Utility: VAPID鍵の変換
        function urlBase64ToUint8Array(base64String) {
            const padding = '='.repeat((4 - base64String.length % 4) % 4);
            const base64 = (base64String + padding).replace(/-/g, '+').replace(/_/g, '/');
            const rawData = atob(base64);
            return Uint8Array.from([...rawData].map(char => char.charCodeAt(0)));
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