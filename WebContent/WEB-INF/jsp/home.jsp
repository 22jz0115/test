<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="assets/css/style.css">
    <title>Weather Information</title>
</head>
<body>
    <header>
        <a href="#"><img class="imgVector" src="assets/js/home/script.js" alt=""></a>
        <h1>ホーム</h1>
    </header>
    <div class="calendar-wrap">
        <table class="calendar"><caption></caption></table>
    </div>

    <h2 class="notice">お知らせ</h2>
    <div class="noticeMsg">
        <p>
            <%= request.getAttribute("weatherDescription") != null ? request.getAttribute("weatherDescription") : "天気情報が取得できませんでした。" %>
        </p>
    </div>

    <div class="flex-other">
        <div class="columntaskbt">
            <div class="shorttaskmaneger">
                <ul>
                    <li>8:00 朝ごはん</li>
                    <li>8:30 外出</li>
                </ul>
            </div>
            <div class="buttons">
                <a class="starbutton" href=""><img src="assets/img/unnamed.png" alt=""></a>
                <a class="bellbutton" href=""><img src="assets/img/kkrn_icon_oshirase_4.png" alt=""></a>
                <a class="ratebutton" href="Result"><img src="assets/img/22462108.png" alt=""></a>
            </div>
        </div>
        <div class="other-button">
            <a class="link-button" href=""><p>カテゴリー</p></a>
            <a class="link-button" href="Priset"><p>プリセット</p></a>
            <a class="link-button" href="LifeHack"><p>ライフハック</p></a>
            <a class="todaybutton" href=Task"><p>今日のタスク</p></a>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="assets/js/home/script.js"></script>
</body>
</html>
