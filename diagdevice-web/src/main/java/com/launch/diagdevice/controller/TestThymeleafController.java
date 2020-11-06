package com.launch.diagdevice.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;
import com.launch.diagdevice.common.model.PagingData;
import com.launch.diagdevice.common.model.PagingEntity;
import com.launch.diagdevice.entity.User;

@Controller()
@RequestMapping("/tl")
public class TestThymeleafController {
	
	
	@RequestMapping("/userlist")
	 public Object userlist(@RequestParam(value="pageon",defaultValue="1")int pageon
	      ,ModelAndView mv){
		
		Map<String, Object> page = new HashMap<String,Object>();
		page.put("pagecount", 5);
		page.put("pageon", 3);
		
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("users", getUserList());
		map.put("page", page);
	    mv.addAllObjects(map);
	    mv.setViewName("/test/list");
	    return mv;
	}
	
	private List<User> getUserList() {
		List<User> userList = new ArrayList<User>();

        for (int i = 0; i < 9; i++) {
            User dto = new User();

            dto.setId(""+i);
            dto.setUsername("pepstack-" + i);
            dto.setPassword("pwd-Shanghai, China"+i);
            dto.setMobile("1536166412" + i);

            userList.add(dto);
        }
        return userList;
	}
	
	
	@RequestMapping("/list")
    public String  listUser(Model model) {
         
		Map<String, Object> page = new HashMap<String,Object>();
		page.put("rowcount", 12);
		page.put("pagecount", 1);
		
		
        model.addAttribute("users", getUserList());
        //获得当前页
        model.addAttribute("pageNum", 1);
        //获得一页显示的条数
        model.addAttribute("pageSize", 6);
        //是否是第一页
        model.addAttribute("isFirstPage", true);
        //获得总页数
        model.addAttribute("totalPages", 2);
        //是否是最后一页
        model.addAttribute("isLastPage", false);
        
//        model.addAttribute("page", page);

        return "/test/list";
    }
	
	
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request,HttpServletResponse response) throws IOException {

//		response.sendRedirect("http://www.baidu.com");


		return "login";
	}
	
	@RequestMapping("/index")
    public String index(ModelMap map) {
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String result = obj.toString();
		map.addAttribute("hello", result);
        return "index";
    }
	
	@RequestMapping("/403")
    public String f403(ModelMap map) {
		
		map.addAttribute("failText", "登录失败!");
        return "403";
    }
	
	@ResponseBody
    @RequestMapping("/hello")
    public String hello() {
        return "Hello World";
    }
	
	
	@RequestMapping("/hello/ym")
    public String helloym(ModelMap map) {
		
		map.addAttribute("hello", "hello Thymeleaf!");
        return "login_test";
    }
	

}
