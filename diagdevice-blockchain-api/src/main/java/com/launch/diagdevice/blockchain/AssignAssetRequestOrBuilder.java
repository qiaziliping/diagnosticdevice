// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: blockchain.proto

package com.launch.diagdevice.blockchain;

public interface AssignAssetRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:blockchain.AssignAssetRequest)
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
   * 资产所属人的钱包地址
   * </pre>
   *
   * <code>string asset_owner = 2;</code>
   */
  java.lang.String getAssetOwner();
  /**
   * <pre>
   * 资产所属人的钱包地址
   * </pre>
   *
   * <code>string asset_owner = 2;</code>
   */
  com.google.protobuf.ByteString
      getAssetOwnerBytes();

  /**
   * <pre>
   * 资产 ID
   * </pre>
   *
   * <code>string asset_id = 3;</code>
   */
  java.lang.String getAssetId();
  /**
   * <pre>
   * 资产 ID
   * </pre>
   *
   * <code>string asset_id = 3;</code>
   */
  com.google.protobuf.ByteString
      getAssetIdBytes();

  /**
   * <pre>
   * 资产名称
   * </pre>
   *
   * <code>string asset_name = 4;</code>
   */
  java.lang.String getAssetName();
  /**
   * <pre>
   * 资产名称
   * </pre>
   *
   * <code>string asset_name = 4;</code>
   */
  com.google.protobuf.ByteString
      getAssetNameBytes();

  /**
   * <pre>
   * 附加内容
   * </pre>
   *
   * <code>string asset_content = 5;</code>
   */
  java.lang.String getAssetContent();
  /**
   * <pre>
   * 附加内容
   * </pre>
   *
   * <code>string asset_content = 5;</code>
   */
  com.google.protobuf.ByteString
      getAssetContentBytes();

  /**
   * <pre>
   * 资产 Hash
   * </pre>
   *
   * <code>string asset_hash = 6;</code>
   */
  java.lang.String getAssetHash();
  /**
   * <pre>
   * 资产 Hash
   * </pre>
   *
   * <code>string asset_hash = 6;</code>
   */
  com.google.protobuf.ByteString
      getAssetHashBytes();
}
