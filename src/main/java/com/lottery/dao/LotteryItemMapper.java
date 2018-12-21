package com.lottery.dao;

import com.lottery.model.LotteryItem;
import com.lottery.model.LotteryItemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface LotteryItemMapper {
    int countByExample(LotteryItemExample example);

    int deleteByExample(LotteryItemExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LotteryItem record);

    int insertSelective(LotteryItem record);

    List<LotteryItem> selectByExample(LotteryItemExample example);

    LotteryItem selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LotteryItem record, @Param("example") LotteryItemExample example);

    int updateByExample(@Param("record") LotteryItem record, @Param("example") LotteryItemExample example);

    int updateByPrimaryKeySelective(LotteryItem record);

    int updateByPrimaryKey(LotteryItem record);
    
    List<LotteryItem> selectByLotteryId(Integer lotteryId);
}