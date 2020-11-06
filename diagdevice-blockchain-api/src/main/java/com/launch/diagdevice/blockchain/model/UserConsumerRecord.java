package com.launch.diagdevice.blockchain.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class UserConsumerRecord implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -8191385369765858569L;
    private int orderId;
    private String softName;
    private String vinCode;
    private Date diagStartTime;
    private Date diagEndTime;
    private Date createTime;

}
