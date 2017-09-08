package com.newttl.scnualumni_gz.bean.response;

import com.newttl.scnualumni_gz.bean.response.BaseMessage;
import com.newttl.scnualumni_gz.bean.response.Voice;

/**
 * 语音消息
 * @author lgc
 *
 * @date 2017年6月7日 上午9:56:49
 */
public class VoiceMessage extends BaseMessage {

	private Voice Voice;

	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		Voice = voice;
	}

	
}
