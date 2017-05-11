[#ftl]
[@b.head/]
<script src="${base}/static/scripts/uploadify/upload.js"></script>
<script src="${base}/static/scripts/uploadify/jquery.uploadify.v2.1.4.js"></script>
<script src="${base}/static/scripts/uploadify/swfobject.js"></script>
<link href="${base}/static/scripts/uploadify/uploadify.css" rel="stylesheet" type="text/css"/> 
[@b.toolbar title="系统配置"][/@]
[@b.form action="!save?t=${time!}" title="系统配置" theme="list"]
	[@b.textfield label="标题" name="sc.title" value="${sc.title!('资源库')}" style="width:300px;" required="true" maxlength="100" /]
	[@b.field label="标题图片（<1M；高度：76像素）" ]	
		<img id="albumImg" src="${base}/common/file.action?method=downIconBySCId&scId=${sc.id!}&t=${time!}" style="display: none"/>
        <div>
             <input id="file_upload" name="file_upload" type="file" />
        </div>
	[/@]
	[@b.textarea label="页脚信息"  name="sc.footMsg" maxlength="300" value=sc.footMsg! rows="3" cols="40" comment="如果想前台显示‘©’符号请输入‘&+copy;’"/]
	[@b.formfoot]
		[@b.reset/]&nbsp;&nbsp;
		[@b.submit value="action.submit" /]
		[@b.redirectParams/]
		<input type="hidden" name="sc.id" value="${sc.id!}" />
		<input type="hidden" name="iconPath" value="${iconPath!}" />
	[/@]
[/@]
<script>
$(function (){
        uploadimg({
            dir:"system",
            upBtn:"file_upload",
            imgNmae:"iconPath",
            showImg:"albumImg",
            sizeLimit:1*1024*1024
        },false);
    });
    
    $("#albumImg").show();
//     [#if sc.headImage??]
//     [/#if]
</script>
[@b.foot/]