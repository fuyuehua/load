package com.rip.load.service;

import com.rip.load.pojo.BankFund;
import com.rip.load.pojo.BankRecord;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxh
 * @since 2019-05-29
 */
public interface BankRecordService extends IService<BankRecord> {

    BankRecord createTransferRecord2Distributor(BankRecord bankRecord);

    BankRecord createFundRecharge(BankRecord bankRecord);

    boolean createSendFundAndGetPoundage(BankRecord bankRecord, BankRecord poundageRecord, BankFund distributorBank);
}
