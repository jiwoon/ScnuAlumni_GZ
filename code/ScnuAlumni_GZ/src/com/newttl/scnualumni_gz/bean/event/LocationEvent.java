package com.newttl.scnualumni_gz.bean.event;

import com.newttl.scnualumni_gz.bean.event.BaseEvent;

/**
 * 上报位置事件,公众号获取用户位置
 * @author Administrator
 *
 */
public class LocationEvent extends BaseEvent {

	
	//地理位置纬度
	private String Latitude;
	//地理位置经度
	private String Longitude;
	//地理位置精度
	private String Precision;
	
	
	public String getLatitude() {
		return Latitude;
	}
	public void setLatitude(String latitude) {
		Latitude = latitude;
	}
	public String getLongitude() {
		return Longitude;
	}
	public void setLongitude(String longitude) {
		Longitude = longitude;
	}
	public String getPrecision() {
		return Precision;
	}
	public void setPrecision(String precision) {
		Precision = precision;
	}
	
	
}
