package com.launch.diagdevice.blockchain.dao;

import com.launch.diagdevice.blockchain.model.UserOrder;

public interface ZnhyOrderAllocationDao
{
    /**
     * 根据订单id查询支付记录信息
     * @param orderId
     * @return
     * @since DBS V100
     */
    UserOrder queryUserOrderByOrderId(Integer orderId);
}
