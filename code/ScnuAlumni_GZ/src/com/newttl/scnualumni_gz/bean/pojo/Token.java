package com.newttl.scnualumni_gz.bean.pojo;

/**
 * 公众号凭证
 * 进行HTTPS请求之后，微信服务器将返回凭证的json数据
 * 如：{"access_token":"ACCESS_TOKEN","expires_in":7200}
 * @author lgc
 *
 * @date 2017年6月9日 上午11:44:18
 */
public class Token {

	//获取到的凭证
	private String access_token;
	//凭证有效时间，单位：秒
	private int expires_in;
	
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
	
}
