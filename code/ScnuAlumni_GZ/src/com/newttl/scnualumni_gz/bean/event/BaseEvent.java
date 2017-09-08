package com.newttl.scnualumni_gz.bean.event;

/**
 * 事件基类   用户---->公众号
 * @author lgc
 * 
 *
 */
public class BaseEvent {
	
	//开发者微信号
	private String ToUserName;
	//发送方微信号(一个OpenID 比如：oA1HcvwJj9KKuPC6fmRWm8h6Qv4I)
	private String FromUserName;
	//创建时间
	private long CreateTime;
	//消息类型(event)
	private String MsgType;
	//事件类型
    //subscribe(订阅)、unsubscribe(取消订阅)、SCAN(用户已关注 时扫描二维码)、
	//LOCATION(上报位置)、CLICK(点击菜单拉取消息)、VIEW(点击菜单跳转链接)
	private String Event;
		
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
	public String getEvent() {
		return Event;
	}
	public void setEvent(String event) {
		Event = event;
	}
	
	
	
}
