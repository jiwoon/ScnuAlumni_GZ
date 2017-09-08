package com.newttl.scnualumni_gz.bean.request;

import com.newttl.scnualumni_gz.bean.request.BaseMessage;

/**
 * 语音消息  用户--->公众号
 * @author Administrator
 *
 */
public class VoiceMessage extends BaseMessage {

	//语音消息媒体id，可以调用多媒体文件下载接口拉取该媒体
	private String MediaID;
	//语音格式：amr
	private String Format;
	//语音识别结果，UTF8编码
	private String Recognition;
	
	public String getMediaID() {
		return MediaID;
	}
	public void setMediaID(String mediaID) {
		MediaID = mediaID;
	}
	public String getFormat() {
		return Format;
	}
	public void setFormat(String format) {
		Format = format;
	}
	public String getRecognition() {
		return Recognition;
	}
	public void setRecognition(String recognition) {
		Recognition = recognition;
	}
	
}
