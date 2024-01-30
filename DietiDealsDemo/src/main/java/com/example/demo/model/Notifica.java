package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity(name = "Notifica")
public class Notifica {
    @Id
    @SequenceGenerator(
            name = "notifica_sequence",
            sequenceName = "notifica_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "notifica_sequence"
    )
    private Long id;
    private String oggetto;
    private String testo;
    private Boolean letto;
    @ManyToOne
    @JsonIgnore
    private Utente user;

    public Notifica(String oggetto, String testo, Boolean letto, Utente utente) {
        this.oggetto = oggetto;
        this.testo = testo;
        this.letto = letto;
        this.user = utente;
    }
}
