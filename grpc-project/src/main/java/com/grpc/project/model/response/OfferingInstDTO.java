package com.grpc.project.model.response;

import java.util.Date;
import java.util.List;

/**
 * Created by xusong on 2018/10/10.
 */
public class OfferingInstDTO {

    /**
     * 用不标识
     */
    private Long subsId;
    /**
     * 省编码
     */
    private String beId;
    /**
     * 集团客户Id
     */
    private String custId;
    /**
     * 集团客户编码
     */
    private String custCode;
    /**
     * 集团名称
     */
    private String custName;
    /**
     * 省份名称
     */
    private String province;
    /**
     * 物联卡号
     */
    private String msisdn;
    /**
     *商品标识
     */
    private String apnId;

    /**
     *商品标识
     */
    private String offeringId;

    /**
     *商品名称
     */
    private String offeringName;

    /**
     *商品编码
     */
    private String offeringCode;

    /**
     *主体offer标识：Y 是 N 否
     */
    private String primaryFlag;

    /**
     *实例时间
     */
    private Date statusDate;


    /**
     *生效时间
     */
    private Date effectDate;

    /**
     *失效时间
     */
    private Date expiryDate;


    /**
     *Offering实例状态：1: 待激活2: 有效4: 暂停（Suspend）8:预销9: 失效3: 半停5: 挂起
     */
    private String status;
    /**
     * 操作类型
     * 01：新加
     * 02：删除
     * 03：修改
     */
    private String operCode;
    /**
     * 数据来源
     */
    private String sourceFlag;
    /**
     *
     */
    private List<String> custIds;

    public Long getSubsId() {
        return subsId;
    }

    public void setSubsId(Long subsId) {
        this.subsId = subsId;
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

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getApnId() {
        return apnId;
    }

    public void setApnId(String apnId) {
        this.apnId = apnId;
    }

    public String getOfferingId() {
        return offeringId;
    }

    public void setOfferingId(String offeringId) {
        this.offeringId = offeringId;
    }

    public String getOfferingName() {
        return offeringName;
    }

    public void setOfferingName(String offeringName) {
        this.offeringName = offeringName;
    }

    public String getOfferingCode() {
        return offeringCode;
    }

    public void setOfferingCode(String offeringCode) {
        this.offeringCode = offeringCode;
    }

    public String getPrimaryFlag() {
        return primaryFlag;
    }

    public void setPrimaryFlag(String primaryFlag) {
        this.primaryFlag = primaryFlag;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public Date getEffectDate() {
        return effectDate;
    }

    public void setEffectDate(Date effectDate) {
        this.effectDate = effectDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOperCode() {
        return operCode;
    }

    public void setOperCode(String operCode) {
        this.operCode = operCode;
    }

    public String getSourceFlag() {
        return sourceFlag;
    }

    public void setSourceFlag(String sourceFlag) {
        this.sourceFlag = sourceFlag;
    }

    public List<String> getCustIds() {
        return custIds;
    }

    public void setCustIds(List<String> custIds) {
        this.custIds = custIds;
    }
}
