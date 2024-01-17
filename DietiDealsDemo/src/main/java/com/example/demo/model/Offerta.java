package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity(name = "Offerta")
public class Offerta {
    @Id
    @SequenceGenerator(
            name = "offerta_sequence",
            sequenceName = "offerta_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "offerta_sequence"
    )
    private Long id;
    private Float valore;
    private Timestamp data;
    private Boolean attiva;
    @ManyToOne
    //@JsonBackReference(value = "user-offer_reference") only for server
    private Utente owner;
    @ManyToOne
    //@JsonBackReference(value = "asta-offer_reference") only for server
    private Asta asta;
}
