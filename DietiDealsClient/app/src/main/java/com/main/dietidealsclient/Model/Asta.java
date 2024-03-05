package com.main.dietidealsclient.Model;

import android.util.Log;

import com.fasterxml.jackson.annotation.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.sql.Timestamp;
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

public abstract class Asta {

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
        this.id = 0L;
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
        return creatore.getEmail();
    }

    @JsonIgnore
    public abstract Offerta getBestOffer();

    public List<Offerta> getOfferte(){
        return offerte;
    }

    public void setOfferte(List<Offerta> offerte){
        this.offerte = offerte;
    }

    protected Offerta getBestOfferIn(boolean maggiore){
        Log.e("AsteController - getAstePartecipateDaUtente()","Offerte array" + offerte);
        if(offerte == null || offerte.isEmpty()){
            return null;
        }
        Offerta bestOff = offerte.get(0);
        Log.e("AsteController - getAstePartecipateDaUtente()","PreFOR" + bestOff);
        for (Offerta thisOff : offerte){
            if ( maggiore && (bestOff.getValore() < thisOff.getValore())){
                bestOff = thisOff;
            } else if (!maggiore && (bestOff.getValore() < thisOff.getValore())){
                bestOff = thisOff;
            }
        }
        return bestOff;
    }
}
