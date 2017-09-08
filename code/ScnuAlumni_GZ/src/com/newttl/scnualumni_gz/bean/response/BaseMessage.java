package com.newttl.scnualumni_gz.bean.response;

/**
 * 消息基类    公众号--->用户
 * @author lgc
 *
 * @date 2017年6月7日 上午9:03:32
 */
public class BaseMessage {

	//接收方微信号(一个OpenID 比如：oA1HcvwJj9KKuPC6fmRWm8h6Qv4I)
	private String ToUserName;
	//开发者微信号
	private String FromUserName;
	//创建时间
	private long CreateTime;
	//消息类型(文本text/图片image/语音为voice/视频为video/音乐music/图文news)
	private String MsgType;
	
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

}
