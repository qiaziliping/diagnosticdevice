package com.launch.diagdevice.system.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.launch.diagdevice.system.vo.DiagSoftInfo;

public interface DiagSoftInfoMapper
{
    @Select("select ds.soft_id,ds.valid_flag,ds.pdt_type_id,ds.soft_name,dsd.soft_name as soft_name_desc,saa.soft_applicable_area_name from diag_soft ds,diag_soft_desc dsd,soft_applicable_area saa where ds.soft_id=dsd.soft_id and dsd.lan_id=#{lanId} and ds.soft_applicable_area_id=saa.soft_applicable_area_id and ds.pdt_type_id=#{pdtTypeId}")
    @Results({ @Result(property = "softId", column = "soft_id"), @Result(property = "validFlag", column = "valid_flag"), @Result(property = "pdtTypeId", column = "pdt_type_id"),
        @Result(property = "softName", column = "soft_name"), @Result(property = "softNameDesc", column = "soft_name_desc"),
        @Result(property = "softApplicableArea", column = "soft_applicable_area_name") })
    public List<DiagSoftInfo> queryDiagSoftInfoList(@Param("pdtTypeId")Integer pdtTypeId, @Param("lanId")Integer lanId);

    
    @Select("SELECT soft_id FROM diag_user_soft_center WHERE serial_no = #{serialNo}")
    @Results(
    		@Result(property = "softId", column = "soft_id")
    )
	public List<Integer> selectSoftIDListBySerialNo(@Param("serialNo") String serialNo);

}
