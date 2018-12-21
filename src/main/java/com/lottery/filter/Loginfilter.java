package com.lottery.filter;


import com.lottery.common.ResponseModel;
import com.lottery.model.User;
import com.lottery.utils.JsonUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Deque;
import java.util.LinkedList;

public class Loginfilter extends AuthenticatingFilter {


    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return null;
    }

    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) {
        if (isLoginRequest(servletRequest, servletResponse)) {
            return true;
        } else {
            Subject subject = getSubject(servletRequest, servletResponse);
            if(!subject.isAuthenticated())
                return false;
            return true;
        }


    }

    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletResponse httpResponse;
        httpResponse = WebUtils.toHttp(servletResponse);
        httpResponse.setStatus(200);
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json; charset=utf-8");
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        httpResponse.setHeader("Access-Control-Max-Age", "3600");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Origin,No-Cache,X-Requested-With,If-Modified-Since,Pragma,Last-Modified,Cache-Control,Expires,Content-Type,X-E4M-With");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        PrintWriter out = null;
        try {
            out = httpResponse.getWriter();
            ResponseModel Return = new ResponseModel(1001l, "未登录", null);
            String jsonString = JsonUtils.toJson(Return);
            out.append(jsonString);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
        return false;
    }
}