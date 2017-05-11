[#ftl]
[#function toRadioItem data]
[#assign str=''/]
[#list data as v][#assign str]${str}[#if v_index gt 0],[/#if]${v.id}:${v.name}[/#assign][/#list]
[#return str]
[/#function]

[#function subString str max=20]
[#assign result][#if str?length gt max]<label title="${str}">${str?substring(0, max)}..</label>[#else]${str}[/#if][/#assign]
[#return result]
[/#function]

[#function addOrEditTitle entity entityName]
[#assign title]${entityName}[#if !entity.id??]添加[#else]修改[/#if][/#assign]
[#return title/]
[/#function]

