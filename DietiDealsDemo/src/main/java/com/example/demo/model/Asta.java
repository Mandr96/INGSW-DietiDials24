package com.example.demo.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

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
    @Getter
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
    @Getter
    protected Timestamp scadenza;
    protected String categoria;
    protected String nomeProdotto;
    protected String descrizione;
    protected File img;
    protected Boolean scaduta;
    @ManyToOne
    @JsonIgnore
    protected Utente creatore;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "asta")
    @JsonIgnore
    protected List<Offerta> offerte;

    public abstract List<Notifica> chiudi();

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
        return nomeProdotto;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asta asta = (Asta) o;
        return Objects.equals(getId(), asta.getId()) && Objects.equals(getScadenza(), asta.getScadenza()) && Objects.equals(getCategoria(), asta.getCategoria()) && Objects.equals(getNomeProdotto(), asta.getNomeProdotto()) && Objects.equals(getDescrizione(), asta.getDescrizione()) && Objects.equals(getImg(), asta.getImg()) && Objects.equals(getScaduta(), asta.getScaduta()) && Objects.equals(getCreatore(), asta.getCreatore()) && Objects.equals(getOfferte(), asta.getOfferte());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getScadenza(), getCategoria(), getNomeProdotto(), getScaduta(), getCreatore());
    }
}
