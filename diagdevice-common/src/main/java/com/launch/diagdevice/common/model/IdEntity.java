package com.launch.diagdevice.common.model;

import java.io.Serializable;

/**
 * ID实体类
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月10日
 */
public abstract class IdEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5428564393538265311L;

	public IdEntity() {
	}

	protected String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}