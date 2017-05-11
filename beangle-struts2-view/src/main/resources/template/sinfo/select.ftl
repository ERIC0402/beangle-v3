[#ftl]
[#if tag.parameters["svalue"]??]
${tag.parameters["svalue"]?html}
[#else]
${tag.value?html}&nbsp;
[/#if]
