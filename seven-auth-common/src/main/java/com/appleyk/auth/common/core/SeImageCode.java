package com.appleyk.auth.common.core;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * <p>图形码</p>
 *
 * @author appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2022/3/18-10:08
 */
public class SeImageCode {

    private static Random r = new Random();
    private int w = 71;// 宽
    private int h = 25;// 高
    private String[] fontNames = { "宋体", "华文楷体", "黑体", "微软雅黑", "楷体_GB2312", "Arial" };
    private String codes = "1234567abcdefghijkmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYXZ";
    private int[] trans = { 0, 15, 20, 30, 35, 40 };
    private Color bgColor = new Color(255, 255, 255);
    private String text;

    /**
     * 返回一个验证码图片buffer对象：BufferedImage
     */
    public BufferedImage getImage() {
        BufferedImage image = createImage();
        // 获取绘图环境（画笔工具）
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        // sb ： 用来保存验证码字符串文本内容
        StringBuilder sb = new StringBuilder();
        drawLine(image);// 画干扰线
        for (int i = 0; i < 4; ++i) {// 随机生成4个字符
            String s = randomChar() + "";
            sb.append(s);
            Graphics2D g2d_word = (Graphics2D) g2;
            AffineTransform trans = new AffineTransform();
            trans.rotate(randomTran() * 3.14 / 180, 15 * i + 3.5, 5);
            g2d_word.setTransform(trans);
            g2.setFont(randomFont());
            g2.setColor(randomColor());
            g2.drawString(s, 15 * i + 7, 16);
        }
        this.text = sb.toString();// 记录验证码文本内容
        return image;
    }

    /**
     * @return 获取验证码文本内容
     */
    public String getText() {
        return text;
    }

    public void output(BufferedImage image, OutputStream out) {
        try {
            ImageIO.write(image, "jpeg", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawLine(BufferedImage image) {
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        for (int i = 0; i < 150; ++i) {
            int x1 = r.nextInt(w);
            int y1 = r.nextInt(h);
            int x2 = r.nextInt(w);
            int y2 = r.nextInt(h);
            g2.setColor(getRandColor(170, 200));
            g2.drawLine(x1, y1, x2, y2);
        }

    }

    public Color getRandColor(int min, int max) {
        if (min > 255)
            min = 255;
        if (max > 255)
            max = 255;
        int red = r.nextInt(max - min) + min;
        int green = r.nextInt(max - min) + min;
        int blue = r.nextInt(max - min) + min;
        return new Color(red, green, blue);
    }

    public Color randomColor() {
        int red = r.nextInt(150);
        int green = r.nextInt(150);
        int blue = r.nextInt(150);
        return new Color(red, green, blue);
    }

    public Font randomFont() {
        int index = r.nextInt(fontNames.length);
        String fontName = fontNames[index];
        int style = r.nextInt(4);
        int size = r.nextInt(5) + 20;
        return new Font(fontName, style, size);
    }

    public char randomChar() {
        int index = r.nextInt(codes.length());
        return codes.charAt(index);
    }

    public int randomTran() {
        int index = r.nextInt(trans.length);
        return trans[index];
    }

    public BufferedImage createImage() {
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setColor(this.bgColor);
        g2.fillRect(0, 0, w, h);
        return image;
    }
}
