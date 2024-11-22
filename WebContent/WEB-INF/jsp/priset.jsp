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
    
    $(document).ready(function() {
        // preset_nameの変更イベントを監視
        $('[name="preset_name"]').change(function() {
            // preset_nameを取得
            const presetName = $(this).val();
            
            // サーバーにAJAXリクエストを送信
            $.ajax({
                url: '/test/GetPresetData',  // サーバーのURL（このURLに合わせてサーバー側でエンドポイントを作成）
                type: 'GET',
                data: { name: presetName }, // preset_nameをパラメータとして送信
                success: function(response) {
                    // 成功時にtaskListを更新
                    updateTaskList(response);  // サーバーから受け取ったプリセットデータを引数として渡す
                },
                error: function() {
                    alert('プリセットを取得できませんでした。');
                }
            });
        });
    });

    // taskListを更新する関数
    function updateTaskList(presetData) {
        const taskList = $('#taskList');
        taskList.empty();  // 既存のタスクリストをクリア
        console.log(presetData);

        // サーバーから受け取ったプリセットのタスクをリストに追加
        if (presetData && presetData.tasks && presetData.tasks.length > 0) {
            presetData.tasks.forEach(function(task) {
                const taskItem = `<li class="task">
                                    <div class="timersolid">
                                        <p class="tasktime">${task.taskTime}</p>
                                    </div>
                                    <p>${task.name}</p>
                                    <input type="reset" value="-" onclick="removeTask(this)">
                                  </li>`;
                taskList.append(taskItem);
            });
        } else {
            taskList.append('<li>選択されたプリセットにはタスクがありません。</li>');
        }
    }

    // リセットボタンがクリックされたときにその親のliを削除する関数
    function removeTask(button) {
        const taskItem = button.closest('li');
        if (taskItem) {
            taskItem.remove();
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
        
        <input type="submit" value="プリセットタスク追加">
    </form>

    <form action="Preset" method="post" class="presetsForm">
    	<div class="setName">
            <h2>プリセット名</h2>
            <select id="presetName" name="preset_name">
            	<option  hidden selected="selected" value="">プリセットを選択してください</option>
            	<c:forEach var="preset" items="${presetList}">
			            <option id="preset" value="${preset.id}">${preset.presetName}</option> 
			    </c:forEach>
            </select>
        </div>
        
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
