package com.lottery.controller;

import com.lottery.common.ResponseModel;
import com.lottery.model.User;
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

@Controller()
@RequestMapping(value = "distributor")
@Api("分销商")
public class DistributorController {




    @Autowired
    UserService userService;


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

    @RequestMapping(value = "IWantToDistribute",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "我的分销记录", notes = "我的分销记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", dataType = "string", paramType = "query")
    })
    public ResponseModel myDistribute(@RequestParam(value ="openid")String openid){
        User user = userService.findUserByOpenidAndType(openid,4);
        if (user==null)
            return new ResponseModel(500L,"您尚未成为分销商");

return null;

    }



}
