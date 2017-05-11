[#ftl/]
<div class="grid">[@b.messages slash="4"/][#if tag.hasbar]<div id="${tag.id}_bar1" class="gridbar"></div>[/#if]
<table id="${tag.id}" class="gridtable"${tag.parameterString}>
[#if tag.cols?size>0]
<thead class="gridhead">
[#if tag.filterable="true" || tag.filters?size>0]
<tr>
	<th class="gridselect-top">[@b.submit id="${tag.id}_filter_submit" class="grid-filter-submit" value=""/]</th>
	[#list tag.cols as cln]
	[#if !(cln.type??)]
	[#if tag.isFilterable(cln)]
	<th title="${cln.title}" [#if cln.width??]width="${cln.width}"[/#if] style="padding-left:3px">
	[#if tag.filters[cln.property]??]${tag.filters[cln.property]}[#else]
	<div style="margin-right:6px"><input type="text" name="${cln.propertyPath}"  maxlength="100" value="${(Parameters[cln.propertyPath]!)?html}" style="width:100%;"/></div>
	[/#if]
	</th>
	[#else]<th [#if cln.width??]width="${cln.width}"[/#if]></th>[/#if]
	[/#if]
	[/#list]
</tr>
[/#if]
<tr>
[#list tag.cols as cln]<th [#if cln.width??]width="${cln.width}" [/#if][#if cln.type??] class="gridselect-top"[#if cln.type!="checkbox"]>[#else]><input type="${cln.type}" name="${cln.boxname}box" onclick="bg.input.toggleCheckBox(document.getElementsByName('${cln.boxname}'),event)" title="${b.text('action.selectall')}"/>[/#if][#else][#if tag.isSortable(cln)]class="gridhead-sortable" id="${cln.parameters['sort']!(tag.defaultSort(cln.property))}"[/#if]>${cln.title}[/#if]</th>
[/#list]
</tr>
</thead>
[/#if]

<tbody id="${tag.id}_data">${tag.body}</tbody>
</table>
[#if tag.hasbar]
[#if (tag.pageable && tag.notFullPage) || tag.items?size == 0]
[#assign pageSize][#if tag.pageable]${tag.items.pageSize}[#else]20[/#if][/#assign]
[#assign gridemptyHeight=(pageSize?number - tag.items?size)*16/]
<div class="gridempty" id="${tag.id}_empty" style="height: ${gridemptyHeight}px; "></div>
[/#if]
<div id="${tag.id}_bar2"  class="gridbar"></div>
[/#if]
<script type="text/javascript">
page_${tag.id} = bg.page("${request.requestURI}","${tag.parameters['target']!""}");
page_${tag.id}.target("${tag.parameters['target']!""}",'${tag.id}').action("${request.requestURI}").addParams('${b.paramstring}').orderBy("${Parameters['orderBy']!('null')}");
bg.ui.grid.init('${tag.id}',page_${tag.id});
[#if tag.hasbar]
bar=new bg.ui.gridbar(['${tag.id}_bar1','${tag.id}_bar2'],'${(tag.parameters['title']?default(''))?replace("'","\"")}');
bar.pageId('${tag.id}')
[#if tag.pageable]
page_${tag.id}.pageInfo(${tag.items.pageNo},${tag.items.pageSize},${tag.items.total});
bar.addPage(page_${tag.id},[#if tag.parameters['fixPageSize']??][][#else]null[/#if],{${b.text('page.description')}});
[#--
[#if tag.notFullPage]bg.ui.grid.fillEmpty('${tag.id}_empty',${tag.items.pageSize},${tag.items?size},'${tag.emptyMsg!b.text("page.noData")}');[/#if]
--]
[/#if]
[#if tag.var??]action=bar.addEntityAction('${tag.var}',page_${tag.id});[/#if]
${tag.bar!}
[/#if]
[#-- refresh interval --]
[#if tag.refresh??]
if(typeof ${tag.id}_timer !="undefined"){clearTimeout(${tag.id}_timer)}
var ${tag.id}_timer=setTimeout(function(){if(document.getElementById('${tag.id}')) page_${tag.id}.goPage()},${tag.refresh}*1000);
[/#if]
</script>
</div>