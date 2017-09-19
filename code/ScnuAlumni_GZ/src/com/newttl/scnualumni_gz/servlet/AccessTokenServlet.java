package com.newttl.scnualumni_gz.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.newttl.scnualumni_gz.logs.ScnuAlumniLogs;
import com.newttl.scnualumni_gz.util.TokenThread;

/**
 * 在web.xml中自启动获取接口凭证access_token
 * @author lgc
 *
 * 2017年9月18日 下午7:10:15
 */
public class AccessTokenServlet extends HttpServlet {

	private static final long serialVersionUID = 8480076130720717388L;
	private static Logger logger=ScnuAlumniLogs.getLogger();

	/**
	 * 自启动时初始化获取access_token
	 */
	@Override
	public void init() throws ServletException {
		logger.debug("====自启动时初始化获取access_token====");
		//获取初始参数appId,appSecret
		TokenThread.appId=getInitParameter("appid");
		TokenThread.appSecret=getInitParameter("appsecret");
		//启动线程
		new Thread(new TokenThread()).start();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
}
