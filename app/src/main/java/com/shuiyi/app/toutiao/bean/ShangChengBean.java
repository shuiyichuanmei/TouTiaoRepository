package com.shuiyi.app.toutiao.bean;

/**
 * Created by Hey on 2016/6/25.
 */
public class ShangChengBean {
    public ShangChengBean() {
    }

    private String Spid;
    private String SpName;
    private String SpJifen;
    private String Spimg;

    public String getId() {
        return Spid;
    }

    public void setId(String id) {
        this.Spid = id;
    }

    public String getSpName() {
        return SpName;
    }

    public void setSpName(String mingcheng) {
        this.SpName = mingcheng;
    }

    public String getSpimg() {
        return Spimg;
    }

    public void setSpimg(String imgxiao) {
        this.Spimg = imgxiao;
    }

    public String getSpJifen() {
        return SpJifen;
    }

    public void setSpJifen(String spJifen) {
        this.SpJifen = spJifen;
    }


}
