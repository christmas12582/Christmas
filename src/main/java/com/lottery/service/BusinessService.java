package com.lottery.service;

import com.lottery.dao.BuyMapper;
import com.lottery.dao.LotteryMapper;
import com.lottery.dao.ProductMapper;
import com.lottery.dao.UnitMapper;
import com.lottery.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class BusinessService {

    @Autowired
    ProductMapper productMapper;
    @Autowired
    BuyMapper buyMapper;

    @Autowired
    UnitMapper unitMapper;

    @Autowired
    LotteryMapper lotteryMapper;

    public List<HashMap<String,Object>> getMyProduct(Integer userid){
        BuyExample buyExample= new BuyExample();
        buyExample.createCriteria().andUseridEqualTo(userid).andExpiredateGreaterThanOrEqualTo(new Date());
        List<Buy> buyList=buyMapper.selectByExample(buyExample);
        if (buyList.size()==0)
            return null;

        List<HashMap<String,Object>> reuslt=new ArrayList<>();
        for(Buy buy :buyList){
            Integer productid=buy.getProductid();
            Integer unitid=buy.getUnitid();
            Integer lotteryid=buy.getLotteryid();

            Product product=productMapper.selectByPrimaryKey(productid);
            Unit unit = unitMapper.selectByPrimaryKey(unitid);
            Lottery lottery=lotteryMapper.selectByPrimaryKey(lotteryid);
            HashMap<String,Object> myproductdetail=new HashMap<>();
            myproductdetail.put("lotteryid",lottery);
            myproductdetail.put("product",product);
            myproductdetail.put("unit",unit);
            reuslt.add(myproductdetail);
        }
        return reuslt;



    }


    public Buy getBuybyLotteryid(Integer lotteryid){
        BuyExample buyExample= new BuyExample();
        buyExample.createCriteria().andLotteryidEqualTo(lotteryid);
        List<Buy> buyList=buyMapper.selectByExample(buyExample);
        if (!buyList.isEmpty())
            return buyList.get(0);
        else
            return null;
    }

    public int updateLotteryValid(Integer lotteryid,Integer isvalid,Integer mcount){
        Lottery lottery = new Lottery();
        lottery.setId(lotteryid);
        lottery.setIsvalid(isvalid);
        lottery.setMcount(mcount);
        return lotteryMapper.updateByPrimaryKeySelective(lottery);

    }
}
