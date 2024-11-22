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