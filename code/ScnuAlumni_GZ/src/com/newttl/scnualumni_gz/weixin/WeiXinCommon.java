package com.newttl.scnualumni_gz.weixin;

/**
 * 微信公共类
 * 
 * @author lgc
 *
 * @date 2017年7月4日 上午8:54:01
 */
public class WeiXinCommon {
	
	/**
	 * 根目录
	 */
	public static final String projectUrl="http://jqfrudd.hk1.mofasuidao.cn/ScnuAlumni_GZ/";
	
	
	/***
	 * URL 
	 *
	 * 新浪云  http://ntkwechat.applinzi.com ✔
	 * ngrokcc  http://newtkwx.ngrok.cc ✔
	 * ngrok.plub  http://newtkwx.ngrok.club
	 * 魔法隧道  http://jqfrudd.hk1.mofasuidao.cn
	 */
	
	public static final String WEIXIN_URL="http://jqfrudd.hk1.mofasuidao.cn/ScnuAlumni_GZ/WxServlet";
	public static final String REDIRECT_URI="http://jqfrudd.hk1.mofasuidao.cn/ScnuAlumni_GZ/OAuthServlet";
	public static final String OAUTH_URL="http://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect";

	/**
	 * 活动中心网页授权
	 */
	public static final String activityUrl="http://jqfrudd.hk1.mofasuidao.cn/ScnuAlumni_GZ/OAuthServlet";
	
	/**
	 * 查找校友
	 */
	public static final String SchoolMateUrl="http://jqfrudd.hk1.mofasuidao.cn/ScnuAlumni_GZ/findSchoolMate.jsp";
	
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
	public static final String qrCodeRoot="http://jqfrudd.hk1.mofasuidao.cn/WeiXinMedia/qrcodes/";
	
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
	public static final String signUpUrl="http://jqfrudd.hk1.mofasuidao.cn/ScnuAlumni_GZ/SignUpServlet";
	
	/**
	 * 服务器中图片根目录
	 */
	public static final String ImgUrl = "D:/soft/Tomcat/tomcat8044/apache-tomcat-8.0.44/webapps/ROOT/image";
	
	/**
	 * 公众号二维码
	 */
	public static final String QrFileUrl="http://jqfrudd.hk1.mofasuidao.cn/image/SpecialQR.jpg";
	
	/**
	 * 活动图片路径
	 */
	public static final String activityImgUrl="http://jqfrudd.hk1.mofasuidao.cn/image/SpecialActivity.jpg";
	
}
