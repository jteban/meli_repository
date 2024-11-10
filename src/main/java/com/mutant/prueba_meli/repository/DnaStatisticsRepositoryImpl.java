package com.mutant.prueba_meli.repository;

import com.mutant.prueba_meli.entity.DnaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DnaStatisticsRepositoryImpl implements DnaStatisticsRepository{
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<DnaEntity> findAllStatistics() {
        Query query = entityManager.createQuery("SELECT d FROM DnaEntity d");
        return query.getResultList();
    }
}
