[#ftl/]
[#if tag.id??]
<div class="ui-widget" id="${tag.id}" style="display: none">
[#if tag.hasActionMessages()]
<div class="actionMessage">
	<div class="ui-state-highlight ui-corner-all"> 
		[#list tag.actionMessages as message]
		<span class="ui-icon ui-icon-info" style="float: left; margin-right: 0.3em;"></span>
		<span>${message!}</span>[#if message_has_next]<br/>[/#if]
		[/#list]
	</div>
</div>
[/#if]
[#if tag.hasActionErrors()]
<div class="actionError">
	<div class="ui-state-error ui-corner-all" style="padding: 0.1em 0.7em;"> 
		[#list tag.actionErrors as message]
		<span class="ui-icon ui-icon-alert" style="float: left; margin-right: 0.3em;margin-top:7px;"></span>
		<span>${message!}</span>[#if message_has_next]<br/>[/#if]
		[/#list]
	</div>
</div>
[/#if]
</div>
[#if tag.parameters['slash']??]
<script>
	//jQuery("${tag.id}").fadeOut("fast",function(){alert(1)});
	//setTimeout(function(){var msgdiv=document.getElementById('${tag.id}');if(msgdiv) msgdiv.style.display="none";},${tag.parameters['slash']}*1000);
	$(function (){
		var os = {show: {effect: 'drop', direction: "up" }, hide: { effect: 'drop', direction: "up" }, position: "top", title:"提示信息", minHeight: 30,  resizable:false};
		$( "#${tag.id}" ).dialog(os);
		setTimeout(function() {$( "#${tag.id}" ).dialog( "close" );}, ${tag.parameters['slash']}*1000 );
	});
</script>
[/#if]
[/#if]