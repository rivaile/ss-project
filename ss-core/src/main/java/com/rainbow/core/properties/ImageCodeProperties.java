package com.rainbow.core.properties;

/**
 * @author denglin
 * @version V1.0
 * @Description: 图片验证码配置项
 * @ClassName: BrowserProperties
 * @date 2018/9/17 15:21
 */
public class ImageCodeProperties extends SmsCodeProperties {

    public ImageCodeProperties() {
        setLength(4);
    }

    /**
     * 图片宽
     */
    private int width = 67;
    /**
     * 图片高
     */
    private int height = 23;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
