<#import "/template/message.ftl" as msg>
<#if Session['WW_TRANS_I18N_LOCALE']??>
<#assign language= Session['WW_TRANS_I18N_LOCALE'].language>
<#else>
<#assign language="zh">
</#if>
<#macro i18nName(entity)><#if language?index_of("en")!=-1><#if entity.engName!?trim=="">${entity.name!}<#else>${entity.engName!}</#if><#else><#if entity.name!?trim!="">${entity.name!}<#else>${entity.engName!}</#if></#if></#macro>

<#macro i18nSelect datas selected  extra...>
<select <#list extra?keys as attr>${attr}="${extra[attr]?html}" </#list>>
    <#nested>
    <#list datas as data>
    <option value="${data.id}" <#if data.id?string=selected>selected</#if>><@i18nName data/></option>
    </#list>
</select>
</#macro>

<#macro radio2 name value>
<input type="radio" value="1" name="${name}" <#if value>checked</#if> ><@msg.text name="common.yes" />
       <input type="radio" value="0" name="${name}" <#if (!value)>checked</#if> ><@msg.text name="common.no" />
       </#macro>

       <#macro select2 name selected hasAll extra...>
       <select name="${name}" <#list extra?keys as attr>${attr}="${extra[attr]?html}" </#list>>
       <#if hasAll><option value="" <#if selected=''>selected</#if>><@msg.text name="common.all" /></option></#if>
    <option value="1" <#if selected='1'>selected</#if>><@msg.text name="common.yes" /></option>
    <option value="0" <#if selected='0'>selected</#if>><@msg.text name="common.no" /></option>
</select>
</#macro>


<!--name action-->
<#macro actionForm entity name action extra...>
<form action=""  name="${name}" method="post">
    <#nested>
</form>
<script type="text/javascript">
    var form = document['${name}'];
    var action = "${action}";
    var queryForm = document['queryForm'];
    var queryStr;
    var resultQueryStr = queryStr = getInputParams(queryForm,null,false);

    function beforSubmmit(method) {
        var ids = getSelectIds("${entity}Id");
        if (ids == null || ids == "") {
            alert("<@msg.text name='common.select'/>");
            return false;
        }

        form.action = "${action}?method=" + method;
        addHiddens(form,resultQueryStr);
        addParamsInput(form,resultQueryStr);
        return true;
    }
    function edit(id) {
        if(id != undefined){
            checkOneBox(id);
        }
        if (beforSubmmit("edit")) {
            submitId(form, '${entity}Id', false);
        }
    }

    function checkOneBox(id){
        $("[name='${entity}Id']").each(function(){
            $(this).attr("checked",false);
        });
        $("[name='${entity}Id'][value='"+id+"']").attr("checked",true);
    }

    function info(giveId) {
        if(giveId==null){
            if (beforSubmmit("info")) {
                submitId(form, '${entity}Id', false);
            }
        }else{
            form.action = "${action}?method=info";
            addInput(form,"${entity}Id",giveId, "hidden");
            form.submit();
        }
    }
    function remove(id){
        if(id != undefined){
            checkOneBox(id);
        }
        if (beforSubmmit("remove")) {
            submitId(form,"${entity}Id",true,null,"<@msg.text name='common.confirmAction'/>");
        }
    }
    function add(){
        form.action = "${action}?method=edit";
        addInput(form,'${entity}Id',"");
        addHiddens(form,resultQueryStr);
        addParamsInput(form,resultQueryStr);
        form.submit();
    }
    function multiAction(method,confirmMsg){
        submitAction(method,true,confirmMsg);
    }
    function singleAction(method,confirmMsg){
        submitAction(method,false,confirmMsg);
    }
    function singleAction(method,tar,confirmMsg){
        if(tar == undefined){
            tar = "";
        }
        if(confirmMsg == undefined){
            confirmMsg = null;
        }
        submitAction(method,false,confirmMsg);
        form.target = tar;
    }
    function submitAction(method,multiId,confirmMsg){
        if (beforSubmmit(method)) {
            if(null!=confirmMsg){
                if(!confirm(confirmMsg))return;
            }
            submitId(form,"${entity}Id",multiId);
        }
    }
    function exportList(){
        form.action="${action}?method=export";
        addHiddens(form,resultQueryStr);
        form.submit();
    }
    function submitActionForm(method, id, confirmMsg){
        addParamsInput(form,resultQueryStr);
        if(method != undefined){
            form.action="${action}?method=" + method;
        }
        if(id != undefined && id != null){
            addInput(form,'${entity}.id',id);
        }
        if(undefined!=confirmMsg){
            if(!confirm(confirmMsg))return;
        }
        form.submit();
    }
    var multiIdAction=multiAction;
    
    function back(){
        history.go(-1);
    }
</script>
</#macro>
