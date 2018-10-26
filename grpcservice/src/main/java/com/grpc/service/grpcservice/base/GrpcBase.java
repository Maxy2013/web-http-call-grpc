package com.grpc.service.grpcservice.base;

import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import onelink.api.oneservice.account.service.AccountServiceGrpc;
import onelink.api.oneservice.bill.service.BillServiceGrpc;
import onelink.api.oneservice.cm.service.CmServiceGrpc;
import onelink.api.oneservice.order.service.OrderServiceGrpc;

/**
 * @author lz
 * 2018/10/18 15:22
 */
public class GrpcBase {
    private Channel serverChannel = ManagedChannelBuilder.forAddress("localhost", 9898).usePlaintext().build();
    protected OrderServiceGrpc.OrderServiceBlockingStub orderServiceBlockingStub = OrderServiceGrpc.newBlockingStub(serverChannel);
    protected BillServiceGrpc.BillServiceBlockingStub billServiceBlockingStub = BillServiceGrpc.newBlockingStub(serverChannel);
    protected AccountServiceGrpc.AccountServiceBlockingStub accountServiceBlockingStub = AccountServiceGrpc.newBlockingStub(serverChannel);
    protected CmServiceGrpc.CmServiceBlockingStub cmServiceBlockingStub = CmServiceGrpc.newBlockingStub(serverChannel);
}
