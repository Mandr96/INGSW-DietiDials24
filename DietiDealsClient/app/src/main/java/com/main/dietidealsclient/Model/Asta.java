package com.main.dietidealsclient.Model;

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
}
