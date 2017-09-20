package com.newttl.scnualumni_gz.util;


import org.apache.log4j.Logger;

import com.newttl.scnualumni_gz.bean.menu.Menu;
import com.newttl.scnualumni_gz.bean.menu.PersonalMenu;
import com.newttl.scnualumni_gz.logs.ScnuAlumniLogs;
import com.newttl.scnualumni_gz.util.CommonUtil;

import net.sf.json.JSONObject;

/**
 * 自定义菜单操作工具类
 * 
 * @author lgc
 *
 * @date 2017年6月9日 下午2:29:10
 */
public class MenuUtil {
	private static Logger logger=ScnuAlumniLogs.getLogger();
	//创建菜单  POST（请使用https协议）
	private final static String menu_create_url=" https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	//查询菜单  GET (获得默认菜单和全部个性化菜单信息)
	private final static String menu_get_url="https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	//删除菜单 GET (删除所有自定义菜单,包括默认菜单和全部个性化菜单)
	private final static String menu_delete_url="https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	//创建个性化菜单  POST
	private final static String create_personal_menu="https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token=ACCESS_TOKEN";
	//删除个性化菜单 POST
	private final static String delete_personal_menu="https://api.weixin.qq.com/cgi-bin/menu/delconditional?access_token=ACCESS_TOKEN";
	//测试个性化菜单匹配结果 POST
	private final static String match_personal_menu="https://api.weixin.qq.com/cgi-bin/menu/trymatch?access_token=ACCESS_TOKEN";
	
	/**
	 * 创建默认菜单
	 * @param menu 菜单对象
	 * @param access_token 凭证
	 * @return true 成功; false 失败
	 * @成功时返回      {"errcode":0,"errmsg":"ok"}
	 * @失败时返回      {"errcode":40018,"errmsg":"invalid button name size"}
	 */
	public static boolean createMenu(Menu menu,String access_token){
		logger.debug("====创建默认菜单====");
		boolean result=false;
		String url=menu_create_url.replace("ACCESS_TOKEN", access_token);
		//将菜单对象转换为json数据
		String jsonMenu=JSONObject.fromObject(menu).toString();
		logger.debug("默认菜单数据-jsonMenu::"+jsonMenu);
		//发起HTTPS的POST请求，创建菜单
		JSONObject jsonResult= CommonUtil.httpsRequest(url, "POST", jsonMenu);
		if (jsonResult != null) {
			int errcode=jsonResult.getInt("errcode");
			String errmsg=jsonResult.getString("errmsg");
			if (errcode == 0) {
				result=true;
				logger.debug("====创建默认菜单【成功】====");
			}else {
				result=false;
				logger.error("====创建默认菜单【失败】====");
			}
			logger.debug(jsonResult.toString());
		}
		return result;
	}
 
	/**
	 * 查询菜单
	 * @param access_token 凭证
	 * @return 查询到返回菜单json数据，否则返回null
	 */
	public static String getMenu(String access_token){
		logger.debug("====查询菜单====");
		String result=null;
		String url=menu_get_url.replace("ACCESS_TOKEN", access_token);
		//发起HTTPS的GET请求，获取菜单json数据
		JSONObject jsonObject=CommonUtil.httpsRequest(url, "GET", null);
		if (jsonObject !=null) {
			result=jsonObject.toString();
		}
		logger.debug("菜单::"+result);
		return result;
	}
	
	/**
	 * 删除菜单
	 * @param access_token 接口凭证
	 * @return
	 */
	public static boolean deleteMenu(String access_token){
		logger.debug("====删除菜单====");
		boolean result=false;
		String url=menu_delete_url.replace("ACCESS_TOKEN", access_token);
		//发起HTTPS的GET请求，删除菜单
		JSONObject jsonObject=CommonUtil.httpsRequest(url, "GET", null);
		if (jsonObject !=null) {
			int errcode=jsonObject.getInt("errcode");
			String errmsg=jsonObject.getString("errmsg");
			if (errcode == 0) {
				result=true;
				logger.debug("====删除菜单【成功】====");
			}else {
				result=false;
				logger.error("====删除菜单【失败】====");
			}
		}
		logger.debug(jsonObject.toString());
		return result;
		
	}
	
	/**
	 * 创建个性化菜单
	 * @param personalMenu 个性化菜单
	 * @param access_token 接口凭证
	 * @return 返回 JSONObject 对象
	 * @成功返回: {"menuid":"208379533"}
	 * @失败返回: 相应的错误码和错误信息
	 */
	public static JSONObject createPersonalMenu(PersonalMenu personalMenu,String access_token){
		logger.debug("====创建个性化菜单====");
		JSONObject respJson=null;
		String url=create_personal_menu.replace("ACCESS_TOKEN", access_token);
		String jsonMenu=JSONObject.fromObject(personalMenu).toString();
		logger.debug("个性化菜单数据-jsonMenu::"+jsonMenu);
		//发起请求
		JSONObject jsonObject=CommonUtil.httpsRequest(url, "POST", jsonMenu);
		if (jsonObject != null) {
			respJson = jsonObject;
			if (jsonObject.containsKey("menuid")) {
				logger.debug("====创建个性化菜单【成功】====");
			}else {
				logger.error("====创建个性化菜单【失败】====");
			}
		}
		logger.debug(respJson.toString());
		return respJson;

	}
	
	/**
	 * 删除个性菜单
	 * @param menuId 个性化菜单id
	 * @param access_token 接口凭证
	 * @return true|false
	 */
	public static boolean delPersonalMenu(String menuId,String access_token){
		logger.debug("====删除个性菜单====");
		boolean result=false;
		String url=delete_personal_menu.replace("ACCESS_TOKEN", access_token);
		String jsonData="{\"menuid\":\"%s\"}";
		jsonData=String.format(jsonData, menuId);
		//发起请求 
		JSONObject jsonObject=CommonUtil.httpsRequest(url, "POST", jsonData);
		if (jsonObject != null) {
			int errcode=jsonObject.getInt("errcode");
			if (errcode == 0) {
				result=true;
				logger.debug("====删除个性菜单【成功】====");
			}else {
				result=false;
				logger.error("====删除个性菜单【失败】====");
			}
		}
		logger.debug(jsonObject.toString());
		return result;
	}
	/**
	 * 测试个性化菜单匹配结果
	 * @param userId 粉丝的openId或者微信号
	 * @param access_token 接口凭证
	 * @return JSONObject 对象
	 */
	public static JSONObject matchPersonalMenu(String userId,String access_token){
		logger.debug("====测试个性化菜单匹配结果====");
		JSONObject respJson=null;
		String url=match_personal_menu.replace("ACCESS_TOKEN", access_token);
		String jsonData="{\"user_id\":\"%s\"}";
		jsonData=String.format(jsonData, userId);
		//发起请求 
		JSONObject jsonObject=CommonUtil.httpsRequest(url, "POST", jsonData);
		if (jsonObject != null) {
			respJson=jsonObject;
			if (jsonObject.containsKey("errcode")) {
				logger.error("====测试个性化菜单匹配结果【失败】====");
			}else {
				logger.debug("====测试个性化菜单匹配结果【成功】====");
			}
		}
		logger.debug(respJson.toString());
		return respJson;
	}
	
}
