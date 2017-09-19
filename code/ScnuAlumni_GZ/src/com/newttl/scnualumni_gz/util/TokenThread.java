package com.newttl.scnualumni_gz.util;

import org.apache.log4j.Logger;

import com.newttl.scnualumni_gz.bean.pojo.Token;
import com.newttl.scnualumni_gz.logs.ScnuAlumniLogs;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * 获取access_token的子线程
 * @author lgc
 *
 * 2017年9月18日 下午7:16:55
 */
public class TokenThread implements Runnable {

	private static Logger logger=ScnuAlumniLogs.getLogger();
	// access_token凭证获取（GET）的url
	private final static String token_url = 
				"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	public static String appId="";
	public static String appSecret="";
	public static Token mToken=null;
	@Override
	public void run() {
		while(true){
			try {
				mToken=this.getToken();
				if (null != mToken) {
					logger.debug("获取凭证Token【成功】::"+mToken.getAccess_token());
					Thread.sleep(7000*1000);//获取到access_token,休眠7000s
				}else {
					Thread.sleep(1000*3);//获取不到access_token,休眠3s
				}
			} catch (Exception e) {
				logger.error("获取凭证Token【异常】::"+e.toString());
				/*e.printStackTrace();*/
				try {
					Thread.sleep(1000*10);//发生异常,休眠10s
				} catch (InterruptedException e1) {
					logger.error("【发生异常,休眠10s】::"+e.toString());
				}
			}
		}
	}
	
	/**
	 * 获取接口凭证Token实例
	 * @return
	 */
	private Token getToken() throws JSONException{
		logger.debug("====获取凭证Token====");
		Token token=null;
		String requestUrl=token_url.replace("APPID", appId).replace("APPSECRET", appSecret);
		JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "GET", null);
		if (jsonObject!=null) {
			logger.debug("获取凭证Token-【jsonObject】::"+jsonObject.toString());
			token=new Token();
			token.setAccess_token(jsonObject.getString("access_token"));
			token.setExpires_in(jsonObject.getInt("expires_in"));
		}
		return token;
	}

}
