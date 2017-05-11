[#ftl]
[@b.head/]
<script src="${base}/static/scripts/uploadify/upload.js"></script>
<script src="${base}/static/scripts/uploadify/jquery.uploadify.v2.1.4.js"></script>
<script src="${base}/static/scripts/uploadify/swfobject.js"></script>
<link href="${base}/static/scripts/uploadify/uploadify.css" rel="stylesheet" type="text/css"/> 
[@b.toolbar title="info.moduleUpdate"]bar.addBack();[/@]
[#assign userMsg]${b.text("entity.menu")}[/#assign]
[#assign labelInfo]${b.text("ui.editForm",userMsg)}[/#assign]
[@b.form action="!save" title=labelInfo theme="list"]
	[@b.field label="菜单配置" required="true"]
		<select  name="menu.profile.id" style="width:150px;" >
		[#list profiles as profile]
		<option value="${profile.id}" [#if ((menu.profile.id)!0) ==profile.id]selected="selected"[/#if]>${profile.name}</option>
		[/#list]
		</select>
	[/@]
	[@b.textfield label="common.name" name="menu.name" value="${menu.name!}" style="width:200px;"  required="true" maxlength="100" /]
	[@b.textfield label="标题" name="menu.title" value="${menu.title!}" style="width:200px;" required="true" maxlength="50"/]
	[@b.select label="上级菜单" name="parent.id" value=(menu.parent.id)! style="width:200px;"  items=parents option="id,description" empty="..."/]
	[@b.select label="来源系统" name="menu.subSystem.id" value=(menu.subSystem.id)! style="width:200px;"  items=subSystems option="id,name" empty="本系统"/]
	[@b.textfield label="同级顺序号" name="indexno" value="${menu.indexno!}" required="true" maxlength="2" check="match('integer').range(1,100)" /]
	[@b.field label="common.status" required="true"]
		<select  name="menu.enabled" style="width:100px;" >
			<option value="true" [#if menu.enabled]selected="selected"[/#if]>${b.text("action.activate")}</option>
			<option value="false" [#if !menu.enabled]selected="selected"[/#if]>${b.text("action.freeze")}</option>
		</select>
	[/@]
	[@b.field label="图片" ]	
		<img id="albumImg" src="${base}/common/file.action?method=downIconById&menuId=${menu.id!}" style="display: none"/>
        <div style="margin:3px 0;padding-left:150px;">
             <input id="file_upload" name="file_upload" type="file" />
        </div>
	[/@]
	[@b.textfield label="menu.entry"  name="menu.entry" value="${menu.entry!}" maxlength="100" /]
	[@b.select2 label="使用资源" name1st="Resources" name2nd="resourceId" items1st=resources?sort_by("name") items2nd= menu.resources?sort_by("name") option="id,description"/]
	[@b.textarea label="common.remark"  name="menu.remark" maxlength="50" value=menu.remark! rows="2" cols="40"/]
	[@b.formfoot]
		[@b.reset/]&nbsp;&nbsp;
		[@b.submit value="action.submit" /]
		[@b.redirectParams/]
		<input type="hidden" name="menu.id" value="${menu.id!}" />
		<input type="hidden" name="iconPath" value="${iconPath!}" />
	[/@]
[/@]
<script>
$(function (){
        uploadimg({
            dir:"menu",
            upBtn:"file_upload",
            imgNmae:"iconPath",
            showImg:"albumImg",
            sizeLimit:1*1024*1024,
            width:75
        },false);
    });

    
//     [#if menu.icon??]
    $("#albumImg").show();
//     [/#if]
</script>
[@b.foot/]