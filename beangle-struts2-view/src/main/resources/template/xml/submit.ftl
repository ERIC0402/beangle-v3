[#ftl]
[#--
<input type="submit" [#if tag.id??]id="${tag.id}"[/#if] value="${tag.value!'Submit'}" onclick="bg.form.submit('${tag.formId}',[#if tag.action??]'${tag.action}'[#else]null[/#if],[#if tag.target??]'${tag.target}'[#else]null[/#if],[#if tag.onsubmit??]${tag.onsubmit}[#else]null[/#if]);return false;"${tag.parameterString} class="ip_6"/>
--]
[#--
buttonClass:显示标题处的底色，不写为默认样式-灰色，其他样式可传值：blue  red  green  yellow  purple  grey  light-grey
swap:带箭头的图标，可选值有：m-icon-swapright m-icon-swapleft m-icon-swapdown m-icon-swapup 图标颜色：m-icon-white
buttonClass:显示标题处的底色，不写为默认样式-灰色，其他样式可传值：blue  red  green  yellow  purple  grey  light-grey
--]
<button class="btn [#if tag.parameters['buttonClass']??]${tag.parameters['buttonClass']}[#else]green[/#if]" type="submit" [#if tag.id??]id="${tag.id}"[/#if] onclick="bg.form.submit('${tag.formId}',[#if tag.action??]'${tag.action}'[#else]null[/#if],[#if tag.target??]'${tag.target}'[#else]null[/#if],[#if tag.onsubmit??]${tag.onsubmit}[#else]null[/#if]);return false;"${tag.parameterString}>
	${tag.value!'Submit'}
	<i class="[#if tag.parameters['swap']??]${tag.parameters['swap']}[#else]m-icon-swapright[/#if] [#if !tag.parameters['buttonClass']?? || tag.parameters['buttonClass']!='default']m-icon-white[/#if]"></i>
</button>