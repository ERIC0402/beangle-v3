[#ftl]
[#import "comm.ftl" as c/]
[@c.tr tag=tag]
<input id="${tag.id}" name="captcha" title="[#if tag.comment??]${tag.comment}[#else]${tag.title}[/#if]" class="captcha"  type="text" class="ip_11" maxlength="6"/>
<img src="${b.url('/security/captcha')}" class="captchaImg" title="验证码,点击更换一张" onclick='this.src="${b.url("/security/captcha")}?d="+new Date().getTime();' alt="验证码" width="80" height="30" style="vertical-align:top;"/>
[/@]