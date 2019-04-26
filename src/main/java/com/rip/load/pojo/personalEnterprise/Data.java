package com.rip.load.pojo.personalEnterprise;

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





}
