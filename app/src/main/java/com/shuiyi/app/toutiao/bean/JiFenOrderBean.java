package com.shuiyi.app.toutiao.bean;

/**
 * Created by Hey on 2016/6/28.
 */
public class JiFenOrderBean {
    public JiFenOrderBean() {
    }

    private String id;
    private String mingcheng;
    private int SpJifen;
    private String Spimg;
    private String status;

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

    public void setSpimg(String Spimg) {
        this.Spimg = Spimg;
    }
    public int getSpJifen() {
        return SpJifen;
    }
    public void setSpJifen(String SpJifen) {
        String.valueOf(SpJifen);
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String Status) {
        this.status = Status;
    }
}

