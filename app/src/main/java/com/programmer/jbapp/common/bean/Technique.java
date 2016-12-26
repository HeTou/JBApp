package com.programmer.jbapp.common.bean;

/**
 * zft
 * 2016/11/22.
 */
public class Technique {
   private String url;   //图片
   private String title;   //标题
   private String dec;     //描述

    private String className;
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
