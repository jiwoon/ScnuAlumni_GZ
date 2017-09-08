package com.newttl.scnualumni_gz.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

/**
 * 服务器证书信任管理器
 * 
 * @author lgc
 *
 * @date 2017年6月8日 下午3:53:44
 */

public class MyX509TrustManager implements X509TrustManager {

	/**
	 * 检查客户端证书
	 */
	@Override
	public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		// TODO Auto-generated method stub

	}

	/**
	 * 检查服务器证书
	 */
	@Override
	public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		// TODO Auto-generated method stub

	}

	/**
	 * 返回受信任的X509数组
	 */
	@Override
	public X509Certificate[] getAcceptedIssuers() {
		// TODO Auto-generated method stub
		return null;
	}


}
