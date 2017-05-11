[#ftl]
[@b.head/]
[#include "../nav.ftl"/]
<table  class="indexpanel">
	<tr>
		<td class="index_view">
		[@b.form name="subSystemSearchForm"  action="!search" target="subSystemlist" title="ui.searchForm" theme="search"]
			[@b.textfields names="subSystem.name;名称,subSystem.domain;域名,subSystem.contextPath;根路径,subSystem.key;密钥"/]
			[@b.select name="subSystem.enabled" label="common.status" value="1" empty="..." items={'1':'${b.text("action.activate")}','0':'${b.text("action.freeze")}'}/]
		[/@]
		</td>
		<td class="index_content">[@b.div id="subSystemlist" href="!search" /]</td>
	</tr>
</table>
[@b.foot/]