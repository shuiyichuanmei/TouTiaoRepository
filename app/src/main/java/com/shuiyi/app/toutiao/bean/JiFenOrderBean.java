package com.shuiyi.app.toutiao.bean;

import java.util.Date;

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
    private String dingdanhao;
    private String createdate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSpJifen(int spJifen) {
        SpJifen = spJifen;
    }

    public String getDingdanhao() {
        return dingdanhao;
    }

    public void setDingdanhao(String dingdanhao) {
        this.dingdanhao = dingdanhao;
    }

    public String getMingcheng() {

        return mingcheng;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
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

