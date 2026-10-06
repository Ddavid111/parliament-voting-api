package com.example.demo.controller;

import com.example.demo.dto.SzavazasRequest;
import com.example.demo.dto.SzavazasResponse;
import com.example.demo.dto.SzavazatResponse;
import com.example.demo.dto.EredmenyResponse;
import com.example.demo.dto.NapiSzavazasokResponse;
import com.example.demo.service.SzavazasService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/szavazasok")
public class SzavazasController {

    private final SzavazasService szavazasService;

    public SzavazasController(SzavazasService szavazasService) {
        this.szavazasService = szavazasService;
    }

    @PostMapping("/szavazas")
    public ResponseEntity<SzavazasResponse> szavazasMentese(
            @RequestBody SzavazasRequest request) {

        SzavazasResponse response =
                szavazasService.szavazasMentese(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping ("/szavazat")
    public ResponseEntity<SzavazatResponse> szavazatlekerese(
            @RequestParam("szavazas") String szavazasId,
            @RequestParam("kepviselo") String kepviselo) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(szavazasService.szavazatlekerese(szavazasId, kepviselo));
    }

    @GetMapping ("/eredmeny")
    public ResponseEntity<EredmenyResponse> eredmenylekerese(
            @RequestParam("szavazas") String szavazasId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(szavazasService.eredmenylekerese(szavazasId));
    }

    @GetMapping ("/napi-szavazasok")
    public ResponseEntity<NapiSzavazasokResponse> napiSzavazasokLekerese(
            @RequestParam("nap") LocalDate nap) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(szavazasService.napiSzavazasokLekerese(nap));
    }

}