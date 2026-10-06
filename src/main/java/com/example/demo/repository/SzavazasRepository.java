package com.example.demo.repository;

import com.example.demo.entity.Szavazas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.List;

public interface SzavazasRepository extends JpaRepository<Szavazas, Long> {

    Optional<Szavazas> findBySzavazasId(String szavazasId);

    boolean existsByIdopont(Instant idopont);

    Optional<Szavazas> findFirstByTipusAndIdopontBeforeOrderByIdopontDesc(
        String tipus,
        Instant idopont
    );

    List<Szavazas> findByIdopontGreaterThanEqualAndIdopontLessThan(
        Instant kezdet,
        Instant veg
    );

}