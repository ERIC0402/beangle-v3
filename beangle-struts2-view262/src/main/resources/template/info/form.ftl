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
[#--
--]
<script type="text/javascript" src="${base}/static/scripts/validator2/validator2.js"></script>
<link href="${base}/static/scripts/validator2/css/validator2.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
//bg.ui.load("validator2");
bg.ui.form.init("${tag.id}");

[#if (tag.validate!"")=="true"]
var a_fields${tag.id} = {};
[#list tag.checks?keys as id]
[#assign check=tag.checks[id]/]
a_fields${tag.id}["#${id}"] = new Validator2Str()${check}.getConfig();
[/#list]
var v${tag.id} = new validator2(a_fields${tag.id}, "#${tag.id}");
[/#if]
[#--
$("#${tag.id}").validate();
[#if (tag.validate!"")=="true"]
${tag.validateStr}
[/#if]
--]
function onsubmit${tag.id}(){
	var res=true;
[#if (tag.validate!"")=="true"]
	res =v${tag.id}.exec();
[/#if]
	if(false==res) return false;
	[#if tag.onsubmit??]
	var nativeOnsubmit${tag.id} = function(){${tag.onsubmit}}
	try{res=nativeOnsubmit${tag.id}();}catch(e){alert(e);return false;}
	[/#if]
	return res;
}
</script>
[/#if]