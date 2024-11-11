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
    });

    // 文字色を変更する関数
    $('#textColorBtn').on('click', function() {
        var textColor = $('#textcolor').val();
        $('body').css('color', textColor); // 文字色を変更
    });
});