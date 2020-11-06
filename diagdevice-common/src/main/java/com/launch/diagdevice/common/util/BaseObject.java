package com.launch.diagdevice.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

/**
 * Base class for Model objects. Child objects should implement toString(),
 * equals() and hashCode().
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class BaseObject {
	protected String action;
	protected String communicate_id = "ucenter_id";
	protected String version = "1.0";

	/**
	 * Returns a multi-line String with key=value pairs.
	 * 
	 * @return a String representation of this class.
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws Exception
	 */
	public String toString() {
		StringBuffer strToReturn = new StringBuffer();
		PropertyDescriptor[] propDescs = BeanUtils.getPropertyDescriptors(this.getClass());

		for (int i = 0; i < propDescs.length; i++) {
			String propName = propDescs[i].getName();
			PropertyDescriptor prop = propDescs[i];

			if (prop.getReadMethod() != null && prop.getWriteMethod() != null) {
				try {
					Object value = null;

					// 如果为日期类型
					if (prop.getPropertyType().isAssignableFrom(java.util.Date.class)) {
						value = prop.getReadMethod().invoke(this);
						if (null != value) {
							SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							value = dateformat1.format((Date) value);
						}
					} else {
						value = prop.getReadMethod().invoke(this);
					}

					if (!propName.equals("sign") && value != null && !StringUtils.isEmpty(value.toString())) {
						strToReturn.append(propName + "=");
						strToReturn.append(value + "&");
					}
				} catch (Exception e) {
					strToReturn.append("");
				}
			}
		}

		return strToReturn.substring(0, strToReturn.length() - 1);
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getCommunicate_id() {
		return communicate_id;
	}

	public void setCommunicate_id(String communicate_id) {
		this.communicate_id = communicate_id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
