package com.openapi.pharmacyrecommandation.pharmacy.entity.dto;

import com.openapi.pharmacyrecommandation.pharmacy.entity.Pharmacy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PharmacyDto {
    private Long id;
    private String pharmacyName;
    private String pharmacyAddress;
    private double latitude;
    private double longitude;

    public static PharmacyDto from(Pharmacy pharmacy) {
        return PharmacyDto.builder()
                .id(pharmacy.getId())
                .pharmacyName(pharmacy.getPharmacyName())
                .pharmacyAddress(pharmacy.getPharmacyAddress())
                .latitude(pharmacy.getLatitude())
                .longitude(pharmacy.getLongitude())
                .build();
    }
}
