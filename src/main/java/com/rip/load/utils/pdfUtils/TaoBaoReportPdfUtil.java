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
 * @Description: 生成淘宝报告
 * @Author: FYH
 * @Date: Created in 13:56 2019/5/21
 * @Modified:
 */
public class TaoBaoReportPdfUtil {

    /**
     * 淘宝报告
     * @throws IOException
     * @throws DocumentException
     */
    public static Boolean taoBaoReport(String json,String savePath) {
        try {
            //################################################获取数据############################################################
            Map<String, Object> jsonMap = JSON.parseObject(json);
            if (jsonMap.get("code") != null && Integer.valueOf(jsonMap.get("code").toString()) == 200) {
                Map<String, Object> resultMap = JSON.parseObject(jsonMap.get("result").toString());
                JSONArray itemListArray = JSONArray.parseArray(resultMap.get("itemList").toString());//用户详情
                for (Object items : itemListArray) {
                    Map<String, Object> itemsMap = JSON.parseObject(String.valueOf(items));
                    if ("15".equals(String.valueOf(itemsMap.get("type")))) {//淘宝报告

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
                        Map<String,Object> alipayInfoMap = JSON.parseObject(dataMap.get("alipayInfo").toString());//绑定的支付宝信息
                        JSONArray addressesArray = JSON.parseArray(dataMap.get("addresses").toString());//收货地址
                        JSONArray orderDetailsArray = JSON.parseArray(dataMap.get("orderDetails").toString());//订单信息，商品信息、商家信息

                        PdfPTable table = CreateTableUtil.createTable(1);
                        Image img = null;
                        try{
                            img = Image.getInstance("../img/taobao.jpg");//图片的地址
                        }catch (Exception e){
                        }
                        if(img == null)
                            img = Image.getInstance("/usr/local/tomcatfangdi-8088/webapps/img/taobao.jpg");//图片的地址

                        img.scaleAbsolute(CreateTableUtil.mmTopx(200),CreateTableUtil.mmTopx(297));
                        PdfPCell photoCell = new PdfPCell(img);
                        photoCell.setBorder(0);
                        photoCell.setHorizontalAlignment(1);
                        table.addCell(photoCell);
                        document.add(table);

                        // 向文档中添加内容
                        Paragraph paragraph = new Paragraph("淘宝报告",CreateTableUtil.headfontfirst);
                        paragraph.setAlignment(1);
                        document.add(paragraph);
                        document.add(new Paragraph("\n"));

                        //################################################基本信息############################################################
                        table = CreateTableUtil.createTable(4);
                        float[] columnWidths = { 1.5f, 3f, 1.5f, 3f};
                        table.setWidths(columnWidths);

                        //######第一行内容######
                        PdfPCell cell = CreateTableUtil.createCell("基本信息", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(4); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);
                        //######第二行内容######
                        cell = CreateTableUtil.createCell("真实姓名:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("realName").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("登陆邮箱:",CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("email").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        //######第三行内容######
                        cell = CreateTableUtil.createCell("身份证号:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("identityNo").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("绑定手机:",CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("mobile").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        //######第四行内容######
                        cell = CreateTableUtil.createCell("用户号:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("username").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("会员等级:",CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("vipLevel").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        //######第五行内容######
                        cell = CreateTableUtil.createCell("昵称:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("nickName").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("成长值:",CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("growthValue").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        //######第六行内容######
                        cell = CreateTableUtil.createCell("性别:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("gender").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("信用积分:",CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("creditScore").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        //######第七行内容######
                        cell = CreateTableUtil.createCell("出生日期:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("birthday").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("好评率:",CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("favorableRate").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        //######第八行内容######
                        cell = CreateTableUtil.createCell("认证渠道:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("identityChannel").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("安全等级:",CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("securityLevel").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        //######第八行内容######
                        cell = CreateTableUtil.createCell("是否实名认证:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(basicInfoMap.get("identityStatus").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(null,CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(null,CreateTableUtil.textfont);
                        table.addCell(cell);

                        document.add(table);

                        //################################################绑定的支付宝信息表############################################################
                        table = CreateTableUtil.createTable(4);
                        table.setSpacingBefore(5f); //和上一个表格间距5f

                        float[] alipayInfoColumnWidths = { 1.5f, 3f, 1.5f, 3f};
                        table.setWidths(alipayInfoColumnWidths);
                        //######第一行内容######
                        cell = CreateTableUtil.createCell("绑定的支付宝信息:", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(4); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);
                        //######第二行内容######
                        cell = CreateTableUtil.createCell("支付宝账号:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(alipayInfoMap.get("username").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("真实认证姓名:",CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(alipayInfoMap.get("realName").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        //######第三行内容######
                        cell = CreateTableUtil.createCell("邮箱:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(alipayInfoMap.get("email").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("实名认证身份证号码:",CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(alipayInfoMap.get("identityNo").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        //######第四行内容######
                        cell = CreateTableUtil.createCell("绑定手机:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(alipayInfoMap.get("mobile").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("实名认证状态:",CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(alipayInfoMap.get("identityStatus").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        //######第五行内容######
                        cell = CreateTableUtil.createCell("账户余额:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(alipayInfoMap.get("accBal").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("花呗可用额度:",CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(alipayInfoMap.get("huabeiAvailableLimit").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        //######第六行内容######
                        cell = CreateTableUtil.createCell("余额宝:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(alipayInfoMap.get("yuebaoBal").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("花呗消费额度:",CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(alipayInfoMap.get("huabeiLimit").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        //######第七行内容######
                        cell = CreateTableUtil.createCell("历史累计收益:", CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(alipayInfoMap.get("yuebaoHisIncome").toString(),CreateTableUtil.textfont);
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(null,CreateTableUtil.textfont);
                        cell.setBackgroundColor(CreateTableUtil.tableBody);//表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell(null,CreateTableUtil.textfont);
                        table.addCell(cell);
                        document.add(table);

                        //################################################收货地址表############################################################
                        table = CreateTableUtil.createTable(4);
                        table.setSpacingBefore(5f); //和上一个表格间距5f

                        float[] addressesColumnWidths = { 1f, 5f, 1f, 1f};
                        table.setWidths(addressesColumnWidths);
                        //######第一行内容######
                        cell = CreateTableUtil.createCell("收货地址:", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setColspan(4); //合并列
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle); //表格底色
                        table.addCell(cell);
                        //######第二行内容######
                        cell = CreateTableUtil.createCell("姓名", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("地址", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("邮编", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        cell = CreateTableUtil.createCell("手机号", CreateTableUtil.textfont); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tableBody); //表格底色
                        table.addCell(cell);
                        for(Object address:addressesArray){
                            Map<String,Object> addressMap = JSON.parseObject(address.toString());
                            //######第三行内容######
                            cell = CreateTableUtil.createCell(addressMap.get("name").toString(), CreateTableUtil.textfont); //表格中添加的文字
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(addressMap.get("address").toString(), CreateTableUtil.textfont); //表格中添加的文字
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(addressMap.get("zipCode").toString(), CreateTableUtil.textfont); //表格中添加的文字
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(addressMap.get("mobile").toString(), CreateTableUtil.textfont); //表格中添加的文字
                            table.addCell(cell);
                        }

                        document.add(table);

                        //################################################订单详情表############################################################
                        table = CreateTableUtil.createTable(10);
                        table.setSpacingBefore(5f); //和上一个表格间距5f

                        float[] orderDetailsColumnWidths = { 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f, 2f};
                        table.setWidths(orderDetailsColumnWidths);

                        //######第一行内容######
                        cell = CreateTableUtil.createCell("订单详情", CreateTableUtil.headfontsecond); //表格中添加的文字
                        cell.setBackgroundColor(CreateTableUtil.tatleTitle);
                        cell.setColspan(10); //合并列
                        table.addCell(cell);
                        document.add(table);

                        for(Object orderDetails:orderDetailsArray){
                            table = CreateTableUtil.createTable(10);
                            table.setWidths(orderDetailsColumnWidths);
                            Map<String,Object> orderDetailsMap = JSON.parseObject(orderDetails.toString());
                            //######第二行内容######
                            cell = CreateTableUtil.createCell("订单号 :    "+orderDetailsMap.get("orderId").toString(), CreateTableUtil.keyfonfirst);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            cell.setColspan(10); //合并列
                            table.addCell(cell);
                            //######第三行内容######
                            cell = CreateTableUtil.createCell("订单金额 :", CreateTableUtil.textkeyfont);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(orderDetailsMap.get("orderAmt").toString(), CreateTableUtil.textfont);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell("订单时间 :", CreateTableUtil.textkeyfont);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(orderDetailsMap.get("orderTime").toString(), CreateTableUtil.textfont);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            cell.setColspan(7); //合并列
                            table.addCell(cell);
                            //######第四行内容######
                            cell = CreateTableUtil.createCell("订单状态 :", CreateTableUtil.textkeyfont);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(orderDetailsMap.get("orderStatus").toString(), CreateTableUtil.textfont);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            table.addCell(cell);

                            Map<String,Object> logisticsInfoMap = JSON.parseObject(orderDetailsMap.get("logisticsInfo").toString());//获取物流信息

                            cell = CreateTableUtil.createCell("收货人信息 :", CreateTableUtil.textkeyfont);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(logisticsInfoMap.get("receivePersonName").toString()+"  "+logisticsInfoMap.get("receivePersonMobile").toString(), CreateTableUtil.textfont);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            cell.setColspan(2);
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(logisticsInfoMap.get("receiveAddress").toString(), CreateTableUtil.textfont);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            cell.setColspan(5); //合并列
                            table.addCell(cell);

                            //******商品信息******
                            JSONArray itemsArray = JSON.parseArray(orderDetailsMap.get("items").toString());//订单信息，商品信息、商家信息
                            for(Object item:itemsArray){
                                Map<String,Object> itemMap = JSON.parseObject(item.toString());

                                cell = CreateTableUtil.createCell("购买商品信息", CreateTableUtil.keyfonfirst);
                                cell.setBorderWidth(0.0f);//表格线条的粗细
                                cell.setColspan(10); //合并列
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell("商品ID", CreateTableUtil.textkeyfont);
                                cell.setBorderWidth(0.0f);//表格线条的粗细
                                cell.setColspan(2); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("商品名称", CreateTableUtil.textkeyfont);
                                cell.setBorderWidth(0.0f);//表格线条的粗细
                                cell.setColspan(4); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("商品单价", CreateTableUtil.textkeyfont);
                                cell.setBorderWidth(0.0f);//表格线条的粗细
                                cell.setColspan(2); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell("商品数量", CreateTableUtil.textkeyfont);
                                cell.setBorderWidth(0.0f);//表格线条的粗细
                                cell.setColspan(2); //合并列
                                table.addCell(cell);

                                cell = CreateTableUtil.createCell(itemMap.get("itemId").toString(), CreateTableUtil.textfont);
                                cell.setBorderWidth(0.0f);//表格线条的粗细
                                cell.setColspan(2); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(itemMap.get("itemName").toString(), CreateTableUtil.textfont);
                                cell.setBorderWidth(0.0f);//表格线条的粗细
                                cell.setColspan(4); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(itemMap.get("itemPrice").toString(), CreateTableUtil.textfont);
                                cell.setBorderWidth(0.0f);//表格线条的粗细
                                cell.setColspan(2); //合并列
                                table.addCell(cell);
                                cell = CreateTableUtil.createCell(itemMap.get("itemQuantity").toString(), CreateTableUtil.textfont);
                                cell.setBorderWidth(0.0f);//表格线条的粗细
                                cell.setColspan(2); //合并列
                                table.addCell(cell);
                            }
                            //******物流信息******
                            cell = CreateTableUtil.createCell("物流信息", CreateTableUtil.keyfonfirst);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            cell.setColspan(10); //合并列
                            table.addCell(cell);

                            cell = CreateTableUtil.createCell("运送方式", CreateTableUtil.textkeyfont);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            cell.setColspan(2); //合并列
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell("物流公司", CreateTableUtil.textkeyfont);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            cell.setColspan(4); //合并列
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell("送货单号", CreateTableUtil.textkeyfont);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            cell.setColspan(4); //合并列
                            table.addCell(cell);

                            cell = CreateTableUtil.createCell(logisticsInfoMap.get("deliverType").toString(), CreateTableUtil.textfont);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            cell.setBorderWidthBottom(0.1f);//表格最下方表格线条
                            cell.setColspan(2); //合并列
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(logisticsInfoMap.get("deliverCompany").toString(), CreateTableUtil.textfont);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            cell.setBorderWidthBottom(0.1f);//表格最下方表格线条
                            cell.setColspan(4); //合并列
                            table.addCell(cell);
                            cell = CreateTableUtil.createCell(logisticsInfoMap.get("deliverId").toString(), CreateTableUtil.textfont);
                            cell.setBorderWidth(0.0f);//表格线条的粗细
                            //cell.setBorderWidthRight(0.1f);//表格最右测表格线条
                            cell.setBorderWidthBottom(0.1f);//表格最下方表格线条
                            cell.setColspan(4); //合并列
                            table.addCell(cell);
                            document.add(table);
                        }
                        // 关闭文档
                        document.close();
                        return true;
                    }
                }
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (DocumentException e) {
            e.printStackTrace();
            return false;
        }
    }
}
