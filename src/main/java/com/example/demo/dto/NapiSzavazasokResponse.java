package com.example.demo.dto;

import java.util.List;

public class NapiSzavazasokResponse {

    private List<NapiSzavazasDTO> szavazasok;

    public List<NapiSzavazasDTO> getSzavazasok() {
        return szavazasok;
    }

    public void setSzavazasok(List<NapiSzavazasDTO> szavazasok) {
        this.szavazasok = szavazasok;
    }
    
}
