package com.newttl.scnualumni_gz.bean.menu;

import com.newttl.scnualumni_gz.bean.menu.Button;

/**
 * location_select 发送位置
 * 弹出地理位置选择器用户点击按钮后，微信客户端将调起地理位置选择工具，完成选择操作后，
 * 将选择的地理位置发送给开发者的服务器，同时收起位置选择工具，随后可能会收到开发者下发的消息
 * 
 * (注)仅支持微信iPhone5.4.1以上版本，和Android5.4以上版本的微信用户，旧版本微信用户点击后将没有回应，开发者也不能正常接收到事件推送
 * 
 * @author lgc
 *
 * @date 2017年6月9日 上午9:08:46
 */
public class Location_Select extends Button {

	//location_select
	private String type;
	//rselfmenu_2_0
	private String key;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	
	
}
