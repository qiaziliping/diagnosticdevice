package com.launch.diagdevice.blockchain.service;

import com.launch.diagdevice.blockchain.model.CzlAsset;
import com.launch.diagdevice.blockchain.model.CzlAssetRecord;

/**
 * <存证链存证服务> 资产存证，资产记录存证
 * @author ouxiangrui
 * @version 1.0 2018年9月27日
 * @since DBS V100
 */
public interface CzService
{
    /**
     * 创建资产
     * @param czlAsset
     * @since DBS V100
     */
    void createAsset(CzlAsset czlAsset);
    /**
     * 创建设备资产
     * @since DBS V100
     */
//    void createDeviceAsset(String serialNo);

    /**
     * 创建用户资产
     * @since DBS V100
     */
//    void createUserAsset(String userId);

    /**
     * 创建软件价格资产
     * @since DBS V100
     */
//    void createSoftPriceAsset(String id);
    
    /**
     * 
     * @param assetRecord
     * @since DBS V100
     */
    void appendAssetRecord(CzlAssetRecord assetRecord);

    /**
     * 创建用户资产记录，包含订单记录
     * @since DBS V100
     */
//    void appendUserOrderRecord(Integer orderId);

    /**
     * 创建用户消费记录
     * @since DBS V100
     */
//    void appendUserCosumeRecord(Integer orderId);

    /**
     * @param device
     * @since DBS V100
     */
//    void appendDeviceRecord(String deviceId);

}
