package com.launch.diagdevice.common.model.request;

public class RequestPaging extends BaseRequest {

	public RequestPaging() {
		sortBy = "1";
		sortType = "1";
	}

	private static final long serialVersionUID = 7584671848291002123L;
	// private static final String ASC = "0";
	private Integer pageNum;
	private Integer pageSize;
	private String sortBy;
	private String sortType;
	private String exportMethod;
	protected String startDate;
	protected String endDate;

	public String getExportMethod() {
		return exportMethod;
	}

	public void setExportMethod(String exportMethod) {
		this.exportMethod = exportMethod;
	}

	/*
	 * public PagingEntity toEntity(Class clazz) throws InstantiationException,
	 * IllegalAccessException { PagingEntity entity =
	 * (PagingEntity)clazz.newInstance(); BeanUtils.copyProperties(this,
	 * entity); entity.setLimit(getLimit()); entity.setOffset(getOffset());
	 * entity.setOrderBy(getSortBy()); entity.setOrderByType(getSortType());
	 * return entity; }
	 */

	public Integer getPageNum() {
		if (pageNum == null)
			return new Integer(1);
		else
			return pageNum;
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

	public Integer getOffset() {
		Integer page = getPageNum();
		Integer pageSize = getPageSize();
		Integer offset = Integer.valueOf((page.intValue() - 1) * pageSize.intValue());
		return offset;
	}

	public Integer getLimit() {
		return getPageSize();
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public String getSortType() {
		if ("0".equalsIgnoreCase(sortType))
			sortType = "asc";
		else
			sortType = "desc";
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Integer getRealPageSize() {
		if (pageSize == null)
			throw new RuntimeException("piageSize is null");
		else
			return pageSize;
	}

}