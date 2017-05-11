[#ftl]
[@b.head/]
[#include "../comm.ftl"/]
[@b.form action="!search" name="znxxSearchForm" id="znxxSearchForm"]
[@b.grid  items=znxxs var="znxx" sortable="true" filterable="true"]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("查看回复情况",ck("ckhfqk"));
		bar.addItem("${b.text("action.delete")}",action.remove());
		
		function NamedFunction(name,func){
			this.name=name;
			this.func=func;
		}
			
		function ck(methodName){
			return new NamedFunction(methodName,function(){
				try {
					var form = action.getForm();
					action.submitIdAction(methodName, null, null,true);
				}catch(e){
					bg.alert(e)
				}
			});	
	    }
		[@b.gridfilter property="state"]
			<select  name="znxx.reply" style="width:95%;" onchange="bg.form.submit(this.form)">
				<option value="" [#if (Parameters['znxx.reply']!"")=""]selected="selected"[/#if]>..</option>
				<option value="true" [#if (Parameters['znxx.reply']!"")="true"]selected="selected"[/#if]>已回复</option>
				<option value="false" [#if (Parameters['znxx.reply']!"")="false"]selected="selected"[/#if]>未回复</option>
			</select>
		[/@]
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col width="20%" property="title" title="标题" ]
			[#if znxx.title??&&znxx.title?length>12]
				${znxx.title[0..12]}...
			[#else]
				${znxx.title!}
			[/#if]
		[/@]
		[@b.col width="40%" property="content" title="内容"]
			[#if znxx.content??&&znxx.content?length>27]
				${znxx.content[0..27]}...
			[#else]
				${znxx.content!}
			[/#if]
		[/@]
		[@b.col width="20%" property="time" title="发送时间" align="center" ]
			${(znxx.time?string("yyyy-MM-dd HH:mm:ss"))!}
		[/@]
		[@b.col width="10%" property="reply" title="是否回复" align="center"]
			[@shortEnableInfo znxx.reply  "已回复" "未回复"/]
		[/@]
		[@b.col width="10%" title="下载文件" align="center"]
			[#if znxx.filePath??]
				[@downLoadFile "${znxx.filePath!}" "${znxx.fileName!}"/]
			[/#if]
		[/@]
	[/@]
[/@]
[/@]
[@b.foot/]