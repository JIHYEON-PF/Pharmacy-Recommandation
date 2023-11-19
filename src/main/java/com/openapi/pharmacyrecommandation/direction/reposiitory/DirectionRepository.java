package com.openapi.pharmacyrecommandation.direction.reposiitory;

import com.openapi.pharmacyrecommandation.direction.entity.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectionRepository extends JpaRepository<Direction, Long> {
}
