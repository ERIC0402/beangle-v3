[#ftl]
[#import "comm.ftl" as c/]
[@c.tr tag=tag]
<script src="${base}/static/scripts/plupload-2.1.1/plupload.full.min.js"></script>
[#if tag.multi??]
<link rel="stylesheet" href="${base}/static/scripts/plupload-2.1.1/jquery.plupload.queue/css/jquery.plupload.queue.css" type="text/css" media="screen" />
<script src="${base}/static/scripts/plupload-2.1.1/jquery.plupload.queue/jquery.plupload.queue.js"></script>
<style type="text/css">
	.plupload_filelist_footer{height: 36px;}
	.plupload_header{display:none;}
</style>
<div id="uploader${tag.id}">
    <p>Your browser doesn't have Flash, Silverlight or HTML5 support.</p>
</div>
<div id="template${tag.id}" style="display: none">
	<input name="fileData" value="#i#" />
	<input name="fileData#i#.filePath" value="#filePath#" />
	<input name="fileData#i#.fileName" value="#fileName#" />
	<input name="fileData#i#.fileSize" value="#fileSize#" />
</div>
<div id="hiden${tag.id}" style="display: none">
</div>

<script type="text/javascript">
(function (){
	var i = 0;
	var uploader = $("#uploader${tag.id}").pluploadQueue({
		file_data_name: "fileData",
		runtimes : 'html5,flash,silverlight,html4',
        url : "${base}/common/file!upload.action?folder=${tag.dir!}",
        max_file_size : eval('${tag.limit!"1024000"}') + 'mb',
        chunk_size: '1mb',
        rename: true,
        sortable: true,
        flash_swf_url : '${base}/static/scripts/plupload-2.1.1/Moxie.swf',
        silverlight_xap_url : '${base}/static/scripts/plupload-2.1.1/Moxie.xap',
        views: {
            list: true,
            thumbs: true, // Show thumbs
            active: 'thumbs'
        },
	    setup: function (uploader){
			uploader.bind('FilesAdded', function(up, files) {
				$("#uploader${tag.id} .plupload_buttons").show();
				$("#uploader${tag.id} .plupload_upload_status").hide();
			});
			uploader.bind('FileUploaded', function(up, file, res) {
				var data = eval("(" + res.response + ")");
				var t = $("#template${tag.id}").html();
				t = t.replace(/#i#/g, ++i).replace("#filePath#", data.filePath).replace("#fileName#", data.fileName).replace("#fileSize#", data.fileSize);
				$("#hiden${tag.id}").append(t);
			});
	    }
    });
})();
</script>
[#else]
<div>
	<img id="albumImg${tag.id}" src="${tag.url!}" style="display: none" [#if tag.width??] width="${tag.width}"[/#if] [#if tag.height??] height="${tag.height}"[/#if]/>
	<input id="valueipt${tag.id}" type="hidden" name="${tag.name}" value="${tag.value!}" />
</div>
<div id="container">
    <a id="browse${tag.id}" href="javascript:;">上传图片</a> 
</div>
<script type="text/javascript">
(function (){
	//[#if tag.url??]
	$("#albumImg${tag.id}").show();
	//[/#if]
	var uploader = new plupload.Uploader({
		multi_selection: false,
		file_data_name: "fileData",
		filters: {
		    mime_types : [
		        { title : "Image files", extensions : "jpg,gif,png" }
		    ]
		},
	    browse_button: 'browse${tag.id}',
	    url: "${base}/common/file!upload.action?folder=${tag.dir!}"
	});
	
	uploader.init();
	
	uploader.bind('FilesAdded', function(up, files) {
		uploader.start();
	});
	uploader.bind('FileUploaded', function(up, file, res) {
		var data = eval("(" + res.response + ")");
		$("#albumImg${tag.id}").attr("src", "${base}/common/file!download.action?file=" + data.img).show();
		$("#valueipt${tag.id}").val(data.img);
	});
})();
</script>
[/#if]
${tag.body!}
[/@]