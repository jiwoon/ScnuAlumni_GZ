package com.newttl.scnualumni_gz.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.apache.log4j.Logger;

import com.newttl.scnualumni_gz.bean.pojo.PicModel;
import com.newttl.scnualumni_gz.bean.pojo.SNSUserInfo;
import com.newttl.scnualumni_gz.bean.pojo.WeiXinGroups;
import com.newttl.scnualumni_gz.bean.pojo.WeiXinMedia;
import com.newttl.scnualumni_gz.bean.pojo.WeiXinOauth2Token;
import com.newttl.scnualumni_gz.bean.pojo.WeiXinPermanentQRCode;
import com.newttl.scnualumni_gz.bean.pojo.WeiXinTemporaryQRCode;
import com.newttl.scnualumni_gz.bean.pojo.WeiXinUserInfo;
import com.newttl.scnualumni_gz.bean.pojo.WeiXinUserList;
import com.newttl.scnualumni_gz.bean.response.Article;
import com.newttl.scnualumni_gz.bean.response.Music;
import com.newttl.scnualumni_gz.interfaces.AdvancedInterface;
import com.newttl.scnualumni_gz.logs.ScnuAlumniLogs;
import com.newttl.scnualumni_gz.util.CommonUtil;
import com.newttl.scnualumni_gz.weixin.WeiXinCommon;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 高级接口类
 * @author lgc
 *
 * @date 2017年6月26日 下午2:35:52
 */
public class AdvancedUtil {
	
	private static Logger logger=ScnuAlumniLogs.getLogger();

	/**
	 * @return 返回 AdvancedInterface 接口的实现类对象
	 */
	public AdvancedInterface getAdvancedMethod(){
		Advanced advanced=new Advanced();
		return advanced;
	}
	
	private class Advanced implements AdvancedInterface{		
		/**
		 * 组装文本客服消息
		 * 
		 * @param openId 消息发送的对象
		 * @param content 文本内容
		 * @return String
		 */
		@Override
		public String makeTextCustomMessage(String openId,String content){
			content=content.replace("\"", "\\\"");
			String jsonMsg="{\"touser\":\"%s\",\"msgtype\":\"text\",\"text\":{\"content\":\"%s\"}}";
			return String.format(jsonMsg, openId,content);
		}
		
		/**
		 * 组装图片客服消息
		 * 
		 * @param openId 消息发送对象
		 * @param mediaId 媒体文件id
		 * @return String
		 */
		@Override
		public String makeImageCustomMessage(String openId, String mediaId) {
			String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"image\",\"image\":{\"media_id\":\"%s\"}}";
			return String.format(jsonMsg, openId, mediaId);
		}
		
		/**
		 * 组装语音客服消息
		 * 
		 * @param openId 消息发送对象
		 * @param mediaId 媒体文件id
		 * @return String
		 */
		@Override
		public String makeVoiceCustomMessage(String openId, String mediaId) {
			String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"voice\",\"voice\":{\"media_id\":\"%s\"}}";
			return String.format(jsonMsg, openId, mediaId);
		}
		
		/**
		 * 组装视频客服消息
		 * 
		 * @param openId 消息发送对象
		 * @param mediaId 媒体文件id
		 * @param thumbMediaId 视频消息缩略图的媒体id
		 * @return String
		 */
		@Override
		public String makeVideoCustomMessage(String openId, String mediaId, String thumbMediaId) {
			String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"video\",\"video\":{\"media_id\":\"%s\",\"thumb_media_id\":\"%s\"}}";
			return String.format(jsonMsg, openId, mediaId, thumbMediaId);
		}
		
		/**
		 * 组装音乐客服消息
		 * 
		 * @param openId 消息发送对象
		 * @param music 音乐对象
		 * @return String
		 */
		@Override
		public String makeMusicCustomMessage(String openId, Music music) {
			String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"music\",\"music\":%s}";
			jsonMsg = String.format(jsonMsg, openId, JSONObject.fromObject(music).toString());
			// 将jsonMsg中的thumbmediaid替换为thumb_media_id
			jsonMsg = jsonMsg.replace("thumbmediaid", "thumb_media_id");
			return jsonMsg;
		}
		
		/**
		 * 组装图文客服消息      (点击跳转到外链)
		 * 
		 * @param openId 消息发送对象
		 * @param articleList 图文消息列表
		 * @return String
		 */
		@Override
		public String makeNewsCustomMessage(String openId, List<Article> articleList) {
			String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"news\",\"news\":{\"articles\":%s}}";
			jsonMsg = String.format(jsonMsg, openId, JSONArray.fromObject(articleList).toString().replaceAll("\"", "\\\""));
			// 将jsonMsg中的picUrl替换为picurl
			jsonMsg = jsonMsg.replace("picUrl", "picurl");
			return jsonMsg;
		}
		
		/**
		 * 组装图文客服消息  (跳转到图文页面)
		 * 
		 * @param openId  消息发送对象
		 * @param mediaId 媒体文件id
		 * @return String
		 */
		@Override
		public String makeMpNewsCustomMessage(String openId,String mediaId){
			String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"mpnews\",\"mpnews\":{\"media_id\":\"%s\"}}";
			return String.format(jsonMsg, openId, mediaId);
		}
		
		/**
		 * 组装卡券客服消息
		 * 
		 * @param openId  消息发送对象
		 * @param cardId  卡卷id
		 * @return String
		 */
		@Override
		public String makeWxCardCustomMessage(String openId,String cardId){
			String jsonMsg = "{\"touser\":\"%s\",\"msgtype\":\"wxcard\",\"wxcard\":{\"card_id\":\"%s\"}}";
			return String.format(jsonMsg, openId, cardId);
		}
		
		/**
		 * 发送客服消息
		 * 
		 * @param accessToken 接口访问凭证
		 * @param jsonMsg json格式的客服消息（包括touser、msgtype和消息内容）
		 * @return true | false
		 */
		@Override
		public boolean sendCustomMessage(String accessToken, String jsonMsg) {
			boolean result = false;
			// 拼接请求地址
			String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
			requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
			// 发送客服消息
			JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "POST", jsonMsg);

			if (null != jsonObject) {
				int errorCode = jsonObject.getInt("errcode");
				String errorMsg = jsonObject.getString("errmsg");
				if (0 == errorCode) {
					result = true;
					logger.debug("====客服消息发送成功====");
					logger.debug(jsonObject.toString());
				} else {
					logger.error("====客服消息发送【失败】====");
					logger.error(jsonObject.toString());
				}
			}

			return result;
		}
		
		/**
		 * 获取网页授权凭证
		 * 
		 * @param appId  公众号的 appID
		 * @param appSecret 公众号的 appsecret
		 * @param code 用户同意授权，获取的code
		 * @return WeiXinOauth2Token  返回网页授权信息类实例
		 */
		@Override
		public WeiXinOauth2Token getOauth2AccessToken(String appId,String appSecret,String code){
			logger.debug("====获取网页授权====");
			WeiXinOauth2Token wot=null;
			//拼接请求网址
			String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
			requestUrl=requestUrl.replace("APPID", appId);
			requestUrl=requestUrl.replace("SECRET", appSecret);
			requestUrl=requestUrl.replace("CODE", code);
			//获取网页授权凭证 
			JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "GET", null);
			if (jsonObject != null) {
				try {
					wot=new WeiXinOauth2Token();
					wot.setAccessToken(jsonObject.getString("access_token"));
					wot.setExpiresIn(jsonObject.getInt("expires_in"));
					wot.setOpenId(jsonObject.getString("openid"));
					wot.setRefreshToken(jsonObject.getString("refresh_token"));
					wot.setScope(jsonObject.getString("scope"));
					logger.debug("====获取网页授权成功====");
				} catch (Exception e) {
					wot=null;
					logger.error("====获取网页授权【失败】====");
					logger.error(jsonObject.toString());
					logger.error(e.toString());
				}
				
			}
			
			return wot;
		}
		
		/**
		 * 刷新网页授权凭证
		 *  
		 * @param appId 公众号的 appID
		 * @param refreshToken  公众号的 appsecret
		 * @return WeiXinOauth2Token  返回网页授权信息类实例
		 */
		@Override
		public WeiXinOauth2Token refreshOauth2AccessToken(String appId,String refreshToken){
			logger.debug("====刷新网页授权====");
			WeiXinOauth2Token wot=null;
			//拼接请求网址 
			String requestUrl="https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
			requestUrl=requestUrl.replace("APPID", appId);
			requestUrl=requestUrl.replace("REFRESH_TOKEN", refreshToken);
			//刷新网页授权凭证 
			JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "GET", null);
			if (jsonObject != null) {
				try {
					wot=new WeiXinOauth2Token();
					wot.setAccessToken(jsonObject.getString("access_token"));
					wot.setExpiresIn(jsonObject.getInt("expires_in"));
					wot.setOpenId(jsonObject.getString("openid"));
					wot.setRefreshToken(jsonObject.getString("refresh_token"));
					wot.setScope(jsonObject.getString("scope"));
					logger.debug("====刷新网页授权成功====");
				} catch (Exception e) {
					wot=null;
					logger.error("====刷新网页授权【失败】====");
					logger.error(jsonObject.toString());
					logger.error(e.toString());
				}
				
			}
			
			return wot;
		}
		
		/**
		 * 通过网页授权拉取用户信息
		 * 
		 * @param accessToken  网页授权接口调用凭证
		 * @param openId 用户的唯一标识
		 * @param language 返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
		 * @return SNSUserInfo 返回用户实例对象
		 */
		@Override
		public SNSUserInfo  getSNSUserInfo(String accessToken,String openId,String language){
			logger.debug("====网页授权拉取用户信息====");
			SNSUserInfo user=null;
			//拼接请求网址      lang 表示返回国家地区语音版本   zh_CN 简体中文 ，zh_TW 繁体 中文 ，en 英语
			String requestUrl="https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
			requestUrl=requestUrl.replace("ACCESS_TOKEN", accessToken);
			requestUrl=requestUrl.replace("OPENID", openId);
			requestUrl=requestUrl.replace("zh_CN", language);
			//获取用户信息
			JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "GET", null);
			if (jsonObject !=null) {
				try {
					user=new SNSUserInfo();
					user.setOpenId(jsonObject.getString("openid"));
					user.setNickName(jsonObject.getString("nickname"));
					user.setSex(jsonObject.getInt("sex"));
					user.setProvince(jsonObject.getString("province"));
					user.setCity(jsonObject.getString("city"));
					user.setCountry(jsonObject.getString("country"));
					user.setHeadImgUrl(jsonObject.getString("headimgurl"));
					user.setPrivilegeList(JSONArray.toList(jsonObject.getJSONArray("privilege"),List.class));
					logger.debug("====拉取用户信息成功====");
				} catch (Exception e) {
					user=null;
					logger.error("====拉取用户信息【失败】====");
					logger.error(jsonObject.toString());
					logger.error(e.toString());
				}
			}
			
			return user;
		}
		
		/**
		 * 创建临时带参数二维码
		 * 
		 * @param accessToken  接口访问凭证 (CommonUtil.getToken)
		 * @param expireSeconds  该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒。
		 * @param sceneId  场景值ID，临时二维码时为32位非0整型
		 * @return WeiXinTemporaryQRCode 对象实例
		 */
		@Override
		public WeiXinTemporaryQRCode createTemporaryQRCode(String accessToken,int expireSeconds,int sceneId){
			logger.debug("====创建临时带参数二维码====");
			WeiXinTemporaryQRCode weiXinQRCode=null;
			//拼接请求网址
			String requestUrl="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKENPOST";
			requestUrl=requestUrl.replace("TOKENPOST", accessToken);
			//需要提交参数
			String jsonMsg="{\"expire_seconds\": %d, \"action_name\": \"QR_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": %d}}}";
			//获取二维码信息
			JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonMsg, expireSeconds,sceneId));
			if (jsonObject != null) {
				try {
					weiXinQRCode=new WeiXinTemporaryQRCode();
					weiXinQRCode.setTicket(jsonObject.getString("ticket"));
					weiXinQRCode.setExpireSeconds(jsonObject.getInt("expire_seconds"));
					weiXinQRCode.setUrl(jsonObject.getString("url"));
					logger.debug("====创建临时带参数二维码成功====");
				} catch (Exception e) {
					weiXinQRCode=null;
					int errcode=jsonObject.getInt("errcode");
					String errmsg=jsonObject.getString("errmsg");
					System.out.println("创建临时带参数二维码失败"+"\n"+" errcode::"+errcode+"\n"+"errorMsg::"+ errmsg);
					logger.error("====创建临时带参数二维码【失败】====");
					logger.error(jsonObject.toString());
					logger.error(e.toString());
				}
			}
			return weiXinQRCode;
			
		}
		
		/**
		 * 创建永久带参数二维码
		 * sceneId 场景值ID为  int 类型
		 * @param accessToken 接口访问凭证 (CommonUtil.getToken)
		 * @param sceneId 场景值ID 永久二维码时最大值为100000（目前参数只支持1--100000）
		 * @return WeiXinPermanentQRCode 对象实例
		 * 
		 * @请求提交 POST 的参数 如 {"action_name": "QR_LIMIT_SCENE", "action_info": {"scene": {"scene_id": 123}}}
		 */
		@Override
		public WeiXinPermanentQRCode createPermanentQRCode(String accessToken,int sceneId){
			logger.debug("====创建永久带参数二维码====");	
			WeiXinPermanentQRCode weiXinQRCode=null;
			//拼接请求网址
			String requestUrl="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKENPOST";
			requestUrl=requestUrl.replace("TOKENPOST", accessToken);
			//需要提交的参数
			String jsonMsg="{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": %d}}}";
			//获取二维码信息
			JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonMsg, sceneId));
			
			if (jsonObject != null) {
				try {
					weiXinQRCode=new WeiXinPermanentQRCode();
					weiXinQRCode.setTicket(jsonObject.getString("ticket"));
					weiXinQRCode.setUrl(jsonObject.getString("url"));
					logger.debug("====创建永久带参数二维码成功====");					
				} catch (Exception e) {
					weiXinQRCode=null;
					logger.error("====创建永久带参数二维码【失败】====");
					logger.error(jsonObject.toString());
					logger.error(e.toString());
				}
			}
			return weiXinQRCode;
			
		}
		
		/**
		 * 创建永久带参数二维码
		 * sceneStr 场景值ID为  String 类型
		 * 
		 * @param accessToken 接口访问凭证  (CommonUtil.getToken)
		 * @param sceneStr 场景值ID（字符串形式的ID），字符串类型，长度限制为1到64，仅永久二维码支持此字段   
		 * @return WeiXinPermanentQRCode 对象实例
		 * 
		 *  @请求提交 POST 的参数 如 {"action_name": "QR_LIMIT_STR_SCENE", "action_info": {"scene": {"scene_str": "123"}}}
		 */
		@Override
		public WeiXinPermanentQRCode createPermanentQRCode(String accessToken,String sceneStr){
			logger.debug("====创建永久带参数二维码成功====");	
			WeiXinPermanentQRCode weiXinQRCode=null;
			//拼接请求网址
			String requestUrl="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKENPOST";
			requestUrl=requestUrl.replace("TOKENPOST", accessToken);
			//需要提交的参数 
			String jsonMsg="{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"%s\"}}}";
			//获取二维码信息
			JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "POST", String.format(jsonMsg, sceneStr));
			if (jsonObject != null) {
				try {
					weiXinQRCode=new WeiXinPermanentQRCode();
					weiXinQRCode.setTicket(jsonObject.getString("ticket"));
					weiXinQRCode.setUrl(jsonObject.getString("url"));
					logger.debug("====创建永久带参数二维码成功====");
				} catch (Exception e) {
					weiXinQRCode=null;
					logger.error("====创建永久带参数二维码【失败】====");
					logger.error(jsonObject.toString());
					logger.error(e.toString());
				}
			}
			return weiXinQRCode;
			
		}
		
		/**
		 * 根据 ticket 获取二维码
		 * 
		 * @param ticket 二维码ticket
		 * @param savePath 保存路径
		 * @return filePath 文件名
		 */
		@Override
		public String getQRCode(String ticket,String savePath){
			logger.debug("====根据 ticket获取二维码====");
			String filePath=null;
			String fileName=null;
			//拼接请求网址
			String requestUrl="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
			//对 ticket 进行 URL 编码，以避免出现空格
			ticket=CommonUtil.urlEncodingUTF8(ticket);
			requestUrl=requestUrl.replace("TICKET", ticket);
			try {
				URL url=new URL(requestUrl);
				HttpsURLConnection connection=(HttpsURLConnection) url.openConnection();
				connection.setDoInput(true);
				connection.setRequestMethod("GET");
				if (!savePath.endsWith("/")) {
					savePath+="/";
				}
				//将 ticket 做为文件名
				//filePath=savePath+ticket+".jpg";

				filePath = savePath+"QR.jpg";

				fileName=WeiXinCommon.qrCodeRoot+ticket+".jpg";
				//将微信服务器返回的输入流，写入文件中
				InputStream in=connection.getInputStream();
				BufferedInputStream bStream=new BufferedInputStream(in);
				FileOutputStream fos=new FileOutputStream(new File(filePath));
				byte[] bytes=new byte[1024*4];
				int size=0;
				while ((size=bStream.read(bytes))!=-1) {
					fos.write(bytes, 0, size);
				}
				
				fos.close();
				bStream.close();
				in.close();
				connection.disconnect();
				logger.debug("====根据 ticket获取二维码成功====");
				logger.debug("filePath::"+filePath);
			} catch (Exception e) {
				filePath=null;
				logger.error("====根据 ticket获取二维码【失败】====");
				logger.error(e.toString());
			}
			return fileName;
		}
		
		/**
		 * 保存微信头像地址到本地 imgUrl 从微信获取的原始头像链接 path 保存的本地地址
		 * @param head_img_url
		 * @param head_img
		 * @throws Exception
		 */
		public void saveImageLocal(String head_img_url, String head_img){
			logger.debug("====保存微信头像地址到本地====");
			// 截取头像图片链接最后一个字符，把/0 换成/96，即图片像素为96 X 96
			String news = head_img_url.substring(0, head_img_url.length() - 2);
			String new_url = news + "/96";
			URL url;
			try {
				url = new URL(new_url);
				// 打开链接
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				// 设置请求方式为"GET"
				conn.setRequestMethod("GET");
				// 超时响应时间为5秒
				conn.setConnectTimeout(5 * 1000);
				// 通过输入流获取图片数据
				InputStream inStream = conn.getInputStream();
				// 得到图片的二进制数据，以二进制封装得到数据，具有通用性
				byte[] data = readInputStream(inStream);
				// new一个文件对象用来保存图片，默认保存当前工程根目录
				File imageFile = new File(head_img);
				// 创建输出流
				FileOutputStream outStream = new FileOutputStream(imageFile);
				// 写入数据
				outStream.write(data);
				// 关闭输出流
				outStream.close();
				logger.debug("====保存微信头像地址到本地【成功】====");
			} catch (Exception e) {
				logger.error("====保存微信头像地址到本地【失败】====");
				logger.error(e.toString());
			}
			
		}
		
		private byte[] readInputStream(InputStream inStream){
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
 	        //创建一个Buffer字符串  
 	        byte[] buffer = new byte[1024];  
 	        //每次读取的字符串长度，如果为-1，代表全部读取完毕  
 	        int len = 0;
	        try {  
	 	        //使用一个输入流从buffer里把数据读取出来  
				while( (len=inStream.read(buffer)) != -1 ){  
				    //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度  
				    outStream.write(buffer, 0, len);  
				}
				//关闭输入流  
		        inStream.close();  
			} catch (IOException e) {
				logger.error("====保存微信头像地址到本地【失败】====");
				logger.error(e.toString());
			}
	        //把outStream里的数据写入内存  
	        return outStream.toByteArray();  
	    }  
		
		/**
		 * 获取用户基本信息
		 * 
		 * @param accessToken 接口访问凭证  (CommonUtil.getToken)
		 * @param openId 用户的标识
		 * @return WeiXinUserInfo 返回用户信息对象实例
		 */
		@Override
		public WeiXinUserInfo getUserInfo(String accessToken,String openId){
			logger.debug("====获取用户基本信息====");
			WeiXinUserInfo userInfo=null;
			//拼接请求地址
			String requestUrl="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
			requestUrl=requestUrl.replace("ACCESS_TOKEN", accessToken);
			requestUrl=requestUrl.replaceAll("OPENID", openId);			
			//发起请求，获取用户信息
			JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "GET", null);			
			if (jsonObject != null) {
				try {
					userInfo=new WeiXinUserInfo();
					
					if (jsonObject.containsKey("unionid")) {
						userInfo.setUnionId(jsonObject.getString("unionid"));
					}else {
						userInfo.setUnionId("公众号未绑定到微信开放平台");
					}
					userInfo.setSubscribe(jsonObject.getInt("subscribe"));
					userInfo.setOpenId(jsonObject.getString("openid"));
					userInfo.setNickName(jsonObject.getString("nickname"));
					userInfo.setSex(jsonObject.getInt("sex"));
					userInfo.setCity(jsonObject.getString("city"));
					userInfo.setProvince(jsonObject.getString("province"));
					userInfo.setCountry(jsonObject.getString("country"));
					userInfo.setLanguage(jsonObject.getString("language"));
					userInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
					userInfo.setSubscribeTime(jsonObject.getString("subscribe_time"));
					userInfo.setRemark(jsonObject.getString("remark"));
					userInfo.setGroupId(jsonObject.getString("groupid"));
					logger.debug("====获取用户基本信息【成功】====");					
				} catch (Exception e) {
					userInfo=null;
					if (jsonObject.getInt("subscribe") == 0) {
						logger.error("====用户未关注该公众号！====");
					}else {
						logger.error("====获取用户基本信息【失败】====");	
					}
					logger.error(jsonObject.toString());
					logger.error(e.toString());
				}
			}
			return userInfo;
		}
		
		 
		/**
		 * 拉取关注用户列表
		 * 
		 * @param accessToken 接口凭证
		 * @param nextOpenId 第一个拉取的OPENID，不填默认从头开始拉取
		 * @return 返回WeiXinUserList实例对象
		 */
		@Override
		public WeiXinUserList getWeiXinUserList(String accessToken,String nextOpenId){
			logger.debug("====拉取关注用户列表====");
			WeiXinUserList userList=null;
			//拼接请求网址
			if (nextOpenId == null) {
				nextOpenId="";
			}
			String requestUrl="https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
			requestUrl=requestUrl.replace("ACCESS_TOKEN", accessToken);
			requestUrl=requestUrl.replace("NEXT_OPENID", nextOpenId);
			//发起请求
			JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "GET", null);
			if (jsonObject != null) {
				try {
					userList=new WeiXinUserList();
					userList.setTotal(jsonObject.getInt("total"));
					userList.setCount(jsonObject.getInt("count"));
					userList.setData(JSONArray.toList(jsonObject.getJSONObject("data").getJSONArray("openid"), List.class));
					userList.setNextOpenId(jsonObject.getString("next_openid"));
					logger.debug("====拉取关注用户列表【成功】====");
				} catch (Exception e) {
					userList=null;
					logger.error("====拉取关注用户列表【失败】====");
					logger.error(jsonObject.toString());
					logger.error(e.toString());
				}
			}
			
			return userList;
		}
		
		/**
		 * 获取用户分组信息
		 * 
		 * @param accessToken 接口凭证
		 * @return 返回 List<WeiXinGroups> 分组列表
		 */
		@Override
		public List<WeiXinGroups> getWeiXinGroups(String accessToken){
			logger.debug("====获取用户分组信息====");
			List<WeiXinGroups> groups=null;
			//拼接请求网址 https://api.weixin.qq.com/cgi-bin/tags/get?access_token=ACCESS_TOKEN
			//或者https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN
			String requestUrl="https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN";
			requestUrl=requestUrl.replace("ACCESS_TOKEN", accessToken);
			//发起请求
			JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "GET", null);
			if (jsonObject != null) {
				try {
					groups=JSONArray.toList(jsonObject.getJSONArray("groups"),WeiXinGroups.class);
					logger.debug("====获取用户分组信息【成功】====");
					logger.debug(jsonObject.toString());
				} catch (Exception e) {
					groups=null;
					logger.error("====获取用户分组信息【失败】====");
					logger.error(jsonObject.toString());
					logger.error(e.toString());
				}
			}
			return groups;
		}
		
		/**
		 * 创建分组
		 * @说明  需要 POST 数据 {"group":{"name":"groupName"}}
		 * 
		 * @param accessToken 接口凭证
		 * @param groupName 分组名称
		 * @return  WeiXinGroups 返回分组实例对象
		 */
		@Override
		public WeiXinGroups createGroup(String accessToken,String groupName){
			logger.debug("====创建分组====");
			WeiXinGroups groups=null;
			//拼接请求网址                           https://api.weixin.qq.com/cgi-bin/tags/create?access_token=ACCESS_TOKEN
			String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN";
			requestUrl=requestUrl.replace("ACCESS_TOKEN", accessToken);
			//需要提交的数据
			String postStr="{\"group\":{\"name\":\"%s\"}}";
			postStr=String.format(postStr, groupName);
			//发起请求
			JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "POST", postStr);
			if (jsonObject != null) {
				try {
					groups=new WeiXinGroups();
					groups.setId(jsonObject.getJSONObject("group").getInt("id"));
					groups.setName(jsonObject.getJSONObject("group").getString("name"));
					logger.debug("====创建分组【成功】====");
				} catch (Exception e) {
					groups=null;
					logger.error("====创建分组【失败】====");
					logger.error(jsonObject.toString());
					logger.error(e.toString());
				}
			}
			return groups;
		}
		
		/**
		 * 删除指定 id 的分组
		 * @param groupId 分组id
		 * @param accessToken 接口凭证
		 * @return true,成功|false,失败
		 */
		@Override
		public boolean deleteGroup(int groupId,String accessToken){
			logger.debug("====删除指定 id 的分组====");
			boolean result=false;
			//请求网址 https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=ACCESS_TOKEN
			String requestUrl="https://api.weixin.qq.com/cgi-bin/groups/delete?access_token=ACCESS_TOKEN";
			requestUrl=requestUrl.replace("ACCESS_TOKEN", accessToken);
			//post数据
			String jsonData="{\"group\":{\"id\" : %d}}";
			jsonData=String.format(jsonData, groupId);
			//发起请求
			JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "POST", jsonData);
			if (jsonObject != null) {
				int errcode=jsonObject.getInt("errcode");
				String errmsg=jsonObject.getString("errmsg");
				if (errcode == 0) {
					result=true;
					logger.debug("====删除指定 id 的分组【成功】====");
				}else {
					result=false;
					logger.error("====删除指定 id 的分组【失败】====");
				}
			}
			logger.debug(jsonObject.toString());
			return result;
		}
		
		/**
		 * 修改指定 id 的分组的对应名称
		 * @说明  需要 POST 数据 {"group":{"id":108,"name":"groupName"}}
		 * 
		 * @param accessToken 接口凭证
		 * @param groupId 分组的id
		 * @param groupName 修改后的分组名称
		 * @return 返回 true 表示修改成功;false 表示修改失败
		 */
		@Override
		public boolean updateGroup(String accessToken,int groupId,String groupName){
			logger.debug("====修改指定 id 的分组的对应名称====");
			boolean result=false;
			//拼接请求网址                           https://api.weixin.qq.com/cgi-bin/tags/update?access_token=ACCESS_TOKEN
			String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=ACCESS_TOKEN";
			requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
			// 需要 POST 的json数据
			String jsonData = "{\"group\": {\"id\": %d, \"name\": \"%s\"}}";
			jsonData=String.format(jsonData, groupId,groupName);
			//发起请求
			JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "POST", jsonData);
			if (jsonObject != null) {
				int errcode=jsonObject.getInt("errcode");
				String errmsg=jsonObject.getString("errmsg");
				if (errcode == 0) {
					result=true;
					logger.debug("====修改指定 id 的分组的对应名称【成功】====");
				}else {
					result=false;
					logger.error("====修改指定 id 的分组的对应名称【失败】====");
					logger.error(jsonObject.toString());
				}
			}
			return result;
		}
		
		/**
		 * 移动指定openId的用户到指定groupId的分组中
		 * 
		 * @param accessToken 接口凭证
		 * @param opendId 用户标识
		 * @param groupId 分组的id
		 * @return true 表示移动成功,false 表示移动失败
		 * 
		 * @说明 需要提交的json数据  "{\"openid\":\"%s\",\"to_groupid\":%d}"
		 */
		@Override
		public boolean moveMemberGroup(String accessToken,String openId,int groupId){
			logger.debug("====移动指定openId的用户到指定groupId的分组中====");
			boolean result=false;
			// 拼接请求地址
			String requestUrl = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=ACCESS_TOKEN";
			requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken);
			// 需要提交的json数据
			String jsonData = "{\"openid\":\"%s\",\"to_groupid\":%d}";
			jsonData=String.format(jsonData, openId,groupId);
			//发起请求
			JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "POST", jsonData);
			if (jsonObject != null) {
				int errcode=jsonObject.getInt("errcode");
				String errmsg=jsonObject.getString("errmsg");
				if (errcode == 0) {
					result=true;
					logger.debug("====移动指定openId的用户到指定groupId的分组中【成功】====");
				}else {
					result=false;
					logger.error("====移动指定openId的用户到指定groupId的分组中【失败】====");
					logger.error(jsonObject.toString());
				}
			}
			return result;
		}
		
		//创建个性化菜单
		//https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token=ACCESS_TOKEN
		/*public String createPersonalMenu(){
			
		}*/
		
		/**
		 * 从微信服务器上下载对应 mediaId 的媒体文件
		 * 
		 * @param accessToken 接口凭证
		 * @param mediaId 媒体文件id
		 * @param savePath 保存文件位置
		 * @return filePath 返回文件的路径
		 * 
		 * @说明 可下载用户发送过来的媒体文件
		 */
		@Override
		public String downLoadMedia(String accessToken, String mediaId, String savePath){
			logger.debug("====从微信服务器上下载对应 mediaId 的媒体文件====");
			String filePath=null;
			// 拼接请求地址
			String requestUrl = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
			requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("MEDIA_ID", mediaId);
			try {
				URL url=new URL(requestUrl);
				HttpURLConnection connection=(HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.setDoInput(true);
				if (!savePath.endsWith("/")) {
					savePath+="/";
				}
				//根据内容类型获取文件扩展名，做为文件名
				String fileExt=CommonUtil.getFileExt(connection.getHeaderField("Content-Type"));
				filePath=savePath+mediaId+fileExt;
				
				InputStream inputStream=connection.getInputStream();
				BufferedInputStream bis=new BufferedInputStream(inputStream);
				FileOutputStream fileOutputStream=new FileOutputStream(new File(filePath));
				int size=0;
				byte bytes[]=new byte[1024*4];
				while ((size=bis.read(bytes)) != -1) {
					fileOutputStream.write(bytes, 0, size);
				}
				//释放资源
				fileOutputStream.close();
				bis.close();
				inputStream.close();
				inputStream=null;
				connection.disconnect();
				logger.debug("====从微信服务器上下载对应 mediaId 的媒体文件【成功】====");
			} catch (Exception e) {
				filePath=null;
				logger.error("====从微信服务器上下载对应 mediaId 的媒体文件【失败】====");
				logger.debug(e.toString());
			}
			
			return filePath;
		}
		
		/**
		 * 上传图文消息素材
		 * @param articlesJson 图文消息json数据
		 * @param accessToken 接口凭证
		 * @return 返回 JSONObject 对象
		 */
		@Override
		public JSONObject uploadNewsArticles(String articlesJson,String accessToken){
			logger.debug("====上传图文消息素材====");
			JSONObject respJson=null;
			//请求地址
			String requestUrl="https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";
			requestUrl=requestUrl.replace("ACCESS_TOKEN", accessToken);
			try {
				//发起请求
				JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "POST", articlesJson);
				respJson=jsonObject;
				logger.debug(respJson.toString());
			} catch (Exception e) {
				respJson=null;
				logger.error("====上传图文消息素材【失败】====");
				logger.error(respJson.toString());
				logger.error(e.toString());
			}
			return respJson;
		}
		
		/**
		 * 根据标签tag_id来群发消息
		 * @param massJson 群发消息的json数据
		 * @param accessToken 接口凭证
		 * @return 返回 JSONObject 对象
		 */ 
		@Override
		public JSONObject massByTag(String massJson,String accessToken){
			logger.debug("====根据标签tag_id来群发消息====");
			JSONObject respJson=null;
			//请求地址
			String requestUrl="https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
			requestUrl=requestUrl.replace("ACCESS_TOKEN", accessToken);
			try {
				//发起请求
				JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "POST", massJson);
				respJson=jsonObject;
				logger.debug(respJson.toString());
			} catch (Exception e) {
				respJson=null;
				logger.error("====根据标签tag_id来群发消息【失败】====");
				logger.error(respJson.toString());
				logger.error(e.toString());
			}
			return respJson;
		}
		
		/**
		 * 根据openid列表来群发消息
		 * @param massJson 群发消息的json数据
		 * @param accessToken 接口凭证
		 * @return 返回 JSONObject 对象
		 */
		@Override
		public JSONObject massByOpenIdList(String massJson,String accessToken){
			logger.debug("====根据openid列表来群发消息====");
			JSONObject respJson=null;
			//请求地址
			String requestUrl="https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";
			requestUrl=requestUrl.replace("ACCESS_TOKEN", accessToken);
			try {
				//发起请求
				JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "POST", massJson);
				respJson=jsonObject;
				logger.debug(respJson.toString());
			} catch (Exception e) {
				respJson=null;
				logger.debug("====根据openid列表来群发消息【失败】====");
				logger.error(respJson.toString());
				logger.error(e.toString());
			}
			return respJson;
		}
		
		/**
		 * 指定用户预览群发消息
		 * @param previewJson 预览接口的post数据
		 * @param accessToken 接口凭证
		 * @return 返回JSONObject对象 
		 */
		@Override
		public JSONObject massOpenIdpreview(String previewJson,String accessToken){
			logger.debug("====指定用户预览群发消息====");
			JSONObject respJson=null;
			//请求地址
			String requestUrl="https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=ACCESS_TOKEN";
			requestUrl=requestUrl.replace("ACCESS_TOKEN", accessToken);
			try {
				//发起请求
				JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "POST", previewJson);
				respJson=jsonObject;
				logger.debug(respJson.toString());
			} catch (Exception e) {
				respJson=null;
				logger.error("====指定用户预览群发消息【失败】====");
				logger.error(respJson.toString());
				logger.error(e.toString());
			}
			return respJson;
		}
		
		/**
		 * 上传群发接口图文消息中的图片
		 * @param accessToken 接口凭证
		 * @param mediaFileUrl 图片的原始路径(图片为jpg/png格式)
		 * @return 返回 JSONObject 对象
		 */
		@Override
		public JSONObject uploadNewsImage(String accessToken,String mediaFileUrl) {
			logger.debug("====上传群发接口图文消息中的图片====");
			JSONObject respJson=null;
			// 拼装请求地址
			String uploadMediaUrl = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";
			uploadMediaUrl = uploadMediaUrl.replace("ACCESS_TOKEN", accessToken);

			// 定义数据分隔符
			String boundary = "------------7da2e536604c8";
			try {
				URL uploadUrl = new URL(uploadMediaUrl);
				HttpURLConnection uploadConn = (HttpURLConnection) uploadUrl.openConnection();
				uploadConn.setDoOutput(true);
				uploadConn.setDoInput(true);
				uploadConn.setRequestMethod("POST");
				// 设置请求头Content-Type
				uploadConn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
				// 获取媒体文件上传的输出流（往微信服务器写数据）
				OutputStream outputStream = uploadConn.getOutputStream();

				URL mediaUrl = new URL(mediaFileUrl);
				HttpURLConnection meidaConn = (HttpURLConnection) mediaUrl.openConnection();
				meidaConn.setDoOutput(true);
				meidaConn.setRequestMethod("GET");

				// 从请求头中获取内容类型
				String contentType = meidaConn.getHeaderField("Content-Type");
				// 根据内容类型判断文件扩展名
				String fileExt = CommonUtil.getFileExt(contentType);
				// 请求体开始
				outputStream.write(("--" + boundary + "\r\n").getBytes());
				outputStream.write(
						String.format("Content-Disposition: form-data; name=\"media\"; filename=\"file1%s\"\r\n", fileExt)
								.getBytes());
				outputStream.write(String.format("Content-Type: %s\r\n\r\n", contentType).getBytes());

				// 获取媒体文件的输入流（读取文件）
				BufferedInputStream bis = new BufferedInputStream(meidaConn.getInputStream());
				byte[] buf = new byte[8096];
				int size = 0;
				while ((size = bis.read(buf)) != -1) {
					// 将媒体文件写到输出流（往微信服务器写数据）
					outputStream.write(buf, 0, size);
				}
				// 请求体结束
				outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());
				outputStream.close();
				bis.close();
				meidaConn.disconnect();

				// 获取媒体文件上传的输入流（从微信服务器读数据）
				InputStream inputStream = uploadConn.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				StringBuffer buffer = new StringBuffer();
				String str = null;
				while ((str = bufferedReader.readLine()) != null) {
					buffer.append(str);
				}
				bufferedReader.close();
				inputStreamReader.close();
				// 释放资源
				inputStream.close();
				inputStream = null;
				uploadConn.disconnect();

				// 使用JSON-lib解析返回结果
				JSONObject jsonObject = JSONObject.fromObject(buffer.toString());
				respJson=jsonObject;
				logger.debug(respJson.toString());
			} catch (Exception e) {
				respJson = null;
				logger.error("====上传群发接口图文消息中的图片【失败】====");
				logger.error(respJson.toString());
				logger.error(e);
			}
			return respJson;
		}
		
		/**
		 * 上传媒体文件(如图文消息的封面)
		 * 
		 * @param accessToken
		 *            接口访问凭证
		 * @param type
		 *            媒体文件类型（image、voice、video和thumb）
		 * @param mediaFileUrl
		 *            媒体文件的url
		 */
		public WeiXinMedia uploadMedia(String accessToken, String type, String mediaFileUrl) {
			logger.debug("====上传媒体文件(如图文消息的封面)====");
			WeiXinMedia weixinMedia = null;
			// 拼装请求地址
			String uploadMediaUrl = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
			uploadMediaUrl = uploadMediaUrl.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);

			// 定义数据分隔符
			String boundary = "------------7da2e536604c8";
			try {
				URL uploadUrl = new URL(uploadMediaUrl);
				HttpURLConnection uploadConn = (HttpURLConnection) uploadUrl.openConnection();
				uploadConn.setDoOutput(true);
				uploadConn.setDoInput(true);
				uploadConn.setRequestMethod("POST");
				// 设置请求头Content-Type
				uploadConn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
				// 获取媒体文件上传的输出流（往微信服务器写数据）
				OutputStream outputStream = uploadConn.getOutputStream();

				URL mediaUrl = new URL(mediaFileUrl);
				HttpURLConnection meidaConn = (HttpURLConnection) mediaUrl.openConnection();
				meidaConn.setDoOutput(true);
				meidaConn.setRequestMethod("GET");

				// 从请求头中获取内容类型
				String contentType = meidaConn.getHeaderField("Content-Type");
				// 根据内容类型判断文件扩展名
				String fileExt = CommonUtil.getFileExt(contentType);
				// 请求体开始
				outputStream.write(("--" + boundary + "\r\n").getBytes());
				outputStream.write(
						String.format("Content-Disposition: form-data; name=\"media\"; filename=\"file1%s\"\r\n", fileExt)
								.getBytes());
				outputStream.write(String.format("Content-Type: %s\r\n\r\n", contentType).getBytes());

				// 获取媒体文件的输入流（读取文件）
				BufferedInputStream bis = new BufferedInputStream(meidaConn.getInputStream());
				byte[] buf = new byte[8096];
				int size = 0;
				while ((size = bis.read(buf)) != -1) {
					// 将媒体文件写到输出流（往微信服务器写数据）
					outputStream.write(buf, 0, size);
				}
				// 请求体结束
				outputStream.write(("\r\n--" + boundary + "--\r\n").getBytes());
				outputStream.close();
				bis.close();
				meidaConn.disconnect();

				// 获取媒体文件上传的输入流（从微信服务器读数据）
				InputStream inputStream = uploadConn.getInputStream();
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				StringBuffer buffer = new StringBuffer();
				String str = null;
				while ((str = bufferedReader.readLine()) != null) {
					buffer.append(str);
				}
				bufferedReader.close();
				inputStreamReader.close();
				// 释放资源
				inputStream.close();
				inputStream = null;
				uploadConn.disconnect();

				// 使用JSON-lib解析返回结果
				JSONObject jsonObject = JSONObject.fromObject(buffer.toString());
				weixinMedia = new WeiXinMedia();
				weixinMedia.setMediaType(jsonObject.getString("type"));
				// type等于thumb时的返回结果和其它类型不一样
				if ("thumb".equals(type))
					weixinMedia.setMediaId(jsonObject.getString("thumb_media_id"));
				else
					weixinMedia.setMediaId(jsonObject.getString("media_id"));
				weixinMedia.setCreateAt(jsonObject.getInt("created_at"));
			} catch (Exception e) {
				weixinMedia = null;
				logger.error("====上传媒体文件(如图文消息的封面)【失败】====");
				logger.error(e.toString());
			}
			return weixinMedia;
		}
		
		/**
		 * 获取活动海报的media_id
		 * @param String content 海报内容
		 * @param String accessToken 公众号凭证
		 * @return WeiXinMedia.getMediaId()
		 */
		public String getActivityImgId(String content,String accessToken) throws Exception {
			logger.debug("====获取活动海报的media_id====");
			/*
			 * 提取活动邀请海报模板图片 的路径
			 */
			String path =WeiXinCommon.ImgUrl+"/InviteModel.jpg";

			/*
			 * 生成活动海报
			 */
			PicModel.MakeActivityImg(content, path);
			/**
			 * 上传多媒体文件 需要上传URL链接 而不是本地图片地址
			 */
			WeiXinMedia weixinMedia = uploadMedia(accessToken, "image", WeiXinCommon.activityImgUrl);

			return weixinMedia.getMediaId();
		}

		/**
		 * 获取二维码的media_id
		 * @param String user 用户微信id
		 * @param String appID 
		 * @param String appSecret 
		 * @return string
		 */
		
		@Override
		public String getQRid(String user,String appID,String appSecret) throws Exception {
			logger.debug("====获取二维码的media_id====");
			
			String accessToken = CommonUtil.getToken().getAccess_token();
			/**  
			 * 获取用户信息，并将用户头像保存在本地
			 */
			WeiXinUserInfo username = getUserInfo(accessToken, user);
			// 获取用户的昵称
			String nickname = username.getNickName();
			// 获取用户的头像
			String head_img_url = username.getHeadImgUrl();
			//头像图片保存地址
			String head_img = WeiXinCommon.ImgUrl+"/head.jpg";
			saveImageLocal(head_img_url, head_img);

			
			// 根据ticket 获取永久二维码
			WeiXinPermanentQRCode  weixinQRCode = createPermanentQRCode(accessToken, user);

			// 临时二维码的ticket
			String ticket = weixinQRCode.getTicket();
			
			// 根据ticket换取二维码 ，并保存到 Path 路径,二维码图片名称为ticket.jpg
			String savePath = WeiXinCommon.ImgUrl;
			getQRCode(ticket, savePath);

			/**
			 * 生成专属二维码模板,拼接微信用户的头像、昵称、专属二维码在一个模板中
			 */
			PicModel.MakeImg(nickname,head_img);
			/**
			 * 上传多媒体文件 需要上传URL链接 而不是本地图片地址
			 */
			WeiXinMedia weixinMedia = uploadMedia(accessToken, "image", WeiXinCommon.QrFileUrl);
			return weixinMedia.getMediaId();
		}
		
		/**
		 * 对api调用次数清零
		 * @param accessToken 接口凭证
		 * @param appId 公众号appid
		 * @return
		 */
		public JSONObject clearQuota(String accessToken,String appId){
			logger.debug("====对api调用次数清零====");
			JSONObject respJson=null;
			String requestUrl="https://api.weixin.qq.com/cgi-bin/clear_quota?access_token=ACCESS_TOKEN";
			requestUrl=requestUrl.replace("ACCESS_TOKEN", accessToken);
			try {
				// 需要提交的json数据 
				String jsonData = "{\"appid\":\"%s\"}";
				jsonData=String.format(jsonData, appId);
				//发起请求
				JSONObject jsonObject=CommonUtil.httpsRequest(requestUrl, "POST", jsonData);
				respJson=jsonObject;
				logger.debug(respJson.toString());
			} catch (Exception e) {
				respJson=null;
				logger.error("====对api调用次数清零【失败】====");
				logger.error(respJson.toString());
				logger.error(e.toString());
			}
			return respJson;
		}
		
	}
	
}
