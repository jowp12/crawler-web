package com.crawler.bean;

import java.util.List;

public class Info {

    private String title;
    private String solicitationNumber;
    private String agency;
    private String offerLocation;
    private String synopsis;
    private String noticeType;
    private String postedDate;
    private String responseDate;
    private String archivingPolicy;
    private String archiveDate;
    private String originalSetAside;
    private String setAside;
    private String classificationCode;
    private String naicsCode;
    private String contractingOfficeAddress;
    private String placeOfPerformance;
    private String primaryPointOfContractName;
    private String primaryPointOfContractEmail;
    private String primaryPointOfContractPhone;
    private List<String> filePath;
    private String url;
    private String filePathStr;
    private int taskId;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSolicitationNumber() {
        return solicitationNumber;
    }

    public void setSolicitationNumber(String solicitationNumber) {
        this.solicitationNumber = solicitationNumber;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getOfferLocation() {
        return offerLocation;
    }

    public void setOfferLocation(String offerLocation) {
        this.offerLocation = offerLocation;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(String responseDate) {
        this.responseDate = responseDate;
    }

    public String getArchivingPolicy() {
        return archivingPolicy;
    }

    public void setArchivingPolicy(String archivingPolicy) {
        this.archivingPolicy = archivingPolicy;
    }

    public String getArchiveDate() {
        return archiveDate;
    }

    public void setArchiveDate(String archiveDate) {
        this.archiveDate = archiveDate;
    }

    public String getOriginalSetAside() {
        return originalSetAside;
    }

    public void setOriginalSetAside(String originalSetAside) {
        this.originalSetAside = originalSetAside;
    }

    public String getSetAside() {
        return setAside;
    }

    public void setSetAside(String setAside) {
        this.setAside = setAside;
    }

    public String getClassificationCode() {
        return classificationCode;
    }

    public void setClassificationCode(String classificationCode) {
        this.classificationCode = classificationCode;
    }

    public String getNaicsCode() {
        return naicsCode;
    }

    public void setNaicsCode(String naicsCode) {
        this.naicsCode = naicsCode;
    }

    public String getContractingOfficeAddress() {
        return contractingOfficeAddress;
    }

    public void setContractingOfficeAddress(String contractingOfficeAddress) {
        this.contractingOfficeAddress = contractingOfficeAddress;
    }

    public String getPlaceOfPerformance() {
        return placeOfPerformance;
    }

    public void setPlaceOfPerformance(String placeOfPerformance) {
        this.placeOfPerformance = placeOfPerformance;
    }

    public String getPrimaryPointOfContractName() {
        return primaryPointOfContractName;
    }

    public void setPrimaryPointOfContractName(String primaryPointOfContractName) {
        this.primaryPointOfContractName = primaryPointOfContractName;
    }

    public String getPrimaryPointOfContractEmail() {
        return primaryPointOfContractEmail;
    }

    public void setPrimaryPointOfContractEmail(String primaryPointOfContractEmail) {
        this.primaryPointOfContractEmail = primaryPointOfContractEmail;
    }

    public String getPrimaryPointOfContractPhone() {
        return primaryPointOfContractPhone;
    }

    public void setPrimaryPointOfContractPhone(String primaryPointOfContractPhone) {
        this.primaryPointOfContractPhone = primaryPointOfContractPhone;
    }

    public List<String> getFilePath() {
        return filePath;
    }

    public void setFilePath(List<String> filePath) {
        this.filePath = filePath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilePathStr() {
        return filePathStr;
    }

    public void setFilePathStr(String filePathStr) {
        this.filePathStr = filePathStr;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "Info{" +
                "title='" + title + '\'' +
                ", solicitationNumber='" + solicitationNumber + '\'' +
                ", agency='" + agency + '\'' +
                ", offerLocation='" + offerLocation + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", noticeType='" + noticeType + '\'' +
                ", postedDate='" + postedDate + '\'' +
                ", responseDate='" + responseDate + '\'' +
                ", archivingPolicy='" + archivingPolicy + '\'' +
                ", archiveDate='" + archiveDate + '\'' +
                ", originalSetAside='" + originalSetAside + '\'' +
                ", setAside='" + setAside + '\'' +
                ", classificationCode='" + classificationCode + '\'' +
                ", naicsCode='" + naicsCode + '\'' +
                ", contractingOfficeAddress='" + contractingOfficeAddress + '\'' +
                ", placeOfPerformance='" + placeOfPerformance + '\'' +
                ", primaryPointOfContractName='" + primaryPointOfContractName + '\'' +
                ", primaryPointOfContractEmail='" + primaryPointOfContractEmail + '\'' +
                ", primaryPointOfContractPhone='" + primaryPointOfContractPhone + '\'' +
                ", filePath=" + filePath +
                ", url='" + url + '\'' +
                ", filePathStr='" + filePathStr + '\'' +
                '}';
    }
}
