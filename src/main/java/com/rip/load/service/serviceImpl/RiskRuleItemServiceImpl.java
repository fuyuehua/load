package com.rip.load.service.serviceImpl;

import com.rip.load.pojo.Item;
import com.rip.load.pojo.RiskRule;
import com.rip.load.pojo.RiskRuleItem;
import com.rip.load.mapper.RiskRuleItemMapper;
import com.rip.load.service.ItemService;
import com.rip.load.service.RiskRuleItemService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.rip.load.service.RiskRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zxh
 * @since 2019-05-21
 */
@Service
public class RiskRuleItemServiceImpl extends ServiceImpl<RiskRuleItemMapper, RiskRuleItem> implements RiskRuleItemService {

    @Autowired
    private ItemService itemService;
    @Autowired
    RiskRuleService riskRuleService;


    @Override
    public List<RiskRuleItem> setAll(List<RiskRuleItem> linkList) {
        List<Integer> temp = new ArrayList<>();
        List<Integer> temp1 = new ArrayList<>();
        for(RiskRuleItem link : linkList){
            temp.add(link.getItemId());
            temp1.add(link.getRiskRuleId());
        }
        List<Item> items = itemService.selectBatchIds(temp);
        List<RiskRule> riskRules = riskRuleService.selectBatchIds(temp1);

        List<RiskRule> newRiskRules = riskRuleService.setRule4RiskRule(riskRules);

        for(RiskRuleItem link : linkList){
            for(Item item : items){
                if(item.getId().equals(link.getItemId())){
                    link.setItem(item);
                }
            }
            for(RiskRule riskRule : newRiskRules){
                if(riskRule.getId().equals(link.getRiskRuleId())){
                    link.setRiskRule(riskRule);
                }

            }
        }
        return linkList;
    }

    @Override
    public List<RiskRuleItem> setRiskRule(List<RiskRuleItem> linkList) {
        List<Integer> temp1 = new ArrayList<>();
        for(RiskRuleItem link : linkList){
            temp1.add(link.getRiskRuleId());
        }
        List<RiskRule> riskRules = riskRuleService.selectBatchIds(temp1);

        List<RiskRule> newRiskRules = riskRuleService.setRule4RiskRule(riskRules);

        for(RiskRuleItem link : linkList){
            for(RiskRule riskRule : newRiskRules){
                if(riskRule.getId().equals(link.getRiskRuleId())){
                    link.setRiskRule(riskRule);
                }

            }
        }
        return linkList;
    }
}
