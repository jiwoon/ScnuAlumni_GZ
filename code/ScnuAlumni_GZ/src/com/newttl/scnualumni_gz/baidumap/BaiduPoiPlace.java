package com.newttl.scnualumni_gz.baidumap;

/**
 * 调用百度地图poi到的地址信息
 * 
 * @author lgc
 *
 * 2017年7月14日 上午10:23:46
 */
public class BaiduPoiPlace implements Comparable<BaiduPoiPlace>{

	//地点名称
	private String name;
	//经度
	private String lng;
	//维度
	private String lat;
	//详细地址
	private String address;
	//联系电话
	private String telephone;
	//距离
	private int distance;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	@Override
	public int compareTo(BaiduPoiPlace o) {
		return this.distance-o.distance;
	}
	
	
}
