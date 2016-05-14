tinymce.init({
	selector:'#codeEditor',
	content_css : '/css/style.css',
	menubar: false,
	statusbar: false,
	toolbar: false,
	height : "350"
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
		/*$.post( "/api/compile", JSON.stringify({"code": "dfsdsdf"}))
			.done(function( data ) {
				//alert( "Data Loaded: " + data );
				});*/

		$.ajax({
			url: '/api/compile',
			type: 'POST',
			data: JSON.stringify({"code": $("#codeEditor").val()}),
			contentType: 'application/json; charset=utf-8',
			dataType: 'json',
			async: false,
			success: function(msg) {
				alert(msg);
			}
		});
	});


	$("#error-container").hide()
});