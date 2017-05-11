[#ftl]
[@b.head]
<link href="${base}/static/themes/${b.theme.ui}/homePage.css" rel="stylesheet" type="text/css" />
<script src="${base}/static/scripts/js/view_menus.js" type="text/javascript"></script>
[/@]
[#macro i18nNameTitle(entity)][#if locale.language?index_of("en")!=-1][#if entity.engTitle!?trim==""]${entity.title!}[#else]${entity.engTitle!}[/#if][#else][#if entity.title!?trim!=""]${entity.title!}[#else]${entity.engTitle!}[/#if][/#if][/#macro]

[#assign displayed={} /]
[#macro displayMenu menu]
[#if !(displayed[menu.id?string]??)][#assign displayed=displayed+{menu.id?string:menu}/][#else][#return/][/#if]
<li>
[#if menu.entry??]
[@b.a href="${(menu.entry)!}" target="main"][@i18nNameTitle menu/][/@]
[#else]
	[@i18nNameTitle menu/]
	<ul style="padding-left: 20px;">
	[#list menu.children?sort_by("code") as child]
		[#if submenus?seq_contains(child)][@displayMenu child/][/#if]
	[/#list]
	</ul>
[/#if]
</li>
[/#macro]

[@b.div id="MLeft"]
<div class="list_box_1">
[#if submenus?size>0]
	<ul>
	[#list submenus! as module]
		<li class="li_1">
			[#if module.entry??]
				[@b.a href="${(module.entry)!}" myTitle="${(module.title)!}" parentId="${(module.parent.id)!}" parentName="${(module.parent.title)!}" target="main" class="subMenu"]
					[#if (module_index + 1) % 2 == 0 && (module_index + 1) % 4 !=0]
					
					<div class="ico3_2" [@icon menu=module/]></div>
					[#elseif (module_index + 1) % 3 == 0]
					<div class="ico3_3" [@icon menu=module/]>&nbsp;</div>
					[#elseif (module_index + 1) % 4 == 0]
					<div class="ico3_4" [@icon menu=module/]>&nbsp;</div>
					[#else]
					<div class="ico3_1" [@icon menu=module/]>&nbsp;</div>
					[/#if]
					<h3>[@i18nNameTitle module/]</h3>
				[/@]
			[#else]
				[@b.a href="!childmenus?menu.id=${(module.id)!}" myTitle="${(module.title)!}" parentId="${(module.parent.id)!}" parentName="${(module.parent.title)!}" target="main" class="subMenu"]
					[#if (module_index + 1) % 2 == 0 && (module_index + 1) % 4 !=0]
					<div class="ico3_2" [@icon menu=module/]>&nbsp;</div>
					[#elseif (module_index + 1) % 3 == 0]
					<div class="ico3_3" [@icon menu=module/]>&nbsp;</div>
					[#elseif (module_index + 1) % 4 == 0]
					<div class="ico3_4" [@icon menu=module/]>&nbsp;</div>
					[#else]
					<div class="ico3_1" [@icon menu=module/]>&nbsp;</div>
					[/#if]
					<h3>[@i18nNameTitle module/]</h3>
				[/@]
			[/#if]
				
		</li>
		[#if (module_index + 1) % 4 ==0]
			<li class="li_2"></li>
		[/#if]
	[/#list]
	</ul>
[#else]
without any menu!
[/#if]
[#macro icon menu]
	[#if menu.icon??]
		style="background: url('${base}/common/file.action?method=downIconById&menuId=${menu.id!}') no-repeat 50% 50%;"
	[/#if]
[/#macro]
</div>
[/@]
[@b.div id="MRight"]
	<div class="BlankLine2"></div>
	<div class="list_box_2">
		<h3>站内通知</h3>
		<ul>
			[#list zntzs as item]
				<li class="li_1">
					<a href="${base}/system/zntz!info.action?zntz.id=${item.id!}" target="_blank">
						${(item.time?string("yyyy-MM-dd"))!} 
						[#if item.title??&&item.title?length>13]
							${item.title[0..13]}...
						[#else]
							${item.title!}
						[/#if]
					</a>
				</li>
			[/#list]
		</ul>
	</div>
	<div class="BlankLine2"></div>
	<div class="list_box_2">
		<h3>站内消息</h3>
		<ul>
			[#list znxxs as item]
				<li class="li_1">
					<a href="${base}/system/znxxhf!edit.action?znxx.id=${item.id!}" target="_blank">
						${(item.time?string("yyyy-MM-dd"))!} 
						[#if item.title??&&item.title?length>13]
							${item.title[0..13]}...
						[#else]
							${item.title!}
						[/#if]
					</a>
				</li>
			[/#list]
		</ul>
	</div>
[/@]
<script type="text/javaScript">
	$(".subMenu").click(function(e){
		$("#position_bar").find("span").remove();
		if ($(this).attr("parentId") != ""){
			var firstSpan = $("<span class='firstMenu'>&nbsp;&gt;</span>");
			var firstA = $("<a href='${base}/home!submenus.action?menu.id="+$(this).attr("parentId")+"'>"+$(this).attr("parentName")+"</a>");
			firstA.click(function(e){
				$("#position_bar").find("span:gt(0)").remove();
				return bg.Go(this,'main',true);
			});
			firstSpan.append("&nbsp;").append(firstA);
			$("#position_bar").append(firstSpan);
		}
		//var secondSpan = $("<span class='fontMenu'>&nbsp;>&nbsp;"+$(this).attr("myTitle")+"</span>");
		var secondSpan = $("<span class='fontMenu'>&nbsp;>&nbsp;</span>");
		secondSpan.append(submenus.getMenuA($(this)));
		$("#position_bar").append(secondSpan);
	});
        
       if($("#position_bar").length>0){
           var menuTarget="main";
            $("#position_bar").find("span").remove();
            [#--
            [#assign locationpath][#list menuPath as mpath][#if mpath_has_next]&nbsp;&gt;&nbsp;<a href='${base}/home!submenus.action?menu.id=${mpath.id}'  onclick='return bg.Go(this,menuTarget);'>${mpath.title}</a>[#else]&nbsp;&gt;&nbsp;${mpath.title}[/#if][/#list][/#assign]
            --]
            [#assign locationpath][#list menuPath as mpath]&nbsp;&gt;&nbsp;<a href='${base}/home!submenus.action?menu.id=${mpath.id}'  onclick='return bg.Go(this,menuTarget);'>${mpath.title}</a>[/#list][/#assign]
            $("#position_bar").append("<span class='fontMenu'>${locationpath}</span>");
       }
</script>
[@b.foot/]
