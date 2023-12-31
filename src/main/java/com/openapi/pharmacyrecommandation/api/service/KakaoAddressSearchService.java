package com.openapi.pharmacyrecommandation.api.service;

import com.openapi.pharmacyrecommandation.api.dto.KakaoApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoAddressSearchService {
    private final KakaoUriBuilderService kakaoUriBuilderService;
    private final RestTemplate restTemplate;

    @Value("${kakao.rest.api.key}")
    private String kakaoRestApiKey;

    @Retryable(
            value = {RuntimeException.class},
            maxAttempts = 2,
            backoff = @Backoff(delay = 2000)
    )
    public KakaoApiResponseDto requestAddressSearch(String address) {
        if (ObjectUtils.isEmpty(address)) {
            return null;
        }

        return restTemplate.exchange(
                createUri(address),
                HttpMethod.GET,
                createHttpEntity(),
                KakaoApiResponseDto.class
        ).getBody();
    }

    @Recover
    public KakaoApiResponseDto recover(RuntimeException re, String address) {
        log.error("All the retires failed. address: {}, error: {}", address, re.getMessage());
        return null;
    }

    private URI createUri(String queryParam) {
        return kakaoUriBuilderService.buildUriByAddress(queryParam);
    }

    private HttpEntity createHttpEntity() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoRestApiKey);

        return new HttpEntity<>(httpHeaders);
    }
}
