[#ftl]
[@b.head]
[/@]
<!-- 头部导航 -->
[#assign weekDay = ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" ]/]
<div id="Nav_bar">
	<div class="top_box">
		<div class="logo_box"><img src="${base}/static/login/images/index_01.png"></div>
		<div id="items_header">
			<ul>
				[#--
				<li id="header_email_item">
					<div id="quicklink_4">
						<div class="first_menu_4">
							<a class="email_ico" href="javascript:void(0)">消息</a><span>1</span>
						</div>
					</div>
				</li>
				<li id="header_quicklink_item">
					<div id="quicklink">
						<div class="first_menu_1">设置</div>
							<ul class="the_menu">
								<li><a href="javascript:void(0)">添加模块</a></li>
								<li><a href="javascript:void(0)">调整布局</a></li>
								<li><a href="javascript:void(0)">编辑切换</a></li>
							</ul>
					</div>
				</li>
				--]
				<li id="header_quicklink_item_2">
					<div id="quicklink_3">
						<div class="password_menu"><a href="#" onclick="modifyPassword(this)">修改密码</a></div>
					</div>
				</li>
				
				<li id="header_quicklink_item_3">
					<div id="quicklink_3">
						<div class="div_box"><a href="javascript:void(0)">帮 助</a></div>
					</div>
				</li>
				<li id="header_quicklink_item_5">
					<div id="quicklink_5">
						<div class="div_box"><a href="${base}/logout.action">注 销</a></div>
					</div>
				</li>
			</ul>
		</div>
		
		<div class="welcome_bar">
			[#--<span style=""><img src="${base}/avatar/user.action?user.name=${user.name!}" width="20px" style="vertical-align:middle"/></span>--]
			[@b.form]
				<span class="f5">[@b.a href="/security/my" class="userName" target="_blank" title="查看登录记录"]${user.fullname}(${user.name})[/@]</span>
				[#if (user.categories?size==1)]
					<span class="f4">${user.categories[0].title}</span>
					<input  type="hidden" name="security.categoryId" value="${categoryId}" style="width:70px"/>
				[#else]
					[@b.select name="security.categoryId" items=user.categories title="entity.userCategory"  style="float:left;margin-top:10px;margin-left:2px;width:70px" value=categoryId option="id,title"/]
				[/#if]
				[#if (user.categories?size>1) ]
					<span class="f4">
						<input type="submit" value="切换" style="cursor:pointer;margin-top:10px;margin-left:2px;"/>
					</span>
				[/#if]
			[/@]
		</div>
		<div class="member_box"></div>
		<div class="date_bar">${date?string("yyyy年MM月dd日")} ${weekDay[week]}</div>
	</div>
</div>

<!-- 中部内容 -->
<div id="BodyBg">
	<div id="MainBody" class="bg1">
		<div id="position_bar">您的当前位置：<a href="${base}/home.action">首页</a></div>
		<div id="tool_bar">
			<div id="tab_title_box_1" style="width:1000px;">
				[#macro i18nNameTitle(entity)][#if locale.language?index_of("en")!=-1][#if entity.engTitle?if_exists?trim==""]${entity.title?if_exists}[#else]${entity.engTitle?if_exists}[/#if][#else][#if entity.title?if_exists?trim!=""]${entity.title?if_exists}[#else]${entity.engTitle?if_exists}[/#if][/#if][/#macro]
				[#list menus?if_exists as module]
					[#if module_index==0]
					[@b.a class="a_now" href="!submenus?menu.id=${module.id}" target="main" ]
						<span>[@i18nNameTitle module/]</span>
					[/@]
					[#else]
					[@b.a href="!submenus?menu.id=${module.id}" target="main" ]
						<span>[@i18nNameTitle module/]</span>
					[/@]
					[/#if]
				[/#list]
			</div>
			[#--
			<div class="search_box">
				[@b.form aciton="!searchmenus" target="main"]
				<table cellpadding="0" cellspacing="0" width="100%">
					<tr>
						<td class="td_1">搜 索</td>
						<td width="168" align="right"><input class="ip_1" type="text"></td>
						<td align="left"><input type="submit" class="ip_2" value=""></td>
					</tr>
				</table>
				[/@]
			</div>--]
		</div>
		<div class="BlankLine1"></div>
		[@b.div id="main" class="main" href="!submenus?menu.id=${(menus?first.id)!}"/]
	</div>
</div>

<!-- 底部版权 -->
<div id="BottomBg">本系统推荐使用IE8及以上版本IE浏览器</div>
<div id="BottomBg">Copyright &copy; 2013 All Rights Reserved.</div>
<script type="text/javaScript">
	$("#tab_title_box_1 a").each(function(index){
		$(this).focus(function(e){
			$(this).blur();
		});
		$(this).click(function(e){
			$(this).parent().find("a").each(function(e){
				$(this).removeClass("a_now");
			});
			$(this).addClass("a_now");
		});
	});
	function modifyPassword(ele) {
		jQuery(ele).colorbox(
		{
			iframe : true,
			width : "800px",
			height : "600px",
			href : "${base}/security/password!edit.action"
		});
	}
</script>
[@b.foot/]