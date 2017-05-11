// JavaScript Document
$(document).ready(function() {
	$("#quicklink").hover(function() {
		$('ul.the_menu').show();// 当鼠标放上去的时候,程序处理
		$("#quicklink .first_menu_1").addClass("current_hit");
	}, function() {
		$('ul.the_menu').hide();// 当鼠标离开的时候,程序处理
		$("#quicklink .first_menu_1").removeClass("current_hit");
	});
	$("#quicklink_2").hover(function() {
		// //$('ul.the_menu_2').show();//当鼠标放上去的时候,程序处理
		$("#quicklink_2 .first_menu").addClass("current_hit_2");
	}, function() {
		// //$('ul.the_menu_2').hide();//当鼠标离开的时候,程序处理
		$("#quicklink_2 .first_menu").removeClass("current_hit_2");
	});
	$("#quicklink_4").hover(function() {
		// //$('ul.the_menu_4').show();//当鼠标放上去的时候,程序处理
		$("#quicklink_4 .first_menu_4").addClass("current_hit_4");
	}, function() {
		// //$('ul.the_menu_4').hide();//当鼠标离开的时候,程序处理
		$("#quicklink_4 .first_menu_4").removeClass("current_hit_4");
	});
});
//NamedFunction
function NamedFunction(name, func) {
	this.name = name;
	this.func = func;
}
// JavaScript Document
$(document).ready(function() {
	// ------------peasonal_btn-----------------------------------------------------
	try {
		$('.down_list').hover(function() {
			$(this).find('#peasonal_info_box').css('display', 'block');// 当鼠标放上去的时候,程序处理
			$(this).find('.first_a').removeClass('down_list_ico');
			$(this).find('.first_a').addClass('down_list_ico_hover');
		}, function() {
			$(this).find('#peasonal_info_box').css('display', 'none');// 当鼠标离开的时候,程序处理
			$(this).find('.first_a').addClass('down_list_ico');
			$(this).find('.first_a').removeClass('down_list_ico_hover');
		});
	} catch (e) {
	}
});
