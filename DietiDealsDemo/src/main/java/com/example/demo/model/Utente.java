package com.example.demo.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "email")
@Entity
public class Utente {
    @Id
    private String email;
    private String nome;
    private String cognome;
    private String password;
    private String shortbio;
    private String city;
    private File profilePic;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    @JsonManagedReference
    private List<Notifica> notifiche;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "creatore")
    @JsonManagedReference
    private List<Asta> aste;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owner")
    @JsonManagedReference
    private List<Offerta> offerte;

    @JsonCreator
    public Utente(@JsonProperty("email") String email,
                  @JsonProperty("nome") String nome,
                  @JsonProperty("cognome") String cognome,
                  @JsonProperty("password") String password,
                  @JsonProperty("shortbio") String shortbio,
                  @JsonProperty("city") String city,
                  @JsonProperty("notifiche") List<Notifica> notifiche,
                  @JsonProperty("aste") List<Asta> aste,
                  @JsonProperty("offerte") List<Offerta> offerte) {
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.password = password;
        this.shortbio = shortbio;
        this.city = city;
        this.notifiche = notifiche;
        this.aste = aste;
        this.offerte = offerte;
    }
}
