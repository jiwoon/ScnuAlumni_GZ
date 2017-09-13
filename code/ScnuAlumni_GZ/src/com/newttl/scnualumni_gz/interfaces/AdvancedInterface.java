
package com.newttl.scnualumni_gz.interfaces;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.newttl.scnualumni_gz.bean.pojo.SNSUserInfo;
import com.newttl.scnualumni_gz.bean.pojo.WeiXinGroups;
import com.newttl.scnualumni_gz.bean.pojo.WeiXinMedia;
import com.newttl.scnualumni_gz.bean.pojo.WeiXinOauth2Token;
import com.newttl.scnualumni_gz.bean.pojo.WeiXinPermanentQRCode;
import com.newttl.scnualumni_gz.bean.pojo.WeiXinTemporaryQRCode;
import com.newttl.scnualumni_gz.bean.pojo.WeiXinUserInfo;
import com.newttl.scnualumni_gz.bean.pojo.WeiXinUserList;
import com.newttl.scnualumni_gz.bean.response.Article;
import com.newttl.scnualumni_gz.bean.response.Music;

import net.sf.json.JSONObject;

/**
 * 公众号高级接口接口类
 * @author lgc
 *
 * 2017年7月20日 下午5:47:40
 */
public interface AdvancedInterface {

	/**
	 * 组装文本客服消息
	 * 
	 * @param openId 消息发送的对象
	 * @param content 文本内容
	 * @return String
	 */
	String makeTextCustomMessage(String openId,String content);
	
	/**
	 * 组装图片客服消息
	 * 
	 * @param openId 消息发送对象
	 * @param mediaId 媒体文件id
	 * @return String
	 */
	String makeImageCustomMessage(String openId, String mediaId);
	
	/**
	 * 组装语音客服消息
	 * 
	 * @param openId 消息发送对象
	 * @param mediaId 媒体文件id
	 * @return String
	 */
	String makeVoiceCustomMessage(String openId, String mediaId);
	
	/**
	 * 组装视频客服消息
	 * 
	 * @param openId 消息发送对象
	 * @param mediaId 媒体文件id
	 * @param thumbMediaId 视频消息缩略图的媒体id
	 * @return String
	 */
	String makeVideoCustomMessage(String openId, String mediaId, String thumbMediaId);
	
	/**
	 * 组装音乐客服消息
	 * 
	 * @param openId 消息发送对象
	 * @param music 音乐对象
	 * @return String
	 */
	String makeMusicCustomMessage(String openId, Music music);
	
	/**
	 * 组装图文客服消息      (点击跳转到外链)
	 * 
	 * @param openId 消息发送对象
	 * @param articleList 图文消息列表
	 * @return String
	 */
	String makeNewsCustomMessage(String openId, List<Article> articleList);
	
	/**
	 * 组装图文客服消息  (跳转到图文页面)
	 * 
	 * @param openId  消息发送对象
	 * @param mediaId 媒体文件id
	 * @return String
	 */
	String makeMpNewsCustomMessage(String openId,String mediaId);
	
	/**
	 * 组装卡券客服消息
	 * 
	 * @param openId  消息发送对象
	 * @param cardId  卡卷id
	 * @return String
	 */
	String makeWxCardCustomMessage(String openId,String cardId);
	
	/**
	 * 发送客服消息
	 * 
	 * @param accessToken 接口访问凭证
	 * @param jsonMsg json格式的客服消息（包括touser、msgtype和消息内容）
	 * @return true | false
	 */
	boolean sendCustomMessage(String accessToken, String jsonMsg);
	
	/**
	 * 获取网页授权凭证
	 * 
	 * @param appId  公众号的 appID
	 * @param appSecret 公众号的 appsecret
	 * @param code 用户同意授权，获取的code
	 * @return WeiXinOauth2Token  返回网页授权信息类实例
	 */
	WeiXinOauth2Token getOauth2AccessToken(String appId,String appSecret,String code);
	
	/**
	 * 刷新网页授权凭证
	 *  
	 * @param appId 公众号的 appID
	 * @param refreshToken  公众号的 appsecret
	 * @return WeiXinOauth2Token 返回网页授权信息类实例
	 */
	WeiXinOauth2Token refreshOauth2AccessToken(String appId,String refreshToken);
	
	/**
	 * 通过网页授权拉取用户信息
	 * 
	 * @param accessToken  网页授权接口调用凭证
	 * @param openId 用户的唯一标识
	 * @param language 返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
	 * @return SNSUserInfo 返回用户实例对象
	 */
	SNSUserInfo  getSNSUserInfo(String accessToken,String openId,String language);
	
	/**
	 * 创建临时带参数二维码
	 * 
	 * @param accessToken  接口访问凭证 (CommonUtil.getToken)
	 * @param expireSeconds  该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒。
	 * @param sceneId  场景值ID，临时二维码时为32位非0整型
	 * @return WeiXinTemporaryQRCode 对象实例
	 */
	WeiXinTemporaryQRCode createTemporaryQRCode(String accessToken,int expireSeconds,int sceneId);
	
	/**
	 * 创建永久带参数二维码
	 * sceneId 场景值ID为  int 类型
	 * @param accessToken 接口访问凭证 (CommonUtil.getToken)
	 * @param sceneId 场景值ID 永久二维码时最大值为100000（目前参数只支持1--100000）
	 * @return WeiXinPermanentQRCode 对象实例
	 * 
	 * @请求提交 POST 的参数 如 {"action_name": "QR_LIMIT_SCENE", "action_info": {"scene": {"scene_id": 123}}}
	 */
	WeiXinPermanentQRCode createPermanentQRCode(String accessToken,int sceneId);
	
	/**
	 * 创建永久带参数二维码
	 * sceneStr 场景值ID为  String 类型
	 * 
	 * @param accessToken 接口访问凭证  (CommonUtil.getToken)
	 * @param sceneStr 场景值ID（字符串形式的ID），字符串类型，长度限制为1到64，仅永久二维码支持此字段   
	 * @return WeiXinPermanentQRCode 对象实例
	 * 
	 *  @请求提交 POST 的参数 如 {"action_name": "QR_LIMIT_STR_SCENE", "action_info": {"scene": {"scene_str": "123"}}}
	 */
	WeiXinPermanentQRCode createPermanentQRCode(String accessToken,String sceneStr);
	
	/**
	 * 获取二维码的media_id
	 * @param String user 用户微信id
	 * @param String appID 
	 * @param String appSecret 
	 * @return string
	 */
	String getQRid(String user, String appID,String appSecret) throws Exception;
	
	/**
	 * 根据 ticket 获取二维码
	 * 
	 * @param ticket 二维码ticket
	 * @param savePath 保存路径
	 * @return filePath 文件名
	 */
	String getQRCode(String ticket,String savePath);
	
	/**
	 * 获取用户基本信息
	 * 
	 * @param accessToken 接口访问凭证  (CommonUtil.getToken)
	 * @param openId 用户的标识
	 * @return WeiXinUserInfo 返回用户信息对象实例
	 */
	WeiXinUserInfo getUserInfo(String accessToken,String openId);
	
	/**
	 * 拉取关注用户列表
	 * 
	 * @param accessToken 接口凭证
	 * @param nextOpenId 第一个拉取的OPENID，不填默认从头开始拉取
	 * @return 返回WeiXinUserList实例对象
	 */
	WeiXinUserList getWeiXinUserList(String accessToken,String nextOpenId);
	
	/**
	 * 获取用户分组信息
	 * 
	 * @param accessToken 接口凭证
	 * @return 返回 List<WeiXinGroups> 分组列表
	 */
	List<WeiXinGroups> getWeiXinGroups(String accessToken);
	
	/**
	 * 创建分组
	 * @说明  需要 POST 数据 {"group":{"name":"groupName"}}
	 * 
	 * @param accessToken 接口凭证
	 * @param groupName 分组名称
	 * @return  WeiXinGroups 返回分组实例对象
	 */
	WeiXinGroups createGroup(String accessToken,String groupName);
	
	/**
	 * 修改指定 id 的分组的对应名称
	 * @说明  需要 POST 数据 {"group":{"id":108,"name":"groupName"}}
	 * 
	 * @param accessToken 接口凭证
	 * @param groupId 分组的id
	 * @param groupName 修改后的分组名称
	 * @return 返回 true 表示修改成功;false 表示修改失败
	 */
	boolean updateGroup(String accessToken,int groupId,String groupName);
	
	/**
	 * 移动指定openId的用户到指定groupId的分组中
	 * 
	 * @param accessToken 接口凭证
	 * @param opendId 用户标识
	 * @param groupId 分组的id
	 * @return true 表示移动成功,false 表示移动失败
	 * 
	 * @说明 需要提交的json数据  "{\"openid\":\"%s\",\"to_groupid\":%d}"
	 */
	boolean moveMemberGroup(String accessToken,String openId,int groupId);
	
	/**
	 * 上传图文消息素材
	 * @param articlesJson 图文消息json数据
	 * @param accessToken 接口凭证
	 * @return 返回 JSONObject 对象
	 */
	JSONObject uploadNewsArticles(String articlesJson,String accessToken);
	
	/**
	 * 上传群发接口图文消息中的图片
	 * @param accessToken 接口凭证
	 * @param mediaFileUrl 图片的原始路径(图片为jpg/png格式)
	 * @return 图文消息中图片的url
	 */
	JSONObject uploadNewsImage(String accessToken,String mediaFileUrl);
	
	/**
	 * 根据标签tag_id来群发消息
	 * @param massJson 群发消息的json数据
	 * @param accessToken 接口凭证
	 * @return 返回 JSONObject 对象
	 */
	JSONObject massByTag(String massJson,String accessToken);
	
	/**
	 * 根据openid列表来群发消息
	 * @param massJson 群发消息的json数据
	 * @param accessToken 接口凭证
	 * @return 返回 JSONObject 对象
	 */ 
	JSONObject massByOpenIdList(String massJson,String accessToken);
	
	/**
	 * 指定用户预览群发消息
	 * @param previewJson 预览接口的post数据
	 * @param accessToken 接口凭证
	 * @return 返回JSONObject对象 
	 */
	JSONObject massOpenIdpreview(String previewJson,String accessToken);
	
	/**
	 * 上传媒体文件到微信服务器
	 * 
	 * @param accessToken 接口凭证
	 * @param type 媒体文件类型( 有图片（image）、语音（voice）、视频（video）和缩略图（thumb）)
	 * @param mediaFileUrl 媒体文件的路径
	 * @return WeiXinMedia 返回WeiXinMedia对象实例
	 * 
	 * @说明 http请求方式：POST/FORM，使用https
	 * 目前只成功上传了 image 类型
	 * 
	 */
	WeiXinMedia uploadMedia(String accessToken, String type, String mediaFileUrl);
	
	/**
	 * 获取活动海报的media_id
	 * @param String content 海报内容
	 * @param String accessToken 公众号凭证
	 * @return WeiXinMedia.getMediaId()
	 */
	String getActivityImgId(String content,String accessToken) throws Exception;
	

	/**
	 * 从微信服务器上下载对应 mediaId 的媒体文件
	 * 
	 * @param accessToken 接口凭证
	 * @param mediaId 媒体文件id
	 * @param savePath 保存文件位置
	 * @return filePath 返回文件的路径
	 * 
	 * @说明 可下载用户发送过来的媒体文件
	 */
	String downLoadMedia(String accessToken, String mediaId, String savePath);
	
	/**
	 * 对api调用次数清零
	 * @param accessToken 接口凭证
	 * @param appId 公众号appid
	 * @return
	 */
	JSONObject clearQuota(String accessToken,String appId);
}
