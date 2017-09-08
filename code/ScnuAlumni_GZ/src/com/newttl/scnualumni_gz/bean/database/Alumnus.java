package com.newttl.scnualumni_gz.bean.database;

/**
 * 查找校友首页校友个人信息
 * @author lgc
 *
 * 2017年8月14日 下午4:07:31
 */
public class Alumnus {

	private String openId;
	private String userName;
	private String headImgUrl;
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	
}
