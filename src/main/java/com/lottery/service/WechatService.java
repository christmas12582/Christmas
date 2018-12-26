/**
 * 
 */
package com.lottery.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.codec.digest.Md5Crypt;
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
	@Value(value = "wechat.appid")
	private String appid;
	
	/**
	 * 商户号
	 */
	@Value(value = "wechat.mechid")
	private String mechid;
	
	/**
	 * 服务器ip
	 */
	@Value(value = "wechat.payip")
	private String payip;
	
	/**
	 * 回调url
	 */
	@Value(value = "wechat.notifyUrl")
	private String notifyUrl;
	
	/**
	 * 统一下单url
	 */
	@Value(value = "wechat.unifiedorderUrl")
	private String unifiedorderUrl;
	
	/**
	 * 商户平台设置的密钥key
	 */
	@Value(value = "wechat.key")
	private String key;
	
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
			buffer.append(keyValueXml(entry.getKey(), entry.getValue()));
		}
		try{
			String response = HttpClientUtil.doPostwithxml(unifiedorderUrl, keyValueXml("xml", buffer.substring(0)));
			String returnCode = getValueFromXml(response, "return_code");
			if(!"SUCCESS".equals(returnCode)){
				logger.error("userid--"+user.getId()+"--phone--"+user.getPhone()+"--微信统一下单失败--return_code--"+returnCode+"--return_msg--"+getValueFromXml(response,"return_msg"));
				return null;
			}
			String resultCode = getValueFromXml(response, "result_code");
			if(!"SUCCESS".equals(resultCode)){
				logger.error("userid--"+user.getId()+"--phone--"+user.getPhone()+"--微信统一下单失败--result_code--"+resultCode+"--err_code--"+getValueFromXml(response,"err_code")+"--err_code_des--"+getValueFromXml(response,"err_code_des"));
				return null;
			}
			return getValueFromXml(response, "prepay_id");
		}catch (Exception e) {
			logger.error("userid--"+user.getId()+"--phone--"+user.getPhone()+"--微信统一下单失败--exception--"+e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 键值对转xml字符串
	 * @param key
	 * @param value
	 * @return
	 */
	private String keyValueXml(String key, Object value){
		if(value != null){
			return "<"+key+">"+value.toString()+"</"+key+">";
		}else {
			return "<"+key+"></"+key+">";
		}
	}
	
	/**
	 * 参数签名
	 * @param paramMap
	 * @return
	 */
	private String sign(Map<String, Object> paramMap){
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
		return Md5Crypt.md5Crypt(param.getBytes()).toUpperCase();
	}
	
	/**
	 * 获取xml中key对应的value
	 * @param xml
	 * @param key
	 * @return
	 */
	private String getValueFromXml(String xml, String key){
		if(StringUtils.isNullOrNone(xml)){
			return null;
		}
		String startkey = "<"+key+"><![CDATA[";
		int index = xml.indexOf(startkey);
		if(index == -1){
			return null;
		}
		int startValue = index + startkey.length();
		String endKey = "]]></"+key+">";
		index = xml.indexOf(endKey);
		if(index == -1){
			return null;
		}
		if(startValue>index){
			return null;
		}
		return xml.substring(startValue, index);
	}
}
