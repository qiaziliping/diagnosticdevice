package com.launch.diagdevice.blockchain.model;

import java.io.Serializable;

import lombok.Data;

/**
 * 存证链资产记录表
 * @author ouxiangrui
 */
@Data
public class CzlAssetRecord implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -7525885353185871078L;
    /**
     * 取hash值类名
     */
    private String className;
    /**
     * 记录标识
     */
    private String recordId;
    /**
     * 记录大概内容
     */
    private String content;
    /**
     * 记录类型，
     */
    private String recordType;
    /**
     * 资产标识
     */
    private String assetId;
    /**
     * 记录hash值
     */
    private String metaHash;

}
