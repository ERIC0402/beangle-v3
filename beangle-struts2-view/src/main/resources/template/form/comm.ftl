[#ftl]
[#macro tr tag]
<div class="form-group">[#-- ${tag.parameterString} --]
	<label  class="col-md-3 control-label">[#if (tag.required!"")=="true"]<span class="required">*</span>[/#if][#if tag.label??]${tag.label}：[/#if]</label>
    <div class="col-md-6" >
		[#nested/]
		[#if tag.comment??]<span class="help-block">${tag.comment!}</span>[/#if]
	</div>
</div>
[/#macro]

[#macro group title="" id="" notable=0 tableClass="table_style_2"]
	[#if title != ""]
		<h2 class="form-section">${title}</h2>
	[/#if]
	[#if notable ==0][@table tableClass][#nested][/@][#else][#nested][/#if]
[/#macro]

[#macro table tableClass="table_style_2"]
<table class="${tableClass}" cellpadding="0" cellspacing="0">[#nested/]</table>
[/#macro]

[#macro showProgressBar datas currentStep=1]
<ul class="nav nav-pills nav-justified steps">
	[#list datas as item]
		<li class="[#if (item_index+1)<currentStep]done[#elseif (item_index+1)==currentStep]active [#elseif datas?size=(item_index+1)][#else][/#if]">
			<a class="step" data-toggle="tab">
			<span class="number">${item_index+1}</span>
			<span class="desc"><i class="fa fa-check"></i> ${item!}</span>   
			</a>
		</li>
	[/#list]
</ul>
[#assign progressLength=(currentStep/datas?size)*100]

<div class="progress progress-striped" id="bar" role="progressbar">
	<div class="progress-bar progress-bar-success" style="width: ${progressLength!}%;"></div>
</div>
[#--
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
--]
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
