package com.rip.load.pojo.businessData;

import java.util.List;

public class Data {

    /**
     * 关键字
     */
    private String key;
    /**
     * EXIST,NO_DATA
     */
    private String status;
    /**
     * 企业基本信息
     */
    private Basic basic;
    /**
     * 股东信息
     */
    private List<Shareholder> shareholder;
    /**
     *高管信息
     */
    private List<ShareholderPersons> shareholderPersons;
    /**
     * 法人对外投资信息
     */
    private List<LegalPersonInvests> legalPersonInvests;
    /**
     * 法人其他任职信息
     */
    private List<LegalPersonPostions> legalPersonPostions;
    /**
     *企业对外投资信息
     */
    private List<EnterpriseInvests> enterpriseInvests;
    /**
     *变更信息
     */
    private List<AlterInfos> alterInfos;
    /**
     *分支机构信息
     */
    private List<Filiations> filiations;
    /**
     *动产抵押物信息
     */
    private List<MorguaInfos> morguaInfos;
    /**
     *失信被执行人信息
     */
    private List<PunishBreaks> punishBreaks;
    /**
     * 被执行人信息
     */
    private List<Punished> punished;
    /**
     *股权冻结历史信息
     */
    private List<SharesFrosts> sharesFrosts;
    /**
     *清算信息
     */
    private List<Liquidations> liquidations;
    /**
     *行政处罚历史信息
     */
    private List<CaseInfos> caseInfos;
    /**
     *动产抵押-变更信息
     */
    private List<MortgageAlter> mortgageAlter;
    /**
     *动产抵押-注销信息
     */
    private List<MortgageCancels> mortgageCancels;
    /**
     *动产抵押-被担保主债权信息
     */
    private List<MortgageDebtors> mortgageDebtors;
    /**
     *动产抵押-抵押人信息
     */
    private List<MortgagePersons> mortgagePersons;
    /**
     *动产抵押-登记信息
     */
    private List<MortgageRegisters> mortgageRegisters;
    /**
     *严重违法信息
     */
    private List<BreakLaw> breakLaw;
    /**
     *企业异常名录
     */
    private List<ExceptionLists> exceptionLists;
    /**
     *组织机构代码
     */
    private List<OrgBasics> orgBasics;

    /**
     *股权出质信息详情
     */
    private OrgDetails orgDetails;
    /**
     *股权出质信息
     */
    private List<EquityInfos> equityInfos;
    /**
     *股权出质信息-变更信息
     */
    private List<EquityChangeInfos> equityChangeInfos;
    /**
     *股权出质信息-注销信息
     */
    private List<CancellationOfInfos> cancellationOfInfos;
    /**
     *注册商标
     */
    private List<TradeMarks> tradeMarks;

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

    public Basic getBasic() {
        return basic;
    }

    public void setBasic(Basic basic) {
        this.basic = basic;
    }

    public List<Shareholder> getShareholder() {
        return shareholder;
    }

    public void setShareholder(List<Shareholder> shareholder) {
        this.shareholder = shareholder;
    }

    public List<ShareholderPersons> getShareholderPersons() {
        return shareholderPersons;
    }

    public void setShareholderPersons(List<ShareholderPersons> shareholderPersons) {
        this.shareholderPersons = shareholderPersons;
    }

    public List<LegalPersonInvests> getLegalPersonInvests() {
        return legalPersonInvests;
    }

    public void setLegalPersonInvests(List<LegalPersonInvests> legalPersonInvests) {
        this.legalPersonInvests = legalPersonInvests;
    }

    public List<LegalPersonPostions> getLegalPersonPostions() {
        return legalPersonPostions;
    }

    public void setLegalPersonPostions(List<LegalPersonPostions> legalPersonPostions) {
        this.legalPersonPostions = legalPersonPostions;
    }

    public List<EnterpriseInvests> getEnterpriseInvests() {
        return enterpriseInvests;
    }

    public void setEnterpriseInvests(List<EnterpriseInvests> enterpriseInvests) {
        this.enterpriseInvests = enterpriseInvests;
    }

    public List<AlterInfos> getAlterInfos() {
        return alterInfos;
    }

    public void setAlterInfos(List<AlterInfos> alterInfos) {
        this.alterInfos = alterInfos;
    }

    public List<Filiations> getFiliations() {
        return filiations;
    }

    public void setFiliations(List<Filiations> filiations) {
        this.filiations = filiations;
    }

    public List<MorguaInfos> getMorguaInfos() {
        return morguaInfos;
    }

    public void setMorguaInfos(List<MorguaInfos> morguaInfos) {
        this.morguaInfos = morguaInfos;
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

    public List<SharesFrosts> getSharesFrosts() {
        return sharesFrosts;
    }

    public void setSharesFrosts(List<SharesFrosts> sharesFrosts) {
        this.sharesFrosts = sharesFrosts;
    }

    public List<Liquidations> getLiquidations() {
        return liquidations;
    }

    public void setLiquidations(List<Liquidations> liquidations) {
        this.liquidations = liquidations;
    }

    public List<CaseInfos> getCaseInfos() {
        return caseInfos;
    }

    public void setCaseInfos(List<CaseInfos> caseInfos) {
        this.caseInfos = caseInfos;
    }

    public List<MortgageAlter> getMortgageAlter() {
        return mortgageAlter;
    }

    public void setMortgageAlter(List<MortgageAlter> mortgageAlter) {
        this.mortgageAlter = mortgageAlter;
    }

    public List<MortgageCancels> getMortgageCancels() {
        return mortgageCancels;
    }

    public void setMortgageCancels(List<MortgageCancels> mortgageCancels) {
        this.mortgageCancels = mortgageCancels;
    }

    public List<MortgageDebtors> getMortgageDebtors() {
        return mortgageDebtors;
    }

    public void setMortgageDebtors(List<MortgageDebtors> mortgageDebtors) {
        this.mortgageDebtors = mortgageDebtors;
    }

    public List<MortgagePersons> getMortgagePersons() {
        return mortgagePersons;
    }

    public void setMortgagePersons(List<MortgagePersons> mortgagePersons) {
        this.mortgagePersons = mortgagePersons;
    }

    public List<MortgageRegisters> getMortgageRegisters() {
        return mortgageRegisters;
    }

    public void setMortgageRegisters(List<MortgageRegisters> mortgageRegisters) {
        this.mortgageRegisters = mortgageRegisters;
    }

    public List<BreakLaw> getBreakLaw() {
        return breakLaw;
    }

    public void setBreakLaw(List<BreakLaw> breakLaw) {
        this.breakLaw = breakLaw;
    }

    public List<ExceptionLists> getExceptionLists() {
        return exceptionLists;
    }

    public void setExceptionLists(List<ExceptionLists> exceptionLists) {
        this.exceptionLists = exceptionLists;
    }

    public List<OrgBasics> getOrgBasics() {
        return orgBasics;
    }

    public void setOrgBasics(List<OrgBasics> orgBasics) {
        this.orgBasics = orgBasics;
    }

    public OrgDetails getOrgDetails() {
        return orgDetails;
    }

    public void setOrgDetails(OrgDetails orgDetails) {
        this.orgDetails = orgDetails;
    }

    public List<EquityInfos> getEquityInfos() {
        return equityInfos;
    }

    public void setEquityInfos(List<EquityInfos> equityInfos) {
        this.equityInfos = equityInfos;
    }

    public List<EquityChangeInfos> getEquityChangeInfos() {
        return equityChangeInfos;
    }

    public void setEquityChangeInfos(List<EquityChangeInfos> equityChangeInfos) {
        this.equityChangeInfos = equityChangeInfos;
    }

    public List<CancellationOfInfos> getCancellationOfInfos() {
        return cancellationOfInfos;
    }

    public void setCancellationOfInfos(List<CancellationOfInfos> cancellationOfInfos) {
        this.cancellationOfInfos = cancellationOfInfos;
    }

    public List<TradeMarks> getTradeMarks() {
        return tradeMarks;
    }

    public void setTradeMarks(List<TradeMarks> tradeMarks) {
        this.tradeMarks = tradeMarks;
    }
}
