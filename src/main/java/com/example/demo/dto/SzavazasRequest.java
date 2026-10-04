package com.example.demo.dto;

import java.time.Instant;
import java.util.List;

public class SzavazasRequest {

    private Instant idopont;
    private String targy;
    private String tipus;
    private String eljaras;
    private String elnok;
    private List<SzavazatRequest> szavazatok;

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

    public List<SzavazatRequest> getSzavazatok() {
        return szavazatok;
    }

    public void setSzavazatok(List<SzavazatRequest> szavazatok) {
        this.szavazatok = szavazatok;
    }
}