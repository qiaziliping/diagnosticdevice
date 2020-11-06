package com.launch.diagdevice.system.service.impl;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.client.RestTemplate;

import com.alibaba.dubbo.config.annotation.Service;
import com.launch.diagdevice.common.constant.Constants;
import com.launch.diagdevice.common.util.Md5SumUtil;
import com.launch.diagdevice.common.util.ParameteToken;
import com.launch.diagdevice.common.util.TokenResult;
import com.launch.diagdevice.remoteuc.dao.impl.RemoteCallTool;
import com.launch.diagdevice.system.dao.SysProductDao;
import com.launch.diagdevice.system.dao.SysUserDao;
import com.launch.diagdevice.system.model.SysProduct;
import com.launch.diagdevice.system.model.SysUser;
import com.launch.diagdevice.system.service.SysProductManager;
import com.launch.diagdevice.system.vo.UserLoginInfo;

@Service(interfaceClass = SysProductManager.class)
public class SysProductManagerImpl implements SysProductManager {
	@Autowired
	private SysProductDao sysProductDao;
	@Autowired
	private SysUserDao sysUserDao;
	@Autowired
	private RemoteCallTool remoteCallTool;

	@Override
	@Cacheable(cacheNames = { "diagdevice_mycar" }, keyGenerator = "cacheKeyGenerator")
	public SysProduct getProcuctBySerialNo(String serialNo) {
		return sysProductDao.getProcuctBySerialNo(serialNo);
	}

	@Override
	@Cacheable(cacheNames = { "diagdevice_mycar" }, keyGenerator = "cacheKeyGenerator")
	public SysProduct getProductBySerialNoCC(String serialNo, String cc) {
		return sysProductDao.getProductBySerialNoCC(serialNo, cc);
	}

	@Override
	@Cacheable(cacheNames = { "diagdevice_mycar" }, keyGenerator = "cacheKeyGenerator")
	public ArrayList<String> filterSerialNoByVenderOrFiliale(Integer venderId, Integer filialeId,
			List<String> serialNoList) {
		return sysProductDao.filterSerialNoByVenderOrFiliale(venderId, filialeId, serialNoList);
	}

	@Override
	// @Cacheable(cacheNames={"diagdevice_mycar"},keyGenerator="cacheKeyGenerator")
	public UserLoginInfo getProductUserLoginInfoBySerialNo(String serialNo) throws Exception {
		SysProduct sysProduct = getProcuctBySerialNo(serialNo);
		SysUser sysUser = sysUserDao.getSysUser(sysProduct.getUserId());
		String token = remoteCallTool.getToken(sysUser.getCc());
		System.out.println("------" + token);
		UserLoginInfo info = new UserLoginInfo();
		info.setCc(sysUser.getCc());
		info.setUserId(sysUser.getUserId());
		info.setToken(token);
		info.setSerialNo(serialNo);

		info.setPdtTypeId(sysProduct.getPdtTypeId());
		info.setSoftConfId(sysProduct.getSoftConfId());

		return info;
	}

}
