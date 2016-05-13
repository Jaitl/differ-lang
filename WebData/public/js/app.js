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
		$.post( "/compile", {"code": $("#codeEditor").val()})
			.done(function( data ) {
				//alert( "Data Loaded: " + data );
			});
	});


	$("#error-container").hide()
});