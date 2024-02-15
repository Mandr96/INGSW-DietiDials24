package com.main.dietidealsclient.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Token {
  public Integer id;
  public String token;
  public boolean revoked;
  public boolean expired;
  public Utente utente;
}
