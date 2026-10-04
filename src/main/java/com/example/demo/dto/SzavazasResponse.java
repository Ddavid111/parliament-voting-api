package com.example.demo.dto;

public class SzavazasResponse {

    private String szavazasId;

    public SzavazasResponse(String szavazasId) {
        this.szavazasId = szavazasId;
    }

    public String getSzavazasId() {
        return szavazasId;
    }

    public void setSzavazasId(String szavazasId) {
        this.szavazasId = szavazasId;
    }
}