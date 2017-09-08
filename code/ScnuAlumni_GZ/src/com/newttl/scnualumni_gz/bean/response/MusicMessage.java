package com.newttl.scnualumni_gz.bean.response;

import com.newttl.scnualumni_gz.bean.response.BaseMessage;
import com.newttl.scnualumni_gz.bean.response.Music;

/**
 * 音乐消息
 * @author lgc
 *
 * @date 2017年6月7日 上午10:20:23
 */
public class MusicMessage extends BaseMessage {

	private Music Music;

	public Music getMusic() {
		return Music;
	}

	public void setMusic(Music music) {
		this.Music = music;
	}
	
}
