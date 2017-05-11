[#ftl]
[#include "../form/comm.ftl"/]
[#--
[#macro tr tag]
<tr${tag.parameterString}>
    <td class="td_title_1 td_style_2">[#if (tag.required!"")=="true"]<span class="f_3">*</span>[/#if][#if tag.label??]${tag.label}：[/#if]</td>
    <td class="td_style_2">
		[#nested/]
	</td>
</tr>
[/#macro]

[#macro group title="" id=""]
	<li id="${id}">
		[#if title != ""]
		<h2 class="h2_title_1 h2_expand">${title}</h2>
		[/#if]
		<div class="view_div">[@table][#nested][/@]</div>
	</li>
[/#macro]

[#macro table]
<table class="table_style_2">[#nested/]</table>
[/#macro]

[#macro showProgressBar datas currentStep=1]
	<div class="BlankLine1"></div>
	<table class="progress_bar" cellpadding="0" cellspacing="0">
		<tr>
			[#list datas as item]
				 <td class="[#if (item_index+1)<currentStep]finish_state arrow_2[#elseif (item_index+1)==currentStep]finish_state [#if currentStep!=datas?size]arrow_1[/#if][#elseif datas?size=(item_index+1)]unfinished_state[#else]unfinished_state arrow_1[/#if]">
				 	${item_index+1}、${item!}
				 </td>
			[/#list]
		</tr>
	</table>
	<div class="BlankLine1"></div>
[/#macro]
--]