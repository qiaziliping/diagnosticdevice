package com.launch.diagdevice.blockchain;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.14.0)",
    comments = "Source: blockchain.proto")
public final class UserGrpc {

  private UserGrpc() {}

  public static final String SERVICE_NAME = "blockchain.User";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.launch.diagdevice.blockchain.CreateAssetRequest,
      com.launch.diagdevice.blockchain.TransactionReply> getCreateAssetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateAsset",
      requestType = com.launch.diagdevice.blockchain.CreateAssetRequest.class,
      responseType = com.launch.diagdevice.blockchain.TransactionReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.launch.diagdevice.blockchain.CreateAssetRequest,
      com.launch.diagdevice.blockchain.TransactionReply> getCreateAssetMethod() {
    io.grpc.MethodDescriptor<com.launch.diagdevice.blockchain.CreateAssetRequest, com.launch.diagdevice.blockchain.TransactionReply> getCreateAssetMethod;
    if ((getCreateAssetMethod = UserGrpc.getCreateAssetMethod) == null) {
      synchronized (UserGrpc.class) {
        if ((getCreateAssetMethod = UserGrpc.getCreateAssetMethod) == null) {
          UserGrpc.getCreateAssetMethod = getCreateAssetMethod = 
              io.grpc.MethodDescriptor.<com.launch.diagdevice.blockchain.CreateAssetRequest, com.launch.diagdevice.blockchain.TransactionReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "blockchain.User", "CreateAsset"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.launch.diagdevice.blockchain.CreateAssetRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.launch.diagdevice.blockchain.TransactionReply.getDefaultInstance()))
                  .setSchemaDescriptor(new UserMethodDescriptorSupplier("CreateAsset"))
                  .build();
          }
        }
     }
     return getCreateAssetMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.launch.diagdevice.blockchain.AssignAssetRequest,
      com.launch.diagdevice.blockchain.TransactionReply> getAssignAssetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AssignAsset",
      requestType = com.launch.diagdevice.blockchain.AssignAssetRequest.class,
      responseType = com.launch.diagdevice.blockchain.TransactionReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.launch.diagdevice.blockchain.AssignAssetRequest,
      com.launch.diagdevice.blockchain.TransactionReply> getAssignAssetMethod() {
    io.grpc.MethodDescriptor<com.launch.diagdevice.blockchain.AssignAssetRequest, com.launch.diagdevice.blockchain.TransactionReply> getAssignAssetMethod;
    if ((getAssignAssetMethod = UserGrpc.getAssignAssetMethod) == null) {
      synchronized (UserGrpc.class) {
        if ((getAssignAssetMethod = UserGrpc.getAssignAssetMethod) == null) {
          UserGrpc.getAssignAssetMethod = getAssignAssetMethod = 
              io.grpc.MethodDescriptor.<com.launch.diagdevice.blockchain.AssignAssetRequest, com.launch.diagdevice.blockchain.TransactionReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "blockchain.User", "AssignAsset"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.launch.diagdevice.blockchain.AssignAssetRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.launch.diagdevice.blockchain.TransactionReply.getDefaultInstance()))
                  .setSchemaDescriptor(new UserMethodDescriptorSupplier("AssignAsset"))
                  .build();
          }
        }
     }
     return getAssignAssetMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.launch.diagdevice.blockchain.UpdateAssetRequest,
      com.launch.diagdevice.blockchain.AssetReply> getUpdateAssetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateAsset",
      requestType = com.launch.diagdevice.blockchain.UpdateAssetRequest.class,
      responseType = com.launch.diagdevice.blockchain.AssetReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.launch.diagdevice.blockchain.UpdateAssetRequest,
      com.launch.diagdevice.blockchain.AssetReply> getUpdateAssetMethod() {
    io.grpc.MethodDescriptor<com.launch.diagdevice.blockchain.UpdateAssetRequest, com.launch.diagdevice.blockchain.AssetReply> getUpdateAssetMethod;
    if ((getUpdateAssetMethod = UserGrpc.getUpdateAssetMethod) == null) {
      synchronized (UserGrpc.class) {
        if ((getUpdateAssetMethod = UserGrpc.getUpdateAssetMethod) == null) {
          UserGrpc.getUpdateAssetMethod = getUpdateAssetMethod = 
              io.grpc.MethodDescriptor.<com.launch.diagdevice.blockchain.UpdateAssetRequest, com.launch.diagdevice.blockchain.AssetReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "blockchain.User", "UpdateAsset"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.launch.diagdevice.blockchain.UpdateAssetRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.launch.diagdevice.blockchain.AssetReply.getDefaultInstance()))
                  .setSchemaDescriptor(new UserMethodDescriptorSupplier("UpdateAsset"))
                  .build();
          }
        }
     }
     return getUpdateAssetMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.launch.diagdevice.blockchain.AppendRecordRequest,
      com.launch.diagdevice.blockchain.TransactionReply> getAppendRecordMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AppendRecord",
      requestType = com.launch.diagdevice.blockchain.AppendRecordRequest.class,
      responseType = com.launch.diagdevice.blockchain.TransactionReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.launch.diagdevice.blockchain.AppendRecordRequest,
      com.launch.diagdevice.blockchain.TransactionReply> getAppendRecordMethod() {
    io.grpc.MethodDescriptor<com.launch.diagdevice.blockchain.AppendRecordRequest, com.launch.diagdevice.blockchain.TransactionReply> getAppendRecordMethod;
    if ((getAppendRecordMethod = UserGrpc.getAppendRecordMethod) == null) {
      synchronized (UserGrpc.class) {
        if ((getAppendRecordMethod = UserGrpc.getAppendRecordMethod) == null) {
          UserGrpc.getAppendRecordMethod = getAppendRecordMethod = 
              io.grpc.MethodDescriptor.<com.launch.diagdevice.blockchain.AppendRecordRequest, com.launch.diagdevice.blockchain.TransactionReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "blockchain.User", "AppendRecord"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.launch.diagdevice.blockchain.AppendRecordRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.launch.diagdevice.blockchain.TransactionReply.getDefaultInstance()))
                  .setSchemaDescriptor(new UserMethodDescriptorSupplier("AppendRecord"))
                  .build();
          }
        }
     }
     return getAppendRecordMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.launch.diagdevice.blockchain.Request,
      com.launch.diagdevice.blockchain.AssetTotalSupplyReply> getAssetTotalSupplyMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AssetTotalSupply",
      requestType = com.launch.diagdevice.blockchain.Request.class,
      responseType = com.launch.diagdevice.blockchain.AssetTotalSupplyReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.launch.diagdevice.blockchain.Request,
      com.launch.diagdevice.blockchain.AssetTotalSupplyReply> getAssetTotalSupplyMethod() {
    io.grpc.MethodDescriptor<com.launch.diagdevice.blockchain.Request, com.launch.diagdevice.blockchain.AssetTotalSupplyReply> getAssetTotalSupplyMethod;
    if ((getAssetTotalSupplyMethod = UserGrpc.getAssetTotalSupplyMethod) == null) {
      synchronized (UserGrpc.class) {
        if ((getAssetTotalSupplyMethod = UserGrpc.getAssetTotalSupplyMethod) == null) {
          UserGrpc.getAssetTotalSupplyMethod = getAssetTotalSupplyMethod = 
              io.grpc.MethodDescriptor.<com.launch.diagdevice.blockchain.Request, com.launch.diagdevice.blockchain.AssetTotalSupplyReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "blockchain.User", "AssetTotalSupply"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.launch.diagdevice.blockchain.Request.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.launch.diagdevice.blockchain.AssetTotalSupplyReply.getDefaultInstance()))
                  .setSchemaDescriptor(new UserMethodDescriptorSupplier("AssetTotalSupply"))
                  .build();
          }
        }
     }
     return getAssetTotalSupplyMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.launch.diagdevice.blockchain.AssetRequest,
      com.launch.diagdevice.blockchain.AssetReply> getAssetOfMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AssetOf",
      requestType = com.launch.diagdevice.blockchain.AssetRequest.class,
      responseType = com.launch.diagdevice.blockchain.AssetReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.launch.diagdevice.blockchain.AssetRequest,
      com.launch.diagdevice.blockchain.AssetReply> getAssetOfMethod() {
    io.grpc.MethodDescriptor<com.launch.diagdevice.blockchain.AssetRequest, com.launch.diagdevice.blockchain.AssetReply> getAssetOfMethod;
    if ((getAssetOfMethod = UserGrpc.getAssetOfMethod) == null) {
      synchronized (UserGrpc.class) {
        if ((getAssetOfMethod = UserGrpc.getAssetOfMethod) == null) {
          UserGrpc.getAssetOfMethod = getAssetOfMethod = 
              io.grpc.MethodDescriptor.<com.launch.diagdevice.blockchain.AssetRequest, com.launch.diagdevice.blockchain.AssetReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "blockchain.User", "AssetOf"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.launch.diagdevice.blockchain.AssetRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.launch.diagdevice.blockchain.AssetReply.getDefaultInstance()))
                  .setSchemaDescriptor(new UserMethodDescriptorSupplier("AssetOf"))
                  .build();
          }
        }
     }
     return getAssetOfMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.launch.diagdevice.blockchain.AssetRequest,
      com.launch.diagdevice.blockchain.AssetRecordCountReply> getAssetRecordCountMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AssetRecordCount",
      requestType = com.launch.diagdevice.blockchain.AssetRequest.class,
      responseType = com.launch.diagdevice.blockchain.AssetRecordCountReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.launch.diagdevice.blockchain.AssetRequest,
      com.launch.diagdevice.blockchain.AssetRecordCountReply> getAssetRecordCountMethod() {
    io.grpc.MethodDescriptor<com.launch.diagdevice.blockchain.AssetRequest, com.launch.diagdevice.blockchain.AssetRecordCountReply> getAssetRecordCountMethod;
    if ((getAssetRecordCountMethod = UserGrpc.getAssetRecordCountMethod) == null) {
      synchronized (UserGrpc.class) {
        if ((getAssetRecordCountMethod = UserGrpc.getAssetRecordCountMethod) == null) {
          UserGrpc.getAssetRecordCountMethod = getAssetRecordCountMethod = 
              io.grpc.MethodDescriptor.<com.launch.diagdevice.blockchain.AssetRequest, com.launch.diagdevice.blockchain.AssetRecordCountReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "blockchain.User", "AssetRecordCount"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.launch.diagdevice.blockchain.AssetRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.launch.diagdevice.blockchain.AssetRecordCountReply.getDefaultInstance()))
                  .setSchemaDescriptor(new UserMethodDescriptorSupplier("AssetRecordCount"))
                  .build();
          }
        }
     }
     return getAssetRecordCountMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.launch.diagdevice.blockchain.AssetRecordOfRequest,
      com.launch.diagdevice.blockchain.AssetRecordOfReply> getAssetRecordOfMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "AssetRecordOf",
      requestType = com.launch.diagdevice.blockchain.AssetRecordOfRequest.class,
      responseType = com.launch.diagdevice.blockchain.AssetRecordOfReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.launch.diagdevice.blockchain.AssetRecordOfRequest,
      com.launch.diagdevice.blockchain.AssetRecordOfReply> getAssetRecordOfMethod() {
    io.grpc.MethodDescriptor<com.launch.diagdevice.blockchain.AssetRecordOfRequest, com.launch.diagdevice.blockchain.AssetRecordOfReply> getAssetRecordOfMethod;
    if ((getAssetRecordOfMethod = UserGrpc.getAssetRecordOfMethod) == null) {
      synchronized (UserGrpc.class) {
        if ((getAssetRecordOfMethod = UserGrpc.getAssetRecordOfMethod) == null) {
          UserGrpc.getAssetRecordOfMethod = getAssetRecordOfMethod = 
              io.grpc.MethodDescriptor.<com.launch.diagdevice.blockchain.AssetRecordOfRequest, com.launch.diagdevice.blockchain.AssetRecordOfReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "blockchain.User", "AssetRecordOf"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.launch.diagdevice.blockchain.AssetRecordOfRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.launch.diagdevice.blockchain.AssetRecordOfReply.getDefaultInstance()))
                  .setSchemaDescriptor(new UserMethodDescriptorSupplier("AssetRecordOf"))
                  .build();
          }
        }
     }
     return getAssetRecordOfMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.launch.diagdevice.blockchain.SendToAddrRequest,
      com.launch.diagdevice.blockchain.TransactionReply> getSendToAddrMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SendToAddr",
      requestType = com.launch.diagdevice.blockchain.SendToAddrRequest.class,
      responseType = com.launch.diagdevice.blockchain.TransactionReply.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.launch.diagdevice.blockchain.SendToAddrRequest,
      com.launch.diagdevice.blockchain.TransactionReply> getSendToAddrMethod() {
    io.grpc.MethodDescriptor<com.launch.diagdevice.blockchain.SendToAddrRequest, com.launch.diagdevice.blockchain.TransactionReply> getSendToAddrMethod;
    if ((getSendToAddrMethod = UserGrpc.getSendToAddrMethod) == null) {
      synchronized (UserGrpc.class) {
        if ((getSendToAddrMethod = UserGrpc.getSendToAddrMethod) == null) {
          UserGrpc.getSendToAddrMethod = getSendToAddrMethod = 
              io.grpc.MethodDescriptor.<com.launch.diagdevice.blockchain.SendToAddrRequest, com.launch.diagdevice.blockchain.TransactionReply>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "blockchain.User", "SendToAddr"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.launch.diagdevice.blockchain.SendToAddrRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.launch.diagdevice.blockchain.TransactionReply.getDefaultInstance()))
                  .setSchemaDescriptor(new UserMethodDescriptorSupplier("SendToAddr"))
                  .build();
          }
        }
     }
     return getSendToAddrMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static UserStub newStub(io.grpc.Channel channel) {
    return new UserStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static UserBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new UserBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static UserFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new UserFutureStub(channel);
  }

  /**
   */
  public static abstract class UserImplBase implements io.grpc.BindableService {

    /**
     */
    public void createAsset(com.launch.diagdevice.blockchain.CreateAssetRequest request,
        io.grpc.stub.StreamObserver<com.launch.diagdevice.blockchain.TransactionReply> responseObserver) {
      asyncUnimplementedUnaryCall(getCreateAssetMethod(), responseObserver);
    }

    /**
     */
    public void assignAsset(com.launch.diagdevice.blockchain.AssignAssetRequest request,
        io.grpc.stub.StreamObserver<com.launch.diagdevice.blockchain.TransactionReply> responseObserver) {
      asyncUnimplementedUnaryCall(getAssignAssetMethod(), responseObserver);
    }

    /**
     */
    public void updateAsset(com.launch.diagdevice.blockchain.UpdateAssetRequest request,
        io.grpc.stub.StreamObserver<com.launch.diagdevice.blockchain.AssetReply> responseObserver) {
      asyncUnimplementedUnaryCall(getUpdateAssetMethod(), responseObserver);
    }

    /**
     */
    public void appendRecord(com.launch.diagdevice.blockchain.AppendRecordRequest request,
        io.grpc.stub.StreamObserver<com.launch.diagdevice.blockchain.TransactionReply> responseObserver) {
      asyncUnimplementedUnaryCall(getAppendRecordMethod(), responseObserver);
    }

    /**
     */
    public void assetTotalSupply(com.launch.diagdevice.blockchain.Request request,
        io.grpc.stub.StreamObserver<com.launch.diagdevice.blockchain.AssetTotalSupplyReply> responseObserver) {
      asyncUnimplementedUnaryCall(getAssetTotalSupplyMethod(), responseObserver);
    }

    /**
     */
    public void assetOf(com.launch.diagdevice.blockchain.AssetRequest request,
        io.grpc.stub.StreamObserver<com.launch.diagdevice.blockchain.AssetReply> responseObserver) {
      asyncUnimplementedUnaryCall(getAssetOfMethod(), responseObserver);
    }

    /**
     */
    public void assetRecordCount(com.launch.diagdevice.blockchain.AssetRequest request,
        io.grpc.stub.StreamObserver<com.launch.diagdevice.blockchain.AssetRecordCountReply> responseObserver) {
      asyncUnimplementedUnaryCall(getAssetRecordCountMethod(), responseObserver);
    }

    /**
     */
    public void assetRecordOf(com.launch.diagdevice.blockchain.AssetRecordOfRequest request,
        io.grpc.stub.StreamObserver<com.launch.diagdevice.blockchain.AssetRecordOfReply> responseObserver) {
      asyncUnimplementedUnaryCall(getAssetRecordOfMethod(), responseObserver);
    }

    /**
     */
    public void sendToAddr(com.launch.diagdevice.blockchain.SendToAddrRequest request,
        io.grpc.stub.StreamObserver<com.launch.diagdevice.blockchain.TransactionReply> responseObserver) {
      asyncUnimplementedUnaryCall(getSendToAddrMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getCreateAssetMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.launch.diagdevice.blockchain.CreateAssetRequest,
                com.launch.diagdevice.blockchain.TransactionReply>(
                  this, METHODID_CREATE_ASSET)))
          .addMethod(
            getAssignAssetMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.launch.diagdevice.blockchain.AssignAssetRequest,
                com.launch.diagdevice.blockchain.TransactionReply>(
                  this, METHODID_ASSIGN_ASSET)))
          .addMethod(
            getUpdateAssetMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.launch.diagdevice.blockchain.UpdateAssetRequest,
                com.launch.diagdevice.blockchain.AssetReply>(
                  this, METHODID_UPDATE_ASSET)))
          .addMethod(
            getAppendRecordMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.launch.diagdevice.blockchain.AppendRecordRequest,
                com.launch.diagdevice.blockchain.TransactionReply>(
                  this, METHODID_APPEND_RECORD)))
          .addMethod(
            getAssetTotalSupplyMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.launch.diagdevice.blockchain.Request,
                com.launch.diagdevice.blockchain.AssetTotalSupplyReply>(
                  this, METHODID_ASSET_TOTAL_SUPPLY)))
          .addMethod(
            getAssetOfMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.launch.diagdevice.blockchain.AssetRequest,
                com.launch.diagdevice.blockchain.AssetReply>(
                  this, METHODID_ASSET_OF)))
          .addMethod(
            getAssetRecordCountMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.launch.diagdevice.blockchain.AssetRequest,
                com.launch.diagdevice.blockchain.AssetRecordCountReply>(
                  this, METHODID_ASSET_RECORD_COUNT)))
          .addMethod(
            getAssetRecordOfMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.launch.diagdevice.blockchain.AssetRecordOfRequest,
                com.launch.diagdevice.blockchain.AssetRecordOfReply>(
                  this, METHODID_ASSET_RECORD_OF)))
          .addMethod(
            getSendToAddrMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.launch.diagdevice.blockchain.SendToAddrRequest,
                com.launch.diagdevice.blockchain.TransactionReply>(
                  this, METHODID_SEND_TO_ADDR)))
          .build();
    }
  }

  /**
   */
  public static final class UserStub extends io.grpc.stub.AbstractStub<UserStub> {
    private UserStub(io.grpc.Channel channel) {
      super(channel);
    }

    private UserStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new UserStub(channel, callOptions);
    }

    /**
     */
    public void createAsset(com.launch.diagdevice.blockchain.CreateAssetRequest request,
        io.grpc.stub.StreamObserver<com.launch.diagdevice.blockchain.TransactionReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getCreateAssetMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void assignAsset(com.launch.diagdevice.blockchain.AssignAssetRequest request,
        io.grpc.stub.StreamObserver<com.launch.diagdevice.blockchain.TransactionReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getAssignAssetMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateAsset(com.launch.diagdevice.blockchain.UpdateAssetRequest request,
        io.grpc.stub.StreamObserver<com.launch.diagdevice.blockchain.AssetReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getUpdateAssetMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void appendRecord(com.launch.diagdevice.blockchain.AppendRecordRequest request,
        io.grpc.stub.StreamObserver<com.launch.diagdevice.blockchain.TransactionReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getAppendRecordMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void assetTotalSupply(com.launch.diagdevice.blockchain.Request request,
        io.grpc.stub.StreamObserver<com.launch.diagdevice.blockchain.AssetTotalSupplyReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getAssetTotalSupplyMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void assetOf(com.launch.diagdevice.blockchain.AssetRequest request,
        io.grpc.stub.StreamObserver<com.launch.diagdevice.blockchain.AssetReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getAssetOfMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void assetRecordCount(com.launch.diagdevice.blockchain.AssetRequest request,
        io.grpc.stub.StreamObserver<com.launch.diagdevice.blockchain.AssetRecordCountReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getAssetRecordCountMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void assetRecordOf(com.launch.diagdevice.blockchain.AssetRecordOfRequest request,
        io.grpc.stub.StreamObserver<com.launch.diagdevice.blockchain.AssetRecordOfReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getAssetRecordOfMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void sendToAddr(com.launch.diagdevice.blockchain.SendToAddrRequest request,
        io.grpc.stub.StreamObserver<com.launch.diagdevice.blockchain.TransactionReply> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getSendToAddrMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class UserBlockingStub extends io.grpc.stub.AbstractStub<UserBlockingStub> {
    private UserBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private UserBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new UserBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.launch.diagdevice.blockchain.TransactionReply createAsset(com.launch.diagdevice.blockchain.CreateAssetRequest request) {
      return blockingUnaryCall(
          getChannel(), getCreateAssetMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.launch.diagdevice.blockchain.TransactionReply assignAsset(com.launch.diagdevice.blockchain.AssignAssetRequest request) {
      return blockingUnaryCall(
          getChannel(), getAssignAssetMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.launch.diagdevice.blockchain.AssetReply updateAsset(com.launch.diagdevice.blockchain.UpdateAssetRequest request) {
      return blockingUnaryCall(
          getChannel(), getUpdateAssetMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.launch.diagdevice.blockchain.TransactionReply appendRecord(com.launch.diagdevice.blockchain.AppendRecordRequest request) {
      return blockingUnaryCall(
          getChannel(), getAppendRecordMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.launch.diagdevice.blockchain.AssetTotalSupplyReply assetTotalSupply(com.launch.diagdevice.blockchain.Request request) {
      return blockingUnaryCall(
          getChannel(), getAssetTotalSupplyMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.launch.diagdevice.blockchain.AssetReply assetOf(com.launch.diagdevice.blockchain.AssetRequest request) {
      return blockingUnaryCall(
          getChannel(), getAssetOfMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.launch.diagdevice.blockchain.AssetRecordCountReply assetRecordCount(com.launch.diagdevice.blockchain.AssetRequest request) {
      return blockingUnaryCall(
          getChannel(), getAssetRecordCountMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.launch.diagdevice.blockchain.AssetRecordOfReply assetRecordOf(com.launch.diagdevice.blockchain.AssetRecordOfRequest request) {
      return blockingUnaryCall(
          getChannel(), getAssetRecordOfMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.launch.diagdevice.blockchain.TransactionReply sendToAddr(com.launch.diagdevice.blockchain.SendToAddrRequest request) {
      return blockingUnaryCall(
          getChannel(), getSendToAddrMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class UserFutureStub extends io.grpc.stub.AbstractStub<UserFutureStub> {
    private UserFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private UserFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new UserFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.launch.diagdevice.blockchain.TransactionReply> createAsset(
        com.launch.diagdevice.blockchain.CreateAssetRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getCreateAssetMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.launch.diagdevice.blockchain.TransactionReply> assignAsset(
        com.launch.diagdevice.blockchain.AssignAssetRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getAssignAssetMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.launch.diagdevice.blockchain.AssetReply> updateAsset(
        com.launch.diagdevice.blockchain.UpdateAssetRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getUpdateAssetMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.launch.diagdevice.blockchain.TransactionReply> appendRecord(
        com.launch.diagdevice.blockchain.AppendRecordRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getAppendRecordMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.launch.diagdevice.blockchain.AssetTotalSupplyReply> assetTotalSupply(
        com.launch.diagdevice.blockchain.Request request) {
      return futureUnaryCall(
          getChannel().newCall(getAssetTotalSupplyMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.launch.diagdevice.blockchain.AssetReply> assetOf(
        com.launch.diagdevice.blockchain.AssetRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getAssetOfMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.launch.diagdevice.blockchain.AssetRecordCountReply> assetRecordCount(
        com.launch.diagdevice.blockchain.AssetRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getAssetRecordCountMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.launch.diagdevice.blockchain.AssetRecordOfReply> assetRecordOf(
        com.launch.diagdevice.blockchain.AssetRecordOfRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getAssetRecordOfMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.launch.diagdevice.blockchain.TransactionReply> sendToAddr(
        com.launch.diagdevice.blockchain.SendToAddrRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getSendToAddrMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_ASSET = 0;
  private static final int METHODID_ASSIGN_ASSET = 1;
  private static final int METHODID_UPDATE_ASSET = 2;
  private static final int METHODID_APPEND_RECORD = 3;
  private static final int METHODID_ASSET_TOTAL_SUPPLY = 4;
  private static final int METHODID_ASSET_OF = 5;
  private static final int METHODID_ASSET_RECORD_COUNT = 6;
  private static final int METHODID_ASSET_RECORD_OF = 7;
  private static final int METHODID_SEND_TO_ADDR = 8;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final UserImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(UserImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_ASSET:
          serviceImpl.createAsset((com.launch.diagdevice.blockchain.CreateAssetRequest) request,
              (io.grpc.stub.StreamObserver<com.launch.diagdevice.blockchain.TransactionReply>) responseObserver);
          break;
        case METHODID_ASSIGN_ASSET:
          serviceImpl.assignAsset((com.launch.diagdevice.blockchain.AssignAssetRequest) request,
              (io.grpc.stub.StreamObserver<com.launch.diagdevice.blockchain.TransactionReply>) responseObserver);
          break;
        case METHODID_UPDATE_ASSET:
          serviceImpl.updateAsset((com.launch.diagdevice.blockchain.UpdateAssetRequest) request,
              (io.grpc.stub.StreamObserver<com.launch.diagdevice.blockchain.AssetReply>) responseObserver);
          break;
        case METHODID_APPEND_RECORD:
          serviceImpl.appendRecord((com.launch.diagdevice.blockchain.AppendRecordRequest) request,
              (io.grpc.stub.StreamObserver<com.launch.diagdevice.blockchain.TransactionReply>) responseObserver);
          break;
        case METHODID_ASSET_TOTAL_SUPPLY:
          serviceImpl.assetTotalSupply((com.launch.diagdevice.blockchain.Request) request,
              (io.grpc.stub.StreamObserver<com.launch.diagdevice.blockchain.AssetTotalSupplyReply>) responseObserver);
          break;
        case METHODID_ASSET_OF:
          serviceImpl.assetOf((com.launch.diagdevice.blockchain.AssetRequest) request,
              (io.grpc.stub.StreamObserver<com.launch.diagdevice.blockchain.AssetReply>) responseObserver);
          break;
        case METHODID_ASSET_RECORD_COUNT:
          serviceImpl.assetRecordCount((com.launch.diagdevice.blockchain.AssetRequest) request,
              (io.grpc.stub.StreamObserver<com.launch.diagdevice.blockchain.AssetRecordCountReply>) responseObserver);
          break;
        case METHODID_ASSET_RECORD_OF:
          serviceImpl.assetRecordOf((com.launch.diagdevice.blockchain.AssetRecordOfRequest) request,
              (io.grpc.stub.StreamObserver<com.launch.diagdevice.blockchain.AssetRecordOfReply>) responseObserver);
          break;
        case METHODID_SEND_TO_ADDR:
          serviceImpl.sendToAddr((com.launch.diagdevice.blockchain.SendToAddrRequest) request,
              (io.grpc.stub.StreamObserver<com.launch.diagdevice.blockchain.TransactionReply>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class UserBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    UserBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.launch.diagdevice.blockchain.BlockChainProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("User");
    }
  }

  private static final class UserFileDescriptorSupplier
      extends UserBaseDescriptorSupplier {
    UserFileDescriptorSupplier() {}
  }

  private static final class UserMethodDescriptorSupplier
      extends UserBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    UserMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (UserGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new UserFileDescriptorSupplier())
              .addMethod(getCreateAssetMethod())
              .addMethod(getAssignAssetMethod())
              .addMethod(getUpdateAssetMethod())
              .addMethod(getAppendRecordMethod())
              .addMethod(getAssetTotalSupplyMethod())
              .addMethod(getAssetOfMethod())
              .addMethod(getAssetRecordCountMethod())
              .addMethod(getAssetRecordOfMethod())
              .addMethod(getSendToAddrMethod())
              .build();
        }
      }
    }
    return result;
  }
}
