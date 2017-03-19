package com.programmer.jbapp.common.bean;

/**
 * zft
 * 2016/11/22.
 */
public class Moudle {
    private int img;
    private String name;
    private Class activity;


    public Moudle(int img, String name, Class activity) {
        this.img = img;
        this.name = name;
        this.activity = activity;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public Class getActivity() {
        return activity;
    }

    public void setActivity(Class activity) {
        this.activity = activity;
    }
}
