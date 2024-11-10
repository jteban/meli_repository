package com.mutant.prueba_meli.service;

import com.mutant.prueba_meli.entity.DnaEntity;
import com.mutant.prueba_meli.repository.DnaStatisticsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class DnaStatisticsTest {

    @Mock
    private DnaStatisticsRepository dnaStatisticsRepository;

    @InjectMocks
    private DnaStatistics dnaStatistics;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetStatisticsWithMutantsAndHumans() {
        DnaEntity mutantDna1 = new DnaEntity();
        mutantDna1.setMutant(true);
        DnaEntity mutantDna2 = new DnaEntity();
        mutantDna2.setMutant(true);
        DnaEntity humanDna = new DnaEntity();
        humanDna.setMutant(false);

        List<DnaEntity> allStatistics = Arrays.asList(mutantDna1, mutantDna2, humanDna);

        when(dnaStatisticsRepository.findAllStatistics()).thenReturn(allStatistics);

        Map<String, Object> stats = dnaStatistics.getStatistics();

        assertEquals(2L, stats.get("count_mutant_dna"));
        assertEquals(1L, stats.get("count_human_dna"));
        assertEquals(2.0, stats.get("ratio"));
    }

    @Test
    void testGetStatisticsWithOnlyHumans() {
        DnaEntity humanDna1 = new DnaEntity();
        humanDna1.setMutant(false);
        DnaEntity humanDna2 = new DnaEntity();
        humanDna2.setMutant(false);

        List<DnaEntity> allStatistics = Arrays.asList(humanDna1, humanDna2);

        when(dnaStatisticsRepository.findAllStatistics()).thenReturn(allStatistics);

        Map<String, Object> stats = dnaStatistics.getStatistics();

        assertEquals(0L, stats.get("count_mutant_dna"));
        assertEquals(2L, stats.get("count_human_dna"));
        assertEquals(0.0, stats.get("ratio"));
    }

    @Test
    void testGetStatisticsWithNoEntries() {
        List<DnaEntity> allStatistics = Arrays.asList();

        when(dnaStatisticsRepository.findAllStatistics()).thenReturn(allStatistics);

        Map<String, Object> stats = dnaStatistics.getStatistics();

        assertEquals(0L, stats.get("count_mutant_dna"));
        assertEquals(0L, stats.get("count_human_dna"));
        assertEquals(0.0, stats.get("ratio"));
    }
}
