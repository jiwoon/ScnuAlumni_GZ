package com.newttl.scnualumni_gz.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newttl.scnualumni_gz.bean.pojo.SNSUserInfo;
import com.newttl.scnualumni_gz.bean.pojo.WeiXinOauth2Token;
import com.newttl.scnualumni_gz.util.AdvancedUtil;
import com.newttl.scnualumni_gz.weixin.WeiXinCommon;

/**
 * 授权后的回调处理服务
 * 
 * @author lgc
 *
 * @date 2017年6月27日 下午3:04:41
 */
public class OAuthServlet extends HttpServlet {

	/**
	 * add generated serial Version ID
	 */
	private static final long serialVersionUID = -5888420896380854918L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		System.out.println("OAuthServlet doGet");
		
		//设置编码格式，防止中文出现乱码
		req.setCharacterEncoding("gb2312");
		resp.setCharacterEncoding("gb2312");
		
		//用户同意授权后，能够获得 code
		String code=req.getParameter("code");
		
		System.out.println("code::"+code);
		
		//用户同意授权
		if (!(code.equals("authdeny"))) {
			//获取网页授权凭证 access_token
			AdvancedUtil advancedUtil=new AdvancedUtil();
			WeiXinOauth2Token weiXinOauth2Token=advancedUtil.getAdvancedMethod().getOauth2AccessToken(WeiXinCommon.appID, WeiXinCommon.appsecret, code);
			String accessToken=weiXinOauth2Token.getAccessToken();
			//获得用户的标志
			String openID=weiXinOauth2Token.getOpenId();
			//拉取用户信息
			SNSUserInfo snsUserInfo=advancedUtil.getAdvancedMethod().getSNSUserInfo(accessToken, openID, "zh_CN");
			
			//设置要传递的信息 ,将用户信息放到 request 对象中，这样可以传递到目标页面,
			req.setAttribute("snsUserInfo", snsUserInfo);
			//跳转到目标页面
//			req.getRequestDispatcher("snsUserInfo.jsp").forward(req, resp);
			req.getRequestDispatcher("activity.jsp").forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}

}
