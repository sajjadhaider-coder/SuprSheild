package com.spring3.oauth.jwt.repository;

import com.spring3.oauth.jwt.models.LicenseKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LicenseKeyRepository extends JpaRepository<LicenseKey, Long> {

}
