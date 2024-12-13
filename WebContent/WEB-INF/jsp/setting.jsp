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
	
	                const switch1 = document.getElementById('switch1');
	                if (switch1) {
	                    switch1.addEventListener('change', function() {
	                        if (switch1.checked) {
	                            subscribeUser(registration);
	                        } else {
	                            unsubscribeUser(registration);
	                        }
	                    });
	                } else {
	                    console.error('スイッチが見つかりません');
	                }
	
	                // Push通知の購読
	                function subscribeUser(registration) {
	                    registration.pushManager.getSubscription().then(function(subscription) {
	                        if (subscription) {
	                            console.log('既に購読されています');
	                            return;
	                        }
	
	                        // 新たにPush通知の購読を作成
	                        registration.pushManager.subscribe({
	                            userVisibleOnly: true,
	                            applicationServerKey: urlBase64ToUint8Array('BBNgWYrBUGNBxLIb5IOUufjXNNkP-NWOwyt7k4QFxRxQfkZWKzBwsRwx_NnbNEyJLXeTOHnbXagsT-e_7wmkmMo')
	                        }).then(function(newSubscription) {
	                            console.log('Push通知の購読に成功:', newSubscription);
	                            sendSubscriptionToServer(newSubscription);
	                        }).catch(function(error) {
	                            console.error('Push通知の購読に失敗:', error);
	                            alert('Push通知の購読に失敗しました。後で再試行してください。');
	                        });
	                    }).catch(function(error) {
	                        console.error('Push通知のサブスクリプション取得エラー:', error);
	                    });
	                }
	
	                // Push通知の解除
	                function unsubscribeUser(registration) {
	                    registration.pushManager.getSubscription().then(function(subscription) {
	                        if (!subscription) {
	                            console.log('現在、Push通知は購読されていません。');
	                            return;
	                        }
	
	                        subscription.unsubscribe().then(function() {
	                            console.log('Push通知を解除しました');
	                            sendUnsubscriptionToServer();
	                        }).catch(function(error) {
	                            console.error('Push通知の解除に失敗:', error);
	                        });
	                    }).catch(function(error) {
	                        console.error('Push通知の解除情報取得エラー:', error);
	                    });
	                }
	
	                // サーバーに購読情報を送信
	                function sendSubscriptionToServer(subscription) {
	                    console.log(subscription);
	
	                    if (!subscription.keys || !subscription.keys.auth || !subscription.keys.p256dh) {
	                        console.error('Pushサブスクリプションの鍵情報が不足しています');
	                        return;
	                    }
	
	                    const subscriptionData = {
	                        endpoint: subscription.endpoint,
	                        keys: {
	                            auth: subscription.keys.auth,
	                            p256dh: subscription.keys.p256dh
	                        }
	                    };
	
	                    fetch('/test/PushServlet', {
	                        method: 'POST',
	                        headers: {
	                            'Content-Type': 'application/json',
	                        },
	                        body: JSON.stringify(subscriptionData)
	                    })
	                    .then(response => {
	                        if (response.ok) {
	                            console.log('購読情報がサーバーに送信されました');
	                        } else {
	                            console.error('購読情報の送信に失敗しました');
	                        }
	                    })
	                    .catch(error => {
	                        console.error('送信エラー:', error);
	                    });
	                }
	
	                // サーバーに解除情報を送信
	                function sendUnsubscriptionToServer() {
	                    fetch('/test/Unsubscribe', {
	                        method: 'POST',
	                        headers: { 'Content-Type': 'application/json' }
	                    })
	                    .then(response => {
	                        if (response.ok) {
	                            console.log('解除情報がサーバーに送信されました');
	                        } else {
	                            console.error('解除情報の送信に失敗しました');
	                        }
	                    })
	                    .catch(error => {
	                        console.error('解除情報送信エラー:', error);
	                    });
	                }
	
	                // Base64をUint8Arrayに変換
	                function urlBase64ToUint8Array(base64String) {
	                    const padding = '='.repeat((4 - base64String.length % 4) % 4);
	                    const base64 = (base64String + padding).replace(/\-/g, '+').replace(/_/g, '/');
	                    const rawData = window.atob(base64);
	                    const outputArray = new Uint8Array(rawData.length);
	                    for (let i = 0; i < rawData.length; ++i) {
	                        outputArray[i] = rawData.charCodeAt(i);
	                    }
	                    return outputArray;
	                }
	
	            }).catch((error) => {
	                console.error('Service Worker registration failed:', error);
	            });
	        });
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
                    <input type="checkbox" id="switch1"/>
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