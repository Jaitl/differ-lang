tinymce.init({
	plugins : 'paste',
	selector:'#codeEditor',
	content_css : '/css/style.css',
	menubar: false,
	statusbar: false,
	toolbar: false,
	height : "350",
	paste_as_text: true,
	paste_auto_cleanup_on_paste: true,
	paste_word_valid_elements: false
});
//tinyMCE.activeEditor.getContent()

$(document).ready(function(){
	/*$(".btn-slide").click(function(){
		$("#panel").slideToggle("slow");
		$(this).toggleClass("active");
	});*/

	$.get( "/api/bnf", function( data ) {
		$("#bnf-area").text(data);
	});

	$.get( "/api/program", function( data ) {
		$("#codeEditor").val(data);
	});

	$("#bt-run").click( function() {
		$("#error-container").hide();
		$("#plot-container").hide();

		$.ajax({
			url: '/api/compile',
			type: 'POST',
			data: JSON.stringify({"code": tinyMCE.activeEditor.getContent()}),
			contentType: 'application/json; charset=utf-8',
			dataType: 'json',
			async: false,
			success: function(result) {
				if (result.error === false) {
					$("#plot-container").show();
					var myplot = $("#my-plot");
					var myLineChart = new Chart(myplot, {
						type: 'line',
						data: result.result
					});
				} else {
					console.log(result.textError)
					$("#error-container").show();
					$("#error-text").text(result.textError);
				}
			}
		});
	});


	$("#error-container").hide();
	$("#plot-container").hide();
});