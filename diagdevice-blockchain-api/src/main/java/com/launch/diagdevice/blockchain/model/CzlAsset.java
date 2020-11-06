package com.launch.diagdevice.blockchain.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class CzlAsset implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -4001086321105856562L;
    /**
     * 资产所在表
     */
//    private String tableName;
    /**
     * 资产所在字段
     */
//    private String fieldName;
    /**
     * 资产标识,fieldvValue字段
     */
    private String assetName;
    /**
     * 资产类型
     */
    private String assetType;
    /**
     * 资产uuid
     */
    private String assetId;
    /**
     * 
     */
    private String assetHash;

    /*public CzlAsset(String tableName, String fieldName, String assetType)
    {
        super();
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.assetType = assetType;
    }*/
    
    public CzlAsset(String assetType)
    {
        this.assetType = assetType;
    }

    public CzlAsset()
    {
        super();
    }

}
