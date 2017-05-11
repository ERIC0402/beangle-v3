[#ftl]
[#macro ckeditor id name value]
<textarea id="${id}" name="${name}" class="ickeditor">${value}</textarea>
<script type="text/javascript" src="${base}/static/scripts/ckeditor_4.4/ckeditor.js"></script>
<script type="text/javascript" src="${base}/static/scripts/ckeditor_4.4/adapters/jquery.js"></script>
<script type="text/javascript">
setTimeout(function (){
var editor = CKEDITOR.replace("${id}");
editor.on( 'change', function( evt ) {
    // getData() returns CKEditor's HTML content.
    $("#${id}").html(evt.editor.getData());
});
}, 1);

</script>
[/#macro]

[#macro ckeditorWys id name value]
<textarea id="${id}" name="${name}" class="ickeditor">${value}</textarea>
<script type="text/javascript" src="${base}/static/scripts/ckeditor_4.4/ckeditor.js"></script>
<script type="text/javascript" src="${base}/static/scripts/ckeditor_4.4/adapters/jquery.js"></script>
<script type="text/javascript">
setTimeout(function (){
 CKEDITOR.replace( '${id}',{toolbar :[[ 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo' ]],enterMode:CKEDITOR.ENTER_BR,shiftEnterMode:CKEDITOR.ENTER_BR});
}, 1)
</script>
[/#macro]