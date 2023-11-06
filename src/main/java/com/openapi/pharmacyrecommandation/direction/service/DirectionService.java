package com.openapi.pharmacyrecommandation.direction.service;

import com.openapi.pharmacyrecommandation.api.dto.DocumentDto;
import com.openapi.pharmacyrecommandation.direction.entity.Direction;
import com.openapi.pharmacyrecommandation.pharmacy.entity.dto.PharmacyDto;
import com.openapi.pharmacyrecommandation.pharmacy.service.PharmacySearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class DirectionService {
    private final PharmacySearchService pharmacySearchService;
    private static final double earthRadius = 6371;
    private static final int MAX_SEARCH_COUNT = 3;
    private static final double RADIUS_KM = 10.0;

    public List<Direction> buildDirectionList(DocumentDto documentDto) {
        if (Objects.isNull(documentDto)) {
            return Collections.emptyList();
        }
        return pharmacySearchService.searchPharmacyDtoList().stream()
                .map(pharmacyDto -> Direction.createDirection(documentDto, pharmacyDto, calculateDistance(documentDto, pharmacyDto)))
                .filter(direction -> direction.getDistance() <= RADIUS_KM)
                .sorted(Comparator.comparing(Direction::getDistance))
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());
    }

    private double calculateDistance(DocumentDto documentDto, PharmacyDto pharmacyDto) {
        return calculateDistance(documentDto.getLatitude(), documentDto.getLongitude(), pharmacyDto.getLatitude(), pharmacyDto.getLongitude());
    }

    // Haversine formula
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);
        lon1 = Math.toRadians(lon1);
        lon2 = Math.toRadians(lon2);

        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
    }
}
