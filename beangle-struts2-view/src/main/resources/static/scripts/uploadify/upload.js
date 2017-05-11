/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function uploadimg(options, zipImg) {
	options.method = "upload";
	options.zipImg = options.zipImg || zipImg || true;
	$('#' + options.upBtn).uploadify({
		fileDesc : '图片(*.jpg;*.jpeg)',
		fileExt : '*.jpg;*.jpeg;*.gif;*.png',
		multi : false,
		auto : true,
		scriptData : options,
		folder : options.dir,
		sizeLimit : options.sizeLimit,
		onComplete : function(event, ID, fileObj, response, data) {
			var mydata = eval('(' + response + ')');
			var img = mydata.img;
			options.imgNmae && (options.imgName = options.imgNmae);
			$("[name='" + options.imgName + "']").val(img);
			if (img.indexOf("/") != 0) {
				img = "/" + img;
			}
			$("#" + options.showImg).attr("src", base + "/common/download.action?file=" + img).css("display", "");
			options.ofile = img;
			$('#' + options.upBtn).uploadifySettings('scriptData', options);
		}
	});
}

function uploadFile(options, onComplete) {
	options.method = "uploadFile";
	$('#' + options.upBtn).uploadify({
		fileDesc : options.fileDesc || '文件(*.*)',
		fileExt : options.fileExt || '*.*',
		multi : false,
		auto : true,
		scriptData : options,
		folder : options.dir,
		sizeLimit : options.sizeLimit || 10*1024*1024,
		onComplete : function(event, ID, fileObj, response, data) {
			var mydata = eval('(' + response + ')');
			if (onComplete) {
				onComplete(mydata);
			} else {
				var filePath = mydata.filePath;
				var fileName = mydata.fileName;
				var fileSize = mydata.fileSize;
				$("#" + options.filePath).val(filePath);
				$("#" + options.fileName).val(fileName);
				$("#" + options.fileSize).val(fileSize);
			}
		}
	});
}
