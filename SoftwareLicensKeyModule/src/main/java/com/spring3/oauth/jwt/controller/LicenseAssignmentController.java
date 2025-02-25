package com.spring3.oauth.jwt.controller;

import com.spring3.oauth.jwt.services.LicenseKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/license-keys")
public class LicenseAssignmentController {
    @Autowired
    private LicenseKeyService licenseKeyService;

    // API to assign a license key to software
    @PostMapping("/assign")
    public ResponseEntity<String> assignLicenseKey(
            @RequestParam String softwareId,
            @RequestParam String licenseKey) {
        //licenseKeyService.assignLicenseKey(softwareId, licenseKey);
        return ResponseEntity.ok("The API is under development");
    }

    // API to unassign a license key from software
    @PostMapping("/unassign")
    public ResponseEntity<String> unassignLicenseKey(
            @RequestParam String softwareId,
            @RequestParam String licenseKey) {
       // licenseKeyService.unassignLicenseKey(softwareId, licenseKey);
        return ResponseEntity.ok("The API is under development");
    }
}
