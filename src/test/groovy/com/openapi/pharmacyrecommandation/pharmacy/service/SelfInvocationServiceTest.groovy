package com.openapi.pharmacyrecommandation.pharmacy.service

import com.openapi.pharmacyrecommandation.AbstractIntegrationContainerBaseTest
import com.openapi.pharmacyrecommandation.pharmacy.entity.Pharmacy
import com.openapi.pharmacyrecommandation.pharmacy.repository.PharmacyRepository
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

class SelfInvocationServiceTest extends AbstractIntegrationContainerBaseTest {
    @Autowired
    private SelfInvocationService selfInvocationService;
    @Autowired
    private PharmacyRepository pharmacyRepository;

    def setup() {
        pharmacyRepository.deleteAll();
    }

    def "self invocation"() {
        given:
        String address = "서울 특별시 성북구 종암동";
        String name = "은혜약국";
        double latitude = 36.11;
        double longitude = 128.11;

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(address)
                .pharmacyName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build();

        when:
        selfInvocationService.bar(Arrays.asList(pharmacy));

        then:
        def exception = thrown(RuntimeException.class);
        def result = pharmacyRepository.findAll();
        result.size() == 0;
    }
}
