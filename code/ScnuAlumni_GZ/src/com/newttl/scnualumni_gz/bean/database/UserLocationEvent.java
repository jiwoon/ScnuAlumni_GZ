package com.newttl.scnualumni_gz.bean.database;

/**
 * 远程数据库中 user_location_event 表的javabean对象
 * @author lgc
 *
 * @date 2017年7月3日 下午5:15:49
 */
public class UserLocationEvent {

	//用户的  openId
	private String fromUserName;
	//消息创建时间
	private long createTime;
	//用户的纬度
	private String latitude;
	//用户的经度
	private String longitude;
	//用户位置的精确度
	private String precision;
	//经纬度转换后的用户位置信息
	private String address;
	
	
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getPrecision() {
		return precision;
	}
	public void setPrecision(String precision) {
		this.precision = precision;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
