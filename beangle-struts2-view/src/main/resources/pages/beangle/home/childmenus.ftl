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

<!-- 左边菜单 -->
[@b.div id="MLeft"]
<div class="list_box_3">
	[#if submenus?size>0]
	<table class="title_2">
		<tr>
			<td><img src="${base}/static/themes/${b.theme.ui}/images/homePage/00index_48.png"></td>
			<td class="td_1">${(menu.title)!}</td>
			<td class="td_2">功能介绍： <span>${(menu.remark)!"暂无简介"}</span></td>
		</tr>
	</table>
	<div class="BlankLine1"></div>
	<table class="list_1">
		<tbody>
			[#assign nodeIndex = 0/]
			[#list submenus! as module]
			[#if module_index % 3 == 0]
			<tr>
			[/#if]
				<td class="td_4">
					<h5>[@i18nNameTitle module/]</h5>
					<div>${(module.remark)!"暂无简介"}</div>
				<div align="right">
					[@b.a href="${(module.entry)!}" parentId="${(module.parent.id)!}" myTitle="${(module.title)!}" parentName="${(module.parent.title)!}" target="main" class="childMenu"]点击进入[/@]
					</div>
				</td>
				[#if (module_index + 1) % 3 != 0]
				<td class="arrow_1" style="background:#fff;">&nbsp;</td>
				[/#if]
				[#if nodeIndex == (module_index - 2)]
			</tr>
			<tr>
				<td height="20">&nbsp;</td>
			</tr>
			[#assign nodeIndex = (module_index + 1)]
			[/#if]
			[/#list]
		</tbody>
	</table>
	[#else]
	without any menu!
	[/#if]
</div>
[/@]

<!-- 右边导航 -->
[@b.div id="MRight"]
<div class="list_box_4">
	[#if parentmenus?size>0]
	<ul>
		[#list parentmenus! as module]
		<li class="li_1">
			[#if module.entry??]
			[@b.a href="${(module.entry)!}" myTitle="${(module.title)!}" parentId="${(module.parent.id)!}" parentName="${(module.parent.title)!}" target="main" class="parentMenu"]
			<table cellpadding="0" cellspacing="0" width="50" height="50">
				<tr>
									<td height="50" width="50" align="center" valign="middle">
										[#if (module_index + 1) % 2 == 0 && (module_index + 1) % 4 !=0]
											[#if module.icon??]
												<img src="${base}/common/file.action?method=downIconById&menuId=${module.id!}" width="30"/>
											[#else]
												<img src="${base}/static/themes/${b.theme.ui}/images/homePage/00index_51.png" width="30"/>
											[/#if]
										[#elseif (module_index + 1) % 3 == 0]
											[#if module.icon??]
												<img src="${base}/common/file.action?method=downIconById&menuId=${module.id!}" width="30"/>
											[#else]
												<img src="${base}/static/themes/${b.theme.ui}/images/homePage/00index_54.png" width="30"/>
											[/#if]
										[#elseif (module_index + 1) % 4 == 0]
											[#if module.icon??]
												<img src="${base}/common/file.action?method=downIconById&menuId=${module.id!}" width="30"/>
											[#else]
												<img src="${base}/static/themes/${b.theme.ui}/images/homePage/00index_57.png" width="30"/>
											[/#if]
										[#else]
											[#if module.icon??]
												<img src="${base}/common/file.action?method=downIconById&menuId=${module.id!}" width="30"/>
											[#else]
												<img src="${base}/static/themes/${b.theme.ui}/images/homePage/00index_48.png" width="30"/>
											[/#if]
										[/#if]
									</td>
				</tr>
			</table>
			<h3>[@i18nNameTitle module/]</h3>
			[/@]
			[#else]
			[@b.a href="!childmenus?menu.id=${module.id}" myTitle="${(module.title)!}" parentId="${(module.parent.id)!}" parentName="${(module.parent.title)!}" target="main" class="parentMenu"]
			<table cellpadding="0" cellspacing="0" width="50" height="50">
				<tr>
									<td height="50" width="50" align="center" valign="middle">
										[#if (module_index + 1) % 2 == 0 && (module_index + 1) % 4 !=0]
											[#if module.icon??]
												<img src="${base}/common/file.action?method=downIconById&menuId=${module.id!}" width="30"/>
											[#else]
												<img src="${base}/static/themes/${b.theme.ui}/images/homePage/00index_51.png" width="30"/>
											[/#if]
										[#elseif (module_index + 1) % 3 == 0]
											[#if module.icon??]
												<img src="${base}/common/file.action?method=downIconById&menuId=${module.id!}" width="30"/>
											[#else]
												<img src="${base}/static/themes/${b.theme.ui}/images/homePage/00index_54.png" width="30"/>
											[/#if]
										[#elseif (module_index + 1) % 4 == 0]
											[#if module.icon??]
												<img src="${base}/common/file.action?method=downIconById&menuId=${module.id!}" width="30"/>
											[#else]
												<img src="${base}/static/themes/${b.theme.ui}/images/homePage/00index_57.png" width="30"/>
											[/#if]
										[#else]
											[#if module.icon??]
												<img src="${base}/common/file.action?method=downIconById&menuId=${module.id!}" width="30"/>
											[#else]
												<img src="${base}/static/themes/${b.theme.ui}/images/homePage/00index_48.png" width="30"/>
											[/#if]
										[/#if]
									</td>
				</tr>
			</table>
			<h3>[@i18nNameTitle module/]</h3>
			[/@]
			[/#if]
		</li>
				[#if (module_index + 1) % 4 == 0]
					<li class="li_2"></li>
		[/#if]
		[/#list]
	</ul>
</div>
[/#if]
[/@]
<script type="text/javaScript">
		//	$(".childMenu").click(function(e){
	//		$("#position_bar").find("span:gt(0)").remove();
	//		$("#position_bar").find(".fontMenu").remove();
	//		$("#position_bar").find(".secondMenu").remove();
	//		var secondSpan = $("<span class='secondMenu'>&nbsp;>");
	//		var secondA = $("<a href='home!childmenus.action?menu.id="+$(this).attr("parentId")+"'>"+$(this).attr("parentName")+"</a>");
	//		secondA.click(function(e){
	//			$("#position_bar").find("span:gt(1)").remove();
	//			$("#position_bar").find(".fontMenu").remove();
	//			return bg.Go(this,'main',true);
	//		});
	//		secondSpan.append("&nbsp;").append(secondA);
	//		var threeSpan = $("<span class='fontMenu'>&nbsp;>&nbsp;"+$(this).attr("myTitle")+"</span>");
	//		$("#position_bar").append(secondSpan).append(threeSpan);
	//	});
	//
	//	$(".parentMenu").click(function(e){
	//		$("#position_bar").find("span").remove();
	//		if ($(this).attr("parentId") != ""){
	//			var firstSpan = $("<span class='firstMenu'>&nbsp;>");
	//			var firstA = $("<a href='home!submenus.action?menu.id="+$(this).attr("parentId")+"'>"+$(this).attr("parentName")+"</a>");
	//			firstA.click(function(e){
	//				$("#position_bar").find("span:gt(0)").remove();
	//				return bg.Go(this,'main',true);
	//			});
	//			firstSpan.append("&nbsp;").append(firstA);
	//			$("#position_bar").append(firstSpan);
	//		}
	//		var secondSpan = $("<span class='fontMenu'>&nbsp;>&nbsp;"+$(this).attr("myTitle")+"</span>");
	//		$("#position_bar").append(secondSpan);
	//	});
	$("#position_bar").find("span").remove();
	var menuTarget="main";
	[#--
	[#assign locationpath][#list menuPath as mpath][#if mpath_has_next]&nbsp;&gt;&nbsp;<a href='${base}/home!submenus.action?menu.id=${mpath.id}'  onclick='return bg.Go(this,menuTarget,true);'>${mpath.title}</a>[#else]&nbsp;&gt;&nbsp;${mpath.title}[/#if][/#list][/#assign]
	--]
	[#assign locationpath][#list menuPath as mpath]&nbsp;&gt;&nbsp;<a href='${base}/home![#if mpath_index == 0 ]submenus[#else]childmenus[/#if].action?menu.id=${mpath.id}'  onclick='return bg.Go(this,menuTarget,true);'>${mpath.title}</a>[/#list][/#assign]
	[#assign locationpath2][#list menuPath as mpath]&nbsp;&gt;&nbsp;[#if mpath_has_next]<a href='${base}/home!submenus.action?menu.id=${mpath.id}'  onclick='return bg.Go(this,menuTarget,true);'>${mpath.title}</a>[#else]<a href='${base}/home!childmenus.action?menu.id=${mpath.id}' onclick='return bg.Go(this,menuTarget,true);'>${mpath.title}</a>[/#if][/#list][/#assign]
	$("#position_bar").append("<span class='fontMenu'>${locationpath}</span>");
	$(function(){
		$(".childMenu").click(function (){
			//var locationpath2 = "${locationpath2}";
			//locationpath2 += "&nbsp;&gt;&nbsp;" + $(this).parent().prevAll("h5").html();
			//$("#position_bar").find("span").html("<span class='fontMenu'>"+locationpath2+"</span>");
			$("#position_bar").find("span").append("&nbsp;>&nbsp;").append(submenus.getMenuA($(this)));
		});
   });
</script>
[@b.foot/]