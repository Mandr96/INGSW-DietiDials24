package com.example.demo.authService.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
/* Classe base per AuthenticationRequest (necessaria per jpa) */
public class AuthenticationRequest {

  private String email;
  String password;
}
