package com.launch.diagdevice.system.dao.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.launch.diagdevice.system.model.SysProduct;

//@Mapper
public interface SysProductMapper {
	@Select("select sp.*,spt.pdt_type from SYS_PRODUCT sp,SYS_PDT_TYPE spt where sp.pdt_type_id=spt.pdt_type_id and SERIAL_NO = #{serialNo} ")
	@Results({ @Result(property = "productId", column = "PRODUCT_ID"),
			@Result(property = "createTime", column = "CREATE_TIME"),
			@Result(property = "filialeId", column = "FILIALE_ID"),
			@Result(property = "pdtState", column = "PDT_STATE"),
			@Result(property = "productNo", column = "PRODUCT_NO"), @Result(property = "regTime", column = "REG_TIME"),
			@Result(property = "remark", column = "REMARK"), @Result(property = "saleTime", column = "SALE_TIME"),
			@Result(property = "serialNo", column = "SERIAL_NO"),
			@Result(property = "updateTime", column = "UPDATE_TIME"), @Result(property = "userId", column = "USER_ID"),
			@Result(property = "venderId", column = "VENDER_ID"),
			@Result(property = "pdtTypeId", column = "PDT_TYPE_ID"),
			@Result(property = "softConfId", column = "SOFT_CONF_ID"),
			@Result(property = "originConfId", column = "ORIGIN_CONF_ID"),
			@Result(property = "password", column = "PASSWORD"),
			@Result(property = "isInnerProduct", column = "IS_INNER_PRODUCT"),
			@Result(property = "isPassWordFlag", column = "IS_PASSWORD_FLAG"),
			@Result(property = "orderNo", column = "ORDER_NO"),
			@Result(property = "productPart", column = "PRODUCT_PART"),
			@Result(property = "upgradeFlag", column = "UPGRADEFLAG"),
			@Result(property = "downloadAreaId", column = "DOWNLOAD_AREA_ID"),
			@Result(property = "countryId", column = "COUNTRY_ID"),
			@Result(property = "downloadFlag", column = "DOWNLOAD_FLAG"),
			@Result(property = "pdtType", column = "pdt_type")})
	public SysProduct getProcuctBySerialNo(@Param("serialNo") String serialNo);

	@Select("select t.* from sys_product t join sys_user u on u.user_id = t.user_id where t.serial_no = #{serialNo} and u.cc=#{cc}")
	@Results({ @Result(property = "productId", column = "PRODUCT_ID"),
			@Result(property = "createTime", column = "CREATE_TIME"),
			@Result(property = "filialeId", column = "FILIALE_ID"),
			@Result(property = "pdtState", column = "PDT_STATE"),
			@Result(property = "productNo", column = "PRODUCT_NO"), @Result(property = "regTime", column = "REG_TIME"),
			@Result(property = "remark", column = "REMARK"), @Result(property = "saleTime", column = "SALE_TIME"),
			@Result(property = "serialNo", column = "SERIAL_NO"),
			@Result(property = "updateTime", column = "UPDATE_TIME"), @Result(property = "userId", column = "USER_ID"),
			@Result(property = "venderId", column = "VENDER_ID"),
			@Result(property = "pdtTypeId", column = "PDT_TYPE_ID"),
			@Result(property = "softConfId", column = "SOFT_CONF_ID"),
			@Result(property = "originConfId", column = "ORIGIN_CONF_ID"),
			@Result(property = "password", column = "PASSWORD"),
			@Result(property = "isInnerProduct", column = "IS_INNER_PRODUCT"),
			@Result(property = "isPassWordFlag", column = "IS_PASSWORD_FLAG"),
			@Result(property = "orderNo", column = "ORDER_NO"),
			@Result(property = "productPart", column = "PRODUCT_PART"),
			@Result(property = "upgradeFlag", column = "UPGRADEFLAG"),
			@Result(property = "downloadAreaId", column = "DOWNLOAD_AREA_ID"),
			@Result(property = "countryId", column = "COUNTRY_ID"),
			@Result(property = "downloadFlag", column = "DOWNLOAD_FLAG") })
	public SysProduct getProductBySerialNoCC(@Param("serialNo") String serialNo, @Param("cc") String cc);

	@Select("<script>" + "select sp.serial_no from sys_product sp where sp.serial_no in"
			+"<foreach collection=\"serialNoList\" index=\"index\" item=\"item\" open=\"(\" separator=\",\" close=\")\"> '${item}' </foreach>"
			+ "<if test='venderId!=null'>" + "and sp.vender_id = #{venderId}" + "</if>" + "<if test='filialeId!=null'>"
			+ "and sp.filiale_id = #{filialeId}" + "</if>" + "</script>")
	public ArrayList<String> filterSerialNoByVenderOrFiliale(@Param("venderId") Integer venderId,
			@Param("filialeId") Integer filialeId, @Param("serialNoList") List<String> serialNoList);
}
