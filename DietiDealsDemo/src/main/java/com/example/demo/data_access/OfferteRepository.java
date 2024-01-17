package com.example.demo.data_access;

import com.example.demo.model.Offerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OfferteRepository extends JpaRepository<Offerta, Long> {

    @Query(value = "SELECT * FROM Offerta WHERE owner_email = ?1",
            nativeQuery = true)
    public List<Offerta> findByUser(String email);

    @Query(value = "SELECT * FROM Offerta WHERE asta_id = ?1",
            nativeQuery = true)
    public List<Offerta> findByAsta(Long astaID);
}
