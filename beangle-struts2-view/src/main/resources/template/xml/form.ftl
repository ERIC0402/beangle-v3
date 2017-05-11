[#ftl]
<form id="${tag.id}" name="${tag.name}" action="${tag.action}" method="post" [#if tag.target??]target="${tag.target}"[/#if]${tag.parameterString} [#if tag.validate=="true" || tag.onsubmit??]onsubmit="return onsubmit${tag.id}()"[/#if]>
	${tag.body}
</form>
<script type="text/javascript">
bg.ui.form.init("${tag.id}");

jQuery(document).ready(function() {       
   if (jQuery().select2) {
        $('.select2me').select2({
            placeholder: "请选择...",
            allowClear: true
        });
    }
});
</script>
[#if (tag.validate!"")=="true" || tag.onsubmit??]
<script type="text/javascript">
	var form = $('#${tag.id}');
	var rules = {};
	[#list tag.checks?keys as id]
	[#assign check=tag.checks[id]/]
	rules[$("#${id}").attr("name")] = new ValidateStr()${check}.getConfig();
	[/#list]
	ValidateForm(form, rules);
	function onsubmit${tag.id}(){
		return form.valid();
	}
	
</script>
[/#if]