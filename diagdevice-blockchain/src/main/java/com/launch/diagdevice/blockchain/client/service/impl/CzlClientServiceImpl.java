package com.launch.diagdevice.blockchain.client.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.launch.diagdevice.blockchain.AppendRecordRequest;
import com.launch.diagdevice.blockchain.AssetRecordOfReply;
import com.launch.diagdevice.blockchain.AssetRecordOfRequest;
import com.launch.diagdevice.blockchain.AssetReply;
import com.launch.diagdevice.blockchain.AssetRequest;
import com.launch.diagdevice.blockchain.AssignAssetRequest;
import com.launch.diagdevice.blockchain.CreateAssetRequest;
import com.launch.diagdevice.blockchain.TransactionReply;
import com.launch.diagdevice.blockchain.UpdateAssetRequest;
import com.launch.diagdevice.blockchain.UserGrpc;
import com.launch.diagdevice.blockchain.client.service.CzlClientService;

import io.grpc.ManagedChannel;
import io.grpc.netty.GrpcSslContexts;
import io.grpc.netty.NettyChannelBuilder;

@Service
public class CzlClientServiceImpl implements CzlClientService {
	public static final Logger logger = LoggerFactory.getLogger(CzlClientServiceImpl.class);
	@Value("${blockchain.host}")
	private String bcHost;

	@Value("${blockchain.prot}")
	private Integer bcPort;

	@Value("${blockchain.apiKey}")
	private String bcApiKey;

	@Override
	public String createAsset(String assetId, String assetName, String assetContent, String assetHash) {
		// 先在区块链平台创建设备
		ManagedChannel channel = this.getChannel();
		TransactionReply reply;
		UserGrpc.UserBlockingStub stub = UserGrpc.newBlockingStub(channel);
		CreateAssetRequest request = null;
		if (assetHash == null) {
			request = CreateAssetRequest.newBuilder().setApiKey(bcApiKey).setAssetId(assetId).setAssetName(assetName)
					.build();
		} else {
			request = CreateAssetRequest.newBuilder().setApiKey(bcApiKey).setAssetId(assetId).setAssetName(assetName)
					.setAssetHash(assetHash).build();
		}
		reply = stub.createAsset(request);
		logger.info(reply.getTxnHash());
		return reply.getTxnHash();
	}

	@Override
	public String assignAsset(String assetOwner, String assetId, String assetName, String assetContent,
			String assetHash) {
		ManagedChannel channel = this.getChannel();
		TransactionReply reply;
		UserGrpc.UserBlockingStub stub = UserGrpc.newBlockingStub(channel);
		AssignAssetRequest request = null;
		request = AssignAssetRequest.newBuilder().setApiKey(bcApiKey).setAssetId(assetId).setAssetOwner(assetOwner)
				.setAssetName(assetName).setAssetContent(assetContent).build();
		reply = stub.assignAsset(request);
		logger.info(reply.getTxnHash());
		return reply.getTxnHash();
	}

	@Override
	public String updateAsset(String assetId, String assetName, String assetContent, String assetHash) {
		ManagedChannel channel = this.getChannel();
		AssetReply reply;
		UserGrpc.UserBlockingStub stub = UserGrpc.newBlockingStub(channel);
		UpdateAssetRequest request = null;
		if (assetHash == null) {
			request = UpdateAssetRequest.newBuilder().setApiKey(bcApiKey).setAssetId(assetId).setAssetName(assetName)
					.setAssetContent(assetContent).build();
		} else {
			request = UpdateAssetRequest.newBuilder().setApiKey(bcApiKey).setAssetId(assetId).setAssetName(assetName)
					.setAssetContent(assetContent).setAssetHash(assetHash).build();
		}
		reply = stub.updateAsset(request);
		logger.info(reply.getAssetHash());
		return reply.getAssetHash();
	}

	public String appendRecord(String deviceId, String recordName, String param, String hashValue) {
		ManagedChannel channel = this.getChannel();
		TransactionReply appendRecRep;
		UserGrpc.UserBlockingStub stub = UserGrpc.newBlockingStub(channel);
		AppendRecordRequest appendRecordReq = AppendRecordRequest.newBuilder().setApiKey(bcApiKey).setAssetId(deviceId)
				.setRecordName(recordName).setContent(param).setMetaHash(hashValue).build();
		appendRecRep = stub.appendRecord(appendRecordReq);
		logger.info(appendRecRep.getTxnHash());
		return appendRecRep.getTxnHash();
	}

	public AssetReply assetOf(String deviceId) {
		ManagedChannel channel = this.getChannel();
		AssetReply reply;
		UserGrpc.UserBlockingStub stub = UserGrpc.newBlockingStub(channel);
		AssetRequest request = AssetRequest.newBuilder().setApiKey(bcApiKey).setAssetId(deviceId).build();
		reply = stub.assetOf(request);
		logger.info(new Gson().toJson(reply));
		return reply;
	}

	public Map<String, Object> assetRecordOf(String deviceId, String metaHash) {
		ManagedChannel channel = this.getChannel();
		AssetRecordOfReply reply;
		HashMap<String, Object> result;
		UserGrpc.UserBlockingStub stub = UserGrpc.newBlockingStub(channel);
		AssetRecordOfRequest request = AssetRecordOfRequest.newBuilder().setApiKey(bcApiKey).setAssetId(deviceId)
				.setMetaHash(metaHash).build();
		reply = stub.assetRecordOf(request);
		result = new HashMap<String, Object>();
		result.put("recordName", reply.getRecordName());
		result.put("content", reply.getContent());
		result.put("metaHash", reply.getMetaHash());
		logger.info(new Gson().toJson(result));
		return result;
	}

	public ManagedChannel getChannel() {
		ManagedChannel channel = null;
		try {
			channel = NettyChannelBuilder.forAddress(bcHost, bcPort).sslContext(GrpcSslContexts.forClient()
					.trustManager(new File(this.getClass().getResource("/").getPath() + "//keys//server.pem")).build())
					.build();
		} catch (Exception e) {
		}
		return channel;
	}

}
