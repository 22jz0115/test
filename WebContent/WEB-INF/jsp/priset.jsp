<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="assets/css/style.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <script type="text/javascript" src="assets/js/preset/preset.js"></script>
    <title>Document</title>
    <link rel="shortcut icon" href="assets/img/icon-192x192.png" type="image/png">
    <link rel="manifest" href="manifest.json">
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
	<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.min.js"></script>
    <script>
        // リセットボタンがクリックされたときにその親のliを削除する関数
        function removeTask(button) {
            const taskItem = button.closest('li'); // ボタンの親li要素を取得
            if (taskItem) {
                taskItem.remove(); // li要素をDOMから削除
            }
        }
    </script>
</head>
<body>
    <header>
        <a href="Home"><img class="imgVector" src="assets/img/Vector.png" alt=""></a>
        <h1>プリセット</h1>
    </header>

    <form action="PresetInput" method="get" class="presetsForm">
        <div class="setName">
            <h2>プリセット名</h2>
            <input type="text" name="preset_name" value="" data-options='${jsonString}'  class="free_dropdown" />
        </div>
        <input type="submit" value="プリセットタスク追加">
    </form>
    
    <form action="Preset" method="post" class="presetsForm">
        <div class="form-group">
		    <label for="dateInput">日付</label>
		    <!-- 日付選択 -->
		    <input type="date" id="dateInput" name="dateInput" value="${selectedDate}">
		</div>

        <div class="presets">
            <ul>
                <li class="task">
                    <div>
                        <div class="timersolid">
                            <p class="tasktime">8:00</p>
                            <span></span>
                        </div>
                        <p>朝ごはん食べる</p>
                    </div>
                    <input type="reset" value="-" onclick="removeTask(this)"> <!-- onClickイベントを追加 -->
                </li>
                <li class="task">
                    <div>
                        <div class="timersolid">
                            <p class="tasktime">8:00</p>
                            <span></span>
                        </div>
                        <p>朝ごはん食べる</p>
                    </div>
                    <input type="reset" value="-" onclick="removeTask(this)"> <!-- onClickイベントを追加 -->
                </li>
                <li class="task">
                    <div>
                        <div class="timersolid">
                            <p class="tasktime">8:00</p>
                            <span></span>
                        </div>
                        <p>朝ごはん食べる</p>
                    </div>
                    <input type="reset" value="-" onclick="removeTask(this)"> <!-- onClickイベントを追加 -->
                </li>
                <li class="task">
                    <div>
                        <div class="timersolid">
                            <p class="tasktime">8:00</p>
                            <span></span>
                        </div>
                        <p>朝ごはん食べる</p>
                    </div>
                    <input type="reset" value="-" onclick="removeTask(this)"> <!-- onClickイベントを追加 -->
                </li>
            </ul>
        </div>

        <input type="submit" value="追加">
    </form>
</body>
</html>
