$('#FashionStatement').val('');
var limitNum = 1000;
var pattern = '还可以输入' + limitNum + '字';
$('#statementRowChk').html(pattern);
$('#FashionStatement').keyup(function() {
	var remain = $(this).val().length;
	if (remain > 1000) {
		pattern = $('字数超过限制，请适当删除部分内容');
	} else {
		var result = limitNum - remain;
		pattern = '还可以输入' + result + '字';
	}
	$('#statementRowChk').html(pattern);
});

function checktext(text) {
	allValid = true;
	for (i = 0; i < text.length; i++) {
		if (text.charAt(i) != " ") {
			allValid = false;
			break;
		}
	}
	return allValid;
}

function gbcount(id, total, used, remain) {
	var max;
	max = total.value;
	var message = $("#"+id);
	if (message.val().length > max) {
		message.val() = message.val().substring(0, max);
		used.value = max;
		remain.value = 0;
		alert("留言不能超过 "+max+" 个字!");
	} else {
		used.value = message.val().length;
		remain.value = max - used.value;
	}
}