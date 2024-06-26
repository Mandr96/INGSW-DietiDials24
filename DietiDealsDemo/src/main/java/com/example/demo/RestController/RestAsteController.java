package com.example.demo.RestController;

import com.example.demo.data_access.AsteRepository;
import com.example.demo.data_access.NotificheRepository;
import com.example.demo.data_access.OfferteRepository;
import com.example.demo.data_access.UtentiRepository;
import com.example.demo.model.Asta;
import com.example.demo.model.Notifica;
import com.example.demo.model.Offerta;
import com.example.demo.model.Utente;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class RestAsteController {
    private final AsteRepository asteRep;
    private final OfferteRepository offerteRep;
    private final NotificheRepository notificheRep;
    private final UtentiRepository userRep;

    @Autowired
    public RestAsteController(AsteRepository asteRep, OfferteRepository offerteRep, NotificheRepository notRep, UtentiRepository userRep) {
        this.asteRep = asteRep;
        this.offerteRep = offerteRep;
        this.notificheRep = notRep;
        this.userRep = userRep;
    }

    @GetMapping(value = "asta/get/{id}")
    public Asta getAsta(@PathVariable("id") Long id) {
        checkIfScadutaAndSave(id);
        Optional<Asta> result = asteRep.findById(id);
        return result.orElse(null);
    }

    @GetMapping(value = "offerta/byUser/{email}")
    public List<Offerta> getOfferteByUser(@PathVariable("email") String email) {
        System.out.println("getOfferteByuser request received. Argument: "+email);
        List<Offerta> list = offerteRep.findByUser(email);
        System.out.println("retrieved: "+list);
        return list;
    }

    @GetMapping(value = "offerta/byAsta/{id}")
    public List<Offerta> getOfferteByAsta(@PathVariable("id") Long astaID) { return offerteRep.findByAsta(astaID); }

    @GetMapping(value = "asta/cerca/{tipo}/{categoria}/{keyword}/{pag}")
    public List<Asta> cercaAste(@PathVariable("tipo") String tipo,
                                @PathVariable("categoria") String categoria,
                                @PathVariable("keyword") String kw,
                                @PathVariable("pag") int pag) {
        System.out.println(tipo+categoria+kw+pag);
        String cat = categoria;
        if(tipo.toLowerCase().contains("tutte")) tipo = "%";
        if(categoria.toLowerCase().contains("tutte")) cat = "%";
        int offset = pag*10;
        return CheckAsteScaduteAndRm(asteRep.searchAste(tipo, cat, kw, offset));
    }

    private List<Asta> CheckAsteScaduteAndRm(List<Asta> aste) {
        aste.removeIf(asta -> checkIfScadutaAndSave(asta.getId()));
        return aste;
    }

    public Boolean checkIfScadutaAndSave(@PathVariable("id")Long astaID) {
        if(asteRep.findById(astaID).isPresent()){
            Asta asta = asteRep.findById(astaID).get();
            if(asta.getScadenza().before(Timestamp.from(Instant.now()))) {
                asteRep.setAstaScaduta(astaID);
                List<Notifica> notifiche = asta.chiudi();
                notificheRep.saveAll(notifiche);
                return true;
            }
        }
        return false;
    }

    @PostMapping(path = "asta/nuova",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Long creaAsta(@RequestBody String body) throws JsonProcessingException, JSONException {
        Asta newAsta = new ObjectMapper().readValue(body, Asta.class);
        newAsta.setCreatore(userRep.findById(new JSONObject(body).getString("creatore")).get());
        asteRep.save(newAsta);
        return newAsta.getId();
    }

    @PostMapping(path = "offerta/nuova",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Long insertOfferta(@RequestBody String body) throws JsonProcessingException, JSONException {
        System.out.println("Richiesta insertOfferta ricevuta");
        Asta asta = asteRep.findById(new JSONObject(body).getLong("asta")).get();
        if (checkIfScadutaAndSave(asta.getId())) {
            return -1L;
        }
        Offerta newOffer = new ObjectMapper().readValue(body, Offerta.class);
        newOffer.setAsta(asta);
        newOffer.setOwner(userRep.findById(new JSONObject(body).getString("owner")).get());
        newOffer.setData(Timestamp.from(Instant.now().truncatedTo(ChronoUnit.MINUTES)));
        offerteRep.save(newOffer);
        return newOffer.getId();
    }

    @GetMapping(path = "offerta/accettazione/{id}")
    public boolean accettaOfferta(@PathVariable("id")Long offertaId){
        Offerta offer = offerteRep.findById(offertaId).get();
        Asta asta = offer.getAsta();
        Utente user = offer.getOwner();
        notificheRep.save(new Notifica("Offerta accettata!", "La tua offerta del valore di "+offer.getValoreAsString()+
                " è stata accettata. Ti sei aggiudicato "+asta.getNomeProdotto(), false, user));
        return true;
    }

    @GetMapping(path = "asta/partecipate/{email}")
    public List<Asta> getAstePartecipate(@PathVariable("email") String email) {
        List<Asta> result = new ArrayList<>();
        List<Offerta> userOffers = offerteRep.findByUser(email);
        for (Offerta offerta : userOffers) {
            System.out.println("offerta" + offerta + " id " + offerta);
            result.add(asteRep.getAstaByOffer(offerta.getId()));
        }
        System.out.println("ret" + result );
        return CheckAsteScaduteAndRm(result);
    }

    @PostMapping("asta/setImg/{astaID}")
    public void setAstaImg(@PathVariable("astaID") Long astaID, @RequestParam("file") MultipartFile file) {
        Optional<Asta> result = asteRep.findById(astaID);
        Asta asta = result.orElse(null);
        asta.setImg((File) file);
        asteRep.save(asta);
    }

    @GetMapping(value = "asta/getcreatore/{id}")
    public String getCreatoreAsta(@PathVariable("id") Long id) {
        checkIfScadutaAndSave(id);
        return asteRep.findById(id).get().getCreatore().getEmail();
    }
}
