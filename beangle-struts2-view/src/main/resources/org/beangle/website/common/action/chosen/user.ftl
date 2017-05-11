[#ftl]
{
		[#list users! as u]
			[#if u_index!=0],[/#if]
			'${u.id!}' : '${u.fullname?js_string}[#if u.name??](${(u.name?js_string)!})[/#if][#if u.department??](${(u.department.name)!})[/#if][#if u.adminClass??](${(u.adminClass.name)!})[/#if]'
		[/#list]
}