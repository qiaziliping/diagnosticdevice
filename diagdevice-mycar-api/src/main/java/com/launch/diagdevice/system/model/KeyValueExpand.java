package com.launch.diagdevice.system.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class KeyValueExpand implements Serializable {

	private static final long serialVersionUID = -7422533009905032540L;
	private String key;
	private String value;
	private String functionDesc;
	private String remark;
	private String oldValue;
	private String updatePeople;
	private Date updateTime;

}
