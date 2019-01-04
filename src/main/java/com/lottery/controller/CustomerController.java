package com.lottery.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.lottery.common.MapFromPageInfo;
import com.lottery.common.ResponseModel;
import com.lottery.model.Lottery;
import com.lottery.model.LotteryItem;
import com.lottery.model.User;
import com.lottery.model.UserLottery;
import com.lottery.service.LotteryService;
import com.lottery.service.UserLotteryService;
import com.lottery.service.UserService;
import com.lottery.service.WechatService;
import com.lottery.utils.JsonUtils;
import com.lottery.utils.StringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/customer")
@Api("用户 ")
public class CustomerController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private LotteryService lotteryService;
	
	@Autowired
	private UserLotteryService userLotteryService;
	
	@Autowired
	private WechatService wechatService;
	
	/**
	 * 保存用户信息
	 * @param openid 微信OPENID
	 * @param phone 用户手机号码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ApiOperation(value = "保存用户手机号码", notes = "保存用户手机号码")
	public ResponseModel saveCustomer(String openid, String phone){
		if(StringUtils.isNullOrNone(new String[]{openid, phone})){
			return new ResponseModel(500l, "请输入手机号码");
		}
		List<User> userList = userService.findUserListByPhone(phone);
		if(userList!=null && userList.size()>0){
			for(User u: userList){
				if(u.getType()==3 && u.getIsvalid() == 1 && !openid.equals(u.getOpenid())){
					return new ResponseModel(500l, "手机号码已使用，请重新输入");
				}
			}
		}
		User user = userService.findUserByOpenidAndType(openid, 3);
		if(user==null){
			user = new User();
			user.setIsvalid(1);
			user.setOpenid(openid);
			user.setType(3);
		}
		user.setPhone(phone);
		userService.saveUser(user);
		return new ResponseModel(200l, "保存手机号码成功");
	}
	
	/**
	 * 用户抽奖资格检查
	 * @param openid
	 * @param lotteryId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/lottery/qualify", method = RequestMethod.GET)
	@ApiOperation(value = "用户参与抽奖资格检查", notes = "用户是否可以参与抽奖")
	public ResponseModel canLottery(String openid, Integer lotteryId){
		User user = userService.findUserByOpenidAndType(openid, 3);
		if(user==null || StringUtils.isNullOrNone(user.getPhone())){
			return new ResponseModel(500l, "请先保存手机号码");
		}
		Lottery lottery = lotteryService.findLotteryById(lotteryId);
		if(lottery==null || lottery.getIsvalid() == 0){
			return new ResponseModel(404l, "抽奖活动不存在或已失效");
		}
		List<UserLottery> userLotteryList = userLotteryService.findUserLotteryListByUserIdAndLotteryId(user.getId(), lotteryId);
		if(userLotteryList!=null && userLotteryList.size()>=lottery.getMcount()){
			return new ResponseModel(501l, "您的抽奖次数已经用完");
		}
		return new ResponseModel(200l, "用户可以参与抽奖", lottery.getMcount()-(userLotteryList==null?0:userLotteryList.size()));
	}
	
	/**
	 * 保存中奖记录
	 * @param openid
	 * @param lotteryItemId
	 * @param shareNum
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/lottery/save", method = RequestMethod.POST)
	@ApiOperation(value = "保存用户抽奖结果", notes = "保存用户抽奖结果")
	public ResponseModel saveLottery(String openid, Integer lotteryItemId, String shareNum){
		User user = userService.findUserByOpenidAndType(openid, 3);
		if(user == null){
			return new ResponseModel(500l, "用户不存在");
		}
		try{
			String message = userLotteryService.lottery(user.getId(), lotteryItemId, shareNum);
			if(!StringUtils.isNullOrNone(message) && message.startsWith("success:")){
				return new ResponseModel(200l, "用户参与抽奖成功", message.split(":")[1]);
			}else{
				return new ResponseModel(501l, message);
			}
		}catch (RuntimeException e) {
			return new ResponseModel(500l, e.getMessage());
		}
	}
	
	/**
	 * 查询中奖详情
	 * @param openid
	 * @param userlotteryId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/lottery/get", method = RequestMethod.GET)
	@ApiOperation(value = "用户查询获奖", notes = "获奖详情")
	public ResponseModel getUserLottery(String openid, Integer userlotteryId){
		User user = userService.findUserByOpenidAndType(openid, 3);
		if(user == null){
			return new ResponseModel(404l, "用户不存在");
		}
		UserLottery userLottery = userLotteryService.findUserLotteryById(userlotteryId);
		if(userLottery==null || !userLottery.getUserid().equals(user.getId())){
			return new ResponseModel(404l, "获奖信息不存在");
		}
		Map<String, Object> data = JsonUtils.toObject(JsonUtils.toJson(userLottery), HashMap.class);
		LotteryItem lotteryItem = lotteryService.findLotteryItemById(userLottery.getLotteryitemid());
		if(lotteryItem!=null){
			data.put("name", lotteryItem.getName());
			data.put("icon", lotteryItem.getIcon());
		}
		data.put("exchange", userLottery.getExchangedate()!=null);
		return new ResponseModel(200l, "查询成功", data);
	}
	
	/**
	 * 分页查询中奖列表
	 * @param openid
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/lottery/page", method = RequestMethod.GET)
	@ApiOperation(value = "用户查询获奖列表", notes = "用户查询获奖列表")
	public ResponseModel getUserLotteryPage(String openid, Integer page, Integer pageSize){
		User user = userService.findUserByOpenidAndType(openid, 3);
		if(user == null){
			return new ResponseModel(404l, "没有查询到中奖信息");
		}
		PageHelper.orderBy("lotterydate desc");
		PageHelper.startPage(page, pageSize);
		List<UserLottery> userLotteryList = userLotteryService.findUserLotteryListByUserId(user.getId());
		MapFromPageInfo<UserLottery> pageInfo = new MapFromPageInfo<UserLottery>(userLotteryList);
		return new ResponseModel(200l, "查询成功", pageInfo);
	}
	
	/**
	 * 分享活动
	 * @param openid
	 * @param userLotteryId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/lottery/share", method = RequestMethod.POST)
	@ApiOperation(value = "用户分享抽奖活动", notes = "用户分享抽奖活动")
	public ResponseModel share(String openid, Integer userLotteryId){
		User user = userService.findUserByOpenidAndType(openid, 3);
		if(user == null){
			return new ResponseModel(404l, "用户不存在");
		}
		return new ResponseModel(200l, "分享成功", userLotteryService.share(user.getId(), userLotteryId));
	}
	
	/**
	 * 获取兑奖二维码
	 * @param openid
	 * @param userlotteryId
	 * @param page
	 * @param response
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/acode", method = RequestMethod.GET)
	@ApiOperation(value = "获取兑奖二维码", notes = "获取兑奖二维码")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "openid", dataType = "String", paramType = "query", required = true),
        @ApiImplicitParam(name = "userLotteryId", dataType = "int", paramType = "query", required = true),
        @ApiImplicitParam(name = "page", dataType = "String", paramType = "query")
	})
	public void createWXACode(String openid, Integer userlotteryId, String page, HttpServletResponse response) throws UnsupportedEncodingException, IOException{
		User user = userService.findUserByOpenidAndType(openid, 3);
		if(user==null){
			return;
		}
		UserLottery userLottery = userLotteryService.findUserLotteryById(userlotteryId);
		if(userLottery==null || !userLottery.getUserid().equals(user.getId())){
			return;
		}
		String accessToken = wechatService.fetchAccessToken();
		if(StringUtils.isNullOrNone(accessToken)){
			return;
		}
		if(StringUtils.isNullOrNone(userLottery.getPrizenum())){
			return;
		}
		String wxacode = wechatService.createWXACode(accessToken, userLottery.getPrizenum(), page);
		if(StringUtils.isNullOrNone(wxacode)){
			return;
		}
		response.setContentType("image/png");
		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.write(wxacode.getBytes("UTF-8"));
		outputStream.flush();
		outputStream.close();
	}
}