[#ftl]
<div id="TopMain">
	<div class="topmain_div">
		<a href="${base}/home.action" class="logo"><img height="76" src="${base}/common/file.action?method=downIconBySCId&scId=${(sc.id)!}"></a>
		<div class="top_tool_bar">
			[#--
			<div class="down_list">
				<a href="#" class="first_a down_list_ico">张张三</a>
				<div id="peasonal_info_box">
					<ul>
						<li>
							<a href="#">选项1</a>
						</li>
						<li>
							<a href="#">选项1</a>
						</li>
						<li>
							<a href="#">选项1</a>
						</li>
						<li>
							<a href="#">选项1</a>
						</li>
					</ul>
				</div>
			</div>
			--]
			[#--
			<div class="down_list">
				<a href="#" class="first_a down_list_ico">个人中心</a>
				<div id="peasonal_info_box">
					<ul>
						<li>
							<a href="#">选项1</a>
						</li>
						<li>
							<a href="#">选项1</a>
						</li>
						<li>
							<a href="#">选项1</a>
						</li>
						<li>
							<a href="#">选项1</a>
						</li>
					</ul>
				</div>
			</div>
			--]
			[#if user??]
			<font class="f_1">${user.fullname}(${user.name})[#--[@b.a href="/security/my" class="first_a" target="_blank" title="查看登录记录"]${user.fullname}(${user.name})[/@]--]</font>
			<div style="float:left">
				[@b.form]
					[#if (user.categories?size==1)]
					[#--
						<span class="f4">${user.categories[0].title}</span>
						<input  type="hidden" name="security.categoryId" value="${categoryId}" style="width:70px"/>
					--]
					[#else]
						<span class="span_1">|</span>
						[@b.select name="security.categoryId" items=user.categories title="entity.userCategory"  style="margin:3px 0;" value=categoryId option="id,title" onchange="this.form.submit();"/]
					[/#if]
				[/@]
			</div>
			<span class="span_1">|</span>
			<a class="first_a modify_password" href="#" onclick="w.modifyPassword(this)">修改密码</a><span class="span_1">|</span>
			[#--
			<a href="#" class="first_a"> 私信（<font class="f_2">8</font>）</a><span class="span_1">|</span>
			<a href="#" class="first_a">使用帮助</a><span class="span_1">|</span>
			--]
			<a class="first_a logout" href="${base}/logout.action">注 销</a>
			[/#if]
			[#include "topMainSetting.ftl"/]
			[#include "topMainLink.ftl"/]
		</div>
	</div>
</div>
