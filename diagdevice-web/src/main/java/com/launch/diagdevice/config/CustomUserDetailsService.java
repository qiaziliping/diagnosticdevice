package com.launch.diagdevice.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Reference;
import com.launch.diagdevice.entity.SysAuthority;
import com.launch.diagdevice.entity.SysRole;
import com.launch.diagdevice.entity.SysUser;
import com.launch.diagdevice.service.SysAuthorityService;
import com.launch.diagdevice.service.SysRoleService;
import com.launch.diagdevice.service.SysUserRoleService;
import com.launch.diagdevice.service.SysUserService;

@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Reference(interfaceClass=SysUserService.class)
	private SysUserService sysUserService;
	
	@Reference(interfaceClass=SysUserRoleService.class)
	private SysUserRoleService sysUserRoleService;
	
	@Reference(interfaceClass=SysRoleService.class)
	private SysRoleService sysRoleService;
	@Reference(interfaceClass=SysAuthorityService.class)
	private SysAuthorityService sysAuthorityService;
	
	

	@Override
	public UserDetails loadUserByUsername(String userName) throws AuthenticationException {
			// 1、从数据库用户名密码
			SysUser user = new SysUser();
			user.setUsername(userName);
			user = sysUserService.selectOne(user);
			
			if (null == user) {
				return null;
			}
			
			// 权限集合
			List<GrantedAuthority> authoritiesList = new ArrayList<GrantedAuthority>();
			
			
			Long userId = Long.parseLong(user.getId());
			// 根据用户ID查询角色列表
			List<SysRole> roleList = sysRoleService.selectByUserId(userId);
			// 默认获取一个角色的权限
			if (!roleList.isEmpty()) {
				SysRole role = roleList.get(0);
				// 根据角色Id查询权限
				List<SysAuthority> result = sysAuthorityService.selectListByRoleId(Long.parseLong(role.getId()));
				for (SysAuthority sa : result) {
					String code = sa.getResourceCode();
					GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(code);
					authoritiesList.add(grantedAuthority);
				}
				
			} 
			
			return new User(user.getUsername(), user.getPassword(), authoritiesList);
			
			
			
			/*Map<String,Object> condition = new HashMap<String,Object>();
			condition.put("userId", user.getId());
			List<SysUserRoleVo> urListVo = sysUserRoleService.selectVoByCondition(condition);

			for (SysUserRoleVo ur : urListVo) {
				String code = ur.getRoleCode();
				code = "ROLE_"+code;
				GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(code);
				authoritiesList.add(grantedAuthority);
			}*/

	}


}
