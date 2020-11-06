// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: blockchain.proto

package com.launch.diagdevice.blockchain;

public interface AppendRecordRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:blockchain.AppendRecordRequest)
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
   * 记录名称
   * </pre>
   *
   * <code>string record_name = 3;</code>
   */
  java.lang.String getRecordName();
  /**
   * <pre>
   * 记录名称
   * </pre>
   *
   * <code>string record_name = 3;</code>
   */
  com.google.protobuf.ByteString
      getRecordNameBytes();

  /**
   * <pre>
   * 记录内容
   * </pre>
   *
   * <code>string content = 4;</code>
   */
  java.lang.String getContent();
  /**
   * <pre>
   * 记录内容
   * </pre>
   *
   * <code>string content = 4;</code>
   */
  com.google.protobuf.ByteString
      getContentBytes();

  /**
   * <pre>
   * 记录的哈希值
   * </pre>
   *
   * <code>string meta_hash = 5;</code>
   */
  java.lang.String getMetaHash();
  /**
   * <pre>
   * 记录的哈希值
   * </pre>
   *
   * <code>string meta_hash = 5;</code>
   */
  com.google.protobuf.ByteString
      getMetaHashBytes();
}