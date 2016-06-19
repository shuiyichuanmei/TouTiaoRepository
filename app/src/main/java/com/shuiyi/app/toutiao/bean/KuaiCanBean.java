package com.shuiyi.app.toutiao.bean;

/**
 * Created by Hey on 2016/6/17.
 */
public class KuaiCanBean {
    public KuaiCanBean() {
    }

    private String id;
    private String Biaoti;
    private String ImageUrl;
    private String xfText;
    private String txText;
    private String createDate;
    private String typeId;

    public String getId() {
        return id;
    }

    public String getBiaoti() {
        return Biaoti;
    }

    public String getXfText() {
        return xfText;
    }

    public String getTxText() {
        return txText;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBiaoti(String biaoti) {
        this.Biaoti = biaoti;
    }

    public void setxfText(String xftext) {
        this.xfText = xftext;
    }

    public void settxText(String txtext) {
        this.txText = txtext;
    }

    public void setImageUrl(String imgurl) {
        this.ImageUrl = imgurl;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
}

