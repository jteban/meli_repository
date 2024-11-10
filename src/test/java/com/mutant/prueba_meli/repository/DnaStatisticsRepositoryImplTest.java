package com.mutant.prueba_meli.repository;

import com.mutant.prueba_meli.entity.DnaEntity;
import com.mutant.prueba_meli.repository.DnaStatisticsRepositoryImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DnaStatisticsRepositoryImplTest {

    @InjectMocks
    private DnaStatisticsRepositoryImpl dnaStatisticsRepository;

    @Mock
    private EntityManager entityManager;

    @Mock
    private Query query;

    @Test
    public void testFindAllStatistics_WithData() {
        List<DnaEntity> expectedDnaEntities = new ArrayList<>();
        expectedDnaEntities.add(new DnaEntity());
        expectedDnaEntities.add(new DnaEntity());

        when(entityManager.createQuery("SELECT d FROM DnaEntity d")).thenReturn(query);
        when(query.getResultList()).thenReturn(expectedDnaEntities);

        List<DnaEntity> actualDnaEntities = dnaStatisticsRepository.findAllStatistics();

        assertEquals(expectedDnaEntities, actualDnaEntities);
    }

    @Test
    public void testFindAllStatistics_EmptyDatabase() {
        when(entityManager.createQuery("SELECT d FROM DnaEntity d")).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList<>());

        List<DnaEntity> actualDnaEntities = dnaStatisticsRepository.findAllStatistics();

        assertTrue(actualDnaEntities.isEmpty());
    }
}