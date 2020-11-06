package com.launch.diagdevice.system.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class DiagSoftInfo implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 8971590795083782391L;
    /**
     * 软件id
     */
    private int softId;
    /**
     * 产品类型id
     */
    private int pdtTypeId;
    /**
     * 软件编码
     */
    private String softName;
    /**
     * 软件名称
     */
    private String softNameDesc;
    /**
     * 适用区域
     */
    private String softApplicableArea;
    /**
     * 有效标识
     */
    private int validFlag;

}
