/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function uploadimg(options,zipImg){
	options.method="upload";
	if(zipImg == undefined){
		zipImg =true;
	}
    options.zipImg = zipImg;
    $('#' + options.upBtn).uploadify({
        fileDesc : '图片(*.jpg;*.jpeg)',
        fileExt : '*.jpg;*.jpeg;*.gif;*.png',
        multi        : false,
        auto      : true  ,
        scriptData:options,
        folder          : options.dir, 
        sizeLimit          : options.sizeLimit,
        onComplete : function(event, ID, fileObj, response, data) {
            var mydata = eval('(' + response + ')');
            var img = mydata.img;
            $("[name='" + options.imgNmae + "']").val(img);
            if(img.indexOf("/") != 0){
                img = "/" + img;
            }
            $("#" + options.showImg).attr("src", base + "/common/file.action?method=downFile&folder=" + img).css("display","");
            options.ofile=img;
            $('#' + options.upBtn).uploadifySettings('scriptData',options);
        }
    });
}

function uploadFile(options, onComplete){
	options.method="uploadFile";
    $('#' + options.upBtn).uploadify({
        fileDesc : options.fileDesc == undefined ? '文件(*.*)' : options.fileDesc,
        fileExt : options.fileExt == undefined ? '*.*' : options.fileExt,
        multi        : false,
        auto      : true  ,
        scriptData:options,
        folder          : options.dir, 
        sizeLimit          : options.sizeLimit,
        onComplete : function(event, ID, fileObj, response, data) {
        	var mydata = eval('(' + response + ')');
        	if(onComplete){
        		onComplete(mydata);
        	}else{
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

