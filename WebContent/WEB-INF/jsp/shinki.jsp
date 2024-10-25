<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="assets/css/an/style.css">
    <title>新規登録ページ</title>
</head>

<body>

    <div class="rogin-form">
        <form action="register" method="post"> <!-- サーブレットにPOSTリクエストを送る -->
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
                            <option value="hokkaido">北海道</option>
                            <option value="aomori">青森県</option>
                            <option value="iwate">岩手県</option>
                            <option value="miyagi">宮城県</option>
                            <option value="akita">秋田県</option>
                            <option value="yamagata">山形県</option>
                            <option value="fukushima">福島県</option>
                            <option value="ibaraki">茨城県</option>
                            <option value="tochigi">栃木県</option>
                            <option value="gunma">群馬県</option>
                            <option value="saitama">埼玉県</option>
                            <option value="chiba">千葉県</option>
                            <option value="tokyo">東京都</option>
                            <option value="kanagawa">神奈川県</option>
                            <option value="niigata">新潟県</option>
                            <option value="toyama">富山県</option>
                            <option value="ishikawa">石川県</option>
                            <option value="fukui">福井県</option>
                            <option value="yamanashi">山梨県</option>
                            <option value="nagano">長野県</option>
                            <option value="gifu">岐阜県</option>
                            <option value="shizuoka">静岡県</option>
                            <option value="aichi">愛知県</option>
                            <option value="mie">三重県</option>
                            <option value="shiga">滋賀県</option>
                            <option value="kyoto">京都府</option>
                            <option value="osaka">大阪府</option>
                            <option value="hyogo">兵庫県</option>
                            <option value="nara">奈良県</option>
                            <option value="wakayama">和歌山県</option>
                            <option value="tottori">鳥取県</option>
                            <option value="shimane">島根県</option>
                            <option value="okayama">岡山県</option>
                            <option value="hiroshima">広島県</option>
                            <option value="yamaguchi">山口県</option>
                            <option value="tokushima">徳島県</option>
                            <option value="kagawa">香川県</option>
                            <option value="ehime">愛媛県</option>
                            <option value="kochi">高知県</option>
                            <option value="fukuoka">福岡県</option>
                            <option value="saga">佐賀県</option>
                            <option value="nagasaki">長崎県</option>
                            <option value="kumamoto">熊本県</option>
                            <option value="oita">大分県</option>
                            <option value="miyazaki">宮崎県</option>
                            <option value="kagoshima">鹿児島県</option>
                            <option value="okinawa">沖縄県</option>
                        </select>
                    </p>
                    <br>
                </div>
            </div>
        <form action="src/servlet/" method="GET">
            <div class="sinki_button">
                <!-- 登録ボタン -->
                <button  id="touroku" type="submit" class="button-0">はじめる</button>
            </div>
        </form>
        
        <div class="migi">
            <!-- ログインページへのリンク -->
            <p class="sinki">
                <a href="../ログイン画面/index.html" class="sinki"><span id="sinki">ログイン画面へ</span></a>
            </p>
        </div>
    </div>

</body>

</html>