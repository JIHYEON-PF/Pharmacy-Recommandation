package com.openapi.pharmacyrecommandation.pharmacy.service;

import com.openapi.pharmacyrecommandation.pharmacy.entity.Pharmacy;
import com.openapi.pharmacyrecommandation.pharmacy.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SelfInvocationService {
    private final PharmacyRepository pharmacyRepository;

    @Transactional
    public void bar(List<Pharmacy> pharmacies) {
        log.info("bar CurrentTransactionName: {}", TransactionSynchronizationManager.getCurrentTransactionName());
        foo(pharmacies);
    }

    public void foo(List<Pharmacy> pharmacies) {
        log.info("foo CurrentTransactionName: {}", TransactionSynchronizationManager.getCurrentTransactionName());

        pharmacies.forEach(pharmacy -> {
            pharmacyRepository.save(pharmacy);
            throw new RuntimeException("error");
        });
    }
}
