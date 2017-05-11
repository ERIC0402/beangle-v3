[#ftl]
[#include "/template/form/comm.ftl"]
[#import "/template/form/components.ftl" as fc]
[@b.head/]
[@b.toolbar title="选项"]bar.addBack();[/@]

[#macro showClassTable className]
[@group title=className]
<table id="propertyTable" class="table_style_1" style="margin: 5px 0px;">
	<thead>
		<tr>
			<th>序号</th>
			<th style="width: 100px;">原标题</th>
			<th>新标题</th>
			<th>是否可编辑</th>
			<th>是否必填</th>
			<th>是否在列表显示</th>
			<th>宽度（百分比）</th>
			<th>默认值</th>
		</tr>
	</thead>
	<tbody class="sort_tbody">
		[#list list?sort_by('px') as v]
		[#if v.className == className]
		<tr class="sorttr" style="">
			<td align="center">
				<input type="hidden" name="classPropertyConfig" value="${v_index}" />
				<input type="hidden" name="classPropertyConfig${v_index}.className" value="${v.className!}" />
				<input type="hidden" name="classPropertyConfig${v_index}.propertyName" value="${v.propertyName!}" />
				<input type="hidden" name="classPropertyConfig${v_index}.label" value="${v.label!}" />
				<input type="hidden" class="pxinput" name="classPropertyConfig${v_index}.px" value="${v.px!}" />
				<label class="pxlabel">${v.px}</label>
			</td>
			<td align="center">
				${v.label!'&nbsp;'}
			</td>
			<td align="center">
				<input value="${v.newLabel!}" name="classPropertyConfig${v_index}.newLabel" style="width: 200px;" />
			</td>
			<td align="center">
				[@fc.switch name="classPropertyConfig${v_index}.editable" value=v.editable/]
			</td>
			<td align="center">
				[@fc.switch name="classPropertyConfig${v_index}.required" value=v.required notnull=v.notnull/]
			</td>
			<td align="center">
				[@fc.switch name="classPropertyConfig${v_index}.listable" value=v.listable/]
			</td>
			<td align="center"><input name="classPropertyConfig${v_index}.width" value="${v.width!}" style="width: 60px;"/></td>
			<td align="center">
				<input value="${v.defaultValue!}" name="classPropertyConfig${v_index}.defaultValue" style="width: 100px;" />
			</td>
		</tr>
		[/#if]
		[/#list]
	</tbody>
</table>
[/@]
[/#macro]
[@b.form action="!saveConfig" theme="form"]
	[#assign lastClassName = ""/]
	[#list list?sort_by('className') as v]
		[#if v.className != lastClassName]
			[@showClassTable v.className/]
			[#assign lastClassName = v.className/]
		[/#if]
	[/#list]
	[@table]
		[@b.formfoot]
			[@b.reset/]&nbsp;&nbsp;
			[@b.submit value="action.submit" /]
			[@b.redirectParams/]
		[/@]
	[/@]
[/@]
<script type="text/javascript">
	$(function (){
		$("#propertyTable .sort_tbody").sortable({
			stop: function (){
				var i = 1;
				$(".sort_tbody tr").each(function (){
					var px = i++;
					$(this).find(".pxlabel").html(px);
					$(this).find(".pxinput").val(px);
				});
			}
		}).disableSelection();
	});
</script>
[@b.foot/]