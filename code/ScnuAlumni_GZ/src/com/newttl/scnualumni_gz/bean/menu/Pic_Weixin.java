package com.newttl.scnualumni_gz.bean.menu;

import com.newttl.scnualumni_gz.bean.menu.Button;

/**
 * pic_weixin 微信相册发图
 * 弹出微信相册发图器用户点击按钮后，微信客户端将调起微信相册，完成选择操作后，
 * 将选择的相片发送给开发者的服务器，并推送事件给开发者，同时收起相册，随后可能会收到开发者下发的消息。
 * 
 * (注)仅支持微信iPhone5.4.1以上版本，和Android5.4以上版本的微信用户，旧版本微信用户点击后将没有回应，开发者也不能正常接收到事件推送
 * 
 * @author lgc
 *
 * @date 2017年6月9日 上午9:02:58
 */
public class Pic_Weixin extends Button {

	//pic_weixin
	private String type;
	//rselfmenu_1_2
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
