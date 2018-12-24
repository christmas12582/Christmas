package com.lottery.dao;

import com.lottery.model.UserLottery;
import com.lottery.model.UserLotteryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface UserLotteryMapper {
    int countByExample(UserLotteryExample example);

    int deleteByExample(UserLotteryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserLottery record);

    int insertSelective(UserLottery record);

    List<UserLottery> selectByExample(UserLotteryExample example);

    UserLottery selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserLottery record, @Param("example") UserLotteryExample example);

    int updateByExample(@Param("record") UserLottery record, @Param("example") UserLotteryExample example);

    int updateByPrimaryKeySelective(UserLottery record);

    int updateByPrimaryKey(UserLottery record);
    
    List<UserLottery> selectByUserIdAndLotteryId(@Param("userId") Integer userId, @Param("lotteryId") Integer lotteryId);
}