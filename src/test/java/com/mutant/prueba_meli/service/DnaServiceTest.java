package com.mutant.prueba_meli.service;

import com.mutant.prueba_meli.entity.DnaEntity;
import com.mutant.prueba_meli.repository.DnaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class DnaServiceTest {

    @Mock
    private DnaRepository dnaRepository;

    @Mock
    private DnaAnalyzerStrategy dnaAnalyzerStrategy;

    @InjectMocks
    private DnaService dnaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testVerifyAndSaveDnaWithExistingMutantRecord() {
        String[] dna = {"ATGC", "CAGT", "TTAT", "AGAC"};
        String dnaSeq = String.join(",", dna);

        DnaEntity existingEntity = new DnaEntity(dnaSeq, true);
        when(dnaRepository.findByDnaSeq(dnaSeq)).thenReturn(Optional.of(existingEntity));

        boolean result = dnaService.verifyAndSaveDna(dna);

        assertTrue(result, "Should return true for existing mutant record");
        verify(dnaRepository, never()).save(any(DnaEntity.class));
        verify(dnaAnalyzerStrategy, never()).isMutant(any());
    }

    @Test
    void testVerifyAndSaveDnaWithExistingHumanRecord() {
        String[] dna = {"ATGC", "CAGT", "TTAT", "AGAC"};
        String dnaSeq = String.join(",", dna);

        DnaEntity existingEntity = new DnaEntity(dnaSeq, false);
        when(dnaRepository.findByDnaSeq(dnaSeq)).thenReturn(Optional.of(existingEntity));

        boolean result = dnaService.verifyAndSaveDna(dna);

        assertFalse(result, "Should return false for existing human record");
        verify(dnaRepository, never()).save(any(DnaEntity.class));
        verify(dnaAnalyzerStrategy, never()).isMutant(any());
    }

    @Test
    void testVerifyAndSaveDnaWithNewMutantRecord() {
        String[] dna = {"ATGC", "CAGT", "TTTT", "AGAC"};
        String dnaSeq = String.join(",", dna);

        when(dnaRepository.findByDnaSeq(dnaSeq)).thenReturn(Optional.empty());
        when(dnaAnalyzerStrategy.isMutant(dna)).thenReturn(true);

        boolean result = dnaService.verifyAndSaveDna(dna);

        assertTrue(result, "Should return true for a new mutant DNA");
        verify(dnaRepository).save(any(DnaEntity.class));
        verify(dnaAnalyzerStrategy).isMutant(dna);
    }

    @Test
    void testVerifyAndSaveDnaWithNewHumanRecord() {
        String[] dna = {"ATGC", "CAGT", "TGAT", "AGAC"};
        String dnaSeq = String.join(",", dna);

        when(dnaRepository.findByDnaSeq(dnaSeq)).thenReturn(Optional.empty());
        when(dnaAnalyzerStrategy.isMutant(dna)).thenReturn(false);

        boolean result = dnaService.verifyAndSaveDna(dna);

        assertFalse(result, "Should return false for a new human DNA");
        verify(dnaRepository).save(any(DnaEntity.class));
        verify(dnaAnalyzerStrategy).isMutant(dna);
    }
}
