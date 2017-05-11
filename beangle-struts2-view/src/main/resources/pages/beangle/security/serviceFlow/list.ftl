[#ftl]
[@b.head/]
<script type="text/javascript">
	bg.ui.load("tabletree");
	defaultColumn=1;
</script>
[@b.grid  items=serviceFlows var="serviceFlow" sortable="false"]
[@b.gridbar title="菜单列表"]
	function activate(isActivate){
		return action.multi("activate","确定操作?","isActivate="+isActivate);
	}
	function preview(){window.open("${b.url('!preview')}?${b.paramstring}");}
	function redirectTo(url){window.open(url);}
	bar.addItem("${b.text("action.new")}",action.add());
	bar.addItem("${b.text("action.edit")}",action.edit());
	bar.addItem("${b.text("action.activate")}",activate(1),'${b.theme.iconurl('actions/activate.png')}');
	bar.addItem("${b.text("action.freeze")}",activate(0),'${b.theme.iconurl('actions/freeze.png')}');
	bar.addItem("${b.text("action.delete")}",action.remove());
[/@]
	[@b.row]
		<tr [#if serviceFlow??] title="入口及备注:${(serviceFlow.entry)!}，${(serviceFlow.describe?html)!}" id="${(serviceFlow.code)!}"[/#if]>
		[@b.boxcol /]
		[@b.col property="title" title="标题"]
		<div class="tier${serviceFlow.depth}" align="left">
		[#if (serviceFlow.children?size==0)]
			<a href="#" class="doc"/>
		[#else]
			<a href="#" class="folder_open" id="${(serviceFlow.code)!}_folder" onclick="toggleRows(this)" >   </a>
		[/#if]
			${(serviceFlow.code)!} ${serviceFlow.title}
		</div>
		[/@]
		[@b.col property="describe" width="35%" title="描述"/]
		[@b.col property="enabled" width="10%" title="common.status" align="center"]
			[@c.sfyx enabled=serviceFlow.enabled yes="激活" no="冻结"/]
		[/@]
		</tr>
	[/@]
[/@]
<script type="text/javascript">
   //展开所有菜单
   displayAllRowsFor(1);
</script>
[@b.foot/]