<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="assets/css/an/style.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="assets/js/home/script.js"></script>
    <title>ライフハック変更</title>
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
    <header>
        <a href="LifeHack" class="back1"><img src="assets/img/戻るボタン.png" alt="戻るボタン"></a>
        <h1>ライフハック変更</h1>
    </header>
    
    <form id="lifeChangeForm" action="LifeChange" method="POST" style="margin: 0;" enctype="multipart/form-data">
        <div class="background-1">
            <input type="hidden" name="lifeId" value="${life.id}">
            
            <div class="class1">
                <label for="title">タイトル</label>
                <input type="text" id="title" name="title" required minlength="1" maxlength="50" size="30" value="${life.title}">
            </div>

            <div class="atcontent">
                <label for="comment">内容</label>
                <textarea name="comment" id="comment" rows="13" cols="40" required>${life.content}</textarea>
            </div>

            <!-- 現在の写真プレビューと削除オプション -->
            <div id="currentPhoto">
                <c:if test="${not empty life.img}">
                    <img id="previewImage" src="${life.img}" alt="現在の画像" style="width: 150px;">
                    <button type="button" id="deleteImageButton">写真を削除</button>
                </c:if>
            </div>

            <label for="file">新しい写真を選択:</label>
            <input type="file" id="file" name="file" accept="image/*" multiple onchange="previewFiles(this);">
            
            <div id="preview"></div>
        </div>
        
        <div class="button-container">
            <button type="submit" class="submit-button">変更を保存</button>
        </div>
    </form>
    
    <form action="LifeHackHistory" method="POST">
        <div class="button-container">
            <input type="hidden" name="lifeId" value="${life.id}">
            <button type="submit" class="submit-button">削除</button>
        </div>
    </form>

    <script>
        let isImageDeleted = false; // 画像が削除されたかのフラグ

        function previewFiles(input) {
            const preview = document.getElementById('preview');
            preview.innerHTML = ''; // 既存のプレビューをクリア

            const files = input.files;
            for (let i = 0; i < files.length; i++) {
                const file = files[i];
                const reader = new FileReader();
                
                reader.onload = function(e) {
                    const img = document.createElement('img');
                    img.src = e.target.result;
                    img.style.width = '150px';
                    img.style.margin = '5px';
                    preview.appendChild(img);
                };
                reader.readAsDataURL(file);
            }
        }
        
        // 現在の写真を削除
        document.getElementById('deleteImageButton')?.addEventListener('click', () => {
            const currentPhoto = document.getElementById('currentPhoto');
            const previewImage = document.getElementById('previewImage');

            if (previewImage) {
                previewImage.remove(); // プレビュー画像削除
                currentPhoto.innerHTML = '<p>現在の写真は削除されました。</p>';
                isImageDeleted = true; // 画像削除フラグをセット

                // 新たに削除された情報をフォームに追加
                const deleteImageInput = document.createElement('input');
                deleteImageInput.type = 'hidden';
                deleteImageInput.name = 'deleteImage';
                deleteImageInput.value = 'true';
                document.getElementById('lifeChangeForm').appendChild(deleteImageInput);
            }
        });

        // フォーム送信時に画像削除フラグを確認
        document.getElementById('lifeChangeForm').addEventListener('submit', function(e) {
            if (isImageDeleted) {
                // フォーム送信時に削除情報を送る
                const deleteImageInput = document.createElement('input');
                deleteImageInput.type = 'hidden';
                deleteImageInput.name = 'deleteImage';
                deleteImageInput.value = 'true';
                this.appendChild(deleteImageInput);
            }
        });
    </script>
</body>
</html>
