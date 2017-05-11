[#ftl]
[@b.head/]
[#include "../nav.ftl"/]
	[@b.tabs]
	  [@b.tab theme="list" hasTable=1 label="java"]
	  	<table >
			<thead class="thead">
				<td>系统属性1</td>
				<td>值</td>
			</thead>
			[#list javaProps?keys?sort as key]
			<tr>
				<td>${b.text(key)}</td>
				<td>${javaProps[key]}</td>
			</tr>
			[/#list]
		</table>
	  [/@]
	  [@b.tab theme="list" hasTable=1 label="os"]
	  	<table>
			<thead class="thead">
				<td>系统属性2</td>
				<td>值</td>
			</thead>
			[#list osProps?keys?sort as key]
			<tr>
				<td>${b.text(key)}</td>
				<td>${osProps[key]}</td>
			</tr>
			[/#list]

		</table>
	  [/@]
	  [@b.tab theme="list" hasTable=1 label="user"]
	  	<table>
			<tr class="thead">
				<td>系统属性</td>
				<td>值</td>
			</tr>
			[#list userProps?keys?sort as key]
			<tr>
				<td>${b.text(key)}</td>
				<td>${userProps[key]}</td>
			</tr>
			[/#list]
		</table>
	  [/@]
	  [@b.tab theme="list" hasTable=1 label="extra"]
	  	<table>
			<thead class="thead">
				<td>系统属性</td>
				<td>值</td>
			</thead>
			[#list extraProps?keys?sort as key]
			<tr>
				<td>${b.text(key)}</td>
				<td>${extraProps[key]}</td>
			</tr>
			[/#list]
		</table>
	  [/@]
	[/@]
[@b.foot/]