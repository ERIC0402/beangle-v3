[#ftl]
[@b.grid items=sessioninfoLogs var="sessioninfoLog"]
	[@b.row]
		[@b.col width="5%" title="序号"]${sessioninfoLog_index+1}[/@]
		[@b.col width="17%" title="sessioninfo.username" property="username"]${sessioninfoLog.username?html}(${sessioninfoLog.fullname?html})[/@]
		[@b.col width="13%" title="sessioninfo.loginAt"  property="loginAt"]${sessioninfoLog.loginAt?string("yy-MM-dd HH:mm:ss")}[/@]
		[@b.col width="13%" title="sessioninfo.logoutAt" property="logoutAt"]${sessioninfoLog.logoutAt?string("yy-MM-dd HH:mm:ss")}[/@]
		[@b.col width="10%" title="sessioninfo.onlineTime" property="onlineTime"]<span title="${sessioninfoLog.remark!}">${(sessioninfoLog.onlineTime/60000)?int}分${(sessioninfoLog.onlineTime/1000)%60}秒</span>[/@]
		[@b.col width="15%" title="sessioninfo.ip" property="ip"/]
		[@b.col width="15%" title="sessioninfo.os" property="os"/]
		[@b.col width="13%" title="sessioninfo.agent" property="agent"/]
	[/@]
[/@]