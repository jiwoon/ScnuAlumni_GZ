package com.newttl.scnualumni_gz.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.newttl.scnualumni_gz.bean.pojo.Token;
import com.newttl.scnualumni_gz.bean.pojo.WeiXinGroups;
import com.newttl.scnualumni_gz.bean.pojo.WeiXinMedia;
import com.newttl.scnualumni_gz.logs.ScnuAlumniLogs;
import com.newttl.scnualumni_gz.util.AdvancedUtil;
import com.newttl.scnualumni_gz.util.CommonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HelloWorld {
	
	/**
	 * 公众号的  appID 和 appsecret
	 */
	private static final String appID="wxdfead60d5e0dbcd6";
	private static final String appsecret="92663408c73b91e5749a0c5de20bd953";
	
	//其中一个用户的 openId
	private static final String user_openId="oA1HcvwJj9KKuPC6fmRWm8h6Qv4I";
	private static final String my_openId="oA1Hcv9PfGShFQfsHXEdjQrPGPmQ";
	
	public static void main(String[] args) {
		
		//获取接口凭证
		Token token=CommonUtil.getToken(appID, appsecret);
		String access_token=token.getAccess_token();
		System.out.println("access_token::"+access_token);
		
		//上传图文消息的图片
		String newsImageUrl="http://jqfrudd.hk1.mofasuidao.cn/image/InviteModel2.jpg";
		AdvancedUtil advancedUtil=new AdvancedUtil();
		//清零api调用次数
//		advancedUtil.getAdvancedMethod().clearQuota(access_token, appID);
		
		
//		WeiXinGroups group=advancedUtil.getAdvancedMethod().createGroup(access_token, "test912");
//		advancedUtil.getAdvancedMethod().moveMemberGroup(access_token, "oA1Hcv9PfGShFQfsHXEdjQrPGPmQ", group.getId());
//		advancedUtil.getAdvancedMethod().moveMemberGroup(access_token, "oA1HcvwuqMAZgdi6l8hdKV48OCJE", group.getId());
//		String url=advancedUtil.getAdvancedMethod().uploadNewsImage(access_token, newsImageUrl).toString();
		
		//上传图文消息缩略图，获取其media_id
		WeiXinMedia media=advancedUtil.getAdvancedMethod()
							.uploadMedia(access_token, "image", "http://jqfrudd.hk1.mofasuidao.cn/image/haha.jpg");
		String mediaId=media.getMediaId();
//		http://mmbiz.qpic.cn/mmbiz_jpg/jGw8UaGJmVR0RYB6BcpIWS07UhVJck03SFBNaNaaicwCbrueMHm5kjRXTiaH3kjrAUCA7pqyJX0xolA710apljvA/0
//		JSONObject  newsImageJson=advancedUtil.getAdvancedMethod().uploadNewsImage(access_token, "http://jqfrudd.hk1.mofasuidao.cn/image/henhen.jpg");
//		String newsImgUrl=newsImageJson.getString("url");
		String newsImgUrl="http://mmbiz.qpic.cn/mmbiz_jpg/jGw8UaGJmVR0RYB6BcpIWS07UhVJck03SFBNaNaaicwCbrueMHm5kjRXTiaH3kjrAUCA7pqyJX0xolA710apljvA/0";
		try {
			//上传图文消息
			
			String contentHtml="<P>图文消息页面的内容，支持HTML标签。具备微信支付权限的公众号，可以使用a标签，其他公众号不能使用，如需插入小程序卡片，可参考下文"
					+ "<br>上面可能没描述清楚问题，下面是详细的信息。谢谢你的解答哦。"
					+ "功能需求：我们公司需要用微信主动推送消息给特定用户。"
					+ "已实现功能：因为微信没有开放主动发送消息的接口，只好通过模拟登陆来实现了。现在已经能主动推送文本消息给指定的微信粉丝了。"
					+ "需要实现的功能：现在领导希望能主动推送带链接的图文消息给指定的用户。"
					+ "问题点：<br>1.我看官方API上对图片的链接有限制，“限制图片链接的域名需要与开发者填写的基本资料中的Url一致 ”。意味着我必须成为开发者才能推送图文消息吗？"
					+ "<br>2.模拟主动推送消息+调用推送图文的API接口能实现主动推送图文消息给指定粉丝吗？"
					+ "<br><img src='"+newsImgUrl+"'> </p>";
//			String content_source_url="http://mp.weixin.qq.com/s?__biz=MzIxODg4NjkxNQ==&tempkey=OTIyX2xYbTFVeGk0cTNPdy95dkZHNF9wcnA0bUR6ZnY5UWZzT3BGWlpFMkhqLWVxdmhzWlc2VWF2aWI2MTcyV1l0aGJMUDNzaWlJRjVLQlQwUkoyd3JCc1hpVDRYUjlfdnBXZjRxd1FVTk11c2FQbXFsZUtqMDJTT28wXzBZVWQ0cVRuR0pkLS1zS3hYcVZhSTNodnFEczFCdE1EZktaLTRGdEwxTlZrNmd%2Bfg%3D%3D&chksm=97e2e74ca0956e5a1b38f27a310f53fad4cb18313ebdd8947d84d1361b9e7a230b3d277d3082&scene=0&previewkey=zSfEVOtY0mfUeswVTgbjQMNS9bJajjJKzz%252F0By7ITJA%253D#wechat_redirect";
			String content_source_url="http://1slmm2s.hk1.mofasuidao.cn/html/first/first.html";
			String author=new String("craddock".getBytes("UTF-8"));
			String title=new String("测试推文".getBytes("UTF-8"));
			String content=new String(contentHtml.getBytes("UTF-8"));
			String digest=new String("测试推文描述".getBytes("UTF-8"));
			String msg = "{\"articles\": [{\"thumb_media_id\":\"" + mediaId + "\", \"author\":\"" + author + "\",\"title\":\""
					+ title + "\",\"content_source_url\":\"" + content_source_url + "\", \"content\":\"" + content
					+ "\",\"digest\":\"" + digest + "\"}]}";
			JSONObject respJson=advancedUtil.getAdvancedMethod().uploadNewsArticles(msg, access_token);
			//获取图文消息的mediaId
			String msgMediaId=respJson.getString("media_id");
			
			// 单独发给该用户进行预览
			String sendPreUrl = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=" + access_token;
			String sendPreMsg = "{\"touser\":\"" + my_openId + "\",\"mpnews\":{\"media_id\":\"" + msgMediaId
					+ "\"},\"msgtype\":\"mpnews\"}";
			advancedUtil.getAdvancedMethod().massOpenIdpreview(sendPreMsg, access_token);
			
			
			/*
			//群发
			String sendAllMsg = "{\"filter\":{\"is_to_all\":true},\"mpnews\":{ \"media_id\":\"" + msgMediaId
					+ "\"}, \"msgtype\":\"mpnews\"}";
			advancedUtil.getAdvancedMethod().massByTag(sendAllMsg, access_token);
			*/
			
		/*
		// 需要提交的json数据 
		JSONObject jsonObject=new JSONObject();
		JSONArray jsonArray=new JSONArray();
		JSONObject object=new JSONObject();
		object.put("thumb_media_id", mediaId);
		object.put("author", "craddock");
		object.put("title", "测试推文111");
		object.put("content_source_url", "http://d.g.wanfangdata.com.cn/Periodical_qbxb201501002.aspx");
		object.put("content", "内容111");
		object.put("digest", "描述111");
		object.put("show_cover_pic", 0);
		jsonArray.add(object);
		jsonObject.put("articles", jsonArray);
		String jsonData =JSONObject.fromObject(jsonObject).toString();
		
		//上传图文素材
		JSONObject respJson=advancedUtil.getAdvancedMethod().uploadNewsArticles(jsonData, access_token);
		*/
		/*	
		//组装群发消息
		JSONObject mass=new JSONObject();
		JSONArray touserArray=new JSONArray();
		touserArray.add("oA1Hcv9PfGShFQfsHXEdjQrPGPmQ");
		touserArray.add("oA1HcvwuqMAZgdi6l8hdKV48OCJE");
		JSONObject mpnewsObj=new JSONObject();
		mpnewsObj.put("media_id", respJson.getString("media_id"));
		mass.put("touser", touserArray);
		mass.put("mpnews", mpnewsObj);
		mass.put("msgtype", "mpnews");
		mass.put("send_ignore_reprint", 0);
		String massJson=String.valueOf(mass);
		//群发图文消息
		JSONObject massResp=advancedUtil.getAdvancedMethod().massByOpenIdList(massJson, access_token);
		ScnuAlumniLogs.getLogger().error(massJson);
		*/
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
}
