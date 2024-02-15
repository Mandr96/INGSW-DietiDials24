package com.main.dietidealsclient.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Offerta {

    private Long id;
    private Float valore;
    private Timestamp data;
    private Boolean attiva;
    @JsonIgnore
    private Utente owner;
    @JsonIgnore
    private Asta asta;
    @JsonCreator
    public Offerta(@JsonProperty("id") Long id,
                   @JsonProperty("valore") Float valore,
                   @JsonProperty("data") Timestamp data,
                   @JsonProperty("attiva") Boolean attiva) {
        this.id = id;
        this.valore = valore;
        this.data = data;
        this.attiva = attiva;
    }

    public Float getValore() {
        return valore;
    }

    public Utente getOwner() {
        return owner;
    }
}
