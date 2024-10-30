<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="assets/css/an/style.css">
    <title>新規登録ページ</title>
    <link rel="shortcut icon" href="assets/img/icon-192x192.png" type="image/png">
    <link rel="manifest" href="manifest.json">
</head>

<body>

    <div class="rogin-form">
        <form action="CreateUser" method="post"> <!-- サーブレットにPOSTリクエストを送る -->
            <div class="background">
                <h1 class="shinki00">新規登録</h1>
            </div>
            <h2>sign in to your account</h2>
            <div class="form-1">
                <div class="login">
                    <p>ニックネーム
                        <input type="text" id="nickname" placeholder="ニックネーム" name="nickname" required>
                        <div class="text_underline"></div><br>
                    </p>
                    <p>メールアドレス
                        <input type="email" id="email" placeholder="メールアドレス" name="email" required>
                        <div class="text_underline"></div><br>
                    </p>
                    <p>パスワード
                        <input type="password" id="password" placeholder="パスワード" name="password" minlength="4" maxlength="20" required>
                        <div class="text_underline"></div>
                    </p>
                    <br>
                    <p>都道府県：
                        <select name="prefecture" required>
                            <option value="016000">北海道</option>
                            <option value="020000">青森県</option>
                            <option value="030000">岩手県</option>
                            <option value="040000">宮城県</option>
                            <option value="050000">秋田県</option>
                            <option value="060000">山形県</option>
                            <option value="070000">福島県</option>
                            <option value="080000">茨城県</option>
                            <option value="090000">栃木県</option>
                            <option value="100000">群馬県</option>
                            <option value="110000">埼玉県</option>
                            <option value="120000">千葉県</option>
                            <option value="130000">東京都</option>
                            <option value="140000">神奈川県</option>
                            <option value="150000">新潟県</option>
                            <option value="160000">富山県</option>
                            <option value="170000">石川県</option>
                            <option value="180000">福井県</option>
                            <option value="190000">山梨県</option>
                            <option value="200000">長野県</option>
                            <option value="210000">岐阜県</option>
                            <option value="220000">静岡県</option>
                            <option value="230000">愛知県</option>
                            <option value="240000">三重県</option>
                            <option value="250000">滋賀県</option>
                            <option value="260000">京都府</option>
                            <option value="270000">大阪府</option>
                            <option value="280000">兵庫県</option>
                            <option value="290000">奈良県</option>
                            <option value="300000">和歌山県</option>
                            <option value="310000">鳥取県</option>
                            <option value="320000">島根県</option>
                            <option value="330000">岡山県</option>
                            <option value="340000">広島県</option>
                            <option value="350000">山口県</option>
                            <option value="360000">徳島県</option>
                            <option value="370000">香川県</option>
                            <option value="380000">愛媛県</option>
                            <option value="390000">高知県</option>
                            <option value="400000">福岡県</option>
                            <option value="410000">佐賀県</option>
                            <option value="420000">長崎県</option>
                            <option value="430000">熊本県</option>
                            <option value="440000">大分県</option>
                            <option value="450000">宮崎県</option>
                            <option value="460100">鹿児島県</option>
                            <option value="471000">沖縄県</option>
                        </select>
                    </p>
                    <br>
                </div>
            </div>
            <div class="sinki_button">
                <!-- 登録ボタン -->
                <button  id="touroku" type="submit" class="button-0">はじめる</button>
            </div>
        </form>
        
        <div class="migi">
            <!-- ログインページへのリンク -->
            <p class="sinki">
                <a href="Login" class="sinki"><span id="sinki">ログイン画面へ</span></a>
            </p>
        </div>
    </div>

</body>

</html>