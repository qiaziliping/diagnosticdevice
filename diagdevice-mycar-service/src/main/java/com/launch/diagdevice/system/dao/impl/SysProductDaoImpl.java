package com.launch.diagdevice.system.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.launch.diagdevice.system.dao.SysProductDao;
import com.launch.diagdevice.system.dao.mapper.SysProductMapper;
import com.launch.diagdevice.system.model.SysProduct;

@Repository
public class SysProductDaoImpl implements SysProductDao {
	@Autowired
	private SysProductMapper mapper;

	@Override
	public SysProduct getProcuctBySerialNo(String serialNo) {
		return mapper.getProcuctBySerialNo(serialNo);
	}

	@Override
	public SysProduct getProductBySerialNoCC(String serialNo, String cc) {
		return mapper.getProductBySerialNoCC(serialNo, cc);
	}

	@Override
	public ArrayList<String> filterSerialNoByVenderOrFiliale(Integer venderId, Integer filialeId,
			List<String> serialNoList) {
		return mapper.filterSerialNoByVenderOrFiliale(venderId, filialeId, serialNoList);
	}
}
