[#ftl]
[@b.head/]
[@b.form action="!search"]
[@b.toolbar title="回复情况查看--${znxx.title!}"]bar.addBack();[/@]

[@b.grid items=znxx.receives?sort_by("name") var="item"]
	[@b.row]
		[@b.col width="20%" title="接收人"]${item.fullname!}[/@]
		[@b.col width="20%" title="回复情况" ]
			[#if yhfs??&&yhfs?seq_contains(item.id)]
				<a href="${base}/system/znxx!ckhf.action?znxx.id=${znxx.id!}&user.id=${item.id!}" target="_blank">
					已回复
				</a>
			[#else]
				未回复
			[/#if]
		[/@]
	[/@]
[/@]
[/@]
[@b.foot/]