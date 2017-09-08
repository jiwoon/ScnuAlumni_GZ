package com.newttl.scnualumni_gz.bean.menu;

import com.newttl.scnualumni_gz.bean.menu.Button;

/**
 * 点击拉取消息按钮
 * @author lgc
 *
 * @date 2017年6月8日 下午5:36:10
 */
public class ClickButton extends Button {

	private String type;
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
