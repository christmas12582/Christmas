package com.lottery.service;

import com.lottery.dao.*;
import com.lottery.model.*;
import com.lottery.utils.DateHelper;
import com.lottery.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;


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

    @Autowired
    UserMapper userMapper;
    
    @Autowired
    private WechatService wechatService;

    @Autowired
    ShareMapper shareMapper;

    Logger logger= LoggerFactory.getLogger(BusinessService.class);

    public List<HashMap<String,Object>> getMyProduct(Integer userid,Integer isvalid){
        List<Buy> buyList=new ArrayList<>();
        BuyExample buyExample= new BuyExample();
        if (isvalid==null){
            buyExample.createCriteria().andUseridEqualTo(userid);
            buyList=buyMapper.selectByExample(buyExample);
        }
        else if (isvalid==1){
            buyExample.createCriteria().andUseridEqualTo(userid)
                    .andExpiredateGreaterThanOrEqualTo(new Date())
                    .andIspayEqualTo(1);
            buyList=buyMapper.selectByExample(buyExample);
        }
        else if(isvalid==0){
            buyList=buyMapper.getnopayorexpirebuy(userid);
        }
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

    public int updateLotteryValid(Integer lotteryid,Integer isvalid,Integer mcount,Integer forceshare){
        Lottery lottery = new Lottery();
        lottery.setId(lotteryid);
        lottery.setIsvalid(isvalid);
        lottery.setMcount(mcount);
        lottery.setForceshare(forceshare);
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
        List<LotteryItem> lotteryItems=new ArrayList<>();
        if (!StringUtils.isNullOrNone(name)){
            lotteryItemExample.createCriteria().andLotteryidEqualTo(lotteryid).andNameEqualTo(name).andIdNotEqualTo(itemid);
            lotteryItems=lotteryItemMapper.selectByExample(lotteryItemExample);
        }else
            lotteryItems.clear();


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
            if(!StringUtils.isNullOrNone(name))
                lotteryItem.setName(name);
            if(!StringUtils.isNullOrNone(icon))
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

    @Transactional(rollbackFor = Exception.class)
    public HashMap<String,Object> buyProdct(String openid,Integer productid,Integer unitid,Integer shareid) throws Exception {
        HashMap<String,Object> result=new HashMap<>();
        User user = new User();
        //判断是否已经有user并且是商家
        UserExample userExample =new UserExample();
        userExample.createCriteria().andOpenidEqualTo(openid).andIsvalidEqualTo(1).andTypeEqualTo(2);
        List<User> userList=userMapper.selectByExample(userExample);
        if (!userList.isEmpty())
            user=userList.get(0);
        else {
            user.setType(2);
            user.setOpenid(openid);
            user.setIsvalid(1);
            user.setShareid(shareid);
            userMapper.insertSelective(user);
        }
        //判断是否已购买过
        BuyExample buyExample= new BuyExample();
        buyExample.createCriteria().andUseridEqualTo(user.getId())
                .andProductidEqualTo(productid)
                .andUnitidEqualTo(unitid)
                .andExpiredateGreaterThanOrEqualTo(new Date());
        List<Buy> hadbuyList=buyMapper.selectByExample(buyExample);
        if (!hadbuyList.isEmpty())
            throw new Exception("您已购买同样的产品，并且尚未过期，请勿重复购买");
        buyExample.clear();
        buyExample.createCriteria().andUseridEqualTo(user.getId())
                .andProductidEqualTo(productid)
                .andUnitidEqualTo(unitid)
                .andIspayEqualTo(0);
        List<Buy> buyListisnopayList=buyMapper.selectByExample(buyExample);
        if (!buyListisnopayList.isEmpty())
            throw new Exception("您已提交同类型订单，请勿重复提交");
        UnitExample unitExample= new UnitExample();
        unitExample.createCriteria().andIdEqualTo(unitid).andIsvalidEqualTo(1);
        List<Unit> unitList=unitMapper.selectByExample(unitExample);
        if (unitList.isEmpty())
            throw new Exception("未找到有效的规格");
        Unit unit=unitList.get(0);
        Integer months=unit.getExpired();
        Integer price=unit.getPrice();
        Buy buy= new Buy();
        buy.setBuydate(new Date());
        buy.setExpiredate(DateHelper.addMonth(new Date(),months));
        buy.setIspay(0);
        buy.setProductid(productid);
        String orderid=genertorOrderid();
        buy.setOrdernum(orderid);
        buy.setUnitid(unitid);
        buy.setUserid(user.getId());
//        Share share=shareMapper.selectByPrimaryKey(shareid);
//        if (share!=null&&new Date().getTime()-DateHelper.addMonth(share.getSharetime(),1).getTime()<=0)
        buy.setShareid(user.getShareid());

        buyMapper.insertSelective(buy);
        String prepayId = wechatService.preOrder(buy);
        if(StringUtils.isNullOrNone(prepayId)){
        	throw new Exception("下单失败");
        }
        result.put("prepayId",prepayId);
        result.put("price",price);

        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateBuybyOrderid(String orderid) throws Exception {
        //生成活动
        Lottery lottery = new Lottery();
        lottery.setIsvalid(1);
        lottery.setMcount(100);
        lottery.setForceshare(0);//默认
        lotteryMapper.insertSelective(lottery);

        BuyExample buyExample= new BuyExample();
        buyExample.createCriteria().andOrdernumEqualTo(orderid);
        List<Buy> buyList=buyMapper.selectByExample(buyExample);
        if (buyList.isEmpty())
            throw new Exception("未找到该订单");
        Buy buy=buyList.get(0);
        if(buy.getIspay()==1)
            throw new Exception("该订单已经付过款了");
        if(buy.getLotteryid()!=null)
            throw new Exception("该订单已经生成过活动了");
        buy.setIspay(1);
        buy.setLotteryid(lottery.getId());
        int count= buyMapper.updateByPrimaryKeySelective(buy);
        //若有shareid则增加提成
        Integer shareid=buy.getShareid();
        Integer unitid=buy.getUnitid();
        if(shareid!=null&&unitid!=null){
            User user=userMapper.selectByPrimaryKey(shareid);
            Unit unit=unitMapper.selectByPrimaryKey(unitid);
            if(user!=null&&unit!=null){
                Integer ratio=user.getRatio();
                Integer price=unit.getPrice();
                Integer money=ratio*price/100;
                Integer oldmoney=user.getMoney();
                if (oldmoney==null)
                    oldmoney=0;
                Integer newmoney=oldmoney+money;
                user.setMoney(newmoney);
                userMapper.updateByPrimaryKeySelective(user);
                logger.info("订单号："+orderid+"为userid："+shareid+"增加了"+newmoney+"（分）的分销提成");
            }else
                logger.info("未找到shareid或unitid为对应的记录，不予分销提成");
        }else
            logger.info("buy中的shareid或unitid为null，不予分销提成");
















    }

    public List<Buy> getMyDistribution(Integer businessid){
        List<Buy> buyList=new ArrayList<>();
        ShareExample shareExample= new ShareExample();
        shareExample.createCriteria().andBusinessidEqualTo(businessid);
        List<Share> shareList=shareMapper.selectByExample(shareExample);
        for(Share share:shareList){
            Integer shareid=share.getId();
            BuyExample buyExample = new BuyExample();
            buyExample.createCriteria().andShareidEqualTo(shareid).andIspayEqualTo(1);
            List<Buy> buys=buyMapper.selectByExample(buyExample);
            buyList.addAll(buys);
        }

        return buyList;
    }



    private String genertorOrderid(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate=sdf.format(new Date());
        String result="";
        Random random=new Random();
        for(int i=0;i<3;i++){
            result+=random.nextInt(10);
        }
        return newDate+result;
    }
}
