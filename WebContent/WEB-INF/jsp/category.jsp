<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
 <link rel="stylesheet" href="assets/css/an/style.css">
    <title>カテゴリー画面</title>
    <style>
        .content {
            display: none; /* 初期状態で全て非表示 */
        }
        .active {
            display: block; /* アクティブなコンテンツのみ表示 */
        }
    </style>
    <link rel="shortcut icon" href="assets/img/icon-192x192.png" type="image/png">
    <link rel="manifest" href="manifest.json">

     
    <script>
        const switchInput = document.getElementById('switch');
        const indoorContent = document.getElementById('indoorContent');
        const outdoorContent = document.getElementById('outdoorContent');

        switchInput.addEventListener('change', function() {
            if (this.checked) {
                // 屋外が選択された場合
                indoorContent.classList.remove('active'); // 屋内を非表示
                outdoorContent.classList.add('active'); // 屋外を表示
            } else {
                // 屋内が選択された場合
                outdoorContent.classList.remove('active'); // 屋外を非表示
                indoorContent.classList.add('active'); // 屋内を表示
            }
        });
    </script>
    <!-- JavaScript -->
    <script>
        const switchInput = document.getElementById('switch');
        const indoorContent = document.getElementById('indoorContent');
        const outdoorContent = document.getElementById('outdoorContent');

        // スイッチでコンテンツ切り替え
        switchInput.addEventListener('change', function() {
            if (this.checked) {
                indoorContent.style.display = 'none';
                outdoorContent.style.display = 'flex';
            } else {
                outdoorContent.style.display = 'none';
                indoorContent.style.display = 'flex';
            }
        });

        // タスクの配列データ
        const tasks = [
            { id: 'task-1', name: "料理" },
            { id: 'task-2', name: "掃除" },
            { id: 'task-3', name: "屋外でのアクティビティ" },
            { id: 'task-4', name: "ピクニック" }
        ];

        // タスクの削除イベント
        document.querySelectorAll('.deletable').forEach(task => {
            const deleteButton = task.querySelector('.delete1');
            deleteButton.addEventListener('click', (event) => {
                event.stopPropagation(); // 親のクリックイベントを無効にする
                const taskId = task.id;
                const taskData = tasks.find(t => t.id === taskId); // タスク情報を取得

                if (taskData) {
                    const confirmation = confirm(`${taskData.name}を削除しますか？`);
                    if (confirmation) {
                        // タスクを削除
                        const target = document.getElementById(taskId);
                        target.remove(); // HTML上から削除
                        const index = tasks.findIndex(t => t.id === taskId);
                        tasks.splice(index, 1); // 配列から削除

                        // イベントリスナーを削除
                        deleteButton.removeEventListener("click", arguments.callee);
                    }
                }
            });
        });
    </script>
</head>

<body>

   <header>
        <a href="Home" class="back1"><img src="assets/img/戻るボタン.png" alt="戻るボタン"></a>
        <h1>カテゴリー</h1>
    </header>
    
    <!-- スイッチボタン -->
    <div class="box-1">
        <label for="switch" class="switch_label-4">  
            <p>屋内</p>
            <div class="switch">
                <input type="checkbox" id="switch" />
                <div class="circle"></div>
                <div class="base"></div>
            </div>
            <span class="title">屋外</span>
        </label>
    </div>

    <!-- 屋内・屋外コンテンツ -->
    <div class="categories">
        <div id="indoorContent" class="content active">
            <p id="task-1" class="deletable">料理 <span class="delete1">削除</span></p> 
            <p id="task-2" class="deletable">掃除 <span class="delete1">削除</span></p>
        </div>
        <div id="outdoorContent" class="content">
            <p id="task-3" class="deletable">屋外でのアクティビティ <span class="delete1">削除</span></p>
            <p id="task-4" class="deletable">ピクニック <span class="delete1">削除</span></p>
        </div>
    </div>
   
    
    
    <div class="delete">
        <form action="Category" method="post"> <!-- JSPサーブレットへの削除処理のURLを指定 -->
            <button type="submit" class="delete-button">
                <img src="assets/img/mingcute_delete-2-line.png" alt="削除アイコン"><br>
                削除
            </button>
        </form>
    </div>

</body>
</html>
