package com.spring3.oauth.jwt.controller;

import com.spring3.oauth.jwt.dto.ApiResponse;
import com.spring3.oauth.jwt.models.BuyLicenseKey;
import com.spring3.oauth.jwt.models.LicenseKey;
import com.spring3.oauth.jwt.services.BuyLicenseKeyService;
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

@RestController
@RequestMapping("/api/v1/buylicensekey")
public class BuyLicenseKeyController {

    @Autowired
    BuyLicenseKeyService buyLicenseKeyService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/getAllBuyLicenseKey")
    public ResponseEntity<ApiResponse> getAllBuyLicenseKey(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(defaultValue = "id") String sortBy,
                                                           @RequestParam(defaultValue = "asc") String direction) {
        ApiResponse apiResponse = null;
        Page<BuyLicenseKey> buyLicenseKeys = null;
        int statusCode = 0;
        try {
            Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            Pageable pageable = PageRequest.of(page, size, sort);

            buyLicenseKeys = buyLicenseKeyService.getAllBuyLicenseKey(pageable);
            if (!buyLicenseKeys.isEmpty()) {
                apiResponse = new ApiResponse(HttpStatus.OK.value(), "Success", buyLicenseKeys);
                return new ResponseEntity<>(apiResponse, HttpStatus.OK);
            } else {
                apiResponse = new ApiResponse(HttpStatus.BAD_REQUEST.value(), "Buy License Keys not found", null);
                return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            apiResponse = new ApiResponse(statusCode, "Failed:" + e, null);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/byUserId/{id}")
    public ResponseEntity<ApiResponse> getBuyLicenseKey(@PathVariable Long id,
                                                        @RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size,
                                                        @RequestParam(defaultValue = "id") String sortBy,
                                                        @RequestParam(defaultValue = "asc") String direction) {
        ApiResponse apiResponse = null;
        Page<BuyLicenseKey> buyLicenseKeys = null;
        int statusCode = 0;
        try {
            Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            Pageable pageable = PageRequest.of(page, size, sort);

            buyLicenseKeys = buyLicenseKeyService.getByUserId(id, pageable);
            if (!buyLicenseKeys.isEmpty()) {
                apiResponse = new ApiResponse(HttpStatus.OK.value(), "Success", buyLicenseKeys);
                return new ResponseEntity<>(apiResponse, HttpStatus.OK);
            } else {
                apiResponse = new ApiResponse(HttpStatus.BAD_REQUEST.value(), "Buy License Keys not found by user id", null);
                return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            apiResponse = new ApiResponse(statusCode, "Failed:" + e, null);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
