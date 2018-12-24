package com.lottery.controller;

import com.github.pagehelper.PageHelper;
import com.lottery.common.MapFromPageInfo;
import com.lottery.common.ResponseModel;
import com.lottery.model.Buy;
import com.lottery.model.LotteryItem;
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
        if(user==null)
            return new ResponseModel(500L,"该用户未注册",null);
        if (user.getType()!=2)
            return new ResponseModel(500L,"该用户不属于商家",null);
        Integer userid=user.getId();
        List<HashMap<String,Object>> result=businessService.getMyProduct(userid);
        if (result==null)
            return new ResponseModel(500L,"该商家未购买任何产品,或商品已过期");
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
       if(user==null)
           return new ResponseModel(500L,"该用户未注册",null);
       if (user.getType()!=2)
           return new ResponseModel(500L,"该用户不属于商家",null);
       Integer userid=user.getId();
       Buy buy= businessService.getBuybyLotteryid(lotteryid);
       if (!buy.getUserid().equals(userid))
           return new ResponseModel(500L,"该活动不属于该用户",null);
       int count= businessService.updateLotteryValid(lotteryid,isvalid,mcount);
       if(count>0)
           return new ResponseModel(0L,"设置成功",null);
       else
           return new ResponseModel(500L,"设置失败，未找到该活动",null);
   }

    //商家增加活动商品
    @ApiOperation(value = "增加活动商品", notes = "增加活动商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", dataType = "String", paramType = "query",required = true),
            @ApiImplicitParam(name = "lotteryid", dataType = "int", paramType = "query",required = true),
            @ApiImplicitParam(name = "orderno", dataType = "int", paramType = "query",required = true),
            @ApiImplicitParam(name = "name", dataType = "String", paramType = "query",required = true),
            @ApiImplicitParam(name = "icon", dataType = "String", paramType = "query",required = true),
            @ApiImplicitParam(name = "mcount", dataType = "int", paramType = "query",required = true),
            @ApiImplicitParam(name = "weight", dataType = "int", paramType = "query",required = true)
    })
    @RequestMapping(value = "addlotteryitem",method = RequestMethod.POST)
    @ResponseBody
    public  ResponseModel addiLotteryItem(
            @RequestParam(value = "openid") String openid,
            @RequestParam(value = "lotteryid") Integer lotteryid,
            @RequestParam(value = "orderno") Integer orderno,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "icon") String icon,
            @RequestParam(value = "mcount") Integer mcount,
            @RequestParam(value = "weight") Integer weight
    )
    {
        User user=userService.findUserByOpenid(openid);
        if(user==null)
            return new ResponseModel(500L,"该用户未注册",null);
        if (user.getType()!=2)
            return new ResponseModel(500L,"该用户不属于商家",null);
        Buy buy= businessService.getBuybyLotteryid(lotteryid);
        if (!buy.getUserid().equals(user.getId()))
            return new ResponseModel(500L,"该活动不属于该用户",null);
        HashMap<String,Object> result=businessService.addLotteryItem(lotteryid,orderno,name,icon,mcount,weight);
        if((Integer)result.get("count")>0){
            return new ResponseModel(0L,"添加成功",result.get("result"));
        }else{
            return new ResponseModel(0L,result.get("result").toString(),null);
        }
    }


    //商家更新活动商品
    @ApiOperation(value = "更新活动商品", notes = "更新活动商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", dataType = "String", paramType = "query",required = true),
            @ApiImplicitParam(name = "lotteryid", dataType = "int", paramType = "query",required = true),
            @ApiImplicitParam(name = "itemid", dataType = "int", paramType = "query",required = true),
            @ApiImplicitParam(name = "orderno", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "icon", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "mcount", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "weight", dataType = "int", paramType = "query")
    })
    @RequestMapping(value = "updatelotteryitem",method = RequestMethod.POST)
    @ResponseBody
    public  ResponseModel updateLotteryItem(
            @RequestParam(value = "openid") String openid,
            @RequestParam(value = "lotteryid") Integer lotteryid,
            @RequestParam(value = "itemid") Integer itemid,
            @RequestParam(value = "orderno",required = false) Integer orderno,
            @RequestParam(value = "name",required = false) String name,
            @RequestParam(value = "icon",required = false) String icon,
            @RequestParam(value = "mcount",required = false) Integer mcount,
            @RequestParam(value = "gcount",required = false) Integer gcount,
            @RequestParam(value = "weight",required = false) Integer weight
    )
    {
        User user=userService.findUserByOpenid(openid);
        if(user==null)
            return new ResponseModel(500L,"该用户未注册",null);
        if (user.getType()!=2)
            return new ResponseModel(500L,"该用户不属于商家",null);
        Buy buy= businessService.getBuybyLotteryid(lotteryid);
        if (!buy.getUserid().equals(user.getId()))
            return new ResponseModel(500L,"该活动不属于该用户",null);
        if(!businessService.getlotteryidByitemid(itemid).equals(lotteryid))
            return new ResponseModel(500L,"该商品不属于该活动",null);
        HashMap<String,Object> result=businessService.updateItem(lotteryid,itemid,orderno,name,icon,gcount,mcount,weight);
        if ((Integer)result.get("count")>0)
             return new ResponseModel(0L,"更新成功",null);
        else
            return new ResponseModel(0L,result.get("result").toString(),null);

    }


    //商家删除活动商品
    @ApiOperation(value = "删除活动商品", notes = "删除活动商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", dataType = "String", paramType = "query",required = true),
            @ApiImplicitParam(name = "lotteryid", dataType = "int", paramType = "query",required = true),
            @ApiImplicitParam(name = "itemid", dataType = "int", paramType = "query",required = true),

    })
    @RequestMapping(value = "deletelotteryitem",method = RequestMethod.POST)
    @ResponseBody
    public  ResponseModel deleteLotteryItem(
            @RequestParam(value = "openid") String openid,
            @RequestParam(value = "lotteryid") Integer lotteryid,
            @RequestParam(value = "itemid") Integer itemid
    )
    {
        User user=userService.findUserByOpenid(openid);
        if(user==null)
            return new ResponseModel(500L,"该用户未注册",null);
        if (user.getType()!=2)
            return new ResponseModel(500L,"该用户不属于商家",null);
        Buy buy= businessService.getBuybyLotteryid(lotteryid);
        if (!buy.getUserid().equals(user.getId()))
            return new ResponseModel(500L,"该活动不属于该用户",null);
        if(!businessService.getlotteryidByitemid(itemid).equals(lotteryid))
            return new ResponseModel(500L,"该商品不属于该活动",null);
        Integer count=businessService.removeItem(itemid);
        return new ResponseModel(0L,"成功删除"+count+"条数据",null);

    }


    //商家查询活动商品列表
    @ApiOperation(value = "删除活动商品", notes = "删除活动商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pagenum", dataType = "int", paramType = "query",required = true),
            @ApiImplicitParam(name = "pagesize", dataType = "int", paramType = "query",required = true),
            @ApiImplicitParam(name = "openid", dataType = "String", paramType = "query",required = true),
            @ApiImplicitParam(name = "lotteryid", dataType = "int", paramType = "query",required = true)

    })
    @RequestMapping(value = "listlotteryitem",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel listLotteryItem(
            @RequestParam(value = "pagenum") Integer pagenum,
            @RequestParam(value = "pagesize") Integer pagesize,
            @RequestParam(value = "openid") String openid,
            @RequestParam(value = "lotteryid") Integer lotteryid
    ){
        User user=userService.findUserByOpenid(openid);
        if(user==null)
            return new ResponseModel(500L,"该用户未注册",null);
        if (user.getType()!=2)
            return new ResponseModel(500L,"该用户不属于商家",null);
        Buy buy= businessService.getBuybyLotteryid(lotteryid);
        if (!buy.getUserid().equals(user.getId()))
            return new ResponseModel(500L,"该活动不属于该用户",null);
        PageHelper.startPage(pagenum,pagesize);
        List<LotteryItem> lotteryItems=businessService.listitem(lotteryid);
        return new ResponseModel(new MapFromPageInfo<>(lotteryItems));
    }


    //查询可够商品列表
    //查询规格商品对应的规格列表
    //购买
    //购买成功回调函数，新增商户，新增活动
}

