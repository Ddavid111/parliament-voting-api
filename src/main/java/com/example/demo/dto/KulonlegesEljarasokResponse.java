package com.example.demo.dto;

import java.util.List;

public class KulonlegesEljarasokResponse {

    private List<KulonlegesEljarasDTO> szavazasok;

    public List<KulonlegesEljarasDTO> getSzavazasok() {
        return szavazasok;
    }

    public void setSzavazasok(List<KulonlegesEljarasDTO> szavazasok) {
        this.szavazasok = szavazasok;
    }
}