package com.example.demo.authService.token;

import com.example.demo.model.Offerta;
import com.example.demo.model.Utente;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

  // Query personalizzata per ottenere tutti i token validi
  @Query(value = """
      select t from Token t inner join Utente u\s
      on t.utente.email = u.email\s
      where u.email = :email and (t.expired = false or t.revoked = false)\s
      """)
  List<Token> findAllValidTokenByUser(String email);

  //query per ottenere il token specifico tramite tokenID
  Optional<Token> findByToken(String token);

}
