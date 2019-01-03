package com.lottery.dao;

import com.lottery.model.Lottery;
import com.lottery.model.LotteryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LotteryMapper {
    int countByExample(LotteryExample example);

    int deleteByExample(LotteryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Lottery record);

    int insertSelective(Lottery record);

    List<Lottery> selectByExample(LotteryExample example);

    Lottery selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Lottery record, @Param("example") LotteryExample example);

    int updateByExample(@Param("record") Lottery record, @Param("example") LotteryExample example);

    int updateByPrimaryKeySelective(Lottery record);

    int updateByPrimaryKey(Lottery record);
}