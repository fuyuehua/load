package com.rip.load.service.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rip.load.pojo.RiskRule;
import com.rip.load.mapper.RiskRuleMapper;
import com.rip.load.pojo.Rule;
import com.rip.load.service.RiskRuleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.rip.load.service.RuleService;
import io.swagger.models.auth.In;
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
 * @since 2019-05-09
 */
@Service
public class RiskRuleServiceImpl extends ServiceImpl<RiskRuleMapper, RiskRule> implements RiskRuleService {

    @Autowired
    private RuleService ruleService;

    @Override
    public List<RiskRule> setRule4RiskRule(List<RiskRule> list) {

        List<Integer> temp = new ArrayList<>();
        for(RiskRule item : list){
            temp.add(item.getRuleId());
        }

        List<Rule> rules = ruleService.selectBatchIds(temp);
        for (Rule rule : rules) {
            for (RiskRule item : list){
                if(rule.getId().equals(item.getRuleId())){
                    item.setRule(rule);
                }
            }
        }
        return list;
    }
}
