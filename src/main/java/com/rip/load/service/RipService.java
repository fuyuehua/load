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
    String idCardElementsService(int id, Integer reportId);

    String operatorThreeElementsService(int id,Integer reportId);

    String inTheNetworkTimeService(int id, Integer reportId);

    String inTheNetworkTimeBService(int id, Integer reportId);

    String vehicleDetailsEnquiryService(int id);

    String businessDataService(int id, Integer reportId);

    String personalEnterpriseService(int id, Integer reportId);

    String personalRiskInformationService(int id, Integer reportId);

    String personalComplaintInquiryCService(int id, Integer reportId);

    Map<String, String> idcardPhotoService(int id, Map<String, Object> map, String suffixUrl, String image);

    String fourElementsOfBankCardService(int id);

    Map<String, String> bankcardPhotoService(int id, Map<String, Object> map, String suffixUrl, String image);

    String operatorTwoElementsService(int id, Integer reportedId);

    String operatorTwoElementsAService(int id, Integer reportId);

    String operatorTwoElementsBService(int id, Integer reportId);

    String ageCheckService(int id, Integer reportId);

    String inTheNetworkTimeAService(int id, Integer reportId);
}
