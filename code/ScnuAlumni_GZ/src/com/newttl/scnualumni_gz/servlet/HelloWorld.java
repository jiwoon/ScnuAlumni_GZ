package com.newttl.scnualumni_gz.servlet;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.newttl.scnualumni_gz.bean.pojo.Token;
import com.newttl.scnualumni_gz.bean.pojo.WeiXinGroups;
import com.newttl.scnualumni_gz.bean.pojo.WeiXinMedia;
import com.newttl.scnualumni_gz.bean.pojo.WeiXinPermanentQRCode;
import com.newttl.scnualumni_gz.bean.pojo.WeiXinTemporaryQRCode;
import com.newttl.scnualumni_gz.bean.pojo.WeiXinUserInfo;
import com.newttl.scnualumni_gz.bean.pojo.WeiXinUserList;
import com.newttl.scnualumni_gz.service.ChatService;
import com.newttl.scnualumni_gz.util.AdvancedUtil;
import com.newttl.scnualumni_gz.util.CommonUtil;

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
		
//		String classPath=ChatService.getIndexDir();
//		System.out.println("classPath::"+"\n"+classPath);
		
		/*
		getWeixinUserInfo(access_token,user_openId);
		
		getWexinUserLists(access_token, "");
		
		getWeixinGroups(access_token);
		
//		createGroup(access_token, "newthk1");
		
		updateGroup(access_token, 100, "family");
		
		moveMember(access_token, my_openId, 100);
		
		getWeixinGroups(access_token);*/
		
		// http://2d625a40.ngrok.io/WeiXinMedia/voice/msg1.amr
//		uploadMedia(access_token, "voice", "http://03b9ab9a.ngrok.io/WeiXinMedia/voice/uploadtest.jpg");
		
//		WeiXinMedia media=AdvancedUtil.uploadTemporaryMedia(access_token, "voice", "http://2d625a40.ngrok.io/WeiXinMedia/voice/begin.mp3");
//		System.out.println(media.getWeixinMedia());
		
//		String downLoadfile=AdvancedUtil.downLoadMedia(access_token, mediaId, savePath);
//		String path="D:/soft/Tomcat/tomcat8044/apache-tomcat-8.0.44/webapps/ROOT/WeiXinMedia/usersMedia/voice/test.mp3";
//		String path="D:/MaLady.mp3";
		
		
//		String path="http://a7ef5a73.ngrok.io/WeiXinMedia/usersMedia/voice/MaLady.mp3";
		
		
//		String path="http://a7ef5a73.ngrok.io/WeiXinMedia/qrcodes/debug.jpg";
		
		
//		AdvancedUtil advancedUtil=new AdvancedUtil();
		
		
//		String hello=advancedUtil.getAdvancedMethod().getHello();
//		System.out.println(hello);
		
//		WeiXinMedia media=advancedUtil.getAdvancedMethod().uploadMedia(access_token, "voice", path);
//		System.out.println(media.getWeixinMedia());
		
		
//		
//		File file=new File(path);
//		
//		String jsonObject=null;
//		try {
//			jsonObject=advancedUtil.getAdvancedMethod().upload(path, access_token, "voice");
//		}catch (Exception e) {
////			e.printStackTrace();
//			System.out.println(e.toString());
//		}
//		
//		System.out.println(jsonObject.toString());
//		
		
		
		//驱动
		String driver="com.mysql.jdbc.Driver";
		// URL指向要访问的数据库名wechat_data
        String url = "jdbc:mysql://127.0.0.1:3306/wechat_data";
        //用户名
        String userName="root";
        //密码
        String password="guochang";
		//加载驱动
        try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        //链接数据库
        try {
			Connection conn=DriverManager.getConnection(url, userName, password);
			 if(!conn.isClosed()){
	             System.out.println("Succeeded connecting to the Database!");
			 }
			 String sqlStr="select joke_content from joke";
			 PreparedStatement ps=conn.prepareStatement(sqlStr);
			 ResultSet rs=ps.executeQuery();
			 while (rs.next()) {
				System.out.println(rs.getString("joke_content"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
	}
	/*
	//创建二维码
	private static void getQRcode(String access_token){

		System.out.println("access_token::"+access_token);
		//创建永久带参数二维码
		WeiXinPermanentQRCode weCode=AdvancedUtil.createPermanentQRCode(access_token, "1520");
		String ticket=weCode.getTicket();
//		int expire=weCode.getExpireSeconds();
		String url=weCode.getUrl();
//		System.out.println("ticket::"+ticket+"\n"+"expire::"+expire+"\n"+"url::"+url);
		System.out.println("ticket::"+ticket+"\n"+"url::"+url);
		
		//根据 ticket 获取二维码
		String savePath="D:/WebProj/myDownload";
		String filePath=AdvancedUtil.getQRCode(ticket, savePath);
		
		System.out.println("二维码路径：："+filePath);
		
	}
	
	//获取指定openId的用户的基本信息
	private static void getWeixinUserInfo(String access_token,String openId){
		WeiXinUserInfo userInfo=AdvancedUtil.getUserInfo(access_token, openId);
		byte[] bytes=userInfo.getWeixinUserInfo().getBytes();
		try {
			System.out.println(new String(bytes, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		
		System.out.println(userInfo.getWeixinUserInfo());
	}
	
	//拉取关注者列表
	private static void getWexinUserLists(String access_token,String nextOpenId){
		WeiXinUserList userList=AdvancedUtil.getWeiXinUserList(access_token, nextOpenId);
		System.out.println(userList.getWeiXinUserList());
	}
	
	//获取用户分组信息
	private static void getWeixinGroups(String access_token){
		List<WeiXinGroups> groups=AdvancedUtil.getWeiXinGroups(access_token);
		for (WeiXinGroups weiXinGroups : groups) {
			System.out.println(weiXinGroups.getWeiXinGroups());
		}
	}
	
	//创建分组
	private static void createGroup(String access_token,String groupName){
		WeiXinGroups groups=AdvancedUtil.createGroup(access_token, groupName);
		System.out.println(groups.getWeiXinGroups());
	}
	
	//修改分组
	private static void updateGroup(String access_token,int groupId,String groupName){
		boolean result=AdvancedUtil.updateGroup(access_token, groupId, groupName);
		System.out.println("修改分组：："+result);
	}
	
	//移动用户到指定分组
	private static void moveMember(String access_token,String openId,int groupId){
		boolean result=AdvancedUtil.moveMemberGroup(access_token, openId, groupId);
	}
	
	//上传媒体文件到微信服务器
	private static void uploadMedia(String accessToken, String type, String mediaFileUrl){
		WeiXinMedia media=AdvancedUtil.uploadTemporaryMedia(accessToken, type, mediaFileUrl);
		System.out.println(media.getWeixinMedia());
	}*/
}
