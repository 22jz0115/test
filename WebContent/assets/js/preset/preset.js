$(document).ready(function () {
    $('.free_dropdown').on('click focus', function () {
        var options = $(this).data("options").split(',');

        $(this).autocomplete({
            source: options,
            minLength: 0,
            delay: 1,
            autoFocus: false,
            scroll: true,
            position: { my: "right top", at: "right bottom", collision: "flip" }
        });

        $(this).autocomplete("search", "");
    });
});

$('*').on('scroll', function (e) {
	$(".ui-autocomplete").hide();
});
$(window).on('scroll', function (e) {
	$(".ui-autocomplete").hide();
});
	
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
            dataType: 'json',  // サーバーからJSON形式でデータを受信
            success: function(response) {
                // 成功時にtaskListを更新
                updateTaskList(response);  // サーバーから受け取ったプリセットデータを引数として渡す
            },
            error: function(status, error) {
            	console.error('AJAX Error:', status, error);
                alert('プリセットを取得できませんでした。');
            }
        });
    });
});

// taskListを更新する関数
function updateTaskList(presetData) {
    const taskList = $('#taskList');
    taskList.empty();  // 既存のタスクリストをクリア

    // サーバーから受け取ったプリセットのタスクをリストに追加
    if (Array.isArray(presetData) && presetData.length > 0) {
        presetData.forEach(function(task) {
        	// taskTimeをフォーマットして表示用文字列を作成
            const taskTime = Array.isArray(task.taskTime)
                ? `${task.taskTime[0]}:${String(task.taskTime[1]).padStart(2, '0')}`
                : "不明";
            const taskItem = `<li class="task">
            					<div>
	                                <div class="timersolid">
	                                    <p class="tasktime">${taskTime}</p>
	                                    <span class="span"></span>
	                                </div>
                                	<p>${task.name}</p>
                                <div>
                              </li>`;
            taskList.append(taskItem);
        });
    } else {
        taskList.append('<li>選択されたプリセットにはタスクがありません。</li>');
    }
}