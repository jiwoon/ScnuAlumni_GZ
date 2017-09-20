package com.newttl.scnualumni_gz.bean.menu;

/**
 * 个性菜单匹配规则
 * @author lgc
 *
 * 2017年9月20日 上午8:37:36
 */
public class Matchrule {

	//分组id
	private String group_id;
	//男（1）女（2），不填则不做匹配
	private String sex;
	//客户端版本，当前只具体到系统型号：IOS(1), Android(2),Others(3)，不填则不做匹配
	private String client_platform_type;
	//国家,不填则不做匹配
	private String country;
	//省份,不填则不做匹配
	private String province;
	//城市,不填则不做匹配
	private String city;
	//语言,不填不做匹配
	private String language;
	
	public String getGroup_id() {
		return group_id;
	}
	public void setGroup_id(String group_id) {
		this.group_id = group_id;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getClient_platform_type() {
		return client_platform_type;
	}
	public void setClient_platform_type(String client_platform_type) {
		this.client_platform_type = client_platform_type;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
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
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	
	
}
