package com.example.demo.authService.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
/* Classe base per RegisterRequest (necessaria per jpa) */
public class RegisterRequest {

  private String nome;
  private String cognome;
  private String email;
  private String password;
}
