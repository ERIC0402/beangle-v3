[#ftl]
[#macro switch label="" name="" value=false notnull=false]
<input type="checkbox" [#if notnull]onclick="return false;" title="必填字段，不能取消。"[#else]onclick="$name('${name}').val(this.checked == true? 1 : 0);"[/#if] id="switch${name}" [#if value || notnull]checked="checked"[/#if]/>[#if label != ""]<label for="switch${name}">${label}</label>[/#if]
<input type="hidden" name="${name}" value="${(value || notnull)?string}"/>
[/#macro]

[#macro select data=[] value="" valueKey="id" nameKey="name" noOption=false empty="请选择..."  extra...]
<select [#if extra?size gt 0][#list extra?keys as attr] ${attr}="${extra[attr]}"[/#list][/#if]>
<option value="">${empty}</option>
[#list data as v]
	[#if noOption]
		[#nested v/]
	[#else]
		[#assign optionValue][#if v?is_string]${v}[#else]${v[valueKey]}[/#if][/#assign]
		[#assign optionText][#if v?is_string]${v}[#else]${v[nameKey]}[/#if][/#assign]
		<option value="${optionValue}" title="${optionText}" [#if value==optionValue?string]selected=selected[/#if]>[@c.substring str=optionText mx=20/]</option>
	[/#if]
[/#list]
</select>
[/#macro]
