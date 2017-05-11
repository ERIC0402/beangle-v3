package org.beangle.website.system.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.model.pojo.LongIdTimeObject;

/**
 * 字典类型
 * @author GOKU
 *
 */
@Entity
public class DictType extends LongIdTimeObject {

	private static final long serialVersionUID = 2042297309066346620L;

	/** 名称 */
	@Size(max = 100)
	@NotNull
	private String name;
	
	/** 编码 */
	@Size(max = 100)
	@NotNull
	private String code;
	
	/** 英文名称 */
	@Size(max = 100)
	private String enName;
	/** 是否有效 */
	private boolean enabled = true;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

}
