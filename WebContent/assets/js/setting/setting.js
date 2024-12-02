$(function() {
    // ヘッダーの背景色を変更する関数
    $('#headerColorBtn').on('click', function() {
        var headerColor = $('#headercolor').val();
        $('#header').css('background-color', headerColor); // ヘッダーの背景色を変更
    });

    // 背景色を変更する関数
    $('#bgColorBtn').on('click', function() {
        var bgColor = $('#bgcolor').val();
        $('body').css('background-color', bgColor); // 背景色を変更
        
        // 通知メッセージを表示
        const notification = document.getElementById("notification");
        notification.style.display = "block";
        
        // 数秒後に通知メッセージを非表示にする
        setTimeout(function() {
            notification.style.display = "none";
        }, 2000); // 2秒後に非表示
    });

    // 文字色を変更する関数
    $('#textColorBtn').on('click', function() {
        var textColor = $('#textcolor').val();
        $('body').css('color', textColor); // 文字色を変更
    });

   $('#bgColorBtn').on('click', function() {
    var bgColor = $('#bgcolor').val();
    $('body').css('background-color', bgColor); // 背景色を変更
    
    
