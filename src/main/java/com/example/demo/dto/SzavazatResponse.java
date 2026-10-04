package com.example.demo.dto;

public class SzavazatResponse {
    
    private String szavazat;

    public SzavazatResponse(String szavazat) {
        this.szavazat = szavazat;
    }

    public String getSzavazat() {
        return szavazat;
    }

    public void setSzavazat(String szavazat) {
        this.szavazat = szavazat;
    }


}
