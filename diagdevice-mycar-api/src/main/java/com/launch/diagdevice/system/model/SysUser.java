package com.launch.diagdevice.system.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class SysUser implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer userId;
	private String cc;
	/*
	 * 新增需求，以经销商登陆，在用户管理--》产品用户管理列表增加一列序列号--Bywuqiaolan 2015/6/17
	 */
	private String serialNo;
	private String userName;
	private Date regTime;
	private Integer gender;
	private Integer state;
	/**
	 * 记录服务商状态，设置规则如下： null ： 未申请----未申请成为服务商，前台展示：申请成为服务商<br/>
	 * 1 ： 已添加----已添加服务商信息，可能审核通过，也可能未通过，前台可展示菜单：添加服务商，查询服务商状态，注销服务商<br/>
	 * 0 ： 注销------已注销服务商信息，该用户添加的信息也将删除<br/>
	 * -1 ： 禁止申请---恶意申请了许多条服务商信息，且管理员审核不通过，则由管理员将此值设置为-1，表示禁止申请
	 */
	private Integer merchantFlag;
	private String mobile;
	private String email;
	/** 0标示没有订购产品关系的经销商，非0时表示产品存在经销商的个数 */
	private Integer venderFlag;

}
