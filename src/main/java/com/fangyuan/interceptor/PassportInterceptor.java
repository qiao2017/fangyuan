package com.fangyuan.interceptor;

import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.fangyuan.dao.LoginTokenDAO;
import com.fangyuan.dao.UserDAO;
import com.fangyuan.entity.HostHolder;
import com.fangyuan.entity.LoginToken;
import com.fangyuan.entity.User;
import com.fangyuan.result.CodeMsg;
import com.fangyuan.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author 乔莹
 * @copyright qiao
 */
@Component
@Slf4j
public class PassportInterceptor implements HandlerInterceptor{
    @Autowired
    LoginTokenDAO loginTokenDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    HostHolder hostHolder;
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        hostHolder.clear();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        String token = null;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        if(request.getParameter("token") != null){
            token = request.getParameter("token");
        }
        if (token == null){
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=utf-8");
            OutputStream os = response.getOutputStream();
            Result result = Result.failure(CodeMsg.SESSION_NOTFOUND);
            os.write(JSON.toJSONBytes(result));
            os.close();
            return false;
        }

        LoginToken loginToken = loginTokenDAO.selectByToken(token);
        if (loginToken == null || loginToken.getExpired().before(new Date()) || LoginToken.INVALID_TOKEN.equals(loginToken.getStatus())) {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=utf-8");
            OutputStream os = response.getOutputStream();
            Result result = Result.failure(CodeMsg.SESSION_ERROR);
            os.write(JSON.toJSONBytes(result));
            os.close();
            return false;
        }
        User user = userDAO.selectById(loginToken.getUserId());
        hostHolder.setUser(user);
        return true;
    }

}
