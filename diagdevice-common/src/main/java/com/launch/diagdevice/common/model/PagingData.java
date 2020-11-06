package com.launch.diagdevice.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PagingData<T>
    implements Serializable
{

	private List<T> rows;
    private Long total;
    
    
	public PagingData(List<T> rows, Long total)
    {
        this.rows = new ArrayList<T>();
        this.total = Long.valueOf(0);
        this.rows = rows;
        this.total = total;
    }

    public PagingData()
    {
        rows = new ArrayList<T>();
        total = Long.valueOf(0);
    }

    public List<T> getRows()
    {
        return rows;
    }

    public void setRows(List<T> rows)
    {
        this.rows = rows;
    }

    public Long getTotal()
    {
        return total;
    }

    public void setTotal(Long total)
    {
        this.total = total;
    }

    /**
	 * 
	 */
	private static final long serialVersionUID = 1201007049440078072L;
}