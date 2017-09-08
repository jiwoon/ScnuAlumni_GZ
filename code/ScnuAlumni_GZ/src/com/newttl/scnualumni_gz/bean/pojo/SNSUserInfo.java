package com.newttl.scnualumni_gz.bean.pojo;

import java.util.List;

/**
 * 通过网页授权拉取用户信息
 * 
 * @author lgc
 *
 * @date 2017年6月27日 上午10:21:33
 */
public class SNSUserInfo {

	/*
	 { 
	  	 "openid":" OPENID",  
		 " nickname": NICKNAME,   
		 "sex":"1",   
		 "province":"PROVINCE"   
		 "city":"CITY",   
		 "country":"COUNTRY",    
		 "headimgurl":    "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ
		4eMsv84eavHiaiceqxibJxCfHe/46",  
		"privilege":[ "PRIVILEGE1" "PRIVILEGE2"     ],    
		 "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL" 
		} 
	 */
	
	// 用户标识
	private String openId;
	// 用户昵称
	private String nickName;
	// 性别（1是男性，2是女性，0是未知）
	private int sex;
	// 省份
	private String province;
	// 城市
	private String city;
	// 国家
	private String country;
	// 用户头像链接
	private String headImgUrl;
	//用户特权
	private List<String> privilegeList;
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public List<String> getPrivilegeList() {
		return privilegeList;
	}
	public void setPrivilegeList(List<String> privilegeList) {
		this.privilegeList = privilegeList;
	}
	
	
}
