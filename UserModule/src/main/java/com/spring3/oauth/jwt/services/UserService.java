package com.spring3.oauth.jwt.services;

import com.spring3.oauth.jwt.dtos.SignupRequest;
import com.spring3.oauth.jwt.dtos.SubAgentListResponse;
import com.spring3.oauth.jwt.dtos.UserInfoResponse;
import com.spring3.oauth.jwt.models.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface UserService {

    UserInfo saveUser(SignupRequest userInfo, HttpServletRequest httpServletRequest);

    UserInfo getUser();

    Page<UserInfo> getAllUser(Pageable pageable);
     String  returnClientIp(HttpServletRequest request);
    UserInfo updateAgentInfo(UserInfo userInfo);

    UserInfoResponse updateUser(UserInfo userInfoRequest, HttpServletRequest httpServletRequest);
    SubAgentListResponse getSubAgentList(String userId);
    UserInfo getUserByUserName(String userName);

    UserInfo assignRole(List<String> roleIds, String userId);

    UserInfo revokRole(List<String> roleIds, String userId);

    Boolean deleteUser(Long userId);
    Optional<UserInfo> getAgentProfile(String userId);

    List<UserInfo> getPaginatedUsers(int limit, int offset);

    SubAgentListResponse getPaginatedSubAgentList(String userId, Pageable pageable);
}
