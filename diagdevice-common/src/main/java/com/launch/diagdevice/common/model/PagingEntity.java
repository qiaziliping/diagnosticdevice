package com.launch.diagdevice.common.model;

public class PagingEntity extends OperateEntity {

	public PagingEntity() {

		pageNum = Integer.valueOf(0);
		pageSize = Integer.valueOf(15);
		// offset = Integer.valueOf(0);
		// limit = Integer.valueOf(15);
		orderByType = "DESC"; //
		orderBy = "1";
	}
	/** 第几页 */
	private Integer pageNum;
	/** 分页大小 */
	private Integer pageSize;
	// private Integer offset;
	// private Integer limit;
	/** 默认降序 */
	private String orderByType;
	/** 默认根据第一列字段排序 */
	private String orderBy;
	// private Date startTime;
	// private Date endTime;

	public String getOrderByType() {
		return orderByType;
	}

	public void setOrderByType(String orderByType) {
		this.orderByType = orderByType;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	/*
	 * public Integer getOffset() { return offset; }
	 * 
	 * public void setOffset(Integer offset) { this.offset = offset; }
	 * 
	 * public Integer getLimit() { return limit; }
	 * 
	 * public void setLimit(Integer limit) { this.limit = limit; }
	 */


	/**
	 * 
	 */
	private static final long serialVersionUID = 1729229920654503098L;

	public Integer getPageNum() {
		if (pageNum == null)
			return new Integer(1);
		if (pageNum.intValue() == 0)
			return new Integer(1);
		else  return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		if (pageSize == null)
			return new Integer(30);
		if (pageSize.intValue() > 100)
			return new Integer(99);
		else
			return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}