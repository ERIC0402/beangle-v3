[#ftl/]
[#--
<li>[#if tag.label??]<label for="${tag.id}" class="title">[#if (tag.required!"")=="true"]<em class="required">*</em>[/#if]${tag.label}：</label>[/#if]<input type="text" id="${tag.id}" [#if tag.title??]title="${tag.title}"[/#if] class="Wdate" onFocus="WdatePicker({dateFmt:'${tag.format}'[#if tag.maxDate??],maxDate:'${tag.maxDate}'[/#if][#if tag.minDate??],minDate:'${tag.minDate}'[/#if]})" name="${tag.name}" value="${tag.value}" ${tag.parameterString}/>[#if tag.comment??]<label class="comment">${tag.comment}</label>[/#if]</li>
--]
[#import "comm.ftl" as c/]
<script type="text/javascript">beangle.ui.load("My97DatePicker");</script>
[@c.tr tag=tag]
	<input type="text" id="${tag.id}" title="[#if tag.comment??]${tag.comment}[#else]${tag.title!}[/#if]" class="Wdate" onFocus="WdatePicker({dateFmt:'${tag.format}'[#if tag.maxDate??],maxDate:'${tag.maxDate}'[/#if][#if tag.minDate??],minDate:'${tag.minDate}'[/#if]})" name="${tag.name}" value="${tag.value}" ${tag.parameterString}/>
[/@]
