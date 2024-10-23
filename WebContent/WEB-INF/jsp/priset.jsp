<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="assets/css/style.css">
    <title>Document</title>
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

    <form action="Task" method="post" class="presetsForm">
        <div class="setName">
            <h2>プリセット名</h2>
            <input list="presets" placeholder="選択または入力してください">
            <datalist id="presets">
                <option value="プリセット1">
                <option value="プリセット2">
                <option value="プリセット3">
                <option value="プリセット4">
            </datalist>
        </div>

        <a href="Task">
            <img class="taskimg" src="assets/img/25630113.png" alt="">
        </a>

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

        <input type="submit" value="保存">
    </form>

</body>
</html>
