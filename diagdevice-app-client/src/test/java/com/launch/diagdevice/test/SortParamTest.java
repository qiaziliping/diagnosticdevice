package com.launch.diagdevice.test;

import java.util.Map;
import java.util.TreeMap;

public class SortParamTest {
	
	
	public static void main(String[] args) {
		Map<String,Object> map = new TreeMap<String,Object>();
		
//		{diagEndTime}/{diagStartTime}/{orderNo}/{sign}/{softName}/{vinCode}
//		`parent_id` bigint(20) DEFAULT NULL COMMENT '父ID',
//		  `sort` int(11) DEFAULT NULL COMMENT '排序',
//		  `resource_name` varchar(100) DEFAULT NULL COMMENT '资源名称',
//		  `resource_type` int(1) DEFAULT NULL COMMENT '资源类型 1预留 2：一级菜单 3：二级菜单 4：三级菜单 5：权限',
//		  `resource_code` varchar(100) DEFAULT NULL COMMENT '资源编码 format as function:permission',
		map.put("parent_id", "");
		map.put("id", "");
		map.put("sort", "");
		map.put("resource_name", "");
		map.put("resource_type", "");
		map.put("resource_code", "");
		
		System.out.println(map);
	}

}
