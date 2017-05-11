[#ftl]
[@b.head theme="xml"/]
[#if ((Session['loginFailureCount'])?default(0)>1)][#assign needCaptcha=true][#else][#assign needCaptcha=false][/#if]
<style>
	body{ background:url(${base}/static/login/images/login.jpg) no-repeat 50% top #fbfbfb; font-family:Arial, Helvetica, sans-serif; font-size:12px; margin:0px;}
	.login_box{ width:260px; margin:0px auto; padding:0px 0px 0px 396px; color:#5c5c5c;}
	.td_1{ font-weight:bold; height:30px; line-height:30px;}
	.ip_1{ width:185px; height:18px; border:1px #cbcbcb solid }
	.ip_11{ width:100px; height:18px; border:1px #cbcbcb solid }
	.ip_2{background:url( ${base}/static/login/images/login_03.gif) no-repeat; width:82px; height:32px; line-height:32px; color:#2c180b; border:none; cursor:pointer}
	.blank_line_1{ height:290px;}
	.blank_line_1 .logo_box{ width:720px; margin:0px auto; padding:150px 200px 0px 0px;}
</style>
<script type="text/javascript" src="${base}/static/scripts/beangle/security.js"></script>
<script type="text/javascript">
    var href = top.location.href;
    if(href.indexOf("login.action") < 0){
	top.location = top.location;
    }
</script>
<body>
<div class="blank_line_1">
<div class="logo_box" align="center"><img src="${base}/static/login/images/login_03.jpg" /></div></div>
<div class="login_box">
[@b.form name="loginForm"  id="login_text" action="login" target="_top"]
	<table>
	<tr><td class="td_1" colspan="3">[@b.messages slash="2"/]</td></tr>
    <tr>
    	<td class="td_1"><label for="username">用户名</label>：</td>
	    <td>
	    	<input name="username" id="username" tabindex="1" title="请输入用户名" type="text" class="ip_1" value="${name!}"/>
	    </td>
	</tr>
    <tr>
	    <td class="td_1"><label for="password">密&nbsp;&nbsp;&nbsp;&nbsp;码</label>：</td>
	    <td>
	    	<input id="password" tabindex="2" type="password" class="ip_1" />
			<input name="encodedPassword" id="encodedPassword" type="hidden" value=""/>
	    </td>
    </tr>
    <tr>
	    <td class="td_1"><label for="password"></td>
	    <td>
	    	<input id="keepLogin" name="keepLogin" type="checkbox" value="1"/>
	    	<label for="keepLogin">2周内自动登录</label>
	    </td>
    </tr>
    [#if needCaptcha]
    <tr>
	    <td class="td_1"><label for="captcha">验证码</label>：</td>
	    <td>
	    	<input id="captcha" name="captcha"  tabindex="3" type="text" class="ip_11"/>
			<img src="${b.url('/security/captcha')}" title="验证码,点击更换一张" onclick="changeCaptcha(this)" alt="验证码" width="50" height="22" style="vertical-align:top;"/>
	    </td>
    </tr>
    [/#if]
    <tr><td height="70" colspan="2" align="center">
    	<input type="hidden" name="backurl" value="${(backurl?html)!}" />
    	[@b.submit name="submitBtn" value="登 录" onsubmit="checkLogin" class="ip_2" ][/@]
    	&nbsp;&nbsp;&nbsp;&nbsp;
    	<input type="reset" class="ip_2" value="重 置" / >
    </td></tr>
    </table>
[/@b.form]
</div>
</body>
<script type="text/javascript">
	var form  = document.loginForm;
	function setf(){
        form['username'].focus();
    }
    setf();
	
	function checkLogin(form){
		if(!form['username'].value){
			alert("用户名称不能为空");
			form['username'].focus();
			return false;
		}
		if(!form['password'].value){
			alert("密码不能为空");
			form['password'].focus();
			return false;
		}
		//[#if needCaptcha]
		if(!form['captcha'].value){
			alert("验证码不能为空");return false;
		}
		//[/#if]
		var modulus = "${modulus!}", exponent = "${exponent!}";  
		var key = RSAUtils.getKeyPair(exponent, '', modulus);  
		var pwd = RSAUtils.encryptedString(key, $("#password").val()); 
		$("#encodedPassword").val(pwd);
		return true;
	}
	if("${locale.language}".indexOf("en")!=-1){document.getElementById('local_en').checked=true;}
	var username=beangle.cookie.get("username");
	if(null!=username){ form['username'].value=username;}
	function changeCaptcha(obj) {
		//获取当前的时间作为参数，无具体意义
		var timenow = new Date().getTime();
		//每次请求需要一个不同的参数，否则可能会返回同样的验证码
		//这和浏览器的缓存机制有关系，也可以把页面设置为不缓存，这样就不用这个参数了。
		obj.src="${b.url('/security/captcha')}?d="+timenow;
	}
</script>
[@b.foot/]