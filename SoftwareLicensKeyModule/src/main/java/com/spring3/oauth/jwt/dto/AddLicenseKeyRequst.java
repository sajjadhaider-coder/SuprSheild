package com.spring3.oauth.jwt.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddLicenseKeyRequst {

    private String keyName;
    private String keyDuration;  // monthly, quatarly, annually
    private String keyPrice;
}
