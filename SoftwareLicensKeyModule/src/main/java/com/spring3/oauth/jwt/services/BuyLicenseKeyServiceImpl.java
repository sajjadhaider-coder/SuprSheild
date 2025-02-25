package com.spring3.oauth.jwt.services;

import com.spring3.oauth.jwt.models.BuyLicenseKey;
import com.spring3.oauth.jwt.repository.BuyLicenseKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BuyLicenseKeyServiceImpl implements BuyLicenseKeyService {

    @Autowired
    BuyLicenseKeyRepository buyLicenseKeyRepo;

    @Override
    public Page<BuyLicenseKey> getAllBuyLicenseKey(Pageable pageable) {

        return buyLicenseKeyRepo.findAll(pageable);
    }

    @Override
    public Page<BuyLicenseKey> getByUserId(Long userId, Pageable pageable) {

        return buyLicenseKeyRepo.findByUserId(userId, pageable);
    }

    @Override
    public void buyLicenseKeyExpiryValidate() {
        List<BuyLicenseKey> buyLicenseKey = buyLicenseKeyRepo.findAll();
        if (!buyLicenseKey.isEmpty()) {
            buyLicenseKey.forEach(buyLicense -> {
                LocalDateTime expiryDate = buyLicense.getExpirydate();

                // Get today's date
                LocalDateTime today = LocalDateTime.now();

                // Check if the expiry date is within 5 days
                long daysLeft = ChronoUnit.DAYS.between(today, expiryDate);

                // If the expiry date is within 5 days,
                if (daysLeft == 5) {
                    // "Warning: Your license will expire in 5 days
                } else if (daysLeft <= 0) {
//                    Your license has expired.
                }
            });
        }
    }
}
