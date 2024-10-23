<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="assets/css/style.css">
    <title>Document</title>
</head>
<body>
    <header>
        <a href="#"><img src="assets/img/Vector.png" alt=""></a>
        <h1>達成率</h1>
    </header>


    <h2 class="toMounthrate">今月の達成率</h2>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <script type="text/javascript" src="assets/js/result/script.js"></script>
    <script type="text/javascript">
        <!--
        //-->
    </script>

    <div>
        <p id="cmpmonth"></p>
        <div class="pie-chart-2" id="pieChart"></div>
    </div>
    <div class="cycle-flex">
        <div>
            <p>屋内タスク</p>
            <div class="pie-chart-2" id="pieChart2"></div>
        </div>
        
        <div>
            <p>屋外タスク</p>
            <div class="pie-chart-2" id="pieChart3"></div>
        </div>
    </div>
    
    
</body>

</html>