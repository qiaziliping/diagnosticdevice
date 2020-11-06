package com.launch.diagdevice.service;

import com.launch.diagdevice.common.service.BaseService;
import com.launch.diagdevice.entity.UserOrder;

/**
 * 用户订单service
 * <p>
 * TODO
 * <p>
 * @author LIPING
 * @version 0.0.1
 * @since 2018年9月27日
 */
public interface UserOrderService extends BaseService<UserOrder> {

	/**
	 * 保存订单以及订单详情
	 * @param uOrder
	 * @param diagSoftPriceId
	 * @return
	 */
	int saveOrderAndDetail(UserOrder uOrder, long diagSoftPriceId);

//	PagingData<ConsumerRecord> selectPage(ConsumerRecord model);

//	Integer batchUpdateStatus(Long userId, List<String> listIds);
	/**
	 * 
	 * 获取设备列表
	 * @return 返回消费记录VO对象，包括用户名
	 * LIPING
	 */
//	PagingData<ConsumerRecordVo> selectPage(ConsumerRecordRequest crRequest);

}
