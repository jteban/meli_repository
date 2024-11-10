package com.mutant.prueba_meli.service;

import com.mutant.prueba_meli.entity.DnaEntity;
import com.mutant.prueba_meli.repository.DnaStatisticsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class DnaStatistics {

    private final DnaStatisticsRepository dnaStatisticsRepository;

    public DnaStatistics(DnaStatisticsRepository dnaStatisticsRepository) {
        this.dnaStatisticsRepository = dnaStatisticsRepository;
    }

    public Map<String, Object> getStatistics() {
        log.info("Starting statistics for dnas");
        List<DnaEntity> allStatistics = dnaStatisticsRepository.findAllStatistics();

        long mutantCount = allStatistics.stream()
                .filter(DnaEntity::isMutant)
                .count();
        log.info("Total mutant: '{}'", mutantCount);
        long humanCount = allStatistics.size() - mutantCount;
        log.info("Total human: '{}'", humanCount);

        double ratio = humanCount > 0 ? (double) mutantCount / humanCount : 0.0;
        log.info("Total ratio: '{}'", ratio);

        Map<String, Object> stats = new HashMap<>();
        stats.put("count_mutant_dna", mutantCount);
        stats.put("count_human_dna", humanCount);
        stats.put("ratio", ratio);

        log.info("Total stats: '{}'", stats);
        return stats;
    }

}
