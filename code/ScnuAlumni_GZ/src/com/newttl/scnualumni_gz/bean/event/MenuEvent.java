package com.newttl.scnualumni_gz.bean.event;

import com.newttl.scnualumni_gz.bean.event.BaseEvent;

/**
 * 点击菜单事件(指的是最终菜单，因为点击菜单弹出子菜单，不会产生上报)
 * @author Administrator
 *
 */
public class MenuEvent extends BaseEvent {

	/*
	 * EventKey
	 * 
	 * 1、Event为 ： CLICK时
	 * 事件KEY值，与自定义菜单接口中KEY值对应
	 * 2、Event为 ： VIEW时
	 * 事件KEY值，设置的跳转URL
	 */
	
	private String EventKey;

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
	
	
}
