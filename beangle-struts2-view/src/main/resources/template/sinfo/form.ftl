[#ftl]
[#import "comm.ftl" as c/]
[@b.messages slash="2"/]
<form id="${tag.id}" [#if !tag.parameters['cssClass']??]class="listform"[/#if] name="${tag.name}" action="${tag.action}" method="post" [#if tag.target??]target="${tag.target}"[/#if]${tag.parameterString} 
[#if tag.validate=="true" || tag.onsubmit??]onsubmit="return onsubmit${tag.id}()"[/#if]>
	<ul class="ul_style_1" id="ul${tag.id}">
		[#if tag.title??]
			[@c.group title=tag.title]${tag.body}[/@]
		[#else]
			[#if tag.notable??]
		${tag.body}
			[#else]
				[@c.table]${tag.body}[/@]
			[/#if]
		[/#if] 
	</ul> 
</form>
[#if (tag.validate!"")=="true" ||tag.onsubmit??]
<script type="text/javascript">

function onsubmit${tag.id}(){
	var res=true;
	if(false==res) return false;
	[#if tag.onsubmit??]
	var nativeOnsubmit${tag.id} = function(){${tag.onsubmit}}
	try{res=nativeOnsubmit${tag.id}();}catch(e){alert(e);return false;}
	[/#if]
	return res;
}
</script>
[/#if]