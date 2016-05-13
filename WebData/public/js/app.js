tinymce.init({
	selector:'#codeEditor',
	content_css : '/css/style.css',
	menubar: false,
	statusbar: false,
	toolbar: false,
	height : "310"
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
	$("#error-container").hide()
});