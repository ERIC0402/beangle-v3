[#ftl]
[@b.head/]
[#include "nav.ftl"/]
[@b.form action="!search?paramId=${paramId!}&isSingle=${isSingle!}" name="selectSearchForm" id="selectSearchForm"]
[@b.grid  items=users var="user" filterable="true"]
	[@b.gridbar]
		bar.addItem("确认选择", "setName(true)");
	[/@]
	[@b.row]
		[#if isSingle??&&isSingle=='1']
			[@b.boxcol type="radio"/]
		[#else]
			[@b.boxcol /]
		[/#if]
		[@b.col width="50%" property="name" title="登陆名" /]
		[@b.col width="50%" property="fullname" title="姓名" /]
	[/@]
[/@]
[/@]

<script language="JavaScript">
 	var users = new Array();
    [#list users as user]
     	users[${user_index}]={'id':'${user.id}','fullname':'${user.fullname}'};
    [/#list]
	
	
		
    function setName(flag){
        var userIds = document.getElementsByName("user.id");
        var userId="";
        var userName = "";
        for(var i=0;i<userIds.length;i++){
            if(userIds[i].checked){
            	parent.$("#${paramId!}").append("<option selected='selected' value='"+userIds[i].value+"'>"+users[i].fullname+"</option>");
            }
        }
       	parent.setResourse();
    }
  </script>



[@b.foot/]