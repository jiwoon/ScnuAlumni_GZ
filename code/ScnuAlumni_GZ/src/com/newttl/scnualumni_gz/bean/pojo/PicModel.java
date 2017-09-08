package com.newttl.scnualumni_gz.bean.pojo;

/**
 * @author jason
 * @time 2017��7��26��
 */
import javax.imageio.ImageIO;

import com.newttl.scnualumni_gz.weixin.WeiXinCommon;

import java.awt.Color;  
import java.awt.Font;  
import java.awt.Graphics2D;  
import java.awt.image.BufferedImage;  
import java.io.File;  
import java.io.IOException;  
import java.net.URL;  
  
  
public class PicModel {  
  
    private Font font = new Font("宋体", Font.PLAIN, 38); 
  
    private Graphics2D g = null;  
  
    private int fontsize = 0;  
  
    private int x = 0;  
  
    private int y = 0;  
  
    /** 
     * 导入本地图片到缓冲区 
     */  
    public BufferedImage loadImageLocal(String imgName) {  
        try {  
            return ImageIO.read(new File(imgName));  
        } catch (IOException e) {  
            System.out.println(e.getMessage());  
        }  
        return null;  
    }  
    
    /** 
     * 导入网络图片到缓冲区 
     */  
    public BufferedImage loadImageUrl(String imgName) {  
        try {  
            URL url = new URL(imgName);  
            return ImageIO.read(url);  
        } catch (IOException e) {  
            System.out.println(e.getMessage());  
        }  
        return null;  
    }  

    /** 
     * 生成新图片到本地 
     */   
    public void writeImageLocal(String newImage, BufferedImage img) {  
        if (newImage != null && img != null) {  
            try {  
                File outputfile = new File(newImage);  
                ImageIO.write(img, "jpg", outputfile);  
            } catch (IOException e) {  
                System.out.println(e.getMessage());  
            }  
        }  
    }  
  
    /** 
     * 设定文字的字体等 
     */  
    public void setFont(String fontStyle, int fontSize) {  
        this.fontsize = fontSize;  
        this.font = new Font(fontStyle, Font.PLAIN, fontSize);  
    }  
  
    /** 
     * 修改图片,返回修改后的图片缓冲区（只输出一行文本） 
     */ 
    public BufferedImage modifyImage(BufferedImage img, Object content, int x, int y) {  
        try {  
            int w = img.getWidth();  
            int h = img.getHeight();  
            g = img.createGraphics();  
            g.setBackground(Color.WHITE);  
            g.setColor(Color.orange);
            if (this.font != null)  
                g.setFont(this.font);  
                this.x = x;  
                this.y = y;  

            if (content != null) {  
                g.drawString(content.toString(), this.x, this.y);  
            }  
            g.dispose();  
        } catch (Exception e) {  
            System.out.println(e.getMessage());  
        }  
        return img;  
    }  
  
    /** 
     * 修改图片,返回修改后的图片缓冲区（只输出一行文本） 
     *  
     * 时间:2007-10-8 
     *  
     * @param img 
     * @return 
     */  
    public BufferedImage modifyImageYe(BufferedImage img) {  
        try {  
            int w = img.getWidth();  
            int h = img.getHeight();  
            g = img.createGraphics();  
            g.setBackground(Color.black);  
            g.setColor(Color.blue);
            if (this.font != null)  
                g.setFont(this.font);  
            g.drawString("reyo.cn", w - 85, h - 5);  
            g.dispose();  
        } catch (Exception e) {  
            System.out.println(e.getMessage());  
        }  
  
        return img;  
    }  
  
    public BufferedImage modifyImagetogeter(BufferedImage b, BufferedImage d) {  
    	  
        try {  
            int w = b.getWidth();  
            int h = b.getHeight();              
            g = d.createGraphics();  
            g.drawImage(b, 110, 620, w, h, null);  
            g.dispose();  
        } catch (Exception e) {  
            System.out.println(e.getMessage());  
        }  
  
        return d;  
    }   
    public BufferedImage modifyImagetogeter2(BufferedImage b, BufferedImage d) {  
  	  
        try {  
            int w = b.getWidth();  
            int h = b.getHeight();              
            g = d.createGraphics();  
            g.drawImage(b, 280, 193, w, h, null);  
            g.dispose();  
        } catch (Exception e) {  
            System.out.println(e.getMessage());  
        }  
  
        return d;  
    } 
  
    /**
     * 生成带头像和昵称的推荐二维码
     * @param nickname 微信用户昵称
     * @param head_img 头像图片
     */
    
    public static void MakeImg(String nickname, String head_img){
    	PicModel tt = new PicModel();  
    	
    	//载入添加二维码模板图片go2.jpg
        BufferedImage d = tt.loadImageLocal(WeiXinCommon.ImgUrl+"/QRModel.jpg");  
        //往图片写入用户昵称，生成新图片qr_nickname.jpg
        tt.writeImageLocal(WeiXinCommon.ImgUrl+"/QRModel_nickname.jpg",tt.modifyImage(d,nickname,320,350));
        
        //获取二维码图片QR.jpg
        BufferedImage e = tt.loadImageLocal(WeiXinCommon.ImgUrl+"/QR.jpg");  

        BufferedImage b = tt.loadImageLocal(WeiXinCommon.ImgUrl+"/QRModel_nickname.jpg"); 
        
        tt.writeImageLocal(WeiXinCommon.ImgUrl+"/QRModel_nickname_QR.jpg", tt.modifyImagetogeter(e, b));
        
        
        BufferedImage g = tt.loadImageLocal(WeiXinCommon.ImgUrl+"/QRModel_nickname_QR.jpg");
        BufferedImage k = tt.loadImageLocal(head_img);
        tt.writeImageLocal(WeiXinCommon.ImgUrl+"/SpecialQR.jpg", tt.modifyImagetogeter2(k, g));
        
    } 
    /**
     * 生成活动邀请海报
     * @param content 海报内容
     * @param img_pic 海报模板图片
     */
    
    public static void MakeActivityImg(String content, String img_pic){
    	PicModel tt = new PicModel();  
        BufferedImage d = tt.loadImageLocal(img_pic);  
        String[] news = content.split("/");
        System.out.println(news[0]+"\n"+news[1]+"\n"+news[2]+"\n"+news[3]+"\n"+news[4]+"\n");
        
        //finalActivityImg 这个是最终生成的活动邀请海报图片
        String finalActivityImg =WeiXinCommon.ImgUrl+ "/SpecialActivity.jpg";
        
        //写入活动名称
        tt.writeImageLocal(finalActivityImg,tt.modifyImage(d,news[0],347,560));
        //写入活动起始时间
        tt.writeImageLocal(finalActivityImg,tt.modifyImage(d,news[1],160,771));
        //写入活动地点
        tt.writeImageLocal(finalActivityImg,tt.modifyImage(d,news[2],347,640));
        //写入活动介绍(考虑到多行文本)
        int intro_postion = 912;
        int intro_startPostion = 160;
        int intro_length = news[3].length();
        StringBuffer buffer = new StringBuffer();
        buffer.append(news[3]);
        for(int i = 0; i < (intro_length/18);i++)
        {	
        	if(i == 0) 
        	{
        		intro_startPostion = 210;
        	}
        	tt.writeImageLocal(finalActivityImg,tt.modifyImage(d,news[3].substring(0, 18),intro_startPostion,intro_postion));
        	intro_postion += 70;
        	intro_startPostion = 160;
        	news[3] = buffer.delete(0, 18).toString();
        }
        tt.writeImageLocal(finalActivityImg,tt.modifyImage(d,news[3].substring(0, news[3].length()),intro_startPostion,intro_postion));
        
        //写入活动发起人
        tt.writeImageLocal(finalActivityImg,tt.modifyImage(d,news[4],555,1391));
        
        System.out.println("成功！");
        
    }  
  
}  