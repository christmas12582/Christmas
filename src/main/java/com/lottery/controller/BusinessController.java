package com.lottery.controller;

import com.lottery.common.ResponseModel;
import com.lottery.model.Buy;
import com.lottery.model.User;
import com.lottery.service.BusinessService;
import com.lottery.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
@Api("商家管理后台")
public class BusinessController {



    @Autowired
    UserService userService;

    @Autowired
    BusinessService businessService;

    //商家获取已购买的活动
    @ApiOperation(value = "商家获取已购买的活动产品", notes = "商家获取已购买的活动产品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", dataType = "String", paramType = "query",required = true),
    })
    @RequestMapping(value = "getmyproduct",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel getMyProduct(@RequestParam(value = "openid") String openid){
        User user=userService.findUserByOpenid(openid);
        if (user.getType()!=2)
            return new ResponseModel(500L,"该用户不属于商家",null);
        Integer userid=user.getId();
        List<HashMap<String,Object>> result=businessService.getMyProduct(userid);
        if (result==null)
            return new ResponseModel(500L,"该商家未购买任何产品");
        return new ResponseModel(result);
   }


   //商家购买产品的时候自动生成一个抽奖活动ID

   //商家设置活动是否有效

    @ApiOperation(value = "设置抽奖活动是否有效及设置用户可以参加的次数", notes = "设置抽奖活动是否有效及设置用户可以参加的次数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", dataType = "String", paramType = "query",required = true),
            @ApiImplicitParam(name = "lotteryid", dataType = "int", paramType = "query",required = true),
            @ApiImplicitParam(name = "isvalid", dataType = "int", paramType = "query",required = true),
            @ApiImplicitParam(name = "mcount", dataType = "int", paramType = "query",value = "如果传入此参数则同步更新用户最大抽奖次数")
    })
   @RequestMapping(value = "updatelottery",method = RequestMethod.POST)
   @ResponseBody
   public ResponseModel updateLottery(
           @RequestParam(value = "openid") String openid,
           @RequestParam(value = "lotteryid") Integer lotteryid,
           @RequestParam(value = "isvalid") Integer isvalid,
           @RequestParam(value = "mcount",required = false) Integer mcount
   ){
       User user=userService.findUserByOpenid(openid);
       if (user.getType()!=2)
           return new ResponseModel(500L,"该用户不属于商家",null);
       Integer userid=user.getId();
       Buy buy= businessService.getBuybyLotteryid(lotteryid);
       if (buy.getUserid()!=userid)
           return new ResponseModel(500L,"该活动不属于该用户",null);
       int count= businessService.updateLotteryValid(lotteryid,isvalid,mcount);
       if(count>0)
           return new ResponseModel(0L,"设置成功",null);
       else
           return new ResponseModel(500L,"设置失败，未找到该活动",null);
   }

    //商家增加活动商品

    //商家更新活动商品

    //商家删除活动商品

    //商家查询活动商品列表


}


