package com.example.demo.dto;

public class EredmenyResponse {

    private String eredmeny;
    private int kepviselok;
    private int igenek;
    private int nemek;
    private int tartozkodasok;
    public String getEredmeny() {
        return eredmeny;
    }
    public void setEredmeny(String eredmeny) {
        this.eredmeny = eredmeny;
    }
    public int getKepviselok() {
        return kepviselok;
    }
    public void setKepviselok(int kepviselok) {
        this.kepviselok = kepviselok;
    }
    public int getIgenek() {
        return igenek;
    }
    public void setIgenek(int igenek) {
        this.igenek = igenek;
    }
    public int getNemek() {
        return nemek;
    }
    public void setNemek(int nemek) {
        this.nemek = nemek;
    }
    public int getTartozkodasok() {
        return tartozkodasok;
    }
    public void setTartozkodasok(int tartozkodasok) {
        this.tartozkodasok = tartozkodasok;
    }

    public EredmenyResponse(String eredmeny, int kepviselok, int igenek, int nemek, int tartozkodasok) {
        this.eredmeny = eredmeny;
        this.kepviselok = kepviselok;
        this.igenek = igenek;
        this.nemek = nemek;
        this.tartozkodasok = tartozkodasok;
    }

    



}