package com.launch.diagdevice.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.launch.diagdevice.common.constant.AppCodeConstant;
import com.launch.diagdevice.common.model.PagingData;
import com.launch.diagdevice.common.util.MD5Util;
import com.launch.diagdevice.dao.UserDao;
import com.launch.diagdevice.dao.UserExtDao;
import com.launch.diagdevice.entity.User;
import com.launch.diagdevice.entity.UserExt;
import com.launch.diagdevice.entity.vo.UserAndExtVo;
import com.launch.diagdevice.service.UserService;

@Service(interfaceClass = UserService.class)
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserExtDao userExtDao;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public int login(User puser,String dateTime) {
		// 用户名不存在
		int flag = 0;
		String username = puser.getUsername();
		String pwd = puser.getPassword();
//		User user = selectByUniqueFlag(username);
		User user = selectOne(puser);

		if (user == null) {
			flag = AppCodeConstant.USER_NOT_EXIST;
		} else {
			// 判断密码是否正确
			String password = user.getPassword();
			password = MD5Util.MD5(password+dateTime);
			
			if (!password.equals(pwd)) {
				flag = AppCodeConstant.PASSWORD_ERROR;
			}
		}
		
		// 修改最后一次登录时间
		if (flag == AppCodeConstant.SUCCESS) {
			UserExt userExt = new UserExt();
			userExt.setUserId(Long.parseLong(user.getId()));
			userExt.setLastTime(new Date());
			userExtDao.updateByUserId(userExt);
		}
		return flag;
	}

	@Override
	public User selectByUniqueFlag(String unique) {
		return userDao.selectByUniqueFlag(unique);
	}

	@Override
	public User selectById(Serializable id) {
		return userDao.selectById(id);
	}

	@Override
	public User selectOne(User model) {
		return userDao.selectOne(model);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Integer save(User e) {
		int count = userDao.save(e);
		if (count > 0) {
			// 保存用户扩展表
			String userId = e.getId();
			UserExt  userExt = new UserExt();
			userExt.setUserId(Long.parseLong(userId));
			userExtDao.save(userExt);
		}
		return count;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Integer update(User e) {
		return 0;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Integer delete(User e) {
		return 0;
	}

	@Override
	public List<User> selectByIndex(User model) {
		return null;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public Integer logicDelete(Serializable id) {
		return null;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	@Override
	public void batchSave(Collection<User> entities) {
	}

	@Override
	public PagingData<UserAndExtVo> selectPage(User user) {
		PagingData<UserAndExtVo> pagingData = new PagingData<UserAndExtVo>();
		
		if (null == user) {
			logger.warn("---device selectPage, but model is null---");
			return pagingData;
		}
		
		int pageNum = user.getPageNum();
		int pageSize = user.getPageSize();
		
		Page<UserAndExtVo> page = PageHelper.startPage(pageNum, pageSize);
		
		List<UserAndExtVo> listvo = this.selectUserAndExtVoByIndex(user);
		
		List<UserAndExtVo> listvo2 = page.getResult();
		logger.info("---listvo2>:"+listvo2);
		
		pagingData.setRows(listvo);
		pagingData.setTotal(page.getTotal());
		return pagingData;
	}

	/**
	 * 查询用户和用户扩展信息列表
	 * LIPING
	 */
	private List<UserAndExtVo> selectUserAndExtVoByIndex(User user) {
		
		return userDao.selectUserAndExtVoByIndex(user);
	}

}
