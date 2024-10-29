<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="assets/css/an/style.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script type="text/javascript" src="jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="../js/ライフハック追加画面/lifehack.js"></script>
    <title>ライフハック追加画面</title>
    <link rel="shortcut icon" href="assets/img/icon-192x192.png" type="image/png">
    <link rel="manifest" href="manifest.json">
</head>
<body>
    <header>
        <a href="LifeHack" class="back1"><img src="assets/img/戻るボタン.png" alt="戻るボタン"></a>
        <h1>ライフハック追加</h1>
    </header>

    <div class="background-1">
        <form action="<%= request.getContextPath() %>/uploadLifeHackServlet" method="post" enctype="multipart/form-data">
            <div class="class1">
                <label for="name">タイトル</label>
                <input type="text" id="name" name="name" required minlength="10" maxlength="50" size="30">
            </div>

            <!-- 写真を挿入する -->
            <div class="content">
                <div class="name">
                    <label for="comment">内容</label>
                </div>
                <textarea name="comment" id="comment" rows="13" cols="40" placeholder="内容を入力してください" required></textarea>
                
                <label for="example">写真を選択:</label>
                <input type="file" name="example" accept="image/*" multiple onchange="previewFiles(this);">
            </div>

            <div id="preview"></div>

            <div class="input">
                <button type="submit" class="submit-button">投稿する</button>
            </div>
        </form>
    </div>

    <script>
        // 画像プレビュー用のJavaScript
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
                    img.style.width = '100px'; // プレビュー画像のサイズを調整
                    img.style.margin = '5px';
                    preview.appendChild(img);
                };
                
                reader.readAsDataURL(file);
            }
        }
    </script>
</body>
</html>