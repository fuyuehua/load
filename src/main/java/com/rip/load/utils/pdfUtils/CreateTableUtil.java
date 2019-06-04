package com.rip.load.utils.pdfUtils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * @Description: 创建表格
 * @Author: FYH
 * @Date: Created in 16:14 2019/5/21
 * @Modified:
 */
public class CreateTableUtil {
    public static Font headfontfirst;// 标题一字体大小
    public static Font headfontsecond;// 标题二字体大小
    public static Font headfontthird;// 标题一字体大小
    public static Font headRedfont;// 标题二字体大小
    public static Font keyGreyFont;//灰色关键字
    public static Font keyfonfirst; //关键字字体大小
    public static Font keyLightBlue;//关键字字体大小
    public static Font textfont;//正文字体大小
    public static Font textRedFont;//正文红色字体
    public static Font textGreenFont;//正文绿色字体
    public static Font textOrangeFont; //正文橘色字体
    public static Font textkeyfont;// 正文中的关键字字体大小
    public static Font textwhitefont;//正文字体大小
    public static BaseColor tatleTitle = BaseColorUtil.Grey; //表格标题底色（深灰色）
    public static BaseColor tableBody = BaseColorUtil.LightGrey; //表格正文底色（浅灰色）

    private static int maxWidth = 520;
    static {
        BaseFont bfChinese;
        try {
            bfChinese = BaseFont.createFont("STSong-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);//设置字体用宋体
            headfontfirst = new Font(bfChinese, 16, Font.BOLD,BaseColorUtil.GRAY);// 标题字体大小(默认黑色)
            headfontsecond = new Font(bfChinese, 10, Font.BOLD,BaseColorUtil.WHITE);// 关键标题字体大小和颜色(白色)
            headfontthird = new Font(bfChinese, 12, Font.BOLD,BaseColorUtil.GRAY);// 标题字体大小(默认黑色)
            headRedfont = new Font(bfChinese,10,Font.NORMAL,BaseColorUtil.Red); //红色
            keyLightBlue = new Font(bfChinese, 9, Font.NORMAL,BaseColorUtil.LightBlue);// 关键标题字体大小和颜色(前蓝色)
            keyfonfirst = new Font(bfChinese, 8, Font.NORMAL,BaseColorUtil.LightBlue);// 关键标题字体大小和颜色(前蓝色)
            textfont = new Font(bfChinese, 8, Font.NORMAL,BaseColorUtil.DARK_GRAY);// 字段字体大小(默认深灰色)
            textkeyfont = new Font(bfChinese,8,Font.NORMAL,BaseColorUtil.GRAY);// 字段字体大小(默认浅灰色)
            textRedFont = new Font(bfChinese,8,Font.NORMAL,BaseColorUtil.Red); //红色
            textGreenFont = new Font(bfChinese,8,Font.NORMAL,BaseColorUtil.GREEN); //绿色
            textOrangeFont = new Font(bfChinese,8,Font.NORMAL,BaseColorUtil.ORANGE); //橘色
            keyGreyFont = new Font(bfChinese, 10, Font.NORMAL,BaseColorUtil.DARK_GRAY);// 字段字体大小(默认深灰色)
            textwhitefont = new Font(bfChinese, 8, Font.BOLD,BaseColorUtil.WHITE);// 关键标题字体大小和颜色(白色)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 一般用于创建表格正文内容
     * @param value 单元格内容
     * @param font  字体大小
     * @return
     */
    public static PdfPCell createCell( String value, Font font){
        PdfPCell cell = new PdfPCell();
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);//设置表格中的文字内容靠左
        cell.setPhrase(new Phrase(value,font));//文字内容和文字大小
        cell.setPadding(4.0f);//内边距
        cell.setBorderColor(BaseColorUtil.Grey); //表格边框颜色
        cell.setBorderWidth(0.1f);//表格线条的粗细
        return cell;
    }

    /**
     * 创建带部分属性的表格
     *
     * @param colNumber 列数
     * @return
     */
    public static PdfPTable createTable( int colNumber) {
        PdfPTable table = new PdfPTable(colNumber);
        try {
            table.setTotalWidth(maxWidth);
            table.setLockedWidth(true);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setBorder(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return table;
    }


    /**
     * 毫米转像素
     * @param mm
     * @return
     */
    public static float mmTopx(float mm){
        mm = (float) (mm *3.33) ;
        return mm ;
    }
}
