package com.example.demo.data_access;

import com.example.demo.model.Notifica;
import com.example.demo.model.Offerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificheRepository extends JpaRepository<Notifica, Long> {

    @Query(value = "SELECT * FROM Notifica WHERE user_email = ?1",
            nativeQuery = true)
    public List<Notifica> findByUser(String email);

    @Query(value = "SELECT * FROM Notifica WHERE user_email = ?1 and letto = false",
            nativeQuery = true)
    public List<Notifica> findUnread(String email);

    @Query(value = "UPDATE Notifica SET letto = true WHERE id = ?1",
            nativeQuery = true)
    public void setRead(Long id);
}
