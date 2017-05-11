[#ftl]
[@b.head/]
[@b.toolbar title="站内消息回复"]bar.addBack();[/@]
[@b.tabs]
	[@b.tab label="站内消息回复"]
		[@b.form action="!save" title="基本信息" theme="list"]
			[@b.field label="主题"]
				${znxx.title!}
			[/@]
			[@b.field label="内容"]
                <div style="padding-left:125px;line-height:25px;">
		            [#if znxx.content??]
		                ${znxx.content?replace("\n","<br>")}
		            [/#if]
                </div>
			[/@]
			[#if znxx.filePath??]
				[@b.field label="附件" ]	
		           <a href="${base}/common/file.action?method=downFile&folder=${znxx.filePath!}&fileName=${znxx.fileName?url('utf-8')}" target="_blank">
		               ${znxx.fileName!}
		           </a>
				[/@]
			[/#if]
			[@b.textarea label="回复内容" required="true" cols="50" rows="6" name="znxxhf.replyContent" value="${znxxhf.replyContent!}" maxlength="1000"/]
			[@b.field label="前次回复"]
				[#if !znxxhfs??||znxxhfs?size==0]无[/#if]
                [#list znxxhfs as item]
                	<div style="padding-left:125px;line-height:25px;">
	                	<b>${(item.time?string("yyyy-MM-dd HH:mm:ss"))!}：</b>
	                	<br>
		                [#if item.replyContent??]
		                	${item.replyContent?replace("\n","<br>")}
		                [/#if]
                	</div>
                [/#list]
			[/@]
			[@b.formfoot]
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
        if(!confirm("提交后不可修改，您是否确定要回复")){
        	return false;
        }
		bg.form.submit(form,form.action,form.target,null,true);
	}
</script>
[@b.foot/]