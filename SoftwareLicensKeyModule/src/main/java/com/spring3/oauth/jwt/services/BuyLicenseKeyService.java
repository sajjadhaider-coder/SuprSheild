package com.spring3.oauth.jwt.services;

import com.spring3.oauth.jwt.models.BuyLicenseKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BuyLicenseKeyService {

    public Page<BuyLicenseKey> getAllBuyLicenseKey(Pageable pageable);

    public Page<BuyLicenseKey> getByUserId(Long userId, Pageable pageable);

    public void buyLicenseKeyExpiryValidate();
}
