package com.lottery.controller;

import com.github.pagehelper.PageHelper;
import com.lottery.common.MapFromPageInfo;
import com.lottery.common.ResponseModel;
import com.lottery.model.*;
import com.lottery.service.*;
import com.lottery.utils.StringUtils;
import com.lottery.utils.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/business")
@Api("商家管理后台")
public class BusinessController {


    @Autowired
    UserService userService;

    @Autowired
    BusinessService businessService;

    @Autowired
    OperatorService operatorService;
    
    @Autowired
	private UserLotteryService userLotteryService;

    @Autowired
    WechatService wechatService;
    
    @Autowired
	private LotteryService lotteryService;
    
    /**
	 * 兑奖超期天数
	 */
	@Value("${exchange.expired}")
	private Integer expired;

    Logger logger= LoggerFactory.getLogger(CustomerController.class);

    //商家获取已购买的活动
    @ApiOperation(value = "商家获取已购买的活动产品", notes = "商家获取已购买的活动产品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "isvalid", dataType = "int", paramType = "query",value = "传入1查询当前有效活动，传入0查询当前过期或未支付的活动,不传查询该账户名下所有活动"),
    })
    @RequestMapping(value = "getmyproduct", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel getMyProduct(@RequestParam(value = "openid") String openid,
                                      @RequestParam(value = "isvalid",required = false,defaultValue = "1") Integer isvalid) {
        List<User> userlist = userService.findUserByOpenid(openid);
        if (userlist == null)
            return new ResponseModel(500L, "该用户未注册", null);
        List<User> bussinessUserList=new ArrayList<>();
        for(User item:userlist){
            if (item.getType() == 2)
                bussinessUserList.add(item);
        }
        if (bussinessUserList.isEmpty())
            return new ResponseModel(500L, "该用户不属于商家", null);
        Integer userid = bussinessUserList.get(0).getId();
        List<HashMap<String, Object>> result = businessService.getMyProduct(userid,isvalid);
        return new ResponseModel(result);
    }


    //商家购买产品的时候自动生成一个抽奖活动ID

    //商家设置活动是否有效

    @ApiOperation(value = "设置抽奖活动是否有效及设置用户可以参加的次数，设置是否需要强制分享或分享次数限制", notes = "设置抽奖活动是否有效及设置用户可以参加的次数，设置是否需要强制分享或分享次数限制")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "lotteryid", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "isvalid", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "forceshare", dataType = "int", paramType = "query",value = "是否强制分享，默认为1"),
            @ApiImplicitParam(name = "mcount", dataType = "int", paramType = "query", value = "如果传入此参数则同步更新用户最大抽奖次数")
    })
    @RequestMapping(value = "updatelottery", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel updateLottery(
            @RequestParam(value = "openid") String openid,
            @RequestParam(value = "lotteryid") Integer lotteryid,
            @RequestParam(value = "isvalid") Integer isvalid,
            @RequestParam(value = "forceshare",defaultValue = "1") Integer forceshare,
            @RequestParam(value = "mcount", required = false) Integer mcount
    ) {
        List<User> userlist = userService.findUserByOpenid(openid);
        if (userlist == null)
            return new ResponseModel(500L, "该用户未注册", null);
        List<User> bussinessUserList=new ArrayList<>();
        for(User item:userlist){
            if (item.getType() == 2)
                bussinessUserList.add(item);
        }
        if (bussinessUserList.isEmpty())
            return new ResponseModel(500L, "该用户不属于商家", null);

        Buy buy = businessService.getBuybyLotteryid(lotteryid);
        if (!buy.getUserid().equals(bussinessUserList.get(0).getId()))
            return new ResponseModel(500L, "该活动不属于该用户", null);
        int count = businessService.updateLotteryValid(lotteryid, isvalid, mcount,forceshare);
        if (count > 0)
            return new ResponseModel(0L, "设置成功", null);
        else
            return new ResponseModel(500L, "设置失败，未找到该活动", null);
    }

    //商家增加活动商品
    @ApiOperation(value = "增加活动商品", notes = "增加活动商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "lotteryid", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "orderno", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "name", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "icon", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "mcount", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "weight", dataType = "int", paramType = "query", required = true)
    })
    @RequestMapping(value = "addlotteryitem", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel addiLotteryItem(
            @RequestParam(value = "openid") String openid,
            @RequestParam(value = "lotteryid") Integer lotteryid,
            @RequestParam(value = "orderno") Integer orderno,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "icon", required=false) String icon,
            @RequestParam(value = "mcount") Integer mcount,
            @RequestParam(value = "weight") Integer weight
    ) {
        List<User> userlist = userService.findUserByOpenid(openid);
        if (userlist == null)
            return new ResponseModel(500L, "该用户未注册", null);
        List<User> bussinessUserList=new ArrayList<>();
        for(User item:userlist){
            if (item.getType() == 2)
                bussinessUserList.add(item);
        }
        if (bussinessUserList.isEmpty())
            return new ResponseModel(500L, "该用户不属于商家", null);

        Buy buy = businessService.getBuybyLotteryid(lotteryid);
        if (!buy.getUserid().equals(bussinessUserList.get(0).getId()))
            return new ResponseModel(500L, "该活动不属于该用户", null);
        HashMap<String, Object> result = businessService.addLotteryItem(lotteryid, orderno, name, icon, mcount, weight);
        if ((Integer) result.get("count") > 0) {
            return new ResponseModel(0L, "添加成功", result.get("result"));
        } else {
            return new ResponseModel(0L, result.get("result").toString(), null);
        }
    }


    //商家更新活动商品
    @ApiOperation(value = "更新活动商品", notes = "更新活动商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "lotteryid", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "itemid", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "orderno", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "icon", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "mcount", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "weight", dataType = "int", paramType = "query")
    })
    @RequestMapping(value = "updatelotteryitem", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel updateLotteryItem(
            @RequestParam(value = "openid") String openid,
            @RequestParam(value = "lotteryid") Integer lotteryid,
            @RequestParam(value = "itemid") Integer itemid,
            @RequestParam(value = "orderno", required = false) Integer orderno,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "icon", required = false) String icon,
            @RequestParam(value = "mcount", required = false) Integer mcount,
            @RequestParam(value = "gcount", required = false) Integer gcount,
            @RequestParam(value = "weight", required = false) Integer weight
    ) {
        List<User> userlist = userService.findUserByOpenid(openid);
        if (userlist == null)
            return new ResponseModel(500L, "该用户未注册", null);
        List<User> bussinessUserList=new ArrayList<>();
        for(User item:userlist){
            if (item.getType() == 2)
                bussinessUserList.add(item);
        }
        if (bussinessUserList.isEmpty())
            return new ResponseModel(500L, "该用户不属于商家", null);

        Buy buy = businessService.getBuybyLotteryid(lotteryid);
        if (!buy.getUserid().equals(bussinessUserList.get(0).getId()))
            return new ResponseModel(500L, "该活动不属于该用户", null);
        gcount = 0;
        HashMap<String, Object> result = businessService.updateItem(lotteryid, itemid, orderno, name, icon, gcount, mcount, weight);
        if ((Integer) result.get("count") > 0)
            return new ResponseModel(0L, "更新成功", null);
        else
            return new ResponseModel(0L, result.get("result").toString(), null);

    }


    //商家删除活动商品
    @ApiOperation(value = "删除活动商品", notes = "删除活动商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "lotteryid", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "itemid", dataType = "int", paramType = "query", required = true),

    })
    @RequestMapping(value = "deletelotteryitem", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel deleteLotteryItem(
            @RequestParam(value = "openid") String openid,
            @RequestParam(value = "lotteryid") Integer lotteryid,
            @RequestParam(value = "itemid") Integer itemid
    ) {
        List<User> userlist = userService.findUserByOpenid(openid);
        if (userlist == null)
            return new ResponseModel(500L, "该用户未注册", null);
        List<User> bussinessUserList=new ArrayList<>();
        for(User item:userlist){
            if (item.getType() == 2)
                bussinessUserList.add(item);
        }
        if (bussinessUserList.isEmpty())
            return new ResponseModel(500L, "该用户不属于商家", null);

        Buy buy = businessService.getBuybyLotteryid(lotteryid);
        if (!buy.getUserid().equals(bussinessUserList.get(0).getId()))
            return new ResponseModel(500L, "该活动不属于该用户", null);
        Integer count = businessService.removeItem(itemid);
        return new ResponseModel(0L, "成功删除" + count + "条数据", null);

    }


    //商家查询活动商品列表
    @ApiOperation(value = "活动奖项列表", notes = "活动奖项列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pagenum", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "pagesize", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "openid", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "lotteryid", dataType = "int", paramType = "query", required = true)

    })
    @RequestMapping(value = "listlotteryitem", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel listLotteryItem(
            @RequestParam(value = "pagenum") Integer pagenum,
            @RequestParam(value = "pagesize") Integer pagesize,
            @RequestParam(value = "openid") String openid,
            @RequestParam(value = "lotteryid") Integer lotteryid
    ) {
        List<User> userlist = userService.findUserByOpenid(openid);
        if (userlist == null)
            return new ResponseModel(500L, "该用户未注册", null);
        List<User> bussinessUserList=new ArrayList<>();
        for(User item:userlist){
            if (item.getType() == 2)
                bussinessUserList.add(item);
        }
        if (bussinessUserList.isEmpty())
            return new ResponseModel(500L, "该用户不属于商家", null);

        Buy buy = businessService.getBuybyLotteryid(lotteryid);
        if (!buy.getUserid().equals(bussinessUserList.get(0).getId()))
            return new ResponseModel(500L, "该活动不属于该用户", null);
        PageHelper.startPage(pagenum, pagesize);
        PageHelper.orderBy("orderno asc");
        List<LotteryItem> lotteryItems = businessService.listitem(lotteryid);
        return new ResponseModel(new MapFromPageInfo<>(lotteryItems));
    }


    //查询可够商品列表
    @ApiOperation(value = "查询可够商品列表", notes = "查询可够商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pagenum", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "pagesize", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "isvalid", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "name", dataType = "String", paramType = "query")

    })
    @RequestMapping(value = "getproductlist", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel getProductList(
            @RequestParam(value = "pagenum", required = false, defaultValue = "1") Integer pagenum,
            @RequestParam(value = "pagesize", required = false, defaultValue = "10") Integer pagesize,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "isvalid", required = false) Integer isvalid
    ) {
        PageHelper.startPage(pagenum, pagesize);
        PageHelper.orderBy("id asc");
        List<Product> productList = operatorService.getProductListbyCondition(null, name, isvalid);
        MapFromPageInfo<Product> mapFromPageInfo = new MapFromPageInfo<>(productList);
        return new ResponseModel(mapFromPageInfo);
    }


    //查询规格商品对应的规格列表
    @ApiOperation(value = "查询规格商品对应的规格列表", notes = "查询规格商品对应的规格列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productid", dataType = "int", paramType = "query", required = true)
    })
    @RequestMapping(value = "getunitlist", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel getProductUnitList(
            @RequestParam(value = "productid") Integer productid) {
        List<Unit> unitList = operatorService.getUnitList(null, productid, 1, null);
        return new ResponseModel(unitList);
    }

    //购买
    @ApiOperation(value = "购买商品，会在后台新增一个商户账号", notes = "购买商品，，会在后台新增一个商户账号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", dataType = "string", paramType = "query",required = true),
            @ApiImplicitParam(name = "name", dataType = "string", paramType = "query",required = true),
            @ApiImplicitParam(name = "phone", dataType = "string", paramType = "query",required = true),
            @ApiImplicitParam(name = "address", dataType = "string", paramType = "query",required = true),
            @ApiImplicitParam(name = "productid", dataType = "int", paramType = "query",required = true),
            @ApiImplicitParam(name = "unitid", dataType = "int", paramType = "query",required = true),
            @ApiImplicitParam(name = "shareid", dataType = "int", paramType = "query",value = "如果是通过分销购买的请传入该值")
    })
    @RequestMapping(value = "buy", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel buyProduct(
            @RequestParam(value = "openid") String openid,
            @RequestParam(value = "productid") Integer productid,
            @RequestParam(value = "unitid") Integer unitid,
            @RequestParam(value = "name") String name,
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "address") String address,
            @RequestParam(value = "shareid",required = false) Integer shareid
            ) {
        try {
            HashMap<String,Object> result = businessService.buyProdct(openid, productid, unitid,shareid,name,address,phone);
            return new ResponseModel(result);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseModel(500L,e.getMessage(),null);
        }

    }


    //购买成功回调函数，新增商户，新增活动
    @ApiOperation(value = "付款成功回调函数，功能是新增一个活动,默认最大中奖次数为100次", notes = "付款成功回调函数，功能是新增一个活动,默认最大中奖次数为100次")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderid", dataType = "string", paramType = "query",required = true),
    })
    @RequestMapping(value = "buycallback")
    @ResponseBody
    public void buyProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String orderid="";
        String request_string=WebUtils.getBodyString(request);
        String returnCode = StringUtils.getValueFromXml(request_string, "return_code");
        if(!"SUCCESS".equals(returnCode)){
            String responsestring="<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";;
            response.getWriter().write(responsestring);
            return;
        }
        String resultCode = StringUtils.getValueFromXml(request_string, "result_code");
        if(!"SUCCESS".equals(resultCode)){
            String responsestring="<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
            response.getWriter().write(responsestring);
            return;
        }
        orderid= StringUtils.getValueFromXml(request_string, "out_trade_no");
        if (StringUtils.isNullOrNone(orderid)){
            String responsestring="<xml><return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[未收到订单号]]></return_msg></xml>";;
            response.getWriter().write(responsestring);
            return;
        }


        try {
            businessService.updateBuybyOrderid(orderid);
            String responsestring="<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";;
            response.getWriter().write(responsestring);
            //付款成功之后判断是否是通过分销进行购买，若是则增加分销记录
            return;
       }catch (Exception e){
           e.printStackTrace();
            String responsestring="<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";;
            response.getWriter().write(responsestring);
            return;
       }
    }


//    //我的分销记录
//    @ApiOperation(value = "查询我的分销记录（购买之后有效）", notes = "查询我的分销记录（购买之后有效）")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "openid", dataType = "string", paramType = "query")
//    })
//    @RequestMapping(value = "mydistribution", method = RequestMethod.POST)
//    @ResponseBody
//    public ResponseModel getmydistributionList(
//            @RequestParam(value = "openid") String openid){
//        List<User> userlist = userService.findUserByOpenid(openid);
//        if (userlist == null)
//            return new ResponseModel(500L, "该用户未注册", null);
//        List<User> bussinessUserList=new ArrayList<>();
//        for(User item:userlist){
//            if (item.getType() == 2)
//                bussinessUserList.add(item);
//        }
//        if (bussinessUserList.isEmpty())
//            return new ResponseModel(500L, "该用户不属于商家", null);
//       List<Buy> distributionList= businessService.getMyDistribution(bussinessUserList.get(0).getId());
//       return new ResponseModel(distributionList);
//    }


    /**
	 * 用户兑奖
	 * @param openid 商家
	 * @param prizenum 中奖码
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/lottery/exchange", method = RequestMethod.POST)
	@ApiOperation(value = "用户兑换奖项", notes = "用户兑换奖项")
	public ResponseModel exchangeLottery(String openid, String prizenum){
		User user = userService.findUserByOpenidAndType(openid, 2);
		if(user == null){
			return new ResponseModel(404l, "用户不存在");
		}
		String name = null;
		synchronized (prizenum) {
			UserLottery userLottery = userLotteryService.findUserLotteryByPrizenum(prizenum);
			if(userLottery==null){
				return new ResponseModel(404l, "获奖信息不存在");
			}
			if(userLottery.getExchangedate()!=null){
				return new ResponseModel(500l, "奖项已兑换");
			}
			if(expired!=null){
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(userLottery.getLotterydate());
				calendar.add(Calendar.DAY_OF_YEAR, expired);
				if(new Date().after(calendar.getTime())){
					return new ResponseModel(500l, "奖项已过期");
				}
			}
			userLotteryService.exchangeUserLottery(userLottery.getId());
			LotteryItem lotteryItem = lotteryService.findLotteryItemById(userLottery.getLotteryitemid());
			name = lotteryItem.getName();
		}
		return new ResponseModel(0l, "兑换成功", name);
	}



    @ApiOperation(value = "获取客户端二维码", notes = "获取客户端二维码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openid", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "lotteryid", dataType = "int", paramType = "query", required = true),
            @ApiImplicitParam(name = "page", dataType = "String", paramType = "query")
    })
	@RequestMapping(value = "getclientqrcode",method = RequestMethod.GET,produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] getLotteryQrCode(String openid, Integer lotteryid,String page){
        List<User> userlist = userService.findUserByOpenid(openid);
        if (userlist == null){
            logger.error("未找到该用户");
            return null;
        }
        List<User> bussinessUserList=new ArrayList<>();
        for(User item:userlist){
            if (item.getType() == 2)
                bussinessUserList.add(item);
        }
        if (bussinessUserList.isEmpty()){
            logger.error("该用户不属于商家");
            return null;
        }
        Buy buy = businessService.getBuybyLotteryid(lotteryid);
        if (!buy.getUserid().equals(bussinessUserList.get(0).getId())){
            logger.error("该活动不属于该商家");
            return null;
        }
        String accessToken = wechatService.fetchclientAccessToken();
        if(StringUtils.isNullOrNone(accessToken)){
            logger.error("客户端accessToken为空");
            return null;
        }
        byte[] rqcode= wechatService.createWXACode(accessToken,lotteryid.toString(),page);
        if(rqcode==null)
            return null;
        return rqcode;
    }

}


