package com.rip.load.service;

import com.rip.load.pojo.Order;
import com.rip.load.pojo.Report;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zxh
 * @since 2019-04-30
 */
public interface ReportService extends IService<Report> {

    Report setItem(Report report);

    Report handleFirstReport(Order order, String remark);

    Report takeFirstReport(Order order);
}
