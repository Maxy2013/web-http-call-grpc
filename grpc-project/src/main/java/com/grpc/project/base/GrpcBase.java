package com.grpc.service.grpcservice.base;

import io.grpc.Channel;
import io.grpc.ManagedChannelBuilder;
import onelink.api.oneservice.account.service.AccountServiceGrpc;
import onelink.api.oneservice.bill.service.BillServiceGrpc;
import onelink.api.oneservice.cm.service.CmServiceGrpc;
import onelink.api.oneservice.detailedbill.service.DetailedBillServiceGrpc;
import onelink.api.oneservice.order.service.OrderServiceGrpc;
import onelink.api.oneservice.riskcontrol.service.RiskControlServiceGrpc;
//import onelink.api.oneservice.riskcontrol.service.RiskControlServiceGrpc;

/**
 * @author lz
 * 2018/10/18 15:22
 */
public class GrpcBase {
//    private Channel serverChannel = ManagedChannelBuilder.forAddress("192.168.156.180", 9898).usePlaintext().build();
//    private Channel serverChannel = ManagedChannelBuilder.forAddress("192.168.156.238", 9888).usePlaintext().build();
//    private Channel serverChannel = ManagedChannelBuilder.forAddress("192.168.235.108", 9898).usePlaintext().build();
    private Channel serverChannel = ManagedChannelBuilder.forAddress("127.0.0.1", 9898).usePlaintext().build();
    protected OrderServiceGrpc.OrderServiceBlockingStub orderServiceBlockingStub = OrderServiceGrpc.newBlockingStub(serverChannel);
    protected BillServiceGrpc.BillServiceBlockingStub billServiceBlockingStub = BillServiceGrpc.newBlockingStub(serverChannel);
    protected AccountServiceGrpc.AccountServiceBlockingStub accountServiceBlockingStub = AccountServiceGrpc.newBlockingStub(serverChannel);
    protected CmServiceGrpc.CmServiceBlockingStub cmServiceBlockingStub = CmServiceGrpc.newBlockingStub(serverChannel);
    protected RiskControlServiceGrpc.RiskControlServiceBlockingStub riskControlServiceBlockingStub = RiskControlServiceGrpc.newBlockingStub(serverChannel);

    protected DetailedBillServiceGrpc.DetailedBillServiceBlockingStub detailedBillServiceBlockingStub = DetailedBillServiceGrpc.newBlockingStub(serverChannel);
}
