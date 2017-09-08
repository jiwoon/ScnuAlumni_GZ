package com.newttl.scnualumni_gz.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 图片验证码接口
 * @author lgc
 *
 * 2017年8月9日 上午8:41:25
 */
public class ImageCodeServlet extends HttpServlet {

	private static final long serialVersionUID = -98861507364053125L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//验证码字符范围
		char[] ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
		int width=48, height=20;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
		Graphics graphics=image.getGraphics();
		graphics.setColor(getRandColor(200, 250));
		graphics.fillRect(0, 0, width, height);
		graphics.setFont(new Font("Times New Roman",Font.PLAIN,12));
		graphics.setColor(getRandColor(160,200));
		Random r=new Random();
		int index;
		StringBuffer buffer=new StringBuffer();
		for (int i=0;i<4;i++){
			index=r.nextInt(ch.length); 
			graphics.setColor(new Color(20+r.nextInt(110),20+r.nextInt(110),20+r.nextInt(110)));  
			graphics.drawString(ch[index]+"",10*i+6,13);
			buffer.append(ch[index]);
		}
		/*
		// 添加噪点
	    int area = (int) (0.02 * width * height);
	    for(int i=0; i<area; ++i){
	        int x = (int)(Math.random() * width);
	        int y = (int)(Math.random() * height);
	        image.setRGB(x, y, (int)(Math.random() * 255));
	    }
		*/
		/*
	    //设置验证码中的干扰线
	    for (int i = 0; i < 6; i++) {  
		      //随机获取干扰线的起点和终点
		      int xstart = (int)(Math.random() * width);
		      int ystart = (int)(Math.random() * height);
		      int xend = (int)(Math.random() * width);
		      int yend = (int)(Math.random() * height);
		      graphics.setColor(interLine(1, 255));
		      graphics.drawLine(xstart, ystart, xend, yend);
		    }
	    */
	    HttpSession session = req.getSession();  //保存到session
		session.setAttribute("verificationCode", buffer.toString().toLowerCase());
		ImageIO.write(image, "JPG", resp.getOutputStream());  //写到输出流
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		
		HttpSession session =req.getSession();
		String verificationCode = ((String)session.getAttribute("verificationCode")).toLowerCase();
		String checkcode = req.getParameter("op").toLowerCase();
		System.out.println("verificationCode::"+verificationCode+"\n"+"checkcode::"+checkcode);
		PrintWriter out = resp.getWriter();
		if(checkcode.equals(verificationCode)){
			out.println(1);
		}else{
			out.println(0);
		}
		out.flush();
		out.close();
	}
	
	/**
	 * 获取随机颜色
	 * @param fc
	 * @param bc
	 * @return
	 */
	private Color getRandColor(int fc,int bc){
		Random random=new Random();
		if(fc > 255) fc=255;
		if(bc > 255) bc=255;
		int r=fc+random.nextInt(bc-fc);  
		int g=fc+random.nextInt(bc-fc);  
		int b=fc+random.nextInt(bc-fc);
		return new Color(r,g,b);
	}
	
	
	/**
	 * 获取随机的颜色值，r,g,b的取值在Low到High之间
	 * @param L 左区间
	 * @param R 右区间
	 * @return 返回随机颜色值
	 */
	private static Color interLine(int Low, int High){
	    if(Low > 255)
	    	Low = 255;
	    if(High > 255)
	    	High = 255;
	    if(Low < 0)
	    	Low = 0;
	    if(High < 0)
	    	High = 0;
	    int interval = High - Low; 
	    int r = Low + (int)(Math.random() * interval);
	    int g = Low + (int)(Math.random() * interval);
	    int b = Low + (int)(Math.random() * interval);
	    return new Color(r, g, b);
	  }
}
