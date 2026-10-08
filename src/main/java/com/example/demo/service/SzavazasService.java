package com.example.demo.service;

import com.example.demo.dto.SzavazasRequest;
import com.example.demo.dto.SzavazasResponse;
import com.example.demo.dto.SzavazatRequest;
import com.example.demo.dto.SzavazatResponse;
import com.example.demo.dto.AtlagResponse;
import com.example.demo.dto.EredmenyResponse;
import com.example.demo.dto.KulonlegesEljarasokResponse;
import com.example.demo.dto.KulonlegesEljarasDTO;
import com.example.demo.dto.NapiSzavazasDTO;
import com.example.demo.dto.NapiSzavazasokResponse;
import com.example.demo.entity.Szavazas;
import com.example.demo.entity.Szavazat;
import com.example.demo.repository.SzavazasRepository;
import com.example.demo.repository.SzavazatRepository;
import com.example.demo.exception.ValidaciosException;
import com.example.demo.exception.NemtalalhatoException;

import org.springframework.stereotype.Service;

import java.util.UUID;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.time.Instant;

@Service
public class SzavazasService {

    private final SzavazasRepository szavazasRepository;

    private final SzavazatRepository szavazatRepository;

    public SzavazasService(SzavazasRepository szavazasRepository, SzavazatRepository szavazatRepository) {
        this.szavazasRepository = szavazasRepository;
        this.szavazatRepository = szavazatRepository;
    }

    public SzavazasResponse szavazasMentese(SzavazasRequest request) {

        validalas(request);

        Szavazas szavazas = new Szavazas();

        szavazas.setSzavazasId(generalSzavazasId());
        szavazas.setIdopont(request.getIdopont());
        szavazas.setTargy(request.getTargy());
        szavazas.setTipus(request.getTipus());
        szavazas.setEljaras(request.getEljaras());
        szavazas.setElnok(request.getElnok());

        for (SzavazatRequest szavazatRequest : request.getSzavazatok()) {

            Szavazat szavazat = new Szavazat();

            szavazat.setKepviselo(szavazatRequest.getKepviselo());
            szavazat.setSzavazat(szavazatRequest.getSzavazat());

            szavazat.setSzavazas(szavazas);

            szavazas.getSzavazatok().add(szavazat);
        }

        szavazasRepository.save(szavazas);

        return new SzavazasResponse(szavazas.getSzavazasId());
    }

    private String generalSzavazasId() {
        return UUID.randomUUID().toString();
    }

    public SzavazatResponse szavazatlekerese(String szavazasId, String kepviselo) 
    {

        Optional<Szavazat> szavazat = szavazatRepository.findBySzavazasSzavazasIdAndKepviselo(szavazasId, kepviselo);

        if (szavazat.isPresent()) {
            return new SzavazatResponse(szavazat.get().getSzavazat());
        }else {
            
            throw new NemtalalhatoException(
                    "A megadott szavazás azonosítóval és képviselővel nem található szavazat."
            );
        }

    }

    public EredmenyResponse eredmenylekerese(String szavazasId) {

        Optional<Szavazas> szavazas = szavazasRepository.findBySzavazasId(szavazasId);

        if (szavazas.isPresent()) {
            Szavazas megtalaltSzavazas = szavazas.get();

            String tipus = megtalaltSzavazas.getTipus();

            List<Szavazat> szavazatok = megtalaltSzavazas.getSzavazatok();

            int igen = szavazatok.stream().filter(szavazat -> szavazat.getSzavazat().equals("i")).toList().size();
            int nem = szavazatok.stream().filter(szavazat -> szavazat.getSzavazat().equals("n")).toList().size();
            int tartozkodas = szavazatok.stream().filter(szavazat -> szavazat.getSzavazat().equals("t")).toList().size();

            int kepviselokSzama = szavazatok.size();

            if(tipus.equals("j"))
            {
                return new EredmenyResponse("F",kepviselokSzama, igen, nem, tartozkodas);
            }
            else if(tipus.equals("e"))
            {
                Optional<Szavazas> jelenletiSzavazas = szavazasRepository.findFirstByTipusAndIdopontBeforeOrderByIdopontDesc("j", megtalaltSzavazas.getIdopont());

                if(!jelenletiSzavazas.isPresent()) {
                    throw new NemtalalhatoException(
                            "Nincs előző jelenléti szavazás az adott időpont előtt."
                    );
                }

                Szavazas jelenlet = jelenletiSzavazas.get();

                int kepviselokSzamaJelenlet = jelenlet.getSzavazatok().size();
                if(igen > kepviselokSzamaJelenlet / 2)
                {
                    return new EredmenyResponse("F",kepviselokSzamaJelenlet, igen, nem, tartozkodas);
                }
                else
                {
                    return new EredmenyResponse("U",kepviselokSzamaJelenlet, igen, nem, tartozkodas);
                }
            }
            else if(tipus.equals("m"))
            {
                if(igen > 100)
                {
                    return new EredmenyResponse("F",200, igen, nem, tartozkodas);
                }
                else
                {
                    return new EredmenyResponse("U",200, igen, nem, tartozkodas);
                }

            }
            else {
                throw new ValidaciosException(
                    "A szavazás típusa csak j, e vagy m lehet."
                );
            }

        } else {
            throw new NemtalalhatoException(
                    "A megadott szavazás azonosítóval nem található szavazás."
            );
        }
    }

    public NapiSzavazasokResponse napiSzavazasokLekerese(LocalDate nap) {

        Instant kezdet = nap.atStartOfDay(ZoneOffset.UTC).toInstant();
        Instant veg = nap.plusDays(1).atStartOfDay(ZoneOffset.UTC).toInstant();

        List<Szavazas> szavazasok = szavazasRepository.findByIdopontGreaterThanEqualAndIdopontLessThan(kezdet, veg);

        List<NapiSzavazasDTO> napiSzavazasok = new ArrayList<>();
        for (Szavazas szavazas : szavazasok) {
            NapiSzavazasDTO napiSzavazasDTO = new NapiSzavazasDTO();
            napiSzavazasDTO.setIdopont(szavazas.getIdopont());
            napiSzavazasDTO.setTargy(szavazas.getTargy());
            napiSzavazasDTO.setTipus(szavazas.getTipus());
            napiSzavazasDTO.setElnok(szavazas.getElnok());
            napiSzavazasDTO.setEljaras(szavazas.getEljaras());

            EredmenyResponse eredmeny = eredmenylekerese(szavazas.getSzavazasId());
        
            napiSzavazasDTO.setEredmeny(eredmeny.getEredmeny());
            napiSzavazasDTO.setKepviselokSzama(eredmeny.getKepviselokSzama());

            List<SzavazatRequest> szavazatDTOk = new ArrayList<>();

            for (Szavazat szavazat : szavazas.getSzavazatok()) {
                SzavazatRequest szavazatDTO = new SzavazatRequest();
                szavazatDTO.setKepviselo(szavazat.getKepviselo());
                szavazatDTO.setSzavazat(szavazat.getSzavazat());

                szavazatDTOk.add(szavazatDTO);
            }

            napiSzavazasDTO.setSzavazatok(szavazatDTOk);

            napiSzavazasok.add(napiSzavazasDTO);
        }
        NapiSzavazasokResponse response = new NapiSzavazasokResponse();
        response.setSzavazasok(napiSzavazasok);

        return response;
    }

    public AtlagResponse atlagszamitas(Instant kezdet, Instant veg) {

        List<Szavazas> szavazasok = szavazasRepository.findByIdopontGreaterThanEqualAndIdopontLessThan(kezdet, veg);
        
        int osszesReszvetel = 0;

        for (Szavazas szavazas : szavazasok) {
            if (!szavazas.getTipus().equals("j")) {
                int resztvevokSzama = szavazas.getSzavazatok().size();
                osszesReszvetel += resztvevokSzama;
            }
        }

        double atlag = (double) osszesReszvetel / 200;
        atlag = Math.round(atlag * 100.0) / 100.0;

        return new AtlagResponse(atlag);
    }

    public KulonlegesEljarasokResponse kulonlegesEljarasokSzama(Instant kezdet, Instant veg)
    {
        
        List<Szavazas> szavazasok = szavazasRepository.findByIdopontGreaterThanEqualAndIdopontLessThan(kezdet, veg);

        int surgosElfogadott = 0;
        int surgosElutasitott = 0;

        int kivetelesElfogadott = 0;
        int kivetelesElutasitott = 0;

        int elteroElfogadott = 0;
        int elteroElutasitott = 0;



        for(Szavazas szavazas : szavazasok)
        {
            if(szavazas.getEljaras().equals("s")||szavazas.getEljaras().equals("k")||szavazas.getEljaras().equals("e"))
            {
               EredmenyResponse eredmeny = eredmenylekerese(szavazas.getSzavazasId());
               if(szavazas.getEljaras().equals("s")){
                    if(eredmeny.getEredmeny().equals("F"))
                    {
                        surgosElfogadott++;
                    }
                    else
                    {
                        surgosElutasitott++;
                    }
               }
               else if(szavazas.getEljaras().equals("k"))
               {
                    if(eredmeny.getEredmeny().equals("F"))
                    {
                        kivetelesElfogadott++;
                    }
                    else
                    {
                        kivetelesElutasitott++;
                    }
               }
               else if(szavazas.getEljaras().equals("e"))
               {
                    if(eredmeny.getEredmeny().equals("F"))
                    {
                        elteroElfogadott++;
                    }
                    else
                    {
                        elteroElutasitott++;
                    }
               }
            }


        }

        List<KulonlegesEljarasDTO> kulonlegesEljarasok = new ArrayList<>();

        kulonlegesEljarasok.add(new KulonlegesEljarasDTO("s", "F", surgosElfogadott));
        kulonlegesEljarasok.add(new KulonlegesEljarasDTO("s", "U", surgosElutasitott));
        kulonlegesEljarasok.add(new KulonlegesEljarasDTO("k", "F", kivetelesElfogadott));
        kulonlegesEljarasok.add(new KulonlegesEljarasDTO("k", "U", kivetelesElutasitott));
        kulonlegesEljarasok.add(new KulonlegesEljarasDTO("e", "F", elteroElfogadott));
        kulonlegesEljarasok.add(new KulonlegesEljarasDTO("e", "U", elteroElutasitott));

        int osszesElfogadott = surgosElfogadott + kivetelesElfogadott + elteroElfogadott;

        int osszesElutasitott = surgosElutasitott + kivetelesElutasitott + elteroElutasitott;

        int osszesSzavazas = osszesElfogadott + osszesElutasitott;

        kulonlegesEljarasok.add(new KulonlegesEljarasDTO("összes", "F", osszesElfogadott));
        kulonlegesEljarasok.add(new KulonlegesEljarasDTO("összes", "U", osszesElutasitott));
        kulonlegesEljarasok.add(new KulonlegesEljarasDTO("összes", "összes", osszesSzavazas));
    
        KulonlegesEljarasokResponse response = new KulonlegesEljarasokResponse();
        response.setSzavazasok(kulonlegesEljarasok);

        return response;
    }

    private void validalas(SzavazasRequest request) {

    // 1. Kötelező adatok ellenőrzése
    if (request.getIdopont() == null) {
        throw new ValidaciosException("Az időpont megadása kötelező.");
    }

    if (request.getTargy() == null || request.getTargy().isBlank()) {
        throw new ValidaciosException("A tárgy megadása kötelező.");
    }

    if (request.getTipus() == null || request.getTipus().isBlank()) {
        throw new ValidaciosException("A típus megadása kötelező.");
    }

    if (request.getElnok() == null || request.getElnok().isBlank()) {
        throw new ValidaciosException("Az elnök megadása kötelező.");
    }

    if (request.getSzavazatok() == null || request.getSzavazatok().isEmpty()) {
        throw new ValidaciosException("Legalább egy szavazat megadása kötelező.");
    }

    // 2. Típus ellenőrzése
    if (!request.getTipus().matches("[jem]")) {
        throw new ValidaciosException(
                "A szavazás típusa csak j, e vagy m lehet."
        );
    }

    // 3. Eljárás ellenőrzése
    if (request.getEljaras() != null &&
            !request.getEljaras().matches("[nske]")) {

        throw new ValidaciosException(
                "Az eljárás csak n, s, k vagy e lehet."
        );
    }

    // 4. Szavazatok értékeinek ellenőrzése
    for (SzavazatRequest szavazat : request.getSzavazatok()) {

        if (szavazat.getKepviselo() == null ||
                szavazat.getKepviselo().isBlank()) {

            throw new ValidaciosException(
                    "A képviselő azonosítója nem lehet üres."
            );
        }

        if (szavazat.getSzavazat() == null ||
                !szavazat.getSzavazat().matches("[int]")) {

            throw new ValidaciosException(
                    "A szavazat csak i, n vagy t lehet."
            );
        }
    }

    // 5. Van-e már szavazás ezen az időponton?
    if (szavazasRepository.existsByIdopont(request.getIdopont())) {
        throw new ValidaciosException(
                "Erre az időpontra már létezik szavazás."
        );
    }

    // 6. Szavazott-e az elnök?
    boolean elnokSzavazott = request.getSzavazatok()
            .stream()
            .anyMatch(szavazat ->
                    szavazat.getKepviselo().equals(request.getElnok())
            );

    if (!elnokSzavazott) {
        throw new ValidaciosException(
                "Az elnöknek is szavaznia kell."
        );
    }

    // 7. Egy képviselő csak egyszer szavazhat
    long kulonbozoKepviselok = request.getSzavazatok()
            .stream()
            .map(SzavazatRequest::getKepviselo)
            .distinct()
            .count();

    if (kulonbozoKepviselok != request.getSzavazatok().size()) {
        throw new ValidaciosException(
                "Egy képviselő csak egyszer szavazhat."
        );
    }
}
}