package com.example.demo.repository;

import com.example.demo.entity.Szavazat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SzavazatRepository extends JpaRepository<Szavazat, Long> {

    Optional<Szavazat> findBySzavazasSzavazasIdAndKepviselo(
            String szavazasId,
            String kepviselo
    );
}