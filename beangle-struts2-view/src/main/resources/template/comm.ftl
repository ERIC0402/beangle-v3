[#ftl]

[#macro enabled enabled right="启用" error="禁用" yes="" no=""]
	[@sfyx enabled right error yes no/]
[/#macro]

[#macro sfyx enabled right="有效" error="无效" yes="" no=""]
	[#if enabled]
		<font color="green">[#if yes == ""]${right}[#else]${yes}[/#if]</font>
	[#else]
		<font color="red">[#if no == ""]${error}[#else]${no}[/#if]</font>
	[/#if]
[/#macro]

[#function formTitle name entity]
[#assign str]${name}-[#if entity.id??]修改[#else]添加[/#if][/#assign]
[#return str]
[/#function]

[#macro substring str mx dot="..."]
[@substringLabel str mx false dot/]
[/#macro]

[#macro substringLabel str mx label=true dot="..."]
[#if label]<label title="${str}">[/#if][#if str?length gt mx][#assign imx = mx - 2/]${str[0..imx]}${dot}[#else]${str}[/#if][#if label]</label>[/#if]
[/#macro]

[#macro select data=[] value="" valueKey="id" nameKey="name" noOption=false empty="请选择..."  extra...]
<select [#list extra?keys as attr] ${attr}="${extra[attr]}"[/#list]>
<option value="">${empty}</option>
[#list data as v]
	[#if noOption]
		[#nested v/]
	[#else]
		<option value="${v[valueKey]}" title="${v[nameKey]}" [#if value==v[valueKey]?string]selected=selected[/#if]>[@c.substring str=v[nameKey] mx=20/]</option>
	[/#if]
[/#list]
</select>
[/#macro]

[#macro required]
<em class="required">*</em>
[/#macro]

[#--将秒转换为时分秒--]
[#macro formatHHMMSS duration]
	[#assign hh=(duration/3600)?number?int]
	[#assign mm=((duration-hh*3600)/60)?number?int]
	[#assign ss=(duration-hh*3600-mm*60)?number?int]
	[#if hh>0]${hh}小时[/#if][#if mm>0]${mm}分[/#if][#if (ss>=0)]${ss}秒[/#if]
[/#macro]

[#--将秒转换为时分--]
[#macro formatHHMM duration]
	[#assign hh=(duration/60)?number?int]
	[#assign mm=(duration-hh*60)?number?int]
	[#if hh>0]${hh}小时[/#if][#if (mm>0)]${mm}分[/#if]
[/#macro]

[#macro substringPoint str mx label=true]
[#if label]<label title="${str}">[/#if][#if str?length gt mx]${str[0..mx]}...[#else]${str}[/#if][#if label]</label>[/#if]
[/#macro]

[#macro errormsgTab errormsg]
[@b.tab label="出错啦"]
		<fieldset> 
			<legend>错误信息</legend>
			<ol>
				<li>
					<div>
						${errormsg!}
					</div>
				</li>
			</ol>
		</fieldset>
[/@]
[/#macro]

[#macro i18nNameTitle(entity)]
	[#if locale.language?index_of("en")!=-1]
		[#if entity.engTitle?if_exists?trim==""]${entity.title?if_exists}[#else]${entity.engTitle?if_exists}[/#if]
	[#else]
		[#if entity.title?if_exists?trim!=""]${entity.title?if_exists}[#else]${entity.engTitle?if_exists}[/#if]
	[/#if]
[/#macro]

[#function isAjax]
[#if (request.getHeader('x-requested-with')!'') == "XMLHttpRequest" || Parameters['x-requested-with']??]
[#return true/]
[#else]
[#return false/]
[/#if]
[/#function]

[#macro chosenCssAndJs]
<link rel="stylesheet" href="${base}/static/scripts/chosen/chosen.css" />
<script type="text/JavaScript" src="${base}/static/scripts/chosen/chosen.jquery.js"></script>
<script type="text/JavaScript" src="${base}/static/scripts/chosen/ajax-chosen.js"></script>
[/#macro]
[#macro colorboxJsAndCss]
<script type="text/javascript" src="${base}/static/scripts/colorbox/jquery-colorbox-1.3.17.1.js"></script>
<link href="${base}/static/scripts/colorbox/colorbox.css" rel="stylesheet" type="text/css" />
[/#macro]
[#macro jqueryUIJsAndCss]
<script type="text/javascript" src="${base}/static/scripts/jquery-ui/jquery-ui-1.9.0.custom.min.js"></script>
<link id="jquery_theme_link" rel="stylesheet" href="${base}/static/scripts/jquery-ui/custom-theme/jquery-ui-1.9.0.custom.css" type="text/css"/>
[/#macro]
[#macro uploadifyJsAndCss]
<script src="${base}/static/scripts/uploadify/upload.js"></script>
<script src="${base}/static/scripts/uploadify/jquery.uploadify.v2.1.4.js"></script>
<script src="${base}/static/scripts/uploadify/swfobject.js"></script>
<link href="${base}/static/scripts/uploadify/uploadify.css" rel="stylesheet" type="text/css"/> 
[/#macro]

[#macro trClass index][#if index%2==0]griddata-even[#else]griddata-odd[/#if][/#macro]
[#function dateToNumber date][#if date?string == "0"][#return 0/][/#if][#return date?string('yyyyMMddHHmmss')?number/][/#function]
[#macro sjzt kssj jssj]
	[#assign nkssj=dateToNumber(kssj)/]
	[#assign njssj=dateToNumber(jssj)/]
	[#assign nnow=dateToNumber(now)/]
	[#if nkssj != 0 && nkssj gt nnow]
		<font color="green">未开始</font>
	[#elseif nkssj lte nnow && (nnow lte njssj || njssj == 0)]
		<font color="red">进行中</font>
	[#else]
		已结束
	[/#if]
[/#macro]
[#function monthBetwwnTwoDate month startDate endDate]
[#assign m = month?number/]
[#assign startMonth = startDate?string('yyyyMM')?number/]
[#assign endMonth = endDate?string('yyyyMM')?number/]
[#list startMonth..endMonth as cm]
[#if cm % 100 == m][#return true/][/#if]
[/#list]
[#return false/]
[/#function]
