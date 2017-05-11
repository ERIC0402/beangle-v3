$(function (){
    //保持在线
    setInterval(function (){
	$.ajax({
	    url:'index!online.action'
	});
    }, 600000);
});
var templatei = 1;
function addSubjectTr(btn, template){
    if(btn.value == "删除"){
	return;
    }
//    if(!checkNum(btn, template)){
//	return;
//    }
    
    var tr = $("." + template).val();
    tr = tr.replace(/\{v\}/g, templatei++)
    tr = $.format(tr);
    tr = $(tr(templatei++));
    var table = $(btn).parents("table");
//    $(tr(templatei++)).appendTo(table);
    tr.appendTo(table);
    tr.find("select").chosen()
    var itr = $(btn).parents("tr");
    itr.find("select").addClass("required");
    btn = itr.find(".btn");
    btn.val("删除");
    btn.removeAttr('onclick');
    setTimeout(function (){
	btn.click(function (){
	    delSubjectTr(this);
	});
    }, 100);
}

function delSubjectTr(btn){
    var itr = $(btn).parents("tr");
    itr.remove();
}

function checkNum(btn, template){
    if(template.indexOf("gender") >= 0){
	var num = $(btn).parents("table").find("tr").length;
	if(num >= 7){
	    alert("非学科类特长只能添加6条。");
	    return false;
	}
    }
    return true;
}

function fillTestData(){
    this.setValue = function (name, i, value){
	return _setValue($("[name='"+name+"']"), i, value);
    }
    this.setValues = function (name, i, value){
	return _setValue($("[name^='"+name+"']"), i, value);
    }
    this._setValue = function (e, i, value){
	if($(e.get(0)).is("select")){
	    e.get(0).selectedIndex = true;
	}else if($(e.get(0)).attr("type") == "radio"){
	    e.get(0).checked = true;
	}else{
	    e.each(function (){
		if(value){
		    this.value = value;
		}else{
		    this.value = i++;
		}
	    })
	}
	return i;
    }
    var i = 1;
    i = this.setValue("student.name", i);
    i = this.setValue("student.gender.id", i);
    i = this.setValue("student.birthDay", i, "2011-01-19");
    i = this.setValue("student.schoolArea.id", i);
    i = this.setValue("student.schoolName", i);
    i = this.setValue("student.contactArea.id", i);
    i = this.setValue("student.contactStreet", i);
    i = this.setValue("student.contactZipCode", i);
    i = this._setValue($(".parentRelation"), i);
    i = this._setValue($(".parentName"), i);
    i = this._setValue($(".parentAge"), i);
    i = this._setValue($(".parentJob"), i);
    i = this._setValue($(".parentWorkplace"), i);
    i = this._setValue($(".parentPhone"), i, '11111111111');
    i = this.setValue("student.homePhone", i);
    i = this.setValue("student.residenceArea.id", i);
    i = this.setValue("student.residenceStreet", i);
    i = this._setValue($(".schoolLifeYuwen"), i);
    i = this._setValue($(".schoolLifeShuxue"), i);
    i = this._setValue($(".schoolLifeWaiyu"), i);
    i = this._setValue($(".schoolLifeBanganbu"), i);
    i = this._setValue($(".schoolLifeNotice"), i);
    i = this.setValue("student.schoolHonors", i);
    i = this.setValue("student.contactStreet", i);
    i = this._setValue($(".generalSpecialtyName"), i);
    i = this._setValue($(".generalSpecialtyLevel"), i);
    i = this._setValue($(".generalSpecialtyDate"), i);
    i = this._setValue($(".generalSpecialtyNotice"), i);
    i = this._setValue($(".subjectSpecialtySubjectId"), i);
    i = this._setValue($(".subjectSpecialtyContent"), i);
    i = this._setValue($(".subjectSpecialtyLevel"), i);
    i = this._setValue($(".subjectSpecialtyDate"), i);
    i = this._setValue($(".subjectSpecialtyNotce"), i);
    i = this.setValue("student.notice", i);
}