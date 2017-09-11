package com.newttl.scnualumni_gz.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.newttl.scnualumni_gz.baidumap.BaiduMapUtil;
import com.newttl.scnualumni_gz.baidumap.BaiduPoiPlace;
import com.newttl.scnualumni_gz.bean.database.UserLocation;
import com.newttl.scnualumni_gz.bean.event.QRCodeEvent;
import com.newttl.scnualumni_gz.bean.pojo.Token;
import com.newttl.scnualumni_gz.bean.pojo.WeiXinUserInfo;
import com.newttl.scnualumni_gz.bean.response.Article;
import com.newttl.scnualumni_gz.bean.response.Image;
import com.newttl.scnualumni_gz.bean.response.ImageMessage;
import com.newttl.scnualumni_gz.bean.response.NewsMessage;
import com.newttl.scnualumni_gz.bean.response.TextMessage;
import com.newttl.scnualumni_gz.logs.ScnuAlumniLogs;
import com.newttl.scnualumni_gz.util.AdvancedUtil;
import com.newttl.scnualumni_gz.util.CommonUtil;
import com.newttl.scnualumni_gz.util.DataBaseUtil;
import com.newttl.scnualumni_gz.util.MessageUtil;
import com.newttl.scnualumni_gz.weixin.WeiXinCommon;

/**
 * 消息处理服务类
 * @author lgc
 *
 * @date 2017年6月7日 下午5:17:48
 */
public class MessageService {

	private static String access_token;
	
	static{
		//获取接口凭证
		Token token=CommonUtil.getToken(WeiXinCommon.appID, WeiXinCommon.appsecret);
		access_token=token.getAccess_token();
	}
	
	/**
	 * 根据用户的请求,进行消息处理
	 * 
	 * @param request 用户请求
	 * @return String respXml 返回用于回复用户的xml消息
	 */
	public static String processRequest(HttpServletRequest request){
		//返回xml格式的消息
		String respXml=null;
		//默认回复的内容
		String respContent="未知的消息类型！";
		
		try {
			//解析用户的请求消息
			Map<String, String> reqMap= MessageUtil.parseXml(request);
			//获取各个节点的内容
			String req_fromUserName=reqMap.get("FromUserName");//用户微信号
			String req_toUserName=reqMap.get("ToUserName");//开发者微信号
			String req_msgType=reqMap.get("MsgType");//消息类型
			String req_createTime=reqMap.get("CreateTime");//消息创建的时间
			
			//回复消息
			String resp_fromUserName=req_toUserName;//开发者微信号
			String resp_toUserName=req_fromUserName;//用户微信号
			
			//创建一个用于回复的文本消息
			TextMessage textMessage=new TextMessage();
			textMessage.setToUserName(resp_toUserName);
			textMessage.setFromUserName(resp_fromUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
			
			//判断用户请求的消息类型
			switch (req_msgType) {
			case MessageUtil.REQ_MESSAGE_TYPE_TEXT://文本消息
				String req_content=reqMap.get("Content").trim();//获取文本消息
				if (req_content.equals("附近")) {
					//回复文本消息，提示用户发送位置信息
					respContent=getUsage();
					textMessage.setContent(respContent);
					//将文本消息对象转换为XML格式
					respXml=MessageUtil.messageToXml(textMessage);
				}else if (req_content.startsWith("附近")) {
					String keyWord=req_content.replaceAll("附近", "").trim();
					//获取用户最后一次发送的地址
					DataBaseUtil dataBaseUtil=new DataBaseUtil();
					UserLocation userLocation=dataBaseUtil.getLastLoaction(req_fromUserName);
					//未获取到,回复文本消息，提示用户再次发送位置
					if (userLocation==null) {
						respContent=getUsage();
						textMessage.setContent(respContent);
						//将文本消息对象转换为XML格式
						respXml=MessageUtil.messageToXml(textMessage);
					}else {
						//获取到，根据转换后百度坐标系去poi周边
						List<BaiduPoiPlace> placeList=BaiduMapUtil.searchPoiPlace(keyWord, 
								userLocation.getBd09Lat(), 
								userLocation.getBd09Lng(), 
								WeiXinCommon.baiduAk);
						if (placeList==null || placeList.size()==0) {
							respContent=String.format("/难过，您发送的位置附近未搜索到“%s”信息！\n\n 请您再发送一次位置！/:rose/:rose/:rose", keyWord);
						}else {
							//获取图文列表
							List<Article> articles=BaiduMapUtil.makeArticleList(placeList, userLocation.getBd09Lng().trim(), userLocation.getBd09Lat().trim());
							//创建图文消息
							NewsMessage newsMessage=new NewsMessage();
							newsMessage.setFromUserName(resp_fromUserName);
							newsMessage.setToUserName(resp_toUserName);
							newsMessage.setCreateTime(new Date().getTime());
							newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
							newsMessage.setArticleCount(articles.size());
							newsMessage.setArticles(articles);
							//将图文消息对象转换为XML格式
							respXml=MessageUtil.messageToXml(newsMessage);
						}
						
					}
				}else {
					//不包含“附近”,则使用自动聊天功能
					respContent=ChatService.chat(req_fromUserName, req_createTime, req_content);
					textMessage.setContent(respContent);
					//将文本消息对象转换为XML格式
					respXml=MessageUtil.messageToXml(textMessage);
				}
				break;
				
			case MessageUtil.REQ_MESSAGE_TYPE_IMAGE://图片消息
				respContent="您发送的是图片消息";
				//设置文本消息内容
				textMessage.setContent(respContent);
				//将文本消息对象转换为XML格式
				respXml=MessageUtil.messageToXml(textMessage);
				break;
				
			case MessageUtil.REQ_MESSAGE_TYPE_VOICE://语音消息
				//语音的MediaId
				String mediaId=reqMap.get("MediaId");
				//从微信服务器下载该语音到本地服务器下
				AdvancedUtil advancedUtil=new AdvancedUtil();
				String downLoadFile=advancedUtil.getAdvancedMethod().downLoadMedia(access_token, mediaId, WeiXinCommon.downLoadFilePathComm+"/voice/");
				ScnuAlumniLogs.getLogger().debug("用户发送语音消息::"+downLoadFile);
				respContent="您发送的是语音消息";
				//设置文本消息内容
				textMessage.setContent(respContent);
				//将文本消息对象转换为XML格式
				respXml=MessageUtil.messageToXml(textMessage);
				break;
				
			case MessageUtil.REQ_MESSAGE_TYPE_VIDEO://视频消息
				respContent="您发送的是视频消息";
				//设置文本消息内容
				textMessage.setContent(respContent);
				//将文本消息对象转换为XML格式
				respXml=MessageUtil.messageToXml(textMessage);
				break;
				
			case MessageUtil.REQ_MESSAGE_TYPE_SHORTVIDEO://小视频消息
				respContent="您发送的是小视频消息";
				//设置文本消息内容
				textMessage.setContent(respContent);
				//将文本消息对象转换为XML格式
				respXml=MessageUtil.messageToXml(textMessage);
				break;
				
			case MessageUtil.REQ_MESSAGE_TYPE_LOCATION://位置消息
				//TODO
				//获取用户的经纬度
				String lng=reqMap.get("Location_X").trim();
				String lat=reqMap.get("Location_Y").trim();
				
				ScnuAlumniLogs.getLogger().debug("Location_X::"+lng+"--"+"Location_Y::"+lat);
				
				//转换成百度坐标系
				String bd09Lng=null;
				String bd09Lat=null;
				UserLocation userLocation=BaiduMapUtil.convertLatlng(lng, lat);
				if (userLocation != null) {
					bd09Lng=userLocation.getBd09Lng();
					bd09Lat=userLocation.getBd09Lat();
					
					ScnuAlumniLogs.getLogger().debug("bd09Lng::"+bd09Lng+"--"+"bd09Lat::"+bd09Lat);
					
				}
				//保存用户位置信息到数据库
				DataBaseUtil baseUtil=new DataBaseUtil();
				baseUtil.saveUserLocation(req_fromUserName, lng, lat, bd09Lng, bd09Lat);
				
				//回复文本消息，提示用户获取位置成功
				StringBuffer buffer = new StringBuffer();
				buffer.append("[愉快]").append("成功接收您的位置！").append("\n\n");
				buffer.append("您可以输入搜索关键词获取周边信息了，例如：").append("\n");
				buffer.append("附近ATM").append("\n");
				buffer.append("附近KTV").append("\n");
				buffer.append("附近厕所").append("\n");
				buffer.append("必须以“附近”两个字开头！");
				respContent = buffer.toString();
				//设置文本消息内容
				textMessage.setContent(respContent);
				//将文本消息对象转换为XML格式
				respXml=MessageUtil.messageToXml(textMessage);
				break;
				
			case MessageUtil.REQ_MESSAGE_TYPE_LINK://链接消息
				//TODO
				respContent="您发送的是链接消息";
				//设置文本消息内容
				textMessage.setContent(respContent);
				//将文本消息对象转换为XML格式
				respXml=MessageUtil.messageToXml(textMessage);
				break;
				
			case MessageUtil.REQ_MESSAGE_TYPE_EVENT://事件消息
				//获取用户请求的事件类型
				String eventType=reqMap.get("Event");
				
				switch (eventType) {
				case MessageUtil.EVENT_TYPE_SUBSCRIBE://关注
					//根据带参二维码中ticket判断新用户是否通过扫描他人推荐二维码进行的关注
					String ticket = reqMap.get("Ticket");
					//非空，说明是扫描推荐二维码进行的关注
					if(ticket != null){
						QRCodeEvent qr = new QRCodeEvent();
						String eventKey = reqMap.get("EventKey");
						DataBaseUtil dataBaseUtil=new DataBaseUtil();
						
						// 截取推荐二维码中参数 选取二维码提供者的微信openid  保存在数据库中
						String provider = eventKey.substring(8, eventKey.length());
						System.out.println("provider::"+provider);
						
						SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
						
						AdvancedUtil advancedUtil3=new AdvancedUtil();
						WeiXinUserInfo providerInfo =advancedUtil3.getAdvancedMethod().getUserInfo(access_token,provider);
						String providerName = providerInfo.getNickName();
						WeiXinUserInfo recieverInfo =advancedUtil3.getAdvancedMethod().getUserInfo(access_token,req_fromUserName);
						String recieverName = recieverInfo.getNickName();
						dataBaseUtil.saveQRCodeParmer(provider, req_fromUserName, ticket, df.format(new Date()), providerName, recieverName);
						
						// 生成关注事件
						qr.setToUserName(resp_toUserName);
						qr.setFromUserName(resp_fromUserName);
						qr.setCreateTime(new Date().getTime());
						qr.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_EVENT);
						qr.setEvent(MessageUtil.EVENT_TYPE_SUBSCRIBE);
						qr.setEventKey(eventKey);
						qr.setTicket(ticket);
						respXml = MessageUtil.messageToXml(qr);
					}
					
					// 新用户关注问候语
					textMessage.setContent(getSubscribeMsg());
					respXml=MessageUtil.messageToXml(textMessage);
					break;
					
				case MessageUtil.EVENT_TYPE_UNSUBSCRIBE://取消关注
					//回复"success",即不做处理
					respXml="success";
					break;
					
				case MessageUtil.EVENT_TYPE_SCAN://扫描带参数二维码
					//TODO
					break;
					
				case MessageUtil.EVENT_TYPE_LOCATION://上报地理位置					
					//回复"success",即不做处理
					respXml="success";
					break;
					
				case MessageUtil.EVENT_TYPE_CLICK://点击菜单拉取消息
					//自定义菜单中的EventKey属性(在创建菜单是自定义的)
					String eventKey=reqMap.get("EventKey");
					if ("qrcode".equals(eventKey)) {
						//获取校友风采图文列表
						List<Article> articles = getAlumniStyle();
						//创建图文消息
						NewsMessage newsMessage=new NewsMessage();
						newsMessage.setFromUserName(resp_fromUserName);
						newsMessage.setToUserName(resp_toUserName);
						newsMessage.setCreateTime(new Date().getTime());
						newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
						newsMessage.setArticleCount(articles.size());
						newsMessage.setArticles(articles);
						//将图文消息对象转换为XML格式
						respXml=MessageUtil.messageToXml(newsMessage);
					}
					break;
					
				case MessageUtil.EVENT_TYPE_VIEW://点击菜单跳转页面
					//用户点击菜单 view 不做回复
					respXml="success";
					break;
				}
				break;
			}			
		} catch (Exception e) {
			e.printStackTrace();
			ScnuAlumniLogs.getLogger().error(e);
		}
		
		return respXml;
	}
	
	/**
	 * 关注语提示
	 * @return 关注语
	 */
	private static String getSubscribeMsg(){
		StringBuffer buffer=new StringBuffer();
		buffer.append("终于等到你[玫瑰]大家都想你了[害羞]");
		buffer.append("\n");
		buffer.append("【菜单】功能");
		buffer.append("\n");
		buffer.append("【新闻】看母校新闻动态、官方微博");
		buffer.append("\n");
		buffer.append("【校友风采】查看校友的风采人生");
		buffer.append("\n");
		buffer.append("【基金会】查找校友互相联络[玫瑰]");
		buffer.append("\n");
		buffer.append("快点和昔日的同学联系吧！[拥抱]");
		buffer.append("\n");
		buffer.append("为了方便更多同学联系您，请先点击菜单栏【基金会】->【个人中心】进行注册~[愉快]");
		return buffer.toString();
	}
	
	/**
	 * 搜索周边说明
	 * @return 说明
	 */
	private static String getUsage(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("周边搜索使用说明").append("\n\n");
		buffer.append("1）发送地理位置").append("\n");
		buffer.append("点击窗口底部的“+”按钮，选择“位置”，点“发送”").append("\n\n");
		buffer.append("2）指定关键词搜索").append("\n");
		buffer.append("格式：附近+关键词\n例如：附近ATM、附近KTV、附近厕所等等");
		return buffer.toString();
	}
	
	/**
	 * 校友风采 article 列表
	 * @return
	 */
	private static List<Article> getAlumniStyle(){
		List<Article> articles=new ArrayList<Article>();
		String projBasePath=WeiXinCommon.projectUrl;
		for (int i = 0; i < 8; i++) {
			Article article=new Article();
			article.setTitle("校友"+(i+1)+"的介绍");
			article.setPicUrl(projBasePath + "images/poisearch.png");
			article.setUrl(String.format(projBasePath+"alumniNews.jsp?alumni=%s", "校友"+String.valueOf(i+1)));
			articles.add(article);
		}
		return articles;
	}
}
