package com.shuiyi.app.toutiao.bean;

/**
 * Created by wang on 2016/6/4.
 */
public class TouTiaoBean {
    public TouTiaoBean()
    {
    }
    private String id;

    private String typeId;

    private String biaoTi;

    private String titleImg;

    private String jianJie;

    private String zuoZhe;

    private String neiRong;

    private String createDate;

    public String getBiaoTi() {
        return biaoTi;
    }

    public String getJianJie() {
        return jianJie;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getId() {
        return id;
    }

    public String getNeiRong() {
        return neiRong;
    }

    public String getTitleImg() {
        return titleImg;
    }

    public String getTypeId() {
        return typeId;
    }

    public String getZuoZhe() {
        return zuoZhe;
    }

    public void setBiaoTi(String biaoTi) {
        this.biaoTi = biaoTi;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setJianJie(String jianJie) {
        this.jianJie = jianJie;
    }

    public void setNeiRong(String neiRong) {
        this.neiRong = neiRong;
    }

    public void setTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public void setZuoZhe(String zuoZhe) {
        this.zuoZhe = zuoZhe;
    }

}
