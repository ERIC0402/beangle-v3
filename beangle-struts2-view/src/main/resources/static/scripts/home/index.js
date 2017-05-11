function W(){
	this.init = function (){
		var v = this;
	    $(function (){
			v.initTop();
			v.initNav();
			v.initOther();
	    	$("#nav .nav_div ul li").last().css("background", "none");
	    });
	}
	this.initTop = function (){
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
	}
	this.initNav = function (){
		$(function() {
			// 一级菜单
			var navas = $(".nav_div ul li a");
			var curNavClass = "nav_a_now";
			navas.first().addClass(curNavClass);
			navas.click(function() {
				navas.removeClass(curNavClass);
				$(this).addClass(curNavClass);
				if(parseInt($("#left_menu").css("width"))<=10){
					$('.left_expand_ico').click();
				}
			});
			// 顶部收缩
			$('.closed_top_ico').click(function() {
				$("#TopMain").slideUp();
				$(this).hide();
				$('.expand_top_ico').show();
			});
			$('.expand_top_ico').click(function() {
				$("#TopMain").slideDown();
				$(this).hide();
				$('.closed_top_ico').show();
			});
			//Tab左右按钮
			/**
			var ul = $(".tab_bg ul");
			$(".tab_news .tab").hover(function (){
				$(".right_tab_ico, .left_tab_ico").fadeIn();
			}, function (){
				$(".right_tab_ico, .left_tab_ico").fadeOut();
			});
			**/
			$(".right_tab_ico").click(function (){
				ul.animate({marginLeft: '-=600px'});
			});
			$(".left_tab_ico").click(function (){
				var marginLeft = parseInt(ul.css("margin-left"));
				if(marginLeft > 0)return;
				if(marginLeft >= 0){
					ul.animate({marginLeft: '+=30px'},"fast", function (){
						ul.animate({marginLeft: '-=30px'},"fast");
					});
					return;
				}
				ul.animate({marginLeft: '+=600px'}, function (){
					var marginLeft = parseInt(ul.css("margin-left"));
					if(marginLeft >= 0){
						ul.animate({marginLeft: '0px'},"fast");
					}
				});
			});
		});
	}
	this.initOther = function (){
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
	}
	this.modifyPassword = function (ele) {
		jQuery().colorbox(
		{
			iframe : true,
			width : "800px",
			height : "600px",
			href : base + "/security/password!edit.action"
		});
	}
	this.goToTab = function(a){
		var rs = this.addTab(a);
		if(!rs){
			return false;
		}else{
			return bg.Go(a, a.target,true)
		}
	}
	this.addTab = function (a){
		//删除其它Tab，只保留最后一次打开的Tab
		this.delTabs()
		var target = a.target;
		var targetDiv = $("#"+target);
		if(targetDiv.length > 0){
			var index = $(".tab_news .itabContent").index(targetDiv);
			this.selectTab(index);
			return false;
		}
		var name = $(a).text();
		var li = $('<li class="dynamic"><a href="#"><font>'+name+'</font><span onclick="w.closeTab(this)">&nbsp;</span></a></li>');
		$(".tab_bg > ul").append(li);
		//var itabContent = $('<iframe id="'+target+'" name="'+target+'" width="100%" height="2000" frameborder="0" class="itabContent"></iframe>');
		var itabContent = $('<div id="'+target+'" class="itabContent _ajax_target"></div>');
		$(".tab_news").append(itabContent);
		this.selectTab($(".itabContent").length-1);
		return true;
	}
	this.selectTab = function (index){
		$('.tab_bg ul li a').removeClass("now");
		SimpleTab.construct('.tab_bg ul li a', '.itabContent', 'now',index);
	}
	this.closeTab = function (span){
		var li = $(span).parent().parent();
		this.removeTab(li);
	}
	this.removeTab = function(li){
		var index = $('.tab_bg ul li').index(li);
		var itabContent = $(".tab_news .itabContent").eq(index);
		itabContent.find("iframe").remove();
		itabContent.attr("url", "").attr("src", "");
		itabContent.remove();
		li.remove();
		if($('.tab_bg ul li').length == index){
			index--;
		}
		this.selectTab(index);
	}
	this.delTabs = function (){
		var v = this;
		$(".tab_bg ul .dynamic").each(function (){
			v.removeTab($(this));
		});
	}
}
window.w = new W();
w.init();