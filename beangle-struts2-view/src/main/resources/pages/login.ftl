[#ftl]
[#if (request.getHeader('x-requested-with')??) || Parameters['x-requested-with']??]
	<script type="text/javascript">
		top.location = top.location;
	</script>
[#else]
[@b.head theme="xml" bodyClass="login" isLogin=1/]
[#if ((Session['loginFailureCount'])?default(0)>1)][#assign needCaptcha=true][#else][#assign needCaptcha=false][/#if]
<link href="${base}/static/metronic1.5.4/assets/css/pages/login-soft.css" rel="stylesheet" type="text/css"/>
<script src="${base}/static/metronic1.5.4/assets/scripts/login-soft.js" type="text/javascript"></script>
<script src="${base}/static/metronic1.5.4/assets/plugins/backstretch/jquery.backstretch.min.js" type="text/javascript"></script>
<script src="${base}/static/metronic1.5.4/assets/plugins/select2/select2.min.js" type="text/javascript"></script>     
<script type="text/javascript" src="${base}/static/scripts/beangle/security.js"></script>
	<!-- BEGIN LOGO -->
	<div class="logo">
		[#--<img src="${base}/static/login/images/login.png" alt="" />--] 
	</div>
	<!-- END LOGO -->
	<!-- BEGIN LOGIN -->
	<div class="content">
		<!-- BEGIN LOGIN FORM -->
		<form name="loginForm" id="login_text" class="login-form" action="${base}/login.action" method="post" target="_top">
			<h3 class="form-title">欢迎登录  [#if (sc.title)??]${sc.title}[/#if]</h3>
			[#assign msg=""]
			[#list actionMessages as item]
				[#assign msg=msg+" "+item]
			[/#list]
			[#list actionErrors as item]
				[#assign msg=msg+" "+item]
			[/#list]
			<div class="alert alert-danger [#if msg==""]display-hide[/#if]">
				<button class="close" data-close="alert"></button>
				<span>[#if msg==""]请输入用户名和密码[#else]${msg!}[/#if]</span>
			</div>
			<div class="form-group">
				<!--ie8, ie9 does not support html5 placeholder, so we just show field title for that-->
				<label class="control-label visible-ie8 visible-ie9">用户名</label>
				<div class="input-icon">
					<i class="fa fa-user"></i>
					<input class="form-control placeholder-no-fix" style="width:100%;" type="text" autocomplete="off" placeholder="用户名" id="username" name="username"/>
				</div>
			</div>
			<div class="form-group">
				<label class="control-label visible-ie8 visible-ie9">密码</label>
				<div class="input-icon">
					<i class="fa fa-lock"></i>
					<input class="form-control placeholder-no-fix" style="width:100%;" type="password" autocomplete="off" placeholder="密码" id="password" name="password"/>
					<input name="encodedPassword" id="encodedPassword" type="hidden" value=""/>
				</div>
			</div>
			[#if needCaptcha]
				<div class="form-group">
					<label class="control-label visible-ie8 visible-ie9">验证码</label>
					<div class="input-icon">
						<i class="fa fa-lock"></i>
						<input class="form-control placeholder-no-fix" style="width:50%;display:inline;" type="text" autocomplete="off" placeholder="验证码" id="captcha" name="captcha"/>
						<img src="${b.url('/security/captcha')}" title="验证码,点击更换一张" onclick="changeCaptcha(this)" alt="验证码" width="80" height="34" style="vertical-align:top;"/>
					</div>
				</div>
			[/#if]
			<div class="form-actions">
				<label class="checkbox">
				<input type="checkbox" name="keepLogin" value="1"/> 记住我
				</label>
				<button type="submit" class="btn blue pull-right">
				登录 <i class="m-icon-swapright m-icon-white"></i>
				</button>
			</div>
			<div class="forget-password">
				[#--
				<h4>忘记密码 ？</h4>
				<p>
					请点击 <a href="#">这里</a>
					重置密码。
				</p>
				--]
			</div>
			<div class="create-account">
				<p><a href="http://www.yanzhisoft.com/" target="_bank" style="color:#E6E6E6;">彦致信息</a></p>
			</div>
		</form>
		<!-- END LOGIN FORM -->        
	</div>
	<!-- END LOGIN -->
	<!-- BEGIN COPYRIGHT -->
	<div class="copyright">
		
	</div>
	<!-- END COPYRIGHT -->
	<script>
		jQuery(document).ready(function() {     
		  App.init();
		  var images = [
		        base+"/static/metronic1.5.4/assets/img/bg/1.jpg",
		        base+"/static/metronic1.5.4/assets/img/bg/2.jpg",
		        base+"/static/metronic1.5.4/assets/img/bg/3.jpg",
		        base+"/static/metronic1.5.4/assets/img/bg/4.jpg"
		        ];
		  Login.init(images);
		});
		
		var form  = document.loginForm;
		function setf(){
	        form['username'].focus();
	    }
	    setf();
	
		var modulus = "${modulus!}", exponent = "${exponent!}";  
		var key = RSAUtils.getKeyPair(exponent, '', modulus);  
		
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
[@b.foot nofooter=1/]
[/#if]