package com.newttl.scnualumni_gz.bean.event;

import com.newttl.scnualumni_gz.bean.event.BaseEvent;

/**
 * 扫描带参数二维码事件
 * 1、用户未关注时，event为  subscribe
 * 2、用户已关注时，event为  SCAN
 * @author Administrator
 *
 */
public class QRCodeEvent extends BaseEvent {

	/*EventKey
	 * 
	 * 1、event为  ： subscribe 时
	 * 事件KEY值，qrscene_为前缀，后面为二维码的参数值
	 * 2、event为  ： SCAN 时
	 * 事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
	 * 
	 */
	private String EventKey;
	//二维码的ticket，可用来换取二维码图片
	private String Ticket;
	
	public String getEventKey() {
		return EventKey;
	}
	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
	public String getTicket() {
		return Ticket;
	}
	public void setTicket(String ticket) {
		Ticket = ticket;
	}
	
}
