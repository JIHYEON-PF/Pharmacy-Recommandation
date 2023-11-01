package com.openapi.pharmacyrecommandation.pharmacy.repository;

import com.openapi.pharmacyrecommandation.pharmacy.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {

}
