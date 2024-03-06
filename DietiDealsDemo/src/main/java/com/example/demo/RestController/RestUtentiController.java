package com.example.demo.RestController;

import com.example.demo.authService.auth.ChangePasswordRequest;
import com.example.demo.authService.auth.RegisterRequest;
import com.example.demo.authService.auth.UserServices;
import com.example.demo.authService.token.Token;
import com.example.demo.authService.token.TokenRepository;
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
    private final UserServices userServices;

    @Autowired
    public RestUtentiController(UtentiRepository repository, UserServices userServices, TokenRepository tokenRepository) {
        this.userRep = repository;
        this.userServices = userServices;
    }

    @GetMapping(value = "/get/{email}")
    public Utente getUser(@PathVariable("email") String email) {
        System.out.println("Richiesta getUserByEmail("+email+") ricevuta");
        Optional<Utente> result = userRep.findById(email);
        return result.orElse(null);
    }

    @GetMapping(value = "asta/getOwner/{astaID}")
    public Utente getAstaOwner(@PathVariable("astaID") Long astaID) {
        System.out.println("Richiesta getAstaOwner("+astaID+") ricevuta");
        return userRep.getAstaOwner(astaID);
    }

    @GetMapping(value = "offerta/getOwner/{offerID}")
    public Utente getOffertaOwner(@PathVariable("offerID") Long offerID) {
        System.out.println("Richiesta getOffertaOwner("+offerID+") ricevuta");
        return userRep.getOfferOwner(offerID);
    }

    @PostMapping(path = "/update")
    public boolean updateUser(@RequestBody Utente requestBody){
        Optional<Utente> result = userRep.findById(requestBody.getEmail());
        if(result.isPresent()){
            Utente user = result.get();
            user.setCity(requestBody.getCity());
            user.setNome(requestBody.getNome());
            user.setCognome(requestBody.getCognome());
            user.setProfilePic(requestBody.getProfilePic());
            user.setShortbio(requestBody.getShortbio());
            userRep.save(user);
            return true;
        }
        return false;
    }

    @PostMapping(path = "/newpassword")
    public boolean setNewPassword(@RequestBody ChangePasswordRequest request, @RequestHeader(name = "Authorization") String authHeader){
        return userServices.changePassword(request,authHeader.replace("Bearer " , ""));

    }

    @PostMapping(path = "/create",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void creaUtente(@RequestBody Utente newUser) {
        userRep.save(newUser);
    }

}
