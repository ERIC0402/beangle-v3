[#ftl]
[#--
<li>[#if tag.label??]<label for="${tag.id}" class="title">[#if (tag.required!"")=="true"]<em class="required">*</em>[/#if]${tag.label}：</label>[/#if]<input type="password" id="${tag.id}" [#if tag.title??]title="${tag.title}"[/#if] name="${tag.name}" maxlength="${tag.maxlength}" ${tag.parameterString}/>[#if tag.comment??]<label class="comment">${tag.comment}</label>[/#if]</li>
--]
[#import "comm.ftl" as c/]
[@c.tr tag=tag]
    <input type="password" id="${tag.id}" value="${tag.value!}" title="[#if tag.comment??]${tag.comment}[#else]${tag.title!}[/#if]" name="${tag.name}"${tag.parameterString}  onpaste="return false"/>
[/@]