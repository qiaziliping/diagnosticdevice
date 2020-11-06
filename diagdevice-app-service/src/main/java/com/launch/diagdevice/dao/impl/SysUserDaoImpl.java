package com.launch.diagdevice.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.launch.diagdevice.common.dao.impl.BaseDAOImpl;
import com.launch.diagdevice.common.dao.mapper.BaseMapper;
import com.launch.diagdevice.dao.SysUserDao;
import com.launch.diagdevice.dao.mapper.SysUserMapper;
import com.launch.diagdevice.entity.SysUser;
import com.launch.diagdevice.entity.vo.SysUserVo;

@Repository
public class SysUserDaoImpl extends BaseDAOImpl<SysUser> implements SysUserDao {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Override
	public BaseMapper<SysUser> getMapper() {
		return sysUserMapper;
	}

	/** 1表示用户已激活  */
	public static Integer IS_ACTIVE_YES = 1;

	@Override
	public SysUser selectById(Serializable id) {
		if (id == null) {
			logger.warn("---sysUserDaoImpl selectById but param is null---id={}", id);
			return null;
		}
		return sysUserMapper.selectById(id);
	}

	@Override
	public SysUser selectOne(SysUser model) {
		if (model == null) {
			logger.warn("---sysUserDaoImpl selectOne but param is null---");
			return null;
		}
		return sysUserMapper.selectOne(model);
	}

	@Override
	public Integer save(SysUser model) {
		if (model == null) {
			logger.warn("---sysUserDaoImpl save but param is null---model={}", model);
			return 0;
		}
		model.setCreateTime(new Date());
		model.setIsActive(IS_ACTIVE_YES);
		if (model.getSex() == null) {
			model.setSex(1);
		}
		return sysUserMapper.save(model);
	}

	@Override
	public Integer update(SysUser model) {
		if (model == null || StringUtils.isEmpty(model.getId())) {
			logger.warn("---sysUserDaoImpl update but param is null---model={}", model);
			return 0;
		}
		model.setUpdateTime(new Date());
		return sysUserMapper.update(model);
	}

	@Override
	public Integer delete(SysUser model) {
		if (model == null || StringUtils.isEmpty(model.getId())) {
			logger.warn("---sysUserDaoImpl delete but param is null---model={}", model);
			return 0;
		}
		model.setUpdateTime(new Date());
		return sysUserMapper.delete(model);
	}

	@Override
	public List<SysUser> selectByIndex(SysUser model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer logicDelete(Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void batchSave(Collection<SysUser> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<SysUserVo> selectVoByIndex(SysUser model) {
		List<SysUserVo> list = new ArrayList<SysUserVo>();

		List<SysUserVo> temp = sysUserMapper.selectVoByIndex(model);
		return null == temp ? list : temp;
	}

}
