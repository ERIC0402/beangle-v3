[#ftl]
[#setting datetime_format="yyyy-MM-dd HH:mm"/]
[#if !(request.getHeader('x-requested-with')??) && !Parameters['x-requested-with']??]
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html dir="ltr" lang="${locale.language}" xml:lang="${locale.language}">
<head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=8">
[#--	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
	<meta http-equiv="content-style-type" content="text/css"/>
	<meta http-equiv="content-script-type" content="text/javascript"/>--]
	[@title/]
	[#--[@beangle_js_head compressed=(Parameters['devMode']?exists)?string("false","true") /--]
	[@beangle_js_head compressed="false" /]
	<link href="${base}/static/themes/${b.theme.ui}/style_comm.css" rel="stylesheet" type="text/css" />
	<link href="${base}/static/themes/${b.theme.ui}/main.css" rel="stylesheet" type="text/css" />
${tag.body}
</head>
<body>
[#else]
	[@title/]
[/#if]

[#macro title]
<title>[#if (sc.title)??]${sc.title} - [/#if][#if (systemConfig['SystemVersion.name'])??]${(systemConfig['SystemVersion.name'])!}[#else]${(systemVersion.name)!} ${(systemVersion.version)!}[/#if]</title>
[/#macro]
[#--
<script type="text/javascript" src="${base}/static/scripts/comm/jquery.blockUI.js"></script>
[#-- <script type="text/javascript" src="${base}/static/scripts/my97/WdatePicker-4.72.js"></script> --]


[#macro beangle_js_head(compressed)]
<script type="text/javascript">window.contextPath = "${base!}";var base = "${base!}", JSESSIONID = "${(Session['id'])!}";var tophref = location.href;</script>
<script type="text/javascript" src="${base}/static/scripts/comm/jquery-latest.js"></script>
<script type="text/javascript" src="${base}/static/scripts/jquery-ui/jquery-ui-1.9.0.custom.min.js"></script>
<link id="jquery_theme_link" rel="stylesheet" href="${base}/static/scripts/jquery-ui/custom-theme/jquery-ui-1.9.0.custom.css" type="text/css"/>
<script type="text/javascript" src="${base}/static/scripts/history/json2.js"></script>
<script type="text/javascript" src="${base}/struts/js/plugins/jquery.subscribe.js"></script>
<script type="text/javascript" src="${base}/static/scripts/history/history.adapter.jquery.min.js"></script>
[#--
<script type="text/javascript" src="${base}/struts/js/struts2/jquery.struts2.min.js"></script>
--]
<script type="text/javascript" src="${base}/static/scripts/comm/jquery.struts2-3.1.0.min.js"></script>
<script type="text/javascript" src="${base}/static/scripts/history/history.js"></script>
<script type="text/javascript" src="${base}/static/scripts/history/history.html4.js"></script>
<script type="text/javascript" src="${base}/static/scripts/beangle/beangle-2.4.2.js"></script>
<script type="text/javascript" src="${base}/static/scripts/beangle/beangle-ui-2.4.2.js"></script>
<link id="beangle_theme_link" href="${base}/static/themes/${b.theme.ui}/beangle-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/static/scripts/colorbox/jquery-colorbox-1.3.17.1.js"></script>
<link href="${base}/static/scripts/colorbox/colorbox.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${base}/static/scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
jQuery(document).ready(function () {jQuery.scriptPath = "${base}/struts/";jQuery.struts2_jquery.minSuffix = "";	jQuery.ajaxSettings.traditional = true;jQuery.ajaxSetup ({cache: false});});
</script>
[/#macro]
