package com.launch.diagdevice.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.launch.diagdevice.entity.SysAuthority;
import com.launch.diagdevice.service.SysAuthorityService;

/**
 * loadResourceDefine()是获取所有权限集合
 * getAttributes() 根据请求的URL获取权限
 * @version 0.0.1
 * @since 2018年11月16日
 */
@Service
public class MyInvocationSecurityMetadataSourceService  implements
        FilterInvocationSecurityMetadataSource {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Reference(interfaceClass=SysAuthorityService.class)
	private SysAuthorityService sysAuthorityService;
	

    private HashMap<String, Collection<ConfigAttribute>> map =null;
    
    /** 5代表权限 */
    private static final int RESOURCE_TYPE_PERMISSIONS = 5;

    /**
     * 加载权限表中所有权限
     */
    public void loadResourceDefine(){
        map = new HashMap<>();
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg;
        
        Map<String, Object> condition = new HashMap<String,Object>();
        condition.put("resourceType", RESOURCE_TYPE_PERMISSIONS);
        List<SysAuthority> permissions = sysAuthorityService.selectByCondition(condition);
        
        for(SysAuthority permission : permissions) {
            array = new ArrayList<>();
            cfg = new org.springframework.security.access.SecurityConfig(permission.getResourceCode());
            //此处只添加了用户的名字，其实还可以添加更多权限的信息，例如请求方法到ConfigAttribute的集合中去。此处添加的信息将会作为MyAccessDecisionManager类的decide的第三个参数。
            array.add(cfg);
            //用权限的getUrl() 作为map的key，用ConfigAttribute的集合作为 value，
            map.put(permission.getResourceUrl(), array);
        }
        logger.info("----authority--All>:"+map);
    }

    //此方法是为了判定用户请求的url 是否在权限表中，如果在权限表中，则返回给 decide 方法，用来判定用户是否有此权限。如果不在权限表中则放行。
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        if(map ==null || map.size() == 0) loadResourceDefine();
        //object 中包含用户请求的request 信息
        HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
        AntPathRequestMatcher matcher;
        String resUrl;
        for(Iterator<String> iter = map.keySet().iterator(); iter.hasNext(); ) {
            resUrl = iter.next();
            matcher = new AntPathRequestMatcher(resUrl);
            if(matcher.matches(request)) {
                return map.get(resUrl);
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}