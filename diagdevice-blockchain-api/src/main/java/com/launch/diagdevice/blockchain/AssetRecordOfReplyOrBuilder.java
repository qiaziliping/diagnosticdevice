// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: blockchain.proto

package com.launch.diagdevice.blockchain;

public interface AssetRecordOfReplyOrBuilder extends
    // @@protoc_insertion_point(interface_extends:blockchain.AssetRecordOfReply)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <pre>
   * 记录名称
   * </pre>
   *
   * <code>string record_name = 1;</code>
   */
  java.lang.String getRecordName();
  /**
   * <pre>
   * 记录名称
   * </pre>
   *
   * <code>string record_name = 1;</code>
   */
  com.google.protobuf.ByteString
      getRecordNameBytes();

  /**
   * <pre>
   * 记录内容
   * </pre>
   *
   * <code>string content = 2;</code>
   */
  java.lang.String getContent();
  /**
   * <pre>
   * 记录内容
   * </pre>
   *
   * <code>string content = 2;</code>
   */
  com.google.protobuf.ByteString
      getContentBytes();

  /**
   * <pre>
   * 记录的哈希值
   * </pre>
   *
   * <code>string meta_hash = 3;</code>
   */
  java.lang.String getMetaHash();
  /**
   * <pre>
   * 记录的哈希值
   * </pre>
   *
   * <code>string meta_hash = 3;</code>
   */
  com.google.protobuf.ByteString
      getMetaHashBytes();

  /**
   * <pre>
   * 记录更新时间
   * </pre>
   *
   * <code>uint64 update_time = 4;</code>
   */
  long getUpdateTime();
}