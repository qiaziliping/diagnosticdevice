package com.launch.diagdevice.common.model.request;

public class IdRequest extends BaseRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1327321408947295962L;
	
	
	private String id;
//  private Integer recVer;

	public IdRequest()
    {
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    /*public Integer getRecVer()
    {
        return recVer;
    }

    public void setRecVer(Integer recVer)
    {
        this.recVer = recVer;
    }*/

}