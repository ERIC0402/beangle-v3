[#ftl]
[@b.head/]
<script src="${base}/static/scripts/uploadify/upload.js"></script>
<script src="${base}/static/scripts/uploadify/jquery.uploadify.v2.1.4.js"></script>
<script src="${base}/static/scripts/uploadify/swfobject.js"></script>
<link href="${base}/static/scripts/uploadify/uploadify.css" rel="stylesheet" type="text/css"/>
[#assign formTitle]字典树[#if dictTree.id??]修改[#else]添加[/#if][/#assign]
[@b.toolbar title=formTitle]bar.addBack();[/@]
[@b.form action="!save" title="字典树信息" theme="list"]
	[@b.textfield label="common.name" name="dictTree.name" value="${dictTree.name!}" style="width:200px;"  required="true" maxlength="100" /]
	[@b.select label="上级字典" name="parent.id" value=(dictTree.parent.id)! style="width:200px;"  items=parents option="id,description" empty="..."/]
	[@b.textfield label="同级顺序号" name="indexno" value="${dictTree.indexno!}" required="true" maxlength="3" check="match('integer').range(1,999)" /]
	[@b.textfield label="代码" name="dictTree.dm" value="${dictTree.dm!}" maxlength="32" check="match('varName')" comment="只能包含以下字符：a-z A-Z _"/]
	[@b.field label="common.status" required="true"]
		<select  name="dictTree.enabled" style="width:100px;" >
			<option value="true" [#if dictTree.enabled]selected="selected"[/#if]>有效</option>
			<option value="false" [#if !dictTree.enabled]selected="selected"[/#if]>无效</option>
		</select>
	[/@]
	[@b.textarea label="common.remark"  name="dictTree.remark" maxlength="50" value=dictTree.remark! rows="2" cols="40"/]
	[@b.formfoot]
		[@b.reset/]&nbsp;&nbsp;
		[@b.submit value="action.submit" /]
		[@b.redirectParams/]
		<input type="hidden" name="dictTree.id" value="${dictTree.id!}" />
		<input type="hidden" name="iconPath" value="${iconPath!}" />
	[/@]
[/@]
<script>
</script>
[@b.foot/]