[#ftl/]
<link rel="stylesheet" type="text/css" href="${base}/static/metronic1.5.4/assets/plugins/bootstrap-datepicker/css/datepicker.css" />
<link rel="stylesheet" type="text/css" href="${base}/static/metronic1.5.4/assets/plugins/bootstrap-datetimepicker/css/datetimepicker.css" />

<script type="text/javascript" src="${base}/static/metronic1.5.4/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="${base}/static/metronic1.5.4/assets/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
<div class="search_item" style="width: 200px;">
	<div id="div${tag.id}" class="col-md-3 input-group date form_meridian_datetime" style="padding-left:0px;">                                       
		<input type="text" id="${tag.id}" style="width:150px;" size="16" class="form-control" name="${tag.name}" placeholder="${tag.label}" value="" ${tag.parameterString}>
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
</div>