package com.launch.diagdevice.system.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.launch.diagdevice.system.dao.DiagSoftInfoDao;
import com.launch.diagdevice.system.dao.mapper.DiagSoftInfoMapper;
import com.launch.diagdevice.system.vo.DiagSoftInfo;

@Repository
public class DiagSoftInfoDaoImpl implements DiagSoftInfoDao
{
    @Autowired
    private DiagSoftInfoMapper mapper;

    @Override
    public List<DiagSoftInfo> queryDiagSoftInfoList(Integer pdtTypeId, Integer lanId)
    {
        return mapper.queryDiagSoftInfoList(pdtTypeId, lanId);
    }

	@Override
	public List<Integer> selectSoftIDListBySerialNo(String serialNo) {
		List<Integer> list = new ArrayList<Integer>();
		if (StringUtils.isEmpty(serialNo)) {
			return list;
		}
		List<Integer> temp = mapper.selectSoftIDListBySerialNo(serialNo);
		return (null == temp) ? list : temp;
	}

}
