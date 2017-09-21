package com.newttl.scnualumni_gz.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.newttl.scnualumni_gz.bean.pojo.SNSUserInfo;
import com.newttl.scnualumni_gz.bean.pojo.WeiXinOauth2Token;
import com.newttl.scnualumni_gz.logs.ScnuAlumniLogs;
import com.newttl.scnualumni_gz.util.AdvancedUtil;
import com.newttl.scnualumni_gz.util.DataBaseUtil;
import com.newttl.scnualumni_gz.weixin.WeiXinCommon;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 用户注册接口
 * @author lgc
 *
 * 2017年8月2日 上午11:58:57
 */
public class SignUpServlet extends HttpServlet {

	/**
	 * add generated serial Version ID
	 */
	private static final long serialVersionUID = -2714335254760543111L;
	private static Logger logger=ScnuAlumniLogs.getLogger();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		logger.debug("【个人中心】网页授权");
		
		//设置编码格式，防止中文出现乱码
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		//用户同意授权后，能够获得 code
		String code=req.getParameter("code");
		
		logger.debug("【个人中心】网页授权-授权码::"+code);
		
		//用户同意授权
		if (!(code.equals("authdeny"))) {
			//获取网页授权凭证 access_token
			AdvancedUtil advancedUtil=new AdvancedUtil();
			WeiXinOauth2Token weiXinOauth2Token=advancedUtil.getAdvancedMethod().getOauth2AccessToken(WeiXinCommon.appID, WeiXinCommon.appsecret, code);
			if (null != weiXinOauth2Token) {
				String accessToken=weiXinOauth2Token.getAccessToken();
				//获得用户的标志
				String openID=weiXinOauth2Token.getOpenId();
				//拉取用户信息
				SNSUserInfo snsUserInfo=advancedUtil.getAdvancedMethod().getSNSUserInfo(accessToken, openID, "zh_CN");
				
				//设置要传递的信息 ,将用户信息放到 request 对象中，这样可以传递到目标页面,
				req.setAttribute("snsUserInfo", snsUserInfo);
				//跳转到目标页面
				req.getRequestDispatcher("/WEB-INF/pages/signUp.jsp").forward(req, resp);
				logger.debug("授权【成功】");
			}else {
				//将未获取到授权的信息传递到目标页面
				String erro="未授权!";
				req.setAttribute("snsUserInfo", erro);
				//跳转到目标页面
				req.getRequestDispatcher("myIndex.jsp").forward(req, resp);
				logger.error("【未授权】");
				
			}
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		logger.debug("====相应查找校友====");
		//设置编码格式，防止中文出现乱码
		/*req.setCharacterEncoding("gb2312");
		resp.setCharacterEncoding("gb2312");*/
		
		resp.setCharacterEncoding("UTF-8");
		
		try {
		//从HttpServletRequest中取输入流
		InputStream iStream=req.getInputStream();
		//读取输入流
		InputStreamReader reader=new InputStreamReader(iStream,"UTF-8");
		BufferedReader bufferedReader=new BufferedReader(reader);
		StringBuffer buffer=new StringBuffer();
		String str=null;
		while (null != (str=bufferedReader.readLine())) {
			buffer.append(str);
		}
		//释放资源
		bufferedReader.close();
		reader.close();
		iStream.close();
		iStream=null;
		// 使用JSON-lib解析返回结果 getBytes("iso8859-1"),"utf-8"
		JSONObject jsonObject = JSONObject.fromObject(buffer.toString());
		String userName=jsonObject.getString("userName");
		logger.debug("userName-"+userName);
		//向前台页面输出结果
		DataBaseUtil baseUtil=new DataBaseUtil();
		JSONObject respJson=baseUtil.getAlumnisName(userName);
		logger.debug(String.valueOf(respJson));
		PrintWriter out=resp.getWriter();
		out.write(String.valueOf(respJson));
		out.flush();
		logger.debug("====相应查找校友【成功】====");
		} catch (Exception e) {
			logger.error("====相应查找校友【失败】====");
			logger.error(e.toString());
		}
		
	}
	
}
