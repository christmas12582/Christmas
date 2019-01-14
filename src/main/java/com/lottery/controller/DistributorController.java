package com.lottery.controller;

import com.github.pagehelper.PageHelper;
import com.lottery.common.MapFromPageInfo;
import com.lottery.common.ResponseModel;
import com.lottery.model.*;
import com.lottery.service.BusinessService;
import com.lottery.service.DistributorService;
import com.lottery.service.UserService;
import com.lottery.service.WechatService;
import com.lottery.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javafx.geometry.Pos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller()
@RequestMapping(value = "distributor")
@Api("分销商")
public class DistributorController {




    @Autowired
    UserService userService;

    @Autowired
    DistributorService distributorService;

    @Autowired
    BusinessService businessService;

    @Autowired
    WechatService wechatService;

    @Value("${defaultratio}")
    Integer defaultratio;

    Logger logger= LoggerFactory.getLogger(DistributorController.class);
    /**
     * 成为分销商
     * @param openid
     * @return
     */
    @RequestMapping(value = "IWantToDistribute",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "我要成为分销商", notes = "我要成为分销商(会在user中插入一条type=4的用户信息)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", dataType = "string", paramType = "query")
    })
    public ResponseModel IWantToDistribute(@RequestParam(value ="openid")String openid,
                                           @RequestParam(value ="phone")String phone){
        User olduser=userService.findUserByOpenidAndType(openid,4);
        if (olduser==null){
            User buser=userService.findUserByOpenidAndType(openid,2);
            User user = new User();
            if (buser!=null){
                user=buser;
                user.setId(null);
            }
                user.setIsvalid(1);
                user.setType(4);
                user.setOpenid(openid);
                user.setPhone(phone);
                user.setRatio(defaultratio);//默认提成比例为100%
            User newuser=userService.adduser(user);
            return new ResponseModel(0L,"您已成功成为分销商",newuser);
        }else
            return new ResponseModel(0L,"该用户已是分销商",olduser);
    }


    @RequestMapping(value = "isdistribute",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "是否是分销商", notes = "是否是分销商")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", dataType = "string", paramType = "query")
    })
    public ResponseModel isDistribute(@RequestParam(value ="openid")String openid){
        User user=userService.findUserByOpenidAndType(openid,4);
        if (user==null)
            return  new ResponseModel(500L,"您尚未成为分销商",null);
        else
            return new ResponseModel(0L,"分销商",user);
    }

    /*
     *生成推广二维码
     */

    @ApiOperation(value = "生成推广二维码", notes = "生成推广二维码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "page", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "getextensionQRcode",method = RequestMethod.GET,produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getLotteryQrCode(String openid,String page){
        User user = userService.findUserByOpenidAndType(openid,4);
        if (user==null){
            logger.error("您尚未成为分销商");
            return null;
        }
        String accessToken = wechatService.fetchAccessToken();
        if(StringUtils.isNullOrNone(accessToken)){
            logger.error("客户端accessToken为空");
            return null;
        }
        byte[] rqcode= wechatService.createWXACode(accessToken,user.getId().toString(),page);
        if(rqcode==null)
            return null;
        return rqcode;
    }



    /**
     * 我的分销
     */

    @RequestMapping(value = "mydistribute",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "我的分销记录", notes = "我的分销记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "ispay", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pagenum", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pagesize", dataType = "int", paramType = "query")
    })
    public ResponseModel myDistribute(
            @RequestParam(value ="openid")String openid,
            @RequestParam(value ="ispay",required = false)Integer ispay,
            @RequestParam(value = "pagenum", required = false, defaultValue = "1") Integer pagenum,
            @RequestParam(value = "pagesize", required = false, defaultValue = "10") Integer pagesize
    ){
        User user = userService.findUserByOpenidAndType(openid,4);
        if (user==null)
            return new ResponseModel(500L,"您尚未成为分销商");
        Integer shareid=user.getId();
        PageHelper.startPage(pagenum,pagesize);
        PageHelper.orderBy("buydate desc");
        List<Buy> buyList= distributorService.mydistribute(shareid,ispay);
        for(Buy buy:buyList){
            if(buy.getProductid()!=null){
                Product product=businessService.getProductByid(buy.getProductid());
                buy.setProduct(product);
            }
            if (buy.getUnitid()!=null){
                Unit unit =businessService.getUnitByid(buy.getUnitid());
                buy.setUnit(unit);

            }
        }
        return new ResponseModel(0L,"获取分销记录成功，(包含未付款的)",new MapFromPageInfo<>(buyList));
    }

    //发起提现
    @RequestMapping(value = "getcash",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "发起提现申请", notes = "发起提现申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", dataType = "string", paramType = "query",required = true),
            @ApiImplicitParam(name = "money", dataType = "int", paramType = "query",value = "提现金额（分）",required = true)
    })
    public ResponseModel getCash(
            @RequestParam(value ="openid")String openid,
            @RequestParam(value ="money")Integer money
    ){
        User user = userService.findUserByOpenidAndType(openid,4);
        if (user==null)
            return new ResponseModel(500L,"您尚未成为分销商");
        if(user.getMoney()==null)
            return new ResponseModel(500L,"您可用提现金额为0");
        if(user.getMoney()<money)
            return new ResponseModel(500L,"你的提现金额超过限额");
        try {
            distributorService.getcash(user.getId(),money);
            return new ResponseModel(0L,"提现申请发起成功，请联系运营商兑换现金");
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseModel(500L,"发起提现失败"+e.getMessage());
        }

    }


    //我的现金记录
    @RequestMapping(value = "myMoney",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "我的现金记录", notes = "我的现金记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", dataType = "string", paramType = "query",required = true),
    })
    public ResponseModel myMoney(
            @RequestParam(value ="openid")String openid
    ){
        User user = userService.findUserByOpenidAndType(openid,4);
        if (user==null)
            return new ResponseModel(500L,"您尚未成为分销商");
        Integer money_valid=user.getMoney();
        if (money_valid==null)
            money_valid=0;
        Integer money_done=0;
        Integer money_doing=0;

        List<Cash> cashList_done=distributorService.myGetCashList(user.getId(),1);
        List<Cash> cashList_doing=distributorService.myGetCashList(user.getId(),0);
        for (Cash cash :cashList_done){
            if(cash.getMoney()!=null)
                money_done+=cash.getMoney();
        }
        for (Cash cash :cashList_doing){
            if(cash.getMoney()!=null)
                money_doing+=cash.getMoney();
        }
        HashMap<String,Object> result= new HashMap<>();
        result.put("money_valid",money_valid);
        result.put("money_done",money_done);
        result.put("money_doing",money_doing);
        return new ResponseModel(0L,"获取我的现金记录成功",result);
    }




    //我发起的提现申请
    @RequestMapping(value = "mygetcashlist",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "我发起的提现申请", notes = "我发起的提现申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", dataType = "string", paramType = "query",required = true),
            @ApiImplicitParam(name = "isexchange", dataType = "int", paramType = "query",value = "筛选条件（是否已提现）"),
            @ApiImplicitParam(name = "pagenum", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pagesize", dataType = "int", paramType = "query")
    })
    public ResponseModel myGetcashList(
            @RequestParam(value ="openid")String openid,
            @RequestParam(value ="isexchange",required = false)Integer isexchange,
            @RequestParam(value = "pagenum", required = false, defaultValue = "1") Integer pagenum,
            @RequestParam(value = "pagesize", required = false, defaultValue = "10") Integer pagesize
    ){
        User user = userService.findUserByOpenidAndType(openid,4);
        if (user==null)
            return new ResponseModel(500L,"您尚未成为分销商");
        PageHelper.startPage(pagenum,pagesize);
        PageHelper.orderBy("createtime desc");
        List<Cash> cashList=distributorService.myGetCashList(user.getId(),isexchange);
        return new ResponseModel(0L,"获取我的提现记录成功",new MapFromPageInfo<>(cashList));
    }




}
