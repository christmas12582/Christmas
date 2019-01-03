package com.lottery.service;

import com.lottery.dao.BuyMapper;
import com.lottery.model.Buy;
import com.lottery.model.BuyExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistributorService {


    @Autowired
    BuyMapper buyMapper;


    public List<Buy> mydistribute(Integer shareid){
        BuyExample buyExample = new BuyExample();
        buyExample.createCriteria().andShareidEqualTo(shareid);
        return   buyMapper.selectByExample(buyExample);

    }

}
