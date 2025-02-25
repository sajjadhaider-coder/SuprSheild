package com.spring3.oauth.jwt.services;

import com.spring3.oauth.jwt.models.UserInfo;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;


public interface AuthService {

    UserInfo saveUser(UserInfo userInfo);

    UserInfo getUser();

    List<UserInfo> getAllUser();
     String  returnClientIp(HttpServletRequest request);

    UserInfo updateUser(UserInfo userInfoRequest, HttpServletRequest httpServletRequest);

    UserInfo getUserByUserName(String userName);

    UserInfo findByUserId(String userId);
    Boolean deleteUser(Long userId);

}
