package com.newttl.scnualumni_gz.bean.pojo;

import java.util.List;

/**
 * 微信用户的基本信息
 * @author lgc
 *
 * @date 2017年7月4日 下午2:38:29
 */
public class WeiXinUserInfo {

	/*
	{
		   "subscribe": 1, 
		   "openid": "o6_bmjrPTlm6_2sgVt7hMZOPfL2M", 
		   "nickname": "Band", 
		   "sex": 1, 
		   "language": "zh_CN", 
		   "city": "广州", 
		   "province": "广东", 
		   "country": "中国", 
		   "headimgurl":  "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4
		eMsv84eavHiaiceqxibJxCfHe/0",
		  "subscribe_time": 1382694957,
		  "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
		  "remark": "",
		  "groupid": 0,
		  "tagid_list":[128,2]
		}
	*/
	
	/* 用户是否订阅该公众号标识，值为0时，代表此用户没有关注该公众号，拉取不到其余信息，只有openid和UnionID（在该公众号绑定到了微信开放平台账号时才有）
	 * 值为1时，代表此用户已关注该公众号
	 */
	private int subscribe;
	//用户的标识
	private String openId;
	//用户的昵称
	private String nickName;
	//用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	private int sex;
	//国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语，默认为zh-CN
	private String language;
	//用户所在城市
	private String city;
	//用户所在省份
	private String province;
	//用户所在国家
	private String country;
	//用户没有头像时该项为空。若用户更换头像，原有头像URL将失效
	private String headImgUrl;
	//用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	private String subscribeTime;
	//只有在用户将公众号绑定到微信开放平台帐号后，才会出现该字段。
	private String unionId;
	//公众号运营者对粉丝的备注，公众号运营者可在微信公众平台用户管理界面对粉丝添加备注
	private String remark;
	//用户所在的分组ID
	private String groupId;
	//用户被打上的标签ID列表
	private List<Integer> tagIdList;
	
	public int getSubscribe() {
		return subscribe;
	}
	public void setSubscribe(int subscribe) {
		this.subscribe = subscribe;
	}
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
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
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
	public String getSubscribeTime() {
		return subscribeTime;
	}
	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}
	public String getUnionId() {
		return unionId;
	}
	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public List<Integer> getTagIdList() {
		return tagIdList;
	}
	public void setTagIdList(List<Integer> tagIdList) {
		this.tagIdList = tagIdList;
	}
	
	public String getWeixinUserInfo(){
		String sex="";
		if (getSex() == 1) {
			sex="男";
		}else if (getSex() == 2) {
			sex="女";
		}else {
			sex="未知";
		}
		return "subscribe::"+getSubscribe()+"\n"+"openid::"+getOpenId()+"\n"+"nickname::"+getNickName()
				+"\n"+"sex::"+sex+"\n"+"language::"+getLanguage()+"\n"+"city::"+getCity()+"\n"
				+"province::"+getProvince()+"\n"+"country::"+getCountry()+"\n"+"headimgurl::"+getHeadImgUrl()+"\n"
				+"subscribe_time::"+getSubscribeTime()+"\n"+"remark::"+getRemark()+"\n"+"groupid::"+getGroupId()
				+"\n"+"tagid_list::"+getTagIdList()+"\n"+"unionid::"+getUnionId();
	}
	
}
