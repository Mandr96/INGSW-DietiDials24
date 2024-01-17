package com.example.demo.RestController;

import com.example.demo.data_access.UtentiRepository;
import com.example.demo.model.Asta;
import com.example.demo.model.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "utente")
public class RestUtentiController {

    private final UtentiRepository userRep;

    @Autowired
    public RestUtentiController(UtentiRepository repository) {
        this.userRep = repository;
    }

    @GetMapping(value = "{email}")
    public Utente getUser(@PathVariable("email") String email) {
        Optional<Utente> result = userRep.findById(email);
        return result.orElse(null);
    }

    @PostMapping(path = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void creaUtente(@RequestBody Utente newUser) {
        userRep.save(newUser);
    }

}
