package com.newttl.scnualumni_gz.bean.menu;

import com.newttl.scnualumni_gz.bean.menu.Button;

/**
 * 点击跳转网页按钮
 * @author lgc
 *
 * @date 2017年6月8日 下午5:39:21
 */
public class ViewButton extends Button {

	private String type;
	private String url;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
