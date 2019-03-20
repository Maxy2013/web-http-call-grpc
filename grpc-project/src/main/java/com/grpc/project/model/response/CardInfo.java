package com.grpc.project.model.response;

/**
 * @author liuzheng
 * 2018/11/15 16:58
 */
public class CardInfo {

    private String msisdn;
    private String iccid;
    private String imsi;
    private String beId;
    private String custId;
    private String subsId;
    private String subsName;
    private Long status;
    private String netType;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
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

    public String getSubsId() {
        return subsId;
    }

    public void setSubsId(String subsId) {
        this.subsId = subsId;
    }

    public String getSubsName() {
        return subsName;
    }

    public void setSubsName(String subsName) {
        this.subsName = subsName;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getNetType() {
        return netType;
    }

    public void setNetType(String netType) {
        this.netType = netType;
    }
}
