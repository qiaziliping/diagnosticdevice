package com.launch.diagdevice.blockchain.dao.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.launch.diagdevice.blockchain.dao.CzlAssetDao;
import com.launch.diagdevice.blockchain.dao.mapper.CzlAssetMapper;
import com.launch.diagdevice.blockchain.model.CzlAsset;

@Component
public class CzlAssetDaoImpl implements CzlAssetDao {
	@Autowired
	private CzlAssetMapper czlAssetMapper;

	@Override
	public CzlAsset saveCzlAsset(CzlAsset asset) {
		asset.setAssetId(UUID.randomUUID().toString());
		// 保存到数据库中
		czlAssetMapper.saveCzlAsset(asset);
		return asset;
	}

	@Override
	public CzlAsset queryCzlAsset(String assetName, String assetType) {
		CzlAsset czlAsset = new CzlAsset();
		czlAsset.setAssetType(assetType);
		czlAsset.setAssetName(assetName);
		return czlAssetMapper.queryCzlAsset(czlAsset);
	}

	@Override
	public CzlAsset queryCzlUserAsset(Integer orderId) {
		return czlAssetMapper.queryCzlUserAsset(orderId);
	}

}
