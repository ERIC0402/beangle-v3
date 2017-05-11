[#ftl]
[#--
<li>[#if tag.label??]<label class="title">[#if (tag.required!"")=="true"]<em class="required">*</em>[/#if]${tag.label}：</label>[/#if]
</li>
--]
[#import "comm.ftl" as c/]
[@c.tr tag=tag]
	<span id="${tag.id}" style="display:none"></span>
	[#list tag.checkboxes as checkbox]
	<input type="checkbox" id="${checkbox.id}" style="width:10px" name="${tag.name}" value="${checkbox.value}"${tag.parameterString} [#if checkbox.checked]checked="checked"[/#if]/>
	<label for="${checkbox.id}">${checkbox.title!}</label>
	[#if tag.comment??]<label class="comment">${tag.comment}</label>[/#if]
	[/#list]
[/@]

