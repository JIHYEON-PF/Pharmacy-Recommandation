package com.openapi.pharmacyrecommandation.api.service

import com.openapi.pharmacyrecommandation.AbstractIntegrationContainerBaseTest
import com.openapi.pharmacyrecommandation.api.dto.DocumentDto
import com.openapi.pharmacyrecommandation.api.dto.KakaoApiResponseDto
import com.openapi.pharmacyrecommandation.api.dto.MetaDto
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper


class KakaoAddressSearchServiceRetryTest extends AbstractIntegrationContainerBaseTest {
    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService;

    @SpringBean
    private KakaoUriBuilderService kakaoUriBuilderService = Mock();

    private MockWebServer mockWebServer;
    private ObjectMapper mapper = new ObjectMapper();
    private String inputAddress = "서울 성북구 종암로 10길";

    def setup() {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        println mockWebServer.port;
        println mockWebServer.url("/");
    }

    def cleanup() {
        mockWebServer.shutdown();
    }

    def "requestAddressSearch retry success"() {
        given:
        def metaDto = new MetaDto(1);
        def documentDto = DocumentDto.builder()
                .addressName(inputAddress)
                .build();
        def expectResposne = new KakaoApiResponseDto(metaDto, Arrays.asList(documentDto));
        def uri = mockWebServer.url("/").uri();

        when:
        mockWebServer.enqueue(new MockResponse().setResponseCode(504))
        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(mapper.writeValueAsString(expectResposne)))

        def kakaoApiResult = kakaoAddressSearchService.requestAddressSearch(inputAddress);

        then:
        2 * kakaoUriBuilderService.buildUriByAddress(inputAddress) >> uri;
        kakaoApiResult.getDocuments().size() == 1
        kakaoApiResult.getMetaDto().totalCount == 1
        kakaoApiResult.getDocuments().get(0).getAddressName() == inputAddress
    }

    def "requestAddressSearch retrun fail"() {
        given:
        def uri = mockWebServer.url("/").uri();

        when:
        mockWebServer.enqueue(new MockResponse().setResponseCode(504))
        mockWebServer.enqueue(new MockResponse().setResponseCode(504))

        def result = kakaoAddressSearchService.requestAddressSearch(inputAddress);

        then:
        2 * kakaoUriBuilderService.buildUriByAddress(inputAddress) >> uri
        result == null
    }
}
