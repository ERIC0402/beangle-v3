[#ftl]
[#--
<input type="reset" class="ip_6" value="${b.text('action.reset')}"/>
--]
[#--
buttonClass:显示标题处的底色，不写为默认样式-灰色，其他样式可传值：blue  red  green  yellow  purple  grey  light-grey
--]
<button class="btn [#if tag.parameters['buttonClass']??]${tag.parameters['buttonClass']}[#else]default[/#if]" type="reset">${b.text('action.reset')}</button>