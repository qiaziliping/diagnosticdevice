package com.launch.diagdevice.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.core.type.TypeReference;
import com.launch.diagdevice.blockchain.model.ZnhyAccount;
import com.launch.diagdevice.blockchain.model.ZnhyAllocation;
import com.launch.diagdevice.blockchain.model.ZnhyAllocationGroup;
import com.launch.diagdevice.blockchain.model.ZnhyAllocationRule;
import com.launch.diagdevice.blockchain.service.ZnhyService;
import com.launch.diagdevice.blockchain.vo.ZnhyAccountVo;
import com.launch.diagdevice.blockchain.vo.ZnhyAllocationVo;
import com.launch.diagdevice.common.constant.AppCodeConstant;
import com.launch.diagdevice.common.constant.Constants;
import com.launch.diagdevice.common.model.PagingData;
import com.launch.diagdevice.common.model.PagingEntity;
import com.launch.diagdevice.common.result.AppListResult;
import com.launch.diagdevice.common.result.AppResult;
import com.launch.diagdevice.common.util.DateUtils;
import com.launch.diagdevice.common.util.JsonHelper;
import com.launch.diagdevice.system.model.SysProduct;
import com.launch.diagdevice.system.service.KeyValueExpandManager;
import com.launch.diagdevice.system.service.SysProductManager;

/**
 * 智能合约控制类
 * @author LIPING
 *
 */
@RestController
@RequestMapping("/znhy")
public class ZnhyController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/** 分账需要用到的，默认为0 */
	private static int DEFAULT_ASSETS_TYPE = 0;
	
	
	@Reference(interfaceClass=ZnhyService.class)
	private ZnhyService znhyService;
	
	@Reference(interfaceClass = SysProductManager.class)
	private SysProductManager sysProductManager;
	@Reference(interfaceClass = KeyValueExpandManager.class)
	private KeyValueExpandManager keyValueExpandManager;
	
	/**
	 * 分页获取分配列表
	 * LIPING
	 */
	@RequestMapping(value = "/getZnhyAllocationPage", method = { RequestMethod.GET })
	public @ResponseBody String getZnhyAllocationPage(@RequestParam int pageNum, @RequestParam int pageSize, @Valid ZnhyAllocation allocation) {
		AppListResult appResult = new AppListResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------getZnhyAllocationPage request param>:[pageNum={},pageSize={}]", pageNum, pageSize);
		logger.info("------getZnhyAllocationPage request>:[allocation={}]", allocation);
		try {

			PagingEntity page = new PagingEntity();
			page.setPageNum(pageNum);
			page.setPageSize(pageSize);
			
			PagingData<ZnhyAllocationVo> pageData = znhyService.selectZnhyAllocationVoPage(page,allocation);
			
			List<ZnhyAllocationVo> list = pageData.getRows();
			for (ZnhyAllocationVo vo : list) {
				String creatorId = vo.getCreatorId();
				ZnhyAccount znhyAccount = znhyService.getZnhyAccountByAccountId(creatorId);
				vo.setAccountName(znhyAccount.getName());
			}
			appResult.setData(list);
			appResult.setCount(pageData.getTotal());
			helper.filter(ZnhyAllocationVo.class, "id,jobGroupId,groupName,name,allocationId,createDate,updateDate,accountName,creatorId", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------device getZnhyAllocationPage error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	/**
	 * 分页获取分配列表
	 * LIPING
	 */
	@RequestMapping(value = "/getZnhyAllocationDetail/{id}", method = { RequestMethod.GET })
	public @ResponseBody String getZnhyAllocationDetail(@PathVariable int id) {
		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------getZnhyAllocationDetail request param>:[id={}]", id);
		try {
			ZnhyAllocation allocation = new ZnhyAllocation();
			allocation.setId(id);
			allocation = znhyService.queryZnhyAllocation(allocation);
			
			if (null != allocation) {
				Integer jobGroupId = allocation.getJobGroupId();
				
				ZnhyAllocationGroup group = znhyService.getAllocationGroup(jobGroupId);
				
				ZnhyAllocationVo vo = new ZnhyAllocationVo();
				BeanUtils.copyProperties(allocation, vo);
				vo.setGroupName(group.getGroupName());
				appResult.setData(vo);
				helper.filter(ZnhyAllocationVo.class, null, "creatorId,assetsType");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------device getZnhyAllocationDetail error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	/**
	 * 添加智能合约分配
	 * @param creatorId 是账户表的accountID（钱包地址id），需要选择一个当做一个拥有者
	 * @param jobGroupId  分账组id
	 * @param name 设备的唯一字符串编码(设备UID)
	 * LIPING
	 */
	@RequestMapping(value = "/saveZnhyAllocation/{creatorId}/{jobGroupId}/{name}", method = { RequestMethod.POST })
	public @ResponseBody String saveZnhyAllocation(@PathVariable String creatorId,@PathVariable int jobGroupId ,@PathVariable String name) {

		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------znhy saveZnhyAllocation [jobGroupId={},name={}]",jobGroupId,name);
		try {
			// 此name就是序列号
			SysProduct product = sysProductManager.getProcuctBySerialNo(name);
			if (null != product) {
				Integer softConfId = product.getSoftConfId();
				// 验证该序列号的产品类型是否允许添加
				if (isValid(softConfId)) {
					// 验证该序列号是否被分配
					ZnhyAllocation allocation =  new ZnhyAllocation();
					allocation.setName(name);
					allocation = znhyService.queryZnhyAllocation(allocation);
					if (null == allocation) {
						
						ZnhyAllocation allo = new ZnhyAllocation();
						allo.setJobGroupId(jobGroupId);
						allo.setName(name);
						allo.setAssetsType(DEFAULT_ASSETS_TYPE);
						// creatorId即账户表的accountID
						allo.setCreatorId(creatorId);
						
						znhyService.createAllocation(allo);
					} else {
						// 该序列号已存在分配
						appResult.setCode(AppCodeConstant.SERIAL_NO_EXIST_ALLOCATION);
						appResult.setMessage(AppCodeConstant.checkCodeOld(AppCodeConstant.SERIAL_NO_EXIST_ALLOCATION));
					}
				} else {
					// 只有指定的产品类型的序列号才可以添加
					appResult.setCode(AppCodeConstant.SERIAL_NO_ERROR);
					appResult.setMessage(AppCodeConstant.checkCodeOld(AppCodeConstant.SERIAL_NO_ERROR));
				}
			} else {
				// 该序列号不存在
				appResult.setCode(AppCodeConstant.SERIAL_NO_NOT_EXIST);
				appResult.setMessage(AppCodeConstant.checkCodeOld(AppCodeConstant.SERIAL_NO_NOT_EXIST));
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------znhy saveZnhyAllocation error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	/**
	 * 修改智能合约分配
	 * @param jobGroupId  分账组id 智能修改这一个字段
	 * LIPING
	 */
//	@RequestMapping(value = "/updateZnhyAllocation/{id}", method = { RequestMethod.POST })
	@RequestMapping(value = "/updateZnhyAllocation/{creatorId}/{id}/{jobGroupId}/{name}", method = { RequestMethod.POST })
	public @ResponseBody String updateZnhyAllocation(@PathVariable String creatorId,@PathVariable int id,@PathVariable int jobGroupId,
			@PathVariable String name) {

		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------znhy updateZnhyAllocation [creatorId={},id={},jobGroupId={},name={}]",creatorId,id,jobGroupId,name);
		try {
			// 此name就是序列号
			SysProduct product = sysProductManager.getProcuctBySerialNo(name);
			if (null != product) {
				Integer softConfId = product.getSoftConfId();
				// 验证该序列号的产品类型是否允许添加
				if (isValid(softConfId)) {
					// 验证该序列号是否被分配
					ZnhyAllocation allocation =  new ZnhyAllocation();
					allocation.setName(name);
					allocation = znhyService.queryZnhyAllocation(allocation);
					if (null == allocation || allocation.getId() == id) {
						
						ZnhyAllocation allo = new ZnhyAllocation();
						allo.setJobGroupId(jobGroupId);
						allo.setCreatorId(creatorId);
						allo.setName(name);
						allo.setId(id);
						
						znhyService.updateAllocation(allo);
						
					} else {
						// 该序列号已存在分配
						appResult.setCode(AppCodeConstant.SERIAL_NO_EXIST_ALLOCATION);
						appResult.setMessage(AppCodeConstant.checkCodeOld(AppCodeConstant.SERIAL_NO_EXIST_ALLOCATION));
					}
				} else {
					// 只有指定的产品类型的序列号才可以添加
					appResult.setCode(AppCodeConstant.SERIAL_NO_ERROR);
					appResult.setMessage(AppCodeConstant.checkCodeOld(AppCodeConstant.SERIAL_NO_ERROR));
				}
			} else {
				// 该序列号不存在
				appResult.setCode(AppCodeConstant.SERIAL_NO_NOT_EXIST);
				appResult.setMessage(AppCodeConstant.checkCodeOld(AppCodeConstant.SERIAL_NO_NOT_EXIST));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------znhy updateZnhyAllocation error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	// ---------------------------------智能合约分配规则------------------------------
	
	/**
	 * 修改分配组名和规则
	 * LIPING
	 */
	@RequestMapping(value = "/updateAllocationRule/{jobGroupId}/{groupName}", method = { RequestMethod.POST })
	public @ResponseBody String updateAllocationRule(@PathVariable int jobGroupId,@PathVariable String groupName,@Valid String ruleListStr) {

		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------znhy saveZnhyAllocationRule [groupName={}]",groupName);
		logger.info("------znhy saveZnhyAllocationRule [ruleListStr={}]",ruleListStr);
		try {
			ZnhyAllocationGroup group = new ZnhyAllocationGroup();
			group.setGroupName(groupName);
			group.setJobGroupId(jobGroupId);
			
			List<ZnhyAllocationRule> ruleList = 
					helper.fromMultiObj(ruleListStr, new TypeReference<List<ZnhyAllocationRule>>(){});
			
			znhyService.updateGroupAndRule(group,ruleList);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------znhy saveZnhyAccount error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	/**
	 * 
	 * 获取分配规则详情
	 * LIPING
	 */
	@RequestMapping(value = "/getAllocationRuleDetail/{jobGroupId}", method = { RequestMethod.GET })
	public @ResponseBody String getAllocationRuleDetail(@PathVariable int jobGroupId) {
		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------getAllocationRuleDetail request param>:[jobGroupId={}]", jobGroupId);
		try {
			Map<String,Object> resultMap = new HashMap<String,Object>();
			
			List<ZnhyAllocationRule> tempListRule = znhyService.queryAllocationRuleByGroupId(jobGroupId);
			ZnhyAllocationGroup group =  znhyService.getAllocationGroup(jobGroupId);
			
			List<Map<String,Object>> ruleList = new ArrayList<Map<String,Object>>();
			
			for (ZnhyAllocationRule rule : tempListRule) {
				// 规则对象
				Map<String,Object> ruleMap = new HashMap<String,Object>();
				// 账户对象
				Map<String,Object> accountMap = new HashMap<String,Object>();
				
				String accountId = rule.getAccountId();
				ZnhyAccount account = znhyService.getZnhyAccountByAccountId(accountId);
				
				accountMap.put("name", account.getName());
				accountMap.put("accountId", accountId);
				
				ruleMap.put("job", rule.getJob());
				
				// 页面显示整数，将小数乘以100
				BigDecimal bd = new BigDecimal(String.valueOf(rule.getRadios()));
				bd = bd.multiply(new BigDecimal(100));
				
				ruleMap.put("radios", bd.doubleValue());
				ruleMap.put("account", accountMap);
				ruleList.add(ruleMap);
			}
			
			resultMap.put("jobGroupId", jobGroupId);
			resultMap.put("groupName", group.getGroupName());
			resultMap.put("ruleList", ruleList);
			
			
			
			appResult.setData(resultMap);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------device getAllocationRuleDetail error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	/**
	 * 
	 * 获取分配组集合列表
	 * LIPING
	 */
	@RequestMapping(value = "/getAllocationGroupList", method = { RequestMethod.GET })
	public @ResponseBody String getAllocationGroupList(@RequestParam int pageNum, @RequestParam int pageSize, @Valid ZnhyAllocationGroup group) {
		AppListResult appResult = new AppListResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------getAllocationGroupList request param>:[pageNum={},pageSize={},groupName={}]", pageNum, pageSize, group.getGroupName());
		try {

			PagingEntity page = new PagingEntity();
			page.setPageNum(pageNum);
			page.setPageSize(pageSize);
			
			PagingData<ZnhyAllocationGroup> pageData = znhyService.selectAllocationGroupPage(page,group);
			
			List<ZnhyAllocationGroup> list = pageData.getRows();
			
			List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
			for (ZnhyAllocationGroup model : list) {
				Map<String, Object> tempMap = new HashMap<String,Object>();
				tempMap.put("jobGroupId", model.getJobGroupId());
				tempMap.put("groupName", model.getGroupName());
				tempMap.put("createDate", DateUtils.convertDateToString(model.getCreateDate(), DateUtils.DATETIME));
				listmap.add(tempMap);
			}
			
			appResult.setData(listmap);
			appResult.setCount(pageData.getTotal());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------device getAllocationGroupList error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	/**
	 * 获取分配组集合列表
	 * LIPING
	 */
	@RequestMapping(value = "/getAllocationGroupAll", method = { RequestMethod.GET })
	public @ResponseBody String getAllocationGroupAll(@Valid ZnhyAllocationGroup group) {
		AppListResult appResult = new AppListResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------getAllocationGroupAll groupName={}]",group.getGroupName());
		try {

			List<ZnhyAllocationGroup> list = znhyService.queryAllocationGroup(group);
			
			List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
			for (ZnhyAllocationGroup model : list) {
				Map<String, Object> tempMap = new HashMap<String,Object>();
				tempMap.put("jobGroupId", model.getJobGroupId());
				tempMap.put("groupName", model.getGroupName());
				listmap.add(tempMap);
			}
			
			appResult.setData(listmap);
			appResult.setCount((long)listmap.size());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------device getAllocationGroupList error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	/**
	 * 获取分配组集合列表
	 * LIPING
	 */
	@RequestMapping(value = "/getZnhyAccountByGroupId/{groupId}", method = { RequestMethod.GET })
	public @ResponseBody String getZnhyAccountByGroupId(@PathVariable long groupId) {
		AppListResult appResult = new AppListResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------getZnhyAccountByGroupId groupId={}]",groupId);
		try {
			
			List<Map<String,Object>> list = znhyService.getZnhyAccountByGroupId(groupId);
			
			appResult.setData(list);
			appResult.setCount((long)list.size());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------device getZnhyAccountByGroupId error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	/**
	 * 添加分账规则
	 * @param accountId  分账人钱包地址id
	 * @param groupName 分组名
	 * @param job 职位、角色
	 * @param radios 分账比例
	 * LIPING
	 */
	@RequestMapping(value = "/saveZnhyAllocationRule/{groupName}", method = { RequestMethod.POST })
	public @ResponseBody String saveZnhyAllocationRule( @PathVariable String groupName,@Valid String ruleListStr) {

		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------znhy saveZnhyAllocationRule [groupName={}]",groupName);
		logger.info("------znhy saveZnhyAllocationRule [ruleListStr={}]",ruleListStr);
		try {
			ZnhyAllocationGroup group = new ZnhyAllocationGroup();
			group.setGroupName(groupName);
			
			List<ZnhyAllocationRule> ruleList = 
					helper.fromMultiObj(ruleListStr, new TypeReference<List<ZnhyAllocationRule>>(){});
			
			
			znhyService.createGroupAndRule(group,ruleList);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------znhy saveZnhyAccount error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	//------------------------------znhyAccount-----------------------------------
	/**
	 * 
	 * 获取账户列表
	 * LIPING
	 */
	@RequestMapping(value = "/getZnhyAccountList", method = { RequestMethod.GET })
	public @ResponseBody String getZnhyAccountList(@RequestParam int pageNum, @RequestParam int pageSize, @Valid ZnhyAccount account) {
		AppListResult appResult = new AppListResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------getZnhyAccountList request param>:[pageNum={},pageSize={},account={}]", pageNum, pageSize, account);
		try {

			PagingEntity page = new PagingEntity();
			page.setPageNum(pageNum);
			page.setPageSize(pageSize);
			
			PagingData<ZnhyAccount> pageData = znhyService.selectPage(page,account);
			
			List<ZnhyAccount> list = pageData.getRows();
			List<ZnhyAccountVo> listvo = new ArrayList<ZnhyAccountVo>();
			for (ZnhyAccount model : list) {
				ZnhyAccountVo vo = new ZnhyAccountVo();
				BeanUtils.copyProperties(model, vo);
				
				if (StringUtils.isNotBlank(model.getAlipay())) {
					vo.setAccountType(Constants.ACCOUNT_NAME_ALIPAY);
					vo.setAccountName(model.getAlipay());
				}
				if (StringUtils.isNotBlank(model.getWeChat())) {
					vo.setAccountType(Constants.ACCOUNT_NAME_WECHAT);
					vo.setAccountName(model.getWeChat());
				}
				if (StringUtils.isNotBlank(model.getPaypal())) {
					vo.setAccountType(Constants.ACCOUNT_NAME_PAYPAL);
					vo.setAccountName(model.getPaypal());
				}
				if (StringUtils.isNotBlank(model.getBankCard())) {
					vo.setAccountType(Constants.ACCOUNT_NAME_BANK_CARD);
					vo.setAccountName(model.getBankCard());
				}
				
				listvo.add(vo);
			}

			appResult.setData(listvo);
			appResult.setCount(pageData.getTotal());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------device getZnhyAccountList error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	/**
	 * 获取所有的数据账户
	 */
	@RequestMapping(value = "/getZnhyAccountAll", method = { RequestMethod.GET })
	public @ResponseBody String getZnhyAccountAll(@Valid ZnhyAccount account) {
		AppListResult appResult = new AppListResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------getZnhyAccountAll request param>:[account={}]", account);
		try {

			List<ZnhyAccount> list = znhyService.queryZnhyAccount(account);
			
			appResult.setData(list);
			appResult.setCount(list != null ? list.size() : 0L);
			helper.filter(ZnhyAccount.class, "id,name,accountId", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------device getZnhyAccountList error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		
		return helper.toJsonStr(appResult);
	}
	
	/**
	 * 添加设备
	 * @param name  分账人姓名（必须跟支付宝实名认证的名字一致）
	 * @param bankCard 分账人银行卡（银行卡收款）
	 * @param weChat 分账人微信openID(微信收款)
	 * @param alipay 分账人支付宝账号（支付宝收款）
	 * @param telephone 分账人手机号码
	 * LIPING
	 */
//	@RequestMapping(value = "/saveZnhyAccount/{alipay}/{name}/{telephone}", method = { RequestMethod.POST })
	@RequestMapping(value = "/saveZnhyAccount/{accountName}/{accountType}/{name}/{telephone}", method = { RequestMethod.POST })
	public @ResponseBody String saveZnhyAccount(@PathVariable String accountName,@PathVariable int accountType, @PathVariable String name,
			@PathVariable String telephone, @Valid ZnhyAccount zAcount) {

		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------znhy saveZnhyAccount zAcount param>:"+zAcount);
		try {
			switch (accountType) {
				case Constants.ACCOUNT_NAME_ALIPAY:
					zAcount.setAlipay(accountName);
					break;
				case Constants.ACCOUNT_NAME_WECHAT:
					zAcount.setWeChat(accountName);
					break;
				case Constants.ACCOUNT_NAME_PAYPAL:
					zAcount.setPaypal(accountName);
					break;
				case Constants.ACCOUNT_NAME_BANK_CARD:
					zAcount.setBankCard(accountName);
					break;
	
				default:
					break;
			}	
			znhyService.createAccount(zAcount);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------znhy saveZnhyAccount error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	/**
	 * 修改账户信息
	 * TODO
	 * liping
	 */
	@RequestMapping(value = "/updateZnhyAccount", method = { RequestMethod.POST })
	public @ResponseBody String updateZnhyAccount(@Valid ZnhyAccountVo zAcountvo) {

		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------znhy updateZnhyAccount zAcount param>:"+zAcountvo);
		try {
			Integer id = zAcountvo.getId();
			if (null != id) {
				znhyService.updateAccount(zAcountvo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------znhy updateZnhyAccount error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	
	/**
	 * 验证是否为PAD3产品，只有pad3才能允许操作
	 * LIPING
	 */
	private boolean isValid(Integer pdtTypeId) {
		boolean flag = false;
		
		String value = keyValueExpandManager.queryValueByKey(Constants.DIAGDEVICE_ALLOW_CONFIG_ID);
		logger.info("UserController isValid produect type config ids={}",value);
		if (org.apache.commons.lang3.StringUtils.isNotBlank(value)) {
			String [] arr = value.split(",");
			
			for (int i = 0;i<arr.length;i++) {
				String arrValue = arr[i];
				int ptId = Integer.parseInt(arrValue);
				
				if (pdtTypeId.intValue() == ptId) {
					flag = true;
					continue;
				}
			}
			
		}
		// 判断是否为PAD3产品
		return flag;
	}
	
	
	
}
