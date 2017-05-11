[#ftl]
[@b.head/]
<script src="${base}/static/scripts/uploadify/upload.js"></script>
<script src="${base}/static/scripts/uploadify/jquery.uploadify.v2.1.4.js"></script>
<script src="${base}/static/scripts/uploadify/swfobject.js"></script>
<link href="${base}/static/scripts/uploadify/uploadify.css" rel="stylesheet" type="text/css"/>
[#assign formTitle]业务流[#if serviceFlow.id??]修改[#else]添加[/#if][/#assign]
[@b.toolbar title=formTitle]bar.addBack();[/@]
[@b.form action="!save" title="业务流信息" theme="list"]
	[@b.textfield label="标题" name="serviceFlow.title" value="${serviceFlow.title!}" style="width:200px;"  required="true" maxlength="100" /]
	[@b.select label="上级节点" name="parent.id" value=(serviceFlow.parent.id)! style="width:200px;"  items=parents option="id,description" empty="..."/]
	[@b.textfield label="同级顺序号" name="indexno" value="${serviceFlow.indexno!}" style="width:200px;" required="true" maxlength="3" check="match('integer').range(1,999)" /]
	[@b.textfield label="入口" name="serviceFlow.entry" value="${serviceFlow.entry!}" style="width:200px;"  maxlength="200" check="match('feihanzi')"/]
	[@b.radios label="状态" name="serviceFlow.enabled" value=serviceFlow.enabled required="true" items="1:激活,0:冻结"/]
	[@b.textarea label="common.remark"  name="serviceFlow.describe" maxlength="50" value=serviceFlow.describe! rows="2" cols="40"/]
	[@b.formfoot]
		[@b.reset/]&nbsp;&nbsp;
		[@b.submit value="action.submit" /]
		[@b.redirectParams/]
		<input type="hidden" name="serviceFlow.id" value="${serviceFlow.id!}" />
	[/@]
[/@]
<script>
</script>
[@b.foot/]