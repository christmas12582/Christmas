package com.lottery.controller;


import com.lottery.common.ResponseModel;
import com.lottery.model.User;
import com.lottery.service.UserService;
import com.lottery.utils.HttpClientUtil;
import com.lottery.utils.JsonUtils;
import com.lottery.utils.MySecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.net.URL;
import java.util.Deque;
import java.util.HashMap;

@Controller
@Api("登录认证")
public class AuthController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "登录", notes = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phone", dataType = "string", paramType = "query",value = "账号是13668054060"),
            @ApiImplicitParam(name = "password", dataType = "string", paramType = "query",value = "密码是yyj651002")
    })
    @RequestMapping(value = "login" ,method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel login(@RequestParam(value = "phone") String phone,
                               @RequestParam(value = "password") String password,
                               HttpSession session){
        Subject subject = SecurityUtils.getSubject();
        if(!subject.isAuthenticated()){
            try {
                UsernamePasswordToken token = new UsernamePasswordToken(phone,password);
                subject.login(token);
                User loginedUser=(User)subject.getPrincipal();
                loginedUser.setPassword(null);
                session.setAttribute("userinfo",loginedUser);
                return new ResponseModel(0L,"登录成功",loginedUser);
            }catch (UnknownAccountException | LockedAccountException une){
                return new ResponseModel(500L,"登录失败",une.getMessage());
            } catch (AuthenticationException ae){
                return new ResponseModel(500L,"登录失败","账号密码错误");
            }
            catch (Exception e){
                e.printStackTrace();
                return new ResponseModel(500L,"登录失败，未知错误",e.getMessage());
            }

        }else{
            User loginedUser=(User)session.getAttribute("userinfo");
            return new ResponseModel(0L,"账号已登录",loginedUser);
        }

    }


    @ApiOperation(value = "注销", notes = "注销")
    @RequestMapping(value = "logout" ,method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel logout(){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        User userinfo = (User) session.getAttribute("userinfo");
        if (userinfo != null) {
            subject.logout();
            return new ResponseModel(0L,"注销成功",null);
        }else {
            return new ResponseModel(0L,"您尚未登录",null);
        }
    }

    @ApiOperation(value = "增加运营商", notes = "增加运营商")
    @RequestMapping(value = "addoperator" ,method = RequestMethod.POST)
    @ResponseBody
    @RequiresRoles("1")
    public ResponseModel addoperator(
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "passowrd") String passowrd,
            @RequestParam(value = "openid") String openid
    ){
        User user = new User();
        user.setIsvalid(1);
        user.setType(1);
        user.setOpenid(openid);
        user.setPassword(MySecurityUtil.encode(passowrd,phone));
        user.setPhone(phone);
        userService.saveUser(user);
        return new ResponseModel(0L,"新增运营商成功",null);
    }



    @ApiOperation(value = "获取微信信息", notes = "获取微信信息")
    @RequestMapping(value = "getseesion" ,method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel getSeesion(
            @RequestParam(value = "appid") String appid,
            @RequestParam(value = "secret") String secret,
            @RequestParam(value = "js_code") String js_code


    ){
        String url="https://api.weixin.qq.com/sns/jscode2session?";
        url+="appid="+appid+"&secret="+secret+"&js_code="+js_code+"&grant_type=authorization_code";
        String response= HttpClientUtil.doGet(url);
        HashMap<String,Object> response_map= JsonUtils.toObject(response,HashMap.class);
        return new ResponseModel(0L,"获取微信信息成功",response_map);
    }

}
