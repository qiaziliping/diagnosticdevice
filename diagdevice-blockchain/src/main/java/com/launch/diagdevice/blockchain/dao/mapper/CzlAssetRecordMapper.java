package com.launch.diagdevice.blockchain.dao.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.launch.diagdevice.blockchain.model.CzlAssetRecord;
import com.launch.diagdevice.blockchain.model.CzlOperateLog;
import com.launch.diagdevice.blockchain.model.Device;
import com.launch.diagdevice.blockchain.model.UserConsumerRecord;
import com.launch.diagdevice.blockchain.model.UserOrder;

public interface CzlAssetRecordMapper {
	/**
	 * 本地数据库记录存证链请求参数和返回结果
	 * 
	 * @param rc
	 */
	@Insert({
			"insert into czl_asset_record(record_id,record_type,asset_id,meta_hash,class_name)values(#{recordId},#{recordType},#{assetId},#{metaHash},#{className})" })
	void saveCzlAssetRecord(CzlAssetRecord rc);

	/**
	 * 本地数据库查询存在链记录,暂时未使用
	 * 
	 * @param rc
	 * @return
	 */
	CzlAssetRecord queryCzlAssetRecord(CzlAssetRecord rc);

	/**
	 * 根据序列号查询设备，以生成记录hash，供设备记录上链
	 * 
	 * @param serialNo
	 * @return
	 * @since DBS V100
	 */
	@Results({ @Result(property = "id", column = "id"), @Result(property = "serialNo", column = "serial_no"),
			@Result(property = "deviceType", column = "device_type"),
			@Result(property = "deviceGroupId", column = "device_group_id"),
			@Result(property = "location", column = "location"), @Result(property = "owner", column = "owner"),
			@Result(property = "voucher", column = "voucher"), @Result(property = "remark", column = "remark"),
			@Result(property = "status", column = "status"),
			@Result(property = "createUserId", column = "create_User_Id"),
			@Result(property = "updateUserId", column = "update_User_Id"),
			@Result(property = "createTime", column = "create_Time"),
			@Result(property = "updateTime", column = "update_Time"),
			@Result(property = "allocationId", column = "allocation_Id") })
	@Select("select * from device d where d.id=#{deviceId}")
	Device queryDeviceById(@Param("deviceId") String deviceId);

	/**
	 * 根据orderId查询信息，以生成记录hash，供用户资产消费记录上链
	 * 
	 * @param orderId
	 * @return
	 * @since DBS V100
	 */
	@Results({ @Result(property = "orderId", column = "order_Id"), @Result(property = "softName", column = "soft_Name"),
			@Result(property = "vinCode", column = "vin_Code"),
			@Result(property = "diagStartTime", column = "diag_Start_Time"),
			@Result(property = "diagEndTime", column = "diag_End_Time"),
			@Result(property = "createTime", column = "create_Time") })
	@Select("select * from user_consume_record ucr where ucr.order_id=${orderId}")
	UserConsumerRecord queryConsumeRecordByOrderId(@Param("orderId") Integer orderId);

	/**
	 * 根据订单id查询支付记录信息，以生成记录hash，供用户资产订单上链
	 * 
	 * @param id
	 * @return
	 * @since DBS V100
	 */
	@Results({ @Result(property = "id", column = "id"), @Result(property = "userId", column = "user_Id"),
			@Result(property = "serialNo", column = "serial_No"), @Result(property = "orderNo", column = "order_No"),
			@Result(property = "price", column = "price"), @Result(property = "payFrom", column = "pay_From"),
			@Result(property = "payTime", column = "pay_Time"), @Result(property = "remark", column = "remark"),
			@Result(property = "status", column = "status"), @Result(property = "createTime", column = "create_Time"),
			@Result(property = "thirdTradeNo", column = "third_Trade_No") })
	@Select("select * from user_order uo where uo.id=${id}")
	UserOrder queryUserOrderById(@Param("id") Integer id);

	/**
	 * 
	 * @param czlOperateLog
	 */
	@Insert({
			"insert into czl_operate_log(record_type,record_id,json_data,create_date)values(#{recordType},#{recordId},#{jsonDate},NOW())" })
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	void saveCzlOperateLog(CzlOperateLog czlOperateLog);

	/**
	 * 
	 * @param czlOperateLog
	 * @return
	 */
	@Results({ @Result(property = "recordId", column = "record_id"),
			@Result(property = "id", column = "id"),
			@Result(property = "recordType", column = "record_type"),
			@Result(property = "jsonDate", column = "json_data"),
			@Result(property = "createDate", column = "create_date") })
	@Select("select * from czl_operate_log col where col.id=${id}")
	CzlOperateLog queryCzlOperateLog(CzlOperateLog czlOperateLog);

}
