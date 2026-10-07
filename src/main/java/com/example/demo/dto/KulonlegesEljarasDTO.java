package com.example.demo.dto;

public class KulonlegesEljarasDTO {

    private String eljaras;
    private String eredmeny;
    private int szam;

    public KulonlegesEljarasDTO(String eljaras, String eredmeny, int szam) {
        this.eljaras = eljaras;
        this.eredmeny = eredmeny;
        this.szam = szam;
    }

    public String getEljaras() {
        return eljaras;
    }

    public void setEljaras(String eljaras) {
        this.eljaras = eljaras;
    }

    public String getEredmeny() {
        return eredmeny;
    }

    public void setEredmeny(String eredmeny) {
        this.eredmeny = eredmeny;
    }

    public int getSzam() {
        return szam;
    }

    public void setSzam(int szam) {
        this.szam = szam;
    }
}