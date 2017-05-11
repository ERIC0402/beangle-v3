[#ftl/]
[@b.head/]
[@b.form theme="list" action="!saveEntity" title="业务数据" noTooltip="1"]
	[@b.textfield name="entity.name" value=entity.name! label="common.name"/]
	[@b.textfield name="entity.remark" value=entity.remark! label="common.remark"/]
	[@b.select2 label="数据限制域" name1st="restrictEntities"  name2nd="fieldId" required="true" items1st=fields items2nd=entity.fields/]
	[@b.formfoot]
		<input type="hidden" name="entity.id" value="${(entity.id)!}" />
		<input type="hidden" class="noTooltip" value="1" />
		[@b.redirectParams/]
		[@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit" /]
	[/@]
[/@]
[@b.foot/]
	