[#ftl]
[@b.head/]
[#--
<h3>Server Error! You know what I mean.</h3>
<div>Create or change [/template/error.ftl] in your webapp directory.Using[#noparse]${(stack.pop().exceptionStack)!}[/#noparse] to display exception stack.</div>
<hr>
<div>
<pre>
${(stack.pop().exceptionStack)!}
</pre>
</div>
--]
<link href="${base}/static/metronic1.5.4/assets/css/pages/error.css" rel="stylesheet" type="text/css"/>
<div class="row">
	<div class="col-md-12 page-404">
		<div class="number">
			404
		</div>
		<div class="details">
			<h3>Oops!  You're lost.</h3>
			<p>
				We can not find the page you're looking for.<br />
				<a class="accordion-toggle collapsed" href="#collapse_2" data-toggle="collapse" data-parent="#accordion1">
						如果您是专家，请点击
						</a>
			</p>
			
			<form action="#">
				<div class="input-group input-medium">
					<input type="text" class="form-control" placeholder="keyword...">
					<span class="input-group-btn">                   
					<button type="submit" class="btn blue"><i class="fa fa-search"></i></button>
					</span>
				</div>
			</form>
		</div>
		<div class="panel-collapse collapse" id="collapse_2" style="height: 0px;">
			<div class="panel-body" style="text-align:left;">
				${(stack.pop().exceptionStack)!}
			</div>
		</div>
	</div>
</div>
[@b.foot/]