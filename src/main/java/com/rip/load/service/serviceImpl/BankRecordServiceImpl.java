package com.rip.load.service.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rip.load.mapper.*;
import com.rip.load.pojo.*;
import com.rip.load.service.BankRecordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxh
 * @since 2019-05-29
 */
@Service
public class BankRecordServiceImpl extends ServiceImpl<BankRecordMapper, BankRecord> implements BankRecordService {

    @Autowired
    private UserPlatformMapper userPlatformMapper;
    @Autowired
    private UserDistributorMapper userDistributorMapper;
    @Autowired
    private BankFundMapper bankFundMapper;
    @Autowired
    private BankRecordMapper bankRecordMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public BankRecord createTransferRecord2Distributor(BankRecord bankRecord) {
        UserPlatform platform = null;
        //添加付款平台商公司名称
        platform = userPlatformMapper.selectList(new EntityWrapper<UserPlatform>()
                .setSqlSelect("user_id as userId, corporate_name as corporateName")
                .eq("user_id", bankRecord.getDraweeId()))
                .get(0);
        bankRecord.setDrawee(platform.getCorporateName());
        //添加操作人名称
        if(bankRecord.getOperatorId().equals(bankRecord.getDraweeId())) {
            bankRecord.setOperator(platform.getCorporateName());
            bankRecord.setOperatorId(platform.getUserId());
        }else{
            User user = userMapper.selectList(new EntityWrapper<User>()
                    .setSqlSelect("id, nickname")
                    .eq("id", bankRecord.getOperatorId()))
                    .get(0);
            bankRecord.setOperator(user.getNickname());
            bankRecord.setOperatorId(user.getId());
        }
        //添加收款渠道商公司名称
        UserDistributor userDistributor = userDistributorMapper.selectList(new EntityWrapper<UserDistributor>()
                .setSqlSelect("user_id as userId, corporate_name as corporateName")
                .eq("user_id", bankRecord.getPayeeId()))
                .get(0);
        bankRecord.setPayee(userDistributor.getCorporateName());
        ////增加收款者钱
        BankFund bankFund = bankFundMapper.selectById(userDistributor.getUserId());
        BigDecimal add = bankFund.getPoundage().add(bankRecord.getMoney());
        bankFund.setPoundage(add);

        Integer insert = bankRecordMapper.insert(bankRecord);
        Integer integer = bankFundMapper.updateById(bankFund);
        if(insert == 1 && integer==1){
            return bankRecord;
        }else
            return null;
    }

    @Override
    public BankRecord createFundRecharge(BankRecord bankRecord) {
        //添加收款渠道商公司名称
        UserDistributor userDistributor = null;
        List<UserDistributor> distributors = userDistributorMapper.selectList(new EntityWrapper<UserDistributor>()
                .setSqlSelect("user_id as userId, corporate_name as corporateName")
                .eq("user_id", bankRecord.getPayeeId()));
        userDistributor = distributors.get(0);
        bankRecord.setPayee(userDistributor.getCorporateName());
        //增加收款者钱
        BankFund bankFund = bankFundMapper.selectById(userDistributor.getUserId());
        BigDecimal add = bankFund.getFund().add(bankRecord.getMoney());
        bankFund.setFund(add);
        Integer insert = bankRecordMapper.insert(bankRecord);
        Integer integer = bankFundMapper.updateById(bankFund);
        if(insert == 1 && integer==1){
            return bankRecord;
        }else
            return null;
    }

    @Override
    public boolean createSendFundAndGetPoundage(BankRecord bankRecord, BankRecord poundageRecord, BankFund distributorBank) {
        Integer insert = bankRecordMapper.insert(bankRecord);
        Integer insert2 = bankRecordMapper.insert(poundageRecord);
        Integer integer = bankFundMapper.updateById(distributorBank);
        return (insert == 1 && insert2 ==1 && integer == 1);
    }
}
