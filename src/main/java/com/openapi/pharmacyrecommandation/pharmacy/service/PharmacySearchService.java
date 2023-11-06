package com.openapi.pharmacyrecommandation.pharmacy.service;

import com.openapi.pharmacyrecommandation.pharmacy.entity.dto.PharmacyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PharmacySearchService {
    private final PharmacyRepositoryService pharmacyRepositoryService;

    public List<PharmacyDto> searchPharmacyDtoList() {
        return pharmacyRepositoryService.findAll().stream()
                .map(PharmacyDto::from)
                .collect(Collectors.toList());
    }
}
