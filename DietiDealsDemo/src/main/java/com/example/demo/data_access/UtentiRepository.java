package com.example.demo.data_access;

import com.example.demo.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtentiRepository extends JpaRepository<Utente, String> {
    //TODO Da togliere
    Optional<com.example.demo.model.Utente> findByEmail(String email);
}
