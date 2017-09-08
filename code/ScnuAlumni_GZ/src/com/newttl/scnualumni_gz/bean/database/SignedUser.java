package com.newttl.scnualumni_gz.bean.database;

/**
 * 用户注册的信息
 * @author lgc
 *
 * 2017年8月2日 下午3:07:41
 */
public class SignedUser {

	//openId headImgUrl userName phone QQ eMail city industry hobby profession sex
	
	//用户标识
	private String openId;
	//用户头像url
	private String headImgUrl;
	//性别
	private String sex;
	//用户姓名
	private String userName;
	//所在学院
	private String college;
	//所在班级
	private String userClass;
	//年级
	private String grade;
	//联系方式内容
	private String contact;
	//联系方式 1:QQ 2:邮箱  3:手机号码
	private String contactType;
	//城市
	private String city;
	//从事行业
	private String industry;
	//爱好
	private String hobby;
	//从事职业
	private String profession;
	
	
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getCollege() {
		return college;
	}
	public void setCollege(String college) {
		this.college = college;
	}
	public String getUserClass() {
		return userClass;
	}
	public void setUserClass(String userClass) {
		this.userClass = userClass;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getContactType() {
		return contactType;
	}
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	
}
