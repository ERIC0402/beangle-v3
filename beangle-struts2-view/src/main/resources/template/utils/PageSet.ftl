[#ftl]
[#macro body title href invoker]
[@b.head/]
	[@c.colorboxJsAndCss/]
	<script type="text/javascript" src="${base}/static/scripts/beangle/PageSet.js"></script>
	<script type="text/javascript">
		$(function (){
			new PageSet("iframe", "${invoker}");
		});
	</script>
	<iframe id="iframe" src="${href}" style="border: 0; width: 100%; height: 800px;"></iframe>
	[#nested/]
[@b.foot/]
[/#macro]

[#macro tr title]
	[@fc.group title=title]
	<tr>
		<td colspan="2">[#nested/]</td>
	</tr>
	[/@]
[/#macro]

[#macro linkTable links type]
	<table class="table_style_1 table_style_4 linkTable">
		<thead>
			<tr>
				<th>名称</th>
				<th>地址</th>
				<th>操作</th>
			</tr>
		</thead>
		[#list links as v]
			[@linkTableTr v type v_index/]
		[/#list]
		[#if links?size == 0]
			[@linkTableTr (v!) type/]
		[/#if]
	</table>
	<textarea id="linkTableTrTemplate${type}" style="display: none;">[@linkTableTr (v!) type "{v}"/]</textarea>
[/#macro]

[#macro linkTableTr link type index=0]
	<tr>
		<td align="center">
			<input type="hidden" name="${type}" value="${index}"/>
			[#assign name=type+"${index}"/]
			<input name="${name}.name" value="${link[0]!}" style="width: 90%"/>
		</td>
		<td align="center">
			<input name="${name}.link" value="${link[1]!}"  style="width: 90%"/>
		</td>
		<td align="center">
			<input type="button" value="删除" onclick="pageSet.delTr(this, 'linkTableTrTemplate${type}')" />
			<input type="button" value="上移" onclick="pageSet.up(this)" />
			<input type="button" value="添加" onclick="pageSet.addTr(this, 'linkTableTrTemplate${type}')" />
		</td>
	</tr>
[/#macro]