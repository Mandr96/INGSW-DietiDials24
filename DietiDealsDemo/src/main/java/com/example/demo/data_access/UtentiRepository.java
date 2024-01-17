package com.example.demo.data_access;

import com.example.demo.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtentiRepository extends JpaRepository<Utente, String> {
}
