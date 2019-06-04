package com.rip.load.utils.pdfUtils;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis3D;
import org.jfree.chart.axis.NumberAxis3D;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Description: 生成统计图
 * @Author: FYH
 * @Date: Created in 10:06 2019/5/27
 * @Modified:
 */
public class StatisticalChartUtil {

    /**
     * 创建饼图
     * @param dpd
     * @param desc
     * @return
     */
    public static String createStatistrical(DefaultPieDataset dpd,String desc){

        JFreeChart chart=ChartFactory.createPieChart(desc,dpd,true,true,false);
        //可以查具体的API文档,第一个参数是标题，第二个参数是一个数据集，第三个参数表示是否显示Legend，第四个参数表示是否显示提示，第五个参数表示图中是否存在URL

        chart.getTitle().setFont(new Font("宋体",Font.BOLD,20));//设置标题字体
        PiePlot piePlot= (PiePlot) chart.getPlot();//获取图表区域对象
        //piePlot.setLabelGenerator(new StandardPieSectionLabelGenerator(StandardPieToolTipGenerator.DEFAULT_TOOLTIP_FORMAT));// 图片中显示百分比:默认方式
        // 图片中显示百分比:自定义方式，{0} 表示选项， {1} 表示数值， {2} 表示所占比例 ,小数点后两位
        //piePlot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}={1}({2})", NumberFormat.getNumberInstance(), new DecimalFormat("0.00%")));
        // 图例显示百分比:自定义方式， {0} 表示选项， {1} 表示数值， {2} 表示所占比例
        piePlot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator("{0}({1}, {2})"));
        piePlot.setStartAngle(90.00);// 设置第一个 饼块section 的开始位置，默认是12点钟方向
        piePlot.setBackgroundPaint(Color.WHITE);
        piePlot.setLabelBackgroundPaint(Color.WHITE);
        piePlot.setLabelFont(new Font("宋体",Font.BOLD,15));//解决乱码
        chart.getLegend().setItemFont(new Font("黑体",Font.BOLD,15));

        String url = "../img/"+System.currentTimeMillis()+".jpg";
        try {
            FileOutputStream fos_jpg = new FileOutputStream(url);
            ChartUtilities.writeChartAsJPEG(fos_jpg,1.0f,chart,800,600,null);
            return url;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建条形统计图
     * @param dataset
     * @param desc
     * @param X
     * @param Y
     * @return
     */
    public static String createBarGraph( DefaultCategoryDataset dataset, String desc, String X, String Y){
        JFreeChart chart=ChartFactory.createBarChart3D(
                desc,
                X,//X轴的标签
                Y,//Y轴的标签
                dataset, //图标显示的数据集合
                PlotOrientation.VERTICAL, //图像的显示形式（水平或者垂直）
                true,//是否显示子标题
                true,//是否生成提示的标签
                true); //是否生成URL链接
        //处理图形上的乱码
        //处理主标题的乱码
        chart.getTitle().setFont(new Font("宋体",Font.BOLD,18));
        //处理子标题乱码
        chart.getLegend().setItemFont(new Font("宋体",Font.BOLD,15));
        //获取图表区域对象
        CategoryPlot categoryPlot = (CategoryPlot)chart.getPlot();
        //获取X轴的对象
        CategoryAxis3D categoryAxis3D = (CategoryAxis3D)categoryPlot.getDomainAxis();
        //获取Y轴的对象
        NumberAxis3D numberAxis3D = (NumberAxis3D)categoryPlot.getRangeAxis();
        //处理X轴上的乱码
        categoryAxis3D.setTickLabelFont(new Font("宋体",Font.BOLD,15));
        //处理X轴外的乱码
        categoryAxis3D.setLabelFont(new Font("宋体",Font.BOLD,15));
        //处理Y轴上的乱码
        numberAxis3D.setTickLabelFont(new Font("宋体",Font.BOLD,15));
        //处理Y轴外的乱码
        numberAxis3D.setLabelFont(new Font("宋体",Font.BOLD,15));
        //处理Y轴上显示的刻度，以10作为1格
        numberAxis3D.setAutoTickUnitSelection(false);
        NumberTickUnit unit = new NumberTickUnit(50);
        numberAxis3D.setTickUnit(unit);
        //获取绘图区域对象
        BarRenderer3D barRenderer3D = (BarRenderer3D)categoryPlot.getRenderer();
        //设置柱形图的宽度
        barRenderer3D.setMaximumBarWidth(0.07);
        //在图形上显示数字
        barRenderer3D.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        barRenderer3D.setBaseItemLabelsVisible(true);
        barRenderer3D.setBaseItemLabelFont(new Font("宋体",Font.BOLD,15));

        String url = "../img/"+System.currentTimeMillis()+".jpg";
        //在D盘目录下生成图片
        File file = new File(url);
        try {
            ChartUtilities.saveChartAsJPEG(file, chart, 800, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static Boolean deletePhoto(String url){
        if(url != null) {
            File dir=new File(url);
            if(!dir.delete()) {
                return false;
            }
        }
        return true;
    }
}
