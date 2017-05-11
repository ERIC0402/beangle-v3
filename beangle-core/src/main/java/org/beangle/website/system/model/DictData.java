package org.beangle.website.system.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.model.pojo.LongIdTimeObject;

/**
 * 字典数据
 * @author GOKU
 *
 */
@Entity(name="org.beangle.website.system.model.DictData")
public class DictData extends LongIdTimeObject {

	private static final long serialVersionUID = 8955107877304788039L;

	/** 编码 */
	@Size(max = 100)
	@NotNull
	private String code;

	/** 名称 */
	@Size(max = 100)
	@NotNull
	private String name;
	
	/** 英文名称 */
	@Size(max = 100)
	private String enName;
	
	/** 国标校标 */
	@Size(max = 100)
	private String gbxb;

	/** 所属字典类型 */
	private DictType dictType;
	
	/** 是否有效 */
	private boolean enabled = true;
	
	public DictData() {
		super();
	}

	public DictData(Long id) {
		setId(id);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public DictType getDictType() {
		return dictType;
	}

	public void setDictType(DictType dictType) {
		this.dictType = dictType;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGbxb() {
		return gbxb;
	}

	public void setGbxb(String gbxb) {
		this.gbxb = gbxb;
	}

	

}
