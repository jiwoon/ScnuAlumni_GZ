package com.newttl.scnualumni_gz.bean.menu;

import com.newttl.scnualumni_gz.bean.menu.Button;

/**
 * pic_photo_or_album 拍照或者相册发图
 * 弹出拍照或者相册发图用户点击按钮后，微信客户端将弹出选择器供用户选择“拍照”或者“从手机相册选择”。
 * 用户选择后即走其他两种流程。
 * 
 * (注)仅支持微信iPhone5.4.1以上版本，和Android5.4以上版本的微信用户，旧版本微信用户点击后将没有回应，开发者也不能正常接收到事件推送
 * 
 * @author lgc
 *
 * @date 2017年6月9日 上午8:59:12
 */
public class Pic_Photo_Or_Album extends Button {

	//pic_photo_or_album
	private String type;
	//rselfmenu_1_1
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
