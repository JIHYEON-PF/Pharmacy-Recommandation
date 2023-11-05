package com.openapi.pharmacyrecommandation.pharmacy.service;

import com.openapi.pharmacyrecommandation.pharmacy.entity.Pharmacy;
import com.openapi.pharmacyrecommandation.pharmacy.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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

    @Transactional(readOnly = true)
    public List<Pharmacy> findAll() {
        return pharmacyRepository.findAll();
    }
}
