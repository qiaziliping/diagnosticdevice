// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: blockchain.proto

package com.launch.diagdevice.blockchain;

public interface SendToAddrRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:blockchain.SendToAddrRequest)
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
   * 收款人地址
   * </pre>
   *
   * <code>string addr = 2;</code>
   */
  java.lang.String getAddr();
  /**
   * <pre>
   * 收款人地址
   * </pre>
   *
   * <code>string addr = 2;</code>
   */
  com.google.protobuf.ByteString
      getAddrBytes();

  /**
   * <pre>
   * 十六进制形式的转账金额,单位为wei
   * </pre>
   *
   * <code>string value = 3;</code>
   */
  java.lang.String getValue();
  /**
   * <pre>
   * 十六进制形式的转账金额,单位为wei
   * </pre>
   *
   * <code>string value = 3;</code>
   */
  com.google.protobuf.ByteString
      getValueBytes();
}
