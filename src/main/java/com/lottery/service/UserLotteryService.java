/**
 * 
 */
package com.lottery.service;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.dao.UserLotteryMapper;
import com.lottery.model.Lottery;
import com.lottery.model.LotteryItem;
import com.lottery.model.UserLottery;
import com.lottery.model.UserLotteryExample;
import com.lottery.model.UserLotteryExample.Criteria;

/**
 * @author ws_yu
 *
 */
@Service
public class UserLotteryService {
	
	@Autowired
	private UserLotteryMapper userLotteryMapper;
	
	@Autowired
	private LotteryService lotteryService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserLotteryService.class);

	/**
	 * 查询用户参与某抽奖活动的抽奖记录
	 * @param userId
	 * @param lotteryId
	 * @return
	 */
	public List<UserLottery> findUserLotteryListByUserIdAndLotteryId(Integer userId, Integer lotteryId){
		return userLotteryMapper.selectByUserIdAndLotteryId(userId, lotteryId);
	}
	
	/**
	 * 查询用户参与抽奖活动的抽奖记录
	 * @param userId
	 * @return
	 */
	public List<UserLottery> findUserLotteryListByUserId(Integer userId){
		UserLotteryExample userLotteryExample = new UserLotteryExample();
		Criteria criteria = userLotteryExample.createCriteria();
		criteria.andUseridEqualTo(userId);
		return userLotteryMapper.selectByExample(userLotteryExample);
	}
	
	/**
	 * 中奖
	 * @param userId
	 * @param lotteryItemId
	 * @return
	 */
	@Transactional
	public String lottery(Integer userId, Integer lotteryItemId){
		String message = "success";
		synchronized(this){
			LotteryItem lotteryItem = lotteryService.findLotteryItemById(lotteryItemId);
			if(lotteryItem==null){
				throw new RuntimeException("奖项不存在");
			}
			Lottery lottery = lotteryService.findLotteryById(lotteryItem.getLotteryid());
			if(lottery==null || lottery.getIsvalid()==0){
				throw new RuntimeException("抽奖活动不存在或已失效");
			}
			if(lotteryItem.getGcount()>=lotteryItem.getMcount()){
				message = "奖项["+lotteryItem.getName()+"]已抽空，抽奖失败";
				logger.info("用户["+userId+"]在参与活动["+lottery.getId()+"]时，"+message);
				return message;
			}
			List<UserLottery> userLotteryList = findUserLotteryListByUserIdAndLotteryId(userId, lottery.getId());
			if(userLotteryList!=null && userLotteryList.size()>=lottery.getMcount()){
				message = "抽奖次数已经用完，抽奖失败";
				logger.info("用户["+userId+"]在活动["+lottery.getId()+"]上的"+message);
				return message;
			}
			lotteryItem.setGcount(lotteryItem.getGcount()+1);
			lotteryService.saveLotteryItem(lotteryItem);
			UserLottery userLottery = new UserLottery();
			userLottery.setLotterydate(new Date());
			userLottery.setUserid(userId);
			userLottery.setLotteryitemid(lotteryItemId);
			userLottery.setLotteryid(lotteryItem.getLotteryid());
			userLottery.setPrizenum(generatePrizenum(userId));
			userLotteryMapper.insert(userLottery);
		}
		return message;
	}
	
	/**
	 * 查询中奖信息
	 * @param id
	 * @return
	 */
	public UserLottery findUserLotteryById(Integer id){
		return userLotteryMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 查询中奖信息
	 * @param prizenum
	 * @return
	 */
	public UserLottery findUserLotteryByPrizenum(String prizenum){
		UserLotteryExample userLotteryExample = new UserLotteryExample();
		Criteria criteria = userLotteryExample.createCriteria();
		criteria.andPrizenumEqualTo(prizenum);
		List<UserLottery> userLotteryList = userLotteryMapper.selectByExample(userLotteryExample);
		if(userLotteryList!=null && userLotteryList.size()>0){
			return userLotteryList.get(0);
		}
		return null;
	}
	
	/**
	 * 兑奖
	 * @param id
	 */
	@Transactional
	public void exchangeUserLottery(Integer id){
		UserLottery userLottery = new UserLottery();
		userLottery.setId(id);
		userLottery.setExchangedate(new Date());
		userLotteryMapper.updateByPrimaryKeySelective(userLottery);
	}
	
	/**
	 * 生成中奖号
	 * @param userId
	 * @return
	 */
	private String generatePrizenum(Integer userId) {
        String date = String.valueOf(new Date().getTime());
        String user = String.valueOf(userId);
        int length = 18 - date.length() - user.length();
        String random = "";
        for(int i = 0; i < length; ++i) {
            Random r = new Random();
            random = random + String.valueOf(r.nextInt(10));
        }
        return date+random+user;
    }
	
}
