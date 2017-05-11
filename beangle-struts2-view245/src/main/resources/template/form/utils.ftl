[#ftl]
[#macro ckeditor id name value]
<textarea id="${id}" name="${name}">${value}</textarea>
<script type="text/javascript">
if(typeof(CKEDITOR) == "undefined"){
	document.write('<script type="text/javascript" src="${base}/static/scripts/ckeditor_4.0/ckeditor.js"></script\>');
}
</script>
<script type="text/javascript">
var ckeditor_id = "${id}";
setTimeout(function (){
CKEDITOR.replace(ckeditor_id);
}, 1)
</script>
[/#macro]