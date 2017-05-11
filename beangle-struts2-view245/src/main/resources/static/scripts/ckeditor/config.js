/**
 * @license Copyright (c) 2003-2013, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.html or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr'; 
	// config.uiColor = '#AADC6E';
	//是否强制复制来的内容取出格式 plugins/pastetext/plugin.js
	//config.forcePasteAsPlainText = false;//不去除
	config.pasteFromWordRemoveFontStyles = false;
	config.pasteFromWordNumberedHeadingToList = true;
	config.pasteFromWordRemoveStyles = false;
	config.allowedContent = true;
	config.language = 'zh-cn';
	//config.font_defaultLabel = '宋体'
	config.font_names='宋体/宋体;黑体/黑体;仿宋/仿宋_GB2312;楷体/楷体_GB2312;隶书/隶书;幼圆/幼圆;微软雅黑/微软雅黑;'+config.font_names;
	config.font_style =
	 {
	 element : 'span',
	 styles : { 'font-family' : '宋体' },
	overrides : [ { element : 'font', attributes : { 'face' : null } } ]
	 };
};
