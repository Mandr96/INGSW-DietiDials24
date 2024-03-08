package com.example.demo.authService.auth;

import com.example.demo.authService.config.JwtService;
import com.example.demo.authService.token.Token;
import com.example.demo.authService.token.TokenRepository;
import com.example.demo.authService.token.TokenType;
import com.example.demo.data_access.UtentiRepository;
import com.example.demo.model.Utente;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UtentiRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  /** Crea l ogetto utente prendendo i dati dalla richiesta
   * codifica la password, salva l utente nel database
   * crea e salva il token e lo invia al client */
  public AuthenticationResponse register(RegisterRequest request) {
    var user = Utente.builder()
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .build();
    if(repository.findById(user.getEmail()).isPresent()){
      return AuthenticationResponse.builder().accessToken("error").build();
    }
    user.setNome(request.getNome());
    user.setCognome(request.getCognome());
    user.setCity(request.getCity());
    user.setShortbio(request.getShortbio());
    user.setProfilePic(request.getProfilePic());
    var savedUser = repository.save(user);
    var jwtToken = jwtService.generateToken(user);
    saveUserToken(savedUser, jwtToken);
    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
        .build();
  }

    /** Chiama l authenticationManager passandogli email e password dalla richiesta */
  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    System.out.println("Start Autenticate");
    String jwtToken;
    try {
      authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                      request.getEmail(),
                      request.getPassword()
              )
      );
      Utente user = repository.findById(request.getEmail()).orElseThrow(() -> new Exception("Error"));
      jwtToken = jwtService.generateToken(user);

      revokeAllUserTokens(user);
      saveUserToken(user, jwtToken);

    } catch (Exception e) {
      jwtToken = "error";
      System.out.println("Authentication failed: Invalid credentials");
    }

    System.out.println("jwt token = " + jwtToken);
    return AuthenticationResponse.builder()
            .accessToken(jwtToken)
            .build();

  }

  /** Crea un ogetto Token e lo inserisce nel database */
  private void saveUserToken(Utente utente, String jwtToken) {
    var token = Token.builder()
        .utente(utente)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  /** Revoca tutti i token di un determinato utente */
  private void revokeAllUserTokens(Utente utente) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(utente.getEmail());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }
}
