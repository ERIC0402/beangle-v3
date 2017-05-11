[#ftl]
[@b.head/]
[#include "../nav.ftl"/]
<table  class="indexpanel">
	<tr>
		<td class="index_view">
		[@b.form name="dictTypeSearchForm"  action="!search" target="dictTypelist" title="ui.searchForm" theme="search"]
			[@b.textfields names="dictType.code;代码,dictType.name;名称"/]
			[@b.select name="dictType.enabled" label="common.status" value="" empty="..." items={'1':'启用','0':'禁用'}/]
		[/@]
		</td>
		<td class="index_content">[@b.div id="dictTypelist" href="!search" /]</td>
	</tr>
</table>
[@b.foot/]