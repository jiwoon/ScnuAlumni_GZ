package com.newttl.scnualumni_gz.bean.pojo;

/**
 * 创建永久带参数二维码时，返回的信息
 * 
 * @author lgc
 *
 * @date 2017年6月28日 下午2:44:57
 */
public class WeiXinPermanentQRCode {

	//获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码。
	private String ticket;
	//二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片
	private String url;
	
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
