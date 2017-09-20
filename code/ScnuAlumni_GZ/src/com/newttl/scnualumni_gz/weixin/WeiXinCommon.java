package com.newttl.scnualumni_gz.weixin;

import java.io.File;

import com.newttl.scnualumni_gz.logs.ScnuAlumniLogs;

/**
 * 微信公共类
 * 
 * @author lgc
 *
 * @date 2017年7月4日 上午8:54:01
 */
public class WeiXinCommon {
	
	/**
	 * 本地测试url http://jqfrudd.hk1.mofasuidao.cn
	 * http://scnugz.hk1.mofasuidao.cn
	 * 阿里云服务器  http://alumni.scnualumnigz.top
	 * (以上域名需要加项目名) /ScnuAlumni_GZ/
	 * 新浪云 http://scnugz.applinzi.com (不需要加项目名)
	 * 
	 * 已备案域名  http://wx.jimi-iot.com
	 */
//	private static final String testUrl="http://scnugz.applinzi.com";
	private static final String testUrl="http://wx.jimi-iot.com/ScnuAlumni_GZ";
	
	/**
	 * html资源
	 */
	public static final String htmlUrl="http://wx.jimi-iot.com/html/nav/nav.html";
	/**
	 * 根目录
	 */
	public static final String projectUrl=testUrl;
	
	
	/***
	 * URL 
	 * 新浪云  http://ntkwechat.applinzi.com ✔
	 * ngrokcc  http://newtkwx.ngrok.cc ✔
	 * ngrok.plub  http://newtkwx.ngrok.club
	 * 魔法隧道  http://jqfrudd.hk1.mofasuidao.cn
	 */
	
	public static final String WEIXIN_URL=testUrl+"/WxServlet";
	public static final String REDIRECT_URI=testUrl+"/OAuthServlet";
	public static final String OAUTH_URL="http://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

	/**
	 * 活动中心网页授权
	 */
	public static final String activityUrl=testUrl+"/OAuthServlet";
	
	/**
	 * 查找校友
	 */
	public static final String SchoolMateUrl=testUrl+"/findSchoolMate.jsp";
	
	/**
	 * 公众号的  appID
	 */
	public static final String appID="wxdfead60d5e0dbcd6";
	/**
	 * 公众号的 appsecret
	 */
	public static final String appsecret="92663408c73b91e5749a0c5de20bd953";
	
	/**
	 * 下载用户发送过来的媒体的存储地址
	 */
	public static final String downLoadFilePathComm="D:/soft/Tomcat/tomcat8044/apache-tomcat-8.0.44/webapps/ROOT/WeiXinMedia/usersMedia";
	
	/**
	 * 下载公众号二维码的存储地址
	 */
	public static final String downloadQrCode="D:/soft/Tomcat/tomcat8044/apache-tomcat-8.0.44/webapps/ROOT/WeiXinMedia/qrcodes";
	
	/**
	 * 获取本地服务器中的二维码的根目录
	 */
	public static final String qrCodeRoot=testUrl+"/qrcodes/";
	
	/**
	 * 百度地图获取周边的接口的Ak CA21bdecc75efc1664af5a195c30bb4e 
	 * wk5Al4x4OXuOOjLkB6xpyzsKxBiUrnCl
	 * 
	 * S0D9rYXOzhqervq5kOkwlOFfCPQZSR5H
	 */
	public static final String baiduAk="CA21bdecc75efc1664af5a195c30bb4e";
	
	public static final String wxAk="S0D9rYXOzhqervq5kOkwlOFfCPQZSR5H";
	
	/**
	 * 个人中心网页授权
	 */
	public static final String signUpUrl=testUrl+"/SignUpServlet";
	
	/**
	 * 服务器中图片根目录
	 */
	public static final String ImgUrl = "D:/soft/Tomcat/tomcat8044/apache-tomcat-8.0.44/webapps/ROOT/image";
	
	/**
	 * 公众号二维码
	 */
	public static final String QrFileUrl=testUrl+"/image/SpecialQR.jpg";
	
	/**
	 * 活动图片路径
	 */
	public static final String activityImgUrl=testUrl+"/image/SpecialActivity.jpg";
	
	/**获取项目在服务器中的真实路径  
	 * 在windows和linux系统下均可正常使用 
	 * @return
	 */
    public static String getRootPath() {  
        String classPath = WeiXinCommon.class.getClassLoader().getResource("/").getPath();  
        String rootPath = "";  
        if("\\".equals(File.separator)){//windows下  
            rootPath = classPath.substring(1, classPath.indexOf("/WEB-INF/classes"));  
            rootPath = rootPath.replace("/", "\\");  
        }  
        if("/".equals(File.separator)){//linux下  
            rootPath = classPath.substring(0,classPath.indexOf("/WEB-INF/classes"));  
            rootPath = rootPath.replace("\\", "/");  
        }
        ScnuAlumniLogs.getLogger().debug("rootPath::"+rootPath);
        return rootPath;  
    }  
	
}
