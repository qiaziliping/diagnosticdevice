package com.launch.diagdevice.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.launch.diagdevice.entity.SysRole;
import com.launch.diagdevice.entity.SysUser;
import com.launch.diagdevice.service.SysRoleService;
import com.launch.diagdevice.service.SysUserService;

//处理登录成功的。
@Component("myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

	@Autowired
    private ObjectMapper objectMapper;
	
	@Reference(interfaceClass = SysUserService.class)
	private SysUserService sysUserService;
	@Reference(interfaceClass = SysRoleService.class)
	private SysRoleService sysRoleService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		
		 // 1、什么都不做的话，那就直接调用父类的方法
		 //super.onAuthenticationSuccess(request, response, authentication);  
         
         //2、这里可以根据实际情况，来确定是跳转到页面或者json格式。
         //如果是返回json格式，那么我们这么写
         
		setGlobalSession(request);
		
         Map<String,Object> map=new HashMap<>();
         map.put("code", 0);
         map.put("message", "login seccess");
         response.setContentType("application/json;charset=UTF-8");
         response.getWriter().write(objectMapper.writeValueAsString(map));
         
         
         // 3、如果是要跳转到某个页面的，比如我们的那个whoim的则
//         new DefaultRedirectStrategy().sendRedirect(request, response, "/account/index");
	}
	
	public void setGlobalSession(HttpServletRequest request) {
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username =  userDetails.getUsername();
		
		SysUser model = new SysUser();
		model.setUsername(username);
		model = sysUserService.selectOne(model);
		
		// 用户对象
		Map<String,Object> userMap = new HashMap<String,Object>();
		userMap.put("userId", model.getId());
		userMap.put("username", username);
		
		// 获取角色
		List<SysRole> ralist = sysRoleService.selectByUserId(Long.parseLong(model.getId()));
		List<Map<String,Object>> roleList = new ArrayList<Map<String,Object>>();
		for (int i = 0,size = ralist.size();i < size;i++) {
			SysRole role = ralist.get(i);
			Map<String, Object> roleMap = new HashMap<String,Object>();
			roleMap.put("id", role.getId());
			roleMap.put("roleName", role.getRoleName());
			roleMap.put("roleCode", role.getRoleCode());
			roleList.add(roleMap);
			// 用户当前角色
			if (i == 0) {
				userMap.put("currRoleName", role.getRoleName());
			}
			
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("sessUser", userMap);
		session.setAttribute("sessRoleList", roleList);
	
	}
	
}
