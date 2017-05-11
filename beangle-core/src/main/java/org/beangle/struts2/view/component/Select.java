/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;

import com.opensymphony.xwork2.util.ValueStack;

import freemarker.template.SimpleScalar;

public class Select extends ClosingUIBean {

	private Object items = Collections.emptyList();
	private String empty = "请选择...";

	private String keyName;
	private String valueName;

	protected String title;

	protected String comment;

	public Select(ValueStack stack) {
		super(stack);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void evaluateParams() {
		Form myform = findAncestor(Form.class);
		if(items != null && !(items instanceof Collection) && !(items instanceof Map) && !(items instanceof Array)){
			List list = CollectUtils.newArrayList();
			list.add(items);
			items = list;
		}
		if (null == keyName) {
			if(items instanceof Collection && ((Collection) items).size() > 0 && ((Collection)items).iterator().next() instanceof String){
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				for(String s : (Collection<String>)items){
					Map map = new HashMap();
					map.put("id", s);
					map.put("name", s);
					list.add(map);
				}
				items = list;
			}
			if (items instanceof Map<?, ?>) {
				keyName = "key";
				valueName = "value";
				items = ((Map<?, ?>) items).entrySet();
			} else {
				keyName = "id";
				valueName = "name";
			}
		}
		if (null == this.id)
			generateIdIfEmpty();
		if (null != label)
			label = getText(label);
		if (null != title) {
			title = getText(title);
		} else {
			title = label;
		}
		if (null != myform) {
			myform.addCheck(this);
		}
	}

	public boolean isSelected(Object obj) {
		if (null == value)
			return false;
		else
			try {
				if (value instanceof Number) {
					Number v = (Number) value;
					return v.longValue() == new Long(PropertyUtils.getSimpleProperty(obj, keyName).toString());
				} else if (value instanceof Collection) {
					@SuppressWarnings("rawtypes")
					Collection c = (Collection) value;
					for (Object o : c) {
						if (o.equals(obj)) {
							return true;
						}
					}
					return false;
				} else {
					try {
						if(obj instanceof Map){
							return value.equals(((Map)obj).get(keyName));
						}else{
							return value.equals(obj) || value.equals(PropertyUtils.getSimpleProperty(obj, keyName));
						}
					} catch (Exception e) {
						return false;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
	}

	public Object getItems() {
		return items;
	}

	public void setItems(Object items) {
		this.items = items;
	}

	public String getEmpty() {
		return empty;
	}

	public void setEmpty(String empty) {
		this.empty = empty;
	}

	public String getKeyName() {
		return keyName;
	}

	public String getValueName() {
		return valueName;
	}

	public void setOption(String option) {
		if (null != option) {
			if (StringUtils.contains(option, ",")) {
				keyName = StringUtils.substringBefore(option, ",");
				valueName = StringUtils.substringAfter(option, ",");
			}
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String getParameterString() {
		String parameterString = super.getParameterString();
		SimpleScalar placeholder = (SimpleScalar) getParameters().get("placeholder");
		if (placeholder != null && StringUtils.isNotEmpty(placeholder.getAsString())) {
			parameterString += " data-placeholder=\"" + placeholder + "\"";
		}
		return parameterString;
	}
}
