package com.launch.diagdevice.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.core.type.TypeReference;
import com.launch.diagdevice.common.constant.AppCodeConstant;
import com.launch.diagdevice.common.model.PagingData;
import com.launch.diagdevice.common.result.AppListResult;
import com.launch.diagdevice.common.result.AppResult;
import com.launch.diagdevice.common.util.DateUtils;
import com.launch.diagdevice.common.util.JsonHelper;
import com.launch.diagdevice.entity.SysUser;
import com.launch.diagdevice.entity.vo.SysUserRoleVo;
import com.launch.diagdevice.entity.vo.SysUserVo;
import com.launch.diagdevice.service.SysUserRoleService;
import com.launch.diagdevice.service.SysUserService;

/**
 * 系统用户控制类
 * @author LIPING
 * @version 0.0.1
 * @since 2018年10月29日
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

	
	@Reference(interfaceClass=SysUserService.class)
	private SysUserService sysUserService;
	
	@Reference(interfaceClass=SysUserRoleService.class)
	private SysUserRoleService sysUserRoleService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * 判断用户名是否存在
	 * @param id 主键ID
	 * LIPING
	 */
	@RequestMapping(value = "/verifyUsernameIsexist/{username}", method = { RequestMethod.GET })
	public @ResponseBody String verifyUsernameIsexist(@PathVariable String username) {

		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------verifyUsernameIsexist request param>:[username={}]", username);
		try {
			
			SysUser model = new SysUser();
			model.setUsername(username);
			
			SysUser sysuser = sysUserService.selectOne(model);
			
			appResult.setData(sysuser);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------verifyUsernameIsexist error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	/**
	 * 
	 * 分页获取系统用户列表
	 * LIPING
	 */
	@RequestMapping(value = "/getSysUserPage", method = { RequestMethod.GET })
	public @ResponseBody String getSysUserPage(@RequestParam int pageNum, @RequestParam int pageSize, @Valid SysUser sysUser) {
		AppListResult appResult = new AppListResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------getSysUserPage request param>:[pageNum={},pageSize={},username={}]", pageNum, pageSize,
				sysUser.getUsername());
		try {

			PagingData<SysUserVo> pageData = sysUserService.selectPage(sysUser);
			
			List<SysUserVo> listvo = pageData.getRows();

			appResult.setData(listvo);
			appResult.setCount(pageData.getTotal());
			helper.filter(SysUserVo.class, "id,username,mobile,email,urVolist,createTime,remark,password", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------getSysUserPage error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	
	
	/**
	 * 查询菜单详情
	 * @param id 主键ID
	 * LIPING
	 */
	@RequestMapping(value = "/getSysUserDetail/{id}", method = { RequestMethod.GET })
	public @ResponseBody String getSysUserDetail(@PathVariable Long id) {

		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------getSysUserDetail request param>:[id={}]", id);
		try {

			Map<String,Object> rstMap = new HashMap<String,Object>();
			SysUser sysUser = sysUserService.selectById(id);
			
			rstMap.put("email", sysUser.getEmail());
			rstMap.put("mobile", sysUser.getMobile());
			rstMap.put("remark", sysUser.getRemark());
			rstMap.put("createTime", DateUtils.convertDateToString(sysUser.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
			rstMap.put("username", sysUser.getUsername());
			
			
			
			Map<String,Object> condition = new HashMap<String,Object>();
			condition.put("userId", id);
			List<SysUserRoleVo> urListVo = sysUserRoleService.selectVoByCondition(condition);
			
			// 角色集合
			List<Map<String,Object>> roleList = new ArrayList<Map<String,Object>>();
			for (SysUserRoleVo vo : urListVo) {
				Map<String,Object> roleMap = new HashMap<String,Object>();
				roleMap.put("roleId", vo.getRoleId());
				roleMap.put("roleName", vo.getRoleName());
				roleList.add(roleMap);
			}
			
			rstMap.put("roleList", roleList);
			
			appResult.setData(rstMap);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------getSysUserDetail error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}

	/**
	 * 删除用户和用户角色关联
	 * @param id 主键
	 * LIPING
	 */
	@RequestMapping(value = "/delete/{id}", method = { RequestMethod.POST })
	public @ResponseBody String delete(@PathVariable Long id) {

		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------sysUser delete request param>:[id={}]",id);
		try {
			
			sysUserService.deleteUserAndUR(id);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------sysUser delete error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	
	/**
	 * 修改用户
	 * roleIds 角色id集合
	 * @param email 邮箱
	 * @param mobile 手机号
	 * @param password 密码
	 * @param remark 备注
	 * @param username 用户名
	 * @param isUpdateRole true表示需要修改角色，false不需要
	 * LIPING
	 */
	@RequestMapping(value = "/update/{id}", method = { RequestMethod.POST })
	public @ResponseBody String update(@PathVariable Long id,SysUser sysUser,@RequestParam String roleIds,@RequestParam Boolean isUpdateRole) {

		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------sysRole update request param>:[id={},sysRole={},roleIds={}]",id, sysUser,roleIds);
		try {
			
			List<String> listIds = helper.fromMultiObj(roleIds, new TypeReference<List<String>>(){});
			
			
			sysUser.setId(String.valueOf(id));
			// 判断密码是否要修改，如果密码为null不需要修改
			if (StringUtils.isNotEmpty(sysUser.getPassword())) {
				String enPwd = passwordEncoder.encode(sysUser.getPassword());
				sysUser.setPassword(enPwd);
			}
			// 修改用户和用户角色关联
			sysUserService.updateUserAndUR(sysUser,listIds,isUpdateRole);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------sysRole update error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	/**
	 * 添加用户
	 * roleIds 角色id集合
	 * @param email 邮箱
	 * @param mobile 手机号
	 * @param password 密码
	 * @param remark 备注
	 * @param username 用户名
	 * LIPING
	 */
//	@Secured({"USER_ADMIN","ROLE_USER"}) //此方法只允许 ROLE_ADMIN 和ROLE_USER 角色 访问
//	@RolesAllowed("USER_ADMIN")
	@RequestMapping(value = "/save/{email}/{mobile}/{password}/{remark}/{username}", method = { RequestMethod.POST })
	public @ResponseBody String save(@RequestParam String roleIds, @PathVariable String email, @PathVariable String mobile,@PathVariable String password
			,@PathVariable String remark,@PathVariable String username) {

		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------sysUser save request param>:[email={},mobile={},password={},remark={},username={}]",
				email, mobile, password, remark, username);
		try {
			
			List<String> listIds = helper.fromMultiObj(roleIds, new TypeReference<List<String>>(){});
			// 密码加密处理
			password = passwordEncoder.encode(password);
			
			SysUser user = new SysUser();
			user.setEmail(email);
			user.setMobile(mobile);
			user.setPassword(password);
			user.setRemark(remark);
			user.setUsername(username);
			// 保存用户和用户关联信息
			sysUserService.saveUserAndUR(user,listIds);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------sysUser save error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	
//	CREATE TABLE `sys_user` (
//			  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
//			  `username` varchar(50) NOT NULL COMMENT '用户名',
//			  `password` varchar(50) NOT NULL COMMENT '密码',
//			  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
//			  `sex` int(1) DEFAULT NULL COMMENT '性别：0女，1男',
//			  `is_active` int(1) DEFAULT NULL COMMENT '是否激活：0否，1是',
//			  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号码',
//			  `email` varchar(20) DEFAULT NULL COMMENT '邮箱',
//			  `sort` int(11) DEFAULT NULL COMMENT '排序',
//			  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
//			  `image_path_url` varchar(300) DEFAULT NULL COMMENT '头像地址',
//			  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
//			  `status` int(1) DEFAULT NULL COMMENT '状态 0正常 1删除',
//			  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建用户ID',
//			  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
//			  `update_user_id` varchar(50) DEFAULT NULL COMMENT '更新用户ID',
//			  `update_time` datetime DEFAULT NULL COMMENT '最后更新时间',
//			  PRIMARY KEY (`id`)
//			) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='后台用户表';

	
	
}
