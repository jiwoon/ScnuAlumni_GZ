package com.newttl.scnualumni_gz.bean.request;

/**
 * 消息基类    用户--->公众号
 * @author Administrator
 * 
 */

public class BaseMessage {

	//开发者微信号
	private String ToUserName;
	//发送方微信号(一个OpenID 比如：oA1HcvwJj9KKuPC6fmRWm8h6Qv4I)
	private String FromUserName;
	//创建时间
	private long CreateTime;
	//消息类型(文本text/图片image/语音为voice/视频为video/小视频为shortvideo/位置location/链接link/事件event)
	private String MsgType;
	//消息id，64位整型
	private long MsgId;
	
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
	public long getMsgId() {
		return MsgId;
	}
	public void setMsgId(long msgId) {
		MsgId = msgId;
	}
	
	
}
