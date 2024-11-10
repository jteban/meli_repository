package com.mutant.prueba_meli.controller;

import com.mutant.prueba_meli.service.DnaService;
import com.mutant.prueba_meli.service.DnaStatistics;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/meli")
public class DnaController {
    private final DnaService dnaService;
    private final DnaStatistics dnaStatistics;

    public DnaController(DnaService dnaService, DnaStatistics dnaStatistics) {
        this.dnaService = dnaService;
        this.dnaStatistics = dnaStatistics;
    }

    @PostMapping("/mutant/")
    public ResponseEntity<Void> isMutant(@RequestBody Map<String, String[]> request){
        String[] dna = request.get("dna");
        boolean isMutant = dnaService.verifyAndSaveDna(dna);
        if(isMutant){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats(){
        Map<String, Object> stats = dnaStatistics.getStatistics();
        return ResponseEntity.ok(stats);
    }
}
