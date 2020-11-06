package com.launch.diagdevice.controller;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * 跳转到视图的controller
 * 因为thymeleaf模板页面不能直接访问
 * @since 2018年12月14日
 */
@Controller
@RequestMapping("/view")
public class ViewController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	//---------------------------------------系统管理start--------------------------------------
	@GetMapping(value="/goSysUserList")
	public String goSysUserList() {
		return "layui/sys_user";
	}
	@GetMapping(value="/goSysAuthorList")
	public String goSysAuthorList() {
		return "layui/sys_author";
	}
	@GetMapping(value="/goSysRoleList")
	public String goSysRoleList() {
		return "layui/sys_role";
	}
	/** 
	 * 角色授权页面  
	 * @param id 角色主键ID
	 * @param redirect 跳转页面地址来源
	 */
	@GetMapping(value="/goRoleAuthorEdit")
	public String goRoleAuthorEdit(Model model,Long id,String redirect,String roleName) {
		model.addAttribute("roleId", id);
		model.addAttribute("redirect", redirect);
		model.addAttribute("roleName", roleName);
		return "layui/sys_role_author";
	}
	/**
	 * 查看角色的权限
	 * @param model
	 * @param id 角色主键ID
	 * @param redirect
	 * @param roleName
	 * @return
	 */
	@GetMapping(value="/goRoleAuthorDetail")
	public String goRoleAuthorDetail(Model model,Long id,String redirect,String roleName) {
		model.addAttribute("roleId", id);
		model.addAttribute("redirect", redirect);
		model.addAttribute("roleName", roleName);
		return "layui/sys_role_author_detail";
	}
	//---------------------------------------系统管理    end--------------------------------------
	//---------------------------------------智能合约start--------------------------------------
	/** 智能合约账户列表  */
	@GetMapping(value="/goZnhyAccountList")
	public String goZnhyAccountList() {
		return "layui/znhy_account";
	}
	/** 智能合约规则组列表  */
	@GetMapping(value="/goAllocationGroupList")
	public String goAllocationGroupList() {
		return "layui/znhy_rule";
	}
	/** 跳转到智能合约规则组添加页面  */
	@GetMapping(value="/goAllocationGroupAdd")
	public String goAllocationGroupAdd(Model model,String redirect) {
		model.addAttribute("redirect", redirect);
		return "layui/znhy_rule_add";
	}
	/** 跳转到智能合约规则组[编辑]页面  */
	@GetMapping(value="/goAllocationGroupEdit")
	public String goAllocationGroupEdit(Model model,Long jobGroupId,String redirect) {
		model.addAttribute("redirect", redirect);
		model.addAttribute("jobGroupId", jobGroupId);
		return "layui/znhy_rule_edit";
	}
	/** 跳转到智能合约规则组[详情]页面  */
	@GetMapping(value="/goAllocationGroupDetail")
	public String goAllocationGroupDetail(Model model,Long jobGroupId,String redirect) {
		model.addAttribute("redirect", redirect);
		model.addAttribute("jobGroupId", jobGroupId);
		return "layui/znhy_rule_detail";
	}
	
	/** 跳转到智能合约【分配】管理页面  */
	@GetMapping(value="/goZnhyAllocationList")
	public String goZnhyAllocationList() {
		return "layui/znhy_allocation";
	}
	
	//---------------------------------------智能合约       end--------------------------------------

	/** 跳转到诊断价格页面  */
	@GetMapping(value="/goDiagPriceList")
	public String goDiagPriceList() {
		return "layui/diag_price";
	}
	
	/** 跳转到设备记录页面  */
	@GetMapping(value="/goDeviceList")
	public String goDeviceList(HttpServletResponse response) {
		response.setHeader("X-Frame-Options", "SAMEORIGIN");
		
		return "layui/device_list";
	}
	/** 跳转到设备添加页面  */
	@GetMapping(value="/goDeviceAdd")
	public String goDeviceAdd(Model model,String redirect) {
		model.addAttribute("redirect", redirect);
		return "layui/device_add";
	}
	/** 跳转到设备详情页面  */
	@GetMapping(value="/goDeviceDetail")
	public String goDeviceDetail(@RequestParam(name="id") Long deviceGroupId,@RequestParam String redirect,Model model) {
		logger.info("----goDeviceDetail--->:"+deviceGroupId);
		model.addAttribute("id", deviceGroupId);
		model.addAttribute("redirect", redirect);
		return "layui/device_detail";
	}
	
	/** 跳转到消费记录页面 */
	@GetMapping(value="/goAppConsumerList")
	public String goAppConsumerList() {
		return "layui/app_consumer";
	}
	
	/** 跳转到消费记录详情页面 */
	@GetMapping(value="/goAppConsumerDetail")
	public String goAppConsumerDetail(@RequestParam(name="orderId") Long id,String redirect, Model model) {
		logger.info("----goAppConsumerDetail--->:"+id);
		model.addAttribute("orderId", id);
		model.addAttribute("redirect", redirect);
		return "layui/app_consumer_detail";
	}
	
	/**
	 * 跳转到APP用户管理页面
	 */
	@GetMapping(value="/goAppUserList")
	public String goAppUserList() {
		return "layui/app_user";
	} 
	
	/**
	 * 首页默认加载APP用户列表页面
	 * @return
	 */
	@RequestMapping(value = "/index.html")
	public String liIndex() {
		
		//return "layui/index";
		return "layui/app_user";
	}
	
	@RequestMapping(value = "/login")
	public String login() {
		return "diag_login";
	}
}
