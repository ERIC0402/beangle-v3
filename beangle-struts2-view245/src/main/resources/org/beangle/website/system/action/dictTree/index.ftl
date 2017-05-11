[#ftl]
[@b.head/]
<table  class="indexpanel">
	<tr>
		<td class="index_view">
			[@b.form action="!search?orderBy=dictTree.code" title="ui.searchForm" target="dictTreelist" theme="search"]
				[@b.textfields names="dictTree.code;排序,dictTree.name;名称"/]
				[@b.select name="dictTree.enabled" label="common.status" items={'true':'有效','false':'无效'}  empty="..."/]
			[/@]
		</td>
		<td class="index_content">
		[@b.div  href="!search?orderBy=code" id="dictTreelist"/]
		</td>
	</tr>
</table>
[@b.foot/]
