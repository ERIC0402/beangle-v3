[#ftl/]
[#if tag.id??]
<div class="ui-widget messages" id="${tag.id}">
[#if tag.hasActionMessages()]
<div class="actionMessage">
	<div> 
		<p>
			[#list tag.actionMessages as message]
			<div style="line-height: 18px;">
				<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
				${message!}
			</div>
			[/#list]
		</p>
	</div>
</div>
[/#if]
[#if tag.hasActionErrors()]
<div class="actionError">
	<div> 
		<p>
			[#list tag.actionErrors as message]
				<div style="line-height: 18px;">
				<span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>
				${message!}
				</div>
			[/#list]
		</p>
	</div>
</div>
[/#if]
</div>
[#if tag.parameters['slash']??]
<script>
	bg.message("${tag.id}", "${tag.parameters['slash']}");
	//setTimeout(function(){var msgdiv=document.getElementById('${tag.id}');if(msgdiv) msgdiv.style.display="none";},${tag.parameters['slash']}*1000);
</script>
[/#if]
[/#if]