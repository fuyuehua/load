package com.rip.load.service;

import com.rip.load.pojo.RiskRuleItem;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxh
 * @since 2019-05-21
 */
public interface RiskRuleItemService extends IService<RiskRuleItem> {

    List<RiskRuleItem> setAll(List<RiskRuleItem> linkList);

    List<RiskRuleItem> setRiskRule(List<RiskRuleItem> linkList);
}
