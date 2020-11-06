package com.launch.diagdevice.blockchain.dao.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.launch.diagdevice.blockchain.model.CzlAsset;

public interface CzlAssetMapper
{

    /**
     * 保存设备资产
     * @param serialNo
     * @return
     * @since DBS V100
     */
    @Insert({ "insert into czl_asset(asset_name,asset_id,asset_type)values(#{assetName},#{assetId},#{assetType})" })
    void saveCzlAsset(CzlAsset czlAsset);

    /**
     * 查询设备资产信息
     * @param serialNo
     * @return
     * @since DBS V100
     */

    @Results({ @Result(property = "fieldName", column = "field_name"), @Result(property = "assetsType", column = "assets_Type"),
        @Result(property = "assetName", column = "asset_name"), @Result(property = "assetId", column = "asset_id") })
    @Select("select *  from czl_asset ca where asset_type=#{assetType} and asset_name=#{assetName}")
    CzlAsset queryCzlAsset(CzlAsset czlAsset);

    /**
     * 根据订单id查询对应的用户资产信息，以便追加资产记录
     * @param orderId
     * @return
     * @since DBS V100
     */
    @Results({ @Result(property = "assetsType", column = "assets_Type"),
        @Result(property = "assetName", column = "asset_name"), @Result(property = "assetId", column = "asset_id") })
    @Select("select ca.* from czl_asset ca ,user_order uo where ca.assets_type='用户钱包' and uo.user_id=ca.asset_name and uo.id=${orderId}")
    CzlAsset queryCzlUserAsset(Integer orderId);

}
