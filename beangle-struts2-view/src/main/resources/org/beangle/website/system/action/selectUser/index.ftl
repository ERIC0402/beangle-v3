[#ftl]
[@b.head/]
[#include "nav.ftl"/]
<table  class="indexpanel">
	<tr>
		<td class="index_view">
		[@b.form name="userSearchForm"  action="!search" target="userlist" title="ui.searchForm" theme="search"]
			[@b.textfields names="user.name;登陆名"/]
			[@b.textfields names="user.fullName;姓名" /]
			<input type="hidden" value="${paramId!}" name="paramId"  />
			<input type="hidden" value="${isSingle!}" name="isSingle"  />
		[/@]
		</td>
		<td class="index_content">[@b.div id="userlist" href="!search?paramId=${paramId!}&isSingle=${isSingle!}" /]</td>
	</tr>
</table>
