[#ftl]
[@b.head/]
[#include "nav.ftl"/]
	[@b.toolbar title="投票管理"]bar.addItem("确认选择", "setName(true)");[/@]
		<table  align="center"  width="100%">
			[#list groups?sort_by("name") as group]
				<tr>
					<td colspan="4">
						<input style="cursor:pointer" id="checkAll_${group.id}" type="checkbox" value="${group.id}"  onclick="selectedAll(this)">
						<label style="cursor:pointer" name="groupMember.group.name" for="checkAll_${group.id}"><font color="red">${group.name}组【点击选中该组下所有教职工】</font></label>
					</td>
			    </tr>
			    <tr>
			    [#assign count=0]
				[#list users as user]
					[#list user.groups as gm]
						[#if gm.group.id=group.id]
							[#if count% 4 == 0]
							</tr><tr>
							[/#if]
							<td width="20%">
								&nbsp;&nbsp;&nbsp;&nbsp;
								<input style="cursor:pointer" type="checkbox"  name="user.id" id="user.id_${user.id}_${group.id}" value="${user.id}" username="${user.fullname}" />
								<label style="cursor:pointer" for="user.id_${user.id}_${group.id}" name="user.name"> ${user.fullname} </label>
							</td>
							[#assign count=count+1]
						[/#if]
					[/#list]
				[/#list]
				</tr>
				[#-- 		
				[#if groupMember_has_next]
				   <hr border="1" height="0" style="border-collapse:collapse;">
				[/#if]
			    --]		
			[/#list]
			
		</table>

<script language="JavaScript">
 	var users = new Array();
    [#list users as user]
     	users[${user_index}]={'id':'${user.id}','name':'${user.name}'};
    [/#list]
	
    function setName(flag){
        var userIds = document.getElementsByName("user.id");
        var userId="";
        var userName = "";
        for(var i=0;i<userIds.length;i++){
            if(userIds[i].checked){
            	parent.$("#${paramId}").append("<option selected='selected' value='"+userIds[i].value+"'>"+users[i].name+"</option>");
            }
        }
       	parent.setResourse();
    }
    
    function selectedAll(groupIds)
    {
    	var che_id=document.getElementsByName("user.id");
    	for(var i=0;i<che_id.length;i++){
    		var user_id=che_id[i].id.split("_")[2];
    	   if(user_id==groupIds.value){
    		che_id[i].checked=groupIds.checked;
    		}
    	}
    }
    
   function  selectaaa(userIdd)
   {
   		var che_id=document.getElementById(userIdd);
   		if(che_id.checked)
   		{
   			che_id.checked="";
   		}else
   		{
   			che_id.checked="true";
   		}
   		
   }
  [#--
   function  selectbbb(userIdd,groupIds)
   {
   	   var che_id=document.getElementsByName("user.id");
    	var che_id2=document.getElementById(userIdd);
   		if(che_id2.checked)
   		{
   			che_id2.checked="";
   		}else
   		{
   			che_id2.checked="true";
	    	for(var i=0;i<che_id.length;i++){
	    		var user_id=che_id[i].id.split("_")[2];
	    	   if(user_id==groupIds.value){
	    		che_id[i].checked=che_id2.checked;
	    		}
	    	}
    	}
   
   }
 
   --]
   
  </script>



[@b.foot/]



