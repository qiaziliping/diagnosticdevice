package com.launch.diagdevice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.launch.diagdevice.common.constant.AppCodeConstant;
import com.launch.diagdevice.common.constant.WebConstant;
import com.launch.diagdevice.common.model.PagingData;
import com.launch.diagdevice.common.result.AppListResult;
import com.launch.diagdevice.common.result.AppResult;
import com.launch.diagdevice.common.util.JsonHelper;
import com.launch.diagdevice.entity.SysAuthority;
import com.launch.diagdevice.entity.vo.SysAuthorityVo;
import com.launch.diagdevice.service.SysAuthorityService;

@RestController
@RequestMapping("/sys/authority")
public class SysAuthorityController {

	
	@Reference(interfaceClass=SysAuthorityService.class)
	private SysAuthorityService sysAuthorityService;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	/**
	 * 获取全部菜单
	 * @return
	 */
	@RequestMapping(value = "/getAuthorityAll", method = { RequestMethod.GET })
	public @ResponseBody String getAuthorityAll() {
		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		try {
			
			Map<String,Object> condition = new HashMap<String,Object>();
			List<SysAuthority> result = sysAuthorityService.selectByCondition(condition);
			appResult.setData(result);
			helper.filter(SysAuthority.class, "id,parentId,resourceCode,resourceName,resourceType", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------verifyHasChildren error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	

	/**
	 * 
	 * 根据权限ID，查询是否有子节点
	 * @return 有子节点返回true，反之 false
	 * LIPING
	 */
	@RequestMapping(value = "/verifyHasChildren/{id}", method = { RequestMethod.GET })
	public @ResponseBody String verifyHasChildren(@PathVariable Long id) {
		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------verifyHasChildren request param>:[id={}]",id);
		try {
			
			Map<String,Object> resultMap = new HashMap<String,Object>();
			
			Map<String,Object> condition = new HashMap<String,Object>();
			condition.put("parentId", id);
			List<SysAuthority> result = sysAuthorityService.selectByCondition(condition);
			if (result.size() > 0) {
				resultMap.put("hasChildren", true);
			} else {  
				resultMap.put("hasChildren", false);
			}
			appResult.setData(resultMap);
			helper.filter(SysAuthority.class, "id,parentId,resourceCode,resourceName,resourceType", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------verifyHasChildren error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	/**
	 * 
	 * 根据角色ID查询权限
	 * LIPING
	 */
	@RequestMapping(value = "/getAuthorityByRoleId/{roleId}", method = { RequestMethod.GET })
	public @ResponseBody String getAuthorityByRoleId(@PathVariable Long roleId) {
		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------getAuthorityByRoleId request param>:[roleId={}]",roleId);
		try {
			
			List<SysAuthority> result = sysAuthorityService.selectListByRoleId(roleId);

			appResult.setData(result);
			helper.filter(SysAuthority.class, "id,parentId,resourceCode,resourceName,resourceType", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------getAuthorityByRoleId error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	
	/**
	 * 
	 * 获取菜单列表，resourceType为2,3,4的
	 * isMenu 1只查询菜单，0，菜单和权限所有的
	 * LIPING
	 */
	@RequestMapping(value = "/getSysAuthorityMenuTree/{isMenu}", method = { RequestMethod.GET })
	public @ResponseBody String getSysAuthorityMenuTree(@PathVariable int isMenu,@Valid SysAuthority authority) {
		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------getSysAuthorityMenuTree request param>:[isMenu={},authority={}]",isMenu,authority);
		try {
			
			List<SysAuthorityVo> resultvo = sysAuthorityService.selectMenuList(authority,isMenu);

			appResult.setData(resultvo);
			helper.filter(SysAuthorityVo.class, "id,parentId,resourceCode,resourceName,resourceType,sort,childList", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------getSysAuthorityMenuTree error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	/**
	 * 
	 * 获取菜单权限列表
	 * LIPING
	 */
//	@PreAuthorize("hasRole('ROLE_Admin')")
//	@Secured("ROLE_ADMIN")
	@RequestMapping(value = "/getSysAuthorityPage", method = { RequestMethod.GET })
	public @ResponseBody String getSysAuthorityPage(@RequestParam int pageNum, @RequestParam int pageSize, @Valid SysAuthority authority) {
		AppListResult appResult = new AppListResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------getSysAuthorityPage request param>:[pageNum={},pageSize={},device.get={},serialNo={}]", pageNum, pageSize,
				authority.getResourceName(),authority.getResourceCode());
		try {

			PagingData<SysAuthorityVo> pageData = sysAuthorityService.selectPage(authority);
			
			List<SysAuthorityVo> listvo = pageData.getRows();

			appResult.setData(listvo);
			appResult.setCount(pageData.getTotal());
			helper.filter(SysAuthorityVo.class, "id,parentId,parentResourceCode,resourceCode,resourceName,resourceUrl,resourceType,sort", null);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------getSysAuthorityPage error info>:{}", e);
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
//	@Secured({"ROLE_super_mgr_role","ROLE_mgr_role"}) //此方法只允许 ROLE_ADMIN 和ROLE_USER 角色 访问
	@RequestMapping(value = "/getSysAuthorityDetail/{id}", method = { RequestMethod.GET })
	public @ResponseBody String getSysAuthorityDetail(@PathVariable Long id) {

		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------getSysAuthorityDetail request param>:[id={}]", id);
		try {

			SysAuthority authority = sysAuthorityService.selectById(id);
			
			helper.filter(SysAuthority.class, "id,parentId,resourceCode,resourceName,resourceType,resourceUrl,sort", null);
			appResult.setData(authority);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------getSysAuthorityDetail error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}

	/**
	 * 删除菜单
	 * @param id 主键
	 * TODO
	 * liping
	 */
	@RequestMapping(value = "/delete/{id}", method = { RequestMethod.POST })
	public @ResponseBody String delete(@PathVariable Long id) {

		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------sysAuthority delete request param>:[id={}]",id);
		try {
			SysAuthority authority = new SysAuthority();
			authority.setId(String.valueOf(id));
			// 删除前先判断是否有子节点，有子节点提示：有子节点不能直接删除
			Map<String,Object> condition = new HashMap<String,Object>();
			condition.put("parentId", id);
			List<SysAuthority> listNum = sysAuthorityService.selectByCondition(condition);
			if (listNum == null || listNum.size() == 0) {
				 sysAuthorityService.delete(authority);
				// 先删除角色权限关联表的数据，再删除权限表数据
//				sysAuthorityService.deleteAuthAndRA(authority);
			} else {
				appResult.setCode(WebConstant.MENU_CHILD_IS_NOT_EMPTY);
				appResult.setMessage(WebConstant.checkCode(WebConstant.MENU_CHILD_IS_NOT_EMPTY));
				return helper.toJsonStr(appResult);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------sysAuthority delete error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	/**
	 * 修改菜单
	 * @param id 主键ID
	 * @param parentId 父ID
	 * @param resourceCode 资源编码
	 * @param resourceName 资源名称
	 * @param resourceType 资源类型 1预留 2：一级菜单 3：二级菜单 4：三级菜单 5：权限
	 * @param sort 排序
	 * {id}/{parentId}/{resourceCode}/{resourceName}/{sort}
	 * LIPING
	 */
	@RequestMapping(value = "/update/{id}", method = { RequestMethod.POST })
	public @ResponseBody String update(@PathVariable Long id,SysAuthority authority) {

		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------sysAuthority update request param>:[id={},authority={}]",id, authority);
		try {
			
			sysAuthorityService.update(authority);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------sysAuthority update error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	/**
	 * 添加菜单
	 * @param parentId 父ID
	 * @param resourceCode 资源编码
	 * @param resourceName 资源名称
	 * @param resourceType 资源类型 1预留 2：一级菜单 3：二级菜单 4：三级菜单 5：权限
	 * @param sort 排序
	 * LIPING
	 */
	@RequestMapping(value = "/save/{parentId}/{resourceCode}/{resourceName}/{resourceType}/{sort}", method = { RequestMethod.POST })
	public @ResponseBody String save(@PathVariable Long parentId, @PathVariable String resourceCode,
			@PathVariable String resourceName, @PathVariable int resourceType, @PathVariable int sort,SysAuthority authority) {

		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------sysAuthority save request param>:[parentId={},resourceCode={},resourceName={},resourceType={},sort={}]",
				parentId, resourceCode, resourceName, resourceType,sort);
		try {
//			SysAuthority authority = new SysAuthority();
			authority.setParentId(parentId);
			authority.setResourceCode(resourceCode);
			authority.setResourceName(resourceName);
			authority.setResourceType(resourceType);
			authority.setSort(sort);
			
			sysAuthorityService.save(authority);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------sysAuthority save error info>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	
	
	
}
