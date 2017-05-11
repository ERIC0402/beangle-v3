[#ftl]
[@b.head title="Dashboard"/]
<style type="text/css">
img {border:0 none;vertical-align:middle}
.module{margin:0px 3px 5px;line-height:1.3em;float:left;width:42%;}
</style>

[#macro menuitem image="" link="" name="" remark=""]
<tr>
	<td>[@b.a href=link]<img height="48" style="margin-right:1em" alt="" width="48" src="${b.theme.iconurl(image!,48)}" />[/@]</td>
	<td style="vertical-align:middle">
		<div class="link">[@b.a href="${link}"]${name}[/@]</div>
		<div style="color:gray; text-decoration:none;">${remark!}</div>
	</td>
</tr>
[/#macro]
[#include "../nav.ftl"/]
<table>
<tr>
	<td width="40%" valign="top">
		<table id="management-links" style="padding-left: 2em;">
			[@menuitem image="setting.gif" link="/system/dict-type" name="字典类型" remark="规划字典分类"/]
			[@menuitem image="user.gif" link="/system/dict-data" name="字典数据" remark="创建字典数据"/]
			[#--
			[@menuitem image="menu.gif" link="system-argument!edit" name="系统参数" remark="配置系统参数"/]
			--]
		</table>
	</td>
	<td valign="top">
	[@b.div id="data" href="!dict-type" /]
	</td>
</tr>
</table>

[@b.foot/]
