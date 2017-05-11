[#ftl]
[@b.head/]
[@b.grid  items=dictTypes var="dictType"]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.modify")}",action.edit());
		bar.addItem("${b.text("action.delete")}",action.remove());
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col width="10%" property="id" title="ID"/]
		[@b.col width="30%" property="code" title="common.code" /]
		[@b.col width="25%" property="name" title="名称" /]
		[@b.col width="25%" property="enName" title="英文名称" /]
		[@b.col width="10%" property="enabled" title="common.status"][#if dictType.enabled]启用[#else]禁用[/#if][/@]
	[/@]
[/@]
[@b.foot/]