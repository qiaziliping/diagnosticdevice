package com.launch.diagdevice.system.service;

import java.util.List;

import com.launch.diagdevice.system.vo.DiagSoftInfo;

/**
 * 诊断软件查询业务
 * @author zhaofeng
 * @version 1.0 Feb 17, 2012
 * @since DBS V100
 */
public interface DiagSoftInfoManager
{
    /**
     * 根据产品类型id查询软件信息列表
     * @param pdtTypeId
     * @param lanId
     * @return
     * @since DBS V100
     */
    public List<DiagSoftInfo> queryDiagSoftInfoList(Integer pdtTypeId,Integer lanId);

    /**
     * 根据序列号查询对应的诊断软件ID集合
     * @param serialNo 软件序列号
     * LIPING
     */
	public List<Integer> selectSoftIDListBySerialNo(String serialNo);
}