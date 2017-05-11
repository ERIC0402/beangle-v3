[#ftl/]
[#--
boxClass:显示标题处的底色，不写为默认样式-灰色，其他样式可传值：blue  red  green  yellow  purple  grey  light-grey
addItem(title,action,imageClass,buttonStyle,alt)：imageClass：按钮上图标样式；buttonStyle：按钮样式底色
imageClass：可为静态图片路径，也可为系统默认的，例如：fa-plus  fa-edit  fa-times  fa-copy  fa-print 等等
buttonStyle：按钮底色，不写为默认样式-灰色，其他样式可传值：blue  red  green  yellow  purple  grey  light-grey
--]
[@b.messages slash="2"/]
<div class="row">
	<div class="col-md-12">
		<!-- BEGIN EXAMPLE TABLE PORTLET-->
		<div class="portlet box [#if tag.parameters['boxClass']??]${tag.parameters['boxClass']}[#else]blue[/#if]">
			[#if tag.parameters['title']??]
				<div class="portlet-title">
					<div class="caption"><i class="fa fa-globe"></i>${tag.parameters['title']!('列表')}</div>
					[#--
					<div class="tools">
						<a href="javascript:;" class="collapse"></a>
						<a href="#portlet-config" data-toggle="modal" class="config"></a>
						<a href="javascript:;" class="reload"></a>
						<a href="javascript:;" class="remove"></a>
					</div>
					--]
				</div>
			[/#if]
			<div class="portlet-body">
				<div class="table-toolbar" id="${tag.id}_bar1">
				[#--
					<div class="btn-group pull-right" >
						<button class="btn dropdown-toggle" data-toggle="dropdown">Tools <i class="fa fa-angle-down"></i>
						</button>
						<ul class="dropdown-menu pull-right">
							<li><a href="#">Print</a></li>
							<li><a href="#">Save as PDF</a></li>
							<li><a href="#">Export to Excel</a></li>
						</ul>
					</div>
					--]
				</div>
				<table class="table table-striped table-bordered table-hover" id="${tag.id}">
					[#if tag.cols?size>0]
						<thead>
							[#if tag.filterable="true" || tag.filters?size>0]
								<tr class="filterable">
									<td width="30">[@b.submit id="${tag.id}_filter_submit" class="grid-filter-submit" value=""/]</td>
									[#list tag.cols as cln]
										[#if !(cln.type??)]
											[#if tag.isFilterable(cln)]
												<td title="${cln.title}" [#if cln.width??]width="${cln.width}"[/#if] style="padding-left:3px">
													[#if tag.filters[cln.property]??]${tag.filters[cln.property]}[#else]
														<div style="margin-right:6px"><input class="form-control" type="text" name="${cln.propertyPath}"  maxlength="100" value="${(Parameters[cln.propertyPath]!)?html}" style="width:100%;"/></div>
													[/#if]
												</td>
											[#else]
												<td [#if cln.width??]width="${cln.width}"[/#if]></td>
											[/#if]
										[/#if]
									[/#list]
								</tr>
							[/#if]
							<tr>
								[#list tag.cols as cln]
									<th style="background-color:#e5e5e5;" [#if cln.width??]width="${cln.width}" [/#if][#if cln.type??] class="table-checkbox"[#if cln.type!="checkbox"]>[#else]><input type="${cln.type}" class="group-checkable" data-set="#sample_1 .checkboxes" name="${cln.boxname}box" onclick="bg.input.toggleCheckBox(document.getElementsByName('${cln.boxname}'),event)" title="${b.text('action.selectall')}"/>[/#if][#else][#if tag.isSortable(cln)] class="sorting" id="${cln.parameters['sort']!(tag.defaultSort(cln.property))}"[/#if]>${cln.title}[/#if]</th>
								[/#list]
							</tr>
						</thead>
					[/#if]
					<tbody id="${tag.id}_data">${tag.body}</tbody>
				</table>
				<div class="row">
					<div class="col-md-5 col-sm-12">
						<div class="dataTables_info" id="sample_1_info"></div>
					</div>
					<div class="col-md-7 col-sm-12">
						<div class="dataTables_paginate paging_bootstrap" id="${tag.id}_bar2">
						
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- END EXAMPLE TABLE PORTLET-->
	</div>
</div>
<script type="text/javascript">
	page_${tag.id} = bg.page("${request.requestURI}","${tag.parameters['target']!''}");
	page_${tag.id}.target("${tag.parameters['target']!""}",'${tag.id}').action("${request.requestURI}").addParams('${b.paramstring}').orderBy("${Parameters['orderBy']!('null')}");

	bg.ui.grid.init('${tag.id}',page_${tag.id});
	[#if tag.hasbar]
		bar=new bg.ui.gridbar(['${tag.id}_bar1'],'${(tag.parameters['title']?default(''))?replace("'","\"")}');
		pageBar=new bg.ui.gridbar(['${tag.id}_bar2'],'${(tag.parameters['title']?default(''))?replace("'","\"")}');
		pageBar.pageId('${tag.id}');
		[#if tag.pageable]
			page_${tag.id}.pageInfo(${tag.items.pageNo},${tag.items.pageSize},${tag.items.total});
			pageBar.addPage(page_${tag.id},[#if tag.parameters['fixPageSize']??][][#else]null[/#if],{${b.text('page.description')}});
			[#--
			[#if tag.notFullPage]bg.ui.grid.fillEmpty('${tag.id}_empty',${tag.items.pageSize},${tag.items?size},'${tag.emptyMsg!b.text("page.noData")}');[/#if]
			--]
		[/#if]
		[#if tag.var??]action=bar.addEntityAction('${tag.var}',page_${tag.id});[/#if]
		${tag.bar!}
	[/#if]
	[#-- refresh interval --]
	[#if tag.refresh??]
		if(typeof ${tag.id}_timer !="undefined"){clearTimeout(${tag.id}_timer);}
		var ${tag.id}_timer=setTimeout(function(){if(document.getElementById('${tag.id}')) page_${tag.id}.goPage()},${tag.refresh}*1000);
	[/#if]
	beangle.ui.list.init("${tag.id}");
	
	$(".filterable select").addClass("form-control");
</script>
[#--
<script>
	jQuery(document).ready(function() {
	   //App.init();
	   var aoColumns = [];
	   var aaSorting = [];
	   int count = 0;
	   $("#${tag.id} tr th").each(function(){
	   		if($(this).hasClass("sorting")){
	   			aoColumns.push(null);
	   		}else{
	   			aoColumns.push({ "bSortable": false });
	   		}
	   		if( $(this).hasClass("sorting_asc")){
	   			aaSorting.push([count, 'asc']);
	   		}else if($(this).hasClass("sorting_desc")){
	   			aaSorting.push([count, 'desc']);
	   		}
	   		count = count + 1;
	   });
	   
	   //TableManaged.init("${tag.id}",aoColumns,aaSorting,${(tag.items.pageSize)!(20)});
	});
</script>
--]