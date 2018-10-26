package com.grpc.service.grpcservice.base;

/**
 * @author lz
 * 2018/10/25 10:27
 */
public class Params {

    private String msisdn;

    private String beId;

    private String custId;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getBeId() {
        return beId;
    }

    public void setBeId(String beId) {
        this.beId = beId;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }
}
