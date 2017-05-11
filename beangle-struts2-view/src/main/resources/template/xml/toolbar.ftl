[#ftl/]
[#--
<div id="${tag.id}"></div>
<script type="text/javascript">
bar = bg.ui.toolbar("${tag.id}"[#if tag.title??],'${tag.title?replace("'","\"")}'[/#if]);
${tag.body}
bar.addHr();
</script>

<div id="${tag.id}" class="title_style_1"></div>
--]
<script type="text/javascript">
$(function(){
	$(".formTitleLabel").html('${tag.title!}');
	var bar = bg.ui.toolbar("${tag.id}");
	
	${tag.body}
});

</script>