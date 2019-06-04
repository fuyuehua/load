package com.rip.load.utils.pdfUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @Description: 风控报告
 * @Author: FYH
 * @Date: Created in 17:12 2019/5/28
 * @Modified:
 */
public class WindControlReportUtil {
    /**
     * 运营商信用报告
     *
     * @throws IOException
     * @throws DocumentException
     */
    public static Boolean windControlReport( String json, String savePath ) {
        // 在指定目录下创建一个文件
        File file = new File(savePath);
        try {
            file.createNewFile();
            // 建立一个Document对象
            Document document = new Document();
            // 设置页面大小
            document.setPageSize(PageSize.A4);
            // 建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();
            //################################################封面############################################################
            PdfPTable table = CreateTableUtil.createTable(1);
            Image img = Image.getInstance("../img/homepage.jpg");//图片的地址
            img.scaleAbsolute(CreateTableUtil.mmTopx(200), CreateTableUtil.mmTopx(297));
            PdfPCell photoCell = new PdfPCell(img);
            photoCell.setBorder(0);
            photoCell.setHorizontalAlignment(1);
            table.addCell(photoCell);
            document.add(table);
            //################################################大标题############################################################
            Paragraph paragraph = new Paragraph("综合风控报告", CreateTableUtil.headfontfirst);
            paragraph.setAlignment(1);
            document.add(paragraph);
            document.add(new Paragraph("\n"));
            //################################################获取数据############################################################
            Map<String, Object> jsonMap = JSON.parseObject(json);
            if (jsonMap.get("code") != null && Integer.valueOf(jsonMap.get("code").toString()) == 200) {
                Map<String, Object> resultMap = JSON.parseObject(jsonMap.get("result").toString());
                JSONArray riskRuleItemsArray = JSONArray.parseArray(resultMap.get("riskRuleItems").toString());//风控规则
                JSONArray itemListArray = JSONArray.parseArray(resultMap.get("itemList").toString());//用户详情
                Map<String, Object> userCustomerMap = JSON.parseObject(String.valueOf(resultMap.get("userCustomer")));//用户基本信息
                //################################################风控规则############################################################
                table = CreateTableUtil.createTable(6);
                float[] bisicInfocolumnWidths = {1f, 1f, 1f, 1f, 1f, 1f};
                table.setWidths(bisicInfocolumnWidths);
                //######第一行内容######
                PdfPCell cell = CreateTableUtil.createCell("风控规则", CreateTableUtil.headfontsecond); //表格中添加的文字
                cell.setColspan(6); //合并列
                cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                table.addCell(cell);

                cell = CreateTableUtil.createCell("规则说明", CreateTableUtil.textfont);
                cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                cell.setColspan(4);
                table.addCell(cell);
                cell = CreateTableUtil.createCell("分值", CreateTableUtil.textfont);
                cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                table.addCell(cell);
                cell = CreateTableUtil.createCell("测评结果", CreateTableUtil.textfont);
                cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                table.addCell(cell);

                for (Object riskRuleItems : riskRuleItemsArray) {
                    Map<String, Object> riskRuleItemsMap = JSON.parseObject(String.valueOf(riskRuleItems));
                    Map<String, Object> riskRuleMap = JSON.parseObject(String.valueOf(riskRuleItemsMap.get("riskRule")));
                    Map<String, Object> ruleMap = JSON.parseObject(String.valueOf(riskRuleMap.get("rule")));
                    String info = "";//规则
                    String grade = ""; //分值
                    String flag = ""; //是否通过（1：通过，0：不通过）
                    if ("ageCheck".equals(String.valueOf(ruleMap.get("method")))) {//年龄验证
                        info = String.valueOf(ruleMap.get("info")).replaceFirst("\\*param\\*", String.valueOf(riskRuleMap.get("paramA"))).replaceFirst("\\*param\\*", String.valueOf(riskRuleMap.get("paramB")));
                        grade = String.valueOf(riskRuleMap.get("grade"));
                    } else if ("idCardElements".equals(String.valueOf(ruleMap.get("method")))) {//身份证核验
                        info = String.valueOf(ruleMap.get("info"));
                        grade = String.valueOf(riskRuleMap.get("grade"));
                    } else if ("operatorThreeElements".equals(String.valueOf(ruleMap.get("method")))) {//客户运营商三要素
                        info = String.valueOf(ruleMap.get("info"));
                        grade = String.valueOf(riskRuleMap.get("grade"));
                    } else if ("operatorTwoElementsA".equals(String.valueOf(ruleMap.get("method")))) {//紧急联系人A运营商三要素
                        info = String.valueOf(ruleMap.get("info"));
                        grade = String.valueOf(riskRuleMap.get("grade"));
                    } else if ("operatorTwoElementsB".equals(String.valueOf(ruleMap.get("method")))) {//紧急联系人B运营商三要素
                        info = String.valueOf(ruleMap.get("info"));
                        grade = String.valueOf(riskRuleMap.get("grade"));
                    } else if ("inTheNetworkTime".equals(String.valueOf(ruleMap.get("method")))) {//手机在网时长
                        info = String.valueOf(ruleMap.get("info")).replaceFirst("\\*param\\*", String.valueOf(riskRuleMap.get("paramA")));
                        grade = String.valueOf(riskRuleMap.get("grade"));
                    } else if ("inTheNetworkTimeA".equals(String.valueOf(ruleMap.get("method")))) {//紧急联系人A手机在网时长
                        info = String.valueOf(ruleMap.get("info")).replaceFirst("\\*param\\*", String.valueOf(riskRuleMap.get("paramA")));
                        grade = String.valueOf(riskRuleMap.get("grade"));
                    } else if ("inTheNetworkTimeB".equals(String.valueOf(ruleMap.get("method")))) {//紧急联系人B手机在网时长
                        info = String.valueOf(ruleMap.get("info")).replaceFirst("\\*param\\*", String.valueOf(riskRuleMap.get("paramA")));
                        grade = String.valueOf(riskRuleMap.get("grade"));
                    } else if ("personalEnterprise".equals(String.valueOf(ruleMap.get("method")))) {//个人名下关联企业起诉
                        info = String.valueOf(ruleMap.get("info")).replaceFirst("\\*param\\*", String.valueOf(riskRuleMap.get("paramA")));
                        grade = String.valueOf(riskRuleMap.get("grade"));
                    } else if ("personalEnterprise2".equals(String.valueOf(ruleMap.get("method")))) {//个人名下关联企业行政处罚
                        info = String.valueOf(ruleMap.get("info")).replaceFirst("\\*param\\*", String.valueOf(riskRuleMap.get("paramA")));
                        grade = String.valueOf(riskRuleMap.get("grade"));
                    } else if ("businessData".equals(String.valueOf(ruleMap.get("method")))) {//企业工商数据查询起诉
                        info = String.valueOf(ruleMap.get("info")).replaceFirst("\\*param\\*", String.valueOf(riskRuleMap.get("paramA")));
                        grade = String.valueOf(riskRuleMap.get("grade"));
                    } else if ("businessData2".equals(String.valueOf(ruleMap.get("method")))) {//企业工商数据查询行政处罚
                        info = String.valueOf(ruleMap.get("info")).replaceFirst("\\*param\\*", String.valueOf(riskRuleMap.get("paramA")));
                        grade = String.valueOf(riskRuleMap.get("grade"));
                    } else if ("businessData3".equals(String.valueOf(ruleMap.get("method")))) {//企业工商数据查询严重违法
                        info = String.valueOf(ruleMap.get("info")).replaceFirst("\\*param\\*", String.valueOf(riskRuleMap.get("paramA")));
                        grade = String.valueOf(riskRuleMap.get("grade"));
                    } else if ("personalComplaintInquiryC".equals(String.valueOf(ruleMap.get("method")))) {//个人涉诉
                        info = String.valueOf(ruleMap.get("info")).replaceFirst("\\*param\\*", String.valueOf(riskRuleMap.get("paramA")));
                        grade = String.valueOf(riskRuleMap.get("grade"));
                    }

                    Font font;
                    if ("1".equals(String.valueOf(riskRuleItemsMap.get("flag")))) {
                        flag = "通过";
                        font = CreateTableUtil.textGreenFont;
                    } else {
                        flag = "不通过";
                        font = CreateTableUtil.textRedFont;
                    }
                    cell = CreateTableUtil.createCell(info, CreateTableUtil.textfont);
                    cell.setColspan(4);
                    table.addCell(cell);
                    cell = CreateTableUtil.createCell(grade, CreateTableUtil.textfont);
                    table.addCell(cell);
                    cell = CreateTableUtil.createCell(flag, font);
                    table.addCell(cell);
                }
                document.add(table);
                //################################################基本验证############################################################
                table = CreateTableUtil.createTable(6);
                table.setSpacingBefore(5f); //和上一个表格间距5f
                cell = CreateTableUtil.createCell("基本验证", CreateTableUtil.headfontsecond); //表格中添加的文字
                cell.setColspan(6); //合并列
                cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                table.addCell(cell);

                cell = CreateTableUtil.createCell("姓名", CreateTableUtil.textfont);
                cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                table.addCell(cell);
                cell = CreateTableUtil.createCell(String.valueOf(userCustomerMap.get("realname")), CreateTableUtil.textfont);
                table.addCell(cell);

                cell = CreateTableUtil.createCell("性别", CreateTableUtil.textfont);
                cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                table.addCell(cell);
                cell = CreateTableUtil.createCell(String.valueOf(userCustomerMap.get("idcardSex")), CreateTableUtil.textfont);
                table.addCell(cell);

                cell = CreateTableUtil.createCell("民族", CreateTableUtil.textfont);
                cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                table.addCell(cell);
                cell = CreateTableUtil.createCell(String.valueOf(userCustomerMap.get("idcardNation")), CreateTableUtil.textfont);
                table.addCell(cell);

                cell = CreateTableUtil.createCell("地址", CreateTableUtil.textfont);
                cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                table.addCell(cell);
                cell = CreateTableUtil.createCell(String.valueOf(userCustomerMap.get("idcardAddress")), CreateTableUtil.textfont);
                cell.setColspan(5);
                table.addCell(cell);

                document.add(table);

                table = CreateTableUtil.createTable(6);
                cell = CreateTableUtil.createCell("身份证号", CreateTableUtil.textfont);
                cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                cell.setRowspan(2);
                table.addCell(cell);
                cell = CreateTableUtil.createCell(String.valueOf(userCustomerMap.get("idcard")), CreateTableUtil.textfont);
                cell.setColspan(5);
                table.addCell(cell);

                int num = 0;
                boolean flag = true;
                cell = CreateTableUtil.createCell("身份证二要素核验：", CreateTableUtil.textfont);
                table.addCell(cell);
                for (Object item : itemListArray) {
                    Map<String, Object> itemMap = JSON.parseObject(String.valueOf(item));
                    if ("1".equals(String.valueOf(itemMap.get("type")))) {//身份证二要素核验
                        Map<String, Object> resultJsonMap = JSON.parseObject(String.valueOf(itemMap.get("resultJson")));
                        Map<String, Object> dataMap = JSON.parseObject(String.valueOf(resultJsonMap.get("data")));
                        String result = null;
                        Font font = CreateTableUtil.textGreenFont;
                        if ("0000".equals(String.valueOf(dataMap.get("key")))) {
                            result = "一致";
                        } else if ("9998".equals(String.valueOf(dataMap.get("key")))) {
                            result = "不一致";
                            font = CreateTableUtil.headRedfont;
                        } else if ("3".equals(String.valueOf(dataMap.get("key")))) {
                            result = "无记录";
                            font = CreateTableUtil.textOrangeFont;
                        }
                        cell = CreateTableUtil.createCell(result, font);
                        cell.setColspan(4);
                        table.addCell(cell);
                        flag = false;
                    }
                    num++;
                    if (flag && num >= itemListArray.size()) {
                        cell = CreateTableUtil.createCell(null, CreateTableUtil.textfont);
                        cell.setColspan(4);
                        table.addCell(cell);
                    }
                }
                document.add(table);

                table = CreateTableUtil.createTable(6);
                cell = CreateTableUtil.createCell("手机号：", CreateTableUtil.textfont);
                cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                cell.setRowspan(3);
                table.addCell(cell);

                cell = CreateTableUtil.createCell(String.valueOf(userCustomerMap.get("cellphone")), CreateTableUtil.textfont);
                cell.setColspan(2);
                table.addCell(cell);
                cell = CreateTableUtil.createCell("运营商：", CreateTableUtil.textfont);
                //cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                table.addCell(cell);

                num = 0;
                flag = true;
                for (Object item : itemListArray) {
                    Map<String, Object> itemMap = JSON.parseObject(String.valueOf(item));
                    if ("2".equals(String.valueOf(itemMap.get("type")))) {//运营商三要素
                        Map<String, Object> resultJsonMap = JSON.parseObject(String.valueOf(itemMap.get("resultJson")));
                        Map<String, Object> dataMap = JSON.parseObject(String.valueOf(resultJsonMap.get("data")));
                        cell = CreateTableUtil.createCell("".equals(String.valueOf(dataMap.get("isp")))?"未知":String.valueOf(dataMap.get("isp")), CreateTableUtil.textfont);
                        cell.setColspan(2);
                        table.addCell(cell);

                        String result = null;
                        Font font = CreateTableUtil.textGreenFont;
                        if ("0000".equals(String.valueOf(dataMap.get("key")))) {
                            result = "一致";
                        } else if ("9998".equals(String.valueOf(dataMap.get("key")))) {
                            result = "不一致";
                            font = CreateTableUtil.textRedFont;
                        } else if ("3".equals(String.valueOf(dataMap.get("key")))) {
                            result = "无记录";
                            font = CreateTableUtil.textOrangeFont;
                        }
                        cell = CreateTableUtil.createCell("运营商三要素核验：", CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(result, font);
                        cell.setColspan(4);
                        table.addCell(cell);
                        flag = false;
                    }
                    num++;
                    if(flag && num >= itemListArray.size()){
                        cell = CreateTableUtil.createCell(null, CreateTableUtil.textfont);
                        cell.setColspan(2);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("运营商三要素核验：", CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(null, CreateTableUtil.textfont);
                        cell.setColspan(4);
                        table.addCell(cell);
                    }
                }
                cell = CreateTableUtil.createCell("手机在网时长：", CreateTableUtil.textfont);
                table.addCell(cell);
                num = 0;
                flag = true;
                for (Object itemOne : itemListArray) {
                    Map<String, Object> itemOneMap = JSON.parseObject(String.valueOf(itemOne));
                    if ("3".equals(String.valueOf(itemOneMap.get("type")))) {//客户手机在网时长
                        Map<String, Object> resultJsonOneMap = JSON.parseObject(String.valueOf(itemOneMap.get("resultJson")));
                        Map<String, Object> dataOneMap = JSON.parseObject(String.valueOf(resultJsonOneMap.get("data")));
                        cell = CreateTableUtil.createCell(String.valueOf(dataOneMap.get("OUTPUT1")), CreateTableUtil.textfont);
                        cell.setColspan(4);
                        table.addCell(cell);
                        flag = false;
                    }
                    num++;
                    if (flag && itemListArray.size() <= num) {
                        cell = CreateTableUtil.createCell(null, CreateTableUtil.textfont);
                        cell.setColspan(4);
                        table.addCell(cell);
                    }
                }
                document.add(table);

                table = CreateTableUtil.createTable(6);
                cell = CreateTableUtil.createCell("紧急联系人A", CreateTableUtil.textfont);
                cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                cell.setRowspan(3);
                table.addCell(cell);

                cell = CreateTableUtil.createCell("姓名：", CreateTableUtil.textfont);
                //cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                table.addCell(cell);
                cell = CreateTableUtil.createCell(String.valueOf(userCustomerMap.get("aRealname")), CreateTableUtil.textfont);
                table.addCell(cell);
                cell = CreateTableUtil.createCell("手机号：", CreateTableUtil.textfont);
                //cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                table.addCell(cell);
                cell = CreateTableUtil.createCell(String.valueOf(userCustomerMap.get("aPhone")), CreateTableUtil.textfont);
                cell.setColspan(2);
                table.addCell(cell);
                num = 0;
                flag = true;
                for (Object item : itemListArray) {
                    Map<String, Object> itemMap = JSON.parseObject(String.valueOf(item));
                    if ("operatorTwoElementsA".equals(String.valueOf(itemMap.get("type")))) {//紧急联系人A是否本人号码（运营商三要素）
                        Map<String, Object> resultJsonMap = JSON.parseObject(String.valueOf(itemMap.get("resultJson")));
                        Map<String, Object> dataMap = JSON.parseObject(String.valueOf(resultJsonMap.get("data")));
                        String result = null;
                        Font font = CreateTableUtil.textGreenFont;
                        if ("0000".equals(String.valueOf(dataMap.get("key")))) {
                            result = "一致";
                        } else if ("9998".equals(String.valueOf(dataMap.get("key")))) {
                            result = "不一致";
                            font = CreateTableUtil.textRedFont;
                        } else if ("3".equals(String.valueOf(dataMap.get("key")))) {
                            result = "无记录";
                            font = CreateTableUtil.textOrangeFont;
                        }
                        cell = CreateTableUtil.createCell("运营商三要素核验：", CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(result, font);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("运营商：", CreateTableUtil.textfont);
                        //cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("".equals(String.valueOf(dataMap.get("isp"))) ? "未知" : String.valueOf(dataMap.get("isp")), CreateTableUtil.textfont);
                        cell.setColspan(2);
                        table.addCell(cell);
                        flag = false;
                    }
                    num++;
                    if(flag && num>=itemListArray.size()){
                        cell = CreateTableUtil.createCell("运营商三要素核验：", CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(null, CreateTableUtil.textfont);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("运营商：", CreateTableUtil.textfont);
                        //cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell(null, CreateTableUtil.textfont);
                        cell.setColspan(2);
                        table.addCell(cell);
                    }
                }

                num = 0;
                flag = true;
                for (Object itemOne : itemListArray) {
                    Map<String, Object> itemOneMap = JSON.parseObject(String.valueOf(itemOne));
                    if ("inTheNetworkTimeA".equals(String.valueOf(itemOneMap.get("type")))) {//客户手机在网时长
                        cell = CreateTableUtil.createCell("手机在网时长：", CreateTableUtil.textfont);
                        table.addCell(cell);
                        Map<String, Object> resultJsonOneMap = JSON.parseObject(String.valueOf(itemOneMap.get("resultJson")));
                        Map<String, Object> dataOneMap = JSON.parseObject(String.valueOf(resultJsonOneMap.get("data")));
                        cell = CreateTableUtil.createCell(String.valueOf(dataOneMap.get("OUTPUT1")), CreateTableUtil.textfont);
                        cell.setColspan(4);
                        table.addCell(cell);
                        flag = false;
                    }
                    num++;
                    if (flag && itemListArray.size() <= num) {
                        cell = CreateTableUtil.createCell("手机在网时长：", CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(null, CreateTableUtil.textfont);
                        cell.setColspan(4);
                        table.addCell(cell);
                    }
                }
                document.add(table);

                table = CreateTableUtil.createTable(6);
                cell = CreateTableUtil.createCell("紧急联系人B", CreateTableUtil.textfont);
                cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                cell.setRowspan(3);
                table.addCell(cell);

                cell = CreateTableUtil.createCell("姓名：", CreateTableUtil.textfont);
                //cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                table.addCell(cell);
                cell = CreateTableUtil.createCell(String.valueOf(userCustomerMap.get("bRealname")), CreateTableUtil.textfont);
                table.addCell(cell);
                cell = CreateTableUtil.createCell("手机号：", CreateTableUtil.textfont);
                //cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                table.addCell(cell);
                cell = CreateTableUtil.createCell(String.valueOf(userCustomerMap.get("bPhone")), CreateTableUtil.textfont);
                cell.setColspan(2);
                table.addCell(cell);
                num = 0;
                flag = true;
                for (Object item : itemListArray) {
                    Map<String, Object> itemMap = JSON.parseObject(String.valueOf(item));
                    if ("operatorTwoElementsB".equals(String.valueOf(itemMap.get("type")))) {//紧急联系人A是否本人号码（运营商三要素）
                        Map<String, Object> resultJsonMap = JSON.parseObject(String.valueOf(itemMap.get("resultJson")));
                        Map<String, Object> dataMap = JSON.parseObject(String.valueOf(resultJsonMap.get("data")));
                        String result = null;
                        Font font = CreateTableUtil.textGreenFont;
                        if ("0000".equals(String.valueOf(dataMap.get("key")))) {
                            result = "一致";
                        } else if ("9998".equals(String.valueOf(dataMap.get("key")))) {
                            result = "不一致";
                            font = CreateTableUtil.textRedFont;
                        } else if ("3".equals(String.valueOf(dataMap.get("key")))) {
                            result = "无记录";
                            font = CreateTableUtil.textOrangeFont;
                        }
                        cell = CreateTableUtil.createCell("运营商三要素核验：", CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(result, font);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("运营商：", CreateTableUtil.textfont);
                        //cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("".equals(String.valueOf(dataMap.get("isp"))) ? "未知" : String.valueOf(dataMap.get("isp")), CreateTableUtil.textfont);
                        cell.setColspan(2);
                        table.addCell(cell);
                        flag = false;
                    }
                    num++;
                    if(flag && num>=itemListArray.size()){
                        cell = CreateTableUtil.createCell("运营商三要素核验：", CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(null, CreateTableUtil.textfont);
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell("运营商：", CreateTableUtil.textfont);
                        //cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);

                        cell = CreateTableUtil.createCell(null, CreateTableUtil.textfont);
                        cell.setColspan(2);
                        table.addCell(cell);
                    }
                }

                num = 0;
                flag = true;
                for (Object itemOne : itemListArray) {
                    Map<String, Object> itemOneMap = JSON.parseObject(String.valueOf(itemOne));
                    if ("inTheNetworkTimeB".equals(String.valueOf(itemOneMap.get("type")))) {//手机在网时长
                        cell = CreateTableUtil.createCell("手机在网时长：", CreateTableUtil.textfont);
                        table.addCell(cell);
                        Map<String, Object> resultJsonOneMap = JSON.parseObject(String.valueOf(itemOneMap.get("resultJson")));
                        Map<String, Object> dataOneMap = JSON.parseObject(String.valueOf(resultJsonOneMap.get("data")));
                        cell = CreateTableUtil.createCell(String.valueOf(dataOneMap.get("OUTPUT1")), CreateTableUtil.textfont);
                        cell.setColspan(4);
                        table.addCell(cell);
                        flag = false;
                    }
                    num++;
                    if (flag && itemListArray.size() <= num) {
                        cell = CreateTableUtil.createCell("手机在网时长：", CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(null, CreateTableUtil.textfont);
                        cell.setColspan(4);
                        table.addCell(cell);
                    }
                }
                document.add(table);
                //################################################企业工商数据############################################################
                table = CreateTableUtil.createTable(8);
                table.setSpacingBefore(5f); //和上一个表格间距5f
                cell = CreateTableUtil.createCell("企业工商数据", CreateTableUtil.headfontsecond); //表格中添加的文字
                cell.setColspan(8); //合并列
                cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                table.addCell(cell);

                cell = CreateTableUtil.createCell("基本信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                cell.setColspan(8); //合并列
                table.addCell(cell);

                num = 0;
                flag = true;
                for (Object item : itemListArray) {
                    Map<String, Object> itemMap = JSON.parseObject(String.valueOf(item));
                    if ("5".equals(String.valueOf(itemMap.get("type")))) {//企业工商数据查
                        // 获取数据
                        Map<String, Object> resultJsonMap = JSON.parseObject(String.valueOf(itemMap.get("resultJson")));
                        if("true".equals(String.valueOf(resultJsonMap.get("success")))){
                            Map<String, Object> dataMap = JSON.parseObject(String.valueOf(resultJsonMap.get("data")));
                            if("EXIST".equals(String.valueOf(dataMap.get("status")))){
                                Map<String, Object> basicMap = JSON.parseObject(String.valueOf(dataMap.get("basic")));//基本信息
                                JSONArray shareholderArray = JSONArray.parseArray(String.valueOf(dataMap.get("shareholder")));//股东信息
                                JSONArray shareholderPersonsArray = JSONArray.parseArray(String.valueOf(dataMap.get("shareholderPersons")));//高管信息
                                JSONArray legalPersonInvestsArray = JSONArray.parseArray(String.valueOf(dataMap.get("legalPersonInvests")));//法人对外投资信息
                                JSONArray legalPersonPostionsArray = JSONArray.parseArray(String.valueOf(dataMap.get("legalPersonPostions")));//法人对外任职信息
                                JSONArray enterpriseInvestsArray = JSONArray.parseArray(String.valueOf(dataMap.get("enterpriseInvests")));//企业对外投资信息
                                JSONArray alterInfosArray = JSONArray.parseArray(String.valueOf(dataMap.get("alterInfos")));//变更信息
                                JSONArray filiationsArray = JSONArray.parseArray(String.valueOf(dataMap.get("filiations")));//分支机构信息
                                JSONArray morguaInfosArray = JSONArray.parseArray(String.valueOf(dataMap.get("morguaInfos")));//动产抵押物信息
                                JSONArray punishBreaksArray = JSONArray.parseArray(String.valueOf(dataMap.get("punishBreaks")));//失信被执行人信息
                                JSONArray punishedArray = JSONArray.parseArray(String.valueOf(dataMap.get("punished")));//被执行人信息
                                JSONArray sharesFrostsArray = JSONArray.parseArray(String.valueOf(dataMap.get("sharesFrosts")));//股权冻结历史信息
                                JSONArray liquidationsArray = JSONArray.parseArray(String.valueOf(dataMap.get("liquidations")));//清算信息
                                JSONArray caseInfosArray = JSONArray.parseArray(String.valueOf(dataMap.get("caseInfos")));//行政处罚历史信息
                                JSONArray mortgageAlterArray = JSONArray.parseArray(String.valueOf(dataMap.get("mortgageAlter")));//动产抵押-变更信息
                                JSONArray mortgageCancelsArray = JSONArray.parseArray(String.valueOf(dataMap.get("mortgageCancels")));//动产抵押-注销信息
                                JSONArray mortgageDebtorsArray = JSONArray.parseArray(String.valueOf(dataMap.get("mortgageDebtors")));//动产抵押-被担保主债权信息
                                JSONArray mortgagePersonsArray = JSONArray.parseArray(String.valueOf(dataMap.get("mortgagePersons")));//动产抵押-抵押人信息
                                JSONArray breakLawArray = JSONArray.parseArray(String.valueOf(dataMap.get("breakLaw")));//严重违法信息
                                JSONArray exceptionListsArray = JSONArray.parseArray(String.valueOf(dataMap.get("exceptionLists")));//企业异常名录
                                JSONArray orgBasicsArray = JSONArray.parseArray(String.valueOf(dataMap.get("orgBasics")));//组织机构代码
                                JSONArray equityInfosArray = JSONArray.parseArray(String.valueOf(dataMap.get("equityInfos")));//股权出质信息
                                JSONArray equityChangeInfosArray = JSONArray.parseArray(String.valueOf(dataMap.get("equityChangeInfos")));//股权出质信息-变更信息
                                JSONArray cancellationOfInfosArray = JSONArray.parseArray(String.valueOf(dataMap.get("cancellationOfInfos")));//股权出质信息-注销信息
                                JSONArray tradeMarksArray = JSONArray.parseArray(String.valueOf(dataMap.get("tradeMarks")));//注册商标

                                cell = CreateTableUtil.createCell("企业名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("entName")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("注册号", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("regNo")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("原注册号", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("oRigegNo")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("法定代表人姓名", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("fName")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("注册资本(万元)", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("regCap")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("实收资本(万元)", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("recCap")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("注册资本币种", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("regCapCur")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("经营状态(在营、吊销、注销、其他)", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("entStatus")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("企业(机构)类型", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("entType")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("开业日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("esDate")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("经营期限自", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("opFrom")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("经营期限至", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("opTo")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("住址", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("dom")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("登记机关", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("regorg")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("许可经营项目", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("abuItem")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("一般经营项目", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("cbuItem")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("经营(业务)范围", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("opScope")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("经营(业务)范围及方式", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("opScoandForm")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("最后年检年度", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("anCheYear")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("变更日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("changDate")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("注销日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("canDate")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("吊销日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("revDate")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("最后年检日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("anCheDate")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(7);
                                table.addCell(cell);


                                cell = CreateTableUtil.createCell("行业门类代码", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("induStryphyCode")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("行业门类名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("induStryphyName")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);


                                cell = CreateTableUtil.createCell("国民经济行业代码", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("induStryCoCode")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("国民经济行业名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("induStryCoName")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("统一社会信用代码", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("creditNo")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("核准日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("apprdate")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("行业门类代码及名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("industryPhyAll")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("注册地址行政区编号", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("regOrgCode")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("企业英文名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("entNameEng")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("经营业务范围", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("zsOpScope")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("住所所在行政区划", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("domDistrict")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("联系电话", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("tel")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("国民经济行业代码及名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("industryCoAll")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("员工人数", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("empNum")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("所在省份", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("regOrgProvince")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("省节点编号", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("sextNodeNum")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("经营场所", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(String.valueOf(basicMap.get("opLoc")), CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(7);
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("股东信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("股东名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("股东详情", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                cell.setColspan(7);
                                table.addCell(cell);
                                if(shareholderArray.size()>0){
                                    for(Object shareholder:shareholderArray){
                                        Map<String,Object> shareholderMap = JSON.parseObject(String.valueOf(shareholder));
                                        cell = CreateTableUtil.createCell(String.valueOf(shareholderMap.get("shaName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        cell.setRowspan(4);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("出资金额(万元):", CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(1);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(shareholderMap.get("subConAm")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("认缴出资币种:", CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(1);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(shareholderMap.get("regCapCur")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("出资方式:", CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(1);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(shareholderMap.get("conForm")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("出资比例:", CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(1);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(shareholderMap.get("fundedRatio")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("出资日期：", CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(1);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(shareholderMap.get("conDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("国别:", CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(1);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(shareholderMap.get("country")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("股东总数量:", CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(1);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(shareholderMap.get("invaMount")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("统一信用代码:", CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(1);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(shareholderMap.get("creditNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("高管信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("姓名", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("职位", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("性别:", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("总人数", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                if(shareholderPersonsArray.size()>0){
                                    for(Object shareholderPersons:shareholderPersonsArray){
                                        Map<String,Object> shareholderPersonsMap = JSON.parseObject(String.valueOf(shareholderPersons));
                                        cell = CreateTableUtil.createCell(String.valueOf(shareholderPersonsMap.get("name")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(shareholderPersonsMap.get("position")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(shareholderPersonsMap.get("sex")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(shareholderPersonsMap.get("personAmount")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("法人对外投资信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("投资企业名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("企业详情", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                cell.setColspan(7);
                                table.addCell(cell);
                                if(legalPersonInvestsArray.size()>0){
                                    for(Object legalPersonInvests:legalPersonInvestsArray){
                                        Map<String,Object> legalPersonInvestsMap = JSON.parseObject(String.valueOf(legalPersonInvests));
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("entName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setRowspan(9);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("注册号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("regNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("企业(机构)类型", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("entType")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("注册资本(万元)", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("regCap")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注册资本币种", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("regCapCur")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("经营状态", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("entStatus")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("登记机关", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("regOrg")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("出资金额(万元)", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("subConAm")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("出资币种", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("curRenCy")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("出资方式", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("conForm")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("出资比例", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("fundedRatio")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("企业总数量", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("entAmount")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("法人代表", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("name")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("统一信用代码", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("creditNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(null, CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(4);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("开业时间", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("esDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注册地行政编号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("postCode")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("吊销日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("revDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注销日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonInvestsMap.get("canDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }


                                cell = CreateTableUtil.createCell("法人其他任职信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("任职企业名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("任职详情", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                cell.setColspan(7);
                                table.addCell(cell);
                                if(legalPersonPostionsArray.size()>0){
                                    for(Object legalPersonPostions:legalPersonPostionsArray){
                                        Map<String,Object> legalPersonPostionsMap = JSON.parseObject(String.valueOf(legalPersonPostions));
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonPostionsMap.get("entName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setRowspan(7);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("职务", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonPostionsMap.get("position")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("是否法人代表", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonPostionsMap.get("lerepSign")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("法人代表", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonPostionsMap.get("name")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("企业总数", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonPostionsMap.get("entAmount")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("企业(机构)类型", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonPostionsMap.get("entType")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注册号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonPostionsMap.get("regNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("注册资本(万元)", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonPostionsMap.get("regCap")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注册资本币种", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonPostionsMap.get("regCapCur")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("经营状态", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonPostionsMap.get("entStatus")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("登记机关", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonPostionsMap.get("regOrg")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("开业日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonPostionsMap.get("esDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注册地行政编号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonPostionsMap.get("postCode")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("吊销日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonPostionsMap.get("revDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注销日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(legalPersonPostionsMap.get("canDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("企业对外投资信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("投资企业名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("企业详情", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                cell.setColspan(7);
                                table.addCell(cell);
                                if(enterpriseInvestsArray.size()>0){
                                    for(Object enterpriseInvests:enterpriseInvestsArray){
                                        Map<String,Object> enterpriseInvestsMap = JSON.parseObject(String.valueOf(enterpriseInvests));
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("entName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setRowspan(9);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("注册号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("regNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("企业(机构)类型", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("entType")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("注册资本(万元)", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("regCap")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注册资本币种", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("regCapCur")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("经营状态", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("entStatus")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("登记机关", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("regOrg")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("出资金额(万元)", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("subConAm")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("出资币种", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("conGroCur")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("出资方式", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("conForm")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("出资比例", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("fundedRation")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("企业总数量", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("binvvAmount")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("法人代表", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("name")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("统一信用代码", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("creditCode")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(6);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("开业时间", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("openingDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注册地行政编号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("postCode")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("吊销日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("revDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注销日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(enterpriseInvestsMap.get("canDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("变更信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("项目", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("内容", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                cell.setColspan(7);
                                table.addCell(cell);
                                num = 1;
                                if(alterInfosArray.size()>0) {
                                    for (Object alterInfos : alterInfosArray) {
                                        cell = CreateTableUtil.createCell("NO"+num, CreateTableUtil.headfontthird); //表格中添加的文字
                                        cell.setHorizontalAlignment(1);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        cell.setRowspan(4);
                                        table.addCell(cell);

                                        Map<String,Object> alterInfosMap = JSON.parseObject(String.valueOf(alterInfos));
                                        cell = CreateTableUtil.createCell("变更日期", CreateTableUtil.textfont); //表格中添加的文字
                                        //cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(alterInfosMap.get("altDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(6);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("变更事项", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(alterInfosMap.get("altItem")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(6);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("变更前内容", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(alterInfosMap.get("altBe")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(6);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("变更后内容", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(alterInfosMap.get("altAf")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(6);
                                        table.addCell(cell);

                                        num++;
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }


                                cell = CreateTableUtil.createCell("分支机构信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("分支机构名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("分支机构详情", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                cell.setColspan(7);
                                table.addCell(cell);
                                if(filiationsArray.size()>0){
                                    for(Object filiations:filiationsArray){
                                        Map<String,Object> filiationsMap = JSON.parseObject(String.valueOf(filiations));
                                        cell = CreateTableUtil.createCell(String.valueOf(filiationsMap.get("brName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setRowspan(4);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("注册号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(filiationsMap.get("brRegNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("机构负责人", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(filiationsMap.get("brPrincipal")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("一般经营项目", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(filiationsMap.get("cbuItme")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("机构地址", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(filiationsMap.get("brAddr")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("统一信用代码", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(filiationsMap.get("brnCreditCode")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("登记机关", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(filiationsMap.get("brnRegOrg")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("主体身份代码", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(filiationsMap.get("brpripId")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(6);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("动产抵押物信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("抵押物名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("综合信息(数量、质量、状况、所在地等情况)", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                cell.setColspan(3);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("备注", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("登记编号", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("所有权或使用权归属", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);

                                if(morguaInfosArray.size()>0) {
                                    for (Object morguaInfos : morguaInfosArray) {
                                        Map<String, Object> filiationsMap = JSON.parseObject(String.valueOf(morguaInfos));
                                        cell = CreateTableUtil.createCell(String.valueOf(filiationsMap.get("guaName")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(filiationsMap.get("comprehensiveDetail")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(filiationsMap.get("remark")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(filiationsMap.get("regNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(filiationsMap.get("owner")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("失信被执行人信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("被执行人姓名/名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("执行详情", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                cell.setColspan(7);
                                table.addCell(cell);
                                if(punishBreaksArray.size()>0){
                                    for(Object punishBreaks:punishBreaksArray){
                                        Map<String,Object> punishBreaksMap = JSON.parseObject(String.valueOf(punishBreaks));
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("name")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setRowspan(11);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("年龄", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("age")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("性别", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("sex")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("身份证号码", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("cardNum")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("身份证原始发证地", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("ysfzd")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("案号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("caseCode")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("案件状态", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("caseState")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("失信人类型", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("type")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("法人/负责人", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("businessEntity")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("立案时间", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("regDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("公布时间", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("publishDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("执行法院", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("courName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("省份", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("areaName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("执行依据文号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("gistId")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("执行依据单位", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("gistUnit")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("失信被执行人行为具体情形", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("disruptTypeName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("被执行人的履行情况", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("performAnce")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("已履行", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("performedPart")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("未履行", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("unPerformPart")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("退出日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("exitDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("关注次数", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("focusNumber")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("法律文书确定的义务", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("duty")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(6);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("被执行人信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("被执行人姓名/名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("执行详情", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                cell.setColspan(7);
                                table.addCell(cell);
                                if(punishedArray.size()>0){
                                    for(Object punished:punishedArray){
                                        Map<String,Object> punishedMap = JSON.parseObject(String.valueOf(punished));
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("name")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setRowspan(6);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("年龄", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("age")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("性别", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("sex")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("身份证号码", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("cardNum")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("身份证原始发证地", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("ysfzd")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("案号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("caseCode")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("案件状态", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("caseState")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("失信人类型", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("type")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("执行法院", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("courtName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("立案时间", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("regDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("执行标的（元）", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("execMoney")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("关注次数", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("focusNum")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("省份", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("areaName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }
                                document.add(table);

                                table = CreateTableUtil.createTable(9);
                                cell = CreateTableUtil.createCell("股权冻结历史信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(9); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("冻结文号", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("冻结机关", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("冻结起始日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("冻结截至日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("冻结金额(万元)", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("解冻机关", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("解冻文号", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("解冻日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("解冻说明", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                if(sharesFrostsArray.size()>0) {
                                    for (Object sharesFrosts : sharesFrostsArray) {
                                        Map<String, Object> sharesFrostsMap = JSON.parseObject(String.valueOf(sharesFrosts));
                                        cell = CreateTableUtil.createCell(String.valueOf(sharesFrostsMap.get("froDocNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(sharesFrostsMap.get("froAuth")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(sharesFrostsMap.get("froFrom")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(sharesFrostsMap.get("froTo")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(sharesFrostsMap.get("froAm")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(sharesFrostsMap.get("thawAuth")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(sharesFrostsMap.get("thawDocNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(sharesFrostsMap.get("thawDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(sharesFrostsMap.get("thawComment")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(9); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("清算信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(9); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("清算责任人", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("清算负责人", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("清算组成员", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("清算完结情况", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("清算完结日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("债务承接人", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("债权承接人", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("电话", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("地址", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                if(liquidationsArray.size()>0) {
                                    for (Object liquidations : liquidationsArray) {
                                        Map<String, Object> liquidationsMap = JSON.parseObject(String.valueOf(liquidations));
                                        cell = CreateTableUtil.createCell(String.valueOf(liquidationsMap.get("ligEntity")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(liquidationsMap.get("ligPrincipal")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(liquidationsMap.get("liQMen")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(liquidationsMap.get("liGSt")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(liquidationsMap.get("ligEndDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(liquidationsMap.get("debtTranee")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(liquidationsMap.get("claimTranee")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(liquidationsMap.get("mobile")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(liquidationsMap.get("address")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(9); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("行政处罚历史信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(9); //合并列
                                table.addCell(cell);
                                num = 0;
                                if(caseInfosArray.size()>0) {
                                    for (Object caseInfos : caseInfosArray) {
                                        Map<String, Object> caseInfosMap = JSON.parseObject(String.valueOf(caseInfos));
                                        cell = CreateTableUtil.createCell("NO"+num, CreateTableUtil.headfontthird); //表格中添加的文字
                                        cell.setRowspan(5);
                                        cell.setHorizontalAlignment(1);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("处罚决定文书", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("penDecNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("签发日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("penDecIssDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("处罚机关", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("penAuth")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("处罚机关名", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("penAuthCn")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("主要违法事实", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("illegFact")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("处罚种类", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("penType")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("处罚种类中文", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("penTypeDescription")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("处罚内容", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("penContent")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("公示日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("announcementDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(7);
                                        table.addCell(cell);
                                        num++;
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(9); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("动产抵押-变更信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(9); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("变更日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("变更内容", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(5);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("登记编号", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                if(mortgageAlterArray.size()>0) {
                                    for (Object mortgageAlter : mortgageAlterArray) {
                                        Map<String, Object> mortgageAlterMap = JSON.parseObject(String.valueOf(mortgageAlter));
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgageAlterMap.get("alterDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgageAlterMap.get("alterDetail")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(5);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgageAlterMap.get("RegNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(9); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("动产抵押-注销信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(9); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("注销日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("注销原因", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(5);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("登记编号", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                if(mortgageCancelsArray.size()>0) {
                                    for (Object mortgageCancels : mortgageCancelsArray) {
                                        Map<String, Object> mortgageCancelsMap = JSON.parseObject(String.valueOf(mortgageCancels));
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgageCancelsMap.get("cancelDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgageCancelsMap.get("cancelReason")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(5);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgageCancelsMap.get("regNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(9); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("动产抵押-被担保主债权信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(9); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("履行债务开始日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("履行债务结束日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("数额", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("担保范围", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("备注", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                cell.setColspan(2);
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("种类", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("编号", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                if(mortgageDebtorsArray.size()>0) {
                                    for (Object mortgageDebtors : mortgageDebtorsArray) {
                                        Map<String, Object> mortgageDebtorsMap = JSON.parseObject(String.valueOf(mortgageDebtors));
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgageDebtorsMap.get("startDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgageDebtorsMap.get("endtDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgageDebtorsMap.get("amount")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgageDebtorsMap.get("range")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgageDebtorsMap.get("remark")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgageDebtorsMap.get("type")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgageDebtorsMap.get("regNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(9); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("动产抵押-抵押权人信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(9); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("抵押权人证照/证件号码", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("抵押权人证照/证件类型", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("所在地", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("抵押权人名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("登记编号", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                if(mortgagePersonsArray.size()>0) {
                                    for (Object mortgagePersons : mortgagePersonsArray) {
                                        Map<String, Object> mortgagePersonsMap = JSON.parseObject(String.valueOf(mortgagePersons));
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgagePersonsMap.get("credentialNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgagePersonsMap.get("credentialType")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgagePersonsMap.get("domain")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgagePersonsMap.get("name")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(mortgagePersonsMap.get("regNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(9); //合并列
                                    table.addCell(cell);
                                }
                                document.add(table);

                                table = CreateTableUtil.createTable(8);
                                cell = CreateTableUtil.createCell("严重违法信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("列入日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("列入原因", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("列入作出决定机关", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("列入作出决定文号", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("移出日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("移出原因", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("移出作出决定机关", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("移出作出决定文号", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                if(breakLawArray.size()>0) {
                                    for (Object breakLaw : breakLawArray) {
                                        Map<String, Object> breakLawMap = JSON.parseObject(String.valueOf(breakLaw));
                                        cell = CreateTableUtil.createCell(String.valueOf(breakLawMap.get("inDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(breakLawMap.get("inReason")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(breakLawMap.get("inRegOrg")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(breakLawMap.get("inSn")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(breakLawMap.get("outDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(breakLawMap.get("outReason")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(breakLawMap.get("outRegOrg")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(breakLawMap.get("outSn")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("企业异常名录", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("企业名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("详情", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                cell.setColspan(7);
                                table.addCell(cell);
                                if(exceptionListsArray.size()>0){
                                    for(Object exceptionLists:exceptionListsArray){
                                        Map<String,Object> exceptionListsMap = JSON.parseObject(String.valueOf(exceptionLists));
                                        cell = CreateTableUtil.createCell(String.valueOf(exceptionListsMap.get("name")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setRowspan(5);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("注册号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(exceptionListsMap.get("regNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("企业类型", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(exceptionListsMap.get("type")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("列入日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(exceptionListsMap.get("inDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("列入原因", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(exceptionListsMap.get("inReason")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("移出日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(exceptionListsMap.get("outDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("移出原因", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(exceptionListsMap.get("outReason")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("列入机关名称", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(exceptionListsMap.get("inOrg")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("移出机关名称", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(exceptionListsMap.get("outOrg")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("统一信用代码", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(exceptionListsMap.get("shxydm")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(6);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("机构组织代码", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("组织机构代码", CreateTableUtil.textfont); //
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("机构地址", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("组织机构名称", CreateTableUtil.textfont); //
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("质疑标志", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                if(orgBasicsArray.size()>0){
                                    for(Object orgBasics:orgBasicsArray){
                                        Map<String,Object> orgBasicsMap = JSON.parseObject(String.valueOf(orgBasics));
                                        cell = CreateTableUtil.createCell(String.valueOf(orgBasicsMap.get("jgdm")), CreateTableUtil.textfont); //
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(orgBasicsMap.get("jgdz")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(orgBasicsMap.get("jgmc")), CreateTableUtil.textfont); //
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(orgBasicsMap.get("zybz")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("股权出质信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("组织机构名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("详情", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                cell.setColspan(7);
                                table.addCell(cell);
                                if(equityInfosArray.size()>0){
                                    for(Object equityInfos:equityInfosArray){
                                        Map<String,Object> equityInfosMap = JSON.parseObject(String.valueOf(equityInfos));
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("jgmc")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setRowspan(7);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("办证机构", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("bzjg")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("代码证办证日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("bzrq")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("法人代表姓名", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("fddbr")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("组织机构代码", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("jgdm")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("机构地址", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("jgdz")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("机构类型", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("jglx")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("行政区划", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("xzqh")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注册日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("zcrq")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("代码证作废日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("zfrq")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("质疑标志", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("zybz")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("出质股权数额", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("stkPawnCzamt")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("出质人证件/证件号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("stkPawnCzcerno")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("出质人", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("stkPawnCzper")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("公示日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("stkPawnDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("质权出质设立登记日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("stkPawnRegdate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("登记编号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("stkPawnRegno")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("状态", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("stkPawnStatus")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("质权人证件/证件号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("stkPawnZqcerno")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("质权人姓名", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("stkPawnZqper")), CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("关联内容", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityInfosMap.get("content")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(4);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("股权出质信息-变更信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("变更内容", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("关联内容", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("变更日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                if(equityChangeInfosArray.size()>0) {
                                    for (Object equityChangeInfos : equityChangeInfosArray) {
                                        Map<String, Object> equityChangeInfosMap = JSON.parseObject(String.valueOf(equityChangeInfos));
                                        cell = CreateTableUtil.createCell(String.valueOf(equityChangeInfosMap.get("stkPawnBgnr")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityChangeInfosMap.get("content")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(equityChangeInfosMap.get("stkPawnBgrq")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("股权出质信息-注销信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("注销原因", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("关联内容", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(3);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("注销日期", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(2);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                if(cancellationOfInfosArray.size()>0) {
                                    for (Object cancellationOfInfos : cancellationOfInfosArray) {
                                        Map<String, Object> cancellationOfInfosMap = JSON.parseObject(String.valueOf(cancellationOfInfos));
                                        cell = CreateTableUtil.createCell(String.valueOf(cancellationOfInfosMap.get("stkPawnRes")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(cancellationOfInfosMap.get("content")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(cancellationOfInfosMap.get("stkPawnDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("注册商标", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("商标名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("详情", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                cell.setColspan(7);
                                table.addCell(cell);
                                if(tradeMarksArray.size()>0){
                                    for(Object tradeMarks:tradeMarksArray){
                                        Map<String,Object> tradeMarksMap = JSON.parseObject(String.valueOf(tradeMarks));
                                        cell = CreateTableUtil.createCell(String.valueOf(tradeMarksMap.get("markName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setRowspan(6);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("商标图片", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(null, CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(6);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("申请日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(tradeMarksMap.get("appDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("初审公告期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(tradeMarksMap.get("checkDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("专用期(起始日期)", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(tradeMarksMap.get("beginDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("专用期(到期日期)", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(tradeMarksMap.get("endDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("注册码解密", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(tradeMarksMap.get("markCodeKey")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注册公告期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(tradeMarksMap.get("regDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("商标类型", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(tradeMarksMap.get("markTypeNew")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("流程", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(tradeMarksMap.get("markTypeNew")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("商标/服务", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(tradeMarksMap.get("typeDetailDes")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(6);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }
                                flag = false;
                            }
                        }
                    }
                    num ++;
                    if(flag && num >= itemListArray.size()){
                        cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setColspan(8); //合并列
                        table.addCell(cell);
                    }
                }
                document.add(table);

                //################################################个人名下企业############################################################
                table = CreateTableUtil.createTable(8);
                table.setSpacingBefore(5f); //和上一个表格间距5f
                cell = CreateTableUtil.createCell("个人名下企业", CreateTableUtil.headfontsecond); //表格中添加的文字
                cell.setColspan(8); //合并列
                cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                table.addCell(cell);

                cell = CreateTableUtil.createCell("失信被执行人信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                cell.setColspan(8); //合并列
                table.addCell(cell);
                num = 0;
                flag = true;
                for (Object item : itemListArray) {
                    Map<String, Object> itemMap = JSON.parseObject(String.valueOf(item));
                    if ("6".equals(String.valueOf(itemMap.get("type")))) {//个人名下企业
                        Map<String, Object> resultJsonMap = JSON.parseObject(String.valueOf(itemMap.get("resultJson")));
                        if("true".equals(String.valueOf(resultJsonMap.get("success")))) {
                            Map<String, Object> dataMap = JSON.parseObject(String.valueOf(resultJsonMap.get("data")));
                            if ("EXIST".equals(String.valueOf(dataMap.get("status")))) {
                                JSONArray punishBreaksArray = JSONArray.parseArray(String.valueOf(dataMap.get("punishBreaks")));//失信被执行人信息
                                JSONArray punishedArray = JSONArray.parseArray(String.valueOf(dataMap.get("punished")));//被执行人信息
                                JSONArray caseInfosArray = JSONArray.parseArray(String.valueOf(dataMap.get("caseInfos")));//行政处罚历史信息
                                JSONArray corporatesArray = JSONArray.parseArray(String.valueOf(dataMap.get("corporates")));//企业法人信息
                                JSONArray corporateManagersArray = JSONArray.parseArray(String.valueOf(dataMap.get("corporateManagers")));//企业主要管理人员信息
                                JSONArray corporateShareholdersArray = JSONArray.parseArray(String.valueOf(dataMap.get("corporateShareholders")));//企业股东信息

                                cell = CreateTableUtil.createCell("被执行人姓名/名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("执行详情", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                cell.setColspan(7);
                                table.addCell(cell);
                                if(punishBreaksArray.size()>0){
                                    for(Object punishBreaks:punishBreaksArray){
                                        Map<String,Object> punishBreaksMap = JSON.parseObject(String.valueOf(punishBreaks));
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("name")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setRowspan(9);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("年龄", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("age")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("性别", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("sex")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("身份证号码", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("cardNum")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("身份证原始发证地", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("ysfzd")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("案号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("caseCode")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(6);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("失信人类型", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("type")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("法人/负责人", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("businessEntity")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("立案时间", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("regDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("公布时间", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("publishDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("执行法院", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("courName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("省份", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("areaName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("执行依据文号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("gistId")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("执行依据单位", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("gistUnit")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("失信被执行人行为具体情形", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("disruptTypeName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("被执行人的履行情况", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("performAnce")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("已履行", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("performedPart")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("未履行", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishBreaksMap.get("unPerformPart")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("被执行人信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(8); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("被执行人姓名/名称", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("执行详情", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                cell.setColspan(7);
                                table.addCell(cell);
                                if(punishedArray.size()>0){
                                    for(Object punished:punishedArray){
                                        Map<String,Object> punishedMap = JSON.parseObject(String.valueOf(punished));
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("name")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setRowspan(6);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("年龄", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("age")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("性别", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("sex")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("身份证号码", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("cardNum")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("身份证原始发证地", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("ysfzd")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("案号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("caseCode")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("案件状态", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("caseState")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("立案时间", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("regDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("执行标的(元)", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("execMoney")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("执行法院", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("courtName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(2);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("省份", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(punishedMap.get("areaName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(8); //合并列
                                    table.addCell(cell);
                                }
                                document.add(table);

                                table = CreateTableUtil.createTable(9);
                                cell = CreateTableUtil.createCell("行政处罚历史信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(9); //合并列
                                table.addCell(cell);
                                num = 0;
                                if(caseInfosArray.size()>0) {
                                    for (Object caseInfos : caseInfosArray) {
                                        Map<String, Object> caseInfosMap = JSON.parseObject(String.valueOf(caseInfos));
                                        cell = CreateTableUtil.createCell("NO"+num, CreateTableUtil.headfontthird); //表格中添加的文字
                                        cell.setRowspan(8);
                                        cell.setHorizontalAlignment(1);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("案由", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("caseReason")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("案发时间", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("caseTime")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("案值", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("caseVal")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("案件类型", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("caseType")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("执行类别", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("exeSort")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("案件结果", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("caseResult")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("处罚决定文书", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("penDecNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("签发日期", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("penDecIssDate")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("处罚机关", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("penAuth")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("主要违法事实", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("illegFact")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("处罚依据", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("penBasis")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("处罚种类", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("penType")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("处罚结果", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("penResult")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("处罚金额(万元)", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("penAm")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("处罚执行情况", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(caseInfosMap.get("penExeSt")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(7);
                                        table.addCell(cell);
                                        num++;
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(9); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("企业法人信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(9); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("查询人姓名", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("详情", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(8);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                if(corporatesArray.size()>0) {
                                    for (Object corporates : corporatesArray) {
                                        Map<String, Object> corporatesMap = JSON.parseObject(String.valueOf(corporates));
                                        cell = CreateTableUtil.createCell(String.valueOf(corporatesMap.get("ryName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setRowspan(4);
                                        cell.setHorizontalAlignment(1);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("企业(机构)名称", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporatesMap.get("entName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注册号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporatesMap.get("regNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("企业(机构)类型", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporatesMap.get("entType")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注册资本(万元)", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporatesMap.get("regCap")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("注册资本币种", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporatesMap.get("regCapCur")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("企业状态", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporatesMap.get("entStatus")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("统一信用代码", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporatesMap.get("creditNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(7);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(9); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("企业主要管理人员信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(9); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("查询人姓名", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("详情", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(8);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                if(corporateManagersArray.size()>0) {
                                    for (Object corporateManagers : corporateManagersArray) {
                                        Map<String, Object> corporateManagersMap = JSON.parseObject(String.valueOf(corporateManagers));
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateManagersMap.get("ryName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setRowspan(4);
                                        cell.setHorizontalAlignment(1);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("职务", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateManagersMap.get("position")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("企业(机构)名称", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateManagersMap.get("entName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("注册号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateManagersMap.get("regNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("统一信用代码", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateManagersMap.get("creditNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("企业(机构)类型", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateManagersMap.get("entType")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注册资本(万元)", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateManagersMap.get("regCap")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("注册资本币种", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateManagersMap.get("regCapCur")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("企业状态", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateManagersMap.get("entStatus")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(9); //合并列
                                    table.addCell(cell);
                                }

                                cell = CreateTableUtil.createCell("企业股东信息", CreateTableUtil.keyLightBlue); //表格中添加的文字
                                cell.setColspan(9); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("查询人姓名", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("详情", CreateTableUtil.textfont); //表格中添加的文字
                                cell.setColspan(8);
                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                table.addCell(cell);
                                if(corporateShareholdersArray.size()>0) {
                                    for (Object corporateShareholders : corporateShareholdersArray) {
                                        Map<String, Object> corporateShareholdersMap = JSON.parseObject(String.valueOf(corporateShareholders));
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateShareholdersMap.get("ryName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setRowspan(5);
                                        cell.setHorizontalAlignment(1);
                                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("企业(机构)名称", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateShareholdersMap.get("entName")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("企业(机构)类型", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateShareholdersMap.get("entType")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("注册号", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateShareholdersMap.get("regNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("统一信用代码", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateShareholdersMap.get("creditNo")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);


                                        cell = CreateTableUtil.createCell("注册资本(万元)", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateShareholdersMap.get("regCap")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("注册资本币种", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateShareholdersMap.get("regCapCur")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("认缴出资额(万元)", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateShareholdersMap.get("subConam")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("认缴出资币种", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateShareholdersMap.get("currency")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);

                                        cell = CreateTableUtil.createCell("出资比例", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateShareholdersMap.get("fundedRatio")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell("企业状态", CreateTableUtil.textfont); //表格中添加的文字
                                        table.addCell(cell);
                                        cell = CreateTableUtil.createCell(String.valueOf(corporateShareholdersMap.get("entStatus")), CreateTableUtil.textfont); //表格中添加的文字
                                        cell.setColspan(3);
                                        table.addCell(cell);
                                    }
                                }else {
                                    cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                                    cell.setColspan(9); //合并列
                                    table.addCell(cell);
                                }
                                flag = false;
                            }
                        }
                    }
                    num ++;
                    if(flag && num >= itemListArray.size()){
                        cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setColspan(8); //合并列
                        table.addCell(cell);
                    }
                }
                document.add(table);

                //################################################个人涉诉############################################################
                table = CreateTableUtil.createTable(8);
                table.setSpacingBefore(5f); //和上一个表格间距5f
                cell = CreateTableUtil.createCell("个人涉诉", CreateTableUtil.headfontsecond); //表格中添加的文字
                cell.setColspan(8); //合并列
                cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                table.addCell(cell);

                num = 0;
                flag = true;
                for (Object item : itemListArray) {
                    Map<String, Object> itemMap = JSON.parseObject(String.valueOf(item));
                    if ("9".equals(String.valueOf(itemMap.get("type")))) {//个人涉诉
                        String[] redsultJsonArr = String.valueOf(itemMap.get("resultJson")).split("fengexian");
                        Boolean f = true;
                        if(redsultJsonArr.length>0){
                            for(int i=0;i<redsultJsonArr.length;i++){
                                Map<String, Object> resultJsonMap = JSON.parseObject(redsultJsonArr[0]);
                                if ("true".equals(String.valueOf(resultJsonMap.get("success")))) {
                                    Map<String, Object> dataMap = JSON.parseObject(String.valueOf(resultJsonMap.get("data")));
                                    JSONArray pageDataArray = JSONArray.parseArray(String.valueOf(dataMap.get("pageData")));
                                    if (pageDataArray.size()>0) {
                                        Boolean ZXGG = true;
                                        Boolean CPWS = true;
                                        Boolean SXGG = true;
                                        Boolean BGT = true;
                                        Boolean KTGG = true;
                                        Boolean FYGG = true;
                                        Boolean WDHMD = true;
                                        Boolean AJLC = true;
                                        for(Object pageData : pageDataArray){
                                            if(f){//姓名和身份证信息只展示一次
                                                cell = CreateTableUtil.createCell("姓名", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(dataMap.get("name")), CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("身份证号", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(dataMap.get("identityCard")), CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);
                                                f = false;
                                            }

                                            Map<String, Object> pageDataMap = JSON.parseObject(String.valueOf(pageData));//涉诉详情
                                            if("CPWS".equals(String.valueOf(pageDataMap.get("dataType")))){//裁判文书
                                                if(CPWS){
                                                    cell = CreateTableUtil.createCell("裁判文书",  CreateTableUtil.keyLightBlue); //表格中添加的文字
                                                    cell.setColspan(8);
                                                    table.addCell(cell);
                                                    CPWS = false;
                                                }

                                                cell = CreateTableUtil.createCell("标题",  CreateTableUtil.keyLightBlue); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("title")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(7);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("裁判文书ID", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("caseType")), CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("类别", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("dataType")), CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("审结时间(时间戳)", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("recordTime")), CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("审结时间(年月日格式)", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("time")), CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("案件类别", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("caseType")), CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("案由", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("caseType")), CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("执行法院名称", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("court")), CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("匹配度", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("matchRatio")), CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("内容", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("content")), CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(7);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("案号", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("caseNO")), CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(7);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("判决结果", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("judgeResult")), CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(7);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("当事人列表", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(8);
                                                cell.setHorizontalAlignment(1);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("当事人名称", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("当事人详情", CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                cell.setColspan(7);
                                                table.addCell(cell);

                                                JSONArray litigantsArray = JSONArray.parseArray(String.valueOf(pageDataMap.get("litigants")));
                                                for(Object litigants:litigantsArray){
                                                    Map<String,Object> litigantsMap = JSON.parseObject(String.valueOf(litigants));

                                                    cell = CreateTableUtil.createCell(String.valueOf(litigantsMap.get("name")), CreateTableUtil.textfont); //表格中添加的文字
                                                    cell.setRowspan(4);
                                                    cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                    table.addCell(cell);

                                                    cell = CreateTableUtil.createCell("当事人生日", CreateTableUtil.textfont); //表格中添加的文字
                                                    table.addCell(cell);
                                                    cell = CreateTableUtil.createCell(String.valueOf(litigantsMap.get("birthday")), CreateTableUtil.textfont); //表格中添加的文字
                                                    cell.setColspan(2);
                                                    table.addCell(cell);
                                                    cell = CreateTableUtil.createCell("当事人称号", CreateTableUtil.textfont); //表格中添加的文字
                                                    table.addCell(cell);
                                                    cell = CreateTableUtil.createCell(String.valueOf(litigantsMap.get("title")), CreateTableUtil.textfont); //表格中添加的文字
                                                    cell.setColspan(3);
                                                    table.addCell(cell);

                                                    cell = CreateTableUtil.createCell("律师事务所", CreateTableUtil.textfont); //表格中添加的文字
                                                    table.addCell(cell);
                                                    cell = CreateTableUtil.createCell(String.valueOf(litigantsMap.get("lawFirm")), CreateTableUtil.textfont); //表格中添加的文字
                                                    cell.setColspan(2);
                                                    table.addCell(cell);
                                                    cell = CreateTableUtil.createCell("地址", CreateTableUtil.textfont); //表格中添加的文字
                                                    table.addCell(cell);
                                                    cell = CreateTableUtil.createCell(String.valueOf(litigantsMap.get("address")), CreateTableUtil.textfont); //表格中添加的文字
                                                    cell.setColspan(3);
                                                    table.addCell(cell);

                                                    cell = CreateTableUtil.createCell("当事人立场", CreateTableUtil.textfont); //表格中添加的文字
                                                    table.addCell(cell);
                                                    cell = CreateTableUtil.createCell(String.valueOf(litigantsMap.get("standpoint")), CreateTableUtil.textfont); //表格中添加的文字
                                                    cell.setColspan(2);
                                                    table.addCell(cell);
                                                    cell = CreateTableUtil.createCell("当事人身份证", CreateTableUtil.textfont); //表格中添加的文字
                                                    table.addCell(cell);
                                                    cell = CreateTableUtil.createCell(String.valueOf(litigantsMap.get("identificationNO")), CreateTableUtil.textfont); //表格中添加的文字
                                                    cell.setColspan(3);
                                                    table.addCell(cell);

                                                    cell = CreateTableUtil.createCell("当事人类型", CreateTableUtil.textfont); //表格中添加的文字
                                                    table.addCell(cell);
                                                    cell = CreateTableUtil.createCell(String.valueOf(litigantsMap.get("type")), CreateTableUtil.textfont); //表格中添加的文字
                                                    cell.setColspan(2);
                                                    table.addCell(cell);
                                                    cell = CreateTableUtil.createCell("委托辩护人", CreateTableUtil.textfont); //表格中添加的文字
                                                    table.addCell(cell);
                                                    cell = CreateTableUtil.createCell(String.valueOf(litigantsMap.get("lawyer")), CreateTableUtil.textfont); //表格中添加的文字
                                                    cell.setColspan(3);
                                                    table.addCell(cell);
                                                }
                                            }else if("ZXGG".equals(String.valueOf(pageDataMap.get("dataType")))){//执行公告
                                                if(ZXGG){
                                                    cell = CreateTableUtil.createCell("执行公告",  CreateTableUtil.keyLightBlue); //表格中添加的文字
                                                    cell.setColspan(8);
                                                    table.addCell(cell);
                                                    ZXGG = false;
                                                }
                                                cell = CreateTableUtil.createCell("标题",  CreateTableUtil.keyLightBlue); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("title")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(7);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("执行公告ID",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("id")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("案件状态",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("caseStatus")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("被执行人姓名",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("name")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("身份证",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("identificationNO")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("执行法院名称",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("court")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("案号",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("caseNO")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("类别",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("dataType")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("匹配度",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("matchRatio")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("立案时间(时间戳)",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("recordTime")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("立案时间(年月日)",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("time")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("执行标的",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("executionTarget")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(7);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("内容",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("content")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(7);
                                                table.addCell(cell);

                                            }else if("SXGG".equals(String.valueOf(pageDataMap.get("dataType")))){//
                                                if(SXGG){
                                                    cell = CreateTableUtil.createCell("失信公告",  CreateTableUtil.keyLightBlue); //表格中添加的文字
                                                    cell.setColspan(8);
                                                    table.addCell(cell);
                                                    SXGG = false;
                                                }

                                                cell = CreateTableUtil.createCell("法院公告ID",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("公告详情",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(7);
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("id")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setRowspan(13);
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("履行情况",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("implementationStatus")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("依据案号",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("evidenceCode")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("执行法院名称",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("court")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("做出执行依据单位",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("executableUnit")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("失信被执行人行为具体情形",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("specificCircumstances")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("生效法律文书确定的义务",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("obligations")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("省份",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("province")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("内容",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("content")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("类别",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("dataType")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("匹配度",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("matchRatio")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("被执行人姓名",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("name")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("身份证",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("identificationNO")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("年龄",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("age")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("性别",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("gender")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("案号",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("caseNO")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("发布时间",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("postTime")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("立案时间(时间戳)",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("recordTime")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("立案时间(年月日)",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("time")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                            }else if("BGT".equals(String.valueOf(pageDataMap.get("dataType")))){//曝光台
                                                if(BGT){
                                                    cell = CreateTableUtil.createCell("曝光台",  CreateTableUtil.keyLightBlue); //表格中添加的文字
                                                    cell.setColspan(8);
                                                    table.addCell(cell);
                                                    BGT = false;
                                                }

                                                cell = CreateTableUtil.createCell("曝光台ID",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("曝光台详情",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(7);
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("id")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setRowspan(7);
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("内容",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("content")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("当事人",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("name")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("案号",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("caseNo")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("类别",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("dataType")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("法院名称",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("court")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("案由",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("caseCause")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("标的金额",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("executionTarget")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("匹配度",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("matchRatio")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("发布时间(时间戳)",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("recordTime")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("发布时间(年月日)",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("time")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);
                                            }else if("KTGG".equals(String.valueOf(pageDataMap.get("dataType")))){//开庭公告
                                                if(KTGG){
                                                    cell = CreateTableUtil.createCell("开庭公告",  CreateTableUtil.keyLightBlue); //表格中添加的文字
                                                    cell.setColspan(8);
                                                    table.addCell(cell);
                                                    KTGG = false;
                                                }

                                                cell = CreateTableUtil.createCell("开庭公告ID",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("开庭公告详情",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(7);
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("id")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setRowspan(6);
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("内容",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("content")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("法院名称",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("court")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("案由",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("caseCause")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("案号",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("caseNO")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("类别",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("dataType")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("被告",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("defendant")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("法官",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("judge")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("当事人",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("party")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("原告",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("plaintiff")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("法庭",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("courtroom")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("匹配度",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("matchRatio")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("开庭时间(时间戳)",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("recordTime")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("开庭时间(年月日)",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("time")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                            }else if("FYGG".equals(String.valueOf(pageDataMap.get("dataType")))){//法院公告
                                                if(FYGG){
                                                    cell = CreateTableUtil.createCell("法院公告",  CreateTableUtil.keyLightBlue); //表格中添加的文字
                                                    cell.setColspan(8);
                                                    table.addCell(cell);
                                                    FYGG = false;
                                                }

                                                cell = CreateTableUtil.createCell("法院公告ID",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("法院公告详情",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(7);
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("id")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setRowspan(5);
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("内容",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("content")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("当事人",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("name")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("公告类型",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("announcementType")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("匹配度",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("matchRatio")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("法院名称",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("court")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("类别",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("dataType")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("开庭时间(时间戳)",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("recordTime")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("开庭时间(年月日)",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("time")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                            }else if("WDHMD".equals(String.valueOf(pageDataMap.get("dataType")))){//网贷黑名单
                                                if(WDHMD){
                                                    cell = CreateTableUtil.createCell("网贷黑名单",  CreateTableUtil.keyLightBlue); //表格中添加的文字
                                                    cell.setColspan(8);
                                                    table.addCell(cell);
                                                    WDHMD = false;
                                                }
                                                cell = CreateTableUtil.createCell("网贷黑名单ID",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("网贷黑名单详情",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(7);
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("id")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setRowspan(8);
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("内容",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("content")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("当事人",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("name")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("类别",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("dataType")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("匹配度",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("matchRatio")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("本金/本息",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("principal")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("未还/罚息",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("penalty")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("已还金额",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("paid")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("信息更新时间",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("updateTime")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("被执行人身份证/组织机构代码",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("identificationNO")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("法院名称",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("court")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("相关当事人",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("relatedParty")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("数据来源单位名称",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("sourceName")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("开庭时间(时间戳)",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("recordTime")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("开庭时间(年月日)",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("time")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);
                                            }else if("AJLC".equals(String.valueOf(pageDataMap.get("dataType")))){//案件流程
                                                if(AJLC){
                                                    cell = CreateTableUtil.createCell("案件流程",  CreateTableUtil.keyLightBlue); //表格中添加的文字
                                                    cell.setColspan(8);
                                                    table.addCell(cell);
                                                    AJLC = false;
                                                }
                                                cell = CreateTableUtil.createCell("案件流程ID",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("案件流程详情",  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(7);
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("id")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setRowspan(8);
                                                cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("内容",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("content")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("当事人",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("name")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(6);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("类别",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("dataType")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("匹配度",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("matchRatio")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("案号",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("caseNo")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("案件类别",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("caseType")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("执行法院名称",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("court")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("流程状态",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("status")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);

                                                cell = CreateTableUtil.createCell("立案时间(时间戳)",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("recordTime")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(2);
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell("立案时间(年月日)",  CreateTableUtil.textfont); //表格中添加的文字
                                                table.addCell(cell);
                                                cell = CreateTableUtil.createCell(String.valueOf(pageDataMap.get("time")),  CreateTableUtil.textfont); //表格中添加的文字
                                                cell.setColspan(3);
                                                table.addCell(cell);
                                            }
                                        }
                                        flag = false;
                                    }
                                }
                            }
                        }
                    }
                    num ++;
                    if(flag && num >= itemListArray.size()){
                        cell = CreateTableUtil.createCell("无数据", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setColspan(8); //合并列
                        table.addCell(cell);
                    }
                }
                document.add(table);
                //备注
                paragraph = new Paragraph("\n\n", CreateTableUtil.keyGreyFont);
                document.add(paragraph);
                //备注
                paragraph = new Paragraph("免责声明 : \n", CreateTableUtil.keyGreyFont);
                document.add(paragraph);
                paragraph = new Paragraph("1.本报告为睿普征信根据信息主体的授权将查询到的信息进行收集、归纳，未对该信息作任何的修改，睿普征信不对信息的有效性、准确性、完整性做出任何承诺和保证。您若对该报告有任何疑问的，欢迎及时向睿普征信及信息源提出异议。\n", CreateTableUtil.textfont);
                document.add(paragraph);
                paragraph = new Paragraph("2.睿普征信对本报告不作任何明示或暗示的保证，请您自行承担相关的决策风险。请您按照约定的范围使用本报告，不得将本报告提供给第三人，否则后果自负。\n", CreateTableUtil.textfont);
                document.add(paragraph);

            }
            // 关闭文档
            document.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
