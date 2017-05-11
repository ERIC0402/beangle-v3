[#ftl]
[#include "/template/list/comm.ftl"]
[#--
boxClass:显示标题处的底色，不写为默认样式-灰色，其他样式可传值：blue  red  green  yellow  purple  grey  light-grey
--]
<!-- START FORM SCRIPTS -->
<script type="text/javascript" src="${base}/static/metronic1.5.4/assets/plugins/jquery-inputmask/jquery.inputmask.bundle.min.js"></script>   
<script type="text/javascript" src="${base}/static/metronic1.5.4/assets/plugins/jquery.input-ip-address-control-1.0.min.js"></script>
<script type="text/javascript" src="${base}/static/metronic1.5.4/assets/plugins/jquery-multi-select/js/jquery.multi-select.js"></script>
<script type="text/javascript" src="${base}/static/metronic1.5.4/assets/plugins/jquery-multi-select/js/jquery.quicksearch.js"></script> 
  
<script type="text/javascript" src="${base}/static/metronic1.5.4/assets/plugins/jquery-validation/dist/jquery.validate.js"></script>
<script type="text/javascript" src="${base}/static/metronic1.5.4/assets/plugins/jquery-validation/dist/additional-methods.js"></script>
<script src="${base}/static/metronic1.5.4/assets/plugins/bootstrap-maxlength/bootstrap-maxlength.min.js" type="text/javascript" ></script>
<script src="${base}/static/metronic1.5.4/assets/plugins/bootstrap-touchspin/bootstrap.touchspin.js" type="text/javascript" ></script>
<script type="text/javascript" src="${base}/static/scripts/beangle/beangle.validate.js"></script>

<!-- END FORM SCRIPTS -->
[#import "/template/form/comm.ftl" as c/]<!-- BEGIN PAGE CONTENT-->
<div class="row">
	<div class="col-md-12">
		<div class="tabbable tabbable-custom boxless">
		[#--
			<ul class="nav nav-tabs">
				<li class="active"><a href="#tab_0" data-toggle="tab">Form Actions</a></li>
				<li><a href="#tab_1" data-toggle="tab">2 Columns</a></li>
				<li><a  href="#tab_2" data-toggle="tab">2 Columns Horizontal</a></li>
				<li><a href="#tab_3" data-toggle="tab">2 Columns View Only</a></li>
				<li><a  href="#tab_4" data-toggle="tab">Row Seperated</a></li>
				<li><a  href="#tab_5" data-toggle="tab">Bordered</a></li>
				<li><a  href="#tab_6" data-toggle="tab">Row Stripped</a></li>
				<li><a  href="#tab_7" data-toggle="tab">Label Stripped</a></li>
			</ul>
			--]
			<div class="tab-pane active">
				<div class="portlet box [#if tag.parameters['boxClass']??]${tag.parameters['boxClass']}[#else]blue[/#if]">
					<div class="portlet-title">
						<div class="caption"><i class="fa fa-reorder"></i><font class="formTitleLabel"></font></div>
						<div class="tools">
							[#--
							<a href="javascript:;" class="collapse"></a>
							<a href="#portlet-config" data-toggle="modal" class="config"></a>
							<a href="javascript:;" class="reload"></a>
							<a href="javascript:;" class="remove"></a>
							--]
						</div>
					</div>
					<div class="portlet-body form">
						<!-- BEGIN FORM-->
						<form id="${tag.id}" [#if !tag.parameters['cssClass']??]class="form-horizontal"[/#if] name="${tag.name}" action="${tag.action}" method="post" [#if tag.target??]target="${tag.target}"[/#if]${tag.parameterString} [#if tag.validate=="true" || tag.onsubmit??]onsubmit="return onsubmit${tag.id}()"[/#if]>
							<div class="form-wizard">
								<div class="form-body">
									[#if tag.parameters['datas']??&&tag.parameters['datas']?size>0]
										[@showProgressBar datas=tag.parameters['datas'] currentStep=tag.parameters['currentStep']/]
									[/#if]
									[#if tag.title??]
										<h2 class="form-section">
											${tag.title!}
										</h2>
									[/#if]
									[#if !tag.parameters['hiddenMsg']??]
										[@b.messages formId="${tag.id}"/]
									[/#if]
									${tag.body}
								</div>
							</div>
						</form>
						<!-- END FORM--> 
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- END PAGE CONTENT-->

<script type="text/javascript">
bg.ui.form.init("${tag.id}");


</script>
[#if (tag.validate!"")=="true" || tag.onsubmit??]
<script type="text/javascript">
	var form = $('#${tag.id}');
	var rules = {};
	[#list tag.checks?keys as id]
	[#assign check=tag.checks[id]/]
	rules[$("#${id?replace('.','\\\\.')}").attr("name")] = new ValidateStr()${check}.getConfig();
	[/#list]
	ValidateForm(form, rules);
	function onsubmit${tag.id}(){
		return form.valid();
	}
	
</script>
[/#if]