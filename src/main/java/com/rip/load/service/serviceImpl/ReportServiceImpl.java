package com.rip.load.service.serviceImpl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.rip.load.pojo.Item;
import com.rip.load.pojo.Report;
import com.rip.load.mapper.ReportMapper;
import com.rip.load.pojo.ReportItem;
import com.rip.load.service.ItemService;
import com.rip.load.service.ReportItemService;
import com.rip.load.service.ReportService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
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
 * @since 2019-04-30
 */
@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements ReportService {

    @Autowired
    private ReportItemService reportItemService;
    @Autowired
    private ItemService itemService;

    @Override
    public Report setItem(Report report) {
        List<ReportItem> reportItems = reportItemService.selectList(new EntityWrapper<ReportItem>().eq("report_id", report.getId()));
        List<Integer> list = new ArrayList<>();
        for(ReportItem reportItem : reportItems){
            list.add(reportItem.getItemId());
        }
        List<Item> items = itemService.selectBatchIds(list);
        report.setItemList(items);
        return report;
    }
}
