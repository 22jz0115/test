<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="stylesheet" href="assets/css/style.css">
	<title>Insert title here</title>
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
        <h1>タスク</h1>
    </header>
    
        <table><caption></caption></table>
            
    <script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>

        <!--
        //-->
    </script>
    <div class="tasks">
        <ul>
            <li class="task">
                <div>
                    <div class="timersolid">
                        <p class="tasktime">8:00</p>
                        <span></span>
                    </div>
                    <p class="taskname">
                        朝ごはん食べる
                    </p>
                </div>
                <div class="buttons">
                    <label class="toggle-button-4">
                        <input type="checkbox" class="check">
                    </label>
                    <input type="reset" value="-" onclick="removeTask(this)">
                </div>
                
            </li>
            <li class="task">
                <div>
                    <div class="timersolid">
                        <p class="tasktime">8:00</p>
                        <span></span>
                    </div>
                    <p class="taskname">
                        朝ごはん食べる
                    </p>
                </div>
                <div class="buttons">
                    <label class="toggle-button-4">
                        <input type="checkbox" class="check">
                    </label>
                    <input type="reset" value="-" onclick="removeTask(this)">
                </div>
                
            </li>
            <li class="task">
                <div>
                    <div class="timersolid">
                        <p class="tasktime">8:00</p>
                        <span></span>
                    </div>
                    <p class="taskname">
                        シャワー
                    </p>
                </div>
                <div class="buttons">
                    <label class="toggle-button-4">
                        <input type="checkbox" class="check">
                    </label>
                    <input type="reset" value="-" onclick="removeTask(this)">
                </div>
                
            </li>
             <li class="task">
                <div>
                    <div class="timersolid">
                        <p class="tasktime">8:00</p>
                        <span></span>
                    </div>
                    <p class="taskname">
                        ゲーム
                    </p>
                </div>
                <div class="buttons">
                    <label class="toggle-button-4">
                        <input type="checkbox" class="check">
                    </label>
                    <input type="reset" value="-" onclick="removeTask(this)">
                </div>
                
            </li>
             <li class="task">
                <div>
                    <div class="timersolid">
                        <p class="tasktime">8:00</p>
                        <span></span>
                    </div>
                    <p class="taskname">
                        運動する
                    </p>
                </div>
                <div class="buttons">
                    <label class="toggle-button-4">
                        <input type="checkbox" class="check">
                    </label>
                    <input type="reset" value="-" onclick="removeTask(this)">
                </div>
                
            </li>
             <li class="task">
                <div>
                    <div class="timersolid">
                        <p class="tasktime">8:00</p>
                        <span></span>
                    </div>
                    <p class="taskname">
                        昼ごはん食べる
                    </p>
                </div>
                <div class="buttons">
                    <label class="toggle-button-4">
                        <input type="checkbox" class="check">
                    </label>
                    <input type="reset" value="-" onclick="removeTask(this)">
                </div>
                
            </li>
             <li class="task">
                <div>
                    <div class="timersolid">
                        <p class="tasktime">8:00</p>
                        <span></span>
                    </div>
                    <p class="taskname">
                        ごはん食べる
                    </p>
                </div>
                <div class="buttons">
                    <label class="toggle-button-4">
                        <input type="checkbox" class="check">
                    </label>
                    <input type="reset" value="-" onclick="removeTask(this)">
                </div>
                
            </li>
            
        </ul>
    </div>
    <h2>メモ</h2>
    <div class="area">
        <textarea name="" id="" cols="50" rows="10"></textarea>
    </div>


    <div class="progress-container">
        <div class="progress-bar" id="countDisplay"></div> <!-- ここに進捗率を表示 -->
    </div>

    

    <div class="bts">
       <a  href=""><p>タスク追加</p></a>
       <a  href=""><p>プリセット追加</p></a> 
    </div>
    
    <<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <script type="text/javascript" src="assets/js/task/script.js"></script>
    
    <!-- サーバーから送られた日付をJavaScriptに渡す -->
    <script>
        // サーバーから渡された日付 (例: "2024-11-15") をJavaScriptに渡す
        const selectedDate = '<%= request.getAttribute("selectedDate") %>';
    </script>
</body>
</html>