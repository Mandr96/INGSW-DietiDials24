package com.example.demo.data_access;

import com.example.demo.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UtentiRepository extends JpaRepository<Utente, String> {

    @Query(value =
                    "SELECT u.* FROM utente u, asta a " +
                    "WHERE u.email = a.creatore_email and a.id = ?1",
            nativeQuery = true)
    public Utente getAstaOwner(Long astaID);

    @Query(value =
                    "SELECT u.* FROM utente u, offerta o " +
                    "WHERE u.email = o.owner_email and o.id = ?1",
            nativeQuery = true)
    public Utente getOfferOwner(Long OfferID);
}
