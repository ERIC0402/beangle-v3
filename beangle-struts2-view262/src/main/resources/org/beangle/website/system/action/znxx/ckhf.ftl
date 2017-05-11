[#ftl]
[@b.head/]
[@b.toolbar title="站内消息回复"]bar.addBack();[/@]
[@b.tabs]
	[@b.tab label="站内消息回复"]
		[@b.form action="!save" title="回复信息" theme="list"]
			[@b.field label="回复"]
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
		[/@]
	[/@]
[/@]

[@b.foot/]