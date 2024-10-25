<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
 <link rel="stylesheet" href="assets/css/an/style.css">
    <title>ご褒美画面</title>
</head>

<body class="body-1">

    <header>
        <a href="../ホーム画面/index.html"><img src="assets/img/戻るボタン.png" alt="戻るボタン"></a><h1>Collection</h1>
    </header>

    <p class="years">現在の年は <span id="year"><%= new java.util.Date().getYear() + 1900 %></span>年です。</p>

    <div class="grid-container">
        <img src="assets/img/cat2.png" alt="金メダル" class="round-image">
        <div class="dotted-circle"><p>2月</p></div>
        <div class="dotted-circle"><p>3月</p></div>
        <div class="dotted-circle"><p>4月</p></div>
        <div class="dotted-circle"><p>5月</p></div>
        <div class="dotted-circle"><p>6月</p></div>
        <div class="dotted-circle"><p>7月</p></div>
        <div class="dotted-circle"><p>8月</p></div>
        <div class="dotted-circle"><p>9月</p></div>
        <div class="dotted-circle-1"><p>10月</p></div>
        <div class="dotted-circle-1"><p>11月</p></div>
        <div class="dotted-circle-1"><p>12月</p></div>
    </div>
</body>
</html>