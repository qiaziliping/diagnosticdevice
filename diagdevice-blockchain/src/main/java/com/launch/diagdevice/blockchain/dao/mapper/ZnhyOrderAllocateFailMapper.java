package com.launch.diagdevice.blockchain.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.launch.diagdevice.blockchain.model.ZnhyOrderAllocateFail;

public interface ZnhyOrderAllocateFailMapper {
	/**
	 * 保存分账失败信息
	 * 
	 * @param za
	 */
	@Insert("INSERT INTO znhy_order_allocate_fail (order_id, serial_no, code, message,success,create_time) VALUES (#{orderId},#{serialNo},${code},#{message},#{success},#{createTime})")
	void saveZnhyOrderAllocateFail(ZnhyOrderAllocateFail zaf);

	/**
	 * 更新分账失败信息
	 * 
	 * @param zaf
	 */
	@Update("<script>"
			+ "update znhy_order_allocate_fail "
			+ "<set>"
				+ "<if test='code != null'>"
					+ " code=#{code} ,"
				+ "</if>"
				+ "<if test='message != null'>"
					+ " message=#{message} ,"
				+ "</if>"
				+ "<if test='success != null'>"
					+ " success=#{success},"
				+ "</if>"
				+ " create_time=now() "
			+ "</set>"
			+ " where order_id=#{orderId}"
			+ "</script>"
			)
	void updateZnhyOrderAllocateFail(ZnhyOrderAllocateFail zaf);

	/**
	 * 用订单号查询分账失败信息
	 * 
	 * @param za
	 * @return
	 */
	@Results({ @Result(property = "orderId", column = "order_Id"),
		@Result(property = "serialNo", column = "serial_no"),
		@Result(property = "code", column = "code"),
		@Result(property = "message", column = "message"),
		@Result(property = "success", column = "success"),
		@Result(property = "createTime", column = "create_time")})
@Select("select * from znhy_order_allocate_fail zar where order_id=#{orderId}")
	ZnhyOrderAllocateFail getZnhyOrderAllocateFail(int orderId);

	/**
	 * 条件查询分账列表
	 * 
	 * @param za
	 * @return
	 */
//	@Results({ @Result(property = "jobGroupId", column = "job_Group_Id"),
//		@Result(property = "accountId", column = "account_Id") })
//@Select("select * from znhy_allocation_rule zar where job_group_id=#{jobGroupId}")
	List<ZnhyOrderAllocateFail> queryZnhyOrderAllocateFail(ZnhyOrderAllocateFail zaf);
}
