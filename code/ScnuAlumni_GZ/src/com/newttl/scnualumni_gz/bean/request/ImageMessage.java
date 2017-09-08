package com.newttl.scnualumni_gz.bean.request;

import com.newttl.scnualumni_gz.bean.request.BaseMessage;

/**
 * 图片消息 用户--->公众号
 * @author Administrator
 */
public class ImageMessage extends BaseMessage {

	//图片链接（由系统生成
	private String PicUrl;
	//图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
	private String MediaId;
	
	public String getPicUrl() {
		return PicUrl;
	}
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	
}
