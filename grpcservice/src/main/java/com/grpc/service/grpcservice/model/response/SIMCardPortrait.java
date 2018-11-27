package com.grpc.service.grpcservice.model.response;

import java.util.List;
import java.util.Map;

/**
 * @author liuzheng
 * 2018/11/23 15:05
 */
public class SIMCardPortrait {
    private String subsId;
    private String msisdn;
    private String cardPhysicalType;
    //已开通服务
    private List<String> serviceType;
    //数据用量
    private String actualVolume;
    int status;
    //敏感区域漫游
    private String roamcountryname;
    private List<String> apnId;
    private String riskUserType;
    private String userType;
    private Map<String, String> rf;
    private List<StringEntry> listMap;

    public String getSubsId() {
        return subsId;
    }

    public void setSubsId(String subsId) {
        this.subsId = subsId;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getCardPhysicalType() {
        return cardPhysicalType;
    }

    public void setCardPhysicalType(String cardPhysicalType) {
        this.cardPhysicalType = cardPhysicalType;
    }

    public List<String> getServiceType() {
        return serviceType;
    }

    public void setServiceType(List<String> serviceType) {
        this.serviceType = serviceType;
    }

    public String getActualVolume() {
        return actualVolume;
    }

    public void setActualVolume(String actualVolume) {
        this.actualVolume = actualVolume;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRoamcountryname() {
        return roamcountryname;
    }

    public void setRoamcountryname(String roamcountryname) {
        this.roamcountryname = roamcountryname;
    }

    public List<String> getApnId() {
        return apnId;
    }

    public void setApnId(List<String> apnId) {
        this.apnId = apnId;
    }

    public String getRiskUserType() {
        return riskUserType;
    }

    public void setRiskUserType(String riskUserType) {
        this.riskUserType = riskUserType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Map<String, String> getRf() {
        return rf;
    }

    public void setRf(Map<String, String> rf) {
        this.rf = rf;
    }

    public List<StringEntry> getListMap() {
        return listMap;
    }

    public void setListMap(List<StringEntry> listMap) {
        this.listMap = listMap;
    }
}
