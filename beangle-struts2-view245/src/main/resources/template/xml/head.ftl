[#ftl]
[#if !(request.getHeader('x-requested-with')??) && !Parameters['x-requested-with']??]
[#--<?xml version="1.0" encoding="UTF-8" ?><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">--]
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="${locale.language}" xml:lang="${locale.language}">
<head>
	<title>[#if tag.parameters['title']??]${tag.parameters['title']} - [/#if]${(systemVersion.name)!} ${(systemVersion.version)!}</title>
	<meta http-equiv="content-type" content="text/html;charset=utf-8" />
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>
	<meta http-equiv="content-style-type" content="text/css"/>
	<meta http-equiv="content-script-type" content="text/javascript"/>
	<script type="text/javascript">var base = "${base!}", contextPath = "${base!}", JSESSIONID = "${Session['id']!}";</script>
	[#--]@beangle_js_head compressed=(Parameters['devMode']?exists)?string("false","true") /--]
	[@beangle_js_head compressed="false" /]
	<script language="JavaScript" type="text/javascript" src="${base}/static/scripts/comm/jquery.blockUI.js"></script>
	<script type="text/javascript" src="${base}/static/scripts/my97/WdatePicker-4.72.js"></script>
	<link href="${base}/static/themes/${b.theme.ui}/homePage.css" rel="stylesheet" type="text/css" />
	<script src="${base}/static/scripts/js/view_menus.js" type="text/javascript"></script>
	<script type="text/javascript">
		function NamedFunction(name,func){
			this.name=name;
			this.func=func;
		}
	</script>
${tag.body}
</head>
<body>
[/#if]

[#macro beangle_js_head(compressed)]
		<script type="text/javascript" src="${base}/static/scripts/comm/jquery-latest.js"></script>
<script type="text/javascript" src="${base}/struts/js/plugins/jquery.subscribe.js"></script>
<script type="text/javascript" src="${base}/static/scripts/jquery-ui/jquery-ui-1.8.14.custom.min.js"></script>
<link id="jquery_theme_link" rel="stylesheet" href="${base}/static/scripts/jquery-ui/smoothness/jquery-ui-1.8.14.custom.css" type="text/css"/>

[#--<script type="text/javascript" src="${base}/struts/js/struts2/jquery.struts2.js"></script>--]
<script type="text/javascript" src="${base}/static/scripts/comm/jquery.struts2-3.1.0.min.js"></script>

<script type="text/javascript">
	jQuery(document).ready(function () {
  	jQuery.scriptPath = "${base}/struts/";
	jQuery.struts2_jquery.minSuffix = "";
	jQuery.ajaxSettings.traditional = true;

	jQuery.ajaxSetup ({
		cache: false
	});
});
</script>
	<link id="beangle_theme_link" href="${base}/static/themes/${b.theme.ui}/beangle-ui.css" rel="stylesheet" type="text/css" />
	<link href="${base}/static/themes/${b.theme.ui}/colorbox.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${base}/static/scripts/colorbox/jquery-colorbox-1.3.17.1.js"></script>
	<script type="text/javascript">if ( typeof window.JSON === 'undefined' ) { document.write('<script src="${base}/static/scripts/history/json2.js"><\/script>'); }</script>
	<script type="text/javascript" src="${base}/static/scripts/history/history.adapter.jquery.js"></script>
	<script type="text/javascript" src="${base}/static/scripts/history/history.js"></script>
	<script type="text/javascript" src="${base}/static/scripts/history/history.html4.js"></script>
	<script type="text/javascript" src="${base}/static/scripts/beangle/beangle-2.4.2.js"></script>
	<script type="text/javascript" src="${base}/static/scripts/beangle/beangle-ui-2.4.2.js"></script>
[/#macro]
