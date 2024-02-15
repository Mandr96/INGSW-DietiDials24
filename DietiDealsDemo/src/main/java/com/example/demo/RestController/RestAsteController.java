package com.example.demo.RestController;

import com.example.demo.data_access.AsteRepository;
import com.example.demo.data_access.NotificheRepository;
import com.example.demo.data_access.OfferteRepository;
import com.example.demo.data_access.UtentiRepository;
import com.example.demo.model.Asta;
import com.example.demo.model.Notifica;
import com.example.demo.model.Offerta;
import com.example.demo.model.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    public RestAsteController(AsteRepository asteRep, OfferteRepository offerteRep, NotificheRepository notRep) {
        this.asteRep = asteRep;
        this.offerteRep = offerteRep;
        this.notificheRep = notRep;
    }

    @GetMapping(value = "asta/get/{id}")
    public Asta getAsta(@PathVariable("id") Long id) {
        Optional<Asta> result = asteRep.findById(id);
        return result.orElse(null);
    }

    @GetMapping(value = "offerta/byUser/{email}")
    public List<Offerta> getOfferteByUser(@PathVariable("email") String email) {
        return offerteRep.findByUser(email);
    }

    @GetMapping(value = "offerta/byAsta/{id}")
    public List<Offerta> getOfferteByAsta(@PathVariable("id") Long astaID) {
        return offerteRep.findByAsta(astaID);
    }

    @GetMapping(value = "asta/cerca/{tipo}/{categoria}/{keyword}/{pag}")
    public List<Asta> cercaAste(@PathVariable("tipo") String tipo,
                                @PathVariable("categoria") String categoria,
                                @PathVariable("keyword") String kw,
                                @PathVariable("pag") int pag) {
        String cat = categoria;
        if(categoria.equalsIgnoreCase("tutte") || categoria.equalsIgnoreCase("any")) cat = "%";
        int offset = pag*10;
        return asteRep.searchAste(tipo, cat, kw, offset);
    }

    @GetMapping(value = "asta/checkScadenza/{id}")
    public Boolean checkScadenza(@PathVariable("id")Long astaID) {
        Asta asta = asteRep.findById(astaID).get();
        if(asta.getScadenza().before(Timestamp.from(Instant.now()))) {
            asteRep.setAstaScaduta(astaID);
            List<Notifica> notifiche = asta.chiudi();
            notificheRep.saveAll(notifiche);
            return true;
        }
        return false;
    }

    @PostMapping(path = "asta/nuova",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Long creaAsta(@RequestBody Asta newAsta) {
        asteRep.save(newAsta);
        return newAsta.getId();
    }

    @PostMapping(path = "offerta/nuova",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public Long insertOfferta(@RequestBody Offerta newOffer) {
        newOffer.setData(Timestamp.from(Instant.now().truncatedTo(ChronoUnit.MINUTES)));
        offerteRep.save(newOffer);
        return newOffer.getId();
    }
}
