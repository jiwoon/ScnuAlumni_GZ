package com.newttl.scnualumni_gz.bean.response;

/**
 * 音乐消息MusicMessage中的一个元素
 * @author lgc
 *
 * @date 2017年6月7日 上午10:07:52
 */
public class Music {

	//音乐标题(不是必须有的)
	private String Title;
	//音乐描述(不是必须有的)
	private String Description;
	//音乐链接(不是必须有的)
	private String MusicURL;
	//高质量音乐链接，WIFI环境优先使用该链接播放音乐(不是必须有的)
	private String HQMusicUrl;
	//缩略图的媒体id，通过素材管理中的接口上传多媒体文件，得到的id(必须有的)
	private String ThumbMediaId;
	
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
	public String getMusicURL() {
		return MusicURL;
	}
	public void setMusicURL(String musicURL) {
		MusicURL = musicURL;
	}
	public String getHQMusicUrl() {
		return HQMusicUrl;
	}
	public void setHQMusicUrl(String hQMusicUrl) {
		HQMusicUrl = hQMusicUrl;
	}
	public String getThumbMediaId() {
		return ThumbMediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}
}
