[#ftl/]
<div class="title_style_1 navmenu2" id="${tag.id}">
	<div class="top_tool_bar top_tool_bar_navmenu" style="float: none">
		${tag.body!}
	</div>
	<div class="ClearDiv"></div>
</div>
[#--
不能加空行
<div class="BlankLine1"></div>
--]
<script type="text/javascript">
	$("#${tag.id} .span_1").last().remove();
	[#if tag.target??]
	$("#${tag.id} a").click(function (){
		$("#${tag.id} a").addClass("first_a");
		$(this).removeClass("first_a");
	}).first().click();
	[/#if]
</script>
[#--
<div [#if tag.id??] id="${tag.id}"[/#if]>${tag.body!}</div>
--]
[#--
<div [#if tag.id??] id="${tag.id}"[/#if] class="navmenu" ><ul>${tag.body!}</ul></div>
--]