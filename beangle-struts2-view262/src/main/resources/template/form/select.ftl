[#ftl]
[#--
<li>[#if tag.label??]<label for="${tag.id}" class="title">[#if (tag.required!"")=="true"]<em class="required">*</em>[/#if]${tag.label}：</label>[/#if]<select id="${tag.id}" [#if tag.title??]title="${tag.title}"[/#if] name="${tag.name}"${tag.parameterString}>${tag.body}[#if tag.empty??]<option value="">${tag.empty}</option>[/#if][#list tag.items as item]<option value="${item[tag.keyName]}"[#if tag.isSelected(item)]selected="selected"[/#if]>${item[tag.valueName]!}</option>[/#list]</select>[#if tag.comment??]<label class="comment">${tag.comment}</label>[/#if]</li>
--]
[#import "comm.ftl" as c/]
[@c.tr tag=tag]
    <select id="${tag.id}" title="[#if tag.comment??]${tag.comment}[#else]${tag.title!}[/#if]" name="${tag.name}"${tag.parameterString}>[#if tag.empty??]<option value="">${tag.empty}</option>[/#if]${tag.body}[#list tag.items as item]<option value="${item[tag.keyName]}"[#if tag.isSelected(item)]selected="selected"[/#if]>${item[tag.valueName]!}</option>[/#list]</select>
[/@]
