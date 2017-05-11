[#ftl/]
[#import "../list/comm.ftl" as c/]
[#if tag.href??]
<div id="${tag.target}" class="_ajax_target"></div><script>bg.Go('${tag.href}','${tag.target}')</script>
[#else]
<div id="${tag.target}">[#if tag.parameters.hasTable??][@c.table]${tag.body}[/@][#else]${tag.body}[/#if]</div>
[/#if]