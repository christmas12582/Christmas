package com.lottery.service;

import com.lottery.dao.BuyMapper;
import com.lottery.dao.CashMapper;
import com.lottery.model.Buy;
import com.lottery.model.BuyExample;
import com.lottery.model.Cash;
import com.lottery.model.CashExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class DistributorService {


    @Autowired
    BuyMapper buyMapper;

    @Autowired
    CashMapper cashMapper;


    public List<Buy> mydistribute(Integer shareid){
        BuyExample buyExample = new BuyExample();
        buyExample.createCriteria().andShareidEqualTo(shareid);
        return   buyMapper.selectByExample(buyExample);

    }


    @Transactional(rollbackFor = Exception.class)
    public void getcash(Integer createid,Integer money){
        Cash cash = new Cash();
        cash.setCreateid(createid);
        cash.setCreatetime(new Date());
        cash.setMoney(money);
        cash.setIsexchange(0);
        cashMapper.insertSelective(cash);
    }

    public List<Cash> myGetCashList(Integer createid,Integer isexchange){
        CashExample cashExample = new CashExample();
        CashExample.Criteria criteria= cashExample.createCriteria();
        criteria.andCreateidEqualTo(createid);
        if (isexchange!=null)
            criteria.andIsexchangeEqualTo(isexchange);
        return cashMapper.selectByExample(cashExample);

    }

}
