function PageSet(iframeId, invoker) {
	var $$ = null, pageSet = this;
	this.templatei = 99;
	invoker = eval(invoker);
	$("#" + iframeId).load(
			function() {
				var iframe = $(this).css({border:0, width:"100%"});
				iframe.height($(iframe.get(0).contentDocument).height());
				$$ = iframe.get(0).contentWindow.$;
//				$$.struts2_jquery.requireCss("/static/scripts/beangle/PageSet.css", base);
				typeof(invoker) == "function" && invoker(pageSet);
			});
	this.hover = function(opts) {
		var selector = opts.selector;
		var items = $$(selector);
		for(var i = 0; i < items.length; i++){
			this.hoverItem(items.eq(i), opts, i);
		}
	}
	this.hoverItem = function(item, opts, i){
		var  href = opts.href;
		var div = $("<div class='pageSetDiv'><input type='button' value='编辑'/><div></div></div>").css({position:"absolute", "z-index":999});
		div.find("div").css({background:"#777", opacity: "0.2"});
		div.find("input").css({"font-size": "18px", padding:"5px 10px", color: "#000", position:"absolute",  "z-index": 99,cursor:"pointer"}).hover(function (){
			$(this).css({background: "#fff"})
		}, function (){
			$(this).css({background: "#ccc"
		})});
		div.height(item.height()).find("div").height(item.height());
		div.width(item.width());
		item.prepend(div);
		var btn = div.find("input");
		if(href.indexOf("?") < 0){
			href = href + "?";
		}
		btn.click(function() {
			$.colorbox({
				top : "50",
				overlayClose : false,
				iframe : true,
				width : "800px",
				height : "600px",
				href : href + "&num" + i
			});
		});
		btn.css("top", (div.height() - btn.outerHeight()) / 2);
		btn.css("left", (div.width() - btn.outerWidth()) / 2);
		div.hide();
		item.hover(function() {
			div.show();
		}, function() {
			div.hide();
		});
	}
	this.addTr = function (btn, templateId){
		var tr = $("#" + templateId).val();
		 tr = tr.replace(/\{v\}/g, pageSet.templatei++);
		 tr = $(tr);
		 tr.insertAfter($(btn).parent().parent());
	}
	this.delTr = function (btn, templateId){
		var table = $(btn).parent().parent().parent();
		if(table.find("tr").length > 1) {
			$(btn).parent().parent().remove();
		}
	}
	this.up = function (btn){
		var v = $(btn).parent().parent();
		v.insertBefore(v.prev());
	}
	this.delImages = function(btn){
		$(btn).parent().parent().remove();
	}
}