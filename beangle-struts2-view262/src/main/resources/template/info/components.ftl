[#ftl]
[#macro switch label name value]
<input type="checkbox" onclick="$name('${name}').val(this.checked == true? 1 : 0);" id="switch${name}" [#if value]checked="checked"[/#if]/><label for="switch${name}">${label}</label>
<input type="hidden" name="${name}" value="${value?string}"/>
[/#macro]
