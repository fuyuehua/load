package com.rip.load.service;

import com.rip.load.pojo.RiskRule;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxh
 * @since 2019-05-09
 */
public interface RiskRuleService extends IService<RiskRule> {

    List<RiskRule> setRule4RiskRule(List<RiskRule> list);
}
