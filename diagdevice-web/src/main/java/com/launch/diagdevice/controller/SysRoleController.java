package com.launch.diagdevice.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.launch.diagdevice.common.util.CollectionTool;
import com.launch.diagdevice.common.util.JsonHelper;
import com.launch.diagdevice.entity.SysRole;
import com.launch.diagdevice.entity.SysRoleAuthority;
import com.launch.diagdevice.entity.vo.SysAuthorityVo;
import com.launch.diagdevice.service.SysAuthorityService;
import com.launch.diagdevice.service.SysRoleAuthorityService;
import com.launch.diagdevice.service.SysRoleService;

/**
 * 角色控制类
 * @author LIPING
 * @version 0.0.1
 * @since 2018年11月13日
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

	@Reference(interfaceClass = SysAuthorityService.class)
	private SysAuthorityService sysAuthorityService;

	@Reference(interfaceClass = SysRoleService.class)
	private SysRoleService sysRoleService;

	@Reference(interfaceClass = SysRoleAuthorityService.class)
	private SysRoleAuthorityService sysRoleAuthorityService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final int IS_CHECKED_YES = 1;
	private static final int IS_CHECKED_NO = 0;

	
	/**
	 * 根据所有角色列表
	 * LIPING
	 */
	@RequestMapping(value = "/getAllRoleList", method = { RequestMethod.GET })
	public String getAllRoleList() {
		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		try {
			
			List<SysRole> ralist = sysRoleService.selectByIndex(null);
			
			
			appResult.setData(ralist);
			helper.filter(SysRole.class, "id,roleName,roleCode", null);
			 
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------getAllRoleList error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	/**
	 * 根据用户ID查询角色列表
	 * LIPING
	 */
	@RequestMapping(value = "/getRoleByUserId/{userId}", method = { RequestMethod.GET })
	public String getRoleByUserId(@PathVariable Long userId) {
		AppListResult appResult = new AppListResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------getRoleByUserId request param>:[userId={}]", userId);
		try {
			
			List<SysRole> ralist = sysRoleService.selectByUserId(userId);
			
			
			int size = ralist.size();
			Long count = (long)size;
			appResult.setData(ralist);
			appResult.setCount(count);
			helper.filter(SysRole.class, "id,roleName,roleCode", null);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------getRoleByUserId error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	/**
	 * 根据角色ID查看角色对应的权限
	 * LIPING
	 */
	@RequestMapping(value = "/getRoleAuthorityByRoleId/{roleId}", method = { RequestMethod.GET })
	public @ResponseBody String getRoleAuthorityByRoleId(@PathVariable Long roleId) {
		AppListResult appResult = new AppListResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------getRoleAuthorityByRoleId request param>:[roleId={}]", roleId);
		try {

			List<SysRoleAuthority> ralist = sysRoleAuthorityService.selectByRoleId(roleId);
			Map<Long,Boolean> modelResId = new HashMap<Long,Boolean>(); 
			for (SysRoleAuthority raModel : ralist) {
				modelResId.put(raModel.getResourceId(), true);
			}
			
			List<SysAuthorityVo> resultvo = sysAuthorityService.selectMenuList(null,0); 
			
			
			for (Map.Entry<Long, Boolean> entMap : modelResId.entrySet()) {
				Long resId = entMap.getKey();
				
				// 循环一级菜单
				for (SysAuthorityVo vo : resultvo) 
				{   
					int type = vo.getResourceType();
					// != 5表示是菜单
					if (type != 5) {
						List<SysAuthorityVo> childlist = vo.getChildList(); 
					    if (CollectionTool.listIsEmpty(childlist)) {
					    	continue;
					    }
					    // 循环二级菜单
					    iterAuthority(childlist, resId);
					} else {
						// else 表示是权限
						Long authId = vo.getId();
			    		if (authId == resId) {
			    			vo.setIsChecked(IS_CHECKED_YES);
			    		}
					}
				}
			}
			
				
			// 循环一级菜单
			for (SysAuthorityVo vo : resultvo) 
			{   
				int type = vo.getResourceType();
				boolean flag = false;
				// != 5表示是菜单
				if (type != 5) {
					List<SysAuthorityVo> childlist = vo.getChildList(); 
				    if (CollectionTool.listIsEmpty(childlist)) {
				    	continue;
				    }
				    // 循环二级菜单或者权限
				    boolean tempFlag = iterIscheckedNull(childlist);
				    if (!flag)  flag = tempFlag;
				    
				    if (flag) vo.setIsChecked(IS_CHECKED_YES);
					else vo.setIsChecked(IS_CHECKED_NO);
				}
			}
			
			
			
			appResult.setData(resultvo);
			helper.filter(SysAuthorityVo.class, "id,parentId,resourceCode,resourceName,resourceType,sort,isChecked,childList", null);
			 

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------getRoleAuthorityByRoleId error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	private boolean iterIscheckedNull(List<SysAuthorityVo> resultvo) {
		boolean flag = false;
		for (SysAuthorityVo vo : resultvo) 
		{
	    	int type2 = vo.getResourceType();
	    	boolean flag2 = false;
	    	if (type2 != 5) {
	    		List<SysAuthorityVo> childlist = vo.getChildList(); 
			    if (CollectionTool.listIsEmpty(childlist)) {
			    	vo.setIsChecked(IS_CHECKED_NO);
			    	continue;
			    }
			    boolean tempFlag = iterIscheckedNull(childlist);
			    if (!flag2)  flag2 = tempFlag;
			    
			    if (flag2){
			    	flag = true;
			    	vo.setIsChecked(IS_CHECKED_YES);
			    } else {
			    	vo.setIsChecked(IS_CHECKED_NO);
			    }
	    	} else {
	    		Integer isCheck = vo.getIsChecked();
	    		if (null != isCheck && isCheck == 1) {
	    			flag = true;
	    			//flag2 = true;
	    		} else {
	    			vo.setIsChecked(IS_CHECKED_NO);
	    		}
	    	}
		}
		
		return flag;
	}

	private void iterAuthority(List<SysAuthorityVo> childlist,Long resId) {
		for (SysAuthorityVo vo2 : childlist) 
		{
	    	int type2 = vo2.getResourceType();
	    	if (type2 != 5) {
	    		List<SysAuthorityVo> childlist2 = vo2.getChildList(); 
			    if (CollectionTool.listIsEmpty(childlist2)) {
			    	continue;
			    }
			    iterAuthority(childlist2, resId);
	    	} else {
	    		Long authId = vo2.getId();
	    		if (authId == resId) {
	    			vo2.setIsChecked(IS_CHECKED_YES);
	    		}
	    	}
		}
	}

	/**
	 * 添加角色权限（角色授权）
	 * @param roleName 角色名称
	 * @param roleCode 角色编码
	 * @param remark 角色描述
	 * LIPING
	 */
	@RequestMapping(value = "/saveRoleAuthority/{roleId}", method = { RequestMethod.POST })
	public @ResponseBody String saveRoleAuthority(@PathVariable Long roleId, @RequestParam String authIds) {

		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------saveRoleAuthority request param>:[roleId={},authIds={},roleName={}]", roleId, authIds);
		try {
			List<String> listIds = helper.fromMultiObj(authIds, new TypeReference<List<String>>() {
			});

			List<SysRoleAuthority> raList = new ArrayList<SysRoleAuthority>();
			if (null != listIds) {
				for (String authId : listIds) {
					SysRoleAuthority ramodel = new SysRoleAuthority();
					ramodel.setResourceId(Long.parseLong(authId));
					ramodel.setRoleId(roleId);
					raList.add(ramodel);
				}
			}

			sysRoleAuthorityService.batchSave(raList, roleId);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------saveRoleAuthority error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}

	// -------------------------------以下为角色的接口-------------------
	/**
	 * 
	 * 获取菜单权限列表
	 * LIPING
	 */
	@RequestMapping(value = "/getSysRolePage", method = { RequestMethod.GET })
	public @ResponseBody String getSysRolePage(@RequestParam int pageNum, @RequestParam int pageSize,
			@Valid SysRole sysRole) {
		AppListResult appResult = new AppListResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------getSysRolePage request param>:[pageNum={},pageSize={},getRoleName={},getRoleCode={}]",
				pageNum, pageSize, sysRole.getRoleName(), sysRole.getRoleCode());
		try {

			PagingData<SysRole> pageData = sysRoleService.selectPage(sysRole);

			List<SysRole> listvo = pageData.getRows();

			appResult.setData(listvo);
			appResult.setCount(pageData.getTotal());
			helper.filter(SysRole.class, "id,roleCode,roleName,remark", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------getSysRolePage error info>:{}", e);
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
	@RequestMapping(value = "/getSysRoleDetail/{id}", method = { RequestMethod.GET })
	public @ResponseBody String getSysRoleDetail(@PathVariable Long id) {

		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------getSysRoleDetail request param>:[id={}]", id);
		try {

			SysRole sysRole = sysRoleService.selectById(id);

			helper.filter(SysRole.class, "id,remark,roleCode,roleName", null);
			appResult.setData(sysRole);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------getSysRoleDetail error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}

	/**
	 * 删除角色
	 * @param id 主键
	 * LIPING
	 */
	@RequestMapping(value = "/delete/{id}", method = { RequestMethod.POST })
	public @ResponseBody String delete(@PathVariable Long id) {

		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------sysRole delete request param>:[id={}]", id);
		try {
			SysRole role = new SysRole();
			role.setId(String.valueOf(id));

//			sysRoleService.delete(role);
			// 删除角色，先删除用户角色关联表和角色权限关联表
			sysRoleService.deleteRoleAndURAndRA(role);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------sysAuthority delete error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}

	/**
	 * 修改角色
	 * @param roleName 角色名称
	 * @param roleCode 角色编码
	 * @param remark 角色描述
	 * LIPING
	 */
	@RequestMapping(value = "/update/{id}", method = { RequestMethod.POST })
	public @ResponseBody String update(@PathVariable Long id, SysRole sysRole) {

		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------sysRole update request param>:[id={},sysRole={}]", id, sysRole);
		try {
			sysRole.setId(String.valueOf(id));
			sysRoleService.update(sysRole);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------sysRole update error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}

	/**
	 * 添加角色
	 * @param roleName 角色名称
	 * @param roleCode 角色编码
	 * @param remark 角色描述
	 * LIPING
	 */
	@RequestMapping(value = "/save/{remark}/{roleCode}/{roleName}", method = { RequestMethod.POST })
	public @ResponseBody String save(@PathVariable String remark, @PathVariable String roleCode,
			@PathVariable String roleName) {

		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------sysRole save request param>:[remark={},roleCode={},roleName={}]", remark, roleCode,
				roleName);
		try {
			SysRole role = new SysRole();
			role.setRoleCode(roleCode);
			role.setRoleName(roleName);
			role.setRemark(remark);
			sysRoleService.save(role);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------sysRole save error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}

}
