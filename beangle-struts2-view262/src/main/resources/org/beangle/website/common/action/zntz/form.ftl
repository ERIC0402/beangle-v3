[#ftl]
[@b.head/]
<script src="${base}/static/scripts/uploadify/upload.js"></script>
<script src="${base}/static/scripts/uploadify/jquery.uploadify.v2.1.4.js"></script>
<script src="${base}/static/scripts/uploadify/swfobject.js"></script>
<link href="${base}/static/scripts/uploadify/uploadify.css" rel="stylesheet" type="text/css"/> 
[@b.toolbar title="站内通知"]bar.addBack();[/@]
[@b.tabs]
	[@b.tab label="站内通知"]
		[@b.form action="!save" title="基本信息" theme="list"]
			[@b.textfield name="zntz.title" label="主题" value="${zntz.title!}" required="true" style="width:380px;" maxlength="100" comment="长度不超过100个字符"/]
			[@b.textarea label="内容" cols="50" rows="6" required="true" name="zntz.content" value="${zntz.content!}" maxlength="1000"/]
			[@b.field label="附件" ]	
                <div style="margin:3px 0;">
                    <input id="file_upload" name="file_upload" type="file" />
                </div>
			[/@]
			[@b.textfield id="fileName" name="zntz.fileName" label="文件名称" value="${zntz.fileName!}" readonly="true" maxlength="100"/]	
			[@b.radios label="是否有效"  name="zntz.state" value=zntz.state items="1:有效,0:无效"/]
			
			[@b.formfoot]
				<input type="hidden" id="filePath" name="zntz.filePath" value="${zntz.filePath!}"/>
				<input type="hidden" name="oldFileName" value="${zntz.filePath!}"/>
				<input type="hidden" name="zntz.id" value="${zntz.id!}" />
				[@b.redirectParams/]
				[@b.reset/]&nbsp;&nbsp;
				<input type="button" value="${b.text("action.submit")}" onClick="submit2(this.form);"/>
			[/@]
		[/@]
	[/@]
[/@]
<script language="JavaScript">
	function submit2(form){
		bg.form.submit(form,form.action,form.target,null,true);
	}
	
	$(function (){
        uploadFile({
            dir:"zntz/${currentDate?string("yyyyMM")}",
            upBtn:"file_upload",
            filePath:"filePath",
            fileName:"fileName",
            sizeLimit:10*1024*1024,
            width:300
        });
    });

</script>
[@b.foot/]