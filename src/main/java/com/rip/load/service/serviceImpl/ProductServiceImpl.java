package com.rip.load.service.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rip.load.pojo.Config;
import com.rip.load.pojo.Product;
import com.rip.load.mapper.ProductMapper;
import com.rip.load.pojo.Risk;
import com.rip.load.pojo.RiskRule;
import com.rip.load.service.ConfigService;
import com.rip.load.service.ProductService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.rip.load.service.RiskRuleService;
import com.rip.load.service.RiskService;
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
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ConfigService configService;
    @Autowired
    private RiskService riskService;
    @Autowired
    private RiskRuleService riskRuleService;

    @Override
    public List<Product> settleConfigRisk(List<Product> list) {
        List<Integer> intList = new ArrayList<>();
        List<Integer> riskIdList = new ArrayList<>();
        for(Product product : list){
            Integer configId = product.getConfigId();
            intList.add(configId);
            riskIdList.add(product.getRiskId());
        }
        List<Config> configs = configService.selectBatchIds(intList);
        List<Risk> risks = riskService.selectBatchIds(riskIdList);
        for(Product product : list) {
            for (Config config : configs) {
                if (config.getId().equals(product.getConfigId())) {
                    product.setConfig(config);
                }
            }
            for (Risk risk : risks) {
                if (risk.getId().equals(product.getRiskId())) {
                    List<RiskRule> riskRules = riskRuleService.selectList(new EntityWrapper<RiskRule>().eq("risk_id", risk.getId()));
                    riskRules = riskRuleService.setRule4RiskRule(riskRules);
                    risk.setRiskRuleList(riskRules);
                    product.setRisk(risk);
                }
            }
        }
        return list;
    }

    @Override
    public List<Product> settleConfig(List<Product> list) {
        List<Integer> intList = new ArrayList<>();
        for(Product product : list){
            Integer configId = product.getConfigId();
            intList.add(configId);
        }
        List<Config> configs = configService.selectBatchIds(intList);
        for(Product product : list) {
            for (Config config : configs) {
                if (config.getId().equals(product.getConfigId())) {
                    product.setConfig(config);
                }
            }
        }
        return list;
    }
}
