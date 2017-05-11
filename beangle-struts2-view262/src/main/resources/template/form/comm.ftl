[#ftl]
[#macro tr tag]
<tr${tag.parameterString}>
    <td class="td_title_1 td_style_2" valign="top">[#if (tag.required!"")=="true"]<span class="f_3">*</span>[/#if][#if tag.label??]${tag.label}：[/#if]</td>
    <td class="td_style_2">
		[#nested/][#if tag.parameters["comment"]??]<label title="${tag.parameters['comment']}" style="font-weight: bolder; color: green;">?</label>[/#if]
	</td>
</tr>
[/#macro]

[#macro group title="" id="" notable=0 tableClass="table_style_2"]
	<li id="${id}">
		[#if title != ""]
		<h2 class="h2_title_1 h2_expand">${title}</h2>
		[/#if]
		<div class="view_div">	[#if notable ==0][@table tableClass][#nested][/@][#else][#nested][/#if]</div>
	</li>
[/#macro]

[#macro table tableClass="table_style_2"]
<table class="${tableClass}" cellpadding="0" cellspacing="0">[#nested/]</table>
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

[#macro uploadImg label name value dir]
[@b.field label=label ]	
	<input type="hidden" name="oldImg" value="${value}"/>
	<input type="hidden" name="${name}" value="${value}"/>
 	<img id="albumImg" src="${base}/common/file.action?method=downFile&folder=${value}" style="display: none"/>
    <div style="margin:3px 0;">
        <input id="file_upload" name="file_upload" type="file" />
    </div>
    <script type="text/javascript">			
			$(function (){
		        uploadimg({
		            dir:"${dir}",
		            ofile:"${value}",
		            upBtn:"file_upload",
		            imgName:"tiMu.pic",
		            showImg:"albumImg",
		            width:300
		        });
		    });	
    //     [#if value != ""]
	    $("#albumImg").show();
	//     [/#if]
	</script>
[/@]
[/#macro]

[#function dateToNumber date formate="yyyyMMddHHmmss"]
	[#return date?string(formate)?number/]
[/#function]
