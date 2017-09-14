package com.newttl.scnualumni_gz.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.newttl.scnualumni_gz.bean.database.Activity;
import com.newttl.scnualumni_gz.bean.database.Alumnus;
import com.newttl.scnualumni_gz.bean.database.Knowledge;
import com.newttl.scnualumni_gz.bean.database.SignedUser;
import com.newttl.scnualumni_gz.bean.database.UserLocation;
import com.newttl.scnualumni_gz.logs.ScnuAlumniLogs;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


/**
 * 数据库操作类
 * 
 * @author lgc
 *
 * @date 2017年7月3日 下午4:25:16
 */
public class DataBaseUtil {

	private static Connection conn;
	private static Logger logger=ScnuAlumniLogs.getLogger();	
	/**
	 * 链接数据库
	 */
	public DataBaseUtil(){
		logger.debug("====链接数据库====");
		//驱动
		String driver="com.mysql.jdbc.Driver";
		// URL指向要访问的数据库名wechat_data
        String url = "jdbc:mysql://127.0.0.1:3306/wechat_data";
        //用户名
        String userName="craddock";
        //密码
        String password="guochang";
		//加载驱动
        try {
			Class.forName(driver);
			logger.debug("====加载驱动【成功】====");
		} catch (ClassNotFoundException e) {
			logger.error("====加载驱动【失败】====");
			logger.error(e.toString());
		}
        //链接数据库
        try {
			conn=DriverManager.getConnection(url, userName, password);
			logger.debug("====链接数据库【成功】====");
		} catch (SQLException e) {
			logger.error("====链接数据库【失败】====");
			logger.error(e.toString());
		}
		
        
        /*连接SQL server
	    try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		} catch (ClassNotFoundException e) {
			System.out.print("DataBaseUtil-ClassNotFoundException::"+"\n"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		try {
			conn=DriverManager.getConnection("jdbc:odbc:Sqllgc","sa","sa123abc");
			sql=conn.createStatement();
		} catch (SQLException e) {
			System.out.print("DataBaseUtil-SQLException::"+"\n"+e.getLocalizedMessage());
			e.printStackTrace();
		}
		*/
		
		
	}
	
	/**
	 * 释放 jdbc资源
	 * 
	 * @param con 数据库链接
	 * @param ps
	 * @param rs 数据库结果集
	 */
	private static void releaseResources(Connection con,PreparedStatement ps,ResultSet rs){
		logger.debug("====释放 jdbc资源====");
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			logger.error("====释放 jdbc资源【失败】====");
			logger.error(e.toString());
		}
	}
	
	/**
	 * 获取知识问答表中的所有记录
	 * 
	 * @return List<Knowledge>
	 */
	public List<Knowledge> findAllKnowledge(){
		logger.debug("====获取知识问答表中的所有记录====");
		List<Knowledge> knowledges=new ArrayList<Knowledge>();
		String sqlStr = "select * from knowledge";
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn.prepareStatement(sqlStr);
			rs=ps.executeQuery();
			while (rs.next()) {
				Knowledge knowledge=new Knowledge();
				knowledge.setId(rs.getInt("id"));
				knowledge.setQuestion(rs.getString("question"));
				knowledge.setAnswer(rs.getString("answer"));
				knowledge.setCategory(rs.getInt("category"));
				knowledges.add(knowledge);
			}
			logger.debug("====获取知识问答表中的所有记录【成功】====");
		} catch (SQLException e) {
			logger.error("====获取知识问答表中的所有记录【失败】====");
			logger.error(e.toString());
		}finally {
			//释放资源
			releaseResources(conn, ps, rs);
		}
		return knowledges;
	}
	
	/**
	 * 获取上一次聊天记录的类型
	 * @param openId 用户id
	 * @return int 返回类型 1-普通聊天，2-笑话，3-上下文
	 */
	public int getLastCategory(String openId){
		logger.debug("====获取上一次聊天记录的类型====");
		int category=-1;
		String sqlStr="select top 1 chat_category from chat_log where open_id=? order by id desc";
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn.prepareStatement(sqlStr);
			ps.setString(1, openId);
			rs=ps.executeQuery();
			if (rs.next()) {
				category=rs.getInt("chat_category");
			}
			logger.debug("====获取上一次聊天记录的类型【成功】====");
		} catch (SQLException e) {
			logger.error("====获取上一次聊天记录的类型【失败】====");
			logger.error(e.toString());
		}finally {
			//释放资源
			releaseResources(conn, ps, rs);
		}
		return category;
	}
	
	/**
	 * 根据问答知识id，随机获取一条答案回复用户
	 * @param pid 问答知识id
	 * @return String 返回回答内容
	 */
	public String getOneKnowledgeSub(int pid){
		logger.debug("====根据问答知识id，随机获取一条答案回复用户====");
		String knowledgeAnswer="";
		String sqlStr="select top 1 answer from knowledge_sub where pid=? order by rand()";
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn.prepareStatement(sqlStr);
			ps.setInt(1, pid);
			rs=ps.executeQuery();
			if (rs.next()) {
				knowledgeAnswer=rs.getString("answer");
			}
			logger.debug("====根据问答知识id，随机获取一条答案回复用户【成功】====");
		} catch (SQLException e) {
			logger.error("====根据问答知识id，随机获取一条答案回复用户【失败】====");
			logger.error(e.toString());
			System.out.println("getOneKnowledgeSub::\n"+e.toString());
		}finally {
			//释放资源
			releaseResources(conn, ps, rs);
		}
		return knowledgeAnswer;
	}
	
	/**
	 * 随机获取一条笑话
	 * @return
	 */
	public String getJoke(){
		logger.debug("====随机获取一条笑话====");
		String jokeContent="";
		String sqlStr="SELECT TOP 1 joke_content FROM joke ORDER BY NEWID()";
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn.prepareStatement(sqlStr);
			rs=ps.executeQuery();
			if (rs.next()) {
				jokeContent=rs.getString("joke_content");
			}
			logger.debug("====随机获取一条笑话【成功】====");
		} catch (SQLException e) {
			logger.error("====随机获取一条笑话【失败】====");
			logger.error(e.toString());
		}finally {
			//释放资源
			releaseResources(conn, ps, rs);
		}
		return jokeContent;
	}
	
	/**
	 * 保存聊天记录
	 * @param open_id 用户id
	 * @param create_time 创建时间
	 * @param req_msg 用户发送的消息
	 * @param resp_msg 公众号回复的消息
	 * @param chat_category 聊天消息类型，1-普通聊天，2-笑话，3-上下文
	 * @return
	 */
	public int saveChatLog(String open_id,String create_time,String req_msg,String resp_msg,int chat_category){
		logger.debug("====保存聊天记录====");
		String sqlStr="insert into chat_log(open_id, create_time, req_msg, resp_msg, chat_category) values(?, ?, ?, ?, ?)";
		int insert = -1;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn.prepareStatement(sqlStr);
			ps.setString(1, open_id);
			ps.setString(2, create_time);
			ps.setString(3, req_msg);
			ps.setString(4, resp_msg);
			ps.setInt(5, chat_category);
			insert=ps.executeUpdate();
			logger.debug("====保存聊天记录【成功】====");
		} catch (SQLException e) {
			logger.error("====保存聊天记录【失败】====");
			logger.error(e.toString());
		}finally {
			//释放资源
			releaseResources(conn, ps, rs);
		}
		return insert;
	}
	
	/**
	 * 向数据库插入用户发送过来的位置信息
	 * @param openID 用户标识
	 * @param lng 用户的经度
	 * @param lat 用户的维度
	 * @param bd09Lng 转换后的百度坐标系经度
	 * @param bd09Lat 转换后的百度坐标系维度
	 * @return int 插入的位置
	 */
	public int saveUserLocation(String openID,String lng,String lat,String bd09Lng,String bd09Lat){
		logger.debug("====向数据库插入用户发送过来的位置信息====");
		String sqlStr = "insert into user_location(open_id, lng, lat, bd09_lng, bd09_lat) values (?, ?, ?, ?, ?)";
		int insertIndex=-1;
		PreparedStatement ps=null;
		try {
			ps=conn.prepareStatement(sqlStr);
			ps.setString(1, openID);
			ps.setString(2, lng);
			ps.setString(3, lat);
			ps.setString(4, bd09Lng);
			ps.setString(5, bd09Lat);
			insertIndex=ps.executeUpdate();
			logger.debug("====向数据库插入用户发送过来的位置信息【成功】====");
		} catch (SQLException e) {
			logger.error("====向数据库插入用户发送过来的位置信息【失败】====");
			logger.error(e.toString());
		}finally {
			//释放资源
			releaseResources(conn, ps, null);
		}
		return insertIndex;
	}
	
	/**
	 * 获取用户最后一次发送的地理位置，用于poi用户周边
	 * @param openId 用户唯一标识
	 * @return UserLocation 返回用户位置信息对象
	 */
	public UserLocation getLastLoaction(String openId){
		logger.debug("====获取用户最后一次发送的地理位置，用于poi用户周边====");
		UserLocation userLocation=null;
		String sqlStr = "select * from user_location where open_id=? order by id desc limit 1";
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn.prepareStatement(sqlStr);
			ps.setString(1, openId);
			rs=ps.executeQuery();
			if (rs.next()) {
				userLocation=new UserLocation();
				userLocation.setOpenId(rs.getString("open_id"));
				userLocation.setLng(rs.getString("lng"));
				userLocation.setLat(rs.getString("lat"));
				userLocation.setBd09Lng(rs.getString("bd09_lng"));
				userLocation.setBd09Lat(rs.getString("bd09_lat"));
			}
			logger.debug("====获取用户最后一次发送的地理位置，用于poi用户周边【成功】====");
		} catch (SQLException e) {
			logger.error("====获取用户最后一次发送的地理位置，用于poi用户周边【失败】====");
			logger.error(e.toString());
		}finally {
			//释放资源
			releaseResources(conn, ps, rs);
		}
		return userLocation;
	}
	
	/**
	 * 查询指定openId的用户是否注册了
	 * @param openId 用户标识
	 * @return false 未注册 | true 已注册
	 */
	public boolean isSigned(String openId){
		logger.debug("====查询指定openId的用户是否注册了====");
		boolean signed=false;
		String sqlStr="select * from signed_users where openId=?";
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn.prepareStatement(sqlStr);
			ps.setString(1, openId);
			rs=ps.executeQuery();
			if (rs.next()) {
				signed=true;
				logger.debug("====【用户已注册】====");
			}else {
				signed=false;
				logger.debug("====【用户未注册】====");
			}
		} catch (SQLException e) {
			logger.error("====查询【失败】====");
			logger.error(e.toString());
		}finally {
			//释放资源
			releaseResources(conn, ps, rs);
		}
		
		return signed;
	}
	
	/**
	 * 判断指定openId的用户是否注册
	 * @param openId 用户标识
	 * @return 已注册,则返回用户信息实例SignedUser;未注册,则返回null
	 */
	public SignedUser getSigned(String openId){
		logger.debug("====判断指定openId的用户是否注册====");
		SignedUser signedUser=null;
		String sqlStr="select * from signed_users where openId=?";
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn.prepareStatement(sqlStr);
			ps.setString(1, openId);
			rs=ps.executeQuery();
			if (rs.next()) {
//openId headImgUrl userName college userClass phone QQ eMail city industry hobby profession sex
				signedUser=new SignedUser();
				signedUser.setOpenId(rs.getString("openId"));
				signedUser.setHeadImgUrl(rs.getString("headImgUrl"));
				signedUser.setUserName(rs.getString("userName"));
				signedUser.setCollege(rs.getString("college"));
				signedUser.setUserClass(rs.getString("userClass"));
				signedUser.setGrade(rs.getString("grade"));
				signedUser.setCity(rs.getString("city"));
				signedUser.setContact(rs.getString("contact"));
				signedUser.setContactType(rs.getString("contactType"));
				signedUser.setIndustry(rs.getString("industry"));
				signedUser.setHobby(rs.getString("hobby"));
				signedUser.setProfession(rs.getString("profession"));
				signedUser.setSex(rs.getString("sex"));
				logger.debug("====【用户已注册】====");
			}else {
				signedUser=null;
				logger.debug("====【用户未注册】====");
			}
		} catch (SQLException e) {
			signedUser=null;
			logger.error("====查询【失败】====");
			logger.error(e.toString());
		}finally {
			//释放资源
			releaseResources(conn, ps, rs);
		}
		return signedUser;
	}
	
	/**
	 * 保存用户注册的信息
	 * @param signedUser
	 * @return  int
	 */
	public int saveSignedUser(SignedUser signedUser){
		logger.debug("====保存用户注册的信息====");
		String sqlStr = "insert into signed_users(openId, headImgUrl, sex, userName, college, userClass, grade, city, contact, contactType, industry, hobby, profession) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		int insert=-1;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn.prepareStatement(sqlStr);
			ps.setString(1, signedUser.getOpenId());
			ps.setString(2, signedUser.getHeadImgUrl());
			ps.setString(3, signedUser.getSex());
			ps.setString(4, signedUser.getUserName());
			ps.setString(5, signedUser.getCollege());
			ps.setString(6, signedUser.getUserClass());
			ps.setString(7, signedUser.getGrade());
			ps.setString(8, signedUser.getCity());
			ps.setString(9, signedUser.getContact());
			ps.setString(10, signedUser.getContactType());
			ps.setString(11, signedUser.getIndustry());
			ps.setString(12, signedUser.getHobby());
			ps.setString(13, signedUser.getProfession());
			
			insert=ps.executeUpdate();
			logger.debug("====保存用户注册的信息【成功】====");
		} catch (SQLException e) {
			logger.error("====保存用户注册的信息【失败】====");
			logger.error(e.toString());
		}finally {
			//释放资源
			releaseResources(conn, ps, rs);
		}
		return insert;
	}

	/**
	 * 更新用户修改的个人信息
	 * @param signedUser
	 * @return int
	 */
	public int updateSignedUser(SignedUser signedUser){
		logger.debug("====更新用户修改的个人信息====");
//openId, headImgUrl, sex, userName, college, userClass, grade, city, contact, contactType, industry, hobby, profession
		String sqlStr="update signed_users set headImgUrl=?, userName=?, college=?, userClass=?, grade=?, contact=?, contactType=?, city=?, industry=?, hobby=?, profession=?, sex=? where openId=?";
		int update=-1;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn.prepareStatement(sqlStr);
			ps.setString(1, signedUser.getHeadImgUrl());
			ps.setString(2, signedUser.getUserName());
			ps.setString(3, signedUser.getCollege());
			ps.setString(4, signedUser.getUserClass());
			ps.setString(5, signedUser.getGrade());
			ps.setString(6, signedUser.getContact());
			ps.setString(7, signedUser.getContactType());
			ps.setString(8, signedUser.getCity());
			ps.setString(9, signedUser.getIndustry());
			ps.setString(10, signedUser.getHobby());
			ps.setString(11, signedUser.getProfession());
			ps.setString(12, signedUser.getSex());
			ps.setString(13, signedUser.getOpenId());
			update=ps.executeUpdate();
			logger.debug("====更新用户修改的个人信息【成功】====");
		} catch (SQLException e) {
			logger.error("====更新用户修改的个人信息【失败】====");
			logger.error(e.toString());
		}finally {
			//释放资源
			releaseResources(conn, ps, rs);
		}
		return update;
	}
	
	/**
	 * 获取对应id的某一个活动
	 * @return List<Activity>
	 */
	public Activity getTheActivity(int id){
		logger.debug("====获取对应id的某一个活动====");
		String sqlStr="select * from activity where id=?";
		Activity activity=new Activity();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn.prepareStatement(sqlStr);
			ps.setInt(1, id);
			rs=ps.executeQuery();
			while (rs.next()) {
				activity.setActivityName(rs.getString("activity_name"));
				activity.setActivityAddress(rs.getString("activity_address"));
				activity.setStartTime(rs.getString("start_time"));
				activity.setEndTime(rs.getString("end_time"));
				activity.setActivityIntro(rs.getString("activity_intro"));
				activity.setActivityHolder(rs.getString("activity_holder"));
			}
			logger.debug("====获取对应id的某一个活动【成功】====");
		} catch (SQLException e) {
			logger.error("====获取对应id的某一个活动【失败】====");
			logger.error(e.toString());
		}finally {
			//释放资源
			releaseResources(conn, ps, rs);
		}
		return activity;
	}
	
	/**
	 * 保存校友活动的信息
	 * @param signedUser
	 * @return  int
	 */
	public int saveActivity(Activity activity){
		logger.debug("====保存校友活动的信息====");
//		openId headImgUrl userName phone QQ eMail city industry hobby profession sex
		String sqlStr = "insert into activity (openid,activity_name,"
				+ "activity_address,start_time,end_time,activity_intro,activity_holder) values(?,?,?,?,?,?,?)";
		int insert=-1;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn.prepareStatement(sqlStr);
			ps.setString(1, activity.getOpenID());
			ps.setString(2, activity.getActivityName());
			ps.setString(3, activity.getActivityAddress());
			ps.setString(4, activity.getStartTime());
			ps.setString(5, activity.getEndTime());
			ps.setString(6, activity.getActivityIntro());
			ps.setString(7, activity.getActivityHolder());
			
			insert=ps.executeUpdate();
			logger.debug("====保存校友活动的信息【成功】====");
		} catch (SQLException e) {
			logger.error("====保存校友活动的信息【失败】====");
		}finally {
			//释放资源
			releaseResources(conn, ps, rs);
		}
		return insert;
	}
	
	
	/**
	 * 更新校友活动信息
	 * @param signedUser
	 * @return 
	 */
	public int updateActivity(Activity activity){
		logger.debug("====更新校友活动信息====");
		String sqlStr="update activity set activity_name=?, activity_address=?, start_time=?, end_time=?, activity_intro=? where id=?";
		int update=-1;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn.prepareStatement(sqlStr);
			ps.setString(1, activity.getActivityName());
			ps.setString(2, activity.getActivityAddress());
			ps.setString(3, activity.getStartTime());
			ps.setString(4, activity.getEndTime());
			ps.setString(5, activity.getActivityIntro());
			ps.setInt(6, activity.getId());
			
			update=ps.executeUpdate();
			logger.debug("====更新校友活动信息【成功】====");
		} catch (SQLException e) {
			logger.error("====更新校友活动信息【失败】====");
			logger.error(e.toString());
		}finally {
			//释放资源
			releaseResources(conn, ps, rs);
		}
		return update;
	}
	
	/**
	 * 删除校友活动信息
	 * @param signedUser
	 * @return int
	 */
	public int deleteActivity(int id){
		logger.debug("====删除校友活动信息====");
		String sqlStr="delete from activity where id =?";
		int delete=-1;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn.prepareStatement(sqlStr);
			ps.setInt(1, id);
			delete=ps.executeUpdate();
			logger.debug("====删除校友活动信息【成功】====");
		} catch (SQLException e) {
			logger.error("====删除校友活动信息【失败】====");
			logger.error(e.toString());
		}finally {
			//释放资源
			releaseResources(conn, ps, rs);
		}
		return delete;
	}
	
	/**
	 * 获取某微信用户发起过的校友活动
	 * @return List<Activity>
	 */
	public List<Activity> getSomeActivity(String openid){
		logger.debug("====获取某微信用户发起过的校友活动====");
		String sqlStr="select * from activity where openid=? order by id desc";

		List<Activity> activitys=new ArrayList<Activity>();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn.prepareStatement(sqlStr);
			ps.setString(1, openid);
			rs=ps.executeQuery();
			while (rs.next()) {
				Activity activity=new Activity();
				activity.setOpenID(rs.getString("openid"));
				activity.setActivityAddress(rs.getString("activity_address"));
				activity.setActivityHolder(rs.getString("activity_holder"));
				activity.setActivityIntro(rs.getString("activity_intro"));
				activity.setActivityName(rs.getString("activity_name"));
				activity.setStartTime(rs.getString("start_time"));
				activity.setEndTime(rs.getString("end_time"));
				activity.setId(rs.getInt("id"));
				activitys.add(activity);	
			}
			logger.debug("====获取某微信用户发起过的校友活动【成功】====");
		} catch (SQLException e) {
			logger.error("====获取某微信用户发起过的校友活动【失败】====");
			logger.error(e.toString());
		}finally {
			//释放资源
			releaseResources(conn, ps, rs);
		}
		return activitys;
	}
	
	/**
	 * 获取所有校友活动信息
	 * @return List<Activity>
	 */
	public List<Activity> getAllActivity(){
		logger.debug("====获取所有校友活动信息====");
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String time = format.format(date);	
		String sqlStr="select * from activity where end_time > '" + time + "' order by start_time";
		List<Activity> activitys=new ArrayList<Activity>();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn.prepareStatement(sqlStr);
			rs=ps.executeQuery();
			while (rs.next()) {
				Activity activity=new Activity();
				activity.setActivityAddress(rs.getString("activity_address"));
				activity.setActivityHolder(rs.getString("activity_holder"));
				activity.setActivityIntro(rs.getString("activity_intro"));
				activity.setActivityName(rs.getString("activity_name"));
				activity.setStartTime(rs.getString("start_time"));
				activity.setEndTime(rs.getString("end_time"));
				activitys.add(activity);	
			}
			logger.debug("====获取所有校友活动信息【成功】====");
		} catch (SQLException e) {
			logger.error("====获取所有校友活动信息【失败】====");
			logger.error(e.toString());
		}finally {
			//释放资源
			releaseResources(conn, ps, rs);
		}
		return activitys;
	}
	
	/**
	 * 保存推荐二维码扫描后用户之间的关系
	 * @param provider 二维码生成者   
	 * @param receiver 二维码扫描者
	 * @param time 扫描时间   
	 * @param ticket 二维码参数
	 * @param providerName 生成者的昵称  
	 * @param receiverName 扫描者的昵称
	 * @return  
	 */
	public int saveQRCodeParmer(String provider,String receiver,String ticket,String time,String providerName,String receiverName){
		logger.debug("====保存推荐二维码扫描后用户之间的关系====");
		String sqlStr = "insert into UserRelation (qr_provider,qr_receiver,"
		+ "qr_ticket,qr_time,qr_provider_name,qr_receiver_name) values(?,?,?,?,?,?)";
		int insert=-1;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn.prepareStatement(sqlStr);
			ps.setString(1, provider);
			ps.setString(2, receiver);
			ps.setString(3, ticket);
			ps.setString(4, time);
			ps.setString(5, providerName);
			ps.setString(6, receiverName);
		
			insert=ps.executeUpdate();
			logger.debug("====保存推荐二维码扫描后用户之间的关系【成功】====");
		} catch (SQLException e) {
			logger.error("====保存推荐二维码扫描后用户之间的关系【失败】====");
			logger.error(e.toString());
		}finally {
			//释放资源
			releaseResources(conn, ps, rs);
		}
		return insert;
	}
	
	/**
	 * 根据姓名查找校友
	 * @param name 校友姓名
	 * @return List<SignedUser> 所有对应名字的校友集合
	 */
	public List<SignedUser> searchSchoolMate(String name){
		logger.debug("====根据姓名查找校友====");
		String sqlStr="select * from signed_users where userName=?";
		List<SignedUser> signedUsers=new ArrayList<SignedUser>();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn.prepareStatement(sqlStr);
			ps.setString(1, name);
			rs=ps.executeQuery();
			while (rs.next()) {
				SignedUser signedUser=new SignedUser();
				signedUser.setOpenId(rs.getString("openId"));
				signedUser.setHeadImgUrl(rs.getString("headImgUrl"));
				signedUser.setUserName(rs.getString("userName"));
				signedUser.setCollege(rs.getString("college"));
				signedUser.setUserClass(rs.getString("userClass"));
				signedUser.setGrade(rs.getString("grade"));
				signedUser.setCity(rs.getString("city"));
				signedUser.setContact(rs.getString("contact"));
				signedUser.setContactType(rs.getString("contactType"));
				signedUser.setIndustry(rs.getString("industry"));
				signedUser.setHobby(rs.getString("hobby"));
				signedUser.setProfession(rs.getString("profession"));
				signedUser.setSex(rs.getString("sex"));
				signedUsers.add(signedUser);
			}
			logger.debug("====根据姓名查找校友【成功】====");
		} catch (SQLException e) {
			logger.error("====根据姓名查找校友【失败】====");
			logger.error(e.toString());
		}finally {
			//释放资源
			releaseResources(conn, ps, rs);
		}
		return signedUsers;
	}
	
	/**
	 * 模糊搜索返回带相同字的校友名字
	 * @param name
	 * @return List<String>
	 */
	public JSONObject getAlumnisName(String name){
		logger.debug("====模糊搜索返回带相同字的校友名字====");
		String sqlStr="select openId,userName,headImgUrl from signed_users where userName like ?";
//		List<String> names=new ArrayList<String>();
		JSONObject jsonObject=new JSONObject();
		if (!("".equals(name))) {
			JSONArray array=new JSONArray();
			PreparedStatement ps=null;
			ResultSet rs=null;
			try {
				ps=conn.prepareStatement(sqlStr);
				ps.setString(1, "%"+name+"%");
				rs=ps.executeQuery();
				while (rs.next()) {
//					names.add(rs.getString("userName"));
//					System.out.println(rs.getString("userName"));
					JSONObject sub=new JSONObject();
					sub.put("openId", rs.getString("openId"));
					sub.put("userName", rs.getString("userName"));
					sub.put("headImgUrl", rs.getString("headImgUrl"));
					array.add(sub);
				}
				jsonObject.put("users", array);
				logger.debug("====模糊搜索返回带相同字的校友名字【成功】====");
			} catch (SQLException e) {
				jsonObject=null;
				logger.error("====模糊搜索返回带相同字的校友名字【失败】====");
				logger.error(e.toString());
			}finally {
				//释放资源
				releaseResources(conn, ps, rs);
			}
		}else {
			JSONArray none=new JSONArray();
			jsonObject.put("users", none);
			logger.error("====【输入名字为空】====");
		}
		
		return jsonObject;
	}
	
	/**
	 * 获取所有校友，显示在查找校友主页
	 * @return List<Alumnus>
	 */
	public List<Alumnus> getAllAlumnus(){
		logger.debug("====获取所有校友，显示在查找校友主页====");
		String sqlStr="SELECT openId,userName,headImgUrl FROM signed_users ORDER BY CONVERT(userName USING gbk ) COLLATE gbk_chinese_ci ASC";
		List<Alumnus> alumnus=new ArrayList<Alumnus>();
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn.prepareStatement(sqlStr);
			rs=ps.executeQuery();
			while (rs.next()) {
				Alumnus alumni=new Alumnus();
				alumni.setOpenId(rs.getString("openId"));
				alumni.setUserName(rs.getString("userName"));
				alumni.setHeadImgUrl(rs.getString("headImgUrl"));
				alumnus.add(alumni);
			}
			logger.debug("====获取所有校友，显示在查找校友主页【成功】====");
		} catch (SQLException e) {
			logger.error("====获取所有校友，显示在查找校友主页【失败】====");
			logger.error(e.toString());
		}finally {
			//释放资源
			releaseResources(conn, ps, rs);
		}
		return alumnus;
	}
	
	
	/**
	 * 向远程数据库新建数据表
	 * 
	 * @param createSqlStr 创建数据表Sql语句
	 * 
	 *@说明 如: String sql = "CREATE TABLE REGISTRATION " +
     *               "(id INTEGER not NULL, " +
     *               " first VARCHAR(255), " + 
     *               " last VARCHAR(255), " + 
     *               " age INTEGER, " + 
     *               " PRIMARY KEY ( id ))"; 
     *               
     *@return createIndex
	 */
	public static int createTable2RemoteDatabase(String createSqlStr){
		logger.debug("====向远程数据库新建数据表====");
		int createIndex=-1;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn.prepareStatement(createSqlStr);
			createIndex=ps.executeUpdate();
			logger.debug("====向远程数据库新建数据表【成功】====");
		} catch (SQLException e) {
			logger.error("====向远程数据库新建数据表【失败】====");
			logger.error(e.toString());
		}finally {
			//释放资源
			releaseResources(conn, ps, rs);
		}
		return createIndex;
	}
	
	
	/**
	 * 增删改数据表
	 * @param strSql SQL语句   
	 * 
	 * @插入语句 "insert into user_location values"+"("+"'"+open_id+"','"+lng+"','"+lat+"','"+bd09_lng+"','"+bd09_lat+"'"+")"
	 * @删除语句 "delete from user_location where open_id"+"="+"'"+number+"'"
	 * @更新语句 "update user_location set bd09_lng = "+newbd09_lng+"where open_id="+"'"+user_open_id+"'"
	 * 
	 * @return insertIndex 操作的位置
	 */
	public  int cruNewData(String strSql){
		logger.debug("====增删改数据表====");
		int index=0;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn.prepareStatement(strSql);
			index=ps.executeUpdate();
			logger.debug("====增删改数据表【成功】====");
		} catch (SQLException e) {
			logger.error("====增删改数据表【失败】====");
			logger.error(e.toString());
		}finally {
			//释放资源
			releaseResources(conn, ps, rs);
		}
		return index;
	}
	
	
	/**
	 * 顺序查询数据表
	 * 
	 * @param strSql 查询语句
	 * @查询语句 "select * from user_location"
	 * 
	 * @return ResultSet 返回结果集对象实例
	 */
	public static ResultSet queryData(String strSql){
		logger.debug("====顺序查询数据表====");
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			ps=conn.prepareStatement(strSql);
			rs=ps.executeQuery();
			logger.debug("====顺序查询数据表【成功】====");
		} catch (SQLException e) {
			logger.error("====顺序查询数据表【失败】====");
			logger.error(e.toString());
		}
		return rs;
	}
}
