package com.lottery.service;

import com.lottery.dao.*;
import com.lottery.model.*;
import com.lottery.utils.StringUtils;
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

    @Autowired
    LotteryItemMapper lotteryItemMapper;

    public List<HashMap<String,Object>> getMyProduct(Integer userid){
        BuyExample buyExample= new BuyExample();
        buyExample.createCriteria().andUseridEqualTo(userid).andExpiredateGreaterThanOrEqualTo(new Date()).andIspayEqualTo(1);
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


    public HashMap<String,Object> addLotteryItem(Integer lotteryid,Integer orderno,String name,String icon,Integer mcount,Integer weight){
        HashMap<String,Object> result= new HashMap<>();
        LotteryItemExample lotteryItemExample = new LotteryItemExample();
        lotteryItemExample.createCriteria().andLotteryidEqualTo(lotteryid).andNameEqualTo(name);
        List<LotteryItem> lotteryItems=lotteryItemMapper.selectByExample(lotteryItemExample);
        if (lotteryItems.size()>0){
            result.put("result","改活动中已有该商品名，请更换名字后重试");
            result.put("count",0);
        }else {
            LotteryItem lotteryItem= new LotteryItem();
            lotteryItem.setLotteryid(lotteryid);
            lotteryItem.setOrderno(orderno);
            lotteryItem.setName(name);
            lotteryItem.setIcon(icon);
            lotteryItem.setMcount(mcount);
            lotteryItem.setWeight(weight);
            lotteryItem.setGcount(0);
            int count=lotteryItemMapper.insertSelective(lotteryItem);
            result.put("result",lotteryItem.getId());
            result.put("count",count);
        }
        return result;
    }


    public  Integer getlotteryidByitemid(Integer itemid){
        LotteryItem lotteryItem=lotteryItemMapper.selectByPrimaryKey(itemid);
        return lotteryItem.getLotteryid();
    }

    public HashMap<String,Object> updateItem(Integer lotteryid,Integer itemid,Integer orderno,String name,String icon,Integer gcount,Integer mcount,Integer weight){
        HashMap<String,Object> result= new HashMap<>();
        LotteryItemExample lotteryItemExample = new LotteryItemExample();
        lotteryItemExample.createCriteria().andLotteryidEqualTo(lotteryid).andNameEqualTo(name).andIdNotEqualTo(itemid);
        List<LotteryItem> lotteryItems=lotteryItemMapper.selectByExample(lotteryItemExample);
        if (lotteryItems.size()>0){
            result.put("result","改活动中已有该商品名，请更换名字后重试");
            result.put("count",0);
        }else {
            LotteryItem lotteryItem= new LotteryItem();
            lotteryItem.setId(itemid);
            if (lotteryid!=null)
                lotteryItem.setLotteryid(lotteryid);
            if (orderno!=null)
                lotteryItem.setOrderno(orderno);
            if(StringUtils.isNullOrNone(name))
                lotteryItem.setName(name);
            if(StringUtils.isNullOrNone(name))
                lotteryItem.setIcon(icon);
            if (mcount!=null)
                lotteryItem.setMcount(mcount);
            if (gcount!=null)
                lotteryItem.setGcount(gcount);
            if (weight!=null)
            lotteryItem.setWeight(weight);
            int count=lotteryItemMapper.updateByPrimaryKey(lotteryItem);
            result.put("result","成功");
            result.put("count",count);
        }
        return result;
    }

    public int removeItem(Integer itemid){
        return lotteryItemMapper.deleteByPrimaryKey(itemid);
    }

    public List<LotteryItem> listitem(Integer lotteryid){
        LotteryItemExample lotteryItemExample= new LotteryItemExample();
        lotteryItemExample.createCriteria().andLotteryidEqualTo(lotteryid);
        return lotteryItemMapper.selectByExample(lotteryItemExample);
    }
}
