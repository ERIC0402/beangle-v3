[#ftl/]
[#--
<li>[#if tag.label??]<label for="${tag.id}" class="title">[#if (tag.required!"")=="true"]<em class="required">*</em>[/#if]${tag.label}：</label>[/#if]<input type="text" id="${tag.id}" [#if tag.title??]title="${tag.title}"[/#if] class="Wdate" onFocus="WdatePicker({dateFmt:'${tag.format}'[#if tag.maxDate??],maxDate:'${tag.maxDate}'[/#if][#if tag.minDate??],minDate:'${tag.minDate}'[/#if]})" name="${tag.name}" value="${tag.value}" ${tag.parameterString}/>[#if tag.comment??]<label class="comment">${tag.comment}</label>[/#if]</li>
--]
[#--
<script type="text/javascript">beangle.ui.load("My97DatePicker");</script>
<input type="text" id="${tag.id}" title="[#if tag.comment??]${tag.comment}[#else]${tag.title!}[/#if]" class="Wdate" onFocus="WdatePicker({dateFmt:'${tag.format}'[#if tag.maxDate??],maxDate:'${tag.maxDate}'[/#if][#if tag.minDate??],minDate:'${tag.minDate}'[/#if]})" name="${tag.name}" value="${tag.value}" ${tag.parameterString}/>
--]
<link rel="stylesheet" type="text/css" href="${base}/static/metronic1.5.4/assets/plugins/bootstrap-datepicker/css/datepicker.css" />
<link rel="stylesheet" type="text/css" href="${base}/static/metronic1.5.4/assets/plugins/bootstrap-datetimepicker/css/datetimepicker.css" />

<script type="text/javascript" src="${base}/static/metronic1.5.4/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="${base}/static/metronic1.5.4/assets/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
<div id="div${tag.id}" class="col-md-3 input-group date form_meridian_datetime" style="padding-left:0px;">                                       
	<input type="text" id="${tag.id}" size="16" class="form-control" name="${tag.name}" value="${(tag.label)!}" ${tag.parameterString}>
	<span class="input-group-btn">
		<button class="btn default date-set" type="button"><i class="fa fa-calendar"></i></button>
	</span>
</div>
<script>
var dateFormat = '${tag.format}';
if(dateFormat=='datetime' || dateFormat=='yyyy-MM-dd HH:mm'){
	$("#div${tag.id}").datetimepicker({
		language: 'zh-CN',
	    isRTL: App.isRTL(),
	    format: "yyyy-MM-dd HH:mm",
	    showMeridian: true,
	    autoclose: true,
	    pickerPosition: (App.isRTL() ? "bottom-right" : "bottom-left"),
	    startDate: '${(tag.minDate?string("yyyy-MM-dd"))!}',
        endDate: '${(tag.maxDate?string("yyyy-MM-dd"))!}',
	    todayBtn: true
	});
}else{
	$("#div${tag.id}").datepicker({
		language: 'zh-CN',
	    isRTL: App.isRTL(),
	    format: "yyyy-MM-dd",
	    autoclose: true,
	    startDate: '${(tag.minDate?string("yyyy-MM-dd"))!}',
        endDate: '${(tag.maxDate?string("yyyy-MM-dd"))!}',
	    todayBtn: true
	});
}

</script>