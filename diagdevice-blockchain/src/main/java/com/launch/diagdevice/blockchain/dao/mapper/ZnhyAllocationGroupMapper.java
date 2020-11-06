package com.launch.diagdevice.blockchain.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.launch.diagdevice.blockchain.model.ZnhyAllocationGroup;

public interface ZnhyAllocationGroupMapper {

	/**
	 * 数据库保存，智能合约组操作新增操作
	 * 
	 * @param znhyAllocation
	 */
	@Insert({
	"insert into znhy_allocation_group(group_name,creator,create_date)values(#{groupName},#{creator},NOW())" })
	@Options(useGeneratedKeys=true, keyProperty="jobGroupId", keyColumn="job_group_id")
	void saveAllocationGroup(ZnhyAllocationGroup allocationGroup);

	/**
	 * 数据库保存，智能合约组操作更新操作
	 * 
	 * @param znhyAllocation
	 */
	@Update("<script>"
			+ "update znhy_allocation_group zag "
			+ "<set>"
				+ "<if test='groupName != null'>"
					+ " zag.group_name=#{groupName} ,"
				+ "</if>"
				+ "<if test='updator != null'>"
					+ " zag.updator=#{updator},"
				+ "</if>"
				+ " zag.update_date=now() "
			+ "</set>"
			+ " where zag.job_group_id=#{jobGroupId}"
			+ "</script>"
			)
	void updateAllocationGroup(ZnhyAllocationGroup allocationGroup);

	/**
	 * 根据条件查询某一条分配组记录
	 * 
	 * @param allocationGroup
	 * @return
	 */
	@Results({ 
		@Result(property = "jobGroupId", column = "job_group_id"),
		@Result(property = "groupName", column = "group_name"),
		@Result(property = "alipay", column = "alipay"),
		@Result(property = "createDate", column = "create_Date"),
		@Result(property = "creator", column = "creator"),
		@Result(property = "updator", column = "updator"),
		@Result(property = "updateDate", column = "update_Date") 
	})
	@Select( "select *  from znhy_allocation_group zag where job_group_id =#{jobGroupId} ")
	ZnhyAllocationGroup getAllocationGroup(int jobGroupId);

	/**
	 * 根据条件查询分配组列表，供后台展示用
	 * 
	 * @param allocationGroup
	 * @return
	 */
	@Results({ @Result(property = "jobGroupId", column = "job_group_id"),
		@Result(property = "groupName", column = "group_name"),
		@Result(property = "alipay", column = "alipay"),
		@Result(property = "createDate", column = "create_Date"),
		@Result(property = "creator", column = "creator"),
		@Result(property = "updator", column = "updator"),
		@Result(property = "updateDate", column = "update_Date") })
	@Select("<script>" + "select *  from znhy_allocation_group zag where 1=1 " + "<if test='groupName!=null and groupName != \"\"'>"
			+ "and zag.group_name = #{groupName}" + "</if>" + "<if test='creator!=null and creator!=\"\" '>"
			+ "and za.creator = #{creator}" + "</if>" + "</script>")
	List<ZnhyAllocationGroup> queryAllocationGroup(ZnhyAllocationGroup allocationGroup);

	
	@Select( "SELECT B.account_id AS accountId,B.name  AS name "
			+" FROM znhy_allocation_rule A, znhy_account B "
			+" WHERE A.account_id = B.account_id "
			+" AND job_group_id = #{groupId} ")
	List<Map<String, Object>> getZnhyAccountByGroupId(long groupId);
	
}
