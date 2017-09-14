package com.newttl.scnualumni_gz.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.newttl.scnualumni_gz.bean.database.Activity;
import com.newttl.scnualumni_gz.bean.pojo.Token;
import com.newttl.scnualumni_gz.logs.ScnuAlumniLogs;
import com.newttl.scnualumni_gz.util.AdvancedUtil;
import com.newttl.scnualumni_gz.util.CommonUtil;
import com.newttl.scnualumni_gz.util.DataBaseUtil;
import com.newttl.scnualumni_gz.weixin.WeiXinCommon;

import net.sf.json.JSONObject;

/**
 * 生成活动海报，并发送给用户
 * @author lgc
 *
 * 2017年9月4日 上午9:46:08
 */
public class SendPosterServlet extends HttpServlet {

	/**
	 * add generated serial Version ID
	 */
	private static final long serialVersionUID = -9127882689605122756L;
	private static Logger logger=ScnuAlumniLogs.getLogger();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.debug("====生成活动海报，并发送给用户====");
		resp.setCharacterEncoding("UTF-8");
		try {
			InputStream inputStream=req.getInputStream();
			InputStreamReader reader=new InputStreamReader(inputStream,"UTF-8");
			BufferedReader bufferedReader=new BufferedReader(reader);
			StringBuffer buffer=new StringBuffer();
			String str=null;
			while (null != (str=bufferedReader.readLine())) {
				buffer.append(str);
			}
			bufferedReader.close();
			reader.close();
			inputStream.close();
			inputStream=null;
			// 使用JSON-lib解析返回结果 
			JSONObject jsonObject = JSONObject.fromObject(buffer.toString());
			String openId=jsonObject.getString("openId");
			String activityId=jsonObject.getString("activityId");
			//发送活动图片
			int actID=Integer.valueOf(activityId);
			sendImgPoster(openId, actID);
			//回复页面
			JSONObject respJson=new JSONObject();
			respJson.put("success", "success");
			PrintWriter out=resp.getWriter();
			out.write(String.valueOf(respJson));
			out.flush();
		} catch (Exception e) {
			logger.error("====生成活动海报，并发送给用户【失败】====");
			logger.error(e.toString());
		}
	}
	
	/**
	 * 通过客服消息发生送活动图片给用户
	 * @param openID 用户Id
	 * @param activityID 活动id
	 */
	private void sendImgPoster(String openID,int activityID){
		logger.debug("====通过客服消息发生送活动图片给用户====");
		try {
			Activity activity = new Activity();
			DataBaseUtil baseUtil = new DataBaseUtil();
			activity = baseUtil.getTheActivity(activityID);
			//获取活动的具体信息
			String aname = activity.getActivityName();
			String start_time = activity.getStartTime();
			String end_time = activity.getEndTime();
			String address = activity.getActivityAddress();
			String atip = activity.getActivityIntro();
			String awho = activity.getActivityHolder();
			
			//客服信息内容
			String content = aname + "/" + start_time + " - " + end_time + "/" + address + "/" + atip + "/" + awho;
			
			//通过凭证 appID appsecret获取 access_token
			Token token=CommonUtil.getToken(WeiXinCommon.appID, WeiXinCommon.appsecret);
			
			// 发送客服文本消息
			AdvancedUtil customMessage = new AdvancedUtil();
			String jsonTextMsg = customMessage.getAdvancedMethod().makeTextCustomMessage(openID, "你的活动邀请海报生成啦！快转发到校友群通知大家参加吧[呲牙]");
			customMessage.getAdvancedMethod().sendCustomMessage(token.getAccess_token(), jsonTextMsg);

			// 发送客服图片消息
			String jsonImageMsg = customMessage.getAdvancedMethod()
					.makeImageCustomMessage(openID,customMessage.getAdvancedMethod().getActivityImgId(content,token.getAccess_token()));
			customMessage.getAdvancedMethod().sendCustomMessage(token.getAccess_token(), jsonImageMsg);
		} catch (Exception e) {
			logger.error("====通过客服消息发生送活动图片给用户【失败】====");
			logger.error(e.toString());
		}
	}

}
