package com.shuiyi.app.toutiao.bean;

/**
 * Created by Hey on 2016/6/25.
 */
public class ShangChengBean {
    public ShangChengBean() {
    }

    private String id;
    private String mingcheng;
    private double SpJifen;
    private String Spimg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMingcheng() {
        return mingcheng;
    }

    public void setMingcheng(String mingcheng) {
        this.mingcheng = mingcheng;
    }

    public String getSpimg() {
        return Spimg;
    }

    public void setSpimg(String spimg) {
        this.Spimg = spimg;
    }

    public double getSpJifen() {
        return SpJifen;
    }

    public void setSpJifen(double spJifen) {
        this.SpJifen = spJifen;
    }
}
