package com.newttl.scnualumni_gz.baidumap;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.newttl.scnualumni_gz.baidumap.BaiduPoiPlace;
import com.newttl.scnualumni_gz.bean.database.UserLocation;
import com.newttl.scnualumni_gz.bean.response.Article;
import com.newttl.scnualumni_gz.logs.ScnuAlumniLogs;
import com.newttl.scnualumni_gz.weixin.WeiXinCommon;

import it.sauronsoftware.base64.Base64;
import net.sf.json.JSONObject;

/**
 * 百度地图操作类
 * 
 * @author lgc
 *
 * 2017年7月14日 上午10:32:26
 */
public class BaiduMapUtil {
	
	private static Logger logger=ScnuAlumniLogs.getLogger();

	/**
	 * 根据关键词，转换后的百度坐标去poi周边
	 * 
	 * @param query 关键词 如：酒店
	 * @param lat 百度坐标系维度
	 * @param lng 百度坐标系经度
	 * @param ak 百度地图开发服务端ak
	 * @return List<BaiduPoiPlace> 返回poi周边地址列表
	 */
	public static List<BaiduPoiPlace> searchPoiPlace(String query,String lat,String lng,String ak){
		logger.debug("====根据关键词，转换后的百度坐标去poi周边====");
		//拼接请求地址
		String requestUrl = "http://api.map.baidu.com/place/v2/search?&query=QUERY&location=LNG,LAT&radius=2000&output=xml&scope=2&page_size=10&page_num=0&ak=AK";
		List<BaiduPoiPlace> placeList=null;
		try {
			requestUrl=requestUrl.replace("QUERY", URLEncoder.encode(query,"UTF-8"));
			requestUrl=requestUrl.replace("LAT", lat.trim());
			requestUrl=requestUrl.replace("LNG", lng.trim());
			requestUrl=requestUrl.replace("AK", ak);
			//调用place api圆形区域检索
			String respXml=httpRequest(requestUrl);
			//解析获取到的xml地址列表
			placeList=parsePlaceXml(respXml);
		} catch (UnsupportedEncodingException e) {
			logger.error("====根据关键词，转换后的百度坐标去poi周边【失败】====");
			logger.error(e.toString());
		}
		
		return placeList;
	}
	
	/**
	 * 发起HTTP请求
	 * @param requestUrl 请求地址
	 * @return String 返回请求获取的xml信息
	 */
	public static String httpRequest(String requestUrl){
		logger.debug("====发起HTTP请求====");
		StringBuffer buffer=new StringBuffer();
		URL url;
		try {
			url = new URL(requestUrl);
			HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
			httpURLConnection.setRequestMethod("GET");
			httpURLConnection.setDoInput(true);
			httpURLConnection.connect();
			//获取返回的输入流转换成字符
			InputStream inputStream=httpURLConnection.getInputStream();
			InputStreamReader inputStreamReader=new InputStreamReader(inputStream,"UTF-8");
			BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
			String str=null;
			while ((str=bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			//释放资源
			inputStream.close();
			inputStream=null;
			httpURLConnection.disconnect();
		} catch (Exception e) {
			logger.error("====发起HTTP请求【失败】====");
			logger.error(e.toString());
		}
		return buffer.toString();
	}
	
	/**
	 * 解析xml数据
	 * @param placeXml xml数据
	 * @return List<BaiduPoiPlace> 返回poi到的信息列表
	 */
	private static List<BaiduPoiPlace> parsePlaceXml(String placeXml){
		logger.debug("====解析xml数据====");
		List<BaiduPoiPlace> placeList=null;
		try {
			Document document=DocumentHelper.parseText(placeXml);
			//获取根节点
			Element rootElement=document.getRootElement();
			//从根节点获取<results>
			Element results=rootElement.element("results");
			//从<results>中获取<result>集合
			List<Element> resultList=results.elements("result");
			//判断集合大小
			if (resultList.size() > 0) {
				placeList=new ArrayList<BaiduPoiPlace>();
				//poi地点名称
				Element nameElement=null;
				//poi详细地址
				Element addressElement=null;
				//poi经纬度
				Element locationElement=null;
				//poi电话信息
				Element phoneElement=null;
				//poi扩展信息
				Element detailInfoElement=null;
				//poi距离
				Element distanceElement=null;
				//遍历集合
				for (Element element : resultList) {
					nameElement=element.element("name");
					locationElement=element.element("location");
					addressElement=element.element("address");
					phoneElement=element.element("telephone");
					detailInfoElement=element.element("detail_info");
					//创建BaiduPoiPlace实例
					BaiduPoiPlace baiduPoiPlace=new BaiduPoiPlace();
					baiduPoiPlace.setName(nameElement.getText());
					baiduPoiPlace.setAddress(addressElement.getText());
					baiduPoiPlace.setLat(locationElement.element("lat").getText());
					baiduPoiPlace.setLng(locationElement.element("lng").getText());
					//当<telephone>元素存在时，获取电话信息
					if (phoneElement != null) {
						baiduPoiPlace.setTelephone(phoneElement.getText());
					}
					//当<detail_info>元素存在时，可以获取距离等信息
					if (detailInfoElement != null) {
						distanceElement=detailInfoElement.element("distance");
						//当<distance>元素存在时，可以获取距离
						if (distanceElement != null) {
							baiduPoiPlace.setDistance(Integer.parseInt(distanceElement.getText()));
						}
					}
					placeList.add(baiduPoiPlace);
				}
				//按距离由近及远排序
				Collections.sort(placeList);
			}
			
		} catch (Exception e) {
			logger.error("====解析xml数据【失败】====");
			logger.error(e.toString());
		}
		return placeList;
	}
	
	/**
	 * 根据poi到的地址列表组装图文消息列表
	 * 
	 * @param placeList poi到的地址列表
	 * @param bd09Lng 转换后的百度坐标经度
	 * @param bd09Lat 转换后的百度坐标维度
	 * @return  List<Article> 返回图文列表
	 */
	public static List<Article> makeArticleList(List<BaiduPoiPlace> placeList,String bd09Lng,String bd09Lat){
		logger.debug("====根据poi到的地址列表组装图文消息列表====");
		//项目的根路径
		String projBasePath=WeiXinCommon.projectUrl;
		List<Article> articleList=new ArrayList<Article>();
		BaiduPoiPlace baiduPoiPlace=null;
		for (int i=0;i< /*placeList.size()*/ 8;i++) {
			baiduPoiPlace=placeList.get(i);
			Article article=new Article();
			article.setTitle(baiduPoiPlace.getName()+"\n距离约"+baiduPoiPlace.getDistance()+"米");
			// P1表示用户发送的位置（坐标转换后），p2表示当前POI所在位置
			article.setUrl(String.format(projBasePath+ "/route.jsp?p1=%s,%s&p2=%s,%s", bd09Lng,bd09Lat,baiduPoiPlace.getLat(),baiduPoiPlace.getLng()));
			// 将首条图文的图片设置为大图
			if (i == 0)
				article.setPicUrl(projBasePath + "/images/poisearch.png");
			else
				article.setPicUrl(projBasePath + "/images/navi.png");
				articleList.add(article);
		}
		return articleList;
	}
	
	/**
	 * 将用户发送过来的经纬度转换成百度坐标系(gcj02--->baidu)
	 * @param lng 用户位置的经度
	 * @param lat 用户位置的维度
	 * @return UserLocation 返回用户位置信息对象
	 * 
	 * @说明 百度坐标转换接口返回如：
	 *  {"error":0,"x":"MjMuMTE1OTMwOTc1Njk2","y":"MTE0LjQyMDIxMjIzMTU2"}
	 */
	
	public static UserLocation convertLatlng(String lng,String lat){
		logger.debug("====将用户发送过来的经纬度转换成百度坐标系====");
		// 百度坐标转换接口
		String convertUrl = "http://api.map.baidu.com/ag/coord/convert?from=2&to=4&x={x}&y={y}";
		convertUrl = convertUrl.replace("{x}", lng);
		convertUrl = convertUrl.replace("{y}", lat);
		UserLocation userLocation=new UserLocation();
		try {
			String jsonConvert=httpRequest(convertUrl);
			JSONObject jsonObject=JSONObject.fromObject(jsonConvert);
			//对转换后的经纬度进行Base64解码
			userLocation.setBd09Lng(Base64.decode(jsonObject.getString("x"),"UTF-8").trim());
			userLocation.setBd09Lat(Base64.decode(jsonObject.getString("y"),"UTF-8").trim());
		} catch (Exception e) {
			userLocation=null;
			logger.error("====将用户发送过来的经纬度转换成百度坐标系【失败】====");
			logger.error(e.toString());
		}
		return userLocation;
	}
}

