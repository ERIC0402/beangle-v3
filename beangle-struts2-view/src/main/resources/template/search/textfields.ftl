[#ftl]
[#--
[#list tag.fields as field]
<tr><td class="search-item"><label for="${field.id}" title="${field.label}">${field.label}：</label><input type="text" id="${field.id}" name="${field.name}" value="${Parameters[field.name]!?html}" maxlength="${field.maxlength}" ${tag.parameterString}/></td></tr>
[/#list]

[#list tag.fields as field]
<div class="item">
	<input type="text" id="${field.id}" name="${field.name}" alt="${field.label}" placeholder="${field.label}" value="${Parameters[field.name]!?html}" maxlength="${field.maxlength}" ${tag.parameterString}/>
</div>
[/#list]
--]
[#list tag.fields as field]
	<div class="search_item form-group">
		<input type="text" class="form-control input-small" style="line-height:18px;" id="${field.id}" name="${field.name}" alt="${field.label}" placeholder="${field.label}" value="${Parameters[field.name]!?html}" maxlength="${field.maxlength}" ${tag.parameterString}/>
		&nbsp;
	</div>
[/#list]