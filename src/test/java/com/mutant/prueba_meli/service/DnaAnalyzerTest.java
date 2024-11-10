package com.mutant.prueba_meli.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class DnaAnalyzerTest {
    private DnaAnalyzer dnaAnalyzer;
    private Logger logger;

    @BeforeEach
    void setUp() {
        dnaAnalyzer = new DnaAnalyzer();
        logger = LoggerFactory.getLogger(DnaAnalyzer.class);
    }

    @Test
    void testIsMutantWithMutantDNA() {
        String[] mutantDna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        assertTrue(dnaAnalyzer.isMutant(mutantDna), "The DNA sequence should be classified as mutant.");
    }

    @Test
    void testIsMutantWithNonMutantDNA() {
        String[] nonMutantDna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
        assertFalse(dnaAnalyzer.isMutant(nonMutantDna), "The DNA sequence should not be classified as mutant.");
    }

    @Test
    void testIsMutantWithEmptyDNA() {
        String[] emptyDna = {};
        Executable executable = () -> dnaAnalyzer.isMutant(emptyDna);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("The sequence DNA can't be empty", exception.getMessage());
    }

    @Test
    void testIsMutantWithNonSquareDNA() {
        String[] nonSquareDna = {"ATGC", "CAGT", "TTAT", "AG"};
        Executable executable = () -> dnaAnalyzer.isMutant(nonSquareDna);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("The matrix isn't square", exception.getMessage());
    }

    @Test
    void testIsMutantWithInvalidCharacters() {
        String[] invalidDna = {"ATGCGA", "CAGTGC", "TTATGT", "AGBXGG", "CCCCTA", "TCACTG"};
        Executable executable = () -> dnaAnalyzer.isMutant(invalidDna);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("The matrix contains invalid characters, must be this letters: ATCG.", exception.getMessage());
    }

    @Test
    void testCountSequences() {
        String[] dna = {"AAAA", "CCCC", "GGGG", "TTTT"};
        int count = dnaAnalyzer.countSequences(dna);
        assertEquals(4, count, "There should be 4 horizontal matches.");
    }

    @Test
    void testTranspose() {
        String[] dna = {"ATGC", "CAGT", "TTAT", "AGCC"};
        String[] transposed = dnaAnalyzer.transpose(dna);
        assertArrayEquals(new String[]{"ACTA", "TATG", "GGAC", "CTTC"}, transposed, "The transposed matrix is incorrect.");
    }

    @Test
    void testReverseRows() {
        String[] dna = {"ATCG", "GCTA", "TTAA", "CGGC"};
        String[] reversed = dnaAnalyzer.reverseRows(dna);
        assertArrayEquals(new String[]{"GCTA", "ATCG", "AATT", "CGGC"}, reversed, "The rows were not reversed correctly.");
    }
}
