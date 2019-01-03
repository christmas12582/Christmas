package com.lottery.controller;

import com.github.pagehelper.PageHelper;
import com.lottery.common.MapFromPageInfo;
import com.lottery.common.ResponseModel;
import com.lottery.model.Buy;
import com.lottery.model.Cash;
import com.lottery.model.Product;
import com.lottery.model.User;
import com.lottery.service.BusinessService;
import com.lottery.service.DistributorService;
import com.lottery.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public ResponseModel IWantToDistribute(@RequestParam(value ="openid")String openid){
        User olduser=userService.findUserByOpenidAndType(openid,4);
        if (olduser==null){
            User user = new User();
            user.setIsvalid(1);
            user.setType(4);
            user.setOpenid(openid);
            User newuser=userService.adduser(user);
            return new ResponseModel(0L,"您已成功成为分销商",newuser);
        }else
            return new ResponseModel(0L,"该用户已是分销商",olduser);
    }


    /**
     * 我的分销
     */

    @RequestMapping(value = "mydistribute",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "我的分销记录", notes = "我的分销记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "pagenum", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pagesize", dataType = "int", paramType = "query")
    })
    public ResponseModel myDistribute(
            @RequestParam(value ="openid")String openid,
            @RequestParam(value = "pagenum", required = false, defaultValue = "1") Integer pagenum,
            @RequestParam(value = "pagesize", required = false, defaultValue = "10") Integer pagesize
    ){
        User user = userService.findUserByOpenidAndType(openid,4);
        if (user==null)
            return new ResponseModel(500L,"您尚未成为分销商");
        Integer shareid=user.getId();
        PageHelper.startPage(pagenum,pagesize);
        PageHelper.orderBy("buydate desc");
        List<Buy> buyList= distributorService.mydistribute(shareid);
        for(Buy buy:buyList){
            Product product=businessService.getProductByid(buy.getProductid());
            buy.setProduct(product);
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
        if(user.getMoney()<money)
            return new ResponseModel(500L,"你的提现金额超过限额");
        distributorService.getcash(user.getId(),money);
            return new ResponseModel(0L,"提现申请发起成功，请联系运营商兑换现金");
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
