[#ftl]
<div id="nav">
	<div class="nav_div">
		<ul>
			[#list menus?if_exists as module]
			<li>
				[#if module.entry??]
				<a href="${b.url(module.entry)}" target="_blank">[@c.i18nNameTitle module/]</a>
				[#else]
				[@b.a href="!menus?menu.id=${module.id}" target="left_menu" ][@c.i18nNameTitle module/][/@]
				[/#if]
			</li>
			[/#list]
		</ul>
	    <a href="#" class="closed_top_ico" title="隐藏顶部"></a>
	    <a href="#" class="expand_top_ico" title="显示顶部"></a>
	</div>
</div>
