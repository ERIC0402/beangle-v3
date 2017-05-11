[#ftl]
[@b.head/]
[#include "../comm.ftl"/]
[@b.form action="!search" name="zntzSearchForm" id="zntzSearchForm"]
[@b.grid  items=zntzs var="zntz" sortable="true" filterable="true"]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.modify")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
		[@b.gridfilter property="state"]
			<select  name="zntz.state" style="width:95%;" onchange="bg.form.submit(this.form)">
				<option value="" [#if (Parameters['zntz.state']!"")=""]selected="selected"[/#if]>..</option>
				<option value="true" [#if (Parameters['zntz.state']!"")="true"]selected="selected"[/#if]>有效</option>
				<option value="false" [#if (Parameters['zntz.state']!"")="false"]selected="selected"[/#if]>无效</option>
			</select>
		[/@]
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col width="20%" property="title" title="标题" ]
			[#if zntz.title??&&zntz.title?length>12]
				${zntz.title[0..12]}...
			[#else]
				${zntz.title!}
			[/#if]
		[/@]
		[@b.col width="40%" property="content" title="内容"]
			[#if zntz.content??&&zntz.content?length>27]
				${zntz.content[0..27]}...
			[#else]
				${zntz.content!}
			[/#if]
		[/@]
		[@b.col width="20%" property="time" title="发送时间" align="center" ]
			${(zntz.time?string("yyyy-MM-dd HH:mm:ss"))!}
		[/@]
		[@b.col width="10%" property="state" title="状态" align="center"]
			[@shortEnableInfo zntz.state "有效" "无效"/]
		[/@]
		[@b.col width="10%" title="下载文件" align="center"]
			[#if zntz.filePath??]
				[@downLoadFile "${zntz.filePath!}" "${zntz.fileName!}"/]
			[/#if]
		[/@]
	[/@]
[/@]
[/@]
[@b.foot/]