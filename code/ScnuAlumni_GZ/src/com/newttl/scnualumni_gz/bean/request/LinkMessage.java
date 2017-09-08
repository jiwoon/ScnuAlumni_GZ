package com.newttl.scnualumni_gz.bean.request;

import com.newttl.scnualumni_gz.bean.request.BaseMessage;

/**
 * 链接消息  用户--->公众号
 * @author Administrator
 *
 */
public class LinkMessage extends BaseMessage {

	
	//消息标题
	private String Title;
	//消息描述
	private String Description;
	//消息链接
	private String Url;
	
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	
}
