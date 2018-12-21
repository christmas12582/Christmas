/**
 * 
 */
package com.lottery.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottery.common.ResponseModel;
import com.lottery.model.Lottery;
import com.lottery.model.LotteryItem;
import com.lottery.service.LotteryService;
import com.lottery.utils.JsonUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author ws_yu
 *
 */
@Controller
@RequestMapping("/lottery")
@Api("抽奖 ")
public class LotteryController {
	
	@Autowired
	private LotteryService lotteryService;

	/**
	 * 查询抽奖活动（含所有奖项）
	 * @param lotteryId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/fget", method = RequestMethod.GET)
	@ApiOperation(value = "查询抽奖活动详情", notes = "查询抽奖活动详情（含所有抽奖奖项）")
	public ResponseModel fget(Integer lotteryId){
		Lottery lottery = lotteryService.findLotteryById(lotteryId);
		if(lottery==null){
			return new ResponseModel(500l, "抽奖活动不存在");
		}
		Map<String, Object> data = JsonUtils.toObject(JsonUtils.toJson(lottery), HashMap.class);
		List<LotteryItem> lotteryItemList = lotteryService.findLotteryItemListByLotteryId(lotteryId);
		if(lotteryItemList!=null && lotteryItemList.size()>0){
			data.put("items", lotteryItemList);
		}
		return new ResponseModel(200l, "查询成功", data);
	}
	
	/**
	 * 查询抽奖活动（只含可用奖项）
	 * @param lotteryId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ApiOperation(value = "查询抽奖活动详情", notes = "查询抽奖活动详情（只含可用奖项）")
	public ResponseModel get(Integer lotteryId){
		Lottery lottery = lotteryService.findLotteryById(lotteryId);
		if(lottery==null){
			return new ResponseModel(500l, "抽奖活动不存在");
		}
		Map<String, Object> data = JsonUtils.toObject(JsonUtils.toJson(lottery), HashMap.class);
		List<LotteryItem> lotteryItemList = lotteryService.findValiableLotteryItemListByLotteryId(lotteryId);
		if(lotteryItemList!=null && lotteryItemList.size()>0){
			data.put("items", lotteryItemList);
		}
		return new ResponseModel(200l, "查询成功", data);
	}
}
