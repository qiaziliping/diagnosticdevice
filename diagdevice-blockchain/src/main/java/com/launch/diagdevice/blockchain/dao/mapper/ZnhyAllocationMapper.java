package com.launch.diagdevice.blockchain.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.launch.diagdevice.blockchain.client.vo.CreateAllocationRequest;
import com.launch.diagdevice.blockchain.model.ZnhyAllocation;
import com.launch.diagdevice.blockchain.vo.ZnhyAllocationVo;

public interface ZnhyAllocationMapper {

	/**
	 * 保存分配表，以供后台管理和组装自动分账订单
	 * 
	 * @param znhyAllocation
	 */
	@Insert("insert into znhy_allocation(job_group_id,creator_id,name,assets_type,create_date,creator)values(#{jobGroupId},#{creatorId},#{name},#{assetsType},now(),#{creator})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	void saveZnhyAllocation(ZnhyAllocation znhyAllocation);

	/**
	 * 保存分配表，以供后台管理和组装自动分账订单
	 * 
	 * @param znhyAllocation
	 */
//	@Update("update znhy_allocation set job_group_id=#{jobGroupId},allocation_id=#{allocationId},update_date=now(),updator=#{updator} where id=#{id}")
	@Update("<script>"
			+ "update znhy_allocation "
			+ "<set>"
				+ "<if test='jobGroupId != null'>"
					+ " job_group_id=#{jobGroupId} ,"
				+ "</if>"
				+ "<if test='allocationId != null and allocationId != \"\"'>"
					+ " allocation_id=#{allocationId} ,"
				+ "</if>"
				+ "<if test='updator != null'>"
					+ " updator=#{updator},"
				+ "</if>"
				+ "<if test='creatorId != null and creatorId != \"\"'>"
					+ " creator_id=#{creatorId},"
				+ "</if>"
				+ "<if test='name != null and name != \"\"'>"
					+ " name=#{name},"
				+ "</if>"
				+ " update_date=now() "
			+ "</set>"
			+ " where id=#{id}"
			+ "</script>"
			)
	void updateZnhyAllocation(ZnhyAllocation znhyAllocation);

	/***
	 * 查询供后台修改
	 * 
	 * @param znhyAllocation
	 * @return
	 */
	@Results({ @Result(property = "jobGroupId", column = "job_Group_Id"),
		@Result(property = "creatorId", column = "creator_Id"),
		@Result(property = "assetsType", column = "assets_Type"),
		@Result(property = "allocationId", column = "allocation_Id"),
		@Result(property = "createDate", column = "create_Date"),
		@Result(property = "updateDate", column = "update_Date") })
	@Select("<script>" + "SELECT * FROM znhy_allocation za  where 1=1 "
		    + "<if test='id!=null and id!=0'>" + " and za.id=#{id}"
			+ "</if>" + "<if test='allocationId!=null and allocationId!=\"\" '>"
			+ "and za.allocation_id = #{allocationId}" + "</if>" 
			+ "<if test='name!=null and name!=\"\" '>"
			+ "and za.name = #{name}" + "</if>" +"</script>")
	ZnhyAllocation queryZnhyAllocation(ZnhyAllocation znhyAllocation);

	/***
	 * 查询供组装配置订单,待确认
	 * 
	 * @param znhyAllocation
	 * @return
	 */
	@Results({ @Result(property = "jobGroupId", column = "job_Group_Id"),
		@Result(property = "accountId", column = "account_Id") })
	@Select("select * from znhy_allocation za,zhny_allocation_group zag,znhy_allocation_rule zar")
	CreateAllocationRequest queryZnhyAllocation4Order(ZnhyAllocation znhyAllocation);

	
	
	@Results({ 
		@Result(property = "groupName", column = "group_name"),
		@Result(property = "jobGroupId", column = "job_Group_id"),
		@Result(property = "name", column = "name"),
		@Result(property = "allocationId", column = "allocation_id"),
		@Result(property = "creatorId", column = "creator_id"),
		@Result(property = "createDate", column = "create_date"),
		@Result(property = "updateDate", column = "update_date"),
	})
	@Select("<script>" 
	        + "SELECT B.group_name,A.* FROM znhy_allocation A LEFT JOIN znhy_allocation_group B "
			+ " ON A.job_group_id = B.job_group_id"
			+ " WHERE 1 = 1 "
			+ "<if test='name != null and name!=\"\" '>"
				+ " AND A.name = #{name}" 
			+ "</if>"
			+ "<if test='jobGroupId != null'>"
				+ " AND A.job_Group_Id = #{jobGroupId}" 
			+ "</if>"
		+"</script>")
	List<ZnhyAllocationVo> queryZnhyAllocationVo(ZnhyAllocation allocation);
}
