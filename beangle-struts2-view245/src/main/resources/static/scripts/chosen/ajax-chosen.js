jQuery.fn.extend({
    ajaxChosen : function(options, callback) {
        var select;
        select = this;
        this.chosen();
//      return;
        var __input;
        if (this.attr('multiple')) {
            __input = this.next('.chzn-container').find(".search-field > input");
        } else {
            __input = this.next('.chzn-container').find(".chzn-search > input");
        }
        return __input.bind('keyup', function(evt) {
            var stroke, _ref;
            stroke = (_ref = evt.which) != null ? _ref : evt.keyCode;
            switch (stroke) {
            case 229:
					return true;
				case 8:
					return true;
				case 9:
					return true;
				case 13:
					return true;
				case 38:
					return true;
				case 40:
					return true;
            }
            var field, val;
            val = $.trim($(this).attr('value'));
            if (val.length < 1 || val === $(this).data('prevVal')) {
                return false;
            }
            $(this).data('prevVal', val);
            field = $(this);
            options.data = {
                term : val,
                key : val
            };
            if (options.postData != undefined) {
                var extraData = options.postData();
                $.each(extraData, function(key, value) {
                    options.data[key] = value;
                });
            }
            if (typeof success !== "undefined" && success !== null) {
                success;
            } else {
                success = options.success;
            }
            ;
            options.success = function(data) {
                var items;
                if (!(data != null)) {
                    return;
                }
                select.find('option').each(function() {
                    if (!$(this).is(":selected")) {
                        return $(this).remove();
                    }
                });
                items = callback(data);
                $.each(items, function(value, text) {
                    var exists = false;
                    select.find('option').each(
                            function(i, option) {
                                if (value.indexOf('没有找到') == -1
                                        && option.value == value) {
                                    exists = true;
                                }
                            })

                    if (!exists) {
                        return $("<option />").attr('value', value).html(text)
                                .appendTo(select);
                    }
                });
                select.trigger("liszt:updated");
//				field.attr('value', val);
                field.trigger("keydown");
                if (typeof success !== "undefined" && success !== null) {
                    return success();
                }
            };
			
			options.type = "POST";
            return $.ajax(options);
        });
    }
});