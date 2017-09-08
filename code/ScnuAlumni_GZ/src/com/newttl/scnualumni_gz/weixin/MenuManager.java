package com.newttl.scnualumni_gz.weixin;


import com.newttl.scnualumni_gz.bean.menu.Button;
import com.newttl.scnualumni_gz.bean.menu.ClickButton;
import com.newttl.scnualumni_gz.bean.menu.ComplexButton;
import com.newttl.scnualumni_gz.bean.menu.Menu;
import com.newttl.scnualumni_gz.bean.menu.ViewButton;
import com.newttl.scnualumni_gz.bean.pojo.Token;
import com.newttl.scnualumni_gz.logs.ScnuAlumniLogs;
import com.newttl.scnualumni_gz.util.CommonUtil;
import com.newttl.scnualumni_gz.util.MenuUtil;
import com.newttl.scnualumni_gz.weixin.WeiXinCommon;

/**
 * 菜单管理类
 * 
 * @author lgc
 *
 * @date 2017年6月9日 上午9:48:56
 */
public class MenuManager {

//	private static Logger log = LoggerFactory.getLogger(MenuManager.class);
	
	/**
	 * 定义菜单
	 * @return
	 */
	private static Menu getMenu(){
		
		ViewButton b11=new ViewButton();
		b11.setName("华师新闻");
		b11.setType("view");
		b11.setUrl("http://news.scnu.edu.cn");
		
		ClickButton b21=new ClickButton();
		b21.setName("二维码");
		b21.setType("click");
		b21.setKey("qrcode");
		
		ViewButton b31=new ViewButton();
		String url0=WeiXinCommon.OAUTH_URL;
		String url1=CommonUtil.urlEncodingUTF8(WeiXinCommon.signUpUrl);
		url0=url0.replace("APPID",WeiXinCommon.appID);
		url0=url0.replace("REDIRECT_URI", url1);
		b31.setName("个人中心");
		b31.setType("view");
		b31.setUrl(url0);
		ScnuAlumniLogs.getLogger().debug("[个人中心]--url::"+"\n"+url0);
		
		ViewButton b32=new ViewButton();
		String ur32=WeiXinCommon.OAUTH_URL;
		String ur33=CommonUtil.urlEncodingUTF8(WeiXinCommon.activityUrl);
		ur32=ur32.replace("APPID",WeiXinCommon.appID);
		ur32=ur32.replace("REDIRECT_URI", ur33);
		b32.setName("活动中心");
		b32.setType("view");
		b32.setUrl(ur32);
		ScnuAlumniLogs.getLogger().debug("[活动中心]--url::"+"\n"+ur32);
		
		ViewButton b33 = new ViewButton();
		b33.setName("查找校友");
		b33.setType("view");
		b33.setUrl(WeiXinCommon.SchoolMateUrl);
		
		ComplexButton mainB3=new ComplexButton();
		mainB3.setName("工具箱");
		mainB3.setSub_button(new Button[]{b33,b32,b31});
		
		Menu menu=new Menu();
		menu.setButton(new Button[]{b11,b21,mainB3});
		
		return menu;
	}
	
	
	public static void main(String[] args) {
		//第三方用户唯一凭证appID
		String appID=WeiXinCommon.appID;
		//第三方用户唯一凭证密钥appsecret
		String appsecret=WeiXinCommon.appsecret;
		
		//通过凭证 appID appsecret获取 access_token
		Token token=CommonUtil.getToken(appID, appsecret);
		
		if (token!=null) {
			String accessToken=token.getAccess_token();
			//创建菜单
			boolean createResult=MenuUtil.createMenu(getMenu(), accessToken);
			if (createResult) {
				ScnuAlumniLogs.getLogger().debug("创建菜单--成功");
			}else {
				ScnuAlumniLogs.getLogger().debug("创建菜单--失败");
			}
			
		}
		
		
	}
	
}
