package com.launch.diagdevice.blockchain.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.launch.diagdevice.blockchain.model.UserOrder;

public interface ZnhyOrderAllocationMapper {

    /**
     * 根据订单id查询支付记录信息，以生成记录hash，供用户资产订单上链
     * @param id
     * @return
     * @since DBS V100
     */
    @Results({ @Result(property = "id", column = "id"),  @Result(property = "serialNo", column = "serial_No"),
        @Result(property = "orderNo", column = "order_No"), @Result(property = "price", column = "price"), @Result(property = "payFrom", column = "pay_From"),
        @Result(property = "payTime", column = "pay_Time"), @Result(property = "thirdTradeNo", column = "third_Trade_No") })
    @Select("select * from user_order uo where uo.id=${id}")
    UserOrder queryUserOrderById(@Param("id")Integer id);

}
