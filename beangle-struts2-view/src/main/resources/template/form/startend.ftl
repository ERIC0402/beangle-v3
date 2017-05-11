[#ftl/]
[#--
<script type="text/javascript">beangle.ui.load("My97DatePicker");</script>
<li>
<label for="${tag.dates?first.id}" class="title" style="padding:8px 0 0 0">${tag.dates?first.label}：</label>
[#list tag.dates as date]
[#if date_index=1]~[/#if]
<input type="text" id="${date.id}" [#if date.title??]title="${date.title}"[/#if] 
class="Wdate" onFocus="WdatePicker({dateFmt:'${tag.format}'[#if date.maxDate??],maxDate:'${date.maxDate}'[/#if][#if date.minDate??],minDate:'${date.minDate}'[/#if]})" name="${date.name}" value="${(date.value)?if_exists}" ${tag.parameterString}/>
[#if (date.required!"")=="true"]<em class="required"><sup>*</sup></em>[/#if]
[#if date.comment??]<label class="comment">${date.comment}</label>[/#if]
[/#list]
</li>


[#list tag.dates as date]
	[#if date_index=1]~[/#if]
	<input type="text" id="${date.id}" [#if date.title??]title="${date.title}"[/#if] 
	class="Wdate" onFocus="WdatePicker({dateFmt:'${tag.format}'[#if date.maxDate??],maxDate:'${date.maxDate}'[/#if][#if date.minDate??],minDate:'${date.minDate}'[/#if]})" name="${date.name}" value="${(date.value)?if_exists}" ${tag.parameterString}/>
	[#if (date.required!"")=="true"]<em class="required"><sup>*</sup></em>[/#if]
	[#if date.comment??]<label class="comment">${date.comment}</label>[/#if]
	[/#list]
--]
<link rel="stylesheet" type="text/css" href="${base}/static/metronic1.5.4/assets/plugins/bootstrap-datepicker/css/datepicker.css" />
<link rel="stylesheet" type="text/css" href="${base}/static/metronic1.5.4/assets/plugins/bootstrap-datetimepicker/css/datetimepicker.css" />
<link rel="stylesheet" type="text/css" href="${base}/static/metronic1.5.4/assets/plugins/bootstrap-daterangepicker/daterangepicker-bs3.css" />
	
<script type="text/javascript" src="${base}/static/metronic1.5.4/assets/plugins/bootstrap-datepicker/js/bootstrap-datepicker.js"></script>
<script type="text/javascript" src="${base}/static/metronic1.5.4/assets/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="${base}/static/metronic1.5.4/assets/plugins/bootstrap-daterangepicker/moment.js"></script>
<script type="text/javascript" src="${base}/static/metronic1.5.4/assets/plugins/bootstrap-daterangepicker/daterangepicker.js"></script> 
[#import "comm.ftl" as c/]
[@c.tr tag=tag]
	[#if !tag.parameters['new']??]
	<div class="input-group date-picker input-daterange">
		[#list tag.dates as date]
			[#if date_index=1]<span class="input-group-addon">to</span>[/#if]
			<input type="text" id="${date.id}" class="form-control startend" name="${date.name}" value="${(date.value)?if_exists}" ${tag.parameterString}>
			[#--[#if (date.required!"")=="true"]<em class="required"><sup>*</sup></em>[/#if]--]
		[/#list]
	</div>
	[#else]
		<div id="reportrange" class="btn default">
			<i class="fa fa-calendar"></i>
			&nbsp;<span>
			[#list tag.dates as date]
				[#if date_index!=0 && date.value??]
					 - 
				[/#if]
				[#if (tag.format=='datetime' || tag.format=='yyyy-MM-dd HH:mm')]
					${(date.value?substring(0,16))!}
				[#else]
					${(date.value?substring(0,10))!}
				[/#if]
			[/#list]
			</span>
			[#list tag.dates as date]
				[#if date_index==0]
					<input type="hidden" class="start" id="${date.id}" name="${date.name}" value="${(date.value)?if_exists}">
				[#else]
					<input type="hidden" class="end" id="${date.id}" name="${date.name}" value="${(date.value)?if_exists}">
				[/#if]
			[/#list]
			<b class="fa fa-angle-down"></b>
		</div>
	[/#if]
[/@]
<script>
var dateFormat = '${tag.format}';
var showtime=false;
if(dateFormat=='datetime' || dateFormat=='yyyy-MM-dd HH:mm'){
	showtime=true;
	$(".startend").datetimepicker({
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
	$(".startend").datepicker({
		language: 'zh-CN',
	    isRTL: App.isRTL(),
	    format: "yyyy-MM-dd",
	    autoclose: true,
	    startDate: '${(tag.minDate?string("yyyy-MM-dd"))!}',
        endDate: '${(tag.maxDate?string("yyyy-MM-dd"))!}',
	    todayBtn: true
	});
}

var dateF = 'YYYY-MM-DD'
if(dateFormat=='datetime' || dateFormat=='yyyy-MM-dd HH:mm'){
	dateF = 'YYYY-MM-DD HH:mm'
}

var startD = $(".start").val();
var endD = $(".end").val();

$('#reportrange').daterangepicker({
        opens: (App.isRTL() ? 'left' : 'right'),
        startDate: startD!=null&&endD!=""?startD:moment(),
        endDate: endD!=null&&endD!=""?endD:moment(),
        //minDate: '01/01/2012',
        //maxDate: '12/31/2014',
        //dateLimit: {
        //    days: 60
        //},
        showDropdowns: true,
        showWeekNumbers: true,
        timePicker: showtime,
        timePickerIncrement: 1,
        timePicker12Hour: true,
        ranges: {
            '今天': [moment(), moment()],
            '近七天': [moment(), moment().subtract('days', -6)],
            '近三十天': [moment(), moment().subtract('days', -29)],
            '本月': [moment().startOf('month'), moment().endOf('month')],
            '下月': [moment().subtract('month', -1).startOf('month'), moment().subtract('month', -1).endOf('month')]
        },
        buttonClasses: ['btn'],
        applyClass: 'green',
        cancelClass: 'default',
        format: 'YYYY-MM-DD',
        separator: ' to ',
        locale: {
            applyLabel: '确定',
            cancelLabel: '取消',
            fromLabel: '开始',
            toLabel: '截止',
            customRangeLabel: '自定义',
            daysOfWeek: ['日', '一', '二', '三', '四', '五', '六'],
            monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
            firstDay: 1
        }
    },
    function (start, end) {
        console.log("Callback has been called!");
        if(start!=null){
        	$('#reportrange span').html(start.format(dateF) + ' - ' + end.format(dateF));
        }
        $('#reportrange input').each(function(index){
        	if(index==0){
        		$(this).val(start.format(dateF));
        	}else{
        		$(this).val(end.format(dateF));
        	}
        });
    }
);
</script>