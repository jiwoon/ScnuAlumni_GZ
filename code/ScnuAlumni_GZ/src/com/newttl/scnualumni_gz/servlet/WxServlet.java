package com.newttl.scnualumni_gz.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.newttl.scnualumni_gz.service.ChatService;
import com.newttl.scnualumni_gz.service.MessageService;
import com.newttl.scnualumni_gz.util.SignUtil;

public class WxServlet extends HttpServlet {

	/**
	 * Token可由开发者可以任意填写，用作生成签名（该Token会和接口URL中包含的Token进行比对，从而验证安全性）
	 * 比如这里我将Token设置为lgc1
	 */
	private final String TOKEN="lgc1";
	
	
	/**
	 * 校验和处理消息
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		System.out.println("进入doPost");
		
		// 将请求、响应的编码均设置为UTF-8（防止中文乱码
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		/*// 接收微信服务器发送请求时传递过来的4个参数 signature timestamp nonce echostr
		String signature=req.getParameter("signature");//微信加密签名signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数
		String timestamp=req.getParameter("timestamp");//时间戳
		String nonce=req.getParameter("nonce");//随机数
		String echostr=req.getParameter("echostr");//随机字符串
		
		//排序 TOKEN timestamp nonce
		String sortStr=SignUtil.sort(TOKEN, timestamp, nonce);
		
		//sha1加密
		String mySignature=SignUtil.sha1(sortStr);
		
		//校验签名
		if (mySignature != null && mySignature != "" && mySignature.equals(signature)) {
			
			PrintWriter writer =resp.getWriter();
			try {
				String respXml= MessageService.processRequest(req);
				writer.write(respXml);
				
				System.out.println("公众号回复::"+"\n"+respXml);
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			writer.close();
			writer=null;
		} else {
			System.out.println("校验失败。");
		}*/
		
		
		
		PrintWriter writer =resp.getWriter();
		try {
			String respXml= MessageService.processRequest(req);
			writer.write(respXml);
			
			System.out.println("公众号回复::"+"\n"+respXml);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		writer.close();
		writer=null;
		
	}
	
	
	/**
	 * 接入验证和其它请求的区别就是，接入验证时是get请求，其它时候是post请求
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("开始校验");
		
		// 接收微信服务器发送请求时传递过来的4个参数 signature timestamp nonce echostr
		String signature=req.getParameter("signature");//微信加密签名signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数
		String timestamp=req.getParameter("timestamp");//时间戳
		String nonce=req.getParameter("nonce");//随机数
		String echostr=req.getParameter("echostr");//随机字符串
		
		String signUp="";
		signUp=req.getParameter("signUp");
		System.out.println("signUp::"+signUp);
		
		System.out.println("echostr::"+echostr);
		
		
		//排序 TOKEN timestamp nonce
		String sortStr=SignUtil.sort(TOKEN, timestamp, nonce);
		
		//sha1加密
		String mySignature=SignUtil.sha1(sortStr);
		
		//校验签名
		PrintWriter writer =resp.getWriter();
		if (mySignature != null && mySignature != "" && mySignature.equals(signature)) {
          System.out.println("校验成功。");
          //如果检验成功输出echostr，微信服务器接收到此输出，才会确认检验完成。
          writer.write(echostr);
		} else {
          System.out.println("校验失败。");
		}
		
		writer.close();
		writer=null;
		
	}
	/*
	@Override
	public void init() throws ServletException {
		File indexDir=new File(ChatService.getIndexDir());
		//如果索引目录不存在，则创建目录
		if (!indexDir.exists()) {
			ChatService.createIndex();
		}
	}
	*/
	
}
