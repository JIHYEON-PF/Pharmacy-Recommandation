package com.openapi.pharmacyrecommandation.direction.entity;

import com.openapi.pharmacyrecommandation.api.dto.DocumentDto;
import com.openapi.pharmacyrecommandation.pharmacy.entity.dto.PharmacyDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity(name = "direction")
public class Direction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String inputAddress;
    private double inputLatitude;
    private double inputLongitude;

    private String targetPharmacyName;
    private String targetAddress;
    private double targetLatitude;
    private double targetLongitude;

    private double distance;

    public static Direction createDirection(DocumentDto documentDto, PharmacyDto pharmacyDto, double distance) {
        return Direction.builder()
                .inputAddress(documentDto.getAddressName())
                .inputLatitude(documentDto.getLatitude())
                .inputLongitude(documentDto.getLongitude())
                .targetPharmacyName(pharmacyDto.getPharmacyName())
                .targetAddress(pharmacyDto.getPharmacyAddress())
                .targetLatitude(pharmacyDto.getLatitude())
                .targetLongitude(pharmacyDto.getLongitude())
                .distance(distance)
                .build();
    }
}
