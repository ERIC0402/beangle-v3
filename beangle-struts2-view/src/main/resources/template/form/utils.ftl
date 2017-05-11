[#ftl]
[#macro ckeditor id name value]
<textarea id="${id}" name="${name}" class="ickeditor">${value}</textarea>
<script type="text/javascript" src="${base}/static/scripts/ckeditor_4.4/ckeditor.js"></script>
<script type="text/javascript">
setTimeout(function (){
CKEDITOR.replace("${id}");
}, 1)
</script>
[/#macro]