[#ftl]
[#if !(request.getHeader('x-requested-with')??) && !Parameters['x-requested-with']??]
[#--<p align="right">
	<a href="http://validator.w3.org/check?uri=referer"><img
		src="http://www.w3.org/Icons/valid-xhtml11"
		alt="Valid XHTML 1.0 Strict" height="15" width="44" /></a>

	<a href="http://jigsaw.w3.org/css-validator/">
	<img  src="http://www.w3.org/Icons/valid-css2"
		alt="Valid CSS 2.0" height="15" width="44" /></a>
</p>
--]
[#if !tag.parameters['nofooter']??]
<!-- BEGIN FOOTER -->
	<div class="footer">
		<div class="footer-inner">
			2014 &copy; YZSOFT.com
		</div>
		<div class="footer-tools">
			<span class="go-top">
			<i class="fa fa-angle-up"></i>
			</span>
		</div>
	</div>
	<!-- END FOOTER -->
[/#if]
[#if !tag.parameters['hiddenBody']??]
</body>
[/#if]
</html>
[/#if]