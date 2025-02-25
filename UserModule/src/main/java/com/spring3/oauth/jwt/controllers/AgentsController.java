package com.spring3.oauth.jwt.controllers;

import com.spring3.oauth.jwt.dtos.ApiResponse;
import com.spring3.oauth.jwt.dtos.SubAgentListResponse;
import com.spring3.oauth.jwt.models.UserInfo;
import com.spring3.oauth.jwt.services.AgentService;
import com.spring3.oauth.jwt.services.UserService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8841")
@RequestMapping("/api/v1/agent")
public class AgentsController {

    @Autowired
    AgentService agentService;


    @Hidden
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getSubAgentList/{userId}")
    public ResponseEntity<ApiResponse> getSubAgentList(@PathVariable String userId){
        agentService.getSubAgentList(userId);
        int statusCode = 0;
        ApiResponse response = null;
        try {
            statusCode = HttpStatus.OK.value();
            SubAgentListResponse userInfoList = agentService.getSubAgentList(userId);
            response = new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", userInfoList);
        } catch(Exception e) {
            statusCode = HttpStatus.UNAUTHORIZED.value();
            response = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "FAILURE", null);
        }
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(statusCode));
    }

    @Hidden
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/getAgentProfile/{userId}")
    public  ResponseEntity<ApiResponse> getAgentProfile(@PathVariable String userId) {
        int statusCode = 0;
        ApiResponse response = null;
        try {
            statusCode = HttpStatus.OK.value();
            UserInfo agentProfile = agentService.getAgentProfile(userId);
            response = new ApiResponse<>(HttpStatus.OK.value(), "SUCCESS", agentProfile);
        } catch(Exception e) {
            statusCode = HttpStatus.UNAUTHORIZED.value();
            response = new ApiResponse<>(HttpStatus.UNAUTHORIZED.value(), "FAILURE", null);
        }
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(statusCode));
    }


}
