package com.launch.diagdevice.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import com.alibaba.dubbo.config.annotation.Service;
import com.launch.diagdevice.system.dao.DiagSoftInfoDao;
import com.launch.diagdevice.system.service.DiagSoftInfoManager;
import com.launch.diagdevice.system.vo.DiagSoftInfo;

@Service(interfaceClass = DiagSoftInfoManager.class)
public class DiagSoftInfoManagerImpl implements DiagSoftInfoManager
{
    @Autowired
    private DiagSoftInfoDao diagSoftInfoDao;


    @Override
    @Cacheable(cacheNames = { "diagdevice_mycar" }, keyGenerator = "cacheKeyGenerator")
    public List<DiagSoftInfo> queryDiagSoftInfoList(Integer pdtTypeId, Integer lanId)
    {

        return diagSoftInfoDao.queryDiagSoftInfoList(pdtTypeId, lanId);
    }


	@Override
	public List<Integer> selectSoftIDListBySerialNo(String serialNo) {
		return diagSoftInfoDao.selectSoftIDListBySerialNo(serialNo);
	}

}
