package com.launch.diagdevice.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.launch.diagdevice.common.constant.AppCodeConstant;
import com.launch.diagdevice.common.constant.AppConstant;
import com.launch.diagdevice.common.constant.WebConstant;
import com.launch.diagdevice.common.result.AppListResult;
import com.launch.diagdevice.common.result.AppResult;
import com.launch.diagdevice.common.util.CollectionTool;
import com.launch.diagdevice.common.util.JsonHelper;
import com.launch.diagdevice.common.util.StringUtil;
import com.launch.diagdevice.common.util.VerifyCodeUtil;
import com.launch.diagdevice.common.utils.RedisUtil;
import com.launch.diagdevice.entity.SysAuthority;
import com.launch.diagdevice.entity.SysRoleAuthority;
import com.launch.diagdevice.entity.SysUser;
import com.launch.diagdevice.entity.vo.SysAuthorityVo;
import com.launch.diagdevice.service.SysAuthorityService;
import com.launch.diagdevice.service.SysRoleAuthorityService;
import com.launch.diagdevice.service.SysUserService;

/**
 * 后台系统用户控制类
 * @author LIPING
 * @version 0.0.1
 * @since 2018年9月6日
 */
@Controller
@RequestMapping("/account")
public class SysAccountController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RedisUtil redisUtil;

	@Reference(interfaceClass = SysUserService.class)
	private SysUserService sysUserService;

	@Reference(interfaceClass = SysAuthorityService.class)
	private SysAuthorityService sysAuthorityService;

	@Reference(interfaceClass = SysRoleAuthorityService.class)
	private SysRoleAuthorityService sysRoleAuthorityService;


	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request, HttpServletResponse response) throws IOException {

		// response.sendRedirect("http://www.baidu.com");
//		return "login";
		return "diag_login";
	}

	@RequestMapping("/index")
	public String index(ModelMap map) {
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String result = obj.toString();
		map.addAttribute("hello", result);

		/*UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String password = userDetails.getPassword();
		String username = userDetails.getUsername();
		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
		logger.info(username + "---------------->:" + password);*/

		return "index";
	}

	/**
	 * 登录处理方法，即登录时post请求的方法；
	 * 此方法的URL必须与
	 */
	/*@RequestMapping("/login/process")
	public void loginProcess(ModelMap map) {
		logger.info("---enter into loginProcess---");
	}*/

	/*@RequestMapping("/login-error")
	public String loginError() {

		return "login_test";
	}*/

	@RequestMapping(value = "/logout",method={RequestMethod.POST,RequestMethod.GET})
	public void logout() {
		logger.info("退出登录");
	}

	/**
	 * 切换用户的角色
	 * LIPING
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/switchRole/{roleId}/{roleName}", method = { RequestMethod.POST, RequestMethod.GET })
//	public @ResponseBody String switchRole(HttpServletRequest request, @PathVariable Long roleId,@PathVariable Long userId) {
	public @ResponseBody String switchRole(HttpServletRequest request, @PathVariable Long roleId,@PathVariable String roleName) {

		AppListResult appResult = new AppListResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------getRoleAuthorityByRoleId request param>:[roleId={},userId={}]", roleId);
		try {

			HttpSession session = request.getSession();
			Map<String,Object> sessUser = (Map<String, Object>)session.getAttribute("sessUser");
			sessUser.put("currRoleName", roleName);
			
			session.setAttribute("sessUser", sessUser);
					
			Long userId = Long.parseLong(String.valueOf(sessUser.get("userId")));
			
			// 切换用户到上下文中
			switchRoleToContext(roleId,userId);
			
			

			List<SysRoleAuthority> ralist = sysRoleAuthorityService.selectByRoleId(roleId);
			Map<Long, Boolean> modelResId = new HashMap<Long, Boolean>();
			for (SysRoleAuthority raModel : ralist) {
				modelResId.put(raModel.getResourceId(), true);
			}

			List<SysAuthorityVo> resultvo = sysAuthorityService.selectMenuList(null, 0);

			for (Map.Entry<Long, Boolean> entMap : modelResId.entrySet()) {
				Long resId = entMap.getKey();

				// 循环一级菜单
				for (SysAuthorityVo vo : resultvo) {
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
			for (SysAuthorityVo vo : resultvo) {
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
					if (!flag)
						flag = tempFlag;

					if (flag)
						vo.setIsChecked(IS_CHECKED_YES);
					else
						vo.setIsChecked(IS_CHECKED_NO);
				}
			}

			appResult.setData(resultvo);
			helper.filter(SysAuthorityVo.class,
					"id,parentId,resourceCode,resourceName,resourceType,sort,isChecked,childList", null);

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
		for (SysAuthorityVo vo : resultvo) {
			int type2 = vo.getResourceType();
			boolean flag2 = false;
			if (type2 != 5) {
				List<SysAuthorityVo> childlist = vo.getChildList();
				if (CollectionTool.listIsEmpty(childlist)) {
					vo.setIsChecked(IS_CHECKED_NO);
					continue;
				}
				boolean tempFlag = iterIscheckedNull(childlist);
				if (!flag2)
					flag2 = tempFlag;

				if (flag2) {
					flag = true;
					vo.setIsChecked(IS_CHECKED_YES);
				} else {
					vo.setIsChecked(IS_CHECKED_NO);
				}
			} else {
				Integer isCheck = vo.getIsChecked();
				if (null != isCheck && isCheck == 1) {
					flag = true;
					// flag2 = true;
				} else {
					vo.setIsChecked(IS_CHECKED_NO);
				}
			}
		}

		return flag;
	}

	private void iterAuthority(List<SysAuthorityVo> childlist, Long resId) {
		for (SysAuthorityVo vo2 : childlist) {
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
	 * 将角色对象权限存放到上下文中
	 * LIPING
	 */
	public void switchRoleToContext(Long roleId,Long userId) {
		
		// 从数据库中获取密码
		SysUser sysUser = sysUserService.selectById(userId);
		String dbPassword = sysUser.getPassword();
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		// String password = userDetails.getPassword(); // 此密码获取为null
		String username = userDetails.getUsername();
		// 老的权限
		Collection<? extends GrantedAuthority> oldAuthorities = userDetails.getAuthorities();
		logger.info("----------------oldAuthorities>:" + oldAuthorities);

		List<GrantedAuthority> authoritiesList = new ArrayList<GrantedAuthority>();

		// 根据角色ID获取权限
		List<SysAuthority> result = sysAuthorityService.selectListByRoleId(roleId);
		for (SysAuthority sa : result) {
			String code = sa.getResourceCode();
			GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(code);
			authoritiesList.add(grantedAuthority);
		}
		logger.info("----------------newAuthorities>:" + authoritiesList);
		UserDetails user = new User(username, dbPassword, authoritiesList);

		Authentication authentication = new UsernamePasswordAuthenticationToken(user, dbPassword, authoritiesList);
		SecurityContext scontext = new SecurityContextImpl(authentication);
		// 将切换的权限设置到context中
		SecurityContextHolder.setContext(scontext);
	}



	/**
	 * 获取验证码接口
	 * LIPING
	 */
	@RequestMapping(value = "/authimage", method = RequestMethod.GET)
	public void AuthImage(HttpServletRequest request, HttpServletResponse response, @RequestParam String date) {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		// 生成随机字串
		String verifyCode = VerifyCodeUtil.generateVerifyCode(4);

		String uuid = StringUtil.getUUID();
		// 10分钟过期,登录过程最多十分钟吧
		redisUtil.set(AppConstant.DIAGDEVICE_VERIFY_CODE_ADMIN + ":" + verifyCode.toLowerCase(), uuid, 60 * 10L);

		// 生成图片
		int w = 134, h = 50;
		try {
			VerifyCodeUtil.outputImage(w, h, response.getOutputStream(), verifyCode);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("sysAccount write img verifycode catch an exception>:{}", e);
		}
	}

	/**
	 * 验证验证码接口
	 * LIPING
	 */
	@ResponseBody
	@RequestMapping(value = "/verifyAuthimage/{verCode}", method = { RequestMethod.POST, RequestMethod.GET })
	public String verifyAuthimage(@PathVariable String verCode) {
		AppResult appResult = new AppResult();
		JsonHelper helper = new JsonHelper();
		logger.info("------param>:" + verCode);
		try {
			if (!StringUtils.isEmpty(verCode)) {

				verCode = verCode.toLowerCase();
				String uuid = redisUtil.get(AppConstant.DIAGDEVICE_VERIFY_CODE_ADMIN + ":" + verCode);

				if (StringUtils.isEmpty(uuid)) {
					appResult.setCode(WebConstant.VERIFY_CODE_ERROR);
					appResult.setMessage(WebConstant.checkCode(WebConstant.VERIFY_CODE_ERROR));
					return helper.toJsonStr(appResult);
				}
				// 先不删除，登录接口还需要验证
				// redisUtil.remove(AppConstant.DIAGDEVICE_VERIFY_CODE+":"+uuid);
			} else {
				// 缺少参数
				appResult.setCode(WebConstant.VERIFY_CODE_IS_EMPTY);
				appResult.setMessage(WebConstant.checkCode(WebConstant.VERIFY_CODE_IS_EMPTY));
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("--------verifyAuthimage.ado>:{}", e);
			appResult.setCode(AppCodeConstant.ERROR);
			appResult.setMessage(AppCodeConstant.checkCode(AppCodeConstant.ERROR));
		}
		return helper.toJsonStr(appResult);
	}
	
	private static final int IS_CHECKED_YES = 1;
	private static final int IS_CHECKED_NO = 0;

}
