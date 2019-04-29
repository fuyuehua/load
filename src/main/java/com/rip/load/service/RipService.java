package com.rip.load.service;

import java.util.Map;

public interface RipService {

    Map<String, Object> getOperatorCreditReports(Map<String, Object> map);


    String ripSetTokenService(Map<String, Object> map, String suffixUrl);

    Map<String, String> operatorCreditReportsService(int id, Map<String, Object> map, String suffixUrl);

    Map<String, String> taoBaoService(int id, Map<String, Object> map, String suffixUrl);

    /**
     * 不传ID就是ThreadLocal本身
     * @param id
     * @return
     */
    String idCardElementsService(int id);

    String operatorThreeElementsService(int id);

    String inTheNetworkTimeService(int id);

    String vehicleDetailsEnquiryService(int id);

    String businessDataService(int id);

    String personalEnterpriseService(int id);

    String personalRiskInformationService(int id);

    String personalComplaintInquiryCService(int id);

    String fourElementsOfBankCardService(int id);
}
