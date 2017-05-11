[#ftl]
[@b.head/]
[#include "../nav.ftl"/]
[@b.tabs]
	[@b.tab label="系统参数基本信息"]
		[@b.form action="!save" title="系统参数" theme="list"]
			[#--
			[@b.textfield name="systemArgument.yjfwqym" label="邮件服务器域名" value="${systemArgument.yjfwqym!}" style="width:300px;" required="true" maxlength="64" comment="最多输入64个字"/]
			[@b.textfield name="systemArgument.yjfwqyhm" label="登录邮件服务器用户名" value="${systemArgument.yjfwqyhm!}" style="width:300px;" required="true" maxlength="64" comment="最多输入64个字"/]
			[@b.textfield name="systemArgument.yjfwqmm" label="登录邮件服务器密码" value="${systemArgument.yjfwqmm!}" style="width:300px;" required="true" maxlength="64" comment="最多输入64个字"/]
			[@b.email label="邮件发送人Email" name="systemArgument.yjfsr" value="${systemArgument.yjfsr!}" style="width:300px;" required="true" maxlength="64" comment="最多输入64个字"/]
			--]
			[@b.textfield name="systemArgument.xiaoNeiIp" label="校内IP段" value="${systemArgument.xiaoNeiIp!}" style="width:300px;" required="true" maxlength="600" /]
			[@b.formfoot]
				<input type="hidden" name="systemArgument.id" value="${systemArgument.id!}" />
				[@b.redirectParams/]
				[@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
			[/@]
		[/@]
	[/@]
[/@]
[@b.foot/]