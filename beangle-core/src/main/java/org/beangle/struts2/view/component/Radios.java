package org.beangle.struts2.view.component;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.StrUtils;

import com.opensymphony.xwork2.util.ValueStack;

public class Radios extends UIBean {

	private Object items;

	private Radio[] radios;

	private Object value;

	private String keyName = "id";
	private String valueName = "title";

	private String comment;

	public Radios(ValueStack stack) {
		super(stack);
	}

	@Override
	protected void evaluateParams() {
		if (null == this.id) generateIdIfEmpty();
		if(label == null) label = name;
		if (null != label) label = getText(label);

		List<?> keys = convertItems();
		radios = new Radio[keys.size()];
		int i = 0;
		for (Object key : keys) {
			radios[i] = new Radio(stack);
			radios[i].setTitle(String.valueOf(((Map<?,?>)items).get(key)));
			radios[i].setValue(key);
			radios[i].setId(StrUtils.concat(this.id + "_" + String.valueOf(i)));
			radios[i].evaluateParams();
			i++;
		}
		if (null == this.value && radios.length > 0) this.value = radios[0].getValue();
		this.value = Radio.booleanize(this.value);
		Form myform = findAncestor(Form.class);
		if (null != myform) {
			if ("true".equals(required)) {
//				myform.addCheck(id+"_span", "assert(function(){$(\"#" + id + ":checked\").length != 0,'必须勾选一项')");
				myform.addCheck(id, ".required()");
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<?> convertItems() {
		if (items instanceof Map) return CollectUtils.newArrayList(((Map<Object, Object>) items).keySet());
		Map<Object, Object> defaultItemMap = new TreeMap<Object, Object>();
		defaultItemMap.put("1", "是");
		defaultItemMap.put("0", "否");
		List<?> defaultKeys = CollectUtils.newArrayList("1", "0");
		Map<Object, Object> itemMap = new TreeMap<Object, Object>();
		List keys = CollectUtils.newArrayList();
		if (null == items) {
			keys = defaultKeys;
			items = defaultItemMap;
		} else if (items instanceof String) {
			if (StringUtils.isBlank((String) items)) {
				keys = defaultKeys;
				items = defaultItemMap;
			}else{
				String[] titleArray = StringUtils.split(items.toString(), ',');
				for (int i = 0; i < titleArray.length; i++) {
					String titleValue = titleArray[i];
					int semiconIndex = titleValue.indexOf(':');
					if (-1 != semiconIndex) {
						keys.add(titleValue.substring(0, semiconIndex));
						itemMap.put(titleValue.substring(0, semiconIndex),
								titleValue.substring(semiconIndex + 1));
					}
				}
				items = itemMap;
			}
		} else if(items instanceof Collection<?>){
			for(Object o :  (Collection) items){
				try {
					Object key = BeanUtils.getProperty(o, keyName);
					Object value = BeanUtils.getProperty(o, valueName);
					keys.add(key);
					itemMap.put(key, value);
				} catch (SecurityException e) {
				} catch (IllegalArgumentException e) {
				} catch (IllegalAccessException e) {
				} catch (InvocationTargetException e) {
				} catch (NoSuchMethodException e) {
				}
			}
			items = itemMap;
		}
		return keys;
	}

	public Object getItems() {
		return items;
	}

	public void setItems(Object items) {
		this.items = items;
	}

	public Radio[] getRadios() {
		return radios;
	}

	public void setRadios(Radio[] radios) {
		this.radios = radios;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		if(value != null){
			value = value.toString();
		}
		this.value = value;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setOption(String option) {
		if (null != option) {
			if (StringUtils.contains(option, ",")) {
				keyName = StringUtils.substringBefore(option, ",");
				valueName = StringUtils.substringAfter(option, ",");
			}
		}
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

}
