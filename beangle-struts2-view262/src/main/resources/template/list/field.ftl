[#ftl]
[#include "../form/field.ftl"/]
[#--
<li${tag.parameterString}>[#if tag.label??]<label class="title">[#if (tag.required!"")=="true"]<em class="required">*</em>[/#if]${tag.label}：</label>[/#if]${tag.body}</li>
--]
[#--
[#import "comm.ftl" as c/]
[@c.tr tag=tag]
    ${tag.body}
[/@]
--]