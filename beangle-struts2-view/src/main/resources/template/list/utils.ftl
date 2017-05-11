[#ftl]
[#include "../form/utils.ftl"/]
[#--
[#macro ckeditor id name value]
<textarea id="${id}" name="${name}">${value}</textarea>
<script type="text/javascript">
var ckeditor_id = "${id}";var edtinstance = CKEDITOR.instances[ckeditor_id];
if(edtinstance){CKEDITOR.remove(edtinstance);} 
CKEDITOR.replace( ckeditor_id,{fullPage : true,extraPlugins : 'docprops', skin : 'v2'});
</script>
[/#macro]
--]