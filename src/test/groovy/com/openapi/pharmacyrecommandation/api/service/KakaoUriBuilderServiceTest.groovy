package com.openapi.pharmacyrecommandation.api.service

import spock.lang.Specification

import java.nio.charset.StandardCharsets

class KakaoUriBuilderServiceTest extends Specification {
    private KakaoUriBuilderService kakaoUriBuilderService;

    def setup() {
        kakaoUriBuilderService = new KakaoUriBuilderService();
    }

    def "buildUriByAddressSearch - 한글 파라미터일 경우 정상적으로 인코딩"() {
        given:
        String address = "서울 강동구";
        def charset = StandardCharsets.UTF_8;

        when:
        def uri = kakaoUriBuilderService.buildUriByAddress(address);
        def decodedResult = URLDecoder.decode(uri.toString(), charset);

        then:
        decodedResult == "https://dapi.kakao.com/v2/local/search/address.json?query=서울 강동구"
    }
}
