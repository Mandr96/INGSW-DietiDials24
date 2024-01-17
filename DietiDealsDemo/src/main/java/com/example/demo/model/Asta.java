package com.example.demo.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AstaClassica.class, name = "AstaClassica"),
        @JsonSubTypes.Type(value = AstaInversa.class, name = "AstaInversa"),
        @JsonSubTypes.Type(value = AstaSilenziosa.class, name = "AstaSilenziosa")
})
@Entity
public abstract class Asta {
    @Id
    @SequenceGenerator(
            name = "asta_sequence",
            sequenceName = "asta_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "asta_sequence"
    )
    protected Long id;
    protected Timestamp scadenza;
    protected String categoria;
    protected String nomeProdotto;
    protected String descrizione;
    protected File img;
    protected Boolean scaduta;
    @ManyToOne
    //@JsonBackReference(value = "user-asta_reference") only for server
    protected Utente creatore;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "asta")
    @JsonManagedReference
    protected List<Offerta> offerte;

    public Asta(Long id, Timestamp scadenza, String nomeProdotto, String descrizione, String categoria, File img, Boolean scaduta, Utente creatore, List<Offerta> offerte) {
        this.id = id;
        this.scadenza = scadenza;
        this.categoria = categoria;
        this.nomeProdotto = nomeProdotto;
        this.descrizione = descrizione;
        this.img = img;
        this.scaduta = scaduta;
        this.creatore = creatore;
        this.offerte = offerte;
    }

    @Override
    public String toString() {
        return "Asta{" +
                "id=" + id +
                ", scadenza=" + scadenza +
                ", nomeProdotto='" + nomeProdotto + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", img=" + img +
                ", scaduta=" + scaduta +
                ", creatore=" + creatore +
                ", offerte=" + offerte +
                '}';
    }
}
