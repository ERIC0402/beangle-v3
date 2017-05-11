[#ftl]
[#--
<form id="${tag.id}" name="${tag.name}" action="${tag.action}" method="post"[#if tag.target??] target="${tag.target}"[/#if]>
<table class="search-widget">
[#if tag.title??]
<tr><td><img src="${b.theme.iconurl("actions/info.png")}" alt="info" class="toolbar-icon"/><em>${tag.title}</em></td></tr>
<tr><td style="font-size:0px"><img src="${b.theme.iconurl("actions/keyline.png")}" height="2" width="100%" alt="keyline"/></td></tr>
[/#if]
${tag.body}[#if !tag.body?contains('submit')]<tr><td align="center"><input type="reset" value="${b.text('action.reset')}"/>&nbsp;&nbsp;<input type="submit" value="查询" onclick="bg.form.submit('${tag.id}');return false;"/></td></tr>[/#if]
</table>
</form>
--]
<div class="BlankLine1"></div>
<form id="${tag.id}" name="${tag.name}" action="${tag.action}" method="post"[#if tag.target??] target="${tag.target}"[/#if]>
	<div class="search_box">
		<div class="search_items">
			[#if !tag.body?contains('search_input')]
				<div style="float: right; width: 9%; padding: 2px 0 2px 1%;">
					<div>
						<div class="search_btn">
							<input type="submit" class="ip_4 search_input" value="搜 索" onclick="bg.form.submit('${tag.id}');return false;" style="width: 42px;">
						</div>
						<a href="#" class="expand_search_btn"></a>
					</div>
				</div>
			[/#if]
			<div style="float: left; width: 90%;">
				${tag.body}
			</div>
			<div class="ClearDiv"></div>
		</div>
	</div>
</form>
<script type="text/javascript">bg.ui.search.initForm("${tag.id}");</script>