[#ftl]
[#--
<li>[#if tag.label??]<label class="title"><em class="required">*</em>${tag.label}：</label>[/#if]
[#list tag.radios as radio]
<input type="radio" id="${radio.id}" style="width:10px" name="${tag.name}" value="${radio.value}"${tag.parameterString} [#if (tag.value!"")== radio.value]checked[/#if]/>
<label for="${radio.id}">${radio.title!}</label>
[/#list]
[#if tag.comment??]<label class="comment">${tag.comment}</label>[/#if]
</li>
--]
[#import "comm.ftl" as c/]
[@c.tr tag=tag]
	<div class="col-md-9">
		<div class="radio-list">
			[#list tag.radios as radio]
				<label class="radio-inline">
					<input type="radio" id="${radio.id}" name="${tag.name}" value="${radio.value}"${tag.parameterString} [#if (tag.value!)== radio.value]checked[/#if]> ${radio.title!}
				</label>
			[/#list]
		</div>
	</div>
[/@]