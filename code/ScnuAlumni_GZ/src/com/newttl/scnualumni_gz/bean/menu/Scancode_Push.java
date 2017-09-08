package com.newttl.scnualumni_gz.bean.menu;

import com.newttl.scnualumni_gz.bean.menu.Button;

/**
 * scancode_push 扫码推事件
 * 扫码推事件用户点击按钮后，微信客户端将调起扫一扫工具，
 * 完成扫码操作后显示扫描结果（如果是URL，将进入URL），且会将扫码的结果传给开发者，开发者可以下发消息。
 * 
 * (注)仅支持微信iPhone5.4.1以上版本，和Android5.4以上版本的微信用户，旧版本微信用户点击后将没有回应，开发者也不能正常接收到事件推送
 * 
 * @author lgc
 *
 * @date 2017年6月9日 上午8:47:23
 */
public class Scancode_Push extends Button {

	//scancode_push
	private String type;
	//rselfmenu_0_1
	private String key;
	private Button[] sub_button;
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
	public Button[] getSub_button() {
		return sub_button;
	}
	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}
	
}
