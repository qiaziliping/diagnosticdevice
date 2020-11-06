package com.launch.diagdevice.system.dao;

import java.util.List;

import com.launch.diagdevice.system.vo.DiagSoftInfo;

public interface DiagSoftInfoDao
{
    /***
     * 根据产品类型id查询软件信息列表
     * @param pdtTypeId
     * @param lanId
     * @return
     * @since DBS V100
     */

    public List<DiagSoftInfo> queryDiagSoftInfoList(Integer pdtTypeId,Integer lanId);

	public List<Integer> selectSoftIDListBySerialNo(String serialNo);

}
