[#ftl]
[#assign scopes={'1':'','0':''}/]
[#assign scopeNames={'1':'public','0':'private'}/]
[#macro resourceScope scope]
	<img style="padding-bottom:5px;vertical-align:middle;" src="${b.theme.iconurl('status/'+ scopeNames[scope?string] + '.png')}"/>${scopes[scope?string]}
[/#macro]

[#macro downLoadFile filePath fileName]
	<a href="${base}/common/file.action?method=downFile&folder=${filePath!}&fileName=${fileName?url('utf-8')}" target="_blank">
		<img style="padding-bottom:5px;vertical-align:middle;" title="点击下载" src="${b.theme.iconurl('actions/download.png')}"/>
	</a>
[/#macro]

[#macro enableInfo enabled]
[#if enabled]<img height="15" width="15" src="${b.theme.iconurl('actions/activate.png')}" alt="activate"/>${b.text("action.activate")}[#else]<img height="15" width="15" src="${b.theme.iconurl('actions/freeze.png')}" alt="freezen"/><em>${b.text("action.freeze")}</em>[/#if]
[/#macro]

[#macro shortEnableInfo enabled stateTrue stateFalure]
[#if enabled]<img height="15" width="15" src="${b.theme.iconurl('actions/activate.png')}" alt="activate" title="${stateTrue!}"/>[#else]<img height="15" width="15" src="${b.theme.iconurl('actions/freeze.png')}" alt="freezen" title="${stateFalure!}"/>[/#if]
[/#macro]
