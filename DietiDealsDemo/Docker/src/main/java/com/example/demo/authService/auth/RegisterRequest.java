package com.example.demo.authService.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

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
  private String shortbio;
  private String city;
  private File profilePic;
}
