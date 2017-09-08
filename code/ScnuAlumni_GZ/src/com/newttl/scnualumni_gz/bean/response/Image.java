package com.newttl.scnualumni_gz.bean.response;

/**
 * 图片消息ImageMessage中的一个元素
 * @author lgc
 *
 * @date 2017年6月7日 上午9:48:32
 */
public class Image {

	//MediaId 是通过素材管理中的接口上传多媒体文件，得到的id。
	private String MediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
}
