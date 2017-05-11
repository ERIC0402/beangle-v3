[#ftl]
[#import "comm.ftl" as c/]
[@c.tr tag=tag]
<img id="albumImg${tag.id}" src="${tag.url!}" style="display: none" [#if tag.width??] width="${tag.width}"[/#if] [#if tag.height??] height="${tag.height}"[/#if]/>
<div>
	<input id="uploadBtn${tag.id}" type="button" value="上传文件" />
	<div id="dropzonediv${tag.id}" style="min-height:200px; display: none;">
		<div class="dz-default dz-message"><span>Drop files here to upload</span></div>
	</div>
</div>
<input id="valueipt${tag.id}" type="hidden" name="${tag.name}" value="${tag.value!}" />

<link href="${base}/static/metronic1.5.4/assets/plugins/dropzone/css/dropzone.css" rel="stylesheet"/>
<script src="${base}/static/metronic1.5.4/assets/plugins/dropzone/dropzone.js"></script>
<script type="text/javascript">
	//[#if tag.url??]
	$("#albumImg${tag.id}").show();
	//[/#if]
	$(function() {
		$("#uploadBtn${tag.id}").click(function (){
			$("#dropzonediv${tag.id}").slideDown();
		});
		var id = "dropzonediv${tag.id}";
		Dropzone.options[id] = {
		  url : "${base}/common/file!upload.action?folder=${tag.dir!}",
		  paramName: "fileData", maxFilesize : "${tag.limit!'1024000'}"*1, success : function (file, data,xhr){
		  	data = eval('(' + data + ')');
		  	$("#albumImg${tag.id}").attr("src", "${base}/common/file!download.action?file=" + data.img);
		  	$("#valueipt${tag.id}").val(data.img);
		  	this.removeFile(file);
		  	$("#dropzonediv${tag.id}").slideUp();
		  	return true;
		  }
		};
		$("#dropzonediv${tag.id}").dropzone().addClass("dropzone");
	}); 
</script>
${tag.body!}
[/@]