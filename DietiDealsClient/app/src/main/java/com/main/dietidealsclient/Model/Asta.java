package com.main.dietidealsclient.Model;

import android.util.Log;

import com.fasterxml.jackson.annotation.*;
import com.main.dietidealsclient.Controller.AsteController;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AstaClassica.class, name = "AstaClassica"),
        @JsonSubTypes.Type(value = AstaInversa.class, name = "AstaInversa"),
        @JsonSubTypes.Type(value = AstaSilenziosa.class, name = "AstaSilenziosa")
})

public abstract class Asta implements Serializable {

    public void setId(Long id) {
        this.id = id;
    }

    protected Long id;
    protected Timestamp scadenza;
    protected String categoria;
    protected String nomeProdotto;
    protected String descrizione;
    protected File img;
    protected Boolean scaduta;
    protected Utente creatore;

    @JsonIgnore
    protected List<Offerta> offerte;


    @JsonCreator
    public Asta(Long id, Timestamp scadenza, String nomeProdotto, String descrizione, String categoria, File img, Boolean scaduta, Utente creatore) {
        this.id = id;
        this.scadenza = scadenza;
        this.categoria = categoria;
        this.nomeProdotto = nomeProdotto;
        this.descrizione = descrizione;
        this.img = img;
        this.scaduta = scaduta;
        this.creatore = creatore;
    }

    public Asta(Timestamp scadenza, String nomeProdotto, String descrizione, String categoria, File img, Utente creatore) {
        this.id = -1L;
        this.scadenza = scadenza;
        this.categoria = categoria;
        this.nomeProdotto = nomeProdotto;
        this.descrizione = descrizione;
        this.img = img;
        this.scaduta = false;
        this.creatore = creatore;
    }

    @Override
    public String toString() {
        return nomeProdotto;
    }

    public String getNomeProdotto() {
        return nomeProdotto;
    }

    public Long getId() {
        return id;
    }

    public Timestamp getScadenza() {
        return scadenza;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public File getImg() {
        return img;
    }

    public Boolean getScaduta() {
        return scaduta;
    }

    @JsonGetter("creatore")
    public String getCreatore() {
        if (id == -1){
            return creatore.getEmail();
        }
        AsteController asteController = new AsteController();
        return asteController.getAstaOwnerEmail(id);
    }

    @JsonIgnore
    public abstract Offerta getBestOffer();
    public abstract String getTypeAsString();
    public abstract Float getActualPrice();

//    @JsonIgnore
//    public Float getBestOfferVal(){
//        Offerta bestOff = getBestOffer();
//        return (bestOff == null) ? 0 : bestOff.getValore();
//    }

    public List<Offerta> getOfferte(){
        return offerte;
    }

    public void setOfferte(List<Offerta> offerte){
        this.offerte = offerte;
    }

    protected Offerta getBestOfferIn(boolean maggiore){
        if(offerte == null || offerte.isEmpty()){
            return new Offerta(-1L, 0F,null, false, null, null);
        }
        Offerta bestOff = offerte.get(0);
        for (Offerta thisOff : offerte){
            if ( maggiore && (bestOff.getValore() < thisOff.getValore())){
                bestOff = thisOff;
            } else if (!maggiore && (bestOff.getValore() < thisOff.getValore())){
                bestOff = thisOff;
            }
        }
        return bestOff;
    }

    public void setCreatore(Utente creatore) {
        this.creatore = creatore;
    }

    public String getDurata() {
        long secs = scadenza.toInstant().minusSeconds(Instant.now().getEpochSecond()).getEpochSecond();
        long hours = secs/3600;
        return Math.floorDiv(hours, 24)+"d "+hours%24+"h";
    }
}
