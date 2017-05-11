[#ftl]
[#import "comm.ftl" as c/]
<!-- BEGIN PAGE CONTENT-->
<div class="portlet box [#if tag.parameters['boxClass']??]${tag.parameters['boxClass']}[#else]blue[/#if]">
	<div class="portlet-title">
		<div class="caption"><i class="fa fa-reorder"></i><font class="formTitleLabel"></font></div>
		<div class="tools">
		</div>
	</div>
	<div class="portlet-body form">
		<!-- BEGIN FORM-->
		<form [#if !tag.parameters['cssClass']??]class="form-horizontal"[/#if] role="form">
			<div class="form-body">
				[#if tag.title??]
					<h3 class="form-section">
						${tag.title!}
					</h3>
				[/#if]
				${tag.body}
			</div>
		</form>
		<!-- END FORM--> 
	</div>
</div>