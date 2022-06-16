/**
 * 
 */

$(document).ready(function(){	
		
	$(".btn-ingrediente").click(function() {
		var val = $(this).text();
		$("#ingredientes").tagsinput('add', val);
	});
	$(".btn-categoria").click(function() {
		var val = $(this).text();
		$("#categorias").tagsinput('add', val);
	});
	
});