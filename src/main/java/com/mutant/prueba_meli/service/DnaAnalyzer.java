package com.mutant.prueba_meli.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DnaAnalyzer implements DnaAnalyzerStrategy {
    @Override
    public boolean isMutant(String[] dna) {
        log.info("Analyzing possible mutant with the dna's sequence '{}'",dna);
        int dnaSize = dna.length;
        int count = 0;

        if(dnaSize == 0){
            throw new IllegalArgumentException("The sequence DNA can't be empty");
        }

        for (String row : dna) {
            if (row.length() != dnaSize) {
                log.warn("The matrix isn't square: '{}'", dna.length);
                throw new IllegalArgumentException("The matrix isn't square");
            }
            if (!row.matches("[ATCG]+")) {
                log.warn("The matrix contains invalid characters, must be this letters: ATCG");
                throw new IllegalArgumentException("The matrix contains invalid characters, must be this letters: ATCG.");
            }
        }

        log.info("Searching sequences' match");
        count += countSequences(dna);
        count += countSequences(transpose(dna));
        count += countDiagonalSequences(dna);
        count += countDiagonalSequences(reverseRows(dna));

        log.info("Total sequence matches: {}", count);
        return count > 1;
    }

    protected int countSequences(String[] dna) {
        log.info("Searching horizontal sequences' match");
        int count = 0;
        for (String row : dna) {
            for (int i = 0; i <= row.length() - 4; i++) {
                if (row.charAt(i) == row.charAt(i + 1) &&
                        row.charAt(i) == row.charAt(i + 2) &&
                        row.charAt(i) == row.charAt(i + 3)) {
                    count++;
                }
            }
        }
        log.info("Total horizontal sequences' match: '{}'",count);
        return count;
    }

    protected String[] transpose(String[] dna) {
        log.info("Transposing sequences' match");
        int n = dna.length;
        String[] transposed = new String[n];
        for (int i = 0; i < n; i++) {
            StringBuilder sb = new StringBuilder();
            for (String row : dna) {
                sb.append(row.charAt(i));
            }
            transposed[i] = sb.toString();
        }
        log.info("Transposed: '{}'",transposed);
        return transposed;
    }

    protected int countDiagonalSequences(String[] dna) {
        log.info("Searching Diagonal sequences' match");
        int count = 0;
        int n = dna.length;

        log.info("Searching main Diagonals sequences");
        for (int i = 0; i <= n - 4; i++) {
            for (int j = 0; j <= n - 4; j++) {
                if (dna[i].charAt(j) == dna[i+1].charAt(j+1) &&
                        dna[i].charAt(j) == dna[i+2].charAt(j+2) &&
                        dna[i].charAt(j) == dna[i+3].charAt(j+3)) {
                    count++;
                }
            }
        }
        log.info("Searching seconds Diagonals sequences - from top to right and bottom to left");
        for (int i = 0; i <= n - 4; i++) {
            for (int j = 3; j < n; j++) {
                if (dna[i].charAt(j) == dna[i+1].charAt(j-1) &&
                        dna[i].charAt(j) == dna[i+2].charAt(j-2) &&
                        dna[i].charAt(j) == dna[i+3].charAt(j-3)) {
                    count++;
                }
            }
        }

        log.info("Total diagonal sequences' match: '{}'",count);
        return count;
    }

    protected String[] reverseRows(String[] dna) {
        log.info("Reversing rows");
        String[] reversed = new String[dna.length];
        for (int i = 0; i < dna.length; i++) {
            reversed[i] = new StringBuilder(dna[i]).reverse().toString();
        }
        log.info("Total reversed sequences' match: '{}'",reversed);
        return reversed;
    }
}
