package com.example.demo.data_access;

import com.example.demo.model.Asta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsteRepository extends JpaRepository<Asta, Long> {

}
