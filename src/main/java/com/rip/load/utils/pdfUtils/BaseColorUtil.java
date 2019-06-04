package com.rip.load.utils.pdfUtils;

import com.itextpdf.text.BaseColor;

/**
 * @Description: 自定义颜色
 * @Author: FYH
 * @Date: Created in 15:16 2019/5/21
 * @Modified:
 */
public class BaseColorUtil extends BaseColor{
    public static final BaseColor Grey = new BaseColor(119,136,153); //灰色
    public static final BaseColor LightGrey = new BaseColor(211,211,211); //浅灰色
    public static final BaseColor LightBlue = new BaseColor(30,144,255); //浅蓝色
    public static final BaseColor Red = new BaseColor(205,0,0); //红色
    public static final BaseColor Orange = new BaseColor(255,165,0);//橘色
    public static final BaseColor Green = new BaseColor(24,180,109);

    public BaseColorUtil( int red, int green, int blue, int alpha ) {
        super(red, green, blue, alpha);
    }

    public BaseColorUtil( int red, int green, int blue ) {
        super(red, green, blue);
    }

    public BaseColorUtil( float red, float green, float blue, float alpha ) {
        super(red, green, blue, alpha);
    }

    public BaseColorUtil( float red, float green, float blue ) {
        super(red, green, blue);
    }

    public BaseColorUtil( int argb ) {
        super(argb);
    }
}
