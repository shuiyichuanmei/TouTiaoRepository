package com.shuiyi.app.toutiao.bean;

public class ImageBean extends BaseBean {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String imgurl = "";
	private String imgpath = "";
	private String imglink = "";
	private String imginfo = "";

	public String getImgpath() {
		return imgpath;
	}

	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getImglink() {
		return imglink;
	}

	public void setImglink(String imglink) {
		this.imglink = imglink;
	}

	public String getImginfo() {
		return imginfo;
	}

	public void setImginfo(String imginfo) {
		this.imginfo = imginfo;
	}

}
