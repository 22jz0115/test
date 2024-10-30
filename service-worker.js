const CACHE_NAME = 'my-pwa-cache-v1';
const URLS_TO_CACHE = [
  '/',
  '/test/manifest.json',
  '/test/service-worker.js',
    // JSPページ
  '/WEB-INF/jsp/home.jsp',
  '/test/taskInput.jsp',
  '/test/result.jsp',
  '/test/addedLife.jsp',
  '/test/category.jsp',
  '/test/collection.jsp',
  '/test/lifeHack.jsp',
  '/WEB-INF/jsp/login.jsp',
  '/test/presetList.jsp',
  '/test/priset.jsp',
  '/test/result.jsp',
  '/test/setting.jsp',
  '/WEB-INF/jsp/shinki.jsp',
  '/test/taskHistory.jsp',
  '/test/task.jsp',

  // CSSファイル
  '/test/assets/css/style.css',
  '/test/assets/css/an/style.css',

  // JavaScriptファイル
  '/test/assets/js/home/script.js',
  '/test/assets/js/result/script.js',
  '/test/assets/js/task/script.js',
  '/test/assets/js/タスク入力画面/taskadd.js',
  '/test/assets/js/ライフハック追加画面/lifehack.js',

  // 画像ファイル
  '/test/assets/img/22462108.png',
  '/test/assets/img/25630113.png',
  '/test/assets/img/bluethooth.png',
  '/test/assets/img/camera.png',
  '/test/assets/img/cat2.png',
  '/test/assets/img/kkrn_icon_oshirase_4.png',
  '/test/assets/img/mail.png',
  '/test/assets/img/mingcute_delete-2-line.png',
  '/test/assets/img/mushimegane_search_icon_1021.png',
  '/test/assets/img/nekokawaiii.png',
  '/test/assets/img/unnamed.png',
  '/test/assets/img/Vector.png',
  '/test/assets/img/ダウンロード.jpg',
  '/test/assets/img/パスワード.png',
  '/test/assets/img/メダル.webp',
  '/test/assets/img/戻るボタン.png',
  '/test/assets/img/通知アイコン.png',
  '/test/assets/img/金メダル.png',

  // アイコン画像（PWA用に追加）
  '/assets/img/icon-192x192.png',
  '/assets/img/icon-512x512.png'
];

// インストール時にリソースをキャッシュ
self.addEventListener('install', (event) => {
    event.waitUntil(
        caches.open(CACHE_NAME).then((cache) => {
            return cache.addAll(URLS_TO_CACHE);
        })
    );
});

// フェッチイベント時にキャッシュをチェック
self.addEventListener('fetch', (event) => {
    event.respondWith(
        caches.match(event.request).then((response) => {
            // キャッシュが存在する場合はそれを返す
            return response || fetch(event.request);
        })
    );
});

// 更新時に古いキャッシュを削除
self.addEventListener('activate', (event) => {
    const cacheWhitelist = [CACHE_NAME];
    event.waitUntil(
        caches.keys().then((cacheNames) => {
            return Promise.all(
                cacheNames.map((cacheName) => {
                    if (cacheWhitelist.indexOf(cacheName) === -1) {
                        return caches.delete(cacheName);
                    }
                })
            );
        })
    );
});

self.addEventListener('push', function(event) {
    const notificationData = event.data ? event.data.text() : '新しい通知があります';

    // クライアントに通知内容を送信
    event.waitUntil(
        clients.matchAll({ type: 'window' }).then(clientList => {
            if (clientList.length > 0) {
                clientList[0].postMessage(notificationData);
            }
        })
    );

    // 通知を表示
    event.waitUntil(
        self.registration.showNotification("プッシュ通知", {
            body: notificationData,
            icon: "/test/assets/img/icon-192x192.png",
        })
    );
});
