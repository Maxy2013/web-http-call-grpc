package com.grpc.service.grpcservice.model.request;

/**
 * @author liuzheng
 * 2018/11/15 16:41
 */
public class GrpcRequest {

    private String entityType;
    private String entityId;
    private String imsi;
    private String beId;
    private String custId;
    private String chargeMoney;
    private String accountName;

    public String getEntityType() {
        return entityType;
    }

    public String getEntityId() {
        return entityId;
    }

    public String getImsi() {
        return imsi;
    }

    public String getBeId() {
        return beId;
    }

    public String getCustId() {
        return custId;
    }

    public String getChargeMoney() {
        return chargeMoney;
    }

    public String getAccountName() {
        return accountName;
    }
}
