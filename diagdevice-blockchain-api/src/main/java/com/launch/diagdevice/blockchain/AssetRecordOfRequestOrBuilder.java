// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: blockchain.proto

package com.launch.diagdevice.blockchain;

public interface AssetRecordOfRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:blockchain.AssetRecordOfRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string api_key = 1;</code>
   */
  java.lang.String getApiKey();
  /**
   * <code>string api_key = 1;</code>
   */
  com.google.protobuf.ByteString
      getApiKeyBytes();

  /**
   * <pre>
   * 资产ID
   * </pre>
   *
   * <code>string asset_id = 2;</code>
   */
  java.lang.String getAssetId();
  /**
   * <pre>
   * 资产ID
   * </pre>
   *
   * <code>string asset_id = 2;</code>
   */
  com.google.protobuf.ByteString
      getAssetIdBytes();

  /**
   * <pre>
   * 资产某一个记录的哈希值
   * </pre>
   *
   * <code>string meta_hash = 3;</code>
   */
  java.lang.String getMetaHash();
  /**
   * <pre>
   * 资产某一个记录的哈希值
   * </pre>
   *
   * <code>string meta_hash = 3;</code>
   */
  com.google.protobuf.ByteString
      getMetaHashBytes();
}