package com.launch.diagdevice.system.vo;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.StringUtils;

/**
 * Base class for Model objects. Child objects should implement toString(),
 * equals() and hashCode().
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public class BaseObject
{
	private String ip;

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
    protected String action;
    // protected String sign;
    protected String communicate_id = "ucenter_id";
    protected String version = "1.0";

    /**
     * Returns a multi-line String with key=value pairs.
     * @return a String representation of this class.
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws Exception
     */
    @Override
	public String toString()
    {
        StringBuffer strToReturn = new StringBuffer();
        PropertyDescriptor[] propDescs = PropertyUtils.getPropertyDescriptors(this);

        for (int i = 0; i < propDescs.length; i++)
        {
            String propName = propDescs[i].getName();

            try
            {
                if ((!(PropertyUtils.getPropertyType(this, propName)).getName().equals(java.lang.String.class.getName()) && PropertyUtils.isReadable(this, propName)
                    && PropertyUtils.isWriteable(this, propName) && PropertyUtils.getProperty(this, propName) != null)
                    || ((PropertyUtils.getPropertyType(this, propName)).getName().equals(java.lang.String.class.getName()) && !StringUtils.isEmpty((String) PropertyUtils.getProperty(this, propName))))
                {
                    try
                    {
                        Object value = null;
                        strToReturn.append(propName + "=");
                        // 如果为日期类型
                        if ((PropertyUtils.getPropertyType(this, propName)).getName().equals(java.util.Date.class.getName()))
                        {
                            SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            value = dateformat1.format(PropertyUtils.getProperty(this, propName));

                        }
                        else
                        {
                            value = PropertyUtils.getProperty(this, propName);
                        }

                        strToReturn.append(value + "&");
                    }
                    catch (Exception e)
                    {
                        strToReturn.append("");
                    }
                }
            }
            catch (IllegalAccessException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (InvocationTargetException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            catch (NoSuchMethodException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return strToReturn.substring(0, strToReturn.length() - 1);
    }

    public String toStringWithEncode() {
		StringBuffer strToReturn = new StringBuffer();
		PropertyDescriptor[] propDescs = PropertyUtils
				.getPropertyDescriptors(this);

		for (int i = 0; i < propDescs.length; i++) {
			String propName = propDescs[i].getName();

			try {
				if ((!(PropertyUtils.getPropertyType(this, propName)).getName()
						.equals(java.lang.String.class.getName())
						&& PropertyUtils.isReadable(this, propName)
						&& PropertyUtils.isWriteable(this, propName) && PropertyUtils
						.getProperty(this, propName) != null)
						|| ((PropertyUtils.getPropertyType(this, propName))
								.getName().equals(
										java.lang.String.class.getName()) && !StringUtils
								.isEmpty((String) PropertyUtils.getProperty(
										this, propName)))) {
					try {
						Object value = null;
						strToReturn.append(propName + "=");
						// 如果为日期类型
						if ((PropertyUtils.getPropertyType(this, propName))
								.getName().equals(
										java.util.Date.class.getName())) {
							SimpleDateFormat dateformat1 = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							value = dateformat1.format(PropertyUtils
									.getProperty(this, propName));

						} else {
							value = URLEncoder.encode(PropertyUtils.getProperty(this, propName).toString(),"UTF-8");
						}

						strToReturn.append(value + "&");
					} catch (Exception e) {
						strToReturn.append("");
					}
				}
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return strToReturn.substring(0, strToReturn.length() - 1);
	}
    public String getAction()
    {
        return action;
    }

    public void setAction(String action)
    {
        this.action = action;
    }

    public String getCommunicate_id()
    {
        return communicate_id;
    }

    public void setCommunicate_id(String communicate_id)
    {
        this.communicate_id = communicate_id;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

}
