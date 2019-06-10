package com.rip.load.utils.pdfUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @Description: 运营商信用报告
 * @Author: FYH
 * @Date: Created in 13:56 2019/5/21
 * @Modified:
 */
public class OperatorReportPdfUtil {
    private static String pieChartUrl = null;
    private static String barChartUrl = null;
    /**
     * 运营商信用报告
     * @throws IOException
     * @throws DocumentException
     */
    public static Boolean operatorReport(String json,String savePath) {
        try {
            Map<String, Object> jsonMap = JSON.parseObject(json);
            if (jsonMap.get("code") != null && Integer.valueOf(jsonMap.get("code").toString()) == 200) {

                Map<String, Object> resultMap = JSON.parseObject(jsonMap.get("result").toString());
                JSONArray itemListArray = JSONArray.parseArray(resultMap.get("itemList").toString());//用户详情
                for (Object items : itemListArray) {
                    Map<String, Object> itemsMap = JSON.parseObject(String.valueOf(items));
                    if ("10".equals(String.valueOf(itemsMap.get("type")))) {//运营商信用报告
                        // 在指定目录下创建一个文件
                        File file = new File(savePath);
                        file.createNewFile();
                        // 建立一个Document对象
                        Document document = new Document();
                        // 设置页面大小
                        document.setPageSize(PageSize.A4);
                        // 建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
                        PdfWriter.getInstance(document, new FileOutputStream(file));
                        document.open();


                        //从json中取出数据
                        Map<String,Object> resultJsonMap = JSON.parseObject(String.valueOf(itemsMap.get("resultJson")));
                        Map<String,Object> dataMap = JSON.parseObject(resultJsonMap.get("data").toString());
                        Map<String,Object> basicInfoMap = JSON.parseObject(dataMap.get("basicInfo").toString());//用户基本信息
                        Map<String,Object> reportMap = JSON.parseObject(dataMap.get("report").toString());//数据源
                        Map<String,Object> relationInfoMap = JSON.parseObject(dataMap.get("relationInfo").toString());//关联信息
                        JSONArray basicInfoCheckArray = JSON.parseArray(dataMap.get("basicInfoCheck").toString());//基本信息核验
                        JSONArray riskListCheckArray = JSON.parseArray(dataMap.get("riskListCheck").toString()); //风险清单检查
                        JSONArray overdueLoanCheckArray = JSON.parseArray(dataMap.get("overdueLoanCheck").toString()); //信贷逾期检查
                        JSONArray multiLendCheckArray = JSON.parseArray(dataMap.get("multiLendCheck").toString()); //多头借贷检查
                        JSONArray riskCallCheckArray = JSON.parseArray(dataMap.get("riskCallCheck").toString()); //风险通话检测
                        Map<String,Object> personasMap = JSON.parseObject(dataMap.get("personas").toString());//用户画像
                        Map<String,Object> riskProfileMap = JSON.parseObject(personasMap.get("riskProfile").toString());//用户画像——风险概况
                        Map<String,Object> socialContactProfileMap = JSON.parseObject(personasMap.get("socialContactProfile").toString());//用户画像——社交概况
                        Map<String,Object> callProfileMap = JSON.parseObject(personasMap.get("callProfile").toString());//用户画像——通话概况
                        Map<String,Object> consumptionProfileMap = JSON.parseObject(personasMap.get("consumptionProfile").toString());//用户画像——消费概况
                        Map<String,Object> callAnalysisMap = JSON.parseObject(dataMap.get("callAnalysis").toString());//通话概况
                        JSONArray activeCallAnalysisArray = JSON.parseArray(dataMap.get("activeCallAnalysis").toString());//活跃情况
                        Map<String,Object> silenceAnalysisMap = JSON.parseObject(dataMap.get("silenceAnalysis").toString());//静默情况
                        JSONArray callDurationAnalysisArray = JSON.parseArray(dataMap.get("callDurationAnalysis").toString()); //通话时间段分析
                        JSONArray consumptionAnalysisArray = JSON.parseArray(dataMap.get("consumptionAnalysis").toString()); //消费能力
                        JSONArray tripAnalysisArray = JSON.parseArray(dataMap.get("tripAnalysis").toString()); //出行信息
                        JSONArray socialContactAnalysisArray = JSON.parseArray(dataMap.get("socialContactAnalysis").toString()); //社交关系概况
                        JSONArray callAreaAnalysisArray = JSON.parseArray(dataMap.get("callAreaAnalysis").toString()); //通话区域分析
                        JSONArray contactAnalysisArray = JSON.parseArray(dataMap.get("contactAnalysis").toString()); //通话联系人分析

                        //一、获取认证验证的数据
                        //0身份证号是否命中风险清单
                        String riskListOfIdentityCard = "未命中";
                        Font riskListOfIdentityCardFont = CreateTableUtil.textGreenFont;//设置字体颜色（未命中绿色，命中红色）;
                        //1.是否实名认证
                        String realNameAuthentication = ""; //是否实名认证
                        Font realNameAuthenticationFont = CreateTableUtil.textGreenFont;//设置字体颜色（实名认证绿色，未实名认证红色）
                        //2.手机号是否命中风险清单
                        String mobileNumberRiskList = "未命中";
                        Font mobileNumberRiskListFont = CreateTableUtil.textGreenFont;//设置字体颜色（未命中绿色，命中红色）
                        //3.姓名与运营商数据是否匹配
                        String nameAndOperatorMatching = "";
                        Font nameAndOperatorMatchingFont = CreateTableUtil.textGreenFont;//设置字体颜色（匹配绿色，不匹配红色）
                        //4.身份证号与运营商数据是否匹配
                        String idcardAndOperatorMatching = "";
                        Font idcardAndOperatorMatchingFont = CreateTableUtil.textGreenFont;//设置字体颜色（匹配绿色，不匹配红色）
                        for(Object basicInfoCheck:basicInfoCheckArray){//遍历基本信息检查
                            Map<String,Object> basicInfoCheckMap = JSON.parseObject(basicInfoCheck.toString());
                            if("idcard_check".equals(basicInfoCheckMap.get("item"))){//身份证校验

                            }else if("mobile_check".equals(basicInfoCheckMap.get("item").toString())){//手机号实名制验证
                                realNameAuthentication = basicInfoCheckMap.get("resultDesc").toString();
                                if("0".equals(basicInfoCheckMap.get("result"))){//0：未实名认证，显示红色
                                    realNameAuthenticationFont = CreateTableUtil.textRedFont;
                                }
                            }else if ("idcard_match".equals(basicInfoCheckMap.get("item"))){//身份证与运营商数据匹配
                                idcardAndOperatorMatching = basicInfoCheckMap.get("resultDesc").toString();
                                if("0".equals(basicInfoCheckMap.get("result"))){
                                    idcardAndOperatorMatchingFont = CreateTableUtil.textRedFont;
                                }else if("2".equals(basicInfoCheckMap.get("result")) || "3".equals(basicInfoCheckMap.get("result"))){
                                    idcardAndOperatorMatchingFont = CreateTableUtil.textOrangeFont;
                                }

                            }else if ("name_match".equals(basicInfoCheckMap.get("item"))){//姓名与运营商数据匹配
                                nameAndOperatorMatching = basicInfoCheckMap.get("resultDesc").toString();
                                if("0".equals(basicInfoCheckMap.get("result"))){
                                    nameAndOperatorMatchingFont = CreateTableUtil.textRedFont;
                                }else if("2".equals(basicInfoCheckMap.get("result")) || "3".equals(basicInfoCheckMap.get("result"))){
                                    nameAndOperatorMatchingFont = CreateTableUtil.textOrangeFont;
                                }
                            }
                        }
                        for(Object riskListCheck:riskListCheckArray){
                            Map<String,Object> riskListCheckMap = JSON.parseObject(riskListCheck.toString());
                            if("1".equals(riskListCheckMap.get("result"))){
                                if(riskListCheckMap.get("item").toString().startsWith("idcard")){
                                    riskListOfIdentityCard = "命中";
                                    riskListOfIdentityCardFont = CreateTableUtil.textRedFont;
                                }else if(riskListCheckMap.get("item").toString().startsWith("mobile")){
                                    mobileNumberRiskList = "命中";
                                    mobileNumberRiskListFont = CreateTableUtil.textRedFont;//设置字体颜色（实名认证绿色，未实名认证红色）
                                }
                            }
                        }

                        PdfPTable table = CreateTableUtil.createTable(1);
                        Image img = Image.getInstance("../img/homepage.jpg");//图片的地址
                        img.scaleAbsolute(CreateTableUtil.mmTopx(200),CreateTableUtil.mmTopx(297));
                        PdfPCell photoCell = new PdfPCell(img);
                        photoCell.setBorder(0);
                        photoCell.setHorizontalAlignment(1);
                        table.addCell(photoCell);
                        document.add(table);


                        //################################################大标题标题############################################################
                        Paragraph paragraph = new Paragraph("运营商信用报告",CreateTableUtil.headfontfirst);
                        paragraph.setAlignment(1);
                        document.add(paragraph);
                        document.add(new Paragraph("\n"));

                        //   \t不起作用,没办法只能用n多的空格代替
                        String table_up = "报告编号 : "+reportMap.get("reportNo")+"                                            " +
                                "数据来源 : "+reportMap.get("dataSource").toString()+"                                         "+
                                "获取时间 : "+reportMap.get("reportTime").toString();
                        paragraph = new Paragraph(table_up,CreateTableUtil.textfont);
                        paragraph.setAlignment(1);
                        document.add(paragraph);
                        document.add(new Paragraph("\n"));

                        //################################################基本信息############################################################
                        table = CreateTableUtil.createTable(6);
                        float[] bisicInfocolumnWidths = { 1f, 1f, 1f, 1f, 1f, 1f};
                        table.setWidths(bisicInfocolumnWidths);
                        //######第一行内容######
                        PdfPCell cell = CreateTableUtil.createCell("基本信息", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(6); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);
                        //######第二行内容######
                        cell = CreateTableUtil.createCell("姓名:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("name").toString(), CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("性别:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("gender").toString(), CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("年龄:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("age").toString(), CreateTableUtil.textfont);
                        table.addCell(cell);
                        //######第二行内容######
                        cell = CreateTableUtil.createCell("地址:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("nativeAddress").toString(), CreateTableUtil.textfont);
                        cell.setColspan(5);
                        table.addCell(cell);
                        document.add(table);


                        table = CreateTableUtil.createTable(18);

                        //######第三行内容######
                        cell = CreateTableUtil.createCell("身份证号:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setColspan(3);//跨列
                        cell.setRowspan(2);//跨行
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("identityNo").toString(), CreateTableUtil.textfont);
                        cell.setBorder(0);
                        cell.setBorderWidthRight(0.1f);
                        cell.setColspan(15);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("身份证号是否命中风险清单", CreateTableUtil.textfont);
                        cell.setBorder(0);
                        cell.setBorderWidthBottom(0.1f);
                        cell.setColspan(4);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(riskListOfIdentityCard, riskListOfIdentityCardFont);
                        cell.setBorder(0);
                        cell.setBorderWidthRight(0.1f);
                        cell.setBorderWidthBottom(0.1f);
                        cell.setColspan(11);
                        table.addCell(cell);

                        //######第四行内容######
                        cell = CreateTableUtil.createCell("手机号:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setColspan(3);
                        cell.setRowspan(4);//跨行
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("mobile").toString(), CreateTableUtil.textfont);
                        cell.setBorder(0);

                        cell.setColspan(2);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell(realNameAuthentication,realNameAuthenticationFont);
                        cell.setBorder(0);

                        cell.setColspan(4);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("入网时间 : "+basicInfoMap.get("regTime").toString(),CreateTableUtil.textfont);
                        cell.setBorder(0);
                        cell.setBorderWidthRight(0.1f);
                        cell.setColspan(9);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("手机号是否命中风险清单",CreateTableUtil.textfont);
                        cell.setBorder(0);
                        cell.setColspan(4);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(mobileNumberRiskList,mobileNumberRiskListFont);
                        cell.setBorder(0);
                        cell.setBorderWidthRight(0.1f);
                        cell.setColspan(11);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("姓名与运营商数据是否匹配",CreateTableUtil.textfont);
                        cell.setBorder(0);
                        cell.setColspan(4);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(nameAndOperatorMatching,nameAndOperatorMatchingFont);
                        cell.setBorder(0);
                        cell.setBorderWidthRight(0.1f);
                        cell.setColspan(11);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("身份证号与运营商数据是否匹配",CreateTableUtil.textfont);
                        cell.setBorder(0);
                        cell.setBorderWidthBottom(0.1f);
                        cell.setColspan(5);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(idcardAndOperatorMatching,idcardAndOperatorMatchingFont);
                        cell.setBorder(0);
                        cell.setBorderWidthRight(0.1f);
                        cell.setBorderWidthBottom(0.1f);
                        cell.setColspan(10);
                        table.addCell(cell);
                        document.add(table);

                        //################################################关联信息############################################################
                        table = CreateTableUtil.createTable(6);
                        table.setSpacingBefore(5f); //和上一个表格间距5f

                        //######第一行内容######
                        cell = CreateTableUtil.createCell("关联信息", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(6); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);
                        //######第二行内容######
                        cell = CreateTableUtil.createCell("手机关联的身份证", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        String idcard = relationInfoMap.get("identiyNos").toString();
                        String mobileAndIdcard = "无相关数据";
                        if(!"[]".equals(idcard)){
                            String[] identiyNos = idcard.substring(idcard.indexOf("[")+1,idcard.lastIndexOf("]")).split(",");
                            StringBuilder stb = new StringBuilder();
                            for(String s:identiyNos){
                                stb.append(s);
                                stb.append("        ");
                            }
                            mobileAndIdcard = stb.toString();
                        }
                        cell = CreateTableUtil.createCell(mobileAndIdcard, CreateTableUtil.textfont); //表格中添加的文字
                        cell.setColspan(5);
                        table.addCell(cell);
                        //######第三行内容######
                        cell = CreateTableUtil.createCell("身份证关联的手机", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        String mobile = relationInfoMap.get("mobiles").toString();
                        String idcardAndMobile = "无相关数据";
                        if(!"[]".equals(mobile)){
                            String[] mobiles = idcard.substring(mobile.indexOf("[")+1,mobile.lastIndexOf("]")).split(",");
                            StringBuilder stb = new StringBuilder();
                            for(String s:mobiles){
                                stb.append(s);
                                stb.append("        ");
                            }
                            idcardAndMobile = stb.toString();
                        }
                        cell = CreateTableUtil.createCell(idcardAndMobile, CreateTableUtil.textfont); //表格中添加的文字
                        cell.setColspan(5);
                        table.addCell(cell);
                        //######第四行内容######
                        cell = CreateTableUtil.createCell("身份证关联的地址", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        String address = relationInfoMap.get("mobiles").toString();
                        String idcardAndAddress = "无相关数据";
                        if(!"[]".equals(mobile)){
                            String[] addresss = idcard.substring(address.indexOf("[")+1,address.lastIndexOf("]")).split(",");
                            StringBuilder stb = new StringBuilder();
                            for(String s:addresss){
                                stb.append(s);
                                stb.append("        ");
                            }
                            idcardAndAddress = stb.toString();
                        }
                        cell = CreateTableUtil.createCell(idcardAndAddress, CreateTableUtil.textfont); //表格中添加的文字
                        cell.setColspan(5);
                        table.addCell(cell);
                        document.add(table);

                        //################################################用户画像############################################################
                        table = CreateTableUtil.createTable(6);
                        table.setSpacingBefore(5f); //和上一个表格间距5f

                        //######第一行内容（标题）######
                        cell = CreateTableUtil.createCell("用户画像", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(6); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);
                        //######第二行内容（评分）######
                        if(dataMap.get("scoreAnalysis") != null){
                            Map<String,Object> scoreAnalysisMap = JSON.parseObject(dataMap.get("scoreAnalysis").toString());
                            JSONArray deductionDetailsArray = JSON.parseArray(scoreAnalysisMap.get("deductionDetails").toString());


                            cell = CreateTableUtil.createCell("评分", CreateTableUtil.textfont);
                            cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                            cell.setRowspan(deductionDetailsArray.size()+1);//跨行
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(scoreAnalysisMap.get("score").toString()+"分", CreateTableUtil.textfont);
                            cell.setColspan(2);
                            cell.setBorderWidthRight(0);
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell("综合评估个人信用的分值", CreateTableUtil.textkeyfont);
                            cell.setColspan(3);
                            cell.setBorderWidthLeft(0);
                            table.addCell(cell);

                            if(deductionDetailsArray.size()>0){
                                cell = CreateTableUtil.createCell("扣分明细", CreateTableUtil.textfont);
                                cell.setRowspan(deductionDetailsArray.size());//跨行
                                cell.setBorderWidthRight(0);
                                cell.setBorderWidthTop(0);
                                table.addCell(cell);
                                int i = 1;
                                for(Object deductionDetails:deductionDetailsArray){
                                    Map<String,Object> deductionDetailMap = JSON.parseObject(deductionDetails.toString());
                                    cell = CreateTableUtil.createCell(deductionDetailMap.get("deduction").toString()+"分",CreateTableUtil.textRedFont);
                                    cell.setBorder(0);
                                    if(i == deductionDetailsArray.size()) {
                                        cell.setBorderWidthBottom(0.1f);
                                    }
                                    table.addCell(cell);
                                    cell = CreateTableUtil.createCell(deductionDetailMap.get("desc").toString(),CreateTableUtil.textkeyfont);
                                    cell.setColspan(3);
                                    cell.setBorder(0);
                                    cell.setBorderWidthRight(0.1f);
                                    if(i == deductionDetailsArray.size()) {
                                        cell.setBorderWidthBottom(0.1f);
                                    }
                                    table.addCell(cell);
                                    i++;
                                }
                            }
                        }
                        //######第三行内容（风险情况）######
                        cell = CreateTableUtil.createCell("风险情况", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setRowspan(4);//跨行
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("风险清单", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(riskProfileMap.get("riskListCnt")+"次", riskListCheckArray.size()==0?CreateTableUtil.textfont:CreateTableUtil.textRedFont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("本人身份证号、手机号命中风险清单总次数", CreateTableUtil.textkeyfont);
                        cell.setColspan(3);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("信贷逾期", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthBottom(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(riskProfileMap.get("overdueLoanCnt")+"次", Integer.valueOf(riskProfileMap.get("overdueLoanCnt").toString())==0?CreateTableUtil.textfont:CreateTableUtil.textRedFont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(0);
                        cell.setBorderWidthBottom(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("本人身份证或手机号命中信贷逾期的情况", CreateTableUtil.textkeyfont);
                        cell.setColspan(3);
                        cell.setBorderWidthLeft(0);
                        cell.setBorderWidthBottom(0);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("多头借贷", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(riskProfileMap.get("multiLendCnt")+"次", Integer.valueOf(riskProfileMap.get("multiLendCnt").toString())==0?CreateTableUtil.textfont:CreateTableUtil.textRedFont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("本人身份证或手机号命中多平台借贷申请情况", CreateTableUtil.textkeyfont);
                        cell.setColspan(3);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("风险通话", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(riskProfileMap.get("riskCallCnt")+"次", Integer.valueOf(riskProfileMap.get("riskCallCnt").toString())==0?CreateTableUtil.textfont:CreateTableUtil.textRedFont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("本手机号与网贷、110 等电话联系次数统计", CreateTableUtil.textkeyfont);
                        cell.setColspan(3);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);

                        //######第三行内容（社交情况）######
                        cell = CreateTableUtil.createCell("社交情况", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setRowspan(4);//跨行
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("最常联系地区", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(socialContactProfileMap.get("freContactArea").toString(), CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("180天内与本人手机通话次数按区域分前3 位\n", CreateTableUtil.textkeyfont);
                        cell.setColspan(3);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("联系人号码总数", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthBottom(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(socialContactProfileMap.get("contactNumCnt").toString()+"个", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(0);
                        cell.setBorderWidthBottom(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("180天内与本人手机通话的号码总数", CreateTableUtil.textkeyfont);
                        cell.setColspan(3);
                        cell.setBorderWidthLeft(0);
                        cell.setBorderWidthBottom(0);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("互通号码总数", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(socialContactProfileMap.get("interflowContactCnt").toString()+"个", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("180天内与本人手机相互通过话的号码总数", CreateTableUtil.textkeyfont);
                        cell.setColspan(3);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("联系人风险名单总数", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(socialContactProfileMap.get("contactRishCnt").toString()+"个", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("180天内与本人手机号通话的号码命中风险名单的总数", CreateTableUtil.textkeyfont);
                        cell.setColspan(3);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);

                        //######第三行内容（通话情况）######
                        cell = CreateTableUtil.createCell("通话情况", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setRowspan(6);//跨行
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("日均通话次数", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(callProfileMap.get("avgCallCnt").toString()+"次", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("180天内总通话次数按日平均", CreateTableUtil.textkeyfont);
                        cell.setColspan(3);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("日均通话时长", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(callProfileMap.get("avgCallTime").toString()+"分钟", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("180天内总通话时长按日平均", CreateTableUtil.textkeyfont);
                        cell.setColspan(3);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("手机静默次数", CreateTableUtil.textRedFont);
                        cell.setRowspan(2);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthTop(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(callProfileMap.get("silenceCnt").toString()+"次", CreateTableUtil.textRedFont);
                        cell.setRowspan(2);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(0);
                        cell.setBorderWidthTop(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("180天内超过24小时持续无通话无短信的总次数", CreateTableUtil.textkeyfont);
                        cell.setColspan(3);
                        cell.setBorder(0);
                        cell.setBorderWidthRight(0.1f);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("( 重点关注：手机如果经常关机或不在服务区，有养号可能)", CreateTableUtil.textRedFont);
                        cell.setColspan(3);
                        cell.setBorderWidthTop(0);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("夜间通话次数", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthBottom(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(callProfileMap.get("nightCallCnt")+"次", CreateTableUtil.textfont);
                        cell.setBorder(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("统计23：00-5：30 的通话次数和平均时长（按次平均）", CreateTableUtil.textfont);
                        cell.setBorderWidthLeft(0);
                        cell.setRowspan(2);
                        cell.setColspan(3);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("平均时长", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthTop(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(callProfileMap.get("nightCallTime")+"分钟", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(0);
                        cell.setBorderWidthTop(0);
                        table.addCell(cell);

                        //######第四行内容（消费情况）######
                        cell = CreateTableUtil.createCell("消费情况", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setRowspan(2);//跨行
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("月均消费", CreateTableUtil.textRedFont);
                        cell.setRowspan(2);//跨行
                        cell.setBorderWidthTop(0);
                        cell.setBorderWidthRight(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(consumptionProfileMap.get("avgFeeMonth")+"元", CreateTableUtil.textRedFont);
                        cell.setRowspan(2);//跨行
                        cell.setBorderWidthTop(0);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthLeft(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("6个内总消费按月平均", CreateTableUtil.textfont);
                        cell.setColspan(3);
                        cell.setBorderWidthTop(0);
                        cell.setBorderWidthLeft(0);
                        cell.setBorderWidthBottom(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("(重点关注：有小号，副号可能)", CreateTableUtil.textRedFont);
                        cell.setColspan(3);
                        cell.setBorderWidthLeft(0);
                        cell.setBorderWidthTop(0);
                        table.addCell(cell);

                        document.add(table);

                        //################################################风险清单检测############################################################
                        table = CreateTableUtil.createTable(6);
                        table.setSpacingBefore(5f); //和上一个表格间距5f

                        //######第一行内容（标题）######
                        cell = CreateTableUtil.createCell("风险清单检测", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(6); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("项目", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setColspan(2);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("      命中次数", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setColspan(4);
                        table.addCell(cell);
                        if(riskListCheckArray.size()>0){
                            for(Object riskListCheck:riskListCheckArray){
                                Map<String,Object> riskListCheckMap = JSON.parseObject(riskListCheck.toString());
                                cell = CreateTableUtil.createCell(riskListCheckMap.get("desc").toString(), CreateTableUtil.textfont);
                                cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                                cell.setColspan(2);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("              "+riskListCheckMap.get("result").toString(), Integer.valueOf(riskListCheckMap.get("result").toString())==0?CreateTableUtil.textfont:CreateTableUtil.textRedFont);
                                cell.setColspan(4);
                                table.addCell(cell);
                            }
                        }else {
                            cell = CreateTableUtil.createCell("无相关数据", CreateTableUtil.textfont);
                            cell.setColspan(6);
                            cell.setHorizontalAlignment(1);
                            table.addCell(cell);
                        }
                        document.add(table);


                        //################################################信贷逾期############################################################
                        table = CreateTableUtil.createTable(30);
                        table.setSpacingBefore(5f); //和上一个表格间距5f

                        //######第一行内容（标题）######
                        cell = CreateTableUtil.createCell("信贷逾期", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(30); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);

                        //######第一行内容######
                        cell = CreateTableUtil.createCell("类别", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setColspan(10);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("内容", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setColspan(20);
                        table.addCell(cell);

                        //######第三行内容######
                        if(overdueLoanCheckArray.size()>0){
                            for(Object overdueLoanCheck:overdueLoanCheckArray){
                                Map<String,Object> overdueLoanCheckMap = JSON.parseObject(overdueLoanCheck.toString());
                                JSONArray detailsArray = JSON.parseArray(overdueLoanCheckMap.get("details").toString()); //信贷逾期详情
                                int i = 1;
                                if(detailsArray.size() > 0){
                                    cell = CreateTableUtil.createCell(overdueLoanCheckMap.get("desc").toString(), CreateTableUtil.textfont);
                                    cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                                    cell.setRowspan(detailsArray.size());
                                    cell.setColspan(10);
                                    table.addCell(cell);
                                    for(Object details:detailsArray){
                                        //System.out.println(detailsArray.size());
                                        Map<String,Object> detailsMap = JSON.parseObject(details.toString());

                                        cell = CreateTableUtil.createCell("逾期金额 :", CreateTableUtil.textfont);
                                        cell.setBorderWidthRight(0);
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(detailsMap.get("overdueAmt")+"元", CreateTableUtil.textRedFont);
                                        cell.setBorderWidthRight(0);
                                        cell.setBorderWidthLeft(0);
                                        cell.setColspan(4);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("逾期天数 :", CreateTableUtil.textfont);
                                        cell.setColspan(3);
                                        cell.setBorderWidthRight(0);
                                        cell.setBorderWidthLeft(0);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(detailsMap.get("overdueDays")+"天", CreateTableUtil.textRedFont);
                                        cell.setColspan(4);
                                        cell.setBorderWidthRight(0);
                                        cell.setBorderWidthLeft(0);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("逾期时间 :", CreateTableUtil.textfont);
                                        cell.setColspan(3);
                                        cell.setBorderWidthRight(0);
                                        cell.setBorderWidthLeft(0);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(detailsMap.get("overdueTime").toString(), CreateTableUtil.textRedFont);
                                        cell.setColspan(3);
                                        cell.setBorderWidthLeft(0);
                                        table.addCell(cell);
                                    }
                                }
                            }
                        }else {
                            cell = CreateTableUtil.createCell("无相关数据", CreateTableUtil.textfont);
                            cell.setColspan(15);
                            cell.setHorizontalAlignment(1);
                            table.addCell(cell);
                        }
                        document.add(table);

                        //################################################多头借贷############################################################
                        table = CreateTableUtil.createTable(3);
                        table.setSpacingBefore(5f); //和上一个表格间距5f

                        //######第一行内容（标题）######
                        cell = CreateTableUtil.createCell("多头借贷", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(3); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);

                        //######第二行内容######
                        cell = CreateTableUtil.createCell("类别", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setColspan(1);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("内容", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setColspan(2);
                        table.addCell(cell);

                        //######第三行内容######
                        if(multiLendCheckArray.size()>0){
                            for(Object multiLendCheck:multiLendCheckArray){
                                Map<String,Object> multiLendCheckMap = JSON.parseObject(multiLendCheck.toString());
                                JSONArray detailsArray = JSON.parseArray(multiLendCheckMap.get("details").toString());
                                if(detailsArray.size()>0){
                                    cell = CreateTableUtil.createCell(multiLendCheckMap.get("desc").toString(), CreateTableUtil.textfont);
                                    cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                                    cell.setRowspan(detailsArray.size());
                                    table.addCell(cell);
                                    for(Object details:detailsArray){
                                        Map<String,Object> detailsMap = JSON.parseObject(details.toString());
                                        cell = CreateTableUtil.createCell(detailsMap.get("lendType").toString(), CreateTableUtil.textfont);
                                        cell.setBorderWidthRight(0);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(detailsMap.get("lendCnt")+"次", CreateTableUtil.textRedFont);
                                        cell.setBorderWidthLeft(0);
                                        table.addCell(cell);
                                    }
                                }
                            }
                        }
                        document.add(table);

                        //################################################风险通话检测############################################################
                        table = CreateTableUtil.createTable(9);
                        table.setSpacingBefore(5f); //和上一个表格间距5f

                        //######第一行内容（标题）######
                        cell = CreateTableUtil.createCell("风险通话检测", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(9); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("检查项", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setRowspan(2);
                        cell.setHorizontalAlignment(1);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("命中描述", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setColspan(2);
                        cell.setRowspan(2);
                        cell.setHorizontalAlignment(1);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("命中次数", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setRowspan(2);
                        cell.setHorizontalAlignment(1);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("时长(s)", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setRowspan(2);
                        cell.setHorizontalAlignment(1);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("详情", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setColspan(4);
                        cell.setHorizontalAlignment(1);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("通话标记", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setHorizontalAlignment(1);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("通话类型", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setHorizontalAlignment(1);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("通话次数", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setHorizontalAlignment(1);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("通话时长(s)", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        cell.setHorizontalAlignment(1);
                        table.addCell(cell);
                        if(riskCallCheckArray.size()>0){
                            for(Object riskCallCheck:riskCallCheckArray){
                                Map<String,Object> riskCallCheckMap = JSON.parseObject(riskCallCheck.toString());
                                JSONArray detailsArray = JSON.parseArray(riskCallCheckMap.get("details").toString());
                                if(detailsArray.size()>0){
                                    cell = CreateTableUtil.createCell(riskCallCheckMap.get("desc").toString(), CreateTableUtil.textfont);
                                    cell.setRowspan(detailsArray.size());
                                    table.addCell(cell);
                                    cell = CreateTableUtil.createCell(riskCallCheckMap.get("hitDesc").toString(), CreateTableUtil.textfont);
                                    cell.setColspan(2);
                                    cell.setRowspan(detailsArray.size());
                                    table.addCell(cell);
                                    cell = CreateTableUtil.createCell(riskCallCheckMap.get("cnt").toString(), CreateTableUtil.textfont);
                                    cell.setRowspan(detailsArray.size());
                                    table.addCell(cell);
                                    cell = CreateTableUtil.createCell(riskCallCheckMap.get("duration").toString(), CreateTableUtil.textfont);
                                    cell.setRowspan(detailsArray.size());
                                    table.addCell(cell);
                                    for(Object details:detailsArray){
                                        Map<String,Object> detailsMap = JSON.parseObject(details.toString());
                                        cell = CreateTableUtil.createCell(detailsMap.get("callTag").toString(), CreateTableUtil.textfont);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(detailsMap.get("callType").toString(), CreateTableUtil.textfont);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(detailsMap.get("callCnt").toString(), CreateTableUtil.textfont);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(detailsMap.get("callTime").toString(), CreateTableUtil.textfont);
                                        table.addCell(cell);
                                    }
                                }
                            }
                        }else {
                            cell = CreateTableUtil.createCell("无相关数据", CreateTableUtil.textfont);
                            cell.setColspan(9);
                            cell.setHorizontalAlignment(1);
                            table.addCell(cell);
                        }

                        document.add(table);

                        //################################################通话概况############################################################
                        table = CreateTableUtil.createTable(4);
                        table.setSpacingBefore(5f); //和上一个表格间距5f

                        //######第一行内容（标题）######
                        cell = CreateTableUtil.createCell("通话概况", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(4); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("日均通话次数", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthBottom(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("日均通话时长", CreateTableUtil.textfont);
                        cell.setBorderWidthLeft(0);
                        cell.setBorderWidthBottom(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("日均主叫次数", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthBottom(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("日均主叫时长", CreateTableUtil.textfont);
                        cell.setBorderWidthLeft(0);
                        cell.setBorderWidthBottom(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(callAnalysisMap.get("avgCallCnt")+"次", CreateTableUtil.textGreenFont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthTop(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(callAnalysisMap.get("avgCallTime")+"分钟", CreateTableUtil.textGreenFont);
                        cell.setBorderWidthLeft(0);
                        cell.setBorderWidthTop(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(callAnalysisMap.get("avgCallingCnt")+"次", CreateTableUtil.textGreenFont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthTop(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(callAnalysisMap.get("avgCallingTime")+"分钟", CreateTableUtil.textGreenFont);
                        cell.setBorderWidthLeft(0);
                        cell.setBorderWidthTop(0);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("日均被叫次数", CreateTableUtil.textfont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthBottom(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("日均被叫时长", CreateTableUtil.textfont);
                        cell.setBorderWidthLeft(0);
                        cell.setBorderWidthBottom(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("本地通话占比", CreateTableUtil.textfont);
                        cell.setBorderWidthBottom(0);
                        cell.setColspan(2);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(callAnalysisMap.get("avgCalledCnt")+"次", CreateTableUtil.textGreenFont);
                        cell.setBorderWidthRight(0);
                        cell.setBorderWidthTop(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(callAnalysisMap.get("avgCalledTime")+"分钟", CreateTableUtil.textGreenFont);
                        cell.setBorderWidthLeft(0);
                        cell.setBorderWidthTop(0);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(callAnalysisMap.get("locCallPct").toString(), CreateTableUtil.textGreenFont);
                        cell.setColspan(2);
                        cell.setBorderWidthTop(0);
                        table.addCell(cell);

                        document.add(table);
                        //################################################活跃情况############################################################
                        table = CreateTableUtil.createTable(6);
                        table.setSpacingBefore(5f); //和上一个表格间距5f
                        //######第一行内容（标题）######
                        cell = CreateTableUtil.createCell("活跃情况", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(6); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("项目", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setColspan(2); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("近1月", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("近3月", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("近4月", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("月均", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);

                        if(activeCallAnalysisArray.size()>0){
                            for(Object activeCallAnalysis:activeCallAnalysisArray){
                                Map<String,Object> activeCallAnalysisMap = JSON.parseObject(activeCallAnalysis.toString());

                                cell = CreateTableUtil.createCell(activeCallAnalysisMap.get("desc").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2); //合并列
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(activeCallAnalysisMap.get("lately1m").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(activeCallAnalysisMap.get("lately3m").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(activeCallAnalysisMap.get("lately6m").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(activeCallAnalysisMap.get("avgMonth").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                            }
                        }else {
                            cell = CreateTableUtil.createCell("无相关数据", CreateTableUtil.textfont);
                            cell.setColspan(6);
                            cell.setHorizontalAlignment(1);
                            table.addCell(cell);
                        }

                        document.add(table);
                        //################################################静默情况（近六个月）############################################################
                        table = CreateTableUtil.createTable(72);
                        table.setSpacingBefore(5f); //和上一个表格间距5f
                        //######第一行内容（标题）######
                        cell = CreateTableUtil.createCell("静默情况(近六个月)", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(14); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("(重点关注：离开长居地时，手机不在服务区或关机)", CreateTableUtil.headRedfont); //表格中添加的文字
                        cell.setColspan(58); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);
                        //######第二行内容######
                        cell = CreateTableUtil.createCell("总静默次数", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(7);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("总静默时长(小时) ", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(10);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("最长一次静默开始时间", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(14);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("最长一次静默时长(小时) ", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(14);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("最近一次静默开始时间", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(13);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("最近一次静默时长(小时)", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(14);
                        table.addCell(cell);

                        //######第三行内容######
                        cell = CreateTableUtil.createCell(silenceAnalysisMap.get("silenceCnt").toString(), CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(7);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(silenceAnalysisMap.get("silenceTime").toString(), CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(10);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(silenceAnalysisMap.get("longestSilenceStart").toString(), CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(14);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(silenceAnalysisMap.get("longestSilenceTime").toString(), CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(14);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(silenceAnalysisMap.get("lastSilenceStart").toString(), CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(13);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(silenceAnalysisMap.get("lastSilenceTime").toString(), CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(14);
                        table.addCell(cell);

                        document.add(table);
                        //################################################通话时间分析（近6 个月）############################################################
                        table = CreateTableUtil.createTable(40);
                        table.setSpacingBefore(5f); //和上一个表格间距5f
                        //######第一行内容（标题）######
                        cell = CreateTableUtil.createCell("通话时间分析(近6个月)", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(40); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);
                        document.add(table);
                        //插入饼图
                        table = CreateTableUtil.createTable(1);
                        DefaultPieDataset dpd=new DefaultPieDataset(); //建立一个默认的饼图
                        if(callDurationAnalysisArray.size()>0) {
                            for (Object callDurationAnalysis : callDurationAnalysisArray) {
                                Map<String,Object> callDurationAnalysisMap = JSON.parseObject(callDurationAnalysis.toString());
                                //创建统计图
                                dpd.setValue(callDurationAnalysisMap.get("desc").toString(), Integer.valueOf(callDurationAnalysisMap.get("callCnt").toString()));  //输入数据
                            }
                            pieChartUrl = StatisticalChartUtil.createStatistrical(dpd,"通话时间分析（近六个月）");
                            if(pieChartUrl != null){
                                img = Image.getInstance(pieChartUrl);//图片的地址
                                img.scaleAbsolute(500,300);
                                photoCell = new PdfPCell(img);
                                photoCell.setHorizontalAlignment(1);
                                photoCell.setRowspan(20);
                                photoCell.setBorder(0);
                                photoCell.setBorderWidthRight(0.1f);
                                photoCell.setBorderWidthLeft(0.1f);
                                table.addCell(photoCell);
                                document.add(table);
                            }
                        }

                        table = CreateTableUtil.createTable(40);
                        cell = CreateTableUtil.createCell("时间段", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(4);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("通话次数", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(4);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("通话号码数", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(4);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("最常联系号码及联系次数", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(8);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("平均通话时长(秒)", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(4);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("主叫次数", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(3);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("主叫时长(秒)", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(5);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("被叫次数", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(3);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("被叫时长(秒)", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        cell.setColspan(5);
                        table.addCell(cell);

                        if(callDurationAnalysisArray.size()>0){
                            for(Object callDurationAnalysis:callDurationAnalysisArray){
                                Map<String,Object> callDurationAnalysisMap = JSON.parseObject(callDurationAnalysis.toString());

                                cell = CreateTableUtil.createCell(callDurationAnalysisMap.get("desc").toString(), CreateTableUtil.textfont); //描述
                                cell.setColspan(4);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(callDurationAnalysisMap.get("callCnt").toString(), CreateTableUtil.textfont); //通话次数
                                cell.setColspan(4);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(callDurationAnalysisMap.get("callNumCnt").toString(), CreateTableUtil.textfont); //通话号码数
                                cell.setColspan(4);
                                table.addCell(cell);
                                if(StringUtils.isEmpty(callDurationAnalysisMap.get("freqContactNum").toString())){
                                    cell = CreateTableUtil.createCell("----", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setBorderWidthRight(0);
                                    cell.setColspan(5);
                                    table.addCell(cell);
                                    cell = CreateTableUtil.createCell(null, CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(3);
                                    cell.setBorderWidthLeft(0);
                                    table.addCell(cell);
                                }else {
                                    cell = CreateTableUtil.createCell(callDurationAnalysisMap.get("freqContactNum")+" :", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(5);
                                    cell.setBorderWidthRight(0);
                                    table.addCell(cell);
                                    cell = CreateTableUtil.createCell(callDurationAnalysisMap.get("freqContactNumCnt")+"次", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(3);
                                    cell.setBorderWidthLeft(0);
                                    table.addCell(cell);
                                }
                                cell = CreateTableUtil.createCell(callDurationAnalysisMap.get("avgCallTime").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(4);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(callDurationAnalysisMap.get("callingCnt").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(callDurationAnalysisMap.get("callingTime").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(5);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(callDurationAnalysisMap.get("calledCnt").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(callDurationAnalysisMap.get("calledTime").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(5);
                                table.addCell(cell);
                            }
                        }else {
                            cell = CreateTableUtil.createCell("无相关数据", CreateTableUtil.textfont);
                            cell.setColspan(40);
                            cell.setHorizontalAlignment(1);
                            table.addCell(cell);
                        }
                        document.add(table);
                        //################################################消费能力############################################################
                        table = CreateTableUtil.createTable(6);
                        table.setSpacingBefore(5f); //和上一个表格间距5f

                        //######第一行内容（标题）######
                        cell = CreateTableUtil.createCell("消费能力", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(6); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("项目", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setColspan(2);
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("近1月", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("近3月", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("近6月", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("平均", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);

                        if(consumptionAnalysisArray.size()>0){
                            for(Object consumptionAnalysis:consumptionAnalysisArray){
                                Map<String,Object> consumptionAnalysisMap = JSON.parseObject(consumptionAnalysis.toString());
                                cell = CreateTableUtil.createCell(consumptionAnalysisMap.get("desc").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(consumptionAnalysisMap.get("lately1m").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(consumptionAnalysisMap.get("lately3m").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(consumptionAnalysisMap.get("lately6m").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(consumptionAnalysisMap.get("avgMonth").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                            }
                        }else {
                            cell = CreateTableUtil.createCell("无相关数据", CreateTableUtil.textfont);
                            cell.setColspan(6);
                            cell.setHorizontalAlignment(1);
                            table.addCell(cell);
                        }
                        document.add(table);

                        //################################################出行信息############################################################
                        table = CreateTableUtil.createTable(4);
                        table.setSpacingBefore(5f); //和上一个表格间距5f

                        //######第一行内容（标题）######
                        cell = CreateTableUtil.createCell("出行信息", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(4); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("出发时间", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("回程时间", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("出发地", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("目的地", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);

                        if(tripAnalysisArray.size()>0){
                            for(Object tripAnalysis:tripAnalysisArray){
                                Map<String,Object> tripAnalysisMap = JSON.parseObject(tripAnalysis.toString());
                                cell = CreateTableUtil.createCell(tripAnalysisMap.get("departureDate").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(tripAnalysisMap.get("returnDate").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(tripAnalysisMap.get("departurePlace").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(tripAnalysisMap.get("destinationPlace").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                            }
                        }else {
                            cell = CreateTableUtil.createCell("无相关数据", CreateTableUtil.textfont);
                            cell.setColspan(4);
                            cell.setHorizontalAlignment(1);
                            table.addCell(cell);
                        }
                        document.add(table);
                        //################################################社交关系############################################################
                        table = CreateTableUtil.createTable(4);
                        table.setSpacingBefore(5f); //和上一个表格间距5f

                        //######第一行内容（标题）######
                        cell = CreateTableUtil.createCell("社交关系", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(4); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("项目", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("内容", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("内容描述", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setColspan(2);
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);

                        if(socialContactAnalysisArray.size()>0){
                            for(Object socialContactAnalysis:socialContactAnalysisArray){
                                Map<String,Object> socialContactAnalysisMap = JSON.parseObject(socialContactAnalysis.toString());
                                cell = CreateTableUtil.createCell(socialContactAnalysisMap.get("desc").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(socialContactAnalysisMap.get("content").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(socialContactAnalysisMap.get("contentDesc").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                table.addCell(cell);
                            }
                        }else {
                            cell = CreateTableUtil.createCell("无相关数据", CreateTableUtil.textfont);
                            cell.setColspan(4);
                            cell.setHorizontalAlignment(1);
                            table.addCell(cell);
                        }
                        document.add(table);
                        //################################################通话区域分析############################################################
                        table = CreateTableUtil.createTable(8);
                        table.setSpacingBefore(5f); //和上一个表格间距5f
                        //######第一行内容（标题）######
                        cell = CreateTableUtil.createCell("通话区域分析", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(8); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);
                        document.add(table);

                        table = CreateTableUtil.createTable(1);
                        //创建条形统计图
                        DefaultCategoryDataset dataset=new DefaultCategoryDataset();//创建条形统计图
                        //添加数据
                        if(callAreaAnalysisArray.size()>0) {
                            for (Object callAreaAnalysis : callAreaAnalysisArray) {
                                Map<String, Object> callAreaAnalysisMap = JSON.parseObject(callAreaAnalysis.toString());
                                dataset.addValue(Integer.valueOf(callAreaAnalysisMap.get("callCnt").toString()),"地区",callAreaAnalysisMap.get("attribution").toString());
                            }
                            barChartUrl = StatisticalChartUtil.createBarGraph(dataset,"通话区域分析","","次数");
                            if(barChartUrl != null){
                                img = Image.getInstance(barChartUrl);//图片的地址
                                img.scaleAbsolute(500,300);
                                photoCell = new PdfPCell(img);
                                photoCell.setHorizontalAlignment(1);
                                photoCell.setRowspan(20);
                                photoCell.setBorder(0);
                                photoCell.setBorderWidthRight(0.1f);
                                photoCell.setBorderWidthLeft(0.1f);
                                table.addCell(photoCell);
                                document.add(table);
                            }
                        }
                        table = CreateTableUtil.createTable(8);
                        cell = CreateTableUtil.createCell("通话地", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("通话次数", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("通话号码数", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("通话时长(s)", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("主叫次数", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("主叫时长(s)", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("被叫次数", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("被叫时长(s)", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);

                        if(callAreaAnalysisArray.size()>0){
                            for(Object callAreaAnalysis:callAreaAnalysisArray){
                                Map<String,Object> callAreaAnalysisMap = JSON.parseObject(callAreaAnalysis.toString());
                                cell = CreateTableUtil.createCell(callAreaAnalysisMap.get("attribution").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(callAreaAnalysisMap.get("callCnt").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(callAreaAnalysisMap.get("callNumCnt").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(callAreaAnalysisMap.get("callTime").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(callAreaAnalysisMap.get("callingCnt").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(callAreaAnalysisMap.get("callingTime").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(callAreaAnalysisMap.get("calledCnt").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(callAreaAnalysisMap.get("calledTime").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                table.addCell(cell);
                            }
                        }else {
                            cell = CreateTableUtil.createCell("无相关数据", CreateTableUtil.textfont);
                            cell.setColspan(8);
                            cell.setHorizontalAlignment(1);
                            table.addCell(cell);
                        }
                        document.add(table);
                        //################################################通话联系人分析############################################################
                        table = CreateTableUtil.createTable(15);
                        table.setSpacingBefore(5f); //和上一个表格间距5f

                        cell = CreateTableUtil.createCell("通话联系人分析", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(15); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("号码", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setColspan(2);
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("互联网标识", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("风险名单", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("归属地", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("通话次数", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("通话时长(s)", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("主叫次数", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("主叫时长(s)", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("被叫次数", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("被叫时长(s)", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("最近一次通话时间", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setColspan(3);
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("最近一次通话时长(s)", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        if(contactAnalysisArray.size()>0){
                            for(Object contactAnalysis:contactAnalysisArray){
                                Map<String,Object> contactAnalysisMap = JSON.parseObject(contactAnalysis.toString());
                                cell = CreateTableUtil.createCell(contactAnalysisMap.get("callNum").toString(), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(contactAnalysisMap.get("callTag").toString(), CreateTableUtil.textfont); //互联网标识
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(contactAnalysisMap.get("isHitRiskList").toString(), CreateTableUtil.textfont); //风险名单
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(contactAnalysisMap.get("attribution").toString(), CreateTableUtil.textfont); //归属地
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(contactAnalysisMap.get("callCnt").toString(), CreateTableUtil.textfont); //通话次数
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(contactAnalysisMap.get("callTime").toString(), CreateTableUtil.textfont); //通话时长(s)
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(contactAnalysisMap.get("callingCnt").toString(), CreateTableUtil.textfont); //主叫次数
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(contactAnalysisMap.get("callingTime").toString(), CreateTableUtil.textfont); //主叫时长(s)
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(contactAnalysisMap.get("calledCnt").toString(), CreateTableUtil.textfont); //被叫次数
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(contactAnalysisMap.get("calledTime").toString(), CreateTableUtil.textfont); //被叫时长(s)
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(contactAnalysisMap.get("lastStart").toString(), CreateTableUtil.textfont); //最近一次通话时间
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(contactAnalysisMap.get("lastTime").toString(), CreateTableUtil.textfont); //最近一次通话时长(s)
                                table.addCell(cell);
                            }
                        }else {
                            cell = CreateTableUtil.createCell("无相关数据", CreateTableUtil.textfont);
                            cell.setColspan(14);
                            cell.setHorizontalAlignment(1);
                            table.addCell(cell);
                        }
                        document.add(table);

                        paragraph = new Paragraph("\n\n", CreateTableUtil.keyGreyFont);
                        document.add(paragraph);
                        //备注
                        paragraph = new Paragraph("免责声明 : \n", CreateTableUtil.keyGreyFont);
                        document.add(paragraph);
                        paragraph = new Paragraph("1.本报告为睿普征信根据信息主体的授权将查询到的信息进行收集、归纳，未对该信息作任何的修改，睿普征信不对信息的有效性、准确性、完整性做出任何承诺和保证。您若对该报告有任何疑问的，欢迎及时向睿普征信及信息源提出异议。\n", CreateTableUtil.textfont);
                        document.add(paragraph);
                        paragraph = new Paragraph("2.睿普征信对本报告不作任何明示或暗示的保证，请您自行承担相关的决策风险。请您按照约定的范围使用本报告，不得将本报告提供给第三人，否则后果自负。\n", CreateTableUtil.textfont);
                        document.add(paragraph);

                        // 关闭文档
                        document.close();
                        return true;
                    }
                }
            }
            // 关闭文档
            //document.close();
            return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            if(pieChartUrl != null){
                StatisticalChartUtil.deletePhoto(pieChartUrl);
            }
            if(barChartUrl != null){
                StatisticalChartUtil.deletePhoto(barChartUrl);
            }
        }
    }
}
