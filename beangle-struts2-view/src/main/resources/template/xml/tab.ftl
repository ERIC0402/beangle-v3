[#ftl/]
[#import "../list/comm.ftl" as c/]
[#if tag.href??]
	<div class="tab-pane" id="${tag.target}"></div><script>bg.Go('${tag.href}','${tag.target}')</script>
[#else]
	<div class="tab-pane" id="${tag.target}">
		[#if tag.parameters.hasTable??]${tag.body}[@c.table][/@][#else]${tag.body}[/#if]
	</div>
[/#if]