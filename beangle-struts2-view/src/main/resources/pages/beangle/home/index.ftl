[#ftl]
[@b.head bodyClass="page-header-fixed bodyImage"]
[#--
<script type="text/javascript" src="${base}/static/scripts/ckeditor_4.0/ckeditor.js"></script>
--]
<script type="text/javascript" src="${base}/static/scripts/comm/SimpleTab.js"></script>
<script type="text/javascript" src="${base}/static/scripts/home/index.js"></script>
[/@]
[#--
<frameset rows="42,*" cols="*" frameborder="0"  border="0" framespacing="0" id="headframe">
    <frame src="home.action?method=topMain" name="topFrame" scrolling="No" noresize="noresize" id="topFrame"/>
    <frameset cols="155,*" frameborder="0" border="0" framespacing="0" id="middleFrame">
        <frame src="home.action?method=moduleList&parentCode=${parentCode}"  name="leftFrame" scrolling="no"   id="leftFrame"/>
        <frame src="home.action?method=welcome" name="main" id="main"/>
    </frameset>
    <#--<frame src="home.action?method=bottomframe" name="bottomFrame" noresize="noresize" scrolling="no" id="bottomFrame"/>-->
</frameset>
--]
[#include "/pages/beangle/home/topMain.ftl"/]
<div class="clearfix"></div>
<div class="page-container">
	[#include "/pages/beangle/home/sideBar.ftl"/]
	<div class="page-content">
		[#include "/pages/beangle/home/styleCustomizer.ftl"/]
		<!-- BEGIN PAGE HEADER-->
		<div class="row">
			<div class="col-md-12">
				<!-- BEGIN PAGE TITLE & BREADCRUMB-->
				<h3 class="page-title">
					首页
				</h3>
				[#--
				<ul class="page-breadcrumb breadcrumb">
					<li>
						<i class="fa fa-home"></i>
						<a>首页</a> 
					</li>
					<li id="position_bar"></li>
				</ul>
				--]
				<!-- END PAGE TITLE & BREADCRUMB-->
			</div>
		</div>
		<!-- END PAGE HEADER-->
		<div id="main">[#include "welcome.ftl"/]</div>
	</div>
</div>
<script>
	jQuery(document).ready(function() {    
	   App.init(); // initlayout and core plugins
	   //Index.init();
	});
</script>
[@b.foot/]