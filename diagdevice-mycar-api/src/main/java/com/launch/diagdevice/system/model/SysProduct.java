package com.launch.diagdevice.system.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class SysProduct implements Serializable {
	private static final long serialVersionUID = -7730462122167419558L;

	private Integer productId;
	// 序列号
	private String serialNo;
	// 产品号
	private String productNo;
	// 产品出库时调用
	private Date saleTime;
	// 产品经销商ID
	private Integer venderId;
	// 产品分公司ID
	private Integer filialeId;
	// 注册时间
	private Date regTime;
	// 创建时间
	private Date createTime;
	// 更新时间
	private Date updateTime;
	// 产品状态
	private Integer pdtState;
	private Integer bookerId;
	// 所属用户ID
	private Integer userId;
	private String remark;
	// 所属产品类型ID
	private Integer pdtTypeId;
	// 产品的软件配置Id
	private Integer softConfId;
	// 产品的软件出厂配置Id
	private Integer originConfId;
	// 产品的使用密码（之前由Android提出后来弃之不用）
	private String password;
	private String productUsePwd;
	// 用户手机的蓝牙MAC地址（之前为蓝牙名称）
	private String bluetoolthName;
	// 是否是内部序列号 如果是内部序列号可以任意烧录软件
	private Integer isInnerProduct;
	// 是否能打印密码
	private Integer isPassWordFlag;
	// 产品注册所在地的经度（dbscar要求的）
	private String longitude;
	// 产品注册所在地的纬度（dbscar要求的）
	private String latitude;
	// ERP 同步过来的订单号
	private String orderNo;
	// 是否为配件（默认为产品）
	private Integer productPart = 0;

	// ERP同步过来客户编码
	private Integer customerNumber;

	// ERP同步过来客户编码（作为查看记录使用）
	private Integer customerNumberUpdate;

	// 是否为DBScar_pro
	private Integer isDbscarPro;

	private String cc;

	private Integer areaId;

	private String svName;

	private String svCity;

	private Integer upgradeFlag;

	private Integer downloadAreaId;

	private Integer countryId;

	private Integer downloadFlag;
	// 产品（删除，更新）审批状态
	private Integer approvalState;
	
	private String pdtType;

}
