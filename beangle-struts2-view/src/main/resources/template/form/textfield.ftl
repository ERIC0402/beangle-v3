[#ftl]
[#--
<li>[#if tag.label??]<label for="${tag.id}" class="title">[#if (tag.required!"")=="true"]<em class="required">*</em>[/#if]${tag.label}：</label>[/#if]<input type="text" id="${tag.id}" [#if tag.title??]title="${tag.title}"[/#if] name="${tag.name}" value="${tag.value?html}" maxlength="${tag.maxlength}" ${tag.parameterString}/>[#if tag.comment??]<label class="comment">${tag.comment}</label>[/#if]</li>
--]
[#import "comm.ftl" as c/]
[@c.tr tag=tag]
	${tag.parameters['beforeInput']!}<input class="form-control" type="text" id="${tag.id}" name="${tag.name}" value="${tag.value?html}" maxlength="${tag.maxlength}" ${tag.parameterString}/>
[/@]