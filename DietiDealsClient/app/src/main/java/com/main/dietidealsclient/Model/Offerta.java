package com.main.dietidealsclient.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Offerta implements Serializable {

    private Long id;
    private Float valore;
    private Timestamp data;
    private Boolean attiva;
    private Utente owner;
    private Asta asta;
    @JsonCreator
    public Offerta(@JsonProperty("id") Long id,
                   @JsonProperty("valore") Float valore,
                   @JsonProperty("data") Timestamp data,
                   @JsonProperty("attiva") Boolean attiva,
                   @JsonProperty("owner") Utente owner,
                   @JsonProperty("asta") Asta asta) {
        this.id = id;
        this.valore = valore;
        this.data = data;
        this.attiva = attiva;
        this.owner = owner;
        this.asta = asta;
    }

    public Float getValore() {
        return valore;
    }

    public Utente getOwner() {
        return owner;
    }

    @JsonGetter("owner")
    public String getOwnerEmail() {
        return owner.getEmail();
    }

    @JsonGetter("asta")
    public Long getAstaID() {
        return asta.getId();
    }

    public Long getId() {
        return id;
    }

    public Timestamp getData() {
        return data;
    }

    public Boolean getAttiva() {
        return attiva;
    }
}
