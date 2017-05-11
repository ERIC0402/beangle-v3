[#ftl/]
<link rel="stylesheet" type="text/css" href="${base}/static/metronic1.5.4/assets/plugins/bootstrap-datepicker/css/datepicker.css" />
<link rel="stylesheet" type="text/css" href="${base}/static/metronic1.5.4/assets/plugins/bootstrap-datetimepicker/css/datetimepicker.css" />

<script type="text/javascript" src="${base}/static/metronic1.5.4/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="${base}/static/metronic1.5.4/assets/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
<div class="search_item" style="width: 450px;">
	[#list tag.dates as date]
		[#--
		<label for="${date.id}">[#if date_index==0]${date.label}：[#else]-[/#if]</label>
		<input style="width: 30%;" type="text" id="${date.id}" [#if date.title??]title="${date.title}" placeholder="${date.title}" [/#if] class="Wdate" onFocus="WdatePicker({dateFmt:'${tag.format}'[#if date.maxDate??],maxDate:'${date.maxDate}'[/#if][#if date.minDate??],minDate:'${date.minDate}'[/#if]})" name="${date.name}" value="${(date.value)?if_exists}" ${tag.parameterString}/>
		--]
		<div id="div${date.id}" class="col-md-3 input-group date form_meridian_datetime" style="padding-left:0px;">                                       
			<input type="text" id="${date.id}" style="width:150px;" size="16" class="form-control" name="${date.name}" placeholder="${date.label}" value="${(date.value)?if_exists}" ${date.parameterString}>
			<span class="input-group-btn">
				<button class="btn default date-set" type="button"><i class="fa fa-calendar"></i></button>
				[#if tag.dates?last.id!=date.id]
					&nbsp;&nbsp;&nbsp;-
				[/#if]
			</span>
		</div>
		<script>
		var dateFormat = '${tag.format}';
		if(dateFormat=='datetime' || dateFormat=='yyyy-MM-dd HH:mm'){
			$("#div${date.id}").datetimepicker({
				language: 'zh-CN',
			    isRTL: App.isRTL(),
			    format: "yyyy-MM-dd HH:mm",
			    showMeridian: true,
			    autoclose: true,
			    pickerPosition: (App.isRTL() ? "bottom-right" : "bottom-left"),
			    todayBtn: true
			});
		}else{
			$("#div${date.id}").datepicker({
				language: 'zh-CN',
			    isRTL: App.isRTL(),
			    format: "yyyy-MM-dd",
			    autoclose: true,
			    todayBtn: true
			});
		}
		
		</script>
	[/#list]
</div>