package com.main.dietidealsclient.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notifica implements Serializable {

    private Long id;
    private String oggetto;
    private String testo;
    private Boolean letto;

    @JsonIgnore
    private Utente user;

    @JsonCreator
    public Notifica(@JsonProperty("id") Long id,
                    @JsonProperty("oggetto") String oggetto,
                    @JsonProperty("testo") String testo,
                    @JsonProperty("letto") Boolean letto) {
        this.id = id;
        this.oggetto = oggetto;
        this.testo = testo;
        this.letto = letto;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOggetto(String oggetto) {
        this.oggetto = oggetto;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public void setLetto(Boolean letto) {
        this.letto = letto;
    }

    public void setUser(Utente user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getOggetto() {
        return oggetto;
    }

    public String getTesto() {
        return testo;
    }

    public Boolean getLetto() {
        return letto;
    }
}
