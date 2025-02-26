package com.spring3.oauth.jwt.services;

import com.spring3.oauth.jwt.repository.SoftwareRepository;
import com.spring3.oauth.jwt.models.Softwares;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SoftwareServiceImpl implements SoftwareService {

    @Autowired
    SoftwareRepository softwareRepository;

    @Override
    public Softwares addSoftware(Softwares softwares) {
        return softwareRepository.save(softwares);
    }


    @Override
    public Boolean deleteSoftware(Softwares softwares) {
        try {
            Boolean isDeleted = false;
            try {
                softwareRepository.delete(softwares);
                isDeleted = true;
            } catch (Exception e) {
                isDeleted = false;
            }
            return isDeleted;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Page<Softwares> getAllSoftwares(Pageable pageable) {
        try {
            Page<Softwares> softwaresList = null;
            try {
                softwaresList = softwareRepository.findAll(pageable);
            } catch (Exception e) {

            }
            return softwaresList;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    public Softwares updateSoftware(Softwares softwares) {
        return null;
    }

    @Override
    public Optional<Softwares> getSoftwareDetailsById(Long softwareId) {
        try {
            return softwareRepository.findById(softwareId);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
