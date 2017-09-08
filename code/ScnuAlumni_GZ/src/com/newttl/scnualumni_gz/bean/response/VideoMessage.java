package com.newttl.scnualumni_gz.bean.response;

import com.newttl.scnualumni_gz.bean.response.BaseMessage;
import com.newttl.scnualumni_gz.bean.response.Video;

/**
 * 视频消息
 * @author lgc
 *
 * @date 2017年6月7日 上午9:58:24
 */
public class VideoMessage extends BaseMessage {
	
	private Video Video;

	public Video getVideo() {
		return Video;
	}

	public void setVideo(Video video) {
		this.Video = video;
	}
	

}
