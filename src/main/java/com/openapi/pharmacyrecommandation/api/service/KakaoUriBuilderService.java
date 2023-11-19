package com.openapi.pharmacyrecommandation.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class KakaoUriBuilderService {
    private final static String KAKAO_LOCAL_SEARCH_ADDRES_URL = "https://dapi.kakao.com/v2/local/search/address.json";
    private final static String KAKAO_LOCAL_CATEGORY_SEARCH_URL = "https://dapi.kakao.com/v2/local/search/category.json";

    public URI buildUriByAddress(String address) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_SEARCH_ADDRES_URL);

        uriBuilder.queryParam("query", address);

        URI uri = uriBuilder.build().encode().toUri();
        log.info("[KakaoUriBuilderService_builderUriByAddress] address: {}, uri: {}]", address, uri);

        return uri;
    }

    public URI buildUriByCategorySearch(double latitude, double longitude, double radius, String category) {
        double meterRadius = radius * 1000;

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_CATEGORY_SEARCH_URL);
        uriBuilder.queryParam("category_group_name", category);
        uriBuilder.queryParam("x", longitude);
        uriBuilder.queryParam("y", latitude);
        uriBuilder.queryParam("radius", meterRadius);
        uriBuilder.queryParam("sort", "distance");

        URI uri = uriBuilder.build().encode().toUri();

        log.info("[KakaoAddressSearchService_buildUriByCategorySearch] uri: {}", uri);

        return uri;
    }
}
