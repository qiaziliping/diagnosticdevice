package com.launch.diagdevice.blockchain.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.launch.diagdevice.blockchain.model.ZnhyAccount;
import com.launch.diagdevice.blockchain.vo.ZnhyAccountVo;

public interface ZnhyAccountMapper {

	/**
	 * 保存智能合约账号信息
	 * 
	 * @param za
	 */
	@Insert({
			"insert into znhy_account(name,bank_card,we_chat,alipay,paypal,telephone,email,create_date,creator)values(#{name},#{bankCard},#{weChat},#{alipay},#{paypal},#{telephone},#{email},NOW(),#{creator})" })
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	void saveZnhyAccount(ZnhyAccount za);

	/**
	 * 更新智能合约账号
	 * 
	 * @param za
	 */
//	@Update("update znhy_account za set za.name=#{name},za.bank_card=#{bankCard},za.we_chat=#{weChat},za.alipay=#{alipay},za.email=#{email},za.account_id=#{accountId},za.updator=#{updator},za.update_date=now() where za.id=#{id}")
	@Update("<script>"
			+ "update znhy_account za "
			+ "<set>"
				+ "<if test='name != null'>"
					+ " za.name=#{name} ,"
				+ "</if>"
				+ "<if test='bankCard != null'>"
					+ " za.bank_card=#{bankCard} ,"
				+ "</if>"	
				+ "<if test='weChat != null'>"
					+ " za.we_chat=#{weChat} ,"
				+ "</if>"	
				+ "<if test='alipay != null'>"
					+ " za.alipay=#{alipay} ,"
				+ "</if>"
				+ "<if test='paypal != null'>"
					+ " za.paypal=#{paypal} ,"
				+ "</if>"
				+ "<if test='email != null'>"
					+ " za.email=#{email},"
				+ "</if>"
				+ "<if test='accountId != null'>"
					+ " za.account_id=#{accountId},"
				+ "</if>"
				+ "<if test='telephone != null'>"
					+ " za.telephone=#{telephone},"
				+ "</if>"
				+ "<if test='updator != null'>"
					+ " za.updator=#{updator},"
				+ "</if>"
				+ " za.update_date=now() "
			+ "</set>"
			+ " where za.id=#{id}"
			+ "</script>"
			)
	void updateZnhyAccount(ZnhyAccount za);
	
	@Update("<script>"
			+ "update znhy_account za "
			+ "<set>"
				+ "<if test='accountTypeOld == 1'>"
					+ " za.alipay= null ,"
				+ "</if>"
				+ "<if test='accountTypeOld == 2'>"
					+ " za.we_chat= null ,"
				+ "</if>"
				+ "<if test='accountTypeOld == 3'>"
					+ " za.paypal= null ,"
				+ "</if>"
				+ "<if test='accountTypeOld == 4'>"
					+ " za.bank_card= null ,"
				+ "</if>"
				+ " za.update_date=now() "	
			+ "</set>"
			+ " where za.id=#{id}"
			+ "</script>"
	)
	void updateAccountNullByAccountTypeOld(ZnhyAccountVo za);

	/**
	 * 查询智能合约账号信息
	 * 
	 * @param id
	 * @return
	 */
	@Results({ @Result(property = "bankCard", column = "bank_Card"),
		@Result(property = "weChat", column = "we_Chat"),
		@Result(property = "alipay", column = "alipay"),
		@Result(property = "paypal", column = "paypal"),
		@Result(property = "name", column = "name"),
		@Result(property = "telephone", column = "telephone"),
		@Result(property = "email", column = "email"),
		@Result(property = "id", column = "id"),
		@Result(property = "assetsType", column = "assets_Type"),
		@Result(property = "accountId", column = "account_id"),
		@Result(property = "createDate", column = "create_Date"),
		@Result(property = "creator", column = "creator"),
		@Result(property = "updator", column = "updator"),
		@Result(property = "updateDate", column = "update_Date") })
	@Select("select * from znhy_account za where za.id = #{id}")
	ZnhyAccount getZnhyAccount(int id);

	/**
	 * 查询智能合约账号信息根据智能合约系统分配的账号id
	 * 
	 * @param id
	 * @return
	 */
	@Results({ @Result(property = "bankCard", column = "bank_Card"),
			@Result(property = "weChat", column = "we_Chat"),
			@Result(property = "alipay", column = "alipay"),
			@Result(property = "paypal", column = "paypal"),
			@Result(property = "name", column = "name"),
			@Result(property = "telephone", column = "telephone"),
			@Result(property = "email", column = "email"),
			@Result(property = "id", column = "id"),
			@Result(property = "accountId", column = "account_id"),
			@Result(property = "createDate", column = "create_Date"),
			@Result(property = "creator", column = "creator"),
			@Result(property = "updator", column = "updator"),
			@Result(property = "updateDate", column = "update_Date") })
	@Select("select * from znhy_account za where za.account_id = #{accountId}")
	ZnhyAccount getZnhyAccountByaccountId(String accountId);

	/**
	 * 条件查询智能合约账号
	 * 
	 * @param za
	 * @return
	 */
	@Results({ @Result(property = "bankCard", column = "bank_Card"),
		@Result(property = "weChat", column = "we_Chat"),
		@Result(property = "alipay", column = "alipay"),
		@Result(property = "paypal", column = "paypal"),
		@Result(property = "name", column = "name"),
		@Result(property = "telephone", column = "telephone"),
		@Result(property = "email", column = "email"),
		@Result(property = "id", column = "id"),
		@Result(property = "accountId", column = "account_id"),
		@Result(property = "createDate", column = "create_Date"),
		@Result(property = "creator", column = "creator"),
		@Result(property = "updator", column = "updator"),
		@Result(property = "updateDate", column = "update_Date") })
	@Select("<script>" + "select *  from znhy_account za where 1=1 " + "<if test='id!=null'>" + "and za.id = #{id}"
			+ "</if>" + "<if test='name!=null and name!=\"\" '>" + "and za.name = #{name}" + "</if>"
			+ "<if test='telephone!=null and telephone!=\"\" '>" + "and za.telephone = #{telephone}" + "</if>"
			+ " ORDER BY id DESC"
			+ "</script>")
	List<ZnhyAccount> queryZnhyAccount(ZnhyAccount za);

	
	

}
