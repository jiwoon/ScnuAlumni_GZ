package com.newttl.scnualumni_gz.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.newttl.scnualumni_gz.logs.ScnuAlumniLogs;
/**
 * 用户编辑个人信息
 * @author lgc
 *
 * 2017年9月21日 下午7:47:43
 */
public class UserEditServlet extends HttpServlet {

	private static final long serialVersionUID = 5755639738422861319L;

	private static Logger logger=ScnuAlumniLogs.getLogger();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		logger.debug("====【修改个人信息】====");
		
		//设置编码格式，防止中文出现乱码
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		//用户同意授权后，能够获得 code
		String openid=req.getParameter("openId");
		
		logger.debug("【修改个人信息】-openId::"+openid);
		
		if ("" != openid) {
			//设置要传递的信息 ,将用户信息放到 request 对象中，这样可以传递到目标页面,
			req.setAttribute("openId", openid);
			//跳转到目标页面
			req.getRequestDispatcher("/WEB-INF/pages/userEdit.jsp").forward(req, resp);
			logger.debug("====修改个人信息页面【跳转成功】====");
		}else {
			//将未获取到授权的信息传递到目标页面
			String erro="修改个人信息失败,请退出!";
			req.setAttribute("snsUserInfo", erro);
			//跳转到目标页面
			req.getRequestDispatcher("myIndex.jsp").forward(req, resp);
			logger.error("====修改个人信息页面【跳转失败】====");
		}
	}
}
