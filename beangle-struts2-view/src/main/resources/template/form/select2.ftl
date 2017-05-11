[#ftl]
[#--
<li${tag.parameterString}>[#if tag.label??]<label class="title">[#if (tag.required!"")=="true"]<em class="required">*</em>[/#if]${tag.label}：</label>[/#if]
</li>
--]
<link rel="stylesheet" type="text/css" href="${base}/static/metronic1.5.4/assets/plugins/jquery-multi-select/css/multi-select.css" />
[#import "comm.ftl" as c/]
[@c.tr tag=tag]
<table cellpadding="0" cellspacing="0">
	<tr>
		<td>
			<select class="form-control" name="${tag.name1st}" multiple="multiple" class="select2" size="${tag.size}" style="${tag.style}" onDblClick="JavaScript:bg.select.moveSelected(this.form['${tag.name1st}'], this.form['${tag.name2nd}'])" >
				[#list tag.items1st as item]
					[#if (tag.items2nd?seq_contains(item))]
					[#else]
						<option value="${(item[tag.keyName])!}" title="${(item[tag.valueName])!}">${(item[tag.valueName])!}</option>
					[/#if]
				[/#list]
			</select>
		</td>
		<td style="width:30px">
			<input name="btn" style="margin:auto" onclick="JavaScript:bg.select.moveSelected(this.form['${tag.name1st}'], this.form['${tag.name2nd}'])" type="button" value="&gt;"/>
			<input name="btn" style="vertical-align: middle;" onclick="JavaScript:bg.select.moveSelected(this.form['${tag.name2nd}'], this.form['${tag.name1st}'])" type="button" value="&lt;"/>
		</td>
		<td>
			<select class="form-control" name="${tag.name2nd}" id="${tag.id}_1" title="${tag.label!}" multiple="multiple" size="${tag.size}" style="${tag.style}" onDblClick="JavaScript:bg.select.moveSelected(this.form['${tag.name2nd}'], this.form['${tag.name1st}'])">
				[#list tag.items2nd as item]
					<option value="${(item[tag.keyName])!}" title="${(item[tag.valueName])!}">${(item[tag.valueName])!}</option>
				[/#list]
			</select>
		</td>
	</tr>
</table>
[/@]