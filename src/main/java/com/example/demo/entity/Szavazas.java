package com.example.demo.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Szavazas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String szavazasId;

    @Column(unique = true, nullable = false)
    private Instant idopont;

    private String targy;

    private String tipus;

    private String eljaras;

    private String elnok;

    @OneToMany(mappedBy = "szavazas", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Szavazat> szavazatok = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public String getSzavazasId() {
        return szavazasId;
    }

    public void setSzavazasId(String szavazasId) {
        this.szavazasId = szavazasId;
    }

    public Instant getIdopont() {
        return idopont;
    }

    public void setIdopont(Instant idopont) {
        this.idopont = idopont;
    }

    public String getTargy() {
        return targy;
    }

    public void setTargy(String targy) {
        this.targy = targy;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public String getEljaras() {
        return eljaras;
    }

    public void setEljaras(String eljaras) {
        this.eljaras = eljaras;
    }

    public String getElnok() {
        return elnok;
    }

    public void setElnok(String elnok) {
        this.elnok = elnok;
    }

    public List<Szavazat> getSzavazatok() {
        return szavazatok;
    }

    public void setSzavazatok(List<Szavazat> szavazatok) {
        this.szavazatok = szavazatok;
    }
}