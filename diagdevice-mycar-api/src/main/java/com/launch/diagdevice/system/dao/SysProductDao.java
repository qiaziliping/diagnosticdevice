package com.launch.diagdevice.system.dao;

import java.util.ArrayList;
import java.util.List;

import com.launch.diagdevice.system.model.SysProduct;

public interface SysProductDao {
	/**
	 * 
	 * @param serialNo
	 * @return
	 */
	public SysProduct getProcuctBySerialNo(String serialNo);

	/**
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
}
