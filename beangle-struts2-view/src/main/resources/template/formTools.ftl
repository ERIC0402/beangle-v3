<#macro enabled name="" value=true t='有效' f='无效'>
<input name="${name}" type="radio" value="1" id="radio${name}1" <#if value==true>checked</#if>/><label for="radio${name}1">${t}</label>
<input name="${name}" type="radio" value="0" id="radio${name}0" <#if value!=true>checked</#if>/><label for="radio${name}0">${f}</label>
</#macro>

<#macro radio2 name value type="boolean" t='是' f='否'>
<#assign v=value/>
<#if type == "number">
<#if value == 1>
<#assign v=true/>
<#else>
<#assign v=false/>
</#if>
</#if>
<input name="${name}" type="radio" value="1" id="radio${name}1" <#if v>checked</#if>/><label for="radio${name}1">${t}</label>
<input name="${name}" type="radio" value="0" id="radio${name}0" <#if !v>checked</#if>/><label for="radio${name}0">${f}</label>
</#macro>

<#macro select data=[] name="" value=0 textName="name">
<#assign cvalue=0>
<#if value?string=="">
	<#assign cvalue="0">
<#else>
	<#assign cvalue=value>
</#if>
<select name="${name}" style="width:160px;">
    <option value="">请选择</option>
    <#list data as v>
    <option value="${v.id}" <#if cvalue?number==v.id>selected</#if> >${v[textName]}</option>
    </#list>
</select>
</#macro>

<#macro chosen name data textName="name" placeholder="请选择..." multiple=false width=400>
<select id="${name}" name="${name}" <#if multiple>multiple="true"</#if> style="width:${width}px;">
			<option disabled=disabled>${placeholder}</option>
			[#list data as v] <option value="${(v.id)!}" selected="selected">${(v[textName])!}</option>[/#list]
		</select>
</#macro>

<#macro columnSelect data name value>
<#assign cvalue=0>
<#if value?string=="">
	<#assign cvalue="0">
<#else>
	<#assign cvalue=value>
</#if>
<select name="${name}" >
    <option value="">请选择</option>
    <#list data as v>
    <option value="${v.id}" <#if cvalue?number==v.id>selected</#if> >(${v.orders?substring(4)})${v.name}</option>
    </#list>
</select>
</#macro>