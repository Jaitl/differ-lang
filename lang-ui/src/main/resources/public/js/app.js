var editor = tinymce.init({
	plugins : 'paste',
	selector:'#codeEditor',
	content_css : '/css/style.css',
	menubar: false,
	statusbar: false,
	toolbar: false,
	height : "350",
	paste_as_text: true,
	paste_auto_cleanup_on_paste: true,
	paste_word_valid_elements: false,
	setup: function(editor) {
		editor.on('init', function(e) {
			$.get( "/api/program", function( data ) {
				tinyMCE.activeEditor.setContent(data)
			});
		});

		editor.on('focus', function(e) {
			if ($.diffCode) {
				tinyMCE.activeEditor.setContent($.diffCode)
				$.diffCode = null;
			}

			if ($('#bnf-container').is(':hidden')) {
				$("#bnf-container").show();
				$("#plot-container").hide();
			}
		});
	}
});

function getRandomColor() {
	var letters = '0123456789ABCDEF'.split('');
	var color = '#';
	for (var i = 0; i < 6; i++ ) {
		color += letters[Math.floor(Math.random() * 16)];
	}
	return color;
}

$(document).ready(function(){
	$.get( "/api/bnf", function( data ) {
		$("#bnf-area").text(data);
	});

	$("#bt-run").click( function() {

		if ($.diffCode) {
			return;
		}
		
		$("#error-container").hide();
		$("#plot-container").hide();
		$("#bnf-container").show();

		if ($.lineChart != null) {
			$.lineChart.destroy();
		}

		$.ajax({
			url: '/api/compile',
			type: 'POST',
			data: JSON.stringify({"code": tinyMCE.activeEditor.getContent()}),
			contentType: 'application/json; charset=utf-8',
			dataType: 'json',
			async: false,
			success: function(result) {
				if (result.error === false) {

					result.result.datasets.forEach(function(elem) {
						color = getRandomColor();
						elem.fill = false;
						elem.backgroundColor = color;
						elem.borderColor = color;
					});

					$("#plot-container").show();
					$("#bnf-container").hide();
					var myplot = $("#my-plot");
					$.lineChart = new Chart(myplot, {
						type: 'line',
						data: result.result
					});
				} else {
					$("#error-container").show();
					$("#error-text").text(result.textError);
					$.diffCode = tinyMCE.activeEditor.getContent();
					tinyMCE.activeEditor.setContent(result.highlightCode);
				}
			}
		});
	});


	$("#error-container").hide();
	$("#plot-container").hide();
	$("#bnf-container").show();
});