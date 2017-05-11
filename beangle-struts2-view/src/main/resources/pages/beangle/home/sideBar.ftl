[#ftl]
<!-- BEGIN SIDEBAR -->
<div class="page-sidebar navbar-collapse collapse" style="z-index:2000;">
	<!-- BEGIN SIDEBAR MENU -->        
	<ul class="page-sidebar-menu pageSideBarMenu">
		<li>
			<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
			<div class="sidebar-toggler hidden-phone"></div>
			<!-- BEGIN SIDEBAR TOGGLER BUTTON -->
		</li>
		<li>
			<!-- BEGIN RESPONSIVE QUICK SEARCH FORM -->
			<div class="form-container">
				<form class="sidebar-search" method="POST">
					<div class="form-container">
						<div class="input-box">
							<a href="javascript:;" class="remove"></a>
							<input type="text" placeholder="搜索菜单" autocomplete="off" id="menuKeyInput"/>
							<input type="button" class="submit" value=" " id="searchButton"/>
						</div>
					</div>
				</form>
			</div>
			<script>
				function searchMenu(key){
					if(key!=""){
						$(".pageSideBarMenu ul, .pageSideBarMenu a").hide();
						$(".pageSideBarMenu .subMenu").each(function(){
							var a = $(this);
							if(a.text().indexOf(key) >= 0){
								showMenu(a);
							}
						});
					}else{
						$(".pageSideBarMenu a").show();
						$(".pageSideBarMenu ul").hide();
					}
				}
				function showMenu(a){
					a.show();
					a.parent().parent().show();
					var pa = a.parent().parent().prev();
					if(pa.length>0){
						showMenu(pa);
					}
				}
				$("#menuKeyInput").keyup(function(){
					searchMenu(this.value);
				});
				$("#searchButton").click(function(){
					searchMenu($("#menuKeyInput").val());
				});
			</script>
			<!-- END RESPONSIVE QUICK SEARCH FORM -->
		</li>
		[#list menus?if_exists as firstMenu]
			[#if firstMenu.entry??]
				<li>
					[@b.a href="${(firstMenu.entry)!}" target="main" class="subMenu"]
					[@icon menu=firstMenu/]
					<span class="title">[@c.i18nNameTitle firstMenu/]</span>
					<span class="selected"></span>
					[/@]
				</li>
			[#else]
				<li>
					<a href="javascript:;">
						[@icon menu=firstMenu/]
						<span class="title">[@c.i18nNameTitle firstMenu/]</span>
						<span class="arrow "></span>
						<span class="selected"></span>
					</a>
					<ul class="sub-menu">
						[#list submenus?if_exists as submenu]
							[#if submenu.id != firstMenu.id && submenu.parent?? && submenu.parent.id==firstMenu.id]
								<li>
									[#if submenu.entry??]
										[@b.a href="${(submenu.entry)!}" target="main" class="subMenu"]
											[@icon menu=submenu/]
											<span class="title">[@c.i18nNameTitle submenu/]</span>
										[/@]
									[#else]
										<a href="javascript:;">
											[@icon menu=firstMenu/]
											<span class="title">[@c.i18nNameTitle submenu/]</span>
											<span class="arrow"></span>
										</a>
										<ul class="sub-menu">
											[#list submenu.children?sort_by("code") as child]
												[#if submenus?seq_contains(child)]
													<li>
														[@b.a href="${(child.entry)!}" target="main" class="subMenu"]
															[@icon menu=child/]
															<span class="title">[@c.i18nNameTitle child/]</span>
														[/@]
													</li>
												[/#if]
											[/#list]
										</ul>
									[/#if]
								</li>
							[/#if]
						[/#list]
					</ul>
				</li>
			[/#if]
		[/#list]
	</ul>
	<!-- END SIDEBAR MENU -->
</div>
<!-- END SIDEBAR -->
[#macro icon menu]
	[#if menu.icon??]
		<i class="fa"><img style="margin-bottom:2px;height:14px;" src="${base}/common/file.action?method=downIconById&menuId=${firstMenu.id!}"/></i>
	[#else]
		<i class="fa ${(menu.systemIcon)!('fa-folder-open')}"></i> 
	[/#if]
[/#macro]
<script type="text/javaScript">
	$(".subMenu").click(function(e){
		var a = $(this);
		$(".page-title").html(a.text());
		
		$("#position_bar").html("");
		$(".pageSideBarMenu ul, .pageSideBarMenu li").removeClass("active");
		appendPosition(a);
	});
	
	function appendPosition(a){
		$("#position_bar").prepend("<a>"+a.text()+"</a>");
		$("#position_bar").prepend('<i class="fa fa-angle-right"></i>');
		var pa = a.parent().parent().prev();
		if(pa.length>0){
			appendPosition(pa);
		}
		a.parent().addClass("active");
	}
</script>