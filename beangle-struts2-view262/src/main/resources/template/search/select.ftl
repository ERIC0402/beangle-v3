[#ftl/]
[#--
<tr><td class="search-item"><label for="${tag.id}">${tag.label}：</label><select id="${tag.id}" name="${tag.name}"${tag.parameterString}>${tag.body}[#if tag.empty??]<option value="">${tag.empty}</option>[/#if][#list tag.items as item]<option value="${item[tag.keyName]}"[#if tag.isSelected(item)]selected="selected"[/#if]>${item[tag.valueName]!}</option>[/#list]</select></td></tr>
--]
<div class="item">
	<select id="${tag.id}" name="${tag.name}"${tag.parameterString}>${tag.body}
		[#if tag.empty?? && !(tag.parameters['noempty']??)]<option value="">${tag.label}</option>[/#if]
		[#list tag.items as item]<option value="${item[tag.keyName]}"[#if tag.isSelected(item)]selected="selected"[/#if]>${item[tag.valueName]!}</option>[/#list]
	</select>
</div>