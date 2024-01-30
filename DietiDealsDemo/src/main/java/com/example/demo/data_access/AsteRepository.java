package com.example.demo.data_access;

import com.example.demo.model.Asta;
import com.example.demo.model.Offerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AsteRepository extends JpaRepository<Asta, Long> {

    @Query(value =
            "SELECT a.* FROM Asta a " +
            "WHERE a.scaduta = false AND a.dtype = ?1 AND a.categoria LIKE ?2 AND a.nome_prodotto LIKE %?3% " +
            "ORDER BY (CASE WHEN a.nome_prodotto LIKE '% '||?3||' %' OR a.nome_prodotto LIKE '% '||?3 OR a.nome_prodotto LIKE ?3||' %' THEN 1 " +
            "ELSE 2 END), a.scadenza " +
            "LIMIT 10 OFFSET ?4",
            nativeQuery = true)
    public List<Asta> searchAste(String tipo, String categoria, String kw, int rowOffset);

    @Transactional
    @Modifying
    @Query(value = "UPDATE asta SET scaduta = true WHERE id = ?1",
            nativeQuery = true)
    public void setAstaScaduta(Long astaID);
}
