package com.spring3.oauth.jwt.controller;


import com.spring3.oauth.jwt.dto.AddLicenseKeyRequst;
import com.spring3.oauth.jwt.models.BuyLicenseKey;
import com.spring3.oauth.jwt.models.LicenseKey;
import com.spring3.oauth.jwt.dto.ApiResponse;
import com.spring3.oauth.jwt.models.Softwares;
import com.spring3.oauth.jwt.models.UserInfo;
import com.spring3.oauth.jwt.services.LicenseKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/licensekey")
public class LicenseKeyController {
    @Autowired
    LicenseKeyService licenseKeyService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/addLicenseKey")
    public ResponseEntity<ApiResponse> saveSoftware(@RequestBody AddLicenseKeyRequst lkr) {
        ApiResponse apiResponse = null;
        int statusCode = 0;
        LicenseKey userResponse = null;
        try {
            LicenseKey licenseKey = new LicenseKey(0, lkr.getKeyName(), lkr.getKeyDuration(), lkr.getKeyPrice());
            userResponse = licenseKeyService.addLicense(licenseKey);
            statusCode = HttpStatus.OK.value();
            apiResponse = new ApiResponse(statusCode, "Success", userResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            apiResponse = new ApiResponse(statusCode, "Failed:"+e, userResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/assignKey")
    public ResponseEntity<ApiResponse> assignKey(@RequestParam List<String> keyIds, @RequestParam String softwareId) {
        ApiResponse apiResponse = null;
        int statusCode = 0;
        Softwares userResponse = null;
        try {
           // LicenseKey licenseKey = new LicenseKey(0, lkr.getKeyName(), lkr.getKeyDuration(), lkr.getKeyPrice());
            userResponse = licenseKeyService.assignKey(keyIds, softwareId);
            statusCode = HttpStatus.OK.value();
            apiResponse = new ApiResponse(statusCode, "Success", userResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            apiResponse = new ApiResponse(statusCode, "Failed:"+e, userResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/buyKey")
    public ResponseEntity<ApiResponse> buyKey(@RequestBody BuyLicenseKey buyLicenseKey) {
        ApiResponse apiResponse = null;
        int statusCode = 0;
        BuyLicenseKey buyLicenseKey1 = null;
        try {
            buyLicenseKey1 = licenseKeyService.buyKey(buyLicenseKey);
            statusCode = HttpStatus.OK.value();
            apiResponse = new ApiResponse(statusCode, "Success", buyLicenseKey1);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            apiResponse = new ApiResponse(statusCode, "Failed:"+e, buyLicenseKey1);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/deleteLicenseKey/{licenseKeyId}")
    public ResponseEntity<ApiResponse> deleteLicenseKey(@PathVariable("licenseKeyId") Long licenseKeyId) {
        ApiResponse apiResponse = null;
        int statusCode = 0;
        Boolean userResponse = null;
        try {
            Optional<LicenseKey> licenseKey = licenseKeyService.getLicenseDetailsById(licenseKeyId);
            userResponse = licenseKeyService.deleteLicense(licenseKey.get());
            statusCode = HttpStatus.OK.value();
            apiResponse = new ApiResponse(statusCode, "Success", userResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            apiResponse = new ApiResponse(statusCode, "Failed:"+e, userResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/getAllLicenseKey")
    public ResponseEntity<ApiResponse> getAllLicenseKey(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        @RequestParam(defaultValue = "id") String sortBy,
                                                        @RequestParam(defaultValue = "asc") String direction) {
        ApiResponse apiResponse = null;
        Page<LicenseKey> licenseKeys = null;
        int statusCode = 0;
        try {
            Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            Pageable pageable = PageRequest.of(page, size, sort);

            licenseKeys = licenseKeyService.getAllLicense(pageable);
            statusCode = HttpStatus.OK.value();
            apiResponse = new ApiResponse(statusCode, "Success", licenseKeys);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            apiResponse = new ApiResponse(statusCode, "Failed:"+e, licenseKeys);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/getLicenseKeyDetailsById/{licenseKeyId}")
    public ResponseEntity<ApiResponse> getLicenseKeyDetailsById(@PathVariable("licenseKeyId") Long licenseKeyId) {
        ApiResponse apiResponse = null;
        Optional<LicenseKey> licenseKey = null;
        int statusCode = 0;
        try {
            licenseKey = licenseKeyService.getLicenseDetailsById(licenseKeyId);
            statusCode = HttpStatus.OK.value();
            apiResponse = new ApiResponse(statusCode, "Success", licenseKey.get());
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            apiResponse = new ApiResponse(statusCode, "Failed:"+e, licenseKey.get());
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
