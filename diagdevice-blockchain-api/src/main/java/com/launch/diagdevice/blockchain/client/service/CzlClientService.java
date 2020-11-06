package com.launch.diagdevice.blockchain.client.service;

import java.util.Map;

import com.launch.diagdevice.blockchain.AssetReply;

/**
 * 封装元征区块链北京分院提供区块链存证业务接口，转化为dubbo接口，直接调用
 * 
 * @author ouxiangrui
 *
 */
public interface CzlClientService {
	/**
	 * 创建资产接口，资产所属人默认为元征平台账号,返回该交易的哈希
	 * 
	 * @param assetId
	 * @param assetName
	 * @param assetContent
	 * @param assetHash
	 *            md5值，数字资产类需要设置，实物资产可以不设置
	 * @return
	 */
	public String createAsset(String assetId, String assetName, String assetContent, String assetHash);

	/**
	 * 分配资产（另一种形式的创建资产），指定账号地址为资产所属人，返回该交易的哈希
	 * 
	 * @param assetOwner
	 * @param assetId
	 * @param assetName
	 * @param assetContent
	 * @param assetHash
	 *            md5值，数字资产类需要设置，实物资产可以不设置
	 * @return
	 */
	public String assignAsset(String assetOwner, String assetId, String assetName, String assetContent,
			String assetHash);

	/**
	 * 更新资产信息，返回更新后的资产完整信息
	 * 
	 * @param assetId
	 *            资产id
	 * @param assetName
	 * @param assetContent
	 * @param assetHash
	 *            md5值，数字资产类需要设置，实物资产可以不设置
	 * @return
	 */
	public String updateAsset(String assetId, String assetName, String assetContent, String assetHash);

	/**
	 * 为资产追加变更记录，前提是已经为该资产调用createAsset创建链上资产
	 * 
	 * @param assetId
	 *            资产id
	 * @param recordName
	 *            记录名称
	 * @param content
	 *            记录内容
	 * @param metaHash
	 *            记录的哈希值
	 * @return
	 */
	public String appendRecord(String assetId, String recordName, String content, String metaHash);

	/**
	 * 资产取证，根据assetId获取该资产信息
	 * 
	 * @param assetId
	 * @return
	 */
	public AssetReply assetOf(String assetId);

	/**
	 * 记录取证，通过assetid和metaHash 获取该资产的记录信息
	 * 
	 * @param assetId
	 * @param metaHash
	 * @return
	 */
	public Map<String, Object> assetRecordOf(String assetId, String metaHash);
}
