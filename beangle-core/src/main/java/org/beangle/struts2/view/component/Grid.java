/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import java.io.Writer;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.struts2.util.MakeIterator;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.page.Page;
import org.beangle.commons.lang.StrUtils;
import org.beangle.ems.config.model.ClassPropertyConfig;
import org.beangle.struts2.view.template.Theme;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * Data table
 * 
 * @author beangle
 */
public class Grid extends ClosingUIBean {
	private List<Col> cols = CollectUtils.newArrayList();
	private Set<Object> colTitles = CollectUtils.newHashSet();
	private Object items;
	private String var;
	// gridbar
	private String bar;
	private String sortable = "true";
	private String filterable = "false";
	private Map<String, String> filters = CollectUtils.newHashMap();

	/** 重新载入的时间间隔（以秒为单位） */
	private String refresh;

	/** 没有数据时显示的文本 */
	private String emptyMsg;
	
	private List<ClassPropertyConfig> configs;

	public Grid(ValueStack stack) {
		super(stack);
	}

	public boolean getHasbar() {
		return (null != bar || items instanceof Page);
	}

	public boolean isPageable() {
		return items instanceof Page<?>;
	}

	public boolean isNotFullPage() {
		if(isPageable()){
			return ((Page<?>) items).size() < ((Page<?>) items).getPageSize();
		}else{
			return ( (List<?>)items).size() < 20;
		}
	}

	public String defaultSort(String property) {
		return StrUtils.concat(var, ".", property);
	}

	public boolean isSortable(Col cln) {
		Object sortby = cln.getParameters().get("sort");
		if (null != sortby) return true;
		return ("true".equals(sortable) && !ObjectUtils.equals(cln.getSortable(), "false") && null != cln
				.getProperty());
	}

	public boolean isFilterable(Col cln) {
		return ("true".equals(filterable) && !ObjectUtils.equals(cln.getFilterable(), "false") && null != cln
				.getProperty());
	}

	protected void addCol(Col column) {
		if(!listable(column)){
			return;
		}
		Object title = column.getTitle();
		if (null == title) {
			title = column.getProperty();
		}
		if (!colTitles.contains(title)) {
			colTitles.add(title);
			cols.add(column);
		}
	}

	public boolean start(Writer writer) {
		generateIdIfEmpty();
		return true;
	}

	public List<Col> getCols() {
		return cols;
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getRefresh() {
		return refresh;
	}

	public void setRefresh(String refresh) {
		int refreshNum = NumberUtils.toInt(refresh);
		if (refreshNum > 0) this.refresh = String.valueOf(refreshNum);
	}

	public void setItems(Object datas) {
		if (datas instanceof String) {
			this.items = findValue((String) datas);
		} else {
			this.items = datas;
		}
	}

	public Object getItems() {
		return items;
	}

	public String getSortable() {
		return sortable;
	}

	public void setSortable(String sortable) {
		this.sortable = sortable;
	}

	public String getFilterable() {
		return filterable;
	}

	public void setFilterable(String filterable) {
		this.filterable = filterable;
	}

	public String getBar() {
		return bar;
	}

	public String getEmptyMsg() {
		return emptyMsg;
	}

	public void setEmptyMsg(String emptyMsg) {
		this.emptyMsg = emptyMsg;
	}

	public Map<String, String> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, String> filters) {
		this.filters = filters;
	}

	public void setConfigs(List<ClassPropertyConfig> configs) {
		this.configs = configs;
	}

	public boolean listable(Col col) {
		String name = col.getProperty();
		if(configs == null || StringUtils.isEmpty(name)){
			return true;
		}
//		name = name.substring(name.indexOf(".") + 1);
//		if(name.endsWith(".id")){
//			name = name.substring(0, name.length() - 3);
//		}
		if("id".equals(name)){
			if(StringUtils.isEmpty(col.width )){
				col.width = "1%";
			}
			return true;
		}
		for(ClassPropertyConfig cpc : configs){
			if(name.startsWith(cpc.getPropertyName())){
				col.setTitle(cpc.getLabel());
				return true;
			}
		}
		return false;
	}
	

	public static class Filter extends ClosingUIBean {
		String property;

		public Filter(ValueStack stack) {
			super(stack);
		}

		@Override
		public boolean doEnd(Writer writer, String body) {
			Grid grid = (Grid) findAncestor(Grid.class);
			if (null != property && null != grid) {
				grid.getFilters().put(property, body);
			}
			return false;
		}

		public String getProperty() {
			return property;
		}

		public void setProperty(String property) {
			this.property = property;
		}

	}

	public static class Bar extends ClosingUIBean {
		private Grid grid;

		public Bar(ValueStack stack) {
			super(stack);
			grid = (Grid) findAncestor(Grid.class);
		}

		@Override
		public boolean doEnd(Writer writer, String body) {
			grid.bar = body;
			return false;
		}

		public Grid getTable() {
			return grid;
		}
	}

	public static class Row extends IterableUIBean {
		private Grid table;
		private String var_index;
		private Iterator<?> iterator;
		private int index = -1;
		protected Object curObj;
		private Boolean innerTr;

		public Row(ValueStack stack) {
			super(stack);
			table = (Grid) findAncestor(Grid.class);
			Object iteratorTarget = table.items;
			iterator = MakeIterator.convert(iteratorTarget);
			if (!iterator.hasNext()) {
				iterator = Collections.singleton(null).iterator();
			}
			this.var_index = table.var + "_index";
		}

		public boolean isHasTr() {
			if (null != innerTr) return innerTr;
			innerTr = StringUtils.contains(body, "<tr");
			return innerTr;
		}

		@Override
		protected boolean next() {
			if (iterator != null && iterator.hasNext()) {
				index++;
				curObj = iterator.next();
				stack.getContext().put(table.var, curObj);
				stack.getContext().put(var_index, index);
				return true;
			} else {
				stack.getContext().remove(table.var);
				stack.getContext().remove(var_index);
			}
			return false;
		}
	}

	/**
	 * @author beangle
	 */
	public static class Col extends ClosingUIBean {
		String property;
		String title;
		String width;
		Row row;
		String sortable;
		String filterable;
		Grid table;

		public Col(ValueStack stack) {
			super(stack);
			table = (Grid) findAncestor(Grid.class);
			row = (Row) findAncestor(Row.class);
		}

		@Override
		public boolean start(Writer writer) {
			if (row.index == 0) {
				row.table.addCol(this);
			}
			return null != row.curObj;
		}

		@Override
		public boolean doEnd(Writer writer, String body) {
			if(!table.listable(this)){
				return false;
			}
			if (getTheme().equals(Theme.DEFAULT_THEME)) {
				try {
					writer.append("<td").append(getParameterString()).append(">");
					if (StringUtils.isNotEmpty(body)) {
						writer.append(body);
					} else if (null != property) {
						Object val = getValue();
						if (null != val) StringEscapeUtils.escapeHtml(writer, val.toString());
					}
					writer.append("</td>");
				} catch (Exception e) {
					e.printStackTrace();
				}
				return false;
			} else {
				return super.doEnd(writer, body);
			}
		}

		public String getProperty() {
			return property;
		}

		public void setProperty(String property) {
			this.property = property;
		}

		/**
		 * find value of row.obj's property
		 * 
		 * @return
		 */
		public Object getValue() {
			return getValue(row.curObj, property);
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getPropertyPath() {
			return StrUtils.concat(row.table.var, ".", property);
		}

		public String getTitle() {
			if (null == title) {
				title = StrUtils.concat(row.table.var, ".", property);
			}
			if(StringUtils.isNotEmpty(title) && title.indexOf("+") >= 0){
				String subtitle = title.substring(0, title.indexOf("+"));
				return getText(subtitle) + getText(title.substring(title.indexOf("+")+1,title.length()));
			}
			return getText(title);
		}

		public String getWidth() {
			return width;
		}

		public void setWidth(String width) {
			this.width = width;
		}

		public String getSortable() {
			return sortable;
		}

		public void setSortable(String sortable) {
			this.sortable = sortable;
		}

		public String getFilterable() {
			return filterable;
		}

		public void setFilterable(String filterable) {
			this.filterable = filterable;
		}

	}

	public static class Boxcol extends Col {

		public Boxcol(ValueStack stack) {
			super(stack);
		}

		String type = "checkbox";
		// checkbox or radiobox name
		String boxname = null;
		boolean checked;

		@Override
		public boolean start(Writer writer) {
			if (null == property) {
				this.property = "id";
			}
			row = (Row) findAncestor(Row.class);
			if (null == boxname) {
				boxname = row.table.var + "." + property;
			}
			if (row.index == 0) {
				row.table.addCol(this);
			}
			return null != row.curObj;
		}

		@Override
		public boolean doEnd(Writer writer, String body) {
			if (getTheme().equals(Theme.DEFAULT_THEME)) {
				try {
					writer.append("<td align='center'");
					if (null != id) writer.append(" id=\"").append(id).append("\"");
					writer.append(getParameterString()).append(">");
					writer.append("<input title='").append(String.valueOf(getValue())).append("' class=\"box\" name=\"").append(boxname).append("\" value=\"")
							.append(String.valueOf(getValue())).append("\" type=\"").append(type)
							.append("\"");
					if (checked) writer.append(" checked=\"checked\"");
					writer.append("/>");
					if (StringUtils.isNotEmpty(body)) {
						writer.append(body);
					}
					writer.append("</td>");
				} catch (Exception e) {
					e.printStackTrace();
				}
				return false;
			} else {
				return super.doEnd(writer, body);
			}
		}

		public String getType() {
			return type;
		}

		@Override
		public String getTitle() {
			return StrUtils.concat(row.table.var, "_", property);
		}

		public String getBoxname() {
			return boxname;
		}

		public void setBoxname(String boxname) {
			this.boxname = boxname;
		}

		public void setType(String type) {
			this.type = type;
		}

		public boolean isChecked() {
			return checked;
		}

		public void setChecked(boolean checked) {
			this.checked = checked;
		}
	}
}
