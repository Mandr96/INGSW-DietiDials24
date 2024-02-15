package com.main.dietidealsclient.Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notifica {

    private Long id;
    private String oggetto;
    private String testo;
    private Boolean letto;

    @JsonIgnore
    private Utente user;

    public Notifica(String oggetto, String testo, Boolean letto, Utente utente) {
        this.oggetto = oggetto;
        this.testo = testo;
        this.letto = letto;
        this.user = utente;
    }
}
