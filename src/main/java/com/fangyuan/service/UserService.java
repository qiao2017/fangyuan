package com.fangyuan.service;

import com.fangyuan.dao.LoginTokenDAO;
import com.fangyuan.dao.UserDAO;
import com.fangyuan.entity.HostHolder;
import com.fangyuan.entity.LoginToken;
import com.fangyuan.entity.User;
import com.fangyuan.exception.RegisterException;
import com.fangyuan.so.LoginSO;
import com.fangyuan.util.MD5Util;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    UserDAO userDAO;

    @Autowired
    LoginTokenDAO loginTokenDAO;

    @Autowired
    HostHolder holder;

    public String register(LoginSO so) throws RegisterException{
        User user = userDAO.selectByName(so.getUsername());
        if (user != null) {
            throw new RegisterException(user.getUsername());
        }

        // 密码强度加强
        user = new User();
        user.setUsername(so.getUsername());
        user.setSalt(UUID.randomUUID().toString().replace("-", ""));
        user.setPassword(MD5Util.md5(so.getPassword()+user.getSalt()));
        user.setCreateDate(new Date());
        user.setNickName(so.getUsername());
        userDAO.addUser(user);
        user = userDAO.selectByName(so.getUsername());
        holder.setUser(user);
        // 登陆
        return addLoginToken(user.getUserId());
    }

    public String login(LoginSO so) {
        User user = userDAO.selectByName(so.getUsername());
        if (user == null){
            return null;
        }

        if (!MD5Util.md5(so.getPassword()+user.getSalt()).equals(user.getPassword())) {
            return null;
        }
        holder.setUser(user);
        return addLoginToken(user.getUserId());
    }

    public Map<String, Object> getUserInfo(){
        Map<String, Object> res = new HashMap<>();
        User user = holder.getUser();
//        User resUser = userDAO.sl
        res.put("userInfo", user);
        return res;
    }

    private String addLoginToken(int userId) {
        LoginToken token = new LoginToken();
        token.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000*3600*24*7);//有效期7天
        token.setExpired(date);
        token.setStatus(LoginToken.VALID_TOKEN);
        token.setToken(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTokenDAO.addToken(token);
        return token.getToken();
    }

    public void logout(String token) {
        loginTokenDAO.updateStatus(token, LoginToken.INVALID_TOKEN);
    }

}
