package com.newttl.scnualumni_gz.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import org.apache.log4j.Logger;

import com.thoughtworks.xstream.mapper.OuterClassMapper;
import com.newttl.scnualumni_gz.bean.pojo.Token;
import com.newttl.scnualumni_gz.logs.ScnuAlumniLogs;
import com.newttl.scnualumni_gz.util.MyX509TrustManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * 通用工具类
 * 
 * @author lgc
 *
 * @date 2017年6月9日 上午10:44:58
 */
public class CommonUtil {

	private static Logger logger=ScnuAlumniLogs.getLogger();
	
	// access_token凭证获取（GET）的url
	public final static String token_url = 
			"https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	
	/**
	 * 发起https请求 
	 * @param requestUrl 请求URL地址
	 * @param requstMethod 请求方法(GET,POST)
	 * @param outPutStr 提交的数据
	 * @return 返回JSONObject对象 
	 */
	public static JSONObject httpsRequest(String requestUrl,String requstMethod,String outPutStr){
		
		logger.debug("====发起https请求 ====");
		
		JSONObject jsonObject=null;

		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm={new MyX509TrustManager()};
			SSLContext sslContext=SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory sslf=sslContext.getSocketFactory();
	
			URL url=new URL(requestUrl);
			HttpsURLConnection connection=(HttpsURLConnection) url.openConnection();
			connection.setSSLSocketFactory(sslf);
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			//设置请求方式
			connection.setRequestMethod(requstMethod);
			//当outPutStr不为空时，向输出流写数据
			if (outPutStr!=null) {
				OutputStream out=connection.getOutputStream();
				// 注意编码格式
				out.write(outPutStr.getBytes("UTF-8"));
				out.close();
			}
			//获取输入流
			InputStream in=connection.getInputStream();
			//读取输入流的内容
			InputStreamReader inReader=new InputStreamReader(in,"UTF-8");//加上编码格式
			BufferedReader bReader=new BufferedReader(inReader);
			String resultStr=null;
			StringBuffer buffer=new StringBuffer();
			while ((resultStr=bReader.readLine())!=null) {
				buffer.append(resultStr);
			}
			//释放资源
			bReader.close();
			inReader.close();
			in.close();
			in=null;
			connection.disconnect();
			
			//返回JSONObject对象
			jsonObject=JSONObject.fromObject(buffer.toString());
		} catch (IOException e) {
			logger.error("====发起https请求 【失败】====");
			logger.error(e.toString());
		} catch (KeyManagementException e) {
			logger.error("====发起https请求 【失败】====");
			logger.error(e.toString());
		}catch (NoSuchAlgorithmException e) {
			logger.error("====发起https请求 【失败】====");
			logger.error(e.toString());
		} catch (NoSuchProviderException e) {
			logger.error("====发起https请求 【失败】====");
			logger.error(e.toString());
		}
		
		return jsonObject;
		
	}
	
	/**
	 * 获取凭证Token
	 * @param appID 第三方用户唯一凭证appID
	 * @param appsecret 第三方用户唯一凭证密钥appsecret
	 * @return
	 */
	public static Token getToken(String appID,String appsecret){
		logger.debug("====获取凭证Token====");
		Token token=null;
		String requestUrl=token_url.replace("APPID", appID).replace("APPSECRET", appsecret);
		//GET方法请求HTTPS，不提交数据
		JSONObject jsonObject=httpsRequest(requestUrl, "GET", null);
		if (jsonObject!=null) {
			try {
				token=new Token();
				token.setAccess_token(jsonObject.getString("access_token"));
				token.setExpires_in(jsonObject.getInt("expires_in"));
				logger.debug("====获取凭证Token【成功】====");
			} catch (JSONException  e) {
				token = null;
				logger.error("====获取凭证Token【失败】====");
				logger.error(jsonObject.toString());
				logger.error(e.toString());
			}
		}
		
		
		return token;
		
	}
	
	/**
	 * url 编码 UTF-8
	 * @param url
	 * @return 返回编码后的 url
	 */
	public static String urlEncodingUTF8(String url){
		String result=url;
		try {
			result=URLEncoder.encode(result, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 根据内容类型判断文件扩展名
	 * 
	 * @param contentType 内容类型
	 * @return fileExt 返回文件后缀名
	 */
	public static String getFileExt(String contentType) {
		String fileExt = "";
		if ("image/jpeg".equals(contentType))
			fileExt = ".jpg";
		else if ("audio/mpeg".equals(contentType))
			fileExt = ".mp3";
		else if ("audio/amr".equals(contentType))
			fileExt = ".amr";
		else if ("video/mp4".equals(contentType))
			fileExt = ".mp4";
		else if ("video/mpeg4".equals(contentType))
			fileExt = ".mp4";
		return fileExt;
	}
	
	/**
	 * 获取随机uuid
	 * @return
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String str = uuid.toString();
		// 去掉"-"符号
		String temp = str.substring(0, 8) + str.substring(9, 13) + str.substring(14, 18) + str.substring(19, 23) + str.substring(24);
		return temp;
	}
	
}
