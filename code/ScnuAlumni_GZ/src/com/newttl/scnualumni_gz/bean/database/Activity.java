package com.newttl.scnualumni_gz.bean.database;


/**
 * 活动信息
 * @author jason
 *
 */
public class Activity {
	
	private int id;
	
	private String openID;
	
	private String activityName;
	
	private String startTime;
	
	private String endTime;
	
	private String activityIntro;
	
	private String activityHolder;
	
	private String activityAddress;
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOpenID() {
		return openID;
	}

	public void setOpenID(String openID) {
		this.openID = openID;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getActivityIntro() {
		return activityIntro;
	}

	public void setActivityIntro(String activityIntro) {
		this.activityIntro = activityIntro;
	}

	public String getActivityHolder() {
		return activityHolder;
	}

	public void setActivityHolder(String activityHolder) {
		this.activityHolder = activityHolder;
	}

	public String getActivityAddress() {
		return activityAddress;
	}

	public void setActivityAddress(String activityAddress) {
		this.activityAddress = activityAddress;
	}
	
	
}
