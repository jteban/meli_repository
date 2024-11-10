package com.mutant.prueba_meli.service;

import com.mutant.prueba_meli.entity.DnaEntity;
import com.mutant.prueba_meli.repository.DnaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@Slf4j
public class DnaService {
    private final DnaRepository dnaRepository;
    private final DnaAnalyzerStrategy dnaAnalyzerStrategy;

    public DnaService(DnaRepository dnaRepository, DnaAnalyzerStrategy dnaAnalyzerStrategy) {
        this.dnaRepository = dnaRepository;
        this.dnaAnalyzerStrategy = dnaAnalyzerStrategy;
    }

    public boolean verifyAndSaveDna(String[] dna){
        log.info("Starting with verify and save DNA");
        String dnaSeq = String.join(",",dna);
        Optional<DnaEntity> existingRecord = dnaRepository.findByDnaSeq(dnaSeq);

        if(existingRecord.isPresent()){
            log.info("There is already mutant with the same dna: '{}'",dna);
            return existingRecord.get().isMutant();
        }

        boolean isMutant = dnaAnalyzerStrategy.isMutant(dna);
        DnaEntity dnaEntity = new DnaEntity(dnaSeq, isMutant);

        if(isMutant){
            log.info("The dna is a mutant");
            dnaEntity.setMutantCount(dnaEntity.getMutantCount() + 1);
        }else {
            log.info("The dna is a human");
            dnaEntity.setHumanCount(dnaEntity.getHumanCount() + 1);
        }
        log.info("Saving dna");
        dnaRepository.save(dnaEntity);
        return isMutant;
    }
}
