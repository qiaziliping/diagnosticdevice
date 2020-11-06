package com.launch.diagdevice.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 集合工具类
 * @author LIPING
 * @date 2016年5月14日 下午3:40:58
 */
public class CollectionTool {

	/**
	 * list集合分页大小
	 */
	public static final int PAGE_SIZE = 100;
//	public static final int PAGE_SIZE = 5;
	
	/**
	 * 判断list是否为空
	 * @author:LIPING
	 * @param <T>
	 */
	public static <T> boolean listIsEmpty(List<T> list) {
		if (null == list) 
			return true;
		if (list.size() == 0) 
			return true;
		return false;
	}
	/**
	 * 判断Map是否为空
	 * @author:LIPING
	 * @param <T>
	 */
	public static <K, V> boolean mapIsEmpty(Map<K,V> map) {
		if (null == map) 
			return true;
		if (map.size() == 0) 
			return true;
		return false;
	}
	
	/**
	 * list集合分页
	 * pageNo 第多少页
	 * int pageSize 分页大小
	 * @author:LIPING
	 * @throws
	 */
	public static <T> List<T> subList(List<T> list,int pageNo,Integer pageSize) {
		List<T> result = new ArrayList<T>();
		if (!listIsEmpty(list)) {
			if (null == pageSize) 
				pageSize = CollectionTool.PAGE_SIZE;
			
			int pageCount = 0;         //总页数
			int size = list.size();    //总记录数
			int rem = size % pageSize; //余数
			
			pageCount = rem == 0?size / pageSize:size / pageSize +1;
			boolean isDivision = rem == 0? true : false;  //是否被整除
			if (pageNo > pageCount || pageNo < 1) { //入参pageNo不能小于1，不能大于总页数
				return result;
			}else if (pageNo == pageCount && !isDivision) {
				result = list.subList((pageNo-1)*pageSize, (pageNo-1)*pageSize+rem);
			}else {
				result = list.subList((pageNo-1)*pageSize, pageNo*pageSize);
			}
		}
		return result;
	}
		
		
	
	
	/*public static void main(String[] args) {

		List<Integer> list = new ArrayList<>();
		for (int i = 1; i < 300; i++) {
			list.add(i);
		}
		List<Integer> result = subList(list, 0, 10);
		System.out.println(result);
	}*/
}
