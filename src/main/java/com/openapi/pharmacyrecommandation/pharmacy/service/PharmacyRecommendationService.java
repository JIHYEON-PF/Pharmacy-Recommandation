package com.openapi.pharmacyrecommandation.pharmacy.service;

import com.openapi.pharmacyrecommandation.api.dto.DocumentDto;
import com.openapi.pharmacyrecommandation.api.dto.KakaoApiResponseDto;
import com.openapi.pharmacyrecommandation.api.service.KakaoAddressSearchService;
import com.openapi.pharmacyrecommandation.direction.entity.Direction;
import com.openapi.pharmacyrecommandation.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class PharmacyRecommendationService {
    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;
    
    private void recommendPharmacyList(String address) {
        KakaoApiResponseDto responseDto = kakaoAddressSearchService.requestAddressSearch(address);
        
        if (Objects.isNull(responseDto) || CollectionUtils.isEmpty(responseDto.getDocuments())) {
            log.error("[PharmacyRecommendationService_recommendPharmacyList fail] Input address: {}", address);
            return;
        }

        DocumentDto documentDto = responseDto.getDocuments().get(0);

//        List<Direction> directions = directionService.buildDirectionList(documentDto);
        List<Direction> directions = directionService.buildDirectionListByCategoryApi(documentDto);
        directionService.saveAll(directions);
    }
}
