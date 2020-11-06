package com.launch.diagdevice.blockchain.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.launch.diagdevice.blockchain.model.ZnhyAllocationRule;

public interface ZnhyAllocationRuleMapper {
	/**
	 * 保存智能合约规则
	 * 
	 * @param znhyAllocationRule
	 */
	@Insert("INSERT INTO znhy_allocation_rule (job_group_id, job, radios, account_id) VALUES (#{jobGroupId},#{job},${radios},#{accountId})")
	void saveZnhyAllocationRule(ZnhyAllocationRule znhyAllocationRule);

	/**
	 * 更新智能合约规则
	 * 
	 * @param znhyAllocationRule
	 */
	@Update("delete from znhy_allocation_rule where job_group_id=#{jobGroupId}")
	void deleteZnhyAllocationRule(int jobGroupId);

	/**
	 * 查询具体的分配组
	 * 
	 * @param jobGroupId
	 * @return
	 */
	@Results({ @Result(property = "jobGroupId", column = "job_Group_Id"),
			@Result(property = "accountId", column = "account_Id") })
	@Select("select * from znhy_allocation_rule zar where job_group_id=#{jobGroupId}")
	List<ZnhyAllocationRule> queryAllocationRuleByGroupId(int jobGroupId);

	
	@Update("<script>"
			+ "update znhy_allocation_rule za "
			+ "<set>"
				+ "<if test='job != null'>"
					+ " za.job=#{job} ,"
				+ "</if>"
				+ "<if test='radios != null'>"
					+ " za.radios=#{radios} ,"
				+ "</if>"	
				+ "<if test='accountId != null'>"
					+ " za.account_id=#{accountId} "
				+ "</if>"	
			+ "</set>"
			+ " where za.job_group_id=#{jobGroupId}"
			+ "</script>"
			)
	int updateAllocationRule(ZnhyAllocationRule rule);

}
