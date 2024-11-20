<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="assets/css/style.css">
    <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
    <script type="text/javascript" src="assets/js/preset/preset.js"></script>
    <title>プリセット</title>
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

        // プリセット名を選択したときにタスクリストを更新する関数
        function updateTaskList() {
            const presetName = document.querySelector('[name="preset_name"]').value;

            // プリセットが見つかった場合にタスクリストを表示
            if (${presetList}) {
                const taskList = document.getElementById('taskList');
                taskList.innerHTML = ''; // 既存のリストをクリア

                // タスクデータをリストに追加
                presetData.tasks.forEach(task => {
                    const taskItem = document.createElement('li');
                    taskItem.classList.add('task');

                    const taskContent = `
                        <div class="timersolid">
                            <p class="tasktime">${task.taskTime}</p>
                            <span></span>
                        </div>
                        <p>${task.name}</p>
                        <input type="reset" value="-" onclick="removeTask(this)">
                    `;
                    taskItem.innerHTML = taskContent;
                    taskList.appendChild(taskItem);
                });
            } else {
                const taskList = document.getElementById('taskList');
                taskList.innerHTML = '<li>プリセットが選択されていません。</li>';
            }
        }

        // ページ読み込み時に初期化
        $(document).ready(function() {
            // 初期状態でタスクリストを更新
            updateTaskList();
        });

        function inputChange() {
        	$('[name="preset_name"]').change(updateTaskList);
            console.log("come");
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
            <!-- プリセット名を選択するための入力欄 -->
            <input type="text" name="preset_name" value="テスト" data-options='${jsonString}' class="free_dropdown" onchange="inputChange()" />
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
            <ul id="taskList">
                <!-- タスクリストはJavaScriptで動的に表示されます -->
            </ul>
        </div>

        <input type="submit" value="追加">
    </form>
</body>
</html>
