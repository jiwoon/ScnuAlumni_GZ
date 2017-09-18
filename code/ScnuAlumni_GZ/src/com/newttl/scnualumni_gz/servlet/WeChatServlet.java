package com.newttl.scnualumni_gz.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.newttl.scnualumni_gz.logs.ScnuAlumniLogs;
import com.newttl.scnualumni_gz.service.MessageService;
import com.newttl.scnualumni_gz.util.SignUtil;
import com.newttl.scnualumni_gz.weixin.MenuManager;

import oracle.net.aso.o;

/**
 * 建立公众号与微信服务器连接
 * 接收微信服务器转发的信息
 * @author lgc
 *
 * 2017年9月16日 上午10:03:54
 */
public class WeChatServlet extends HttpServlet {
	
	private static final long serialVersionUID = 8965925420933719623L;
	private static Logger logger=ScnuAlumniLogs.getLogger();
	/**
	 * Token可由开发者可以任意填写，用作生成签名（该Token会和接口URL中包含的Token进行比对，从而验证安全性）
	 * 比如这里我将Token设置为scnugz
	 */
	private final String TOKEN="scnugz";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.debug("====开始校验====");
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		// 接收微信服务器发送请求时传递过来的4个参数 signature timestamp nonce echostr
		String signature=req.getParameter("signature");//微信加密签名signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数
		String timestamp=req.getParameter("timestamp");//时间戳
		String nonce=req.getParameter("nonce");//随机数
		String echostr=req.getParameter("echostr");//随机字符串
		
		logger.debug("echostr::"+echostr);
		
		//回复微信服务器信息
		String respStr="";
		//首次接入验证,如果检验成功输出echostr，微信服务器接收到此输出，才会确认检验完成
		if (echostr != null && echostr.length() > 1) {
			//排序 TOKEN timestamp nonce
			String sortStr=SignUtil.sort(TOKEN, timestamp, nonce);
			//sha1加密
			String mySignature=SignUtil.sha1(sortStr);
			if (mySignature != null && mySignature != "" && mySignature.equals(signature)) {
		          respStr=echostr;
		          logger.debug("====校验【成功】!====");
				} else {
		          logger.error("====校验【失败】！！！====");
				}
		}else {
			//处理用户发送的消息
			String respXml=new MessageService().processRequest(req);
			respStr=respXml;
		}
		try {
			OutputStream outputStream=resp.getOutputStream();
			outputStream.write(respStr.getBytes("UTF-8"));
			outputStream.flush();
			outputStream.close();
			logger.debug("公众号回复::"+"\n"+respStr);
			//执行菜单
			MenuManager.main(null);
		} catch (Exception e) {
			logger.error("====校验处理消息【失败】!!!====");
			logger.error(e.toString());
		}
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		logger.debug("====校验和处理消息====");
		doGet(req, resp);
	}
}
