package com.spring3.oauth.jwt.controller;

import com.spring3.oauth.jwt.dto.AddSoftwareRequst;
import com.spring3.oauth.jwt.dto.ApiResponse;
import com.spring3.oauth.jwt.models.Softwares;
import com.spring3.oauth.jwt.services.SoftwareService;
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
@RequestMapping("/api/v1/software")
public class SoftwareController {
    @Autowired
    SoftwareService softwareService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/addSoftware")
    public ResponseEntity<ApiResponse> saveSoftware(@RequestBody AddSoftwareRequst addSoftwareRequst) {
        ApiResponse apiResponse = null;
        int statusCode = 0;
        Softwares userResponse = null;
        try {
            Softwares softwares = new Softwares(0L, addSoftwareRequst.getSoftwareName(), addSoftwareRequst.getSoftwareURL(), null);
            userResponse = softwareService.addSoftware(softwares);
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
    @PostMapping(value = "/deleteSoftware/{softwareId}")
    public ResponseEntity<ApiResponse> deleteSoftware(@PathVariable("softwareId") Long softwareId) {
        ApiResponse apiResponse = null;
        int statusCode = 0;
        Boolean userResponse = null;
        try {
            Optional<Softwares> softwares = softwareService.getSoftwareDetailsById(softwareId);
            userResponse = softwareService.deleteSoftware(softwares.get());
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
    @PostMapping(value = "/getAllSoftware")
    public ResponseEntity<ApiResponse> getAllSoftware(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "10") int size,
                                                      @RequestParam(defaultValue = "id") String sortBy,
                                                      @RequestParam(defaultValue = "asc") String direction) {
        ApiResponse apiResponse = null;
        Page<Softwares> softwares = null;
        int statusCode = 0;
        try {
            Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
            Pageable pageable = PageRequest.of(page, size, sort);

            softwares = softwareService.getAllSoftwares(pageable);
            statusCode = HttpStatus.OK.value();
            apiResponse = new ApiResponse(statusCode, "Success", softwares);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            apiResponse = new ApiResponse(statusCode, "Failed:"+e, softwares);
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/getSoftwareDetailsById/{softwareId}")
    public ResponseEntity<ApiResponse> getSoftwareDetailsById(@PathVariable("softwareId") Long softwareId) {
        ApiResponse apiResponse = null;
        Optional<Softwares> softwares = null;
        int statusCode = 0;
        try {
            softwares = softwareService.getSoftwareDetailsById(softwareId);
            statusCode = HttpStatus.OK.value();
            apiResponse = new ApiResponse(statusCode, "Success", softwares.get());
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
            apiResponse = new ApiResponse(statusCode, "Failed:"+e, softwares.get());
            return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
