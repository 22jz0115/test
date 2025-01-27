<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="assets/css/an/style.css">
    <title>設定画面</title>
    <link rel="shortcut icon" href="assets/img/icon-192x192.png" type="image/png">
    <link rel="manifest" href="manifest.json">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="assets/js/nav/script.js"></script>
   	<script>
    if ('serviceWorker' in navigator) {
        window.addEventListener('DOMContentLoaded', () => {
            navigator.serviceWorker.register('/test/service-worker.js').then((registration) => {
                console.log('Service Worker registered with scope:', registration.scope);

                const switch1 = document.getElementById('switch1');
                if (switch1) {
                    switch1.addEventListener('change', async function() {
                        if (switch1.checked) {
                            await subscribeUser(registration);
                        } else {
                            await unsubscribeUser(registration);
                        }
                    });
                } else {
                    console.error('スイッチが見つかりません');
                }

                // Push通知の購読
                async function subscribeUser(registration) {
                    const subscription = await registration.pushManager.getSubscription();
                    if (subscription) {
                        console.log('既に購読されています:', subscription);
                        return;
                    }

                    try {
                        // 鍵を生成
                        const { publicKey, authKey } = await generatePushKeys();

                        // 新たにPush通知の購読を作成
                        const newSubscription = await registration.pushManager.subscribe({
                            userVisibleOnly: true,
                            applicationServerKey: urlBase64ToUint8Array(publicKey),
                        });

                        console.log('Push通知の購読に成功:', newSubscription);

                        // サーバーに送信
                        sendSubscriptionToServer({
                            endpoint: newSubscription.endpoint,
                            keys: {
                                auth: authKey,
                                p256dh: publicKey,
                            },
                        });
                    } catch (error) {
                        console.error('Push通知の購読に失敗:', error);
                        alert('Push通知の購読に失敗しました。後で再試行してください。');
                    }
                }

                // Push通知の解除
                async function unsubscribeUser(registration) {
                    const subscription = await registration.pushManager.getSubscription();
                    if (!subscription) {
                        console.log('現在、Push通知は購読されていません。');
                        return;
                    }

                    try {
                        await subscription.unsubscribe();
                        console.log('Push通知を解除しました');
                        sendUnsubscriptionToServer();
                    } catch (error) {
                        console.error('Push通知の解除に失敗:', error);
                    }
                }

                // サーバーに購読情報を送信
                function sendSubscriptionToServer(subscriptionData) {
                    fetch('/test/PushServlet', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                        },
                        body: JSON.stringify(subscriptionData),
                    })
                        .then((response) => {
                            if (response.ok) {
                                console.log('購読情報がサーバーに送信されました');
                                sendButtonToServer();
                            } else {
                                console.error('購読情報の送信に失敗しました');
                            }
                        })
                        .catch((error) => {
                            console.error('送信エラー:', error);
                        });
                }

            	 // サーバーにボタン変化を送信
                function sendButtonToServer() {
                    fetch('/test/ChangeButton', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded',
                        },
                        body: `message=1`
                    })
                        .then((response) => {
                            if (response.ok) {
                                console.log('ボタン情報がサーバーに送信されました');
                            } else {
                                console.error('ボタン情報の送信に失敗しました');
                            }
                        })
                        .catch((error) => {
                            console.error('ボタン情報送信エラー:', error);
                        });
                }

                // サーバーに解除情報を送信
                function sendUnsubscriptionToServer() {
                    fetch('/test/Unsubscribe', {
                        method: 'POST',
                        headers: { 'Content-Type': 'application/json' },
                    })
                        .then((response) => {
                            if (response.ok) {
                                console.log('解除情報がサーバーに送信されました');
                                sendUnbuttonToServer();
                            } else {
                                console.error('解除情報の送信に失敗しました');
                            }
                        })
                        .catch((error) => {
                            console.error('解除情報送信エラー:', error);
                        });
                }

                // サーバーにボタン変化を送信
                function sendUnbuttonToServer() {
                    fetch('/test/ChangeUnbutton', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded',
                        },
                        body: `message=0`
                    })
                        .then((response) => {
                            if (response.ok) {
                                console.log('ボタン解除情報がサーバーに送信されました');
                            } else {
                                console.error('ボタン解除情報の送信に失敗しました');
                            }
                        })
                        .catch((error) => {
                            console.error('ボタン解除情報送信エラー:', error);
                        });
                }

                // 鍵生成
                async function generatePushKeys() {
                    // P-256（prime256v1）楕円曲線鍵ペアを生成
                    const keyPair = await window.crypto.subtle.generateKey(
                        {
                            name: 'ECDH',
                            namedCurve: 'P-256',
                        },
                        true,
                        ['deriveKey', 'deriveBits']
                    );

                    // 公開鍵をBase64エンコード
                    const publicKey = await exportCryptoKey(keyPair.publicKey);

                    // 認証キー（16バイトランダム値）を生成
                    const authArray = new Uint8Array(16);
                    window.crypto.getRandomValues(authArray);
                    const authKey = btoa(String.fromCharCode(...authArray));

                    console.log('生成された鍵:', { publicKey, authKey });
                    return { publicKey, authKey };
                }

                // 公開鍵をBase64形式にエクスポート
                async function exportCryptoKey(key) {
                    const exportedKey = await window.crypto.subtle.exportKey('raw', key);
                    const uint8Array = new Uint8Array(exportedKey);
                    return btoa(String.fromCharCode(...uint8Array));
                }

                // Base64をUint8Arrayに変換
                function urlBase64ToUint8Array(base64String) {
                    const padding = '='.repeat((4 - (base64String.length % 4)) % 4);
                    const base64 = (base64String + padding).replace(/-/g, '+').replace(/_/g, '/');
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
        <div class="openbtn1"><span></span><span></span><span></span></div>
	    <nav id="g-nav">
	      <ul>
	      	<li> <a class="homebutton" href=Home>ホーム</a></li>
	      	<li> <a class="todaybutton" href=Task>今日のタスク</a></li>
	        <li> <a class="link-button" href="Category">タスク履歴</a></li>
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
    <p class="setting">通知</p>
    <div class="switch">   
        <div class="notification">
            <img src="assets/img/通知アイコン.png" alt="通知アイコン">
            <label for="switch1" class="switch_label">  
                <p>OFF</p>
                <div class="switch">
                    <input type="checkbox" id="switch1" <c:if test="${option.message == 1}">checked</c:if>>
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
        <form action="Logout" method="post">
            <button type="submit" class="logout-button">ログアウト</button>
        </form>
    </div>


</body>
</html>