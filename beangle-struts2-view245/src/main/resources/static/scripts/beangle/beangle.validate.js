
function ValidateStr(){
	this.config  = {};
	this.required = function(){this.config.required = true;return this;}
	this.require = this.required;
	//this.comment = function(str){this.config.l = str;return this;}
	this.match = function(format){this.config["match" + format] = true;return this;}
	this.range = function(min, max){this.config.min = min;this.config.max = max;return this;}
	this.maxl = function(maxlength){this.config.maxlength = maxlength;return this;}
	this.minl = function(minlength){this.config.minlength = minlength;return this;}
	this.select2 = function(select2){this.config.select2 = select2;return this;}
	this.same = function(name){this.config.same = name;return this;}
	this.sfz = function(){this.config.sfz = "1";this.config.l = "身份证号码应为15、18位或者17位+X！";return this;}
	this.fn = function(fn){this.config.fn=fn;return this;}
	this.getConfig = function(){return this.config;}
}
function ValidateForm(form, orules){
    var error = $('.alert-danger', form);
    var success = $('.alert-success', form);
    for(name in orules){
    	var maxlength = orules[name].maxlength || $("[name='" + name + "']").attr("maxlength");
    	if(maxlength){
    		$("[name='" + name + "']").maxlength({
                limitReachedClass: "label label-danger",
                alwaysShow: true
                //placement: App.isRTL() ? 'top-right' : 'top-left'
            });
    	}
    }
    var error1 = $('.alert-danger', form);
    form.validate({
        doNotHideMessage: true, //this option enables to show the error/success messages on tab switch.
        errorElement: 'span', //default input error message container
        errorClass: 'help-block', // default input error message class
        focusInvalid: true, // do not focus the last invalid input
        rules: orules,
        errorPlacement: function (error, element) { // render error placement for each input type
        	error.insertAfter(element);
        },
        invalidHandler: function (event, validator) { //display error alert on form submit   
        	//alert("表单填写有误，请检查后再提交！");
        	error1.find('font').html("表单填写有误，请检查后再提交！");
        	error1.show();
            App.scrollTo(error1, -200);
        },
        highlight: function (element) { // hightlight error inputs
        	$(element).closest('.form-group').removeClass('has-success').addClass('has-error'); 
        },
        unhighlight: function (element) { // revert the change done by hightlight
        	$(element).closest('.form-group').removeClass('has-error').addClass('has-success'); 
        },
        success: function (label) { 
        	error1.hide();
        	label.addClass('valid').closest('.form-group').removeClass('has-error').addClass('has-success');
        }
//        submitHandler: function (form) {
//        	return true;
//        }
    });
}