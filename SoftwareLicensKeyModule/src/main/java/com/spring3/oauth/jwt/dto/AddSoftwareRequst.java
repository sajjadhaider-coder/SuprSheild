package com.spring3.oauth.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddSoftwareRequst {

    private String softwareName;
    private String softwareURL;
}
