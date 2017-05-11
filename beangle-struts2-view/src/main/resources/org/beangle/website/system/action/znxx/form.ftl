[#ftl]
[@b.head/]
<script src="${base}/static/scripts/uploadify/upload.js"></script>
<script src="${base}/static/scripts/uploadify/jquery.uploadify.v2.1.4.js"></script>
<script src="${base}/static/scripts/uploadify/swfobject.js"></script>
<link href="${base}/static/scripts/uploadify/uploadify.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="${base}/static/scripts/chosen/chosen.css" />
<script language="JavaScript" type="text/JavaScript" src="${base}/static/scripts/chosen/chosen.jquery.js"></script>
[@b.toolbar title="站内消息"]bar.addBack();[/@]
[@b.tabs]
	[@b.tab label="站内消息"]
		[@b.form action="!save" title="基本信息" theme="list"]
			[@b.field label="接收人" required="true"]
				<select id="userIds" multiple="true" name="znxx.receives" style="width:400px;">
					[#list znxx.receives?sort_by("name") as item]
						<option value="${item.id!}" selected>${item.fullname!}</option>
					[/#list]
				</select>
				<input style="margin:auto"  type="button" onClick="listUsers(this);" value="选择"/>
			[/@]
			[@b.textfield name="znxx.title" label="主题" value="${znxx.title!}" required="true" style="width:380px;" maxlength="100" comment="长度不超过100个字符"/]
			[@b.textarea label="内容" cols="50" required="true" rows="6" name="znxx.content" value="${znxx.content!}" maxlength="1000"/]
			[@b.field label="附件" ]	
                <div style="margin:3px 0;">
                    <input id="file_upload" name="file_upload" type="file" />
                </div>
			[/@]
			[@b.textfield id="fileName" name="znxx.fileName" label="文件名称" value="${znxx.fileName!}" readonly="true" maxlength="100"/]	
			
			[@b.formfoot]
				<input type="hidden" id="filePath" name="znxx.filePath" value="${znxx.filePath!}"/>
				<input type="hidden" name="oldFileName" value="${znxx.filePath!}"/>
				<input type="hidden" name="znxx.id" value="${znxx.id!}" />
				[@b.redirectParams/]
				[@b.reset/]&nbsp;&nbsp;
				<input type="button" value="${b.text("action.submit")}" onClick="submit2(this.form);"/>
			[/@]
		[/@]
	[/@]
[/@]
<script language="JavaScript">
	function submit2(form){
		var res = null;
        bg.ui.load("validity");
        jQuery.validity.start();
        $("#userIds").require("请填写接收人");
        res = jQuery.validity.end().valid;
        if(false == res) {
            return false;
        }
        if(!confirm("提交后将会把消息发送到接收人，您是否确定要提交")){
        	return false;
        }
		bg.form.submit(form,form.action,form.target,null,true);
	}
	function mustSelectUser(){
		return $('#userIds').val() !="";
	}
	
	$(function (){
        uploadFile({
            dir:"znxx/${currentDate?string("yyyyMM")}",
            upBtn:"file_upload",
            filePath:"filePath",
            fileName:"fileName",
            sizeLimit:10*1024*1024,
            width:300
        });
    });
    
    function listUsers(ele) {
		jQuery(ele).colorbox(
		{
			iframe : true,
			width : "800px",
			height : "600px",
			href : "${base}/system/select-user.action?paramId=userIds"
		});
	}
	jQuery("#userIds").chosen();
	
	function setResourse() {
		jQuery("#userIds").trigger("liszt:updated");
		jQuery("#userIds").chosen();
		jQuery.colorbox.close();
	}

</script>
[@b.foot/]