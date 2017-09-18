package com.newttl.scnualumni_gz.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.binary.Token.StartNode;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.newttl.scnualumni_gz.bean.database.UserLocationEvent;
import com.newttl.scnualumni_gz.bean.event.QRCodeEvent;
import com.newttl.scnualumni_gz.bean.response.Article;
import com.newttl.scnualumni_gz.bean.response.ImageMessage;
import com.newttl.scnualumni_gz.bean.response.MusicMessage;
import com.newttl.scnualumni_gz.bean.response.NewsMessage;
import com.newttl.scnualumni_gz.bean.response.TextMessage;
import com.newttl.scnualumni_gz.bean.response.VideoMessage;
import com.newttl.scnualumni_gz.bean.response.VoiceMessage;
import com.newttl.scnualumni_gz.logs.ScnuAlumniLogs;
import com.newttl.scnualumni_gz.util.DataBaseUtil;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;

/**
 * 消息处理工具类
 * @author lgc
 *
 * @date 2017年6月7日 下午2:37:06
 */
public class MessageUtil {
	
	private static Logger logger=ScnuAlumniLogs.getLogger();
	// 请求消息类型：文本
	public static final String REQ_MESSAGE_TYPE_TEXT="text";
	// 请求消息类型：图片
	public static final String REQ_MESSAGE_TYPE_IMAGE="image";
	// 请求消息类型：语音
	public static final String REQ_MESSAGE_TYPE_VOICE="voice";
	// 请求消息类型：视频
	public static final String REQ_MESSAGE_TYPE_VIDEO="video";
	// 请求消息类型：小视频
	public static final String REQ_MESSAGE_TYPE_SHORTVIDEO="shortvideo";
	// 请求消息类型：位置
	public static final String REQ_MESSAGE_TYPE_LOCATION="location";
	// 请求消息类型：链接
	public static final String REQ_MESSAGE_TYPE_LINK="link";
	
	// 请求消息类型：事件
	public static final String REQ_MESSAGE_TYPE_EVENT="event";
	
	// 事件类型：subscribe(订阅)
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";
	// 事件类型：unsubscribe(取消订阅)
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";
	// 事件类型：scan(用户已关注时的扫描带参数二维码)
	public static final String EVENT_TYPE_SCAN = "SCAN";
	// 事件类型：LOCATION(上报地理位置)
	public static final String EVENT_TYPE_LOCATION = "LOCATION";
	// 事件类型：CLICK(点击菜单拉取消息)
	public static final String EVENT_TYPE_CLICK = "CLICK";
	// 事件类型：VIEW(点击菜单跳转链接)
	public static final String EVENT_TYPE_VIEW = "VIEW";
	
	// 响应消息类型：文本
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";
	// 响应消息类型：图片
	public static final String RESP_MESSAGE_TYPE_IMAGE = "image";
	// 响应消息类型：语音
	public static final String RESP_MESSAGE_TYPE_VOICE = "voice";
	// 响应消息类型：视频
	public static final String RESP_MESSAGE_TYPE_VIDEO = "video";
	// 响应消息类型：音乐
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";
	// 响应消息类型：图文
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";
	
	public MessageUtil(){
		
	}
	
	/**
	 * 解析用户发来的消息(XML)
	 * @throws IOException 
	 */
	public Map<String, String> parseXml(HttpServletRequest request){
		logger.debug("====解析用户发来的消息(XML)====");
		//新建HashMap对象，存放解析的内容
		Map<String, String> msgMap=new HashMap<String,String>();
		try {
			//从HttpServletRequest中取输入流
			InputStream iStream= request.getInputStream();
			//读取输入流
			SAXReader reader=new SAXReader();
			Document document=reader.read(iStream);
			//解析xml根节点
			Element root=document.getRootElement();
			//解析根节点的所有子节点
			List<Element> elements=root.elements();
			//遍历子节点，存放到HashMap中
			logger.debug("====用户发出请求====");
			for (Element element : elements) {
				msgMap.put(element.getName(), element.getText());
				//控制台显示日志
				logger.debug(element.getName()+"=>"+element.getText());
			}
			//释放资源
			iStream.close();
			iStream=null;
		} catch (Exception e) {
			logger.error("====解析用户发来的消息(XML)【失败】====");
			logger.error(e.toString());
		}
		return msgMap;
	}

//========================响应用户消息========================
	
	/**
	 * 扩展xstream使其支持CDATA
	 * 用于将对象转换为XML,响应用户(发送消息给用户)
	 */
	private XStream xStream=new XStream(new XppDriver(){
		public HierarchicalStreamWriter createWriter(Writer out){
			return new PrettyPrintWriter(out){
				
				// 对所有xml节点的转换都增加CDATA标记
				boolean cdata=true;
				
				public void startNode(String name,Class clazz){
					super.startNode(name, clazz);
				}
				
				protected void writeText(com.thoughtworks.xstream.core.util.QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					}else {
						writer.write(text);
					}
				};
			};
			
		}
	});
	
	/**
	 * 文本消息对象转换为XML
	 * @param textMessage
	 * @return xml
	 */
	public String messageToXml(TextMessage textMessage){
		xStream.alias("xml", textMessage.getClass());
		return xStream.toXML(textMessage);
	}
	
	/**
	 * 图片消息对象转换为XML
	 * @param imageMessage
	 * @return xml
	 */
	public String messageToXml(ImageMessage imageMessage){
		xStream.alias("xml", imageMessage.getClass());
		return xStream.toXML(imageMessage);
	}
	
	/**
	 * 语音消息对象转换为XML
	 * @param voiceMessage
	 * @return xml
	 */
	public String messageToXml(VoiceMessage voiceMessage){
		xStream.alias("xml", voiceMessage.getClass());
		return xStream.toXML(voiceMessage);
	}
	
	/**
	 * 视频消息对象转换为XML
	 * @param videoMessage
	 * @return xml
	 */
	public String messageToXml(VideoMessage videoMessage){
		xStream.alias("xml", videoMessage.getClass());
		return xStream.toXML(videoMessage);
	}
	
	/**
	 * 音乐消息对象转换为XML
	 * @param musicMessage
	 * @return xml
	 */
	public String messageToXml(MusicMessage musicMessage){
		xStream.alias("xml", musicMessage.getClass());
		return xStream.toXML(musicMessage);
	}
	
	/**
	 * 图文消息对象转换为XML
	 * @param newsMessage
	 * @return xml
	 */
	public String messageToXml(NewsMessage newsMessage){
		xStream.alias("xml", newsMessage.getClass());
		xStream.alias("item", new Article().getClass());
		return xStream.toXML(newsMessage);
	}
	
	/**
	 * 关注事件转换为XML
	 * @param qrMessage
	 * @return
	 */
	public String messageToXml(QRCodeEvent qrMessage) {
		xStream.alias("xml", qrMessage.getClass());
		return xStream.toXML(qrMessage);
	}
	
	//======================保存用户的消息到数据库=====================
	
	/**
	 * 保存用户位置信息到数据库
	 * 
	 * @param location 位置信息对象
	 * @return insertIndex 返回插入的位置
	 */
	public static int insertLocationData(UserLocationEvent location){
		int insertIndex=-1;
		DataBaseUtil dataBaseUtil=new DataBaseUtil();
		String userName=location.getFromUserName();
		long createTime=location.getCreateTime();
		String latitude=location.getLatitude();
		String longitude=location.getLongitude();
		String precision=location.getPrecision();
		String address=location.getAddress();
		String insertSql="insert into user_location_event values"+"("+"'"+userName+"','"+createTime+"','"+latitude+"','"+longitude+"','"+precision+"','"+address+"'"+")";
		insertIndex=dataBaseUtil.cruNewData(insertSql);
		return insertIndex;
	}
	
	
}
