package com.spring3.oauth.jwt.services;


import com.spring3.oauth.jwt.models.Softwares;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SoftwareService {

    Softwares addSoftware(Softwares softwares);
    Boolean deleteSoftware(Softwares softwares);
    Page<Softwares> getAllSoftwares(Pageable pageable);
    Softwares updateSoftware(Softwares softwares);

    Optional<Softwares> getSoftwareDetailsById(Long softwareId);

}
