package com.mutant.prueba_meli.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DnaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String dnaSeq;

    @Column(nullable = false)
    private boolean isMutant;

    private Long mutantCount;
    private Long humanCount;

    public DnaEntity() {}


    public DnaEntity(String dnaSeq, boolean isMutant) {
        this.dnaSeq = dnaSeq;
        this.isMutant = isMutant;
        this.mutantCount = 0L;
        this.humanCount = 0L;
    }
}
