package com.spring3.oauth.jwt.services;


import com.spring3.oauth.jwt.models.BuyLicenseKey;
import com.spring3.oauth.jwt.models.LicenseKey;
import com.spring3.oauth.jwt.models.Softwares;
import com.spring3.oauth.jwt.models.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface LicenseKeyService {

    LicenseKey addLicense(LicenseKey licenseKey);
    Boolean deleteLicense(LicenseKey licenseKey);
    Page<LicenseKey> getAllLicense(Pageable pageable);
    LicenseKey updateLicense(LicenseKey licenseKey);

    Optional<LicenseKey> getLicenseDetailsById(Long licenseKeyId);
    Softwares assignKey(List<String> keyIds, String softwareId);
    BuyLicenseKey buyKey(BuyLicenseKey buyLicenseKey);

}
