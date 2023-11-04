package com.openapi.pharmacyrecommandation.pharmacy.service;

import com.openapi.pharmacyrecommandation.pharmacy.entity.Pharmacy;
import com.openapi.pharmacyrecommandation.pharmacy.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PharmacyRepositoryService {
    private final PharmacyRepository pharmacyRepository;

    @Transactional
    public void updateAddress(Long id, String address) {
        Pharmacy pharmacy = pharmacyRepository.findById(id)
                .orElse(null);

        if (Objects.isNull(pharmacy)) {
            log.error("[PharmacyRepositoryService updateAddress] not found id: {}", id);
            return;
        }

        pharmacy.updatePharmacyAddress(address);
    }

    public void updateAddressWithoutTransaction(Long id, String address) {
        Pharmacy pharmacy = pharmacyRepository.findById(id)
                .orElse(null);

        if (Objects.isNull(pharmacy)) {
            log.error("[PharmacyRepositoryService updateAddress] not found id: {}", id);
            return;
        }

        pharmacy.updatePharmacyAddress(address);
    }
}
