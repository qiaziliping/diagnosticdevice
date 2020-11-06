package com.launch.diagdevice.system.service;

import java.util.ArrayList;
import java.util.List;

import com.launch.diagdevice.system.model.SysProduct;
import com.launch.diagdevice.system.vo.UserLoginInfo;

/**
 * 
 * 产品service层接口（前后台）
 * 
 * @author zhaofeng
 * @version 1.0 Feb 17, 2012
 * @since DBS V100
 */
public interface SysProductManager {
	/**
	 * 根据用户输入的序列号查询产品
	 * 
	 * @param serialNo
	 * @return
	 * @since DBS V100
	 */
	public SysProduct getProcuctBySerialNo(String serialNo);

	/**
	 * 检查序列号归属
	 * 
	 * @param serialNo
	 * @param cc
	 * @return
	 */
	public SysProduct getProductBySerialNoCC(String serialNo, String cc);

	/**
	 * 根据分公司或经销过滤属于该分公司或经销的序列号
	 * 
	 * @param venderId
	 * @param filialeId
	 * @param serialNoList
	 * @return
	 */
	public ArrayList<String> filterSerialNoByVenderOrFiliale(Integer venderId, Integer filialeId,
			List<String> serialNoList);
	
	public UserLoginInfo getProductUserLoginInfoBySerialNo(String serialNo) throws Exception;
}