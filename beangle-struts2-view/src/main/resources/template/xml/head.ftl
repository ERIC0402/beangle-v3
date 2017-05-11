[#ftl]
[#setting datetime_format="yyyy-MM-dd HH:mm"/]
[#if !(request.getHeader('x-requested-with')??) && !Parameters['x-requested-with']??]
<!DOCTYPE html>
<!--[if IE 7]> <html> <![endif]-->
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!--> <html lang="en" class="no-js"> <!--<![endif]-->
<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	<meta name="MobileOptimized" content="320">
	[@title/]
	[#--[@beangle_js_head compressed=(Parameters['devMode']?exists)?string("false","true") /--]
<!-- BEGIN JAVASCRIPTS(Load javascripts at bottom, this will reduce page load time) -->
	<script type="text/javascript">window.contextPath = "${base!}";var base = "${base!}", JSESSIONID = "${(Session['id'])!}";var tophref = location.href;</script>
	<!-- BEGIN CORE PLUGINS -->   
	<!--[if lt IE 9]>
	<script src="${base}/static/metronic1.5.4/assets/plugins/respond.min.js"></script>
	<script src="${base}/static/metronic1.5.4/assets/plugins/excanvas.min.js"></script> 
	<![endif]-->   
	<script src="${base}/static/metronic1.5.4/assets/plugins/jquery-1.10.2.js" type="text/javascript"></script>
	<script src="${base}/static/metronic1.5.4/assets/plugins/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>   
	<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
	
	<script src="${base}/static/metronic1.5.4/assets/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="${base}/static/metronic1.5.4/assets/plugins/bootstrap-hover-dropdown/twitter-bootstrap-hover-dropdown.min.js" type="text/javascript" ></script>
	<script src="${base}/static/metronic1.5.4/assets/plugins/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
	<script src="${base}/static/metronic1.5.4/assets/plugins/jquery.blockui.min.js" type="text/javascript"></script>  
	<script src="${base}/static/metronic1.5.4/assets/plugins/jquery.cookie.min.js" type="text/javascript"></script>
	<script src="${base}/static/metronic1.5.4/assets/plugins/uniform/jquery.uniform.min.js" type="text/javascript" ></script>
	<!-- END CORE PLUGINS -->
	<!-- BEGIN PAGE LEVEL PLUGINS -->
	[#if !tag.parameters['isLogin']??]
	[#--
	<script src="${base}/static/metronic1.5.4/assets/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
	<script src="${base}/static/metronic1.5.4/assets/plugins/jqvmap/jqvmap/jquery.vmap.js" type="text/javascript"></script>   
	<script src="${base}/static/metronic1.5.4/assets/plugins/jqvmap/jqvmap/maps/jquery.vmap.russia.js" type="text/javascript"></script>
	<script src="${base}/static/metronic1.5.4/assets/plugins/jqvmap/jqvmap/maps/jquery.vmap.world.js" type="text/javascript"></script>
	<script src="${base}/static/metronic1.5.4/assets/plugins/jqvmap/jqvmap/maps/jquery.vmap.europe.js" type="text/javascript"></script>
	<script src="${base}/static/metronic1.5.4/assets/plugins/jqvmap/jqvmap/maps/jquery.vmap.germany.js" type="text/javascript"></script>
	<script src="${base}/static/metronic1.5.4/assets/plugins/jqvmap/jqvmap/maps/jquery.vmap.usa.js" type="text/javascript"></script>
	<script src="${base}/static/metronic1.5.4/assets/plugins/jqvmap/jqvmap/data/jquery.vmap.sampledata.js" type="text/javascript"></script>
	<script src="${base}/static/metronic1.5.4/assets/plugins/flot/jquery.flot.js" type="text/javascript"></script>
	<script src="${base}/static/metronic1.5.4/assets/plugins/flot/jquery.flot.resize.js" type="text/javascript"></script>
	<script src="${base}/static/metronic1.5.4/assets/plugins/jquery.pulsate.min.js" type="text/javascript"></script>
	<script src="${base}/static/metronic1.5.4/assets/plugins/bootstrap-daterangepicker/moment.min.js" type="text/javascript"></script>
	<script src="${base}/static/metronic1.5.4/assets/plugins/bootstrap-daterangepicker/daterangepicker.js" type="text/javascript"></script>     
	<script src="${base}/static/metronic1.5.4/assets/plugins/gritter/js/jquery.gritter.js" type="text/javascript"></script>
	
	<!-- IMPORTANT! fullcalendar depends on jquery-ui-1.10.3.custom.min.js for drag & drop support -->
	<script src="${base}/static/metronic1.5.4/assets/plugins/fullcalendar/fullcalendar/fullcalendar.min.js" type="text/javascript"></script>
	<script src="${base}/static/metronic1.5.4/assets/plugins/jquery-easy-pie-chart/jquery.easy-pie-chart.js" type="text/javascript"></script>
	<script src="${base}/static/metronic1.5.4/assets/plugins/jquery.sparkline.min.js" type="text/javascript"></script>
	
	<!-- BEGIN PAGE LEVEL PLUGIN STYLES --> 
	<link href="${base}/static/metronic1.5.4/assets/plugins/gritter/css/jquery.gritter.css" rel="stylesheet" type="text/css"/>
	<link href="${base}/static/metronic1.5.4/assets/plugins/bootstrap-daterangepicker/daterangepicker-bs3.css" rel="stylesheet" type="text/css" />
	<link href="${base}/static/metronic1.5.4/assets/plugins/fullcalendar/fullcalendar/fullcalendar.css" rel="stylesheet" type="text/css"/>
	<link href="${base}/static/metronic1.5.4/assets/plugins/jqvmap/jqvmap/jqvmap.css" rel="stylesheet" type="text/css"/>
	<link href="${base}/static/metronic1.5.4/assets/plugins/jquery-easy-pie-chart/jquery.easy-pie-chart.css" rel="stylesheet" type="text/css"/>
	<!-- END PAGE LEVEL PLUGIN STYLES -->
	
	<!-- END PAGE LEVEL PLUGINS -->
	<!-- BEGIN PAGE LEVEL SCRIPTS -->
	<script src="${base}/static/metronic1.5.4/assets/scripts/index.js" type="text/javascript"></script>
	<script src="${base}/static/metronic1.5.4/assets/scripts/tasks.js" type="text/javascript"></script>
	
	<link href="${base}/static/metronic1.5.4/assets/css/pages/tasks.css" rel="stylesheet" type="text/css"/>
	
	--]
	
	<script type="text/javascript" src="${base}/static/metronic1.5.4/assets/plugins/select2/select2.js"></script>
	<script type="text/javascript" src="${base}/static/metronic1.5.4/assets/plugins/data-tables/jquery.dataTables.js"></script>
	<script type="text/javascript" src="${base}/static/metronic1.5.4/assets/plugins/data-tables/DT_bootstrap.js"></script>
	
	<script src="${base}/static/metronic1.5.4/assets/scripts/table-managed.js"></script>
	<link rel="stylesheet" href="${base}/static/metronic1.5.4/assets/plugins/data-tables/DT_bootstrap.css" />
	
	<link rel="stylesheet" type="text/css" href="${base}/static/metronic1.5.4/assets/plugins/select2/select2_metro.css" />
	
	[/#if]
	<script src="${base}/static/metronic1.5.4/assets/scripts/app.js" type="text/javascript"></script>
	<script src="${base}/static/metronic1.5.4/assets/plugins/jquery-validation/dist/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${base}/static/metronic1.5.4/assets/plugins/bootbox/bootbox.min.js" type="text/javascript" ></script>
	<!-- END PAGE LEVEL SCRIPTS -->
	<script type="text/javascript" src="${base}/static/scripts/history/json2.js"></script>
	[#--
	<script type="text/javascript" src="${base}/static/scripts/jquery/plugins/jquery.subscribe.js"></script>
	--]
	<script type="text/javascript" src="${base}/struts/js/plugins/jquery.subscribe.js"></script>
	<script type="text/javascript" src="${base}/static/scripts/history/history.adapter.jquery.min.js"></script>
	<script type="text/javascript" src="${base}/static/scripts/comm/jquery.struts2-3.1.0.min.js"></script>
	<script type="text/javascript" src="${base}/static/scripts/history/history.js"></script>
	<script type="text/javascript" src="${base}/static/scripts/history/history.html4.js"></script>
	<script type="text/javascript" src="${base}/static/scripts/beangle/beangle-2.4.2.js"></script>
	<script type="text/javascript" src="${base}/static/scripts/beangle/beangle-ui-2.4.2.js"></script>
	<script type="text/javascript" src="${base}/static/scripts/colorbox/jquery-colorbox-1.3.17.1.js"></script>
	<script src="${base}/static/metronic1.5.4/assets/plugins/bootstrap-toastr/toastr.min.js"></script>
	
	<link rel="stylesheet" type="text/css" href="${base}/static/metronic1.5.4/assets/plugins/bootstrap-toastr/toastr.min.css" />
	<link href="${base}/static/scripts/colorbox/colorbox.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript">
	jQuery(document).ready(function () {jQuery.scriptPath = "${base}/struts/";jQuery.struts2_jquery.minSuffix = "";	jQuery.ajaxSettings.traditional = true;jQuery.ajaxSetup ({cache: false});});
	</script>
	<!-- END JAVASCRIPTS -->
	
	<!-- BEGIN GLOBAL MANDATORY STYLES -->          
	<link href="${base}/static/metronic1.5.4/assets/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css"/>
	<link href="${base}/static/metronic1.5.4/assets/plugins/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css"/>
	<link href="${base}/static/metronic1.5.4/assets/plugins/uniform/css/uniform.default.css" rel="stylesheet" type="text/css"/>
	<!-- END GLOBAL MANDATORY STYLES -->

	<!-- BEGIN THEME STYLES --> 
	<link href="${base}/static/metronic1.5.4/assets/css/style-metronic.css" rel="stylesheet" type="text/css"/>
	<link href="${base}/static/metronic1.5.4/assets/css/style.css" rel="stylesheet" type="text/css"/>
	<link href="${base}/static/metronic1.5.4/assets/css/style-responsive.css" rel="stylesheet" type="text/css"/>
	<link href="${base}/static/metronic1.5.4/assets/css/plugins.css" rel="stylesheet" type="text/css"/>
	<link href="${base}/static/metronic1.5.4/assets/css/themes/default.css" rel="stylesheet" type="text/css" id="style_color"/>
	<link href="${base}/static/metronic1.5.4/assets/css/custom.css" rel="stylesheet" type="text/css"/>
	<!-- END THEME STYLES -->
	<link rel="shortcut icon" href="favicon.ico" />
	
	<link href="${base}/static/themes/myStyle.css" rel="stylesheet" type="text/css"/>
	
	${tag.body}
</head>
	[#if tag.parameters['bodyClass']??]
		<body class="${tag.parameters['bodyClass']}">
	[#else]
		<body>
	[/#if]
[#else]
	[@title/]
[/#if]

[#macro title]
<title>[#if (sc.title)??]${sc.title} - [/#if][#if (systemConfig['SystemVersion.name'])??]${(systemConfig['SystemVersion.name'])!}[#else]${(systemVersion.name)!} ${(systemVersion.version)!}[/#if]</title>
[#--<title>${isLogin}</title>--]
[/#macro]
[#--
<script type="text/javascript" src="${base}/static/scripts/comm/jquery.blockUI.js"></script>
<script type="text/javascript" src="${base}/static/scripts/my97/WdatePicker-4.72.js"></script>
--]