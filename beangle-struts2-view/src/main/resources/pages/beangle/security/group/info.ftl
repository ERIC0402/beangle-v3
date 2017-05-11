[#ftl]
[@b.head/]
[@b.toolbar title="查看"]bar.addBack();[/@]
[@b.form title="用户组信息" theme="info"]
	[@b.field label="common.name"]
		${userGroup.name}
	[/@]
	[@b.field label="common.creator"]
		${(userGroup.owner.name)!}
	[/@]
	[@b.field label="common.createdAt"]
		${userGroup.createdAt?string("yyyy-MM-dd")}
	[/@]
	[@b.field label="common.updatedAt"]
		${userGroup.updatedAt?string("yyyy-MM-dd")}
	[/@]
	[@b.field label="适用身份"]
		${userGroup.category.title}
	[/@]
	[@b.field label="common.status"]
		[#if userGroup.enabled] ${b.text("action.activate")}
		[#else]${b.text("action.freeze")}
		[/#if]
	[/@]
	[@b.field label="group.users"]
		[#list userGroup.members?sort_by(["user","name"]) as m] [#if m.user.enabled][@ems.userinfo user=m.user/][#else]<s>[@ems.userinfo user=m.user/]</s>[/#if]&nbsp;[/#list]
	[/@]
	[@b.field label="common.remark"]
		${userGroup.remark!}
	[/@]
	[@b.field label="其他"]
		[@b.div href="restriction!info?restriction.holder.id=${userGroup.id}&restrictionType=group" /]
	[/@]
[/@]
[#--
[@b.toolbar title='info.group' id="toolbarDiv"]
bar.addBack();
[/@]
<div class="portlet box blue">
	<div class="portlet-title">
		<div class="caption"><i class="fa fa-reorder"></i>查看</div>
		<div id="toolbarDiv" class="tools"></div>
	</div>
	
	<div class="portlet-body form">
	<form class="form-horizontal" role="form">
		<div class="form-body">
			<h3 class="form-section">基本信息</h3>
			<div class="row">
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-3">${b.text("common.name")}:</label>
						<div class="col-md-9">
							<p class="form-control-static">${userGroup.name}</p>
						</div>
					</div>
				</div>
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-3">${b.text("common.creator")}:</label>
						<div class="col-md-9">
							<p class="form-control-static">${(userGroup.owner.name)!}</p>
						</div>
					</div>
				</div>
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-3">${b.text("common.createdAt")}:</label>
						<div class="col-md-9">
							<p class="form-control-static">${userGroup.createdAt?string("yyyy-MM-dd")}</p>
						</div>
					</div>
				</div>
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-3">${b.text("common.updatedAt")}:</label>
						<div class="col-md-9">
							<p class="form-control-static">${userGroup.updatedAt?string("yyyy-MM-dd")}</p>
						</div>
					</div>
				</div>
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-3">适用身份:</label>
						<div class="col-md-9">
							<p class="form-control-static">${userGroup.category.title}</p>
						</div>
					</div>
				</div>
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-3">&nbsp;${b.text("common.status")}:</label>
						<div class="col-md-9">
							<p class="form-control-static">
							[#if userGroup.enabled] ${b.text("action.activate")}
					[#else]${b.text("action.freeze")}
					[/#if]</p>
						</div>
					</div>
				</div>
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-3">${b.text("group.users")}:</label>
						<div class="col-md-9">
							<p class="form-control-static">
							[#list userGroup.members?sort_by(["user","name"]) as m] [#if m.user.enabled][@ems.userinfo user=m.user/][#else]<s>[@ems.userinfo user=m.user/]</s>[/#if]&nbsp;[/#list]
							</p>
						</div>
					</div>
				</div>
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-3">${b.text("common.remark")}:</label>
						<div class="col-md-9">
							<p class="form-control-static">
							${userGroup.remark!}
							</p>
						</div>
					</div>
				</div>
				<div class="col-md-12">
					<div class="form-group">
						<label class="control-label col-md-3">其他:</label>
						<div class="col-md-9">
							<p class="form-control-static">
							[@b.div href="restriction!info?restriction.holder.id=${userGroup.id}&restrictionType=group" /]
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
		</form>
	</div>
</div>
[@b.foot/]
--]