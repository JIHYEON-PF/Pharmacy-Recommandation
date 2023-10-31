package com.openapi.pharmacyrecommandation.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class KakaoUriBuilderService {
    private final static String KAKAO_LOCAL_SEARCH_ADDRES_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    public URI buildUriByAddress(String address) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_SEARCH_ADDRES_URL);

        uriBuilder.queryParam("query", address);

        URI uri = uriBuilder.build().encode().toUri();
        log.info("[KakaoUriBuilderService_builderUriByAddress] address: {}, uri: {}]", address, uri);

        return uri;
    }
}
