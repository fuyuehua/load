package com.rip.load.otherPojo.personalEnterprise;

import java.util.List;

public class Data {

    /**
     *证件号
     */
    private String key;

    /**
     *EXIST, NO_DATA
     */
    private String status;

    /**
     *失信被执行人信息
     */
    private List<PunishBreaks> punishBreaks;

    /**
     *被执行人信息
     */
    private List<Punished> punished;

    /**
     *行政处罚历史信息
     */
    private List<CaseInfos> caseInfos;

    /**
     *企业法人信息
     */
    private List<Corporates> corporates;

    /**
     *企业主要管理人员信息
     */
    private List<CorporateManagers> corporateManagers;

    /**
     *企业股东信息
     */
    private List<CorporateShareholders> corporateShareholders;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PunishBreaks> getPunishBreaks() {
        return punishBreaks;
    }

    public void setPunishBreaks(List<PunishBreaks> punishBreaks) {
        this.punishBreaks = punishBreaks;
    }

    public List<Punished> getPunished() {
        return punished;
    }

    public void setPunished(List<Punished> punished) {
        this.punished = punished;
    }

    public List<CaseInfos> getCaseInfos() {
        return caseInfos;
    }

    public void setCaseInfos(List<CaseInfos> caseInfos) {
        this.caseInfos = caseInfos;
    }

    public List<Corporates> getCorporates() {
        return corporates;
    }

    public void setCorporates(List<Corporates> corporates) {
        this.corporates = corporates;
    }

    public List<CorporateManagers> getCorporateManagers() {
        return corporateManagers;
    }

    public void setCorporateManagers(List<CorporateManagers> corporateManagers) {
        this.corporateManagers = corporateManagers;
    }

    public List<CorporateShareholders> getCorporateShareholders() {
        return corporateShareholders;
    }

    public void setCorporateShareholders(List<CorporateShareholders> corporateShareholders) {
        this.corporateShareholders = corporateShareholders;
    }
}
