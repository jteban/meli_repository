package com.mutant.prueba_meli.repository;

import com.mutant.prueba_meli.entity.DnaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DnaRepository extends JpaRepository<DnaEntity, Long> {
    Optional<DnaEntity> findByDnaSeq(String dnaSeq);
}
