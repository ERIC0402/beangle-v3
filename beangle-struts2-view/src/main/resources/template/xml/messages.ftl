[#ftl/]
[#assign messages=""/]
[#if tag.id??]
	[#if tag.hasActionMessages()]
		[#assign shortCutFunction="success"/]
		[#assign title="提示消息"/]
		[#assign timeOut=5000/]
		[#list tag.actionMessages as message]
			[#if messages != ""][#assign messages=message+"<br/>"/][/#if]
			[#assign messages=message/]
		[/#list]
	[/#if]
	[#if tag.hasActionErrors()]
		[#assign shortCutFunction="error"/]
		[#assign title="错误消息"/]
		[#assign timeOut=500000/]
		[#list tag.actionErrors as message]
			[#if messages != ""][#assign messages=message+"<br/>"/][/#if]
			[#assign messages=message/]
		[/#list]
	[/#if]
	[#if tag.parameters['slash']??]
	<script>
		$(function (){
			toastr.options = {
			  "closeButton": true,
			  "debug": false,
			  "positionClass": "toast-top-center",
			  "onclick": null,
			  "showDuration": "1000",
			  "hideDuration": "1000",
			  "timeOut": "${timeOut}",
			  "extendedTimeOut": "10000",
			  "showEasing": "swing",
			  "hideEasing": "linear",
			  "showMethod": "fadeIn",
			  "hideMethod": "fadeOut"
			}
			var $toast = toastr["${shortCutFunction}"]("${messages}", "${title}"); 
		});
		//bg.message("${tag.id}", "${tag.parameters['slash']}");
		//setTimeout(function(){var msgdiv=document.getElementById('${tag.id}');if(msgdiv) msgdiv.style.display="none";},${tag.parameters['slash']}*1000);
	</script>
	[#elseif tag.parameters['formId']??]
		[#if messages!=""]
			<div class="alert alert-danger messagesShow">
				<button class="close" data-close="alert"></button>
				<font>${messages!}</font>
			</div>
		[/#if]
	[/#if]
[#elseif tag.parameters['formId']??]
	[#if messages!=""]
		${messages!}
	[#else]
		<div class="alert alert-danger display-hide">
			<button class="close" data-close="alert"></button>
			<font></font>
		</div>
	[/#if]
[/#if]