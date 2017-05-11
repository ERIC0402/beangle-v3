[#ftl]
[@b.head/]
<script type="text/javascript">
	bg.ui.load("tabletree");
	defaultColumn=1;
</script>
[@b.grid  items=dictTrees var="dictTree" sortable="false"]
[@b.gridbar title="菜单列表"]
	function activate(isActivate){
		return action.multi("activate","确定操作?","isActivate="+isActivate);
	}
	function preview(){window.open("${b.url('!preview')}?${b.paramstring}");}
	function redirectTo(url){window.open(url);}
	bar.addItem("${b.text("action.new")}",action.add());
	bar.addItem("${b.text("action.edit")}",action.edit());
	bar.addItem("${b.text("action.freeze")}",activate(0),'${b.theme.iconurl('actions/freeze.png')}');
	bar.addItem("${b.text("action.activate")}",activate(1),'${b.theme.iconurl('actions/activate.png')}');
	bar.addItem("${b.text("action.export")}",action.exportData("code:排序,name:common.name,dm:代码,enabled:common.status,remark:common.remark",null,"&fileName=字典树信息"));
	bar.addItem("${b.text("action.delete")}",action.remove());
[/@]
	[@b.row]
		<tr [#if dictTree??] title="入口及备注:${dictTree.entry!} ${(dictTree.remark?html)!}" id="${dictTree.code}"[/#if]>
		[@b.boxcol /]
		[@b.col property="name" title="名称"]
		<div class="tier${dictTree.depth}" align="left">
		[#if (dictTree.children?size==0)]
			<a href="#" class="doc"/>
		[#else]
			<a href="#" class="folder_open" id="${dictTree.code}_folder" onclick="toggleRows(this)" >   </a>
		[/#if]
			${dictTree.code} ${dictTree.name}
		</div>
		[/@]
		[@b.col property="dm" width="10%" title="代码"/]
		[@b.col property="remark" width="20%" title="备注"/]
		[@b.col property="enabled" width="10%" title="common.status" align="center"][@c.sfyx dictTree.enabled/][/@]
		</tr>
	[/@]
[/@]
<script type="text/javascript">
   //展开所有菜单
   displayAllRowsFor(1);
</script>
[@b.foot/]