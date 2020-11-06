package com.launch.diagdevice.blockchain.dao;

import com.launch.diagdevice.blockchain.model.CzlAsset;

public interface CzlAssetDao
{
    
    /**
     * 传入资产类型、资产名称、hash值（图片等）
     * @param czlAsset
     * @return
     * @since DBS V100
     */
    CzlAsset saveCzlAsset(CzlAsset czlAsset);
    
    /**
     * 根据参数查询存证链数字资产信息
     * @param assetName
     * @param assetType
     * @return
     * @since DBS V100
     */
    CzlAsset queryCzlAsset(String assetName,String assetType);

    /**
     * 根据订单id，查找对应用户资产id
     * @param userId
     * @return
     * @since DBS V100
     */
    CzlAsset queryCzlUserAsset(Integer orderId);

}
