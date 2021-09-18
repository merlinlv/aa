package com.me;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class QtCodeUtil
{
    public static  String getCodeStr(){
        QrConfig config = new QrConfig(300, 300) ;
        // 设置边距，既二维码和背景之间的边距
        config.setMargin(3);
        // 设置前景色，既二维码颜色（青色）
        config.setForeColor(145);
        // 设置背景色（灰色）
        config.setBackColor(200);
        config.setErrorCorrection(ErrorCorrectionLevel.H);
        BufferedImage bf= QrCodeUtil.generate("你好",config);
        File f= QrCodeUtil.generate("你好",config ,FileUtil.file("C:\\Users\\merli\\Desktop\\1\\qrcode4.jpg"));
        ByteArrayOutputStream out=new ByteArrayOutputStream();
        String temp=null;
        try {
            ImageIO.write(bf,"png",out);
            temp= Base64.getEncoder().encodeToString(out.toByteArray());
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                out.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return  temp;
    }

    public static  void main (String [] args){
        System.out.println(getCodeStr());
    }
}
