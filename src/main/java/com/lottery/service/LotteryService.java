/**
 * 
 */
package com.lottery.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.dao.LotteryItemMapper;
import com.lottery.dao.LotteryMapper;
import com.lottery.model.Lottery;
import com.lottery.model.LotteryItem;
import com.lottery.model.LotteryItemExample;
import com.lottery.model.LotteryItemExample.Criteria;

/**
 * @author ws_yu
 *
 */
@Service
public class LotteryService {
	
	@Autowired
	private LotteryMapper lotteryMapper;
	
	@Autowired
	private LotteryItemMapper lotteryItemMapper;
	
	/**
	 * 查询抽奖活动详情
	 * @param id
	 * @return
	 */
	public Lottery findLotteryById(Integer id){
		return lotteryMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 查询抽奖活动奖项列表
	 * @param lotteryId
	 * @return
	 */
	public List<LotteryItem> findLotteryItemListByLotteryId(Integer lotteryId){
		LotteryItemExample lotteryItemExample = new LotteryItemExample();
		Criteria criteria = lotteryItemExample.createCriteria();
		criteria.andLotteryidEqualTo(lotteryId);
		return lotteryItemMapper.selectByExample(lotteryItemExample);
	}
	
	/**
	 * 查询抽奖活动可用奖项列表
	 * @param lotteryId
	 * @return
	 */
	public List<LotteryItem> findValiableLotteryItemListByLotteryId(Integer lotteryId){
		return lotteryItemMapper.selectByLotteryId(lotteryId);
	}
	
	/**
	 * 查询奖项
	 * @param id
	 * @return
	 */
	public LotteryItem findLotteryItemById(Integer id){
		return lotteryItemMapper.selectByPrimaryKey(id);
	}
	
	/**
	 * 保存奖项
	 * @param lotteryItem
	 */
	@Transactional
	public void saveLotteryItem(LotteryItem lotteryItem){
		if(lotteryItem.getId()==null){
			lotteryItemMapper.insert(lotteryItem)
		}else{
			lotteryItemMapper.updateByPrimaryKeySelective(lotteryItem);
		}
	}
}
