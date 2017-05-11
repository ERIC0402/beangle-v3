[@b.textfield name="kyxmzx.pzh" label="批准号" value="${kyxmzx.pzh!}" required="true" maxlength="32"/]

[@b.select label="学科大类" name="kyxmzx.xkdl.id" value=(kyxmzx.xkdl.id)!  items=xkdls required="true" option="id,name" empty="请选择..."/]

[@b.radios label="common.status"  name="dictType.enabled" value=dictType.enabled items="1:启用,0:禁用"/]
[@b.radios label="状态" name="kyxmzx.zt.id" value=(kyxmzx.zt.id)! items='${toRadioItem(xmzts)}' required="true" option="id,name"/]

[@b.textarea label="备注" cols="50" rows="3" name="kyxmzx.bz" value=(kyxmzx.bz)! maxlength="1000"/]

 check="match('integer').range(1,100)" 