package com.newttl.scnualumni_gz.bean.request;

import com.newttl.scnualumni_gz.bean.request.BaseMessage;

/**
 * 文本消息  用户--->公众号
 * @author Administrator
 *
 */
public class TextMessage extends BaseMessage {

	//消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
	
}
