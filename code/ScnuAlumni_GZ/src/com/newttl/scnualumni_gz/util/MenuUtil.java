package com.newttl.scnualumni_gz.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newttl.scnualumni_gz.bean.menu.Menu;
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
	
	//创建菜单  POST（请使用https协议）
	private final static String menu_create_url=" https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	//查询菜单  GET
	private final static String menu_get_url="https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	//删除菜单 GET
	private final static String menu_delete_url="https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	
	/**
	 * 创建菜单
	 * @param menu 菜单对象
	 * @param access_token 凭证
	 * @return true 成功; false 失败
	 *   成功时返回      {"errcode":0,"errmsg":"ok"}
	 *   失败时返回      {"errcode":40018,"errmsg":"invalid button name size"}
	 */
	public static boolean createMenu(Menu menu,String access_token){
		boolean result=false;
		String url=menu_create_url.replace("ACCESS_TOKEN", access_token);
		//将菜单对象转换为json数据
		String jsonMenu=JSONObject.fromObject(menu).toString();
		ScnuAlumniLogs.getLogger().debug("菜单数据-jsonMenu::"+jsonMenu);
		//发起HTTPS的POST请求，创建菜单
		JSONObject jsonResult= CommonUtil.httpsRequest(url, "POST", jsonMenu);
		if (jsonResult != null) {
			int errcode=jsonResult.getInt("errcode");
			String errmsg=jsonResult.getString("errmsg");
			if (errcode == 0) {
				result=true;
			}else {
				result=false;
				ScnuAlumniLogs.getLogger().error("创建菜单失败::"+jsonResult.toString());
			}
		}
		return result;
	}
 
	/**
	 * 查询菜单
	 * @param access_token 凭证
	 * @return 查询到返回菜单json数据，否则返回null
	 */
	public static String getMenu(String access_token){
		String result=null;
		String url=menu_get_url.replace("ACCESS_TOKEN", access_token);
		//发起HTTPS的GET请求，获取菜单json数据
		JSONObject jsonObject=CommonUtil.httpsRequest(url, "GET", null);
		if (jsonObject !=null) {
			result=jsonObject.toString();
		}
		return result;
	}
	
	
	public static boolean deleteMenu(String access_token){
		boolean result=false;
		String url=menu_delete_url.replace("ACCESS_TOKEN", access_token);
		//发起HTTPS的GET请求，删除菜单
		JSONObject jsonObject=CommonUtil.httpsRequest(url, "GET", null);
		if (jsonObject !=null) {
			int errcode=jsonObject.getInt("errcode");
			String errmsg=jsonObject.getString("errmsg");
			if (errcode == 0) {
				result=true;
			}else {
				result=false;
				ScnuAlumniLogs.getLogger().error("删除菜单失败::"+jsonObject.toString());
			}
		}
		return result;
		
	}
	
}
