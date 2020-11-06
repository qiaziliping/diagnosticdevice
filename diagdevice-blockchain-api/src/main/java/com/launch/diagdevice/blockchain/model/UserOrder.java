package com.launch.diagdevice.blockchain.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class UserOrder implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 3680332141714069548L;
    private int id;
    private int userId;
    private String serialNo;
    private String orderNo;
    private double price;
    private int payFrom;
    private Date payTime;
    private String remark;
    private int status;
    private Date createTime;
    private String thirdTradeNo;
}
