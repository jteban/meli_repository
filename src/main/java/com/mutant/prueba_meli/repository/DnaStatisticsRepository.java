package com.mutant.prueba_meli.repository;

import com.mutant.prueba_meli.entity.DnaEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DnaStatisticsRepository {
    List<DnaEntity> findAllStatistics();
}
