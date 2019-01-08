/**
 * 
 */
package com.lottery.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lottery.dao.UnitMapper;
import com.lottery.dao.UserMapper;
import com.lottery.model.Buy;
import com.lottery.model.Unit;
import com.lottery.model.User;
import com.lottery.utils.HttpClientUtil;
import com.lottery.utils.JsonUtils;
import com.lottery.utils.StringUtils;

/**
 * @author ws_yu
 *
 */
@Service
public class WechatService {
	
	private static Logger logger = LoggerFactory.getLogger(WechatService.class);
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UnitMapper unitMapper;

	/**
	 * 小程序ID
	 */
	@Value("${wechat.appid}")
	private String appid;


	/**
	 * 客户端小程序ID
	 */
	@Value("${wechat.clientappid}")
	private String clientappid;
	
	/**
	 * 商户号
	 */
	@Value("${wechat.mechid}")
	private String mechid;
	
	/**
	 * 服务器ip
	 */
	@Value("${wechat.payip}")
	private String payip;
	
	/**
	 * 回调url
	 */
	@Value("${wechat.notifyUrl}")
	private String notifyUrl;
	
	/**
	 * 统一下单url
	 */
	@Value("${wechat.unifiedorderUrl}")
	private String unifiedorderUrl;
	
	/**
	 * 商户平台设置的密钥key
	 */
	@Value("${wechat.key}")
	private String key;
	
	/**
	 * 小程序唯一凭证密钥，即 AppSecret，获取方式同 appid
	 */
	@Value("${wechat.secret}")
	private String secret;



	/**
	 * 小程序唯一凭证密钥，即 AppSecret，获取方式同 appid
	 */
	@Value("${wechat.clientsecret}")
	private String clientsecret;

	/**
	 * 商户端调用凭证
	 */
	private String accessToken;
	
	/**
	 * 商户端调用凭证过期时间
	 */
	private Date accessTokenExpired;

	/**
	 * 客户端调用凭证
	 */
	private String clientaccessToken;

	/**
	 * 客户端调用凭证过期时间
	 */
	private Date clientaccessTokenExpired;



	/**
	 * 微信统一下单
	 * @param buy
	 * @return
	 */
	public String preOrder(Buy buy){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("appid", appid);
		paramMap.put("mch_id", mechid);
		paramMap.put("nonce_str", StringUtils.randomCode(24));
		Unit unit = unitMapper.selectByPrimaryKey(buy.getUnitid());
		if(unit!=null){
			paramMap.put("body", unit.getName());
		}
		paramMap.put("out_trade_no", buy.getOrdernum());
		paramMap.put("total_fee", unit.getPrice());
		paramMap.put("spbill_create_ip", payip);
		paramMap.put("notify_url", notifyUrl);
		paramMap.put("trade_type", "JSAPI");
		User user = userMapper.selectByPrimaryKey(buy.getUserid());
		paramMap.put("openid", user.getOpenid());
		paramMap.put("sign", sign(paramMap));
		StringBuffer buffer = new StringBuffer();
		Iterator<Entry<String, Object>> iterator = paramMap.entrySet().iterator();
		while(iterator.hasNext()){
			Entry<String, Object> entry = iterator.next();
			buffer.append(StringUtils.keyValueXml(entry.getKey(), entry.getValue()));
		}
		try{
			String response = HttpClientUtil.doPostwithxml(unifiedorderUrl, StringUtils.keyValueXml("xml", buffer.substring(0)));
			String returnCode = StringUtils.getValueFromXml(response, "return_code");
			if(!"SUCCESS".equals(returnCode)){
				logger.error("userid--"+user.getId()+"--phone--"+user.getPhone()+"--微信统一下单失败--return_code--"+returnCode+"--return_msg--"+StringUtils.getValueFromXml(response,"return_msg"));
				return null;
			}
			String resultCode = StringUtils.getValueFromXml(response, "result_code");
			if(!"SUCCESS".equals(resultCode)){
				logger.error("userid--"+user.getId()+"--phone--"+user.getPhone()+"--微信统一下单失败--result_code--"+resultCode+"--err_code--"+StringUtils.getValueFromXml(response,"err_code")+"--err_code_des--"+StringUtils.getValueFromXml(response,"err_code_des"));
				return null;
			}
			return StringUtils.getValueFromXml(response, "prepay_id");
		}catch (Exception e) {
			logger.error("userid--"+user.getId()+"--phone--"+user.getPhone()+"--微信统一下单失败--exception--"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	/**
	 * 参数签名
	 * @param paramMap
	 * @return
	 */
	public String sign(Map<String, Object> paramMap){
		List<String> keyList = new ArrayList<String>();
		Iterator<Entry<String, Object>> iterator = paramMap.entrySet().iterator();
		while(iterator.hasNext()){
			keyList.add(iterator.next().getKey());
		}
		Collections.sort(keyList);
		StringBuffer buffer = new StringBuffer();
		for(String key: keyList){
			buffer.append("&"+key+"="+paramMap.get(key));
		}
		buffer.append("&key="+key);
		String param = buffer.substring(1);
		logger.info("签名字符串---"+param);
		return new SimpleHash("MD5", param).toString().toUpperCase();
	}
	
	/**
	 * 获取access_token
	 * @return
	 */
	public String fetchAccessToken(){
		if(!StringUtils.isNullOrNone(accessToken) && accessTokenExpired!=null && accessTokenExpired.after(new Date())){
			return accessToken;
		}else{
			Calendar calendar = Calendar.getInstance();
			StringBuffer buffer = new StringBuffer("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential");
			buffer.append("&appid=").append(appid).append("&secret=").append(secret);
			String response = HttpClientUtil.doGet(buffer.substring(0));
			HashMap<String,Object> response_map= JsonUtils.toObject(response,HashMap.class);
			if(response_map.containsKey("access_token")){
				accessToken = String.valueOf(response_map.get("access_token"));
				int expires_in = Integer.parseInt(String.valueOf(response_map.get("expires_in")));
				calendar.add(Calendar.SECOND, expires_in);
				accessTokenExpired = calendar.getTime();
			}else{
				logger.info("获取access_token错误---"+response_map.get("errmsg"));
				accessToken = null;
			}
			return accessToken;
		}
	}
	
	/**
	 * 生成小程序码
	 * @param accessToken
	 * @param scene
	 * @param page
	 * @return
	 */
	public byte[] createWXACode(String accessToken, String scene, String page){
		HashMap<String,Object> paramsmap = new HashMap<String, Object>();
		paramsmap.put("scene", scene);
		if(!StringUtils.isNullOrNone(page)){
			paramsmap.put("page", page);
		}
		StringBuffer buffer = new StringBuffer("https://api.weixin.qq.com/wxa/getwxacodeunlimit");
		buffer.append("?access_token=").append(accessToken);
		byte[] response = HttpClientUtil.doPostForByte(buffer.substring(0), paramsmap);

		try{
			String response_str=new String(response, "UTF-8");
			JsonUtils.toObject(new String(response, "UTF-8"), HashMap.class);
			logger.info("获取小程序码错误---"+response_str);
			response = null;
		}catch (Exception e) {
			
		}
		return response;
	}

	/**
	 * 获取客户端access_token
	 * @return
	 */
	public String fetchclientAccessToken(){
		if(!StringUtils.isNullOrNone(clientaccessToken) && clientaccessTokenExpired!=null && clientaccessTokenExpired.after(new Date())){
			return clientaccessToken;
		}else{
			Calendar calendar = Calendar.getInstance();
			StringBuffer buffer = new StringBuffer("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential");
			buffer.append("&appid=").append(clientappid).append("&secret=").append(clientsecret);
			String response = HttpClientUtil.doGet(buffer.substring(0));
			HashMap<String,Object> response_map= JsonUtils.toObject(response,HashMap.class);
			if(response_map.containsKey("access_token")){
				clientaccessToken = String.valueOf(response_map.get("access_token"));
				int expires_in = Integer.parseInt(String.valueOf(response_map.get("expires_in")));
				calendar.add(Calendar.SECOND, expires_in);
				clientaccessTokenExpired = calendar.getTime();
			}else{
				logger.info("获取access_token错误---"+response_map.get("errmsg"));
                clientaccessToken = null;
			}
			return clientaccessToken;
		}
	}
	//生成客户端二维码
}
