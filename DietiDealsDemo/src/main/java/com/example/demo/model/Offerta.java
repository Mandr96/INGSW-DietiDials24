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
import java.text.DecimalFormat;
import java.text.NumberFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity(name = "Offerta")
public class Offerta {
    @Getter
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
    @JsonIgnore
    private Utente owner;
    @ManyToOne
    @JsonIgnore
    private Asta asta;

    public String getValoreAsString() {
        NumberFormat formatter = new DecimalFormat("0.00");
        return formatter.format(valore)+" €";
    }
}
