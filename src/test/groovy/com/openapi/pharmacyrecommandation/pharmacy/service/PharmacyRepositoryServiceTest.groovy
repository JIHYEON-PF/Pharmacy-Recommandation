package com.openapi.pharmacyrecommandation.pharmacy.service

import com.openapi.pharmacyrecommandation.AbstractIntegrationContainerBaseTest
import com.openapi.pharmacyrecommandation.pharmacy.entity.Pharmacy
import com.openapi.pharmacyrecommandation.pharmacy.repository.PharmacyRepository
import org.springframework.beans.factory.annotation.Autowired

class PharmacyRepositoryServiceTest extends AbstractIntegrationContainerBaseTest {
    @Autowired
    private PharmacyRepositoryService pharmacyRepositoryService;
    @Autowired
    private PharmacyRepository pharmacyRepository;

    def setup() {
        pharmacyRepository.deleteAll();
    }

    def "PharmacyRepositoryService updateAddress with dirty checkout - success"() {
        given:
        String inputAddress = "서울 특별시 성북구 종암동";
        String modifiedAddress = "서울 광진구 구의동";
        String name = "은혜약국";

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(inputAddress)
                .pharmacyName(name)
                .build();

        when:
        def entity = pharmacyRepository.save(pharmacy);

        pharmacyRepositoryService.updateAddress(entity.getId(), modifiedAddress);

        def result = pharmacyRepository.findById(entity.getId());

        then:
        result.get().getPharmacyAddress() == modifiedAddress;
    }

    def "PharmacyRepositoryService updateAddress with dirty checkout - fail"() {
        given:
        String inputAddress = "서울 특별시 성북구 종암동";
        String modifiedAddress = "서울 광진구 구의동";
        String name = "은혜약국";

        def pharmacy = Pharmacy.builder()
                .pharmacyAddress(inputAddress)
                .pharmacyName(name)
                .build();

        when:
        def entity = pharmacyRepository.save(pharmacy);

        pharmacyRepositoryService.updateAddressWithoutTransaction(entity.getId(), modifiedAddress);

        def result = pharmacyRepository.findById(entity.getId());

        then:
        result.get().getPharmacyAddress() == inputAddress;
    }
}
