[#ftl/]
[#assign firstTabId=""]
<div class="tabbable-custom " id="${tag.id}">
	<ul class="nav nav-tabs ">
		[#list tag.tabs as tab]
			<li [#if tab_index==0]class="active"[#assign firstTabId="${tab.id}"][/#if]><a href="#${tab.id}_target" data-toggle="tab">${tab.label}</a></li>
		[/#list]
	</ul>
	<div class="tab-content">
		${tag.body}
	</div>
</div>
<script>
jQuery(document).ready(function () { 
    var tabid = '${firstTabId}';      
    $("#"+tabid+"_target").addClass("active");
});
</script>