package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class Szavazat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String kepviselo;

    private String szavazat;

    @ManyToOne
    @JoinColumn(name = "szavazas_id", nullable = false)
    private Szavazas szavazas;

    public Long getId() {
        return id;
    }

    public String getKepviselo() {
        return kepviselo;
    }

    public void setKepviselo(String kepviselo) {
        this.kepviselo = kepviselo;
    }

    public String getSzavazat() {
        return szavazat;
    }

    public void setSzavazat(String szavazat) {
        this.szavazat = szavazat;
    }

    public Szavazas getSzavazas() {
        return szavazas;
    }

    public void setSzavazas(Szavazas szavazas) {
        this.szavazas = szavazas;
    }
}