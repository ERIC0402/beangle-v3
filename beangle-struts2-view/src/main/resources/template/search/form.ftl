[#ftl]
[#--
<div class="BlankLine1"></div>
<form id="${tag.id}" name="${tag.name}" action="${tag.action}" method="post"[#if tag.target??] target="${tag.target}"[/#if]>
	<div class="search_box">
		<div class="search_items">
			[#if !tag.body?contains('search_input')]
				<div style="float: right; width: 9%; padding: 2px 0 2px 1%;">
					<div>
						<div class="search_btn">
							<input type="submit" class="ip_4 search_input" value="搜 索" onclick="bg.form.submit('${tag.id}');return false;" style="width: 42px;">
						</div>
						<a href="#" class="expand_search_btn"></a>
					</div>
				</div>
			[/#if]
			<div style="float: left; width: 90%;">
				${tag.body}
			</div>
			<div class="ClearDiv"></div>
		</div>
	</div>
</form>
<script type="text/javascript">bg.ui.search.initForm("${tag.id}");</script>
--]
<link href="${base}/static/metronic1.5.4/assets/css/pages/search.css" rel="stylesheet" type="text/css"/>
<style>
.search_items{
	padding:0px 5px;
}
.search_items input, select, textarea{
	margin-top:5px;
}
.search-form-default {
  margin-bottom:5px;
  /*
  background:#f0f6fa;
  */
  padding:0px 14px;
}
.search_items .search_item{float:right; margin-right: 5px; height: 42px; padding: 2px 0;margin-bottom:7px;}
.search_items .search_item input, .search_items .item select{width:100%;}
.search_items .search_item select{border-color: #ccc;}
.search_items .search_item2{width: 38%; margin-left: 2%; }
.expand_search_btn{ float:right; background:url(${base}/static/themes/default/expand_ico_2.gif) no-repeat left center; width:10px; height:25px;margin-left:5px;}
.closed_search_btn{float:right; background:url(${base}/static/themes/default/closed_ico_2.gif) no-repeat left center; width:10px; height:25px;margin-left:5px;}

#search_div span{padding-top:5px;}
</style>

<div class="row search-form-default">
	<div class="col-md-12" style="background:#f0f6fa;padding:2px 0px 0px 0px;">
		<form class="form-inline" id="${tag.id}" name="${tag.name}" action="${tag.action}" method="post"[#if tag.target??] target="${tag.target}"[/#if]>
			<div class="search_items" style="overflow: hidden;">
				[#if !tag.body?contains('search_input')]
					<span class="" style="float:right;padding-top:7px;">
						<button class="btn green searchButton" type="submit" onclick="bg.form.submit('${tag.id}');return false;">
						搜索 &nbsp; <i class="m-icon-swapright m-icon-white"></i>
						</button>
						[#--
						<a href="#" class="collapse"></a>
						--]
					</span>
				[/#if]
				<div id="search_div" style="float: left; width: 86%;">
					${tag.body}
				</div>
			</div>
			<div style="clear:both"></div>
		</form>
	</div>
</div>
<script type="text/javascript">
	setTimeout(function(){bg.ui.search.initForm("${tag.id}");}, 100);
	
	jQuery(document).ready(function() {       
	   if (jQuery().select2) {
	        $('.select2me').select2({
	            placeholder: "Select",
	            allowClear: true
	        });
	    }
	});
	
</script>