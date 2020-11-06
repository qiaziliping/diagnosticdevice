package com.launch.diagdevice.common.model;

import java.util.Date;

/**
 * 操作实体类
 * @author LIPING
 * @version 0.0.1
 * @since 2018年8月10日
 */
public abstract class OperateEntity extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1872200788467832028L;

	public OperateEntity() {
		status = Integer.valueOf(0);
	}

	public static final Integer STATUS_NORMAL = Integer.valueOf(0);
	public static final Integer STATUS_DELETE = Integer.valueOf(1);
	/** 是否删除，0否，1是 */
	protected Integer status;
	protected String remark;
	protected Date createTime;
	protected String createUserId;
	protected Date updateTime;
	protected String updateUserId;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}