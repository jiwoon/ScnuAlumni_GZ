package com.newttl.scnualumni_gz.bean.menu;

import com.newttl.scnualumni_gz.bean.menu.Button;

/**
 * scancode_waitmsg 扫码带提示
 * 扫码推事件且弹出“消息接收中”提示框用户点击按钮后，微信客户端将调起扫一扫工具，
 * 完成扫码操作后，将扫码的结果传给开发者，同时收起扫一扫工具，
 * 然后弹出“消息接收中”提示框，随后可能会收到开发者下发的消息。
 * 
 * (注)仅支持微信iPhone5.4.1以上版本，和Android5.4以上版本的微信用户，旧版本微信用户点击后将没有回应，开发者也不能正常接收到事件推送
 * 
 * @author lgc
 *
 * @date 2017年6月9日 上午8:39:42
 */
public class Scancode_Waitmsg extends Button {

	//scancode_waitmsg
	private String type;
	//rselfmenu_0_0
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
