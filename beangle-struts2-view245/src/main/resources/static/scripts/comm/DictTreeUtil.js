//data:{id:"2", name:"name", pid:"1"}
function DictTreeUtil (){
	this.all_data = null;
	this.data = null;
	this.select = null;
	this.init = function (data, select){
		this.allData = data;
		this.select = select;
	}
	
	this.addOptoinByPid = function(pid){
		var parent = {};
		parent.id = pid;
		var childs = this.getChilds(pid);
		this.data = childs;
		this.addOptoin(parent, "", -1);
	}
	
	this.getChilds = function (pid, array){
		if(array == undefined){
			array = new Array();
		}
		for(var i in this.allData){
			var c = this.allData[i];
			if(c.pid == pid){
				array.push(c);
				this.getChilds(c.id, array);
			}
		}
		return array;
	}
	
	this.addOptoin = function (parent, perfix, index){
		if(index == undefined){
			index = -1;
		}
		if(perfix == undefined){
			perfix = "";
		}
		for(var i = index+1; i < this.data.length; i++){
			var c = this.data[i];
			if(c.pid == parent.id){
				var hasSub = this.hasSub(c, i);
				var hasNextLevel = this.hasNextLevel(c, i);
				var pperfix = perfix;
				var subPerfix = perfix;
				if(hasNextLevel){
					pperfix += '├';
					subPerfix += '│';
				}else{
					pperfix += '└';
					subPerfix += '　';
				}
				var option = "<option value='" + c.id + "'>"+pperfix+c.name+"</option>";
				this.select.append($(option));
				if(hasSub){
					this.addOptoin(c, subPerfix, i);
				}
			}
		}
	}
	
	this.hasSub = function (c, index){
		for(var i = index; i < this.data.length; i++){
			var cc = this.data[i];
			if(cc.pid == c.id){
				return true;
			}
		}
		return false;
	}
	
	this.hasNextLevel = function (c, index){
		for(var i = index+1; i < this.data.length; i++){
			var cc = this.data[i];
			if(cc.pid == c.pid){
				return true;
			}
		}
		return false;
	}
	
	this.addDefault = function (text){
		this.select.prepend(new Option(text,""));
	}
	
	this.tiKuChanged = function (tk){
		if(tk != undefined){
			tk = $(tk);
		}else{
			tk = $("#tkId");
		}
		var tmflid = tk.find("option:selected").attr("tmflid");
		var tmflSelect = this.select;
		tmflSelect.find("option").remove();
		if(tmflid != null){
			this.addOptoinByPid(tmflid);
		}
		this.addDefault("请选择...");
	}
}