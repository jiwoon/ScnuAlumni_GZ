package com.newttl.scnualumni_gz.bean.response;

import com.newttl.scnualumni_gz.bean.response.BaseMessage;

/**
 * 文本消息
 * @author lgc
 *
 * @date 2017年6月7日 上午9:42:40
 */
public class TextMessage extends BaseMessage {

	//回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
	
}
