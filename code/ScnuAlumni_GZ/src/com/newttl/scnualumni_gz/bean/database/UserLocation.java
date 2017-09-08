package com.newttl.scnualumni_gz.bean.database;

/**
 * 
 * 用户发送的地理位置信息类
 * @author lgc
 *
 * 2017年7月14日 上午9:05:22
 */
public class UserLocation {

	//用户的  openId
	private String openId;
	//用户发送过来的经度
	private String lng;
	//用户发送过来的维度
	private String lat;
	//转换为百度坐标系经度
	private String bd09Lng;
	//转换为百度坐标系维度
	private String bd09Lat;
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getBd09Lng() {
		return bd09Lng;
	}
	public void setBd09Lng(String bd09Lng) {
		this.bd09Lng = bd09Lng;
	}
	public String getBd09Lat() {
		return bd09Lat;
	}
	public void setBd09Lat(String bd09Lat) {
		this.bd09Lat = bd09Lat;
	}
	
}
