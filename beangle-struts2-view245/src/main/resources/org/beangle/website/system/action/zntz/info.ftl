[#ftl]
[@b.head/]
[@b.toolbar title="站内通知"]bar.addBack();[/@]
[@b.tabs]
	[@b.tab label="站内通知"]
		[@b.form action="!save" title="基本信息" theme="list"]
			[@b.field label="主题" ]
				${zntz.title!}
			[/@]
			[@b.field label="内容" ]
				<div style="padding-left:125px;line-height:25px;">
		            [#if zntz.content??]
		                ${zntz.content?replace("\n","<br>")}
		            [/#if]
                </div>
			[/@]
			[#if zntz.filePath??]
				[@b.field label="附件" ]	
		           <a href="${base}/common/file.action?method=downFile&folder=${zntz.filePath!}&fileName=${zntz.fileName?url('utf-8')}" target="_blank">
		               ${zntz.fileName!}
		           </a>
				[/@]
			[/#if]
		[/@]
	[/@]
[/@]
[@b.foot/]