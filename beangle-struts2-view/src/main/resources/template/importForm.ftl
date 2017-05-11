[#ftl]
[#macro importForm title src="!importData" templateFile="" ext="xls"]
[@b.head/]
[@b.toolbar title=title]bar.addBack();[/@]
[@b.form name="tiMuImportForm" action="${src}" id="tiMuImportForm" theme="list" enctype="multipart/form-data"]
	<tr>
      <td>上传文件:<input type="file" id="importFile" name="importFile" label="文件目录"/>
      [@b.submit value="提交" onsubmit="validateExtendName"/]
      </td>
    </tr>
     <tr>
       <td> 
          <font size="2"> 请选择.xls文件进行上传</font>
          [#if templateFile!=""]
          <a title="请点击下载" href="${base}/common/file!downFile.action?folder=${templateFile}">【${title}模板.xls】</a>
          [/#if]
          <br>上传文件中的所有信息均要采用文本格式。对于日期和数字等信息也是一样。
          <br>导入速度：约200行/分钟
       </td>
     </tr>
[/@]
<script type="text/javascript">
  function validateExtendName(form){
  	var value=form.importFile.value;
  	var index=value.indexOf("${ext}");
	if((index<1) ||(index<(value.length-4))){
		alert("请选择扩展名为.xls的文件");
		return false;
	}
	return true;
	
  }
</script>
[@b.foot/]
[/#macro]
