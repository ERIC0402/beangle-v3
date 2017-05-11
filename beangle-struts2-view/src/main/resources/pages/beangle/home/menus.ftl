[#ftl]
[@b.head]
[/@]
<script type="text/javascript" src="${base}/static/scripts/home/menus.js"></script>
<script type="text/javascript">
	$(function (){
		if(tophref && tophref.indexOf("#") < 0){
			return;
		}
		var sharp = tophref.substring(tophref.indexOf("#") + 1);
		$(".second_li > a[href^='"+sharp+"']").click();
	});
</script>
<a href="#" class="left_menu_expand_ico left_expand_ico" title="展开菜单"></a>
<a href="#" class="left_menu_closed_ico left_closed_ico" title="隐藏菜单"></a>
<div id="left_menu_div">
      <h2 class="menu_header"><span>[@c.i18nNameTitle pmenu/]</span></h2>
      <ul class="menu_ul">
      	[#list modules as m1 ]
      		[#if m1.parent?? && m1.parent.id == pmenu.id]
	      	 	<li class="first_li">
	      	 		<a class="first_li_a closed" href="#" >[@c.i18nNameTitle m1/]</a>
	      	 		<ul class="menu_expand_ul" style="clear: both;">
			      	 	[#list modules as m2]
			      	 		[#if m2.entry?? && m2.code?starts_with(m1.code) && m1.code != m2.code]
			      	 			<li class="second_li">[@b.a href="${(m2.entry)!}" target="ctab_${m2.id}" onclick="return w.goToTab(this);" ][@c.i18nNameTitle m2/][/@]</li>
			      	 		[/#if]
			      	 	[/#list]
	          		</ul>
	      	 	</li>
	      	[/#if]
      	[/#list]
      </div>
  [@b.foot/]
