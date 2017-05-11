[#ftl]
[#--
<li>[#if tag.label??]<label for="${tag.id}" class="title">[#if (tag.required!"")=="true"]<em class="required">*</em>[/#if]${tag.label}：</label>[/#if]<select id="${tag.id}" [#if tag.title??]title="${tag.title}"[/#if] name="${tag.name}"${tag.parameterString}>${tag.body}[#if tag.empty??]<option value="">${tag.empty}</option>[/#if][#list tag.items as item]<option value="${item[tag.keyName]}"[#if tag.isSelected(item)]selected="selected"[/#if]>${item[tag.valueName]!}</option>[/#list]</select>[#if tag.comment??]<label class="comment">${tag.comment}</label>[/#if]</li>
--]
[#import "comm.ftl" as c/]
[@c.tr tag=tag]
    <select class="form-control select2" [#if tag.parameters['multiple']??]multiple[/#if] id="${tag.id}" name="${tag.name}" ${tag.parameterString} data-placeholder="[#if tag.empty??]${tag.empty}[#else]请选择...[/#if]">
		[#if tag.empty??]<option value="">${tag.empty}</option>[/#if]
		[#--<option value=""></option>--]
		${tag.body}
		[#list tag.items as item]
			<option value="${item[tag.keyName]}"[#if tag.isSelected(item)]selected="selected"[/#if]>${item[tag.valueName]!}</option>
		[/#list]
	</select>
	
	<script type="text/javascript">
	jQuery(document).ready(function() {       
	   if (jQuery().select2) {
	        $('#${tag.id}').select2({
	            placeholder: "请选择...",
	            allowClear: true
	        });
	    }
	});
	</script>
[/@]
