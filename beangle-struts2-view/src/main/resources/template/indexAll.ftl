[@b.textfields names="kyxmzx.pzh;批准号,kyxmzx.mc;项目名称,kyxmzx.fzr;负责人"/]

[@b.select name="kyxmzx.szdw.id" label="所在单位" value="" empty="..." items=szdws option="id,name"/]
[@b.select name="menu.enabled" items=profiles label="common.status" items={'true':'${b.text("action.activate")}','false':'${b.text("action.freeze")}'}  empty="..."/]

[@b.datepicker label="开始完成时间" name="kyxmzx.kswcsj" format="date"/]
[@b.datepicker label="结束完成时间" name="kyxmzx.jswcsj" format="date"/]