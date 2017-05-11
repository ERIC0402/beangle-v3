[#ftl]
<a href="${tag.href}" [#if tag.selected][#else]class="first_a"[/#if] [#if tag.onclick??]onclick="${tag.onclick}"[/#if] ${tag.parameterString} style="float:left">${tag.title!}</a><span class="span_1">|</span>
[#--
<a class="first_a" href="${tag.href}" [#if tag.selected]class="selected"[/#if] [#if tag.onclick??]onclick="${tag.onclick}"[/#if] ${tag.parameterString}>${tag.title!}</a>
--]