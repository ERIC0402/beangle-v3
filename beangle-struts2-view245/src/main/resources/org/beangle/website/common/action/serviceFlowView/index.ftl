[#ftl]
<style type="text/css">
.table{ width:800px; }
.leftTD{ width:100px; text-align:left; height:30px; padding:5px 5px 5px 5px;}
.rightTD{ border:solid 1px; width:700px; height:30px; padding:10px 0px 10px 10px;}
</style>
[#if sf??]
	[@getServiceFlow serviceFlow=sf/]
[#elseif sfs??]
	[#list sfs as item]
		[@getServiceFlow serviceFlow=item/]
	[/#list]
[/#if]
[#macro getServiceFlow serviceFlow]
	<table class="table">
		<tr align="center">
			<td colspan="2" style="padding:10px 0px 10px 0px;"><h2>${serviceFlow.title!}</h2></td>
		</tr>
		[#list serviceFlow.children as item]
			<tr>
				<td class="leftTD"><B>${item_index+1}.${item.title!}</B></td>
				<td class="rightTD">
					[#list item.children as item2]
							<input type="button" value="${item2.title!}" style="height:30px;width:135px">
						[@getChildren serviceFlow=item2/]
					[/#list]
				</td>
			</tr>
			[#if serviceFlow.children?last.id!=item.id]
				<tr>
					<td></td>
					<td align="center"><font size="18">↓</font></td>
				</tr>
			[/#if]
		[/#list]
	</table>
[/#macro]
[#macro getChildren serviceFlow]
	[#if serviceFlow.children?size>0]
		<span style="padding-top:10px;"> → </span>
	[/#if]
	[#list serviceFlow.children as item]
		<input type="button" value="${item.title!}" style="height:30px;width:135px">
		[@getChildren serviceFlow=item/]
	[/#list]
[/#macro]
[@b.foot/]