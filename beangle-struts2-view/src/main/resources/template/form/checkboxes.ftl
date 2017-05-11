[#ftl]
[#--
<li>[#if tag.label??]<label class="title">[#if (tag.required!"")=="true"]<em class="required">*</em>[/#if]${tag.label}：</label>[/#if]
</li>
--]
[#import "comm.ftl" as c/]
[@c.tr tag=tag]
<div class="col-md-9">
	<div class="checkbox-list">
		[#list tag.checkboxes as checkbox]
			<label class="checkbox-inline">
				<input type="checkbox" id="${checkbox.id}" name="${tag.name}" value="${checkbox.value}"${tag.parameterString} [#if checkbox.checked]checked="checked"[/#if]> ${checkbox.title!}
			</label>
		[/#list]
	</div>
</div>
[/@]

