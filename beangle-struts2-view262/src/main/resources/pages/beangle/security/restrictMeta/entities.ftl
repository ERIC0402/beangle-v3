[#ftl]
[@b.head/]
[@b.div id="entities"]
	[@b.messages/]
	<em>业务数据一览</em>&nbsp;&nbsp;&nbsp;[@b.a href="!editEntity" ]新增业务数据[/@]
	[@sj.accordion style="height:600px;"]
	[#list entities as entity]
		[@sj.accordionItem title="${entity.name}"]
			[@b.div id="restrict_entity_${entity.id}"  style="min-height:200px" href="!entityInfo?entity.id=${entity.id}"/]
		[/@]
	[/#list]
	[/@]
[/@]
[@b.foot/]