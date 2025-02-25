package com.spring3.oauth.jwt.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateUserRequest {
    private long id;
    private String username;
    private String deviceType;
    private String status;
}
