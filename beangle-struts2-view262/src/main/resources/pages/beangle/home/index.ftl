[#ftl]
[@b.head]
<script type="text/javascript" src="${base}/static/scripts/comm/SimpleTab.js"></script>
<script type="text/javascript" src="${base}/static/scripts/home/index.js"></script>
<script type="text/javascript" src="${base}/static/scripts/ckeditor_4.0/ckeditor.js"></script>
[/@]
[#include "/pages/beangle/home/topMain.ftl"/]
[#include "/pages/beangle/home/nav.ftl"/]
<div id="BodyBg">
	<div id="MainBody">
		<div class="BlankLine1"></div>
		[#if menus?size == 0]
			[#assign nomenu=1/]
		[/#if]
		[#if !nomenu??]
			[@b.div id="left_menu" class="left_menu_p" style="margin-right:10px;" href="!menus?menu.id=${(menus?first.id)!}"/]
		[/#if]
		<div class="right_box" [#if nomenu??]style="width:1000px;"[/#if]>
			[#include "rightBox.ftl"/]
		</div>
	</div>
</div>
[@b.foot/]
