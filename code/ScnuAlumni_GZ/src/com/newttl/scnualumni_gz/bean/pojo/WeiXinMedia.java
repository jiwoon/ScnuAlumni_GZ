package com.newttl.scnualumni_gz.bean.pojo;

/**
 * 微信临时媒体文件(素材)
 * 
 * @author lgc
 *
 * @date 2017年7月6日 上午9:57:22
 */
public class WeiXinMedia {

	//媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb，主要用于视频与音乐格式的缩略图）
	private String mediaType;
	//媒体文件id
	private String mediaId;
	//媒体文件创建时间
	private int createAt;
	
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public int getCreateAt() {
		return createAt;
	}
	public void setCreateAt(int createAt) {
		this.createAt = createAt;
	}
	
	public String getWeixinMedia(){
		return "mediaType::"+getMediaType()+"\n"+"mediaId::"+getMediaId()+"\n"+"createAt::"+getCreateAt();
	}
	
}
