[#ftl]
[@b.head/]
[#include "../status.ftl"/]
[@b.grid items=users var="user" boxClass="blue"]
	[@b.gridbar]
		bar.addItem("${b.text("action.new")}",action.add());
		bar.addItem("${b.text("action.modify")}",action.edit());
		bar.addItem("${b.text("action.activate")}",activateUser('true'),'${b.theme.iconurl('actions/activate.png')}','green');
		bar.addItem("${b.text("action.freeze")}",activateUser('false'),'${b.theme.iconurl('actions/freeze.png')}','red');
		bar.addItem("${b.text("action.delete")}",action.remove(),null,'red');
		bar.addItem("${b.text("action.export")}",action.exportData("name:登录名,fullname:姓名,mail:电子邮件,groups:用户组,creator.fullname:创建者,effectiveAt:账户生效时间,invalidAt:账户失效时间,passwordExpiredAt:密码失效时间,createdAt:创建时间,updatedAt:修改时间,enabled:状态",null,"&fileName=用户信息"));
		function activateUser(isActivate){
			return action.multi("activate","确定提交?","isActivate="+isActivate);
		}
	[/@]
	[@b.row]
		[@b.boxcol/]
		[@b.col property="name" width="15%"][#--&nbsp;[@ems.avatar user=user/]--]&nbsp;[@b.a href="!dashboard?user.id=${user.id}" target="_blank"]${user.name}[/@][/@]
		[@b.col property="fullname" width="15%"/]
		[@b.col property="mail" title="common.email" width="20%"/]
		[@b.col property="creator.fullname" sort="user.creator" width="10%"/]
		[@b.col property="defaultCategory.name" title="entity.userCategory" width="15%"][#list user.categories as uc][#if uc!=user.defaultCategory]<em>${uc.name}</em>[#else]${uc.name}[/#if][#if uc_has_next]&nbsp;[/#if][/#list][/@]
		[@b.col property="updatedAt" title="common.updatedAt" width="15%"]${user.updatedAt?string("yyyy-MM-dd")}[/@]
		[@b.col property="enabled" title="common.status" width="10%"][@enableInfo user.enabled/][/@]
	[/@]
[/@]
[@b.foot/]