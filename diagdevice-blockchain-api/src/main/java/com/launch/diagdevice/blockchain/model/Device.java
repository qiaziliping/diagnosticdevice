package com.launch.diagdevice.blockchain.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class Device implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 4070395400173296074L;
    private int id;
    private String serialNo;
    private String deviceType;
    private int deviceGroupId;
    private String location;
    private String owner;
    private String voucher;
    private String remark;
    private int status;
    private String createUserId;
    private String updateUserId;
    private Date createTime;
    private Date updateTime;
    private String allocationId;

}
