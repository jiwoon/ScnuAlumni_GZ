package com.newttl.scnualumni_gz.service;

import java.text.SimpleDateFormat;
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
				System.out.println("Location_X::"+lng+"\n"+"Location_Y::"+lat);
				//转换成百度坐标系
				String bd09Lng=null;
				String bd09Lat=null;
				UserLocation userLocation=BaiduMapUtil.convertLatlng(lng, lat);
				if (userLocation != null) {
					bd09Lng=userLocation.getBd09Lng();
					bd09Lat=userLocation.getBd09Lat();
					System.out.println("bd09Lng::"+bd09Lng+"\n"+"bd09Lat::"+bd09Lat);
				}
				//保存用户位置信息到数据库
				DataBaseUtil baseUtil=new DataBaseUtil();
				baseUtil.saveUserLocation(req_fromUserName, lng, lat, bd09Lng, bd09Lat);
				
				//回复文本消息，提示用户获取位置成功
				StringBuffer buffer = new StringBuffer();
				buffer.append("[愉快]").append("成功接收您的位置！").append("\n\n");
				buffer.append("您可以输入搜索关键词获取周边信息了，例如：").append("\n");
				buffer.append("        附近ATM").append("\n");
				buffer.append("        附近KTV").append("\n");
				buffer.append("        附近厕所").append("\n");
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
					textMessage.setContent("终于等到你[玫瑰]大家都想你了[害羞]\n" + "\n【华师新闻】看母校最新动态\n\n"
							+ "【公众号二维码】生成你的专属公众号二维码，轻松邀请校友\n\n" + "【工具箱】查找校友通讯录、校友最近活动\n\n" + "快点和昔日的同学联系吧！[拥抱]\n\n为了方便更多同学联系你，请先点击菜单栏【工具箱】->【个人中心】进行注册~[愉快]");
					respXml=MessageUtil.messageToXml(textMessage);
					break;
					
				case MessageUtil.EVENT_TYPE_UNSUBSCRIBE://取消关注
					// TODO 取消订阅后用户不会再收到公众账号发送的消息，因此不需要回复
					break;
					
				case MessageUtil.EVENT_TYPE_SCAN://扫描带参数二维码
					//TODO
					break;
					
				case MessageUtil.EVENT_TYPE_LOCATION://上报地理位置
					//回复文本消息给用户
//					textMessage.setContent("您的位置已经更新！");
//					respXml=MessageUtil.messageToXml(textMessage);
					
					respXml="success";
					
					/*
					//保存用户位置信息到数据库
					UserLocationEvent location=new UserLocationEvent();
					String req_latitude=reqMap.get("Latitude");
					String req_longitude=reqMap.get("Longitude");
					String req_percision=reqMap.get("Precision");
					//TODO 此处需要将经纬度转换为具体位置
					String address="待转换";
					location.setFromUserName(req_fromUserName);
					location.setCreateTime(Long.valueOf(req_createTime));
					location.setLatitude(req_latitude);
					location.setLongitude(req_longitude);
					location.setPrecision(req_percision);
					location.setAddress(address);
					//保存用户位置
					int insertIndex=MessageUtil.insertLocationData(location);
					System.out.println("保存用户位置信息-insertIndex::"+insertIndex);
					*/
					
					
					break;
					
				case MessageUtil.EVENT_TYPE_CLICK://点击菜单拉取消息
					//自定义菜单中的EventKey属性(在创建菜单是自定义的)
					String eventKey=reqMap.get("EventKey");
					if ("qrcode".equals(eventKey)) {
						//获取临时带参二维码
						AdvancedUtil advancedUtil2=new AdvancedUtil();
						Image qrCodeImg=new Image();
						String s_id = advancedUtil2.getAdvancedMethod().getQRid(resp_toUserName,WeiXinCommon.appID,WeiXinCommon.appsecret);
						qrCodeImg.setMediaId(s_id);
						ImageMessage qrCodeMessage=new ImageMessage();
						qrCodeMessage.setFromUserName(resp_fromUserName);
						qrCodeMessage.setToUserName(resp_toUserName);
						qrCodeMessage.setCreateTime(new Date().getTime());
						qrCodeMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_IMAGE);
						qrCodeMessage.setImage(qrCodeImg);
						//将图片对象转换为XML数据
						respXml=MessageUtil.messageToXml(qrCodeMessage);
					}
					break;
					
				case MessageUtil.EVENT_TYPE_VIEW://点击菜单跳转页面
					//用户点击菜单 view 不做回复
					respXml="success";
					break;
				}
				break;
			}
			
//			//设置文本消息内容
//			textMessage.setContent(respContent);
//			//将文本消息对象转换为XML格式
//			respXml=MessageUtil.messageToXml(textMessage);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return respXml;
	}
	
	/**
	 * 关注语提示
	 * @return 关注语
	 */
	private static String getSubscribeMsg(){
		StringBuffer buffer=new StringBuffer();
		buffer.append("欢迎您的关注！/:rose/:rose/:rose");
		buffer.append("\n\n");
		buffer.append("回复“附近”开始体验搜索附近功能吧！");
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
}
