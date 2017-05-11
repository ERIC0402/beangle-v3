[#ftl]
[@b.head]
[/@]
[@b.toolbar title="导出"]bar.addBack();[/@]
[@b.tabs]
	[@b.tab label="请选择要导出的字段"]
		[@b.form action="!save" theme="list"]
			<fieldset>
				<legend>字段</legend>
				<ol>
					<table>
						[#assign count=0]
						[#list keys as item]
							[#if item_index%4==0]
								<tr>
							[/#if]
								<td style="width:150px;">
									<input type="checkbox" id="mc${item_index}" name="mc" value="${item_index}">
									<label for="mc${item_index}">${titles[item]}</label>
									<input type="hidden" name="key${item_index}" id="key${item_index}" value="${titles[item]}">
									<input type="hidden" name="val${item_index}" id="val${item_index}" value="${item}">
								</td>
							[#assign count=count+1]
							[#if count%4==0]
								</tr>
							[/#if]
						[/#list]
					</table>
				</ol>
			</fieldset>
			[@b.formfoot]
				[@b.redirectParams/]
				<input value="重置" id="reset" type="reset">&nbsp;&nbsp;
				<input type="button" id="button" value="导出" onClick="exportStr(this);"/>
			[/@]
		[/@]
	[/@]
[/@]
<script language="JavaScript">
function exportStr(button){
	var rads=document.getElementsByName("mc");
    var exportStr="";
    
	for(var i=0;i<rads.length;i++){
		if(rads[i].checked==true){
			var wz = rads[i].value;
			var val = document.getElementById("val"+wz).value
			if(exportStr == ""){
				exportStr += val;
			}else{
				exportStr += "," + val;
			}
			exportStr += ":" + document.getElementById("key"+wz).value;
		}
	}
	if(exportStr == ""){
    	alert("请选择您要导出的字段！");
    	return false;
    }
	action.exportData(exportStr,null,"&fileName=企业信息").func();
	$("#reset").attr("disabled",false);
	$("#button").attr("disabled",false);
}
</script>
[@b.foot/]
